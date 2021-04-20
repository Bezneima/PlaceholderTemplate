package PlaceholderTemplate.RestControllers;


import PlaceholderTemplate.Exceptions.StorageException;
import PlaceholderTemplate.Services.StorageService;
import PlaceholderTemplate.Services.UserService;
import PlaceholderTemplate.dto.User;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
public class FileController {

    private final StorageService storageService;
    private final UserService userService;

    public FileController(@Autowired StorageService storageService,
                          @Autowired UserService userService) {
        this.storageService = storageService;
        this.userService = userService;
    }

    @CrossOrigin
    @RequestMapping(value = "/CheckToken", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public @ResponseBody String CheckToken(@RequestBody String requestBody) {
        String result = userService.getUserByToken(requestBody);
        return result;
    }

    @GetMapping(
            value = "/download",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String groupName, @RequestParam String fileName) throws IOException {
        return storageService.downloadFile(groupName, false, fileName);
    }

    @GetMapping(
            value = "/getFileInputFields"
    )
    public String getFileInputFields(@RequestParam String fileMd5Hash) {
        return storageService.getFileInputFields(fileMd5Hash);
    }

    @RequestMapping(
            value = "/upload",
            method = RequestMethod.POST,
            consumes = {"multipart/form-data"}
    )
    public String upload(@RequestParam MultipartFile file//,
                         //@RequestParam String UploadedGroup,
                         //@RequestParam boolean isTemplate
    ) {
        return storageService.uploadFileToGroup(file, "1", false);
    }


    @CrossOrigin
    @RequestMapping(
            value = "/getAllUserFiles",
            method = RequestMethod.POST
    )
    public String getAllUserFiles(@RequestBody String requestBody){
        return storageService.getAllUserFilesLinks(requestBody);

    }

    @ExceptionHandler(StorageException.class)
    public String handleStorageFileNotFound(StorageException e) {
        return "redirect:/failure.html";
    }


}
