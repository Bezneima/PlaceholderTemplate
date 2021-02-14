package PlaceholderTemplate.Dao;

import PlaceholderTemplate.HibernateSessionFactoryUtil;
import PlaceholderTemplate.dto.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

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
            System.out.println(e);
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
            System.out.println(e);
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
