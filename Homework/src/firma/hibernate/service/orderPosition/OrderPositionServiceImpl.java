package firma.hibernate.service.orderPosition;

import firma.hibernate.dao.orderPosition.OrderPositionDAO;
import firma.hibernate.entity.OrderPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPositionServiceImpl implements OrderPositionService{
    @Autowired
    private OrderPositionDAO dao;

    @Override
    public void create(OrderPosition orderPosition) {
        if (orderPosition != null) {
            orderPosition.setId(dao.create(orderPosition));
        }
    }

    @Override
    public OrderPosition read(Long id) {
        if (dao.read(id) != null) {
            return dao.read(id);
        } else {
            return null;
        }
    }

    @Override
    public boolean update(OrderPosition update) {
        if (update != null) {
            return dao.update(update);
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(OrderPosition delete) {
        if (delete != null) {
            return dao.delete(delete);
        } else {
            return false;
        }
    }

    @Override
    public List<OrderPosition> getAll() {
        return dao.getAll();
    }
}
