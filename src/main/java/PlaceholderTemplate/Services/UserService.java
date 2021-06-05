package PlaceholderTemplate.Services;

import PlaceholderTemplate.Dao.GroupDao;
import PlaceholderTemplate.Dao.UserDao;
import PlaceholderTemplate.FileUtils.MediaTypeUtils;
import PlaceholderTemplate.dto.Group;
import PlaceholderTemplate.dto.ProfileGroups;
import PlaceholderTemplate.dto.User;
import PlaceholderTemplate.dto.Users;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
    private final GroupDao groupDao = new GroupDao();
    private final Gson gson = new Gson();
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
    public String getAllExistingGroupsNames (){
        List<ProfileGroups> res = new ArrayList<ProfileGroups>();
        groupDao.findAllExistingGroupsNames().forEach(groupName ->{
            res.add(new ProfileGroups(groupName,groupName));
        });
        return gson.toJson(res);
    }
    public String getAllUsersGroups (String userName){
        List<ProfileGroups> res = new ArrayList<ProfileGroups>();
        groupDao.findAllUsersGroup(userName).forEach(group -> {
            res.add(new ProfileGroups(group.getGroupName(), group.getGroupName()));
        });
        return gson.toJson(res);
    }

    public List<String> setUserToGroups(String userName, String requestBody){
        List<String> groupsNamesToSetUser = new ArrayList<String>();
        ProfileGroups[] usersGroups = gson.fromJson(requestBody, ProfileGroups[].class);
        for (ProfileGroups group : usersGroups) {
            groupsNamesToSetUser.add(group.getValue());
        }
        groupDao.findAllUsersGroup(userName).forEach(group -> {
            groupDao.deleteUserFromGroup(userName, group.getGroupName());
        });
        groupsNamesToSetUser.forEach(groupName -> {
            Group group = new Group();
            group.setGroupId(groupDao.getGroupId(groupName).get(0));
            group.setGroupName(groupName);
            group.setMemberName(userName);
            group.setGroupAdmin(false);
            groupDao.saveUserToGroup(group);
        });

        return groupsNamesToSetUser;
    }

    public String getAllUsers (){
        List<Users> res = new ArrayList<Users>();
        usersDao.findAllUsers().forEach(userName ->{
            res.add(new Users(userName, userName));
        });
        return gson.toJson(res);
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
