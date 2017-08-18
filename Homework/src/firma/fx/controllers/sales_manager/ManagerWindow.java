package firma.fx.controllers.sales_manager;

import firma.fx.controllers.LoginController;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Comparator;
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
    public CheckBox chBoxInStorage;

    @FXML
    public CheckBox chBoxCanceled;

    @FXML
    public CheckBox chBoxReady;

    @FXML
    public CheckBox chBoxInSManager;

    @FXML
    public CheckBox chBoxDone;

    @FXML
    private Label lblOrderNumbers;

    @FXML
    private Label lbCurrentOrder;

    @FXML
    public TableView<Order> tableOrders;

    @FXML
    private TableColumn<Order, Client> columnOrderCustomer;

    @FXML
    private TableColumn<Order, Date> columnOrderDate;

    @FXML
    private TableColumn<Order, String> columnOrderNumber;

    @FXML
    private TableColumn<Order, Double> columnOrderCost;

    @FXML
    private TableColumn<Order, OrderStatus> columnOrderStatus;

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
    private Button btnUpdateOrder;

    @FXML
    private Button btnExit;

    public static Order getCurrentOrder() {
        return currentOrder;
    }

    @FXML
    private void initialize() {
        chBoxCanceled.setSelected(true);
        chBoxInStorage.setSelected(true);
        chBoxInSManager.setSelected(true);
        chBoxDone.setSelected(true);
        chBoxReady.setSelected(true);

        ordersList = FXCollections.observableArrayList();
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_BY_SMANAGER));
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
        ordersList.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getCreateOrder().compareTo(o2.getCreateOrder());
            }
        });

        columnOrderNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        columnOrderDate.setCellValueFactory(new PropertyValueFactory<>("createOrder"));
        columnOrderCustomer.setCellValueFactory(new PropertyValueFactory<>("client"));
        columnOrderCost.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        columnOrderStatus.setCellValueFactory(new PropertyValueFactory<>("orderConditions"));
        tableOrders.setItems(ordersList);

        orderPositionsList = FXCollections.observableArrayList();
        columnProductCode.setCellValueFactory(new PropertyValueFactory<>("positionCode"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        columnProductAmount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        columnProductCost.setCellValueFactory(new PropertyValueFactory<>("totalPriceOfProduct"));

        tableOrders.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    if (tableOrders.getSelectionModel().getSelectedItem() != null) {
                        lbCurrentOrder.setText("ЗАМОВЛЕННЯ #" + tableOrders.getSelectionModel().getSelectedItem().getNumber());
                        orderPositionsList = FXCollections.observableArrayList(orderPositionService.getOrderPositionByOrder(tableOrders.getSelectionModel().getSelectedItem()));
                        orderPositionsList.sort(new Comparator<OrderPosition>() {
                            @Override
                            public int compare(OrderPosition o1, OrderPosition o2) {
                                return o1.getPositionCode().compareTo(o2.getPositionCode());
                            }
                        });
                        tableOrderDetails.setItems(orderPositionsList);
                    }
                }
            }
        });

        tableOrders.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    if (tableOrders.getSelectionModel().getSelectedItem() != null) {
                        currentOrder = tableOrders.getSelectionModel().getSelectedItem();
                        try {
                            Stage stage = new Stage();
                            stage.setTitle("Інформація про замовлення");
                            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/OrderView.fxml"));
                            Scene scene = new Scene(root);
                            stage.setMinWidth(800);
                            stage.setMinHeight(570);
                            stage.setScene(scene);
                            stage.initModality(Modality.WINDOW_MODAL);
                            stage.initOwner(btnNewOrder.getScene().getWindow());
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        tableOrders.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) tableOrders.setEffect(null);
        });
    }

    @FXML
    private void showOrderView(){

    }

    @FXML
    public void showOrderPositions(Order order) {
        if (order != null) {
            orderPositionsList = FXCollections.observableArrayList(orderPositionService.getOrderPositionByOrder(order));
            tableOrderDetails.setItems(orderPositionsList);
        }
    }

    @FXML
    public void updateOrderPositions(Order order) {
        if (order != null) {
            orderPositionsList.clear();
            orderPositionsList.setAll(orderPositionService.getOrderPositionByOrder(order));
        }
    }

    @FXML
    void pressNewOrder() {
        CreateOrder.setManagerWindowController(this);
        try {
            Stage stage = new Stage();
            stage.setTitle("Створити нове замовлення");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/CreateOrder.fxml"));
            Scene scene = new Scene(root);
            stage.setMinWidth(800);
            stage.setMinHeight(820);
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
        if (tableOrders.getSelectionModel().getSelectedItem() != null) {
            currentOrder = orderService.read(tableOrders.getSelectionModel().getSelectedItem().getId());
            UpdateOrder.setCurrentOrderRow(tableOrders.getSelectionModel().getSelectedIndex());
            UpdateOrder.setManagerWindowController(this);

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
        }else{
            tableOrders.setEffect(new InnerShadow(5, Color.RED));
        }
    }

    @FXML
    public void updateOrdersList() {
        ordersList.clear();
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_BY_SMANAGER));
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
    }

    @FXML
    void pressExit() {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void BoxInSManager() {
        orderPositionsList.clear();
        lblOrderNumbers.setText("ЗАМОВЛЕННЯ");
        ChBoxSManagerAction();
    }

    @FXML
    public void BoxInStorage() {
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxStorageAction();
    }

    @FXML
    public void BoxReady() {
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxReadyAction();
    }

    @FXML
    public void BoxCanceled() {
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxCanceledAction();
    }

    @FXML
    public void BoxDone() {
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxDoneAction();
    }

    private void ChBoxSManagerAction() {
        if (chBoxInSManager.isSelected()) {
            ordersList.clear();
            ordersList.setAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_BY_SMANAGER));
            if (chBoxInStorage.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
            }
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
            }
        } else {
            ordersList.clear();
            if (chBoxInStorage.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
            }
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
            }
        }
    }

    private void ChBoxStorageAction() {
        if (chBoxInStorage.isSelected()) {
            ordersList.clear();
            ordersList.setAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
            lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
            if (chBoxInSManager.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_BY_SMANAGER));
            }
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
            }
        } else {
            ordersList.clear();
            if (chBoxInSManager.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_BY_SMANAGER));
            }
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
            }
        }
    }

    private void ChBoxReadyAction() {
        if (chBoxReady.isSelected()) {
            ordersList.clear();
            ordersList.setAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
            if (chBoxInStorage.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
            }
            if (chBoxInSManager.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_BY_SMANAGER));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
            }
        } else {
            ordersList.clear();
            if (chBoxInStorage.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
            }
            if (chBoxInSManager.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_BY_SMANAGER));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
            }
        }
    }

    private void ChBoxDoneAction() {
        if (chBoxDone.isSelected()) {
            ordersList.clear();
            ordersList.setAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
            if (chBoxInStorage.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
            }
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
            }
            if (chBoxInSManager.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_BY_SMANAGER));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
            }
        } else {
            ordersList.clear();
            if (chBoxInStorage.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
            }
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
            }
            if (chBoxInSManager.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_BY_SMANAGER));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
            }
        }
    }

    private void ChBoxCanceledAction() {
        if (chBoxCanceled.isSelected()) {
            ordersList.clear();
            ordersList.setAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
            if (chBoxInStorage.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
            }
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
            }
            if (chBoxInSManager.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_BY_SMANAGER));
            }
        } else {
            ordersList.clear();
            if (chBoxInStorage.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
            }
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
            }
            if (chBoxInSManager.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_BY_SMANAGER));
            }
        }
    }
}