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

public class DeleteVFormController {
    private EmployeeFirm currentEmployee;
    private AccountEmployee currentAccount;
    private EmployeeService employeeService;
    private AccountService accountService;

    @FXML
    private static Stage stageRoot;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnOK;

    @FXML
    private Label lbtext;

    @FXML
    private void initialize(){
        currentEmployee = new AdminWindowController().getCurrentEmployee();
        currentAccount = currentEmployee.getAccountEmployee();
    }

    public static void setStageRoot(Stage stageRoot) {
        DeleteVFormController.stageRoot = stageRoot;
    }

    @FXML
    private void pressCancel(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void pressOK(){
        employeeService = new EmployeeServiceImpl();
        accountService = new AccountServiceImpl();

        employeeService.delete(currentEmployee);
        accountService.delete(currentAccount);

        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.close();
        stageRoot.close();
        new AdminWindowController().updateListEmployee();
    }

    @FXML
    private void pressUpdate(){

    }
}
