package firma.hibernate.dao.client;

import firma.hibernate.entity.Client;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ClientDAOimpl implements ClientDAO {
    @Autowired
    private SessionFactory factory;

    @Override
    @Transactional
    public Long create(Client client) {
        try {
            Long id = (Long) factory.getCurrentSession().save(client);
            return id;
        } catch (HibernateException e) {
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return null;
        }
    }

    @Override
    @Transactional
    public Client read(Long id) {
        return factory.getCurrentSession().get(Client.class, id);
    }

    @Override
    @Transactional
    public boolean update(Client client) {
        try {
            factory.getCurrentSession().saveOrUpdate(client);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(Client client) {
        try {
            factory.getCurrentSession().delete(client);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            return false;
        }
    }

    @Override
    @Transactional
    public List<Client> getAll() {
        return factory.getCurrentSession().createQuery("FROM firma.hibernate.entity.Client").list();
    }
}

