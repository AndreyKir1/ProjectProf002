package firma.hibernate.dao.order;

import firma.hibernate.entity.Client;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.entity.Order;
import firma.support.OrderStatus;

import java.util.List;

public interface OrderDAO {
    Long create(Order order);
    Order read(Long id);
    boolean update(Order order);
    boolean delete(Order order);
    List<Order> getAll();
    List<Order> getOrdersByEmployee(EmployeeFirm employee);
    List<Order> getOrdersByClient(Client client);
    List<Order> getByStatus(OrderStatus orderStatus);
}
