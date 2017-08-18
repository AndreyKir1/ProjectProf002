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
    private void initialize() {
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
    void pressSave() {
        if (fldSurName.getText() != null && fldName.getText() != null && fldLastName.getText() != null && fldEmail.getText() != null && fldPhoneNumber.getText() != null
                && fldSurName.getText().length() > 0 && fldName.getText().length() > 0 && fldLastName.getText().length() > 0
                && fldEmail.getText().length() > 0 && fldPhoneNumber.getText().length() > 0) {
            ClientService service = context.getBean(ClientService.class);
            service.create(new Client(fldSurName.getText(), fldName.getText(), fldLastName.getText(), fldPhoneNumber.getText(), fldEmail.getText()));
            ChoseCustomer.updateCustomerList();
            Stage current = (Stage) btnSave.getScene().getWindow();
            current.close();
        } else {
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
    void pressCancel() {
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();
    }
}
