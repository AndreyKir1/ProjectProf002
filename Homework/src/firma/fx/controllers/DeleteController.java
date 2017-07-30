package firma.fx.controllers;


import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.service.AccountService;
import firma.hibernate.service.AccountServiceImpl;
import firma.hibernate.service.EmployeeService;
import firma.hibernate.service.EmployeeServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeleteController {
    private EmployeeFirm currentEmployee;
    private AccountEmployee currentAccount;
    private EmployeeService employeeService;
    private AccountService accountService;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnOK;

    @FXML
    private Label lbtext;


    @FXML
    private Label lbEmployee;

    @FXML
    private void initialize(){
        currentEmployee = new AdminWindowController().getCurrentEmployee();
        currentAccount = currentEmployee.getAccountEmployee();
        lbEmployee.setText(currentEmployee.getSurname() + " " + currentEmployee.getName() + " " + currentEmployee.getLastName() + "?");
    }

    @FXML
    private void pressOK(){
        employeeService = new EmployeeServiceImpl();
        accountService = new AccountServiceImpl();

        employeeService.delete(currentEmployee);
        accountService.delete(currentAccount);

        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.close();
        new AdminWindowController().updateListEmployee();
    }

    @FXML
    private void pressCancel(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}