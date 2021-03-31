package PlaceholderTemplate.RestControllers;

import PlaceholderTemplate.Services.UserService;
import PlaceholderTemplate.dto.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController {

    @CrossOrigin
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String authorization(@RequestBody String requestBody) {
        UserService userService = new UserService();
        return userService.getToken(requestBody);
    }

    @CrossOrigin
    @RequestMapping(value = "/checkAuthToken",method = RequestMethod.POST)
    public boolean checkAuthToken(@RequestBody String requestBody){
        UserService userService = new UserService();
        return userService.checkToken(requestBody);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUser(@RequestParam String userName, @RequestParam String role) {
        UserService userService = new UserService();
        User user = new User(role, userName);
        /*userService.saveUser(user);*/
        return userService.saveUser(user);
    }


}
