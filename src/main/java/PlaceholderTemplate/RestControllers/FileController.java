package PlaceholderTemplate.RestControllers;


import PlaceholderTemplate.Exceptions.StorageException;
import PlaceholderTemplate.Services.StorageService;
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

    public FileController(@Autowired StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(
            value = "/download",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<InputStreamResource> downloadFile() throws IOException {
        return storageService.downloadFile("1",false,"20AB837C1B723374BF75DF21EEEBB3E1");
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
        return storageService.uploadFileToGroup(file,"1",false);
    }

    @ExceptionHandler(StorageException.class)
    public String handleStorageFileNotFound(StorageException e) {
        return "redirect:/failure.html";
    }


}
