package firma.hibernate.dao.product;

import firma.hibernate.entity.Product;
import firma.hibernate.entity.ProductType;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProductDAOimpl implements ProductDAO {
    @Autowired
    private SessionFactory factory;

    @Override
    @Transactional
    public Long create(Product product) {
        try {
            Long id = (Long) factory.getCurrentSession().save(product);
            return id;
        } catch (HibernateException e) {
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return null;
        }
    }

    @Override
    @Transactional
    public Product read(Long id) {
        return factory.getCurrentSession().get(Product.class, id);
    }

    @Override
    @Transactional
    public boolean update(Product product) {
        try {
            factory.getCurrentSession().saveOrUpdate(product);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(Product product) {
        try {
            factory.getCurrentSession().delete(product);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return false;
        }
    }

    @Override
    @Transactional
    public List<Product> getAll() {
        return factory.getCurrentSession().createQuery("FROM firma.hibernate.entity.Product").list();
    }

    @Override
    @Transactional
    public List<Product> getByProductType(ProductType prodType) {
        Session session = factory.getCurrentSession();
        try {
            Query query = session.createQuery("from Product where productType =:productType");
            query.setParameter("productType", prodType);
            List<Product> list = query.list();
            return list;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }
}
