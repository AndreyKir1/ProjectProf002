package firma.fx.controllers.admin;

import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.service.account.AccountService;
import firma.hibernate.service.account.AccountServiceImpl;
import firma.hibernate.service.employee.EmployeeService;
import firma.hibernate.service.employee.EmployeeServiceImpl;
import firma.support.EmployeeRols;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.lang.reflect.Method;
import java.time.ZoneId;
import java.util.Date;

public class AddController {
    boolean fillBDfield = false;
    boolean fillDOSWfield = false;


    private AccountService accountService = new AccountServiceImpl();

    private EmployeeService employeeService = new EmployeeServiceImpl();

    @FXML
    private static AdminWindowController adminController;

    @FXML
    private DatePicker fldDateOfStartWorking;

    @FXML
    private Button btnCancel;

    @FXML
    private CustomTextField fldSurName;

    @FXML
    private Button btnSave;

    @FXML
    private CustomTextField fldName;

    @FXML
    private DatePicker fldBirthDay;

    @FXML
    private Label lbAge;

    @FXML
    private ChoiceBox<EmployeeRols> fldRole;

    @FXML
    private CustomTextField fldLastName;

    @FXML
    private void initialize() {
        fldRole.getItems().addAll(EmployeeRols.values());
        fldBirthDay.setTooltip(new Tooltip("Дата народження не може бути пізнішою за дату прийому на роботу"));
        fldDateOfStartWorking.setTooltip(new Tooltip("Дата прийому на роботу не може бути ранішою за дату народження"));

        fldSurName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldSurName.setEffect(null);
        });

        fldName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldName.setEffect(null);
        });

        fldLastName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldLastName.setEffect(null);
        });

        fldRole.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fldRole.setEffect(null);
            }
        });

        fldBirthDay.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldBirthDay.setEffect(null);
        });

        fldDateOfStartWorking.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldDateOfStartWorking.setEffect(null);
        });

        fldBirthDay.setEditable(false);
        fldDateOfStartWorking.setEditable(false);

//        fldName.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                btnSave.setDisable(false);
//            }
//        });
//        btnSave.setDisable(true);
        setClearInCTF(fldSurName);
        setClearInCTF(fldName);
        setClearInCTF(fldLastName);
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
    private void pressSave() {
        btnSave.requestFocus();
        InnerShadow shadow = new InnerShadow(5, Color.RED);
        if(fldSurName.getText().length() <= 1) fldSurName.setEffect(shadow);
        if(fldName.getText().length() <= 1) fldName.setEffect(shadow);
        if(fldLastName.getText().length() <= 1) fldLastName.setEffect(shadow);
        if(fldDateOfStartWorking.getValue() == null) fldDateOfStartWorking.setEffect(shadow);
        if(fldBirthDay.getValue() == null) fldBirthDay.setEffect(shadow);
        if(fldRole.getValue() == null) fldRole.setEffect(shadow);

        if (fldSurName.getText() != null && fldSurName.getText() != null && fldSurName.getText() != null
                && fldBirthDay.getValue() != null && fldDateOfStartWorking.getValue() != null
                && fldRole.getValue() != null) {
            Date birthDay = Date.from(fldBirthDay.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date dateOfStartWorking = Date.from(fldDateOfStartWorking.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

            if((dateOfStartWorking.getYear() - birthDay.getYear()) <= 0){
                fldBirthDay.setEffect(new InnerShadow(5, Color.RED));
                fldDateOfStartWorking.setEffect(new InnerShadow(5, Color.RED));
            }else{
                AccountEmployee account = new AccountEmployee();
                EmployeeFirm employee = new EmployeeFirm(fldSurName.getText(), fldName.getText(), fldLastName.getText(),
                        Date.from(fldBirthDay.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        Date.from(fldDateOfStartWorking.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        fldRole.getValue(), account);
                accountService.create(account);
                employeeService.create(employee);
                AdminWindowController awc = new AdminWindowController();
                awc.updateListEmployee();
                Stage stage = (Stage) btnSave.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    private void pressCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
