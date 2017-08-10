package firma.hibernate.entity;

import firma.support.OrderStatus;

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
    @Column (name = "CREATE_DATE")
    private Date createOrder;

    @Temporal(TemporalType.DATE)
    @Column (name = "READY_DATE")
    private Date orderReady;

    @Enumerated(EnumType.ORDINAL)
    @Column (name = "ORDER_CONDITIONS")
    private OrderStatus orderConditions;

    @Column(name = "NOTE_ABOUT_ORDER")
    private String noteAboutOrder;

    private AccountEmployee salesManager;

    private AccountEmployee storageManager;

    private Client client;

    public Order() {
    }

    public Order(String number, Double totalPrice, Date createOrder, Date orderReady, OrderStatus orderConditions, String noteAboutOrder, AccountEmployee salesManager, AccountEmployee storageManager, Client client) {
        this.number = number;
        this.totalPrice = totalPrice;
        this.createOrder = createOrder;
        this.orderReady = orderReady;
        this.orderConditions = orderConditions;
        this.noteAboutOrder = noteAboutOrder;
        this.salesManager = salesManager;
        this.storageManager = storageManager;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreateOrder() {
        return createOrder;
    }

    public void setCreateOrder(Date createOrder) {
        this.createOrder = createOrder;
    }

    public Date getOrderReady() {
        return orderReady;
    }

    public void setOrderReady(Date orderReady) {
        this.orderReady = orderReady;
    }

    public OrderStatus getOrderConditions() {
        return orderConditions;
    }

    public void setOrderConditions(OrderStatus orderConditions) {
        this.orderConditions = orderConditions;
    }

    public String getNoteAboutOrder() {
        return noteAboutOrder;
    }

    public void setNoteAboutOrder(String noteAboutOrder) {
        this.noteAboutOrder = noteAboutOrder;
    }

    public AccountEmployee getSalesManager() {
        return salesManager;
    }

    public void setSalesManager(AccountEmployee salesManager) {
        this.salesManager = salesManager;
    }

    public AccountEmployee getStorageManager() {
        return storageManager;
    }

    public void setStorageManager(AccountEmployee storageManager) {
        this.storageManager = storageManager;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
