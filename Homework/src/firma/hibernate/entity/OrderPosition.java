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

    @Column(name = "POSITION_CODE")
    private String positionCode;

    @Column(name = "TOTAL_PRICE_OF_PRODUCT")
    private Double totalPriceOfProduct;

    @Column(name = "PRODUCT_AMOUNT")
    private Integer productAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @OneToOne
    private Product product;

    public OrderPosition() {
    }

    public OrderPosition(Integer productAmount, Product product) {
        this.productAmount = productAmount;
        this.product = product;
        this.positionName = product.getProductName();
        this.positionCode = product.getProductCode();
        this.totalPriceOfProduct = productAmount*product.getProductPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Double getTotalPriceOfProduct() {
        return totalPriceOfProduct;
    }

    public void setTotalPriceOfProduct(Double totalPriceOfProduct) {
        this.totalPriceOfProduct = totalPriceOfProduct;
    }

    public Integer getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }
}
