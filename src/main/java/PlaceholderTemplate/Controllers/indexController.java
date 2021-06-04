package PlaceholderTemplate.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;
import java.io.OutputStream;

@Controller
public class indexController {
    @RequestMapping(value = "/")
    public String index() throws FileNotFoundException {
        return "redirect:/index.html";
    }
}
