package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.OrderPosition;
import firma.support.OrderStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class UpdateOrder {

    @FXML
    private Button btnAddNewToDB;

    @FXML
    private Button btnOK;

    @FXML
    private Label lbOrderNumber;

    @FXML
    private TableView<OrderPosition> tableOrderPosition;

    @FXML
    private TableColumn<OrderPosition, Integer> columnAmount;

    @FXML
    private TableColumn<OrderPosition, String> columnProductCode;

    @FXML
    private TableColumn<OrderPosition, String> columnProductNane;

    @FXML
    private TableColumn<OrderPosition, Double> columnCost;

    @FXML
    private ChoiceBox<OrderStatus> btnOrderStatus;

    @FXML
    private Label lbPhone;

    @FXML
    private Label lbOrderDarte;

    @FXML
    private DatePicker fldReadyOrderDate;

    @FXML
    private Button btnDeleteOrderPosition;

    @FXML
    private Label lblrderCost;

    @FXML
    private Button btnUpdateOrderPosition;

    @FXML
    private Button btnStorageManager;

    @FXML
    private Button btnCancel;

    @FXML
    private TextArea areaNote;

    @FXML
    private Label lbName;

    @FXML
    private Button btnChooseOrderPosition;

    @FXML
    private Button btnChooseFromDB;

    @FXML
    private Label lbSurname;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbLastName;

    @FXML
    void pressChooseFromDB() {

    }

    @FXML
    void pressAddNewToDB() {

    }

    @FXML
    void pressDeleteOrderPosition() {

    }

    @FXML
    void pressUpdateOrderPosition() {

    }

    @FXML
    void pressChooseOrderPosition() {

    }

    @FXML
    void pressStorageManager() {

    }

    @FXML
    void pressOK() {

    }

    @FXML
    void pressCancel() {

    }

}

