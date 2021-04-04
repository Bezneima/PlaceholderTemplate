package PlaceholderTemplate.RestControllers;

import PlaceholderTemplate.Services.UserService;
import PlaceholderTemplate.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(@Autowired UserService userService){
        this.userService = userService;
    }

    @CrossOrigin
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String authorization(@RequestBody String requestBody) {
        return userService.getToken(requestBody);
    }

    @CrossOrigin
    @RequestMapping(value = "/checkAuthToken",method = RequestMethod.POST)
    public boolean checkAuthToken(@RequestBody String requestBody){
        return userService.checkToken(requestBody);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUser(@RequestParam String userName, @RequestParam String role) {
        User user = new User(role, userName);
        return userService.saveUser(user);
    }

    @CrossOrigin
    @RequestMapping(value = "/getAvatar", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> getAvatar(@RequestParam String userToken) throws FileNotFoundException {
        return userService.getUserAvatar(userToken);
    }

    @CrossOrigin
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public String getUserInfo(@RequestBody String requestBody){
        return userService.getUserByToken(requestBody);
    }
}

