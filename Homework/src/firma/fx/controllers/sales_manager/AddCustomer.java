package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Client;
import firma.hibernate.service.client.ClientService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AddCustomer {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});

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
    void pressSave() {
        if(fldSurName.getText() != null && fldName.getText() != null && fldLastName.getText() != null && fldEmail.getText() != null && fldPhoneNumber.getText() != null
                && fldSurName.getText().length() > 0 && fldName.getText().length() > 0 && fldLastName.getText().length() > 0
                && fldEmail.getText().length() > 0 && fldPhoneNumber.getText().length() > 0){
            ClientService service = context.getBean(ClientService.class);
            service.create(new Client(fldSurName.getText(), fldName.getText(), fldLastName.getText(), fldPhoneNumber.getText(), fldEmail.getText()));
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
}
