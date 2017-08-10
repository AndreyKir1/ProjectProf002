package firma.hibernate.dao.orderPosition;

import firma.hibernate.entity.OrderPosition;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OrderPositionDAOimpl implements OrderPositionDAO{
    @Autowired
    private SessionFactory factory;

    @Override
    @Transactional
    public Long create(OrderPosition orderPosition) {
        try {
            Long id = (Long) factory.getCurrentSession().save(orderPosition);
            return id;
        }catch (HibernateException e){
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return null;
        }
    }

    @Override
    @Transactional
    public OrderPosition read(Long id) {
        return factory.getCurrentSession().get(OrderPosition.class, id);
    }

    @Override
    @Transactional
    public boolean update(OrderPosition orderPosition) {
        try {
            factory.getCurrentSession().saveOrUpdate(orderPosition);
            return true;
        } catch (HibernateException e){
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(OrderPosition orderPosition) {
        try {
            factory.getCurrentSession().delete(orderPosition);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return false;
        }
    }

    @Override
    @Transactional
    public List<OrderPosition> getAll() {
        return factory.getCurrentSession().createQuery("FROM firma.hibernate.entity.OrderPosition").list();
    }
}
