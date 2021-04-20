package PlaceholderTemplate.Dao;

import PlaceholderTemplate.HibernateSessionFactoryUtil;
import PlaceholderTemplate.dto.Group;
import PlaceholderTemplate.dto.User;
import org.hibernate.Session;
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

}
