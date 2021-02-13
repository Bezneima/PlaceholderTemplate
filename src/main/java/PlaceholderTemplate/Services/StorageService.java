package PlaceholderTemplate.Services;

import PlaceholderTemplate.Exceptions.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StorageService {
    @Value("${upload.path}")
    private String path;
    private static final Logger log = LoggerFactory.getLogger(StorageService.class);

    public void uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }
        if(!isDoc(file)){
            throw new StorageException("Wrong format!");
        }
        try {
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();
            Files.copy(is, Paths.get(path+fileName),StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Failed to store file:{}",file.getName(),e);
        }
    }
    public boolean isDoc(MultipartFile file) {
        String fileFormat = "";
        String fileName = file.getOriginalFilename();
        try {
            if (fileName.contains(".")) {
                fileFormat = fileName.substring(fileName.lastIndexOf(".") + 1);
            }
        } catch (NullPointerException e){
            log.error("Missing file extension:{}", fileFormat, e);
        }
        return fileFormat.equals("doc");
    }
}