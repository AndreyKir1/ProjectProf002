package firma.hibernate.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "CUSTOMERS")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumder;

    @Column(name = "EMAIL")
    private String email;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<Order> orders;

    public Client() {
    }

    public Client(String surname, String name, String lastName, String phoneNumder, String email) {
        this.surname = surname;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumder = phoneNumder;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumder() {
        return phoneNumder;
    }

    public void setPhoneNumder(String phoneNumder) {
        this.phoneNumder = phoneNumder;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
