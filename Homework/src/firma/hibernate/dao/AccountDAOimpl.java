package firma.hibernate.dao;

import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AccountDAOimpl implements AccountDAO{

    @Autowired
    private SessionFactory factory;

    @Override
    @Transactional
    public Long create(AccountEmployee account) {
        try {
            Long id = (Long) factory.getCurrentSession().save(account);
            return id;
        }catch (HibernateException e){
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return null;
        }

        /*Session session = factory.openSession();
        session.beginTransaction();
        try{
            Long id = (Long)session.save(account);
            session.getTransaction().commit();
            return id;
        }catch (HibernateException e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }finally {
            session.close();
        }*/
    }

    @Override
    @Transactional
    public AccountEmployee read(Long id) {
        return factory.getCurrentSession().get(AccountEmployee.class, id);

        /*Session session = factory.openSession();
        session.beginTransaction();
        AccountEmployee account = session.get(AccountEmployee.class, id);
        return account;*/
    }

    @Override
    @Transactional
    public boolean update(AccountEmployee account) {
        try {
            factory.getCurrentSession().saveOrUpdate(account);
            return true;
        } catch (HibernateException e){
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return false;
        }

        /*Session session = factory.openSession();
        session.beginTransaction();
        try{
            Query query = session.createQuery("update AccountEmployee set login = :login, " +
                    "password = :password where id =:id");
            query.setParameter("login", account.getLogin());
            query.setParameter("password", account.getPassword());
            query.setParameter("id", account.getId());
            query.executeUpdate();
            session.getTransaction().commit();
            return true;
        }catch (HibernateException e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }finally {
            session.close();
        }*/
    }

    @Override
    @Transactional
    public boolean delete(AccountEmployee account) {
        try {
            factory.getCurrentSession().delete(account);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return false;
        }

        /*Session session = factory.openSession();
        session.beginTransaction();
        try{
            Query query = session.createQuery("delete from AccountEmployee  where id = :id");
            query.setParameter("id", account.getId());
            query.executeUpdate();
            session.getTransaction().commit();
            return true;
        }catch (HibernateException e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }finally {
            session.close();
        }*/
    }

    @Override
    @Transactional
    public List<AccountEmployee> getAll() {
        return factory.getCurrentSession().createQuery("FROM firma.hibernate.entity.AccountEmployee").list();

        /*Session session = factory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from AccountEmployee ");
        List<AccountEmployee> list = query.list();
        session.close();
        return list;*/
    }
}
