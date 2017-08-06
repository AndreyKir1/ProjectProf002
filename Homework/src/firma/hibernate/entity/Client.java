package firma.hibernate.entity;

import javax.persistence.*;

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

}
