package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Client;
import firma.hibernate.service.client.ClientService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DeleteCustomerConfirm {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
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
    private void initialize(){
        lbEmployee.setText(currentClient.getSurname() + " " + currentClient.getName() + " " + currentClient.getLastName());
    }

    @FXML
    void pressOK() {
        ClientService service = context.getBean(ClientService.class);
        service.delete(currentClient);
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

