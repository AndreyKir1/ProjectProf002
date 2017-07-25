package firma.hibernate.dao;

import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class AccountDAOimpl implements AccountDAO{
    private SessionFactory factory = HibernateUtil.getFactory();

    @Override
    public Long create(AccountEmployee account) {
        Session session = factory.openSession();
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
        }
    }

    @Override
    public AccountEmployee read(Long id) {
        Session session = factory.openSession();
        session.beginTransaction();
        AccountEmployee account = session.get(AccountEmployee.class, id);
        return account;
    }

    @Override
    public boolean update(AccountEmployee account) {
        Session session = factory.openSession();
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
        }
    }

    @Override
    public boolean delete(AccountEmployee account) {
        Session session = factory.openSession();
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
        }
    }

    @Override
    public List<AccountEmployee> getAll() {
        Session session = factory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from AccountEmployee ");
        List<AccountEmployee> list = query.list();
        session.close();
        return list;
    }
}
