package firma.hibernate.service.product;


import firma.hibernate.entity.Product;
import firma.hibernate.entity.ProductType;
import firma.support.OrderStatus;

import java.util.List;

public interface ProductService {
    void create(Product product);
    Product read(Long id);
    boolean update(Product product);
    boolean delete(Product product);
    List<Product> getAll();
    List<Product> getByProductType(ProductType productType);
}
