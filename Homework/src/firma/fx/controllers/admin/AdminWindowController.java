package firma.fx.controllers.admin;

import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.service.account.AccountService;
import firma.hibernate.service.account.AccountServiceImpl;
import firma.hibernate.service.employee.EmployeeService;
import firma.hibernate.service.employee.EmployeeServiceImpl;
import firma.hibernate.util.HibernateUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdminWindowController {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private EmployeeService employeeService = context.getBean(EmployeeService.class);
    private AccountService accountService = context.getBean(AccountService.class);
    @FXML
    public static ObservableList<EmployeeFirm> listEmployee;
    @FXML
    private static ObservableList<AccountEmployee> listAccount;
    private static EmployeeFirm currentEmployee;
    private static ViewController viewController;

    @FXML
    private Button btnUpdate;
    @FXML
    private TableView<EmployeeFirm> tblView;
    @FXML
    private Button btnFind;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnShovInfo;
    @FXML
    private Label lblCountEmployee;
    @FXML
    private CustomTextField fldFind;
    @FXML
    private Button btnExit;
    @FXML
    private TableColumn<EmployeeFirm, String> columnLastName;
    @FXML
    private TableColumn<EmployeeFirm, String> columnSurname;
    @FXML
    private TableColumn<EmployeeFirm, String> columnAccount;
    @FXML
    private TableColumn<EmployeeFirm, String> columnName;
    @FXML
    private TableColumn<EmployeeFirm, Date> columnDateStartOfWork;

    public EmployeeFirm getCurrentEmployee() {
        return currentEmployee;
    }

    public static ViewController getViewController() {
        return viewController;
    }

    public void updateListEmployee() {
        listEmployee.clear();
        listEmployee.addAll(employeeService.getAll());
    }

    private void updateCountLbl() {
        lblCountEmployee.setText("Всього співробітників в базі: " + listEmployee.size());
    }

    @FXML
    private void initialize() throws ParseException {
        listEmployee = FXCollections.observableArrayList(employeeService.getAll());
        columnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnDateStartOfWork.setCellValueFactory(new PropertyValueFactory<>("dateOfStarWorking"));
        columnAccount.setCellValueFactory(new PropertyValueFactory<>("account"));//може доведеться булеан приводити до стрінга

        tblView.setItems(listEmployee);
        tblView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    pressShowDetails();
                }
            }
        });

        updateCountLbl();

        listEmployee.addListener(new ListChangeListener<EmployeeFirm>() {
            @Override
            public void onChanged(Change<? extends EmployeeFirm> c) {
                updateCountLbl();
            }
        });

        setClearInCTF(fldFind);
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
    private void pressShowDetails() {
        if (tblView.getSelectionModel().getSelectedItem() != null) {
            currentEmployee = tblView.getSelectionModel().getSelectedItem();
            Long id = currentEmployee.getId();
            currentEmployee = employeeService.read(id);
            AccountEmployee currentAccount = currentEmployee.getAccountEmployee();

            pressShow(currentEmployee.getSurname(), currentEmployee.getName(), currentEmployee.getLastName(),
                    new SimpleDateFormat("dd.MM.yyyy").format(currentEmployee.getBitrhDay()),
                    new SimpleDateFormat("dd.MM.yyyy").format(currentEmployee.getDateOfStarWorking()), currentEmployee.getAge(),
                    currentEmployee.getEmployeeRols().toString(), currentAccount.getLogin(), currentAccount.getPassword());
        }
    }

    @FXML
    private void pressShow(String surmane, String name, String lastname, String birthDay, String dateOfStartWorking, Integer age, String role, String login, String password) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Детальна інформація");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/firma/view/admin/View.fxml"));
            Parent root = fxmlLoader.load();
            viewController = fxmlLoader.getController();
//            встановлення полів вікна перегляду
            viewController.getLbSurname().setText(surmane);
            viewController.getLbName().setText(name);
            viewController.getLbLastName().setText(lastname);
            viewController.getLbBirthDay().setText(birthDay);
            viewController.getLbDayOfStartWorking().setText(dateOfStartWorking);
            viewController.getLbAge().setText(age.toString());
            viewController.getLbRole().setText(role);
            viewController.getLbLogin().setText(login);
            viewController.getLbPassword().setText(password);

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnShovInfo.getScene().getWindow());
            DeleteVFormController.setStageRoot(stage);
            UpdateViewController.setStageRoot(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressAdd() throws ParseException {
        try {
            Stage stage = new Stage();
            stage.setTitle("Додати нового співробітника");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/admin/Add.fxml"));
            Scene scene = new Scene(root);
            stage.setMinWidth(315);
            stage.setMinHeight(360);
            stage.setMaxWidth(415);
            stage.setMaxHeight(360);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnAdd.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressUpdate() {
        currentEmployee = tblView.getSelectionModel().getSelectedItem();
        if (currentEmployee != null) {
            try {
                Stage stage = new Stage();
                stage.setTitle("Редагувати дані співробітника");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/admin/Update.fxml"));
                Scene scene = new Scene(root);
                stage.setMinWidth(315);
                stage.setMinHeight(500);
                stage.setMaxWidth(415);
                stage.setMaxHeight(500);
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnUpdate.getScene().getWindow());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void pressDelete() {
        currentEmployee = tblView.getSelectionModel().getSelectedItem();
        if (currentEmployee != null) {
            try {
                Stage stage = new Stage();
                stage.setTitle("Видалення співробітника");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/admin/Delete.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnDelete.getScene().getWindow());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void pressExit() {
        System.exit(0);
    }

    @FXML
    private void pressSearch(){
        if(fldFind.getText() != null && fldFind.getText().length() > 0){
            List<EmployeeFirm> list = employeeService.getAll();
            listEmployee.clear();
            for(EmployeeFirm el: list){
                if(el.getSurname().toLowerCase().contains(fldFind.getText()) || el.getName().toLowerCase().contains(fldFind.getText())
                        || el.getLastName().toLowerCase().contains(fldFind.getText())){
                    listEmployee.add(el);
                }
            }
            if(listEmployee.size() > 0) {
                fldFind.setText(null);
            }
        }else if (fldFind.getText() == null || fldFind.getText().length() == 0){
            updateListEmployee();
        }
    }
}
