package PlaceholderTemplate.Dao;

import PlaceholderTemplate.HibernateSessionFactoryUtil;
import PlaceholderTemplate.dto.DocFiles;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DocFilesDao {
    private static final Logger log = LoggerFactory.getLogger(DocFilesDao.class);

    public DocFilesDao findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(DocFilesDao.class, id);
    }

    public void save(DocFiles DocFiles) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(DocFiles);
        transaction.commit();
        session.close();
    }

    /*
    public String getAllInputFieldsNamesFromFile(String fileName){
        String result = "";
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from User where userName = '" + name + "'");
            List users = query.list();
            return (User) users.get(0);
        } catch (Exception e) {
            log.error("Some fails with finding user with userName = {}", name, e);
            return null;
        }
        return result;
    }

     */
}
