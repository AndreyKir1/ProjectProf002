package firma.fx.controllers;

import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.service.AccountService;
import firma.hibernate.service.AccountServiceImpl;
import firma.hibernate.service.EmployeeService;
import firma.hibernate.service.EmployeeServiceImpl;
import firma.hibernate.util.HibernateUtil;
import firma.support.EmployeeRols;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
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
    private PasswordField fldPassword;

    @FXML
    private Button btnOK;

    @FXML
    private Label lbLlogin;

    @FXML
    private TextField fldLogin;

    @FXML
    private void initialize() throws ParseException {
        testDataEmployee();
        fldPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldPassword.setEffect(null);
        });

        fldLogin.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldLogin.setEffect(null);
        });
    }

    @FXML
    private void pressCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void pressOK() {
        btnOK.requestFocus();
        EmployeeService employeeService = new EmployeeServiceImpl();
        AccountService accountService = new AccountServiceImpl();

        listEmployee = employeeService.getAll();
        listAccount = accountService.getAll();

        btnOK.setFocusTraversable(true);
//        fldPassword.setFocusTraversable(false);
//        fldLogin.setFocusTraversable(false);


        for(AccountEmployee el:listAccount){
            if (fldLogin.getText().equals(el.getLogin()) && fldPassword.getText().equals(el.getPassword())) {
                try {
                    Stage stage = new Stage();
                    stage.setTitle("База даних працівників підприємства");
                    stage.setMinHeight(600);
                    stage.setMinWidth(800);
                    Parent root = FXMLLoader.load(getClass().getResource("/firma/view/AdminWindow.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    ViewController.setStageAdminWindow(stage);
                    stage.show();
                    Stage curentStage = (Stage) btnOK.getScene().getWindow();
                    curentStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else{
                fldPassword.setEffect(new InnerShadow(5, Color.RED));
                fldLogin.setEffect(new InnerShadow(5, Color.RED));
//                lbIncorrectLogPass.setText("Некоректне введення логіну чи паролю!");
            }
        }
    }
    private void testDataEmployee() throws ParseException {
        SessionFactory factory = HibernateUtil.getFactory();
        Session session = factory.openSession();
        AccountService accountService = new AccountServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();

        //      create Accounts
        AccountEmployee account1 = new AccountEmployee("login1", "password1");
        AccountEmployee account2 = new AccountEmployee("login2", "password2");
        AccountEmployee account3 = new AccountEmployee("login3", "password3");
        AccountEmployee account4 = new AccountEmployee("login4", "password4");
        AccountEmployee account5 = new AccountEmployee("login5", "password5");
        AccountEmployee account6 = new AccountEmployee("login6", "password6");
        AccountEmployee account7 = new AccountEmployee("login7", "password7");
        AccountEmployee account8 = new AccountEmployee("login8", "password8");
        AccountEmployee account9 = new AccountEmployee("login9", "password9");
        AccountEmployee account10 = new AccountEmployee("login10", "password10");
        AccountEmployee account11 = new AccountEmployee("1", "1");


        //      create Employees

        EmployeeFirm employee1 = new EmployeeFirm("Surname1", "Name1", "LastName1", new SimpleDateFormat("dd.MM.yyy").parse("01.01.2001"), new SimpleDateFormat("dd.MM.yyy").parse("01.01.2017"), EmployeeRols.ADMINISTRATOR, account1);
        EmployeeFirm employee2 = new EmployeeFirm("Surname2", "Name2", "LastName2", new SimpleDateFormat("dd.MM.yyy").parse("02.02.2002"), new SimpleDateFormat("dd.MM.yyy").parse("02.02.2017"), EmployeeRols.ADMINISTRATOR, account2);
        EmployeeFirm employee3 = new EmployeeFirm("Surname3", "Name3", "LastName3", new SimpleDateFormat("dd.MM.yyy").parse("03.03.2003"), new SimpleDateFormat("dd.MM.yyy").parse("03.03.2017"), EmployeeRols.ADMINISTRATOR, account3);
        EmployeeFirm employee4 = new EmployeeFirm("Surname4", "Name4", "LastName4", new SimpleDateFormat("dd.MM.yyy").parse("04.04.2004"), new SimpleDateFormat("dd.MM.yyy").parse("04.04.2017"), EmployeeRols.ADMINISTRATOR, account4);
        EmployeeFirm employee5 = new EmployeeFirm("Surname5", "Name5", "LastName5", new SimpleDateFormat("dd.MM.yyy").parse("05.05.2005"), new SimpleDateFormat("dd.MM.yyy").parse("05.05.2017"), EmployeeRols.ADMINISTRATOR, account5);
        EmployeeFirm employee6 = new EmployeeFirm("Surname6", "Name6", "LastName6", new SimpleDateFormat("dd.MM.yyy").parse("06.06.2006"), new SimpleDateFormat("dd.MM.yyy").parse("06.06.2017"), EmployeeRols.ADMINISTRATOR, account6);
        EmployeeFirm employee7 = new EmployeeFirm("Surname7", "Name7", "LastName7", new SimpleDateFormat("dd.MM.yyy").parse("07.07.2007"), new SimpleDateFormat("dd.MM.yyy").parse("07.07.2017"), EmployeeRols.ADMINISTRATOR, account7);
        EmployeeFirm employee8 = new EmployeeFirm("Surname8", "Name8", "LastName8", new SimpleDateFormat("dd.MM.yyy").parse("08.08.2008"), new SimpleDateFormat("dd.MM.yyy").parse("08.08.2017"), EmployeeRols.ADMINISTRATOR, account8);
        EmployeeFirm employee9 = new EmployeeFirm("Surname9", "Name9", "LastName9", new SimpleDateFormat("dd.MM.yyy").parse("09.09.2009"), new SimpleDateFormat("dd.MM.yyy").parse("09.09.2017"), EmployeeRols.ADMINISTRATOR, account9);
        EmployeeFirm employee10 = new EmployeeFirm("Surname10", "Name10", "LastName10", new SimpleDateFormat("dd.MM.yyy").parse("10.10.2010"), new SimpleDateFormat("dd.MM.yyy").parse("10.10.2017"), EmployeeRols.ADMINISTRATOR, account10);
        EmployeeFirm employee11 = new EmployeeFirm("Surname11", "Name11", "LastName11", new SimpleDateFormat("dd.MM.yyy").parse("10.10.2010"), new SimpleDateFormat("dd.MM.yyy").parse("10.10.2017"), EmployeeRols.ADMINISTRATOR, account11);


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


        session.close();
//        HibernateUtil.getFactory().close();
    }


}
