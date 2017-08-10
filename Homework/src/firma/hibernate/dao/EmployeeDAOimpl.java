package firma.hibernate.dao;

import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
public class EmployeeDAOimpl implements EmployeeDAO {

    @Autowired
    private SessionFactory factory;
    /*private SessionFactory factory = HibernateUtil.getFactory();*/

    @Override
    @Transactional
    public Long create(EmployeeFirm employee) {
        try {
            Long id = (Long) factory.getCurrentSession().save(employee);
            return id;
        }catch (HibernateException e){
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return null;
        }

        /*Session session = factory.openSession();
        session.beginTransaction();
        try {
            Long id = (Long) session.save(employee);
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
    public EmployeeFirm read(Long id) {
        return factory.getCurrentSession().get(EmployeeFirm.class, id);

        /*Session session = factory.openSession();
        session.beginTransaction();
        EmployeeFirm employee = session.get(EmployeeFirm.class, id);
        return employee;*/
    }

    @Override
    @Transactional
    public EmployeeFirm readByAccount(AccountEmployee account) {

        Session session = factory.getCurrentSession();
        session.beginTransaction();
        try {
            Query query = session.createQuery("from EmployeeFirm where accountEmployee =:accountEmployee");
            query.setParameter("accountEmployee", account);
            EmployeeFirm employee = (EmployeeFirm) query.list().get(0);
            return employee;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public boolean update(EmployeeFirm employee) {
        try {
            factory.getCurrentSession().saveOrUpdate(employee);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return false;
        }

        /*Session session = factory.openSession();
        session.beginTransaction();
        try {
            Query query = session.createQuery("update EmployeeFirm set surname =:surname," +
                    "name = :name, lastName = :lastName, age = :age, bitrhDay = :bitrhDay," +
                    "dateOfStarWorking = :dateOfStarWorking, employeeRols = :employeeRols," +
                    "account = :account where id = :id");
            query.setParameter("surname", employee.getSurname());
            query.setParameter("name", employee.getName());
            query.setParameter("lastName", employee.getLastName());
            query.setParameter("age", employee.getAge());
            query.setParameter("bitrhDay", employee.getBitrhDay());
            query.setParameter("dateOfStarWorking", employee.getDateOfStarWorking());
            query.setParameter("employeeRols", employee.getEmployeeRols());
            query.setParameter("account", employee.isAccount());
            query.setParameter("id", employee.getId());
            query.executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }*/
    }

    @Override
    @Transactional
    public boolean delete(EmployeeFirm employee) {
        try {
            factory.getCurrentSession().delete(employee);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return false;
        }

        /*Session session = factory.openSession();
        session.beginTransaction();
        try {
            Query query = session.createQuery("delete from EmployeeFirm where id = :id");
            query.setParameter("id", employee.getId());
            query.executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }*/
    }

    @Override
    @Transactional
    public List<EmployeeFirm> getAll() {
        return factory.getCurrentSession().createQuery("FROM firma.hibernate.entity.EmployeeFirm").list();

        /*Session session = factory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from EmployeeFirm");
        List<EmployeeFirm> list = query.list();
        session.close();
        return list;*/
    }
}
