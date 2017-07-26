package firma.fx.controllers;

import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.service.AccountService;
import firma.hibernate.service.AccountServiceImpl;
import firma.hibernate.service.EmployeeService;
import firma.hibernate.service.EmployeeServiceImpl;
import firma.hibernate.util.HibernateUtil;
import firma.support.EmployeeRols;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdminWindowController {
    @FXML
    private static ObservableList<EmployeeFirm> listEmployee;

//    @FXML
//    private static ObservableList<AccountEmployee> listAccount;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<EmployeeFirm> tblView;

    @FXML
    private Button btnFind;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnShovInfo;

    @FXML
    private Label lblCountEmployee;

    @FXML
    private TextField fldFind;

    @FXML
    private TableColumn<EmployeeFirm, String> columnLastName;

    @FXML
    private TableColumn<EmployeeFirm, String> columnSurname;

    @FXML
    private TableColumn<EmployeeFirm, String> columnAccount;

    @FXML
    private TableColumn<EmployeeFirm, String> columnName;

    @FXML
    private TableColumn<EmployeeFirm, Date> columnDateStartOfWork;

    @FXML
    private void initialize() throws ParseException {
        columnSurname.setCellValueFactory(new PropertyValueFactory<EmployeeFirm, String>("surname"));
        columnName.setCellValueFactory(new PropertyValueFactory<EmployeeFirm, String>("name"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<EmployeeFirm, String>("lastName"));
        columnDateStartOfWork.setCellValueFactory(new PropertyValueFactory<EmployeeFirm, Date>("dateOfStarWorking"));
        columnAccount.setCellValueFactory(new PropertyValueFactory<EmployeeFirm, String>("account"));//може доведеться булеан приводити до стрінга
        listEmployee = FXCollections.observableArrayList(testDataEmployee());
        tblView.setItems(listEmployee);
        updateCountLbl();
        listEmployee.addListener(new ListChangeListener<EmployeeFirm>() {
            @Override
            public void onChanged(Change<? extends EmployeeFirm> c) {
                updateCountLbl();
            }
        });
    }

    private void updateCountLbl() {
        lblCountEmployee.setText("Всього співробітників в базі: " + listEmployee.size());
    }

    @FXML
    private void pressShowDetails(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Детальна інформація");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/View.fxml"));
            Scene scene = new Scene (root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnShovInfo.getScene().getWindow());
            DeleteVFormController.setStageRoot(stage);
            UpdateViewController.setStageRoot(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressAdd(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Додати нового співробітника");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/Add.fxml"));
            Scene scene = new Scene(root);
            stage.setMinWidth(315);
            stage.setMinHeight(360);
            stage.setMaxWidth(415);
            stage.setMaxHeight(360);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnAdd.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressUpdate(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Редагувати дані співробітника");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/Update.fxml"));
            Scene scene = new Scene(root);
            stage.setMinWidth(315);
            stage.setMinHeight(500);
            stage.setMaxWidth(415);
            stage.setMaxHeight(500);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnUpdate.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressDelete(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Видалення співробітника");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/Delete.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnDelete.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<EmployeeFirm> testDataEmployee() throws ParseException {
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
        EmployeeFirm employee10 = new EmployeeFirm("Surname11", "Name10", "LastName10", new SimpleDateFormat("dd.MM.yyy").parse("10.10.2010"), new SimpleDateFormat("dd.MM.yyy").parse("10.10.2017"), EmployeeRols.ADMINISTRATOR, account10);

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

        List<EmployeeFirm> listEmployee = employeeService.getAll();
//        List<AccountEmployee> listAccount = accountService.getAll();

        session.close();
        HibernateUtil.getFactory().close();
        return listEmployee;
    }
}
