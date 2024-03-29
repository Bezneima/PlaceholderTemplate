package PlaceholderTemplate.Dao;

import PlaceholderTemplate.HibernateSessionFactoryUtil;
import PlaceholderTemplate.dto.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    public User findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
    }

    public User findByNameAndPass(String name, String password) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from User where userName = '" + name + "' and password='" + password + "'");
            List users = query.list();
            return (User) users.get(0);
        } catch (Exception e) {
            log.error("Some fails with finding user with userName = {} and password = {}", name, password, e);
            return null;
        }
    }

    public User findByName(String name) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from User where userName = '" + name + "'");
            List users = query.list();
            return (User) users.get(0);
        } catch (Exception e) {
            log.error("Some fails with finding user with userName = {}", name, e);
            return null;
        }
    }

    public boolean findByTokenAndName(User requestedUser) {//TODO чет много условий обдумать как делают нормально
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            if (requestedUser.getUserName() != null && requestedUser.getLastUserToken() != null) {
                Query query = session.createQuery("from User where lastUserToken = '" + requestedUser.getLastUserToken() + "' and userName ='" + requestedUser.getUserName() + "'");
                List users = query.list();
                return users.size() > 0;
            }
            return false;
        } catch (Exception e) {
            log.error("Some fails with finding user with userName = {}", requestedUser.getUserName(), e);
            return false;
        }
    }
    public User findUserByTokenAndName(User requestedUser){
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            if (requestedUser.getUserName() != null && requestedUser.getLastUserToken() != null) {
                Query query = session.createQuery("from User where lastUserToken = '" + requestedUser.getLastUserToken() + "' and userName ='" + requestedUser.getUserName() + "'");
                List<User> users = query.list();
                users.get(0).setPassword("");
                return users.get(0);
            }
            return null;
        } catch (Exception e) {
            log.error("Some fails with finding user with userName = {}", requestedUser.getUserName(), e);
            return null;
        }
    }

    public void setToken(String login, String token) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(
                "update User set " +
                        "lastUserToken = :token, " +
                        "tokenCreatedAt = :tokenCreatedAt " +
                        "where userName = :login"
        );
        query.setParameter("login", login);
        query.setParameter("token", token);
        query.setParameter("tokenCreatedAt", java.time.LocalDateTime.now());
        int result = query.executeUpdate();
        transaction.commit();
        session.close();
    }
    public String findAvatarByToken(String userToken){
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            if(userToken != null){
                Query query = session.createQuery("from User where lastUserToken = '" + userToken + "'");
                List<User> users = query.list();
                return users.get(0).getAvatarImgPath();
            }
                return null;
        }
        catch (Exception e) {
            log.error("Some fails with finding user with userToken = {}", userToken, e);
            return null;
        }
    }
    public List<String> findAllUsers(){
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("select distinct userName FROM User");
            List<String> usersNames = query.list();
            return usersNames;
        } catch (Exception e) {
            log.error("Some fails with finding groups", e);
            return null;
        }
    }

    public boolean checkToken(String login, String token) {
        User user = findByName(login);
        if (user != null &&
                user.getLastUserToken().equals(token) &&
                Duration.between(
                        user.getTokenCreatedAt(),
                        java.time.LocalDateTime.now()
                ).getSeconds() <= 60 * 60) {
            setToken(login, token);
            return true;
        }
        return false;
    }


    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    public void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    public List<User> findAll() {
        List<User> users = (List<User>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User").list();
        return users;
    }
}
