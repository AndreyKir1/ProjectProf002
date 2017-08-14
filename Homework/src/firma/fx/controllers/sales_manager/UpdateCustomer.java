package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Client;
import firma.hibernate.service.client.ClientService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UpdateCustomer {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private static Client currentClient;

    @FXML
    private CustomTextField fldPhoneNumber;

    @FXML
    private Button btnCancel;

    @FXML
    private CustomTextField fldSurName;

    @FXML
    private CustomTextField fldEmail;

    @FXML
    private Button btnSave;

    @FXML
    private CustomTextField fldName;

    @FXML
    private CustomTextField fldLastName;

    @FXML
    private void initialize(){
        fldSurName.setText(currentClient.getSurname());
        fldName.setText(currentClient.getName());
        fldLastName.setText(currentClient.getLastName());
        fldPhoneNumber.setText(currentClient.getPhoneNumder());
        fldEmail.setText(currentClient.getEmail());
    }

    @FXML
    void pressSave() {
        if(fldSurName.getText() != null && fldName.getText() != null && fldLastName.getText() != null && fldEmail.getText() != null && fldPhoneNumber.getText() != null
                && fldSurName.getText().length() > 0 && fldName.getText().length() > 0 && fldLastName.getText().length() > 0
                && fldEmail.getText().length() > 0 && fldPhoneNumber.getText().length() > 0){
            ClientService service = context.getBean(ClientService.class);
            currentClient.setSurname(fldSurName.getText());
            currentClient.setName(fldName.getText());
            currentClient.setLastName(fldLastName.getText());
            currentClient.setPhoneNumder(fldPhoneNumber.getText());
            currentClient.setEmail(fldEmail.getText());
            service.update(currentClient);
            ChoseCustomer.updateCustomerList();
            Stage current = (Stage) btnSave.getScene().getWindow();
            current.close();
        }
    }

    @FXML
    void pressCancel() {
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();
    }

    public static void setCurrentClient(Client currentClient) {
        UpdateCustomer.currentClient = currentClient;
    }
}
