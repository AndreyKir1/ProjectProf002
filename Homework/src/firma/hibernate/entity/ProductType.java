package firma.hibernate.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "PRODUCT_TYPE")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TYPE_CODE")
    private String typeCode;

    @Column(name = "TYPE_NAME")
    private String typeName;

    @OneToMany(mappedBy = "productType", fetch = FetchType.LAZY)
    private Set<Product> products;

    public ProductType() {
    }

    public ProductType(String typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return typeName;
    }
}
