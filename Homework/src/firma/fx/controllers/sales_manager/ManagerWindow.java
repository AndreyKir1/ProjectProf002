package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Client;
import firma.hibernate.entity.Order;
import firma.hibernate.entity.OrderPosition;
import firma.hibernate.entity.Product;
import firma.hibernate.service.order.OrderService;
import firma.hibernate.service.orderPosition.OrderPositionService;
import firma.support.OrderStatus;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Date;

public class ManagerWindow {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private OrderService orderService = context.getBean(OrderService.class);
    private OrderPositionService orderPositionService = context.getBean(OrderPositionService.class);
    private static Order currentOrder;

    @FXML
    private static ObservableList<Order> ordersList;

    @FXML
    private static ObservableList<OrderPosition> orderPositionsList;

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
    private TableColumn<Order, Client> columnOrderCustomer;

    @FXML
    private TableColumn<Order, Date> columnOrderDate;

    @FXML
    private TableColumn<Order, String> columnOrderNumber;

    @FXML
    private TableColumn<Order, Double> columnOrderCost;

    @FXML
    private ChoiceBox<OrderStatus> btnOrderStatus;

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

    public static Order getCurrentOrder() {
        return currentOrder;
    }

    @FXML
    private void initialize() {
        ordersList = FXCollections.observableArrayList(orderService.getAll());
        columnOrderNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        columnOrderDate.setCellValueFactory(new PropertyValueFactory<>("createOrder"));
        columnOrderCustomer.setCellValueFactory(new PropertyValueFactory<>("client"));
        columnOrderCost.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        tableOrders.setItems(ordersList);

//        orderPositionsList = FXCollections.observableArrayList();
        columnProductCode.setCellValueFactory(new PropertyValueFactory<>("positionCode"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        columnProductAmount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        columnProductCost.setCellValueFactory(new PropertyValueFactory<>("totalPriceOfProduct"));
//        tableOrderDetails.setItems(orderPositionsList);

        btnOrderStatus.getItems().setAll(OrderStatus.values());

        tableOrders.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    showOrderPositions();
                }
            }
        });

        btnOrderStatus.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Order currentOrder = orderService.read(tableOrders.getSelectionModel().getSelectedItem().getId());
                OrderStatus currentConditions = OrderStatus.values()[newValue.intValue()];
                currentOrder.setOrderConditions(currentConditions);
                orderService.update(currentOrder);
                updateOrdersList();
            }
        });
    }

    @FXML
    private void showOrderPositions() {
        if(tableOrders.getSelectionModel().getSelectedItem() != null){
            orderPositionsList = FXCollections.observableArrayList(orderPositionService.getOrderPositionByOrder(tableOrders.getSelectionModel().getSelectedItem()));
            tableOrderDetails.setItems(orderPositionsList);
            btnOrderStatus.setValue(tableOrders.getSelectionModel().getSelectedItem().getOrderConditions());
        }
    }

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
        try {
            Stage stage = new Stage();
            stage.setTitle("Створити нове замовлення");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/CreateOrder.fxml"));
            Scene scene = new Scene(root);
            stage.setMinWidth(800);
            stage.setMinHeight(772);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnNewOrder.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pressUpdateOrder() {
        if(tableOrders.getSelectionModel().getSelectedItem() != null){
            currentOrder = orderService.read(tableOrders.getSelectionModel().getSelectedItem().getId());
            try {
                Stage stage = new Stage();
                stage.setTitle("Редагування замовлення");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/UpdateOrder.fxml"));
                Scene scene = new Scene(root);
                stage.setMinWidth(800);
                stage.setMinHeight(860);
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnUpdateOrder.getScene().getWindow());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void updateOrdersList(){
        ordersList.clear();
        ordersList.setAll(orderService.getAll());
    }

    @FXML
    void pressChangeStatus() {
        tableOrders.getSelectionModel().getSelectedItem().setOrderConditions(btnOrderStatus.getValue());
    }

    @FXML
    void pressExit() {
        System.exit(0);
    }

}