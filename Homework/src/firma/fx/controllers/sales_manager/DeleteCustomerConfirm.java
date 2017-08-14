package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Client;
import firma.hibernate.service.client.ClientService;
import firma.hibernate.service.order.OrderService;
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

public class DeleteCustomerConfirm {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private ClientService clientService = context.getBean(ClientService.class);
    private OrderService orderService = context.getBean(OrderService.class);
    private static Client currentClient;

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
        lbEmployee.setText(currentClient.getSurname() + " " + currentClient.getName() + " " + currentClient.getLastName());
    }

    @FXML
    void pressOK() {
        clientService.delete(currentClient);
        ChoseCustomer.updateCustomerList();
        Stage current = (Stage) btnOK.getScene().getWindow();
        current.close();


    }

    @FXML
    void pressCancel() {
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();
    }

    public static void setCurrentClient(Client currentClient) {
        DeleteCustomerConfirm.currentClient = currentClient;
    }
}

