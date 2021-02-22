package PlaceholderTemplate.Services;

import PlaceholderTemplate.Dao.DocFilesDao;
import PlaceholderTemplate.Dao.TemplateFilesDao;
import PlaceholderTemplate.Dao.UserDao;
import PlaceholderTemplate.DocxWorker;
import PlaceholderTemplate.Exceptions.StorageException;
import PlaceholderTemplate.FileUtils.MediaTypeUtils;
import PlaceholderTemplate.dto.DocFiles;
import PlaceholderTemplate.dto.TemplateFiles;
import com.google.gson.Gson;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

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
    private final TemplateFilesDao templateFilesDao;
    private final DocFilesDao docFilesDao;
    private static final Logger log = LoggerFactory.getLogger(StorageService.class);

    StorageService(@Autowired UserDao userDao,
                   @Autowired DocFilesDao docFilesDao,
                   @Autowired TemplateFilesDao templateFilesDao) {
        this.usersDao = userDao;
        this.docFilesDao = docFilesDao;
        this.templateFilesDao = templateFilesDao;
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
                List<String> allFileInputFieldsName = DocxWorker.getAllInputFieldsName("uploads/"+finalFilePathStringBuilder.toString() + "/" + md5CashedName);
                Gson gson = new Gson();
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


    public ResponseEntity<InputStreamResource> downloadFile(String groupName, boolean isTemplate, String fileName) throws IOException {
        try {
            MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
            StringBuilder stringBuilder = new StringBuilder("uploads/group/");
            if (isTemplate)
                stringBuilder.append(groupName).append("/templates/").append(fileName);
            else
                stringBuilder.append(groupName).append("/docs/").append(fileName);
            File file = new File(stringBuilder.toString());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                    .contentType(mediaType)
                    .contentLength(file.length())
                    .body(resource);
        } catch (IOException ioException) {
            log.error("error while download file with fileName={} isTemplate={} groupName={}",
                    fileName,
                    isTemplate,
                    groupName,
                    ioException
            );
            throw new IOException(ioException);
        }
    }

    public String getFileInputFields(String fileMd5Hash){
        return docFilesDao.getFileInputFields(fileMd5Hash);
    }
}