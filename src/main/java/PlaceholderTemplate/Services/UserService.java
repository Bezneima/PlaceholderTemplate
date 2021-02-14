package PlaceholderTemplate.Services;

import PlaceholderTemplate.Dao.UserDao;
import PlaceholderTemplate.dto.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

public class UserService {
    private final UserDao usersDao = new UserDao();

    public UserService() {
    }

    public User findUserById(int id) {
        return usersDao.findById(id);
    }

    public String getToken(String name, String password) {
        User user = usersDao.findByNameAndPass(name, password);

        String md5Token = DigestUtils
                .md5Hex(name + password + java.util.Calendar.getInstance().getTime().toString()).toUpperCase();
        usersDao.setToken(name,md5Token);
        if (user != null)
            return md5Token;
        else
            return "false";
    }

    public boolean checkToken(String login,String token){
        return usersDao.checkToken(login,token);
    }

    public void saveUser(User user) {
        usersDao.save(user);
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
