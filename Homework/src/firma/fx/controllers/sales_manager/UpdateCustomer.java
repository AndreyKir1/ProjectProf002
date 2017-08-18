package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Client;
import firma.hibernate.service.client.ClientService;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;

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

        setClearInCTF(fldSurName);
        setClearInCTF(fldName);
        setClearInCTF(fldLastName);
        setClearInCTF(fldEmail);
        setClearInCTF(fldPhoneNumber);

        fldSurName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldSurName.setEffect(null);
        });

        fldName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldName.setEffect(null);
        });

        fldLastName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldLastName.setEffect(null);
        });

        fldPhoneNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldPhoneNumber.setEffect(null);
        });

        fldEmail.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldEmail.setEffect(null);
        });
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
        }else{
            if (fldSurName.getText() == null || fldSurName.getText().length() == 0) {
                fldSurName.setEffect(new InnerShadow(5, Color.RED));
            }
            if (fldName.getText() == null || fldSurName.getText().length() == 0) {
                fldName.setEffect(new InnerShadow(5, Color.RED));
            }
            if (fldLastName.getText() == null || fldSurName.getText().length() == 0) {
                fldLastName.setEffect(new InnerShadow(5, Color.RED));
            }
            if (fldPhoneNumber.getText() == null || fldSurName.getText().length() == 0) {
                fldPhoneNumber.setEffect(new InnerShadow(5, Color.RED));
            }
            if (fldEmail.getText() == null || fldSurName.getText().length() == 0) {
                fldEmail.setEffect(new InnerShadow(5, Color.RED));
            }
        }
    }

    @FXML
    private void setClearInCTF(CustomTextField ctf) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, ctf, ctf.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
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
