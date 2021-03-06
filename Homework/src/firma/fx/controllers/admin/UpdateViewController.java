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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class UpdateViewController {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private EmployeeService employeeService = context.getBean(EmployeeService.class);
    private AccountService accountService = context.getBean(AccountService.class);

    private EmployeeFirm currentEmployee;
    private AccountEmployee currentAccount;

    @FXML
    private static Stage stageRoot;

    @FXML
    private DatePicker fldDateOfStartWorking;

    @FXML
    private Button btnCancel;

    @FXML
    private CustomTextField fldSurName;

    @FXML
    private CustomTextField fldPassword;

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
    private CustomTextField fldLogin;

    @FXML
    private CustomTextField fldLastName;

    @FXML
    private void initialize() {
        fldRole.getItems().setAll(EmployeeRols.values());
        currentEmployee = new AdminWindowController().getCurrentEmployee();
        currentAccount = currentEmployee.getAccountEmployee();
        fldBirthDay.setTooltip(new Tooltip("Дата народження не може бути пізнішою за дату прийому на роботу"));
        fldDateOfStartWorking.setTooltip(new Tooltip("Дата прийому на роботу не може бути ранішою за дату народження"));

        fldSurName.setText(currentEmployee.getSurname());
        fldName.setText(currentEmployee.getName());
        fldLastName.setText(currentEmployee.getLastName());
        fldBirthDay.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(currentEmployee.getBitrhDay())));
        fldDateOfStartWorking.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(currentEmployee.getDateOfStarWorking())));
        lbAge.setText(currentEmployee.getAge().toString());
        fldRole.setValue(currentEmployee.getEmployeeRols());

        fldLogin.setText(currentAccount.getLogin());
        fldPassword.setText(currentAccount.getPassword());

        fldRole.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fldRole.setEffect(null);
            }
        });

        fldSurName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldSurName.setEffect(null);
        });

        fldName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldName.setEffect(null);
        });

        fldLastName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldLastName.setEffect(null);
        });

        fldBirthDay.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldBirthDay.setEffect(null);
        });

        fldDateOfStartWorking.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldDateOfStartWorking.setEffect(null);
        });

        fldLogin.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldLogin.setEffect(null);
        });

        fldPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldPassword.setEffect(null);
        });

        fldBirthDay.setEditable(false);
        fldDateOfStartWorking.setEditable(false);

        setClearInCTF(fldLastName);
        setClearInCTF(fldSurName);
        setClearInCTF(fldName);
        setClearInCTF(fldLogin);
        setClearInCTF(fldPassword);
    }

    public static void setStageRoot(Stage stageRoot) {
        UpdateViewController.stageRoot = stageRoot;
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
    private void pressSave1() {
        shadowEffects();
        btnSave.requestFocus();
        if (fldSurName.getText().length() > 0 && fldName.getText().length() > 0 && fldLastName.getText().length() > 0 && fldBirthDay.getValue() != null
                && fldDateOfStartWorking.getValue() != null && fldRole.getValue() != null
                && !((fldLogin.getText() != null && fldLogin.getText().length() > 0) && (fldPassword.getText() == null || fldPassword.getText().length() <= 0)) &&
                !((fldLogin.getText() == null || fldLogin.getText().length() <= 0) && (fldPassword.getText() != null && fldPassword.getText().length() > 0))
                && ((fldDateOfStartWorking.getValue().getYear() - fldBirthDay.getValue().getYear()) > 0)) {
            pressSave();
            showParentWindow();
        }

    }

    private void pressSave() {
        currentEmployee.setSurname(fldSurName.getText());
        currentEmployee.setName(fldName.getText());
        currentEmployee.setLastName(fldLastName.getText());
        currentEmployee.setAge(LocalDate.now().getYear() - fldBirthDay.getValue().getYear());
        currentEmployee.setBitrhDay(Date.from(fldBirthDay.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        currentEmployee.setDateOfStarWorking(Date.from(fldDateOfStartWorking.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        currentEmployee.setEmployeeRols(fldRole.getValue());
        if ((fldLogin.getText() != null && fldLogin.getText().length() > 0) && (fldPassword.getText() != null && fldPassword.getText().length() > 0)) {
            currentEmployee.setAccount(true);
            currentAccount.setLogin(fldLogin.getText());
            currentAccount.setPassword(fldPassword.getText());
        }else if ((fldLogin.getText() == null && fldPassword.getText() == null) || ((fldLogin.getText() != null && fldLogin.getText().length() <= 0) && (fldPassword.getText() != null && fldPassword.getText().length() <= 0))){
            currentEmployee.setAccount(false);
            currentAccount.setLogin(null);
            currentAccount.setPassword(null);
        }

        accountService.update(currentAccount);
        employeeService.update(currentEmployee);
        Long employeeId = currentEmployee.getId();

        currentEmployee = employeeService.read(employeeId);
        currentAccount = currentEmployee.getAccountEmployee();

        ViewController viewController = AdminWindowController.getViewController();
        viewController.getLbSurname().setText(currentEmployee.getSurname());
        viewController.getLbName().setText(currentEmployee.getName());
        viewController.getLbLastName().setText(currentEmployee.getLastName());
        viewController.getLbRole().setText(currentEmployee.getEmployeeRols().toString());
        viewController.getLbAge().setText(currentEmployee.getAge().toString());
        viewController.getLbBirthDay().setText(new SimpleDateFormat("dd.MM.yyyy").format(currentEmployee.getBitrhDay()));
        viewController.getLbDayOfStartWorking().setText(new SimpleDateFormat("dd.MM.yyyy").format(currentEmployee.getDateOfStarWorking()));
        viewController.getLbLogin().setText(currentAccount.getLogin());
        viewController.getLbPassword().setText(currentAccount.getPassword());

        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    private void shadowEffects() {
        InnerShadow shadow = new InnerShadow(5, Color.RED);
        if (fldSurName.getText().length() <= 0) fldSurName.setEffect(shadow);
        if (fldName.getText().length() <= 0) fldName.setEffect(shadow);
        if (fldLastName.getText().length() <= 0) fldLastName.setEffect(shadow);
        if (fldDateOfStartWorking.getValue() == null) fldDateOfStartWorking.setEffect(shadow);
        if (fldBirthDay.getValue() == null) fldBirthDay.setEffect(shadow);
        if (fldRole.getValue() == null) fldRole.setEffect(shadow);
        if (((fldLogin.getText() != null && fldLogin.getText().length() > 0) && (fldPassword.getText() == null || fldPassword.getText().length() <= 0)) ||
                ((fldLogin.getText() == null || fldLogin.getText().length() <= 0) && (fldPassword.getText() != null && fldPassword.getText().length() > 0))) {
            fldLogin.setEffect(shadow);
            fldPassword.setEffect(shadow);
        }
        if ((fldDateOfStartWorking.getValue().getYear() - fldBirthDay.getValue().getYear()) <= 0){
            fldBirthDay.setEffect(shadow);
            fldDateOfStartWorking.setEffect(shadow);
        }
    }

    @FXML
    private void showParentWindow() {
        new AdminWindowController().updateListEmployee();
        stageRoot.show();

    }

    @FXML
    private void pressCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        stageRoot.show();
    }
}
