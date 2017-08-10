package firma.hibernate.dao.orderPosition;

import firma.hibernate.entity.OrderPosition;

import java.util.List;

public interface OrderPositionDAO {
    Long create(OrderPosition orderPosition);
    OrderPosition read(Long id);
    boolean update(OrderPosition orderPosition);
    boolean delete(OrderPosition orderPosition);
    List<OrderPosition> getAll();
}
