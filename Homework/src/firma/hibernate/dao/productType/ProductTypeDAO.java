package firma.hibernate.dao.productType;

import firma.hibernate.entity.ProductType;

import java.util.List;

public interface ProductTypeDAO {
    Long create(ProductType productType);
    ProductType read(Long id);
    boolean update(ProductType productType);
    boolean delete(ProductType productType);
    List<ProductType> getAll();
}
