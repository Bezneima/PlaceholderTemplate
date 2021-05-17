package PlaceholderTemplate.Dao;

import PlaceholderTemplate.HibernateSessionFactoryUtil;
import PlaceholderTemplate.dto.DocFiles;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

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

    public String getFileInputFields(String fileMd5Hash) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from DocFiles where fileHashName = '" + fileMd5Hash + "'");
            List<DocFiles> docFile = query.list();
            if (docFile.size() > 0)
                return docFile.get(0).getInputFieldsNames();
            else
                return "[]";
        } catch (Exception e) {
            log.error("Some fails with finding docFile with fileMd5Hash = {}", fileMd5Hash, e);
            return null;
        }
    }

    public String getFileName(String fileMd5Hash) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from DocFiles where fileHashName = '" + fileMd5Hash + "'");
            List<DocFiles> docFile = query.list();
            if (docFile.size() > 0)
                return docFile.get(0).getFileName();
            else
                return "";
        } catch (Exception e) {
            log.error("Failed work getFileName = {}", fileMd5Hash, e);
            return null;
        }
    }

    public List<DocFiles> getAllFileLinksByGroupId(Integer groupId) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from DocFiles where groupId = '" + groupId + "'");
            List<DocFiles> docFiles = query.list();
            return docFiles;
        } catch (Exception e) {
            log.error("Failed work getAllFileLinksByGroupId = {}", groupId, e);
            return null;
        }
    }

    public String deleteFile(DocFiles deletedFile) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createNativeQuery("DELETE from doc_files where file_hash_name = '" + deletedFile.getFileHashName() + "'");
            Transaction transaction = session.beginTransaction();
            int result = query.executeUpdate();
            if (result > 0) {
                System.out.println("remove file where fileHashName=" + deletedFile.getFileHashName());
            }
            transaction.commit();
            return "[]";
        } catch (Exception e) {
            //log.error("Some fails with finding docFile with fileMd5Hash = {}", fileMd5Hash, e);
            return null;
        }
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
