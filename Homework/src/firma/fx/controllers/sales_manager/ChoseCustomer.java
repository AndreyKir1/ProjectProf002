package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ChoseCustomer {

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
    private TableView<Client> tblView;

    @FXML
    private Label lblCustomerCount;

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
    void pressAdd() {

    }

    @FXML
    void pressUpdate() {

    }

    @FXML
    void pressDelete() {

    }

    @FXML
    void pressSave() {

    }

    @FXML
    void pressCancel() {

    }

}

