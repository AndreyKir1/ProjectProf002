package firma.fx.controllers;

import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.service.AccountService;
import firma.hibernate.service.AccountServiceImpl;
import firma.hibernate.service.EmployeeService;
import firma.hibernate.service.EmployeeServiceImpl;
import firma.support.EmployeeRols;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.ZoneId;
import java.util.Date;

public class AddController {

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
    }

    @FXML
    private void pressSave() {
        if (fldSurName.getText() != null && fldSurName != null && fldSurName != null && fldBirthDay != null &&
                fldDateOfStartWorking != null && fldRole != null) {

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


    @FXML
    private void pressCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
