package firma.hibernate.dao.product;

import firma.hibernate.entity.Product;
import firma.hibernate.entity.ProductType;
import firma.support.OrderStatus;

import java.util.List;

public interface ProductDAO {
    Long create(Product product);
    Product read(Long id);
    boolean update(Product product);
    boolean delete(Product product);
    List<Product> getAll();
    List<Product> getByProductType(ProductType productType);
}
