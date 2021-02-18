package PlaceholderTemplate.Dao;

import PlaceholderTemplate.HibernateSessionFactoryUtil;
import PlaceholderTemplate.dto.TemplateFiles;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TemplateFilesDao {
    private static final Logger log = LoggerFactory.getLogger(TemplateFilesDao.class);

    public TemplateFilesDao findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(TemplateFilesDao.class, id);
    }

    public void save(TemplateFiles templateFiles) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(templateFiles);
        transaction.commit();
        session.close();
    }
}
