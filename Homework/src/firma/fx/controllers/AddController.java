package firma.fx.controllers;

import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.service.AccountService;
import firma.hibernate.service.AccountServiceImpl;
import firma.hibernate.service.EmployeeService;
import firma.hibernate.service.EmployeeServiceImpl;
import firma.support.EmployeeRols;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static java.awt.Color.RED;

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
    private TextField fldSurName;

    @FXML
    private Button btnSave;

    @FXML
    private TextField fldName;

    @FXML
    private DatePicker fldBirthDay;

    @FXML
    private Label lbAge;

    @FXML
    private ChoiceBox<EmployeeRols> fldRole;

    @FXML
    private TextField fldLastName;

    @FXML
    private void initialize() {
        fldRole.getItems().addAll(EmployeeRols.ADMINISTRATOR, EmployeeRols.MANAGER);

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


//        fldName.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                btnSave.setDisable(false);
//            }
//        });
//        btnSave.setDisable(true);

    }

    @FXML
    private void pressSave() {
        btnSave.setFocusTraversable(true);
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
