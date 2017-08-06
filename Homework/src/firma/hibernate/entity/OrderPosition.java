package firma.hibernate.entity;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_POSITION")
public class OrderPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "POSITION_NAME")
    private String positionName;

    @Column(name = "TOTAL_PRICE_OF_PRODUCT")
    private Double totalPriceOfProduct;

    @Column(name = "PRODUCT_AMOUNT")
    private Integer productAmount;

    private Order order;

    private Product product;
}
