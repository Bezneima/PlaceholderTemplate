package PlaceholderTemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        /*

        final LocalOfficeManager officeManager =
                LocalOfficeManager.builder()
                        .install()
                        .officeHome("/Applications/OpenOffice.app/Contents")
                        .portNumbers(2002)
                        .build();


        try {
            // Start an office process and connect to the started instance (on port 2002).
            officeManager.start();
            File inputFile = new File("uploads/group/1/docs/BF70BA8F8DFD6402116087EE2F343208.docx");
            final File outputFile = new File("uploads/group/1/docs/document.odt");
            final PageSelectorFilter selectorFilter = new PageSelectorFilter(1);
            // Convert
            LocalConverter
                    .builder()
                    .filterChain(selectorFilter)
                    .build()
                    .convert(inputFile)
                    .to(outputFile)
                    .execute();
        } finally {
            // Stop the office process
            LocalOfficeUtils.stopQuietly(officeManager);
        }
        */
        SpringApplication.run(Application.class, args);
    }

}
