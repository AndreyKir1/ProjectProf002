package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Client;
import firma.hibernate.service.client.ClientService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.text.ParseException;

public class ChoseCustomer {

    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"firma/Config.xml"});
    private ClientService clientService = context.getBean(ClientService.class);

    @FXML
    private static ObservableList<Client> listClient;
    private static Client currentClient;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblCustomerCount;

    @FXML
    private TableView<Client> tblView;

    @FXML
    private TableColumn<Client, String> columnLastName;

    @FXML
    private TableColumn<Client, String> columnEmail;

    @FXML
    private TableColumn<Client, String> columnPhone;

    @FXML
    private TableColumn<Client, String> columnSurName;

    @FXML
    private TableColumn<Client, String> columnName;

    @FXML
    void pressAdd() throws ParseException {
        try {
            Stage stage = new Stage();
            stage.setTitle("Додати нового клієнта");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/AddUpdateCustomer.fxml"));
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
    void pressUpdate() {
        currentClient = tblView.getSelectionModel().getSelectedItem();
        if (currentClient != null){
            try {
                Stage stage = new Stage();
                stage.setTitle("Редагувати дані клієнта");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/AddUpdateCustomer.fxml"));
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
    void pressDelete() {
        currentClient = tblView.getSelectionModel().getSelectedItem();
        if (currentClient != null){
            try {
                Stage stage = new Stage();
                stage.setTitle("Видалення клієнта");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/DeleteCustomerConfirm.fxml"));
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
    void pressSave() {

    }

    @FXML
    void pressCancel() {

    }

}

