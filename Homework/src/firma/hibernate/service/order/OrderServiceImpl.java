package firma.hibernate.service.order;

import firma.hibernate.dao.order.OrderDAO;
import firma.hibernate.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDAO dao;

    @Override
    public void create(Order order) {
        if (order != null) {
            order.setId(dao.create(order));
        }
    }

    @Override
    public Order read(Long id) {
        if (dao.read(id) != null) {
            return dao.read(id);
        } else {
            return null;
        }
    }

    @Override
    public boolean update(Order update) {
        if (update != null) {
            return dao.update(update);
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Order delete) {
        if (delete != null) {
            return dao.delete(delete);
        } else {
            return false;
        }
    }

    @Override
    public List<Order> getAll() {
        return dao.getAll();
    }
}
