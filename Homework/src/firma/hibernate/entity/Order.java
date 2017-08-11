package firma.hibernate.entity;

import firma.support.OrderStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    private EmployeeFirm salesManager;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private EmployeeFirm storageManager;

    @ManyToMany()
    @JoinTable(name = "jnd_orders_employee", joinColumns = @JoinColumn(name = "orders_fk"), inverseJoinColumns = @JoinColumn(name = "managers_fk"))
    private Set<EmployeeFirm> managers;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<OrderPosition> orderPositions;

    public Order() {
    }

    public Order(String number, Date createOrder, OrderStatus orderConditions, String noteAboutOrder, Set<EmployeeFirm> managers, Client client) {
        this.number = number;
        this.createOrder = createOrder;
        this.orderConditions = orderConditions;
        this.noteAboutOrder = noteAboutOrder;
        this.managers = managers;
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

//    public EmployeeFirm getSalesManager() {
//        return salesManager;
//    }
//
//    public void setSalesManager(EmployeeFirm salesManager) {
//        this.salesManager = salesManager;
//    }
//
//    public EmployeeFirm getStorageManager() {
//        return storageManager;
//    }
//
//    public void setStorageManager(EmployeeFirm storageManager) {
//        this.storageManager = storageManager;
//    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<OrderPosition> getOrderPositions() {
        return orderPositions;
    }

    public void setOrderPositions(Set<OrderPosition> orderPositions) {
        this.orderPositions = orderPositions;
    }

    public Set<EmployeeFirm> getManagers() {
        return managers;
    }

    public void setManagers(Set<EmployeeFirm> managers) {
        this.managers = managers;
    }
}
