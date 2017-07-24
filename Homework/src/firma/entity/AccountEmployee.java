package firma.entity;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNTS")
public class AccountEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "LOGIN")
    private String login;
    @Column(name = "PASSWORD")
    private String password;

    public AccountEmployee() {
    }

    public AccountEmployee(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
