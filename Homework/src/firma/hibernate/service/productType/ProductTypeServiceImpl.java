package firma.hibernate.service.productType;

import firma.hibernate.dao.productType.ProductTypeDAO;
import firma.hibernate.entity.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService{
    @Autowired
    private ProductTypeDAO dao;

    @Override
    public void create(ProductType productType) {
        if (productType != null) {
            productType.setId(dao.create(productType));
        }
    }

    @Override
    public ProductType read(Long id) {
        if (dao.read(id) != null) {
            return dao.read(id);
        } else {
            return null;
        }
    }

    @Override
    public boolean update(ProductType update) {
        if (update != null) {
            return dao.update(update);
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(ProductType delete) {
        if (delete != null) {
            return dao.delete(delete);
        } else {
            return false;
        }
    }

    @Override
    public List<ProductType> getAll() {
        return dao.getAll();
    }
}
