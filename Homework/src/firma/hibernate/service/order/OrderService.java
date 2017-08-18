package firma.hibernate.service.order;


import firma.hibernate.entity.Client;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.entity.Order;
import firma.support.EmployeeRols;
import firma.support.OrderStatus;

import java.util.List;

public interface OrderService {
    void create(Order order);
    Order read(Long id);
    boolean update(Order order);
    boolean delete(Order order);
    List<Order> getAll();
    List<Order> getOrdersByEmployee(EmployeeFirm employee, OrderStatus orderStatus);
    List<Order> getOrdersByClient(Client client);
    List<Order> getByStatus(OrderStatus orderStatus);
    List<Order> getOrdersWithoutCashier();
    List<Order> getOrdersWithoutStorageManager();

}
