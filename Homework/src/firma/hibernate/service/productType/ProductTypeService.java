package firma.hibernate.service.productType;

import firma.hibernate.entity.ProductType;

import java.util.List;

public interface ProductTypeService {
    void create(ProductType productType);
    ProductType read(Long id);
    boolean update(ProductType productType);
    boolean delete(ProductType productType);
    List<ProductType> getAll();
}
