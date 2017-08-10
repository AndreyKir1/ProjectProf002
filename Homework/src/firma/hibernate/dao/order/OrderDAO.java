package firma.hibernate.dao.order;

import firma.hibernate.entity.Order;

import java.util.List;

public interface OrderDAO {
    Long create(Order order);
    Order read(Long id);
    boolean update(Order order);
    boolean delete(Order order);
    List<Order> getAll();
}
