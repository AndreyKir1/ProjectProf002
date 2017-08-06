package firma.hibernate.entity;

import firma.support.OrderConditions;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ALL_ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ORDER_NUMBER")
    private String number;

    @Column(name = "TOTAL_PRICE")
    private Double totalPrice;

    @Temporal(TemporalType.DATE)
    private Date createOrder;

    @Temporal(TemporalType.DATE)
    private Date orderReady;

    @Enumerated(EnumType.ORDINAL)
    private OrderConditions orderConditions;

    @Column(name = "NOTE_ABOUT_ORDER")
    private String noteAboutOrder;

    private AccountEmployee salesManager;

    private AccountEmployee storegeManager;

    private Client client;
}
