package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Order;
import firma.support.OrderStatus;
import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.ChoiceBox;
        import javafx.scene.control.DatePicker;
        import javafx.scene.control.Label;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.TextArea;
        import javafx.scene.control.TextField;

public class CreateOrder {

    @FXML
    private TableView<Order> TableViewOrders;

    @FXML
    private TableColumn<Order, Integer> columnAmount;

    @FXML
    private TableColumn<Order, String> columnProductCode;

    @FXML
    private TableColumn<Order, String> columnProductNane;

    @FXML
    private TableColumn<Order, Double> columnCost;

    @FXML
    private Button btnAddNewCustomer;

    @FXML
    private ChoiceBox<OrderStatus> btnOrderStatus;

    @FXML
    private Label lbOrderCost;

    @FXML
    private Label lbPhone;

    @FXML
    private Button btnUpdateProductPosition;

    @FXML
    private Button btnDeleteProductPosition;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnGenerateOrderNumber;

    @FXML
    private Button btnSave;

    @FXML
    private Label lbName;

    @FXML
    private DatePicker fldOrderDate;

    @FXML
    private TextArea areaNotes;

    @FXML
    private Button btnAddWithDBase;

    @FXML
    private Label lbSurname;

    @FXML
    private Label lbEmail;

    @FXML
    private TextField fldOrderNumber;

    @FXML
    private Button btnChooseProduct;

    @FXML
    private Label lbLastName;

    @FXML
    void pressGenerateOrderNumber() {

    }

    @FXML
    void AddWithDBase() {

    }

    @FXML
    void pressAddNewCustomer() {

    }

    @FXML
    void pressChooseProduct() {

    }

    @FXML
    void pressUpdateProductPosition() {

    }

    @FXML
    void pressDeleteProductPosition() {

    }

    @FXML
    void pressSave() {

    }

    @FXML
    void pressCancel() {

    }

}

