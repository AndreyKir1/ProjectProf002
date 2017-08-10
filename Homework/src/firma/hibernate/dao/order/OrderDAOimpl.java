package firma.hibernate.dao.order;

import firma.hibernate.entity.Order;
import org.hibernate.HibernateException;
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
}
