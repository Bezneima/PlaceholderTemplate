package PlaceholderTemplate;



import PlaceholderTemplate.Exceptions.StorageException;
import PlaceholderTemplate.Services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class FileUploadController {

    @Autowired
    private StorageService storageService;

    @GetMapping(
            value = "/get-file",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    void getFile() throws IOException {

    }

    @RequestMapping(value = "/doUpload", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public String upload(@RequestParam MultipartFile file) {
        storageService.uploadFile(file);
        return "redirect:/success.html";
    }

    @RequestMapping(value = "/")
    public String index(){
        return "redirect:/index.html";
    }

    @ExceptionHandler(StorageException.class)
    public String handleStorageFileNotFound(StorageException e) {
        return "redirect:/failure.html";
    }




}
