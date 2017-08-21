package firma.hibernate.service.order;

import firma.hibernate.dao.order.OrderDAO;
import firma.hibernate.entity.Client;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.entity.Order;
import firma.support.EmployeeRols;
import firma.support.OrderStatus;
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

    @Override
    public List<Order> getOrdersByEmployee(EmployeeFirm employee, OrderStatus orderStatus) {
        if (employee != null) return dao.getOrdersByEmployee(employee, orderStatus);
        else return null;
    }

    @Override
    public List<Order> getOrdersByClient(Client client) {
        if (client != null) return dao.getOrdersByClient(client);
        else return null;
    }

    @Override
    public List<Order> getByStatus(OrderStatus orderStatus) {
        if (orderStatus != null) return dao.getByStatus(orderStatus);
        else return null;
    }

    @Override
    public List<Order> getOrdersWithoutCashier() {
        return dao.getOrdersWithoutCashier();
    }

    @Override
    public List<Order> getOrdersWithoutStorageManager() {
        return dao.getOrdersWithoutStorageManager();
    }

    @Override
    public List<Order> getNewOrdersStorManager() {
        return dao.getNewOrdersStorManager();
    }

    @Override
    public List<Order> getNewOrdersCashier() {
        return dao.getNewOrdersCashier();
    }
}
