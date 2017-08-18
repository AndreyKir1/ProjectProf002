package firma.hibernate.dao.order;

import firma.hibernate.entity.Client;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.entity.Order;
import firma.support.EmployeeRols;
import firma.support.OrderStatus;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OrderDAOimpl implements OrderDAO {
    @Autowired
    private SessionFactory factory;

    @Override
    @Transactional
    public Long create(Order order) {
        try {
            Long id = (Long) factory.getCurrentSession().save(order);
            return id;
        }catch (HibernateException e){
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return null;
        }
    }

    @Override
    @Transactional
    public Order read(Long id) {
        return factory.getCurrentSession().get(Order.class, id);
    }

    @Override
    @Transactional
    public boolean update(Order order) {
        try {
            factory.getCurrentSession().saveOrUpdate(order);
            return true;
        } catch (HibernateException e){
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(Order order) {
        try {
            factory.getCurrentSession().delete(order);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return false;
        }
    }

    @Override
    @Transactional
    public List<Order> getAll() {
        return factory.getCurrentSession().createQuery("FROM firma.hibernate.entity.Order").list();
    }

    @Override
    @Transactional
    public List<Order> getOrdersByEmployee(EmployeeFirm employee, OrderStatus orderStatus) {
        Session session = factory.getCurrentSession();
        try {
//            Query query = session.createQuery("select distinct o from Order o join o.managers as employee where employee=:employee");
            Query query = session.createQuery("select distinct o from Order o join o.managers as employee where employee=:employee and " +
                    "o.orderConditions=:conditions");
            query.setParameter("conditions", orderStatus);
            query.setParameter("employee", employee);
            List<Order> list = query.list();
            return list;
        }catch (HibernateException e){
            e.printStackTrace();
            return null;
        }
    }

    //
    @Override
    @Transactional
    public List<Order> getOrdersWithoutCashier() {
        Session session = factory.getCurrentSession();
        try {
            Query query = session.createQuery("from Order where isCashier =:isCmanager");
            query.setParameter("isCmanager", false);
            List<Order> list = query.list();
            return list;
        }catch (HibernateException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public List<Order> getOrdersWithoutStorageManager() {
        Session session = factory.getCurrentSession();
        try {
            Query query = session.createQuery("from Order where isStorageManager =:isSmanager");
            query.setParameter("isSmanager", false);
            List<Order> list = query.list();
            return list;
        }catch (HibernateException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public List<Order> getOrdersByClient(Client client) {
        Session session = factory.getCurrentSession();
        try {
            Query query = session.createQuery("from Order where client =:client");
            query.setParameter("client", client);
            List<Order> list = query.list();
            return list;
        }catch (HibernateException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public List<Order> getByStatus(OrderStatus orderStatus) {
        Session session = factory.getCurrentSession();
        try {
            Query query = session.createQuery("from Order where orderConditions =:conditions");
            query.setParameter("conditions", orderStatus);
            List<Order> list = query.list();
            return list;
        }catch (HibernateException e){
            e.printStackTrace();
            return null;
        }
    }
}
