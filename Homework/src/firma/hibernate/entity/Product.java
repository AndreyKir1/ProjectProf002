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

    private ProductType productType;
}
