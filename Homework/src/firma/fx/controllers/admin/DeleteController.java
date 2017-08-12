package firma.fx.controllers.admin;


import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.service.account.AccountService;
import firma.hibernate.service.account.AccountServiceImpl;
import firma.hibernate.service.employee.EmployeeService;
import firma.hibernate.service.employee.EmployeeServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DeleteController {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private EmployeeService employeeService = context.getBean(EmployeeService.class);
    private AccountService accountService = context.getBean(AccountService.class);

    private EmployeeFirm currentEmployee;
    private AccountEmployee currentAccount;

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