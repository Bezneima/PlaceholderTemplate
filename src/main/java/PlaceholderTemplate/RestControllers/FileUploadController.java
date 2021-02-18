package PlaceholderTemplate.RestControllers;


import PlaceholderTemplate.Exceptions.StorageException;
import PlaceholderTemplate.Services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class FileUploadController {

    private final StorageService storageService;

    public FileUploadController(@Autowired StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(
            value = "/get-file",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    void getFile() throws IOException {

    }

    @RequestMapping(
            value = "/upload",
            method = RequestMethod.POST,
            consumes = {"multipart/form-data"}
    )
    public String upload(@RequestParam MultipartFile file//,
                         //@RequestParam String UploadeUserName,
                         //@RequestParam boolean isTemplate
    ) {
        return storageService.uploadFileToGroup(file,"1",true);
    }

    @ExceptionHandler(StorageException.class)
    public String handleStorageFileNotFound(StorageException e) {
        return "redirect:/failure.html";
    }


}
