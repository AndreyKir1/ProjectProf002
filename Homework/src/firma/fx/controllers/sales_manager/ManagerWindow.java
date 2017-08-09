package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Order;
import firma.hibernate.entity.OrderPosition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Date;

public class ManagerWindow {

    @FXML
    private Button btnNewOrder;

    @FXML
    private CheckBox chBoxNew;

    @FXML
    private Label lblOrderNumbers;

    @FXML
    private CheckBox chBoxAll;

    @FXML
    private TableView<Order> tableOrders;

    @FXML
    private TableColumn<Order, String> columnOrderCustomer;

    @FXML
    private TableColumn<Order, Date> columnOrderDate;

    @FXML
    private TableColumn<Order, String> columnOrderNumber;

    @FXML
    private TableColumn<Order, Double> columnOrderCost;

    @FXML
    private Button btnChangeStatus;

    @FXML
    private TableView<OrderPosition> tableOrderDetails;

    @FXML
    private TableColumn<OrderPosition, String> columnProductCode;

    @FXML
    private TableColumn<OrderPosition, Double> columnProductCost;

    @FXML
    private TableColumn<OrderPosition, Integer> columnProductAmount;

    @FXML
    private TableColumn<OrderPosition, String> columnProductName;

    @FXML
    private CheckBox chBoxFinished;

    @FXML
    private CheckBox chBoxWorked;

    @FXML
    private Button btnUpdateOrder;

    @FXML
    private Button btnExit;

    @FXML
    void boxNewOrders() {

    }

    @FXML
    void boxInWork() {

    }

    @FXML
    void boxDone() {

    }

    @FXML
    void boxAll() {

    }

    @FXML
    void pressNewOrder() {

    }

    @FXML
    void pressUpdateOrder() {

    }

    @FXML
    void pressChangeStatus() {

    }

    @FXML
    void pressExit() {

    }

}