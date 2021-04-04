package PlaceholderTemplate.Services;

import PlaceholderTemplate.Dao.UserDao;
import PlaceholderTemplate.FileUtils.MediaTypeUtils;
import PlaceholderTemplate.dto.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.MediaType;

import javax.servlet.ServletContext;

@Service
public class UserService {
    private final UserDao usersDao = new UserDao();
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private ServletContext servletContext;

    public UserService() {
    }

    public User findUserById(int id) {
        return usersDao.findById(id);
    }

    public String getToken(String requestBody) {

        Gson gson = new Gson();
        User requestedUser = gson.fromJson(requestBody, User.class);
        User user = usersDao.findByNameAndPass(requestedUser.getUserName(), requestedUser.getPassword());

        String md5Token = DigestUtils
                .md5Hex(requestedUser.getUserName() + requestedUser.getPassword() + java.util.Calendar.getInstance().getTime().toString()).toUpperCase();
        if (user != null) {
            usersDao.setToken(requestedUser.getUserName(), md5Token);
            user.setLastUserToken(md5Token);
            return gson.toJson(user);
        }
        else
            return "false";
    }

    public String getUserByToken(String requestBody) {
        Gson gson = new Gson();
        User requestedUser = gson.fromJson(requestBody, User.class);
        return gson.toJson(usersDao.findUserByTokenAndName(requestedUser));
    }

    public boolean checkToken(String requestBody) {
        Gson gson = new Gson();
        User requestedUser = gson.fromJson(requestBody, User.class);
        return usersDao.checkToken(requestedUser.getUserName(), requestedUser.getLastUserToken());
    }

    public String saveUser(User user) {
        System.out.println("user.getUserName() != null");
        if (user.getUserName().equals("") || user.getUserName() == null) {
            log.error("Missing userName!");
            return "Missing userName!";
        }
        if (user.getRole().equals("") || user.getRole() == null) {
            user.setRole("user");
        }
        usersDao.save(user);
        return "200";
    }
    public ResponseEntity<InputStreamResource> getUserAvatar(String userToken) throws FileNotFoundException {
        File file = new File("uploads/avatars/"+ usersDao.findAvatarByToken(userToken));
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, "avatar.png");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "filename=avatar.png" )
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public List<User> findAllUsers() {
        return usersDao.findAll();
    }
}
