package PlaceholderTemplate.RestControllers;

import PlaceholderTemplate.Services.UserService;
import PlaceholderTemplate.dto.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController {
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String authorization(@RequestParam String login, @RequestParam String password) {
        UserService userService = new UserService();
        return userService.getToken(login, password);
    }

    @RequestMapping(value = "/checkAuthToken",method = RequestMethod.POST)
    public boolean checkAuthToken(String login,String token){
        UserService userService = new UserService();
        return userService.checkToken(login, token);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUser(@RequestParam String userName, @RequestParam String role) {
        UserService userService = new UserService();
        User user = new User(role, userName);
        /*userService.saveUser(user);*/
        return userService.saveUser(user);
    }


}
