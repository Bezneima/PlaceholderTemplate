package PlaceholderTemplate.FileUtils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.Objects;


public class FileUtils {
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
    public static void writeFileToResourses(String filename,FileFormat fileFormat) throws IOException {
        FileWriter fileWriter = new FileWriter("src/main/resources/"+filename+fileFormat);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print("Some String");
        printWriter.printf("Product name is %s and its price is %d $", "iPhone", 1000);
        printWriter.close();
    }

    public static byte[] getFileinByteFromResource(String filePath) throws IOException {
        InputStream resource = null;
        try {
            resource = new ClassPathResource(filePath).getInputStream();
            return IOUtils.toByteArray(resource);
        } catch (IOException e) {
            log.error("I can not read the file", e);
        }
        return null;
    }
}
