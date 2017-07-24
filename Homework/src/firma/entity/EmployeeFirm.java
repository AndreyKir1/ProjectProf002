package firma.entity;

import firma.support.EmployeeRols;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "EMPLOYEES")
public class EmployeeFirm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "SURNAME")
    private String surname;
    @Column(name = "NAME")
    private String name;
    @Column(name = "LASTNAME")
    private String lastName;
    @Column(name = "AGE")
    private Integer age;
    @Temporal(TemporalType.DATE)
    private Date bitrhDay;
    @Temporal(TemporalType.DATE)
    private Date dateOfStarWorking;
    @Enumerated(EnumType.ORDINAL)
    private EmployeeRols employeeRols;
    @Column(name = "ACCOUNT")
    private boolean account;
    @OneToOne
    private AccountEmployee accountEmployee;

    public EmployeeFirm() {
    }

    public EmployeeFirm(String surname, String name, String lastName, Integer age, Date bitrhDay,
                        Date dateOfStarWorking, EmployeeRols employeeRols, AccountEmployee accountEmployee) {
        this.surname = surname;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.bitrhDay = bitrhDay;
        this.dateOfStarWorking = dateOfStarWorking;
        this.employeeRols = employeeRols;
        this.account = false;
        this.accountEmployee = accountEmployee;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBitrhDay() {
        return bitrhDay;
    }

    public void setBitrhDay(Date bitrhDay) {
        this.bitrhDay = bitrhDay;
    }

    public Date getDateOfStarWorking() {
        return dateOfStarWorking;
    }

    public void setDateOfStarWorking(Date dateOfStarWorking) {
        this.dateOfStarWorking = dateOfStarWorking;
    }

    public EmployeeRols getEmployeeRols() {
        return employeeRols;
    }

    public void setEmployeeRols(EmployeeRols employeeRols) {
        this.employeeRols = employeeRols;
    }

    public boolean isAccount() {
        return account;
    }

    public void setAccount(boolean account) {
        this.account = account;
    }

    public AccountEmployee getAccountEmployee() {
        return accountEmployee;
    }

    public void setAccountEmployee(AccountEmployee accountEmployee) {
        this.accountEmployee = accountEmployee;
    }
}
