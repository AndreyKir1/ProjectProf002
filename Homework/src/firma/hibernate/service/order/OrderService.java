package firma.hibernate.service.order;


import firma.hibernate.entity.Order;

import java.util.List;

public interface OrderService {
    void create(Order order);
    Order read(Long id);
    boolean update(Order order);
    boolean delete(Order order);
    List<Order> getAll();
}
