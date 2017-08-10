package firma.fx.controllers;

import firma.fx.controllers.admin.ViewController;
import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.service.account.AccountService;
import firma.hibernate.service.account.AccountServiceImpl;
import firma.hibernate.service.employee.EmployeeService;
import firma.hibernate.service.employee.EmployeeServiceImpl;
import firma.hibernate.util.HibernateUtil;
import firma.support.EmployeeRols;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class LoginController {

    @FXML
    private List<EmployeeFirm> listEmployee;

    @FXML
    private List<AccountEmployee> listAccount;

    @FXML
    private Button btnCancel;

    @FXML
    private Label lbPassword;

    @FXML
    private CustomPasswordField fldPassword;

    @FXML
    private Button btnOK;

    @FXML
    private Label lbLlogin;

    @FXML
    private CustomTextField fldLogin;

    @FXML
    private void initialize() throws ParseException {
        //TODO
        //testDataEmployee();
        fldPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldPassword.setEffect(null);
        });

        fldLogin.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldLogin.setEffect(null);
        });

        setClearInCPF(fldPassword);
        setClearInCTF(fldLogin);
    }

    @FXML
    private void setClearInCTF(CustomTextField ctf) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, ctf, ctf.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setClearInCPF(CustomPasswordField cpf) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, cpf, cpf.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        HibernateUtil.getFactory().close();
    }

    @FXML
    private void pressOK() {
        btnOK.requestFocus();
        EmployeeService employeeService = new EmployeeServiceImpl();
        AccountService accountService = new AccountServiceImpl();

        listEmployee = employeeService.getAll();
        listAccount = accountService.getAll();

        for (AccountEmployee el : listAccount) {
            if (fldLogin.getText().equals(el.getLogin()) && fldPassword.getText().equals(el.getPassword())) {
                if (employeeService.readByAccount(el).getEmployeeRols().equals(EmployeeRols.ADMINISTRATOR)) {
                    try {
                        EmployeeFirm currentEmployee = employeeService.readByAccount(el);
                        Stage stage = new Stage();
                        stage.setTitle("Вітаємо Вас " + currentEmployee.getSurname() + " " + currentEmployee.getName() + " " + currentEmployee.getLastName()
                                + " . Ви адміністратор системи");
                        stage.setMinHeight(600);
                        stage.setMinWidth(800);
                        Parent root = FXMLLoader.load(getClass().getResource("/firma/view/admin/AdminWindow.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        ViewController.setStageAdminWindow(stage);
                        stage.show();
                        Stage curentStage = (Stage) btnOK.getScene().getWindow();
                        curentStage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (employeeService.readByAccount(el).getEmployeeRols().equals(EmployeeRols.SALES_MANAGER)) {
                    try {
                        EmployeeFirm currentEmployee = employeeService.readByAccount(el);
                        Stage stage = new Stage();
                        stage.setTitle("Вітаємо Вас " + currentEmployee.getSurname() + " " + currentEmployee.getName() + " " + currentEmployee.getLastName()
                                + " . Ваш рівень доступу - менеджер");
                        Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/ManagerWindow.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        ViewController.setStageAdminWindow(stage);
                        stage.show();
                        Stage curentStage = (Stage) btnOK.getScene().getWindow();
                        curentStage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (employeeService.readByAccount(el).getEmployeeRols().equals(EmployeeRols.STORAGE_MANAGER)) {
                    try {
                        EmployeeFirm currentEmployee = employeeService.readByAccount(el);
                        Stage stage = new Stage();
                        stage.setTitle("Вітаємо Вас " + currentEmployee.getSurname() + " " + currentEmployee.getName() + " " + currentEmployee.getLastName()
                                + " . Ваш рівень доступу - кладовщик");
                        Parent root = FXMLLoader.load(getClass().getResource("/firma/view/storage_manager/StorageManagerWindow.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        ViewController.setStageAdminWindow(stage);
                        stage.show();
                        Stage curentStage = (Stage) btnOK.getScene().getWindow();
                        curentStage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            } else {
                fldPassword.setEffect(new InnerShadow(5, Color.RED));
                fldLogin.setEffect(new InnerShadow(5, Color.RED));
            }
        }
    }

    private void testDataEmployee() throws ParseException {

        //TODO

        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"firma/ioc/spring-config.xml"});

        AccountEmployee account11 = new AccountEmployee("admin", "admin");

        EmployeeService employeeService = context.getBean(EmployeeService.class);
        employeeService.create(new EmployeeFirm("Рамік", "Андрій", "Русланович",
                new SimpleDateFormat("dd.MM.yyy").parse("10.10.2010"),
                new SimpleDateFormat("dd.MM.yyy").parse("10.10.2017"),
                EmployeeRols.ADMINISTRATOR, account11));

        /*SessionFactory factory = HibernateUtil.getFactory();
        Session session = factory.openSession();
        AccountService accountService = new AccountServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();

        //      create Accounts
        AccountEmployee account1 = new AccountEmployee("manager", "manager");
        AccountEmployee account2 = new AccountEmployee();
        AccountEmployee account3 = new AccountEmployee();
        AccountEmployee account4 = new AccountEmployee();
        AccountEmployee account5 = new AccountEmployee();
        AccountEmployee account6 = new AccountEmployee();
        AccountEmployee account7 = new AccountEmployee();
        AccountEmployee account8 = new AccountEmployee();
        AccountEmployee account9 = new AccountEmployee();
        AccountEmployee account10 = new AccountEmployee();
        AccountEmployee account11 = new AccountEmployee("admin", "admin");

        //      create Employees
        EmployeeFirm employee1 = new EmployeeFirm("Прищепка", "Антоніна", "Анатоліївна", new SimpleDateFormat("dd.MM.yyy").parse("01.01.2001"), new SimpleDateFormat("dd.MM.yyy").parse("01.01.2017"), EmployeeRols.SALES_MANAGER, account1);
        EmployeeFirm employee2 = new EmployeeFirm("Морда", "Ростислав", "Степанович", new SimpleDateFormat("dd.MM.yyy").parse("02.02.2002"), new SimpleDateFormat("dd.MM.yyy").parse("02.02.2017"), EmployeeRols.SALES_MANAGER, account2);
        EmployeeFirm employee3 = new EmployeeFirm("Брунька", "Мирослава", "Пилипівна", new SimpleDateFormat("dd.MM.yyy").parse("03.03.2003"), new SimpleDateFormat("dd.MM.yyy").parse("03.03.2017"), EmployeeRols.SALES_MANAGER, account3);
        EmployeeFirm employee4 = new EmployeeFirm("Безстрашний", "Олег", "Роанович", new SimpleDateFormat("dd.MM.yyy").parse("04.04.2004"), new SimpleDateFormat("dd.MM.yyy").parse("04.04.2017"), EmployeeRols.SALES_MANAGER, account4);
        EmployeeFirm employee5 = new EmployeeFirm("Рєзкий", "Панас", "Миронович", new SimpleDateFormat("dd.MM.yyy").parse("05.05.2005"), new SimpleDateFormat("dd.MM.yyy").parse("05.05.2017"), EmployeeRols.STORAGE_MANAGER, account5);
        EmployeeFirm employee6 = new EmployeeFirm("Гудзик", "Тетяна", "Мартинівна", new SimpleDateFormat("dd.MM.yyy").parse("06.06.2006"), new SimpleDateFormat("dd.MM.yyy").parse("06.06.2017"), EmployeeRols.STORAGE_MANAGER, account6);
        EmployeeFirm employee7 = new EmployeeFirm("Рододендрон", "Мирослав", "Іванович", new SimpleDateFormat("dd.MM.yyy").parse("07.07.2007"), new SimpleDateFormat("dd.MM.yyy").parse("07.07.2017"), EmployeeRols.SALES_MANAGER, account7);
        EmployeeFirm employee8 = new EmployeeFirm("Бравий", "Карп", "Маркович", new SimpleDateFormat("dd.MM.yyy").parse("08.08.2008"), new SimpleDateFormat("dd.MM.yyy").parse("08.08.2017"), EmployeeRols.STORAGE_MANAGER, account8);
        EmployeeFirm employee9 = new EmployeeFirm("Світлячок", "Світлана", "Юріївна", new SimpleDateFormat("dd.MM.yyy").parse("09.09.2009"), new SimpleDateFormat("dd.MM.yyy").parse("09.09.2017"), EmployeeRols.SALES_MANAGER, account9);
        EmployeeFirm employee10 = new EmployeeFirm("Піпетка", "Жанна", "Прокопівна", new SimpleDateFormat("dd.MM.yyy").parse("10.10.2010"), new SimpleDateFormat("dd.MM.yyy").parse("10.10.2017"), EmployeeRols.SALES_MANAGER, account10);
        EmployeeFirm employee11 = new EmployeeFirm("Рамік", "Андрій", "Русланович", new SimpleDateFormat("dd.MM.yyy").parse("10.10.2010"), new SimpleDateFormat("dd.MM.yyy").parse("10.10.2017"), EmployeeRols.ADMINISTRATOR, account11);
        employee1.setAccount(true);
        employee11.setAccount(true);

//      seve Accounts in DB
        accountService.create(account1);
        accountService.create(account2);
        accountService.create(account3);
        accountService.create(account4);
        accountService.create(account5);
        accountService.create(account6);
        accountService.create(account7);
        accountService.create(account8);
        accountService.create(account9);
        accountService.create(account10);
        accountService.create(account11);

//      save Employees in DB
        employeeService.create(employee1);
        employeeService.create(employee2);
        employeeService.create(employee3);
        employeeService.create(employee4);
        employeeService.create(employee5);
        employeeService.create(employee6);
        employeeService.create(employee7);
        employeeService.create(employee8);
        employeeService.create(employee9);
        employeeService.create(employee10);
        employeeService.create(employee11);

        session.close();*/
//        HibernateUtil.getFactory().close();
    }


}
