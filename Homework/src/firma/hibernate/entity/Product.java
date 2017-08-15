package firma.hibernate.entity;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_ITEM")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRODUCT_PRICE")
    private Double productPrice;

    @Column(name = "AMOUNT_IN_STORAGE")
    private Integer amountInStorage;

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductType productType;

    public Product() {
    }


    public Product(String productCode, String productName, Double productPrice, Integer amountInStorage, ProductType productType) {
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.amountInStorage = amountInStorage;
        this.productType = productType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getAmountInStorage() {
        return amountInStorage;
    }

    public void setAmountInStorage(Integer amountInStorage) {
        this.amountInStorage = amountInStorage;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
