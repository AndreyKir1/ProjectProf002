package firma.fx.controllers.admin;


import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.service.account.AccountService;
import firma.hibernate.service.account.AccountServiceImpl;
import firma.hibernate.service.employee.EmployeeService;
import firma.hibernate.service.employee.EmployeeServiceImpl;
import firma.hibernate.service.order.OrderService;
import firma.support.EmployeeRols;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DeleteController {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private EmployeeService employeeService = context.getBean(EmployeeService.class);
    private AccountService accountService = context.getBean(AccountService.class);
    private OrderService orderService = context.getBean(OrderService.class);

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
    private void initialize() {
        currentEmployee = new AdminWindowController().getCurrentEmployee();
        currentAccount = currentEmployee.getAccountEmployee();
        lbEmployee.setText(currentEmployee.getSurname() + " " + currentEmployee.getName() + " " + currentEmployee.getLastName() + "?");
    }

    @FXML
    private void pressOK() throws InterruptedException {
        if (!currentEmployee.getEmployeeRols().equals(EmployeeRols.ADMINISTRATOR)) {
            if (orderService.getOrdersByEmployee(currentEmployee) == null ||
                    orderService.getOrdersByEmployee(currentEmployee).size() == 0) {
                employeeService.delete(currentEmployee);
                accountService.delete(currentAccount);
            } else {
                try {
                    Stage stage = new Stage();
                    stage.setTitle("Обережно!");
                    Parent root = FXMLLoader.load(getClass().getResource("/firma/view/admin/CantDeleteEmployee.fxml"));
                    Scene scene = new Scene(root);
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(btnOK.getScene().getWindow());
                    stage.show();
                    TimeUnit.SECONDS.sleep(2);
                    stage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Stage stage = new Stage();
                stage.setTitle("Обережно!");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/admin/CantDeleteAdmin.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnOK.getScene().getWindow());
                stage.show();
                TimeUnit.SECONDS.sleep(2);
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.close();
        new AdminWindowController().updateListEmployee();
    }

    @FXML
    private void pressCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}