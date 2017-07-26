package firma.fx.controllers;

import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.service.AccountService;
import firma.hibernate.service.AccountServiceImpl;
import firma.hibernate.service.EmployeeService;
import firma.hibernate.service.EmployeeServiceImpl;
import firma.hibernate.util.HibernateUtil;
import firma.support.EmployeeRols;
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
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class AdminWindowController {
    @FXML
    private static ObservableList<EmployeeFirm> listEmployee;

    @FXML
    private static ObservableList<AccountEmployee> listAccount;

    private EmployeeService employeeService;

    private AccountService accountService;

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
    private TextField fldFind;

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

    @FXML
    private void initialize() throws ParseException {
        accountService = new AccountServiceImpl();
        employeeService = new EmployeeServiceImpl();

        columnSurname.setCellValueFactory(new PropertyValueFactory<EmployeeFirm, String>("surname"));
        columnName.setCellValueFactory(new PropertyValueFactory<EmployeeFirm, String>("name"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<EmployeeFirm, String>("lastName"));
        columnDateStartOfWork.setCellValueFactory(new PropertyValueFactory<EmployeeFirm, Date>("dateOfStarWorking"));
        columnAccount.setCellValueFactory(new PropertyValueFactory<EmployeeFirm, String>("account"));//може доведеться булеан приводити до стрінга
        listEmployee = FXCollections.observableArrayList(employeeService.getAll());
        tblView.setItems(listEmployee);
        tblView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    EmployeeFirm selectedItem = tblView.getSelectionModel().getSelectedItem();
                    pressShowDetails();
//                    viewController.initialize(selectedItem.getSurname(),
//                            selectedItem.getName(), selectedItem.getLastName());
//                    viewController = new ViewController();
//                    pressShowDetails();
//                    viewController.getLbSurname().setText(selectedItem.getSurname());
//                    viewController.getLbName().setText(selectedItem.getName());
//                    viewController.getLbLastName().setText(selectedItem.getLastName());
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
    }

    private void updateCountLbl() {
        lblCountEmployee.setText("Всього співробітників в базі: " + listEmployee.size());
    }

    @FXML
    private void pressShowDetails(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Детальна інформація");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/View.fxml"));
            Scene scene = new Scene (root);
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
    private void pressAdd(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Додати нового співробітника");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/Add.fxml"));
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
    private void pressUpdate(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Редагувати дані співробітника");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/Update.fxml"));
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

    @FXML
    private void pressDelete(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Видалення співробітника");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/Delete.fxml"));
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
