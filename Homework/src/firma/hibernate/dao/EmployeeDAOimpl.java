package firma.hibernate.dao;

import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class EmployeeDAOimpl implements EmployeeDAO {
    private SessionFactory factory = HibernateUtil.getFactory();

    @Override
    public Long create(EmployeeFirm employee) {
        Session session = factory.openSession();
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
        }
    }

    @Override
    public EmployeeFirm read(Long id) {
        Session session = factory.openSession();
        session.beginTransaction();
        EmployeeFirm employee = session.get(EmployeeFirm.class, id);
        return employee;
    }

    @Override
    public boolean update(EmployeeFirm employee) {
        Session session = factory.openSession();
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
            query.setParameter("account", employee.getAccountEmployee());
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
        }
    }

    @Override
    public boolean delete(EmployeeFirm employee) {
        Session session = factory.openSession();
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
        }
    }

    @Override
    public List<EmployeeFirm> getAll() {
        Session session = factory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from EmployeeFirm");
        List<EmployeeFirm> list = query.list();
        session.close();
        return list;
    }
}
