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
public class FileUploadController {

    private final StorageService storageService;

    public FileUploadController(@Autowired StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(
            value = "/download",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<InputStreamResource> downloadFile() throws IOException {
        return storageService.downloadFile("1",true,"contractors.conf");
    }

    @RequestMapping(
            value = "/upload",
            method = RequestMethod.POST,
            consumes = {"multipart/form-data"}
    )
    public String upload(@RequestParam MultipartFile file,
                         @RequestParam String UploadedGroup,
                         @RequestParam boolean isTemplate
    ) {
        return storageService.uploadFileToGroup(file,UploadedGroup,isTemplate);
    }

    @ExceptionHandler(StorageException.class)
    public String handleStorageFileNotFound(StorageException e) {
        return "redirect:/failure.html";
    }


}
