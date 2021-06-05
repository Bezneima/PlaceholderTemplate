package PlaceholderTemplate.Services;

import PlaceholderTemplate.Dao.*;
import PlaceholderTemplate.DocxWorker;
import PlaceholderTemplate.Exceptions.StorageException;
import PlaceholderTemplate.FileUtils.FileUtils;
import PlaceholderTemplate.FileUtils.MediaTypeUtils;
import PlaceholderTemplate.dto.*;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import PlaceholderTemplate.FileUtils.FileFormat;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;

import static PlaceholderTemplate.FileUtils.FileUtils.CheckOrMakePath;

@Service
public class StorageService {
    @Autowired
    private ServletContext servletContext;
    @Value("${upload.path}")
    private String uploadsPath;
    private final UserDao usersDao;
    private final GroupDao groupDao;
    private final TemplateFilesDao templateFilesDao;
    private final DocFilesDao docFilesDao;
    FileUtils fileUtils;
    private static final Logger log = LoggerFactory.getLogger(StorageService.class);
    private final Gson gson = new Gson();


    StorageService(@Autowired UserDao userDao,
                   @Autowired DocFilesDao docFilesDao,
                   @Autowired TemplateFilesDao templateFilesDao,
                   @Autowired GroupDao groupDao,
                   @Autowired FileUtils FileUtils) {
        this.usersDao = userDao;
        this.docFilesDao = docFilesDao;
        this.templateFilesDao = templateFilesDao;
        this.groupDao = groupDao;
        this.fileUtils = FileUtils;
    }

    public String uploadFileToGroup(MultipartFile file, String UploadeGroupId, boolean isTemplate) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }
        /* TODO Потом убрать комментарий
        if (!IsValidDocumentFormat(file)) {
            throw new StorageException("Wrong format!");
            return "Wrong format!";
        }
        */
        StringBuilder stringBuilder = new StringBuilder(UploadeGroupId);
        String md5CashedName = DigestUtils.md5Hex(
                stringBuilder
                        .append(file.getOriginalFilename())
                        .append(java.util.Calendar.getInstance().getTime().toString()).toString()).toUpperCase();
        StringBuilder finalFilePathStringBuilder = new StringBuilder("");
        if (!isTemplate) {
            finalFilePathStringBuilder.append("group/").append(UploadeGroupId).append("/docs");
            try {
                writeFileToPath(file, finalFilePathStringBuilder.toString(), md5CashedName);
                List<InputFields> allFileInputFieldsName = DocxWorker.getAllInputFieldsName("uploads/" + finalFilePathStringBuilder.toString() + "/" + md5CashedName);
                String allFileInputFieldsNameJson = gson.toJson(allFileInputFieldsName);
                DocFiles docFiles = new DocFiles(
                        UploadeGroupId,
                        file.getOriginalFilename(),
                        finalFilePathStringBuilder.toString(),
                        "FileFormat",
                        md5CashedName,
                        allFileInputFieldsNameJson
                );
                docFilesDao.save(docFiles);
            } catch (Exception e) {
                log.error("Error while getAllInputFieldsName({})",
                        finalFilePathStringBuilder.append("/").append(md5CashedName).toString(),
                        e);
                return "Error while getting ${X} fields name(x)";
            }
        } else {
            finalFilePathStringBuilder.append("group/").append(UploadeGroupId).append("/templates");
            TemplateFiles templateFile = new TemplateFiles(
                    UploadeGroupId,
                    file.getOriginalFilename(),
                    finalFilePathStringBuilder.toString(),
                    "FileFormat",
                    md5CashedName
            );
            templateFilesDao.save(templateFile);
            writeFileToPath(file, finalFilePathStringBuilder.toString(), md5CashedName);
        }

        return "File was uploaded";
    }

    public boolean IsValidDocumentFormat(MultipartFile file) {
        String formatsStr;
        String fileName = file.getOriginalFilename();
        String formatOfFile = "";
        formatsStr = Arrays.toString(FileFormat.values());
        String[] formats = formatsStr.replace("[", "").replace("]", "").
                split(". ");
        boolean isValid = false;
        try {
            assert fileName != null;
            if (fileName.contains(".")) {
                formatOfFile = fileName.substring(fileName.lastIndexOf(".") + 1);
            }
        } catch (NullPointerException e) {
            log.error("Missing file extension:{}", formatOfFile, e);
        }

        for (String format : formats) {
            if (formatOfFile.equals(format.toLowerCase())) isValid = true;
        }
        return isValid;
    }

    public void writeFileToPath(MultipartFile file, String relativePath, String fileName) {
        try {
            CheckOrMakePath(relativePath);
            InputStream is = file.getInputStream();
            Files.copy(is, Paths.get(uploadsPath + relativePath + "/" + fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Failed to store file:{}", file.getName(), e);
        }
    }


    public ResponseEntity<InputStreamResource> downloadFile(String groupName, boolean isTemplate, String fileHashName, String fileName) throws IOException {
        try {
            MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileHashName);
            StringBuilder stringBuilder = new StringBuilder("");
            String filenameFinal;
            if (isTemplate) {
                stringBuilder.append("uploads/temp/").append(fileHashName);
                filenameFinal = fileName;
            } else {
                stringBuilder.append("uploads/group/").append(groupName).append("/docs/").append(fileHashName);
                filenameFinal = new String(docFilesDao.getFileName(fileHashName).getBytes("Cp1251"), "Cp1252");
            }
            File file = new File(stringBuilder.toString());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filenameFinal)
                    .contentType(mediaType)
                    .contentLength(file.length())
                    .body(resource);
        } catch (IOException ioException) {
            log.error("error while download file with fileName={} isTemplate={} groupName={}",
                    fileHashName,
                    isTemplate,
                    groupName,
                    ioException
            );
            throw new IOException(ioException);
        }
    }

    public String downloadFilledTemplate(String groupName, String fields, String fileHashName, String fileName) throws Exception {
        String outputPath = DigestUtils.md5Hex(fileHashName + java.util.Calendar.getInstance().getTime().toString()).toUpperCase();
        CheckOrMakePath("temp/" + outputPath + "/notZip/");
        Map map = new HashMap();
        System.out.println(fields);
        DocumentJSON[] res = gson.fromJson(fields, DocumentJSON[].class);
        for (DocumentJSON doc : res) {
            for (Changes change : doc.getChanges()) {
                map.put(change.getValue(), change.getValueTo());
            }
            System.out.println(map);
            // Сделать проверку на одинаковость названий файлов
            Files.createFile(Paths.get("uploads/temp/" + outputPath + "/notZip/" + doc.getTitle()));
            OutputStream os = new java.io.FileOutputStream("uploads/temp/" + outputPath + "/notZip/" + doc.getTitle());

            DocxWorker.replace(
                    "uploads/group/" + groupName + "/docs/" + fileHashName,
                    map,
                    os);
            os.close();
        }
        //CheckOrMakePath("temp/" + outputPath + "/zip");
        return fileUtils.zipDirectory("uploads/temp/"+ outputPath + "/notZip", outputPath + "/zip");
    }

    public String getAllUserFilesLinks(String requestBody) {

        User requestedUser = gson.fromJson(requestBody, User.class);
        if (usersDao.checkToken(requestedUser.getUserName(), requestedUser.getLastUserToken())) {
            List<Group> userGroups = groupDao.findAllUsersGroup("member_name");
            List<DocFiles> fileLinks = new LinkedList<>();
            userGroups.forEach(group -> {
                fileLinks.addAll(docFilesDao.getAllFileLinksByGroupId(group.getGroupId()));
            });
            return gson.toJson(fileLinks);
        } else {
            return null;//TODO обдумать что будет  если не будет доступа.
        }
    }

    public String deleteUserFile(String requestBody) {
        DocFiles deletedFile = gson.fromJson(requestBody, DocFiles.class);
        docFilesDao.deleteFile(deletedFile);
        return "[]";
    }

    public String getFileInputFields(String fileMd5Hash) {
        return docFilesDao.getFileInputFields(fileMd5Hash);
    }
}