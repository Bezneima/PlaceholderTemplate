package PlaceholderTemplate.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;
import java.io.OutputStream;

@Controller
public class indexController {
    @RequestMapping(value = "/")
    public String index() throws FileNotFoundException {
//        String inputfilepath = "uploads/group/1/docs/BF70BA8F8DFD6402116087EE2F343208";
//        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new java.io.File(inputfilepath));
//
//        String outputfilepath = "uploads/group/1/docs/output.tif";
//        OutputStream os = new java.io.FileOutputStream(outputfilepath);
//
//        FOSettings settings = Docx4J.createFOSettings();
//        settings.setWmlPackage(wordMLPackage);
//        settings.setApacheFopMime("image/tiff");
//        Docx4J.toFO(settings, os, Docx4J.FLAG_NONE);
        return "redirect:/index.html";
    }
}
