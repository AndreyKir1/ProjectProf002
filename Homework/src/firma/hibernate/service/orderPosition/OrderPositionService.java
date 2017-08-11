package firma.hibernate.service.orderPosition;

import firma.hibernate.entity.Order;
import firma.hibernate.entity.OrderPosition;

import java.util.List;

public interface OrderPositionService {
    void create(OrderPosition orderPosition);
    OrderPosition read(Long id);
    boolean update(OrderPosition orderPosition);
    boolean delete(OrderPosition orderPosition);
    List<OrderPosition> getAll();
    List<OrderPosition> getOrderPositionByOrder(Order order);

}
