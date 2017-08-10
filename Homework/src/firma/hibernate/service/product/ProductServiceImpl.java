package firma.hibernate.service.product;

import firma.hibernate.dao.product.ProductDAO;
import firma.hibernate.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDAO dao;

    @Override
    public void create(Product product) {
        if (product != null) {
            product.setId(dao.create(product));
        }
    }

    @Override
    public Product read(Long id) {
        if (dao.read(id) != null) {
            return dao.read(id);
        } else {
            return null;
        }
    }

    @Override
    public boolean update(Product update) {
        if (update != null) {
            return dao.update(update);
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Product delete) {
        if (delete != null) {
            return dao.delete(delete);
        } else {
            return false;
        }
    }

    @Override
    public List<Product> getAll() {
        return dao.getAll();
    }
}
