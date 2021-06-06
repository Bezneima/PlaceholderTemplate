package PlaceholderTemplate.Dao;

import PlaceholderTemplate.HibernateSessionFactoryUtil;
import PlaceholderTemplate.dto.Group;
import PlaceholderTemplate.dto.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupDao {
    private static final Logger log = LoggerFactory.getLogger(GroupDao.class);

    public List<Group> findAllUsersGroup(String userName) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Group where memberName = '" + userName + "'");
            List<Group> groups = query.list();
            return groups;
        } catch (Exception e) {
            log.error("Some fails with finding group of userName = {}", userName, e);
            return null;
        }
    }
    public List<String> findAllExistingGroupsNames(){
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("select distinct groupName FROM Group");
            List<String> groups = query.list();
            return groups;
        } catch (Exception e) {
            log.error("Some fails with finding groups", e);
            return null;
        }
    }
    public List<Integer> getGroupId(String groupName){
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("select distinct groupId FROM Group where groupName = '" + groupName + "'");
            List<Integer> groupId = query.list();
            return groupId;
        } catch (Exception e) {
            log.error("Some fails with finding groups", e);
            return null;
        }
    }
    public String deleteUserFromGroup(String userName, String groupName) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("delete from Group where groupName = '" + groupName + "' and memberName = '" + userName + "'");
            Transaction transaction = session.beginTransaction();
            int result = query.executeUpdate();
            if (result > 0) {
                System.out.println("remove user where userName=" + userName + "from" + groupName);
            }
            transaction.commit();
            return "[]";
        } catch (Exception e) {
            return null;
        }
    }
    public Integer countOfUniqueGroups(){
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("select distinct groupName FROM Group");
            List<Integer> groups = query.list();
            return groups.size();
        } catch (Exception e) {
            log.error("Some fails with finding groups", e);
            return null;
        }
    }
    public void saveUserToGroup(Group group){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(group);
        transaction.commit();
        session.close();
    }

}
