package PlaceholderTemplate.Services;

import PlaceholderTemplate.Dao.UserDao;
import PlaceholderTemplate.dto.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDao usersDao = new UserDao();
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService() {
    }

    public User findUserById(int id) {
        return usersDao.findById(id);
    }

    public String getToken(String name, String password) {
        User user = usersDao.findByNameAndPass(name, password);

        String md5Token = DigestUtils
                .md5Hex(name + password + java.util.Calendar.getInstance().getTime().toString()).toUpperCase();
        usersDao.setToken(name, md5Token);
        if (user != null)
            return md5Token;
        else
            return "false";
    }

    public String getUserByToken(String requestBody){
        Gson gson = new Gson();
        User requestedUser = gson.fromJson(requestBody, User.class);
        return gson.toJson(usersDao.findByTokenAndName(requestedUser));
    }

    public boolean checkToken(String login, String token) {
        return usersDao.checkToken(login, token);
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
