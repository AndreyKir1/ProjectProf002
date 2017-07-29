package firma.fx.controllers;

import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import firma.support.EmployeeRols;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class UpdateViewController {
    private EmployeeFirm currentEmployee;
    private AccountEmployee currentAccount;

    @FXML
    private static Stage stageRoot;

    @FXML
    private DatePicker fldDateOfStartWorking;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField fldSurName;

    @FXML
    private TextField fldPassword;

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
    private TextField fldLogin;

    @FXML
    private TextField fldLastName;

    @FXML
    private void initialize() {
        fldRole.getItems().setAll(EmployeeRols.ADMINISTRATOR, EmployeeRols.MANAGER);
        currentEmployee = new AdminWindowController().getCurrentEmployee();
        currentAccount = currentEmployee.getAccountEmployee();

        fldSurName.setText(currentEmployee.getSurname());
        fldName.setText(currentEmployee.getName());
        fldLastName.setText(currentEmployee.getLastName());
        fldBirthDay.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(currentEmployee.getBitrhDay())));
        fldDateOfStartWorking.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(currentEmployee.getDateOfStarWorking())));
        lbAge.setText(currentEmployee.getAge().toString());
        fldRole.setValue(currentEmployee.getEmployeeRols());

        fldLogin.setText(currentAccount.getLogin());
        fldPassword.setText(currentAccount.getPassword());
    }

    public static void setStageRoot(Stage stageRoot) {
        UpdateViewController.stageRoot = stageRoot;
    }

    @FXML
    private void pressSave() {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
        stageRoot.show();
    }

    @FXML
    private void pressCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        stageRoot.show();
    }
}
