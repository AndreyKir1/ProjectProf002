package firma.hibernate.dao.productType;

import firma.hibernate.entity.ProductType;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProductTypeDAOimpl implements ProductTypeDAO {
    @Autowired
    private SessionFactory factory;

    @Override
    @Transactional
    public Long create(ProductType productType) {
        try {
            Long id = (Long) factory.getCurrentSession().save(productType);
            return id;
        } catch (HibernateException e) {
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return null;
        }
    }

    @Override
    @Transactional
    public ProductType read(Long id) {
        return factory.getCurrentSession().get(ProductType.class, id);
    }

    @Override
    @Transactional
    public boolean update(ProductType productType) {
        try {
            factory.getCurrentSession().saveOrUpdate(productType);
            return true;
        } catch (HibernateException e){
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(ProductType productType) {
        try {
            factory.getCurrentSession().delete(productType);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return false;
        }
    }

    @Override
    @Transactional
    public List<ProductType> getAll() {
        return factory.getCurrentSession().createQuery("FROM firma.hibernate.entity.ProductType").list();
    }
}
