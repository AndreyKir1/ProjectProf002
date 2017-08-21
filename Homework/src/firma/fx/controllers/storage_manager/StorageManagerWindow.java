package firma.fx.controllers.storage_manager;

import firma.fx.controllers.LoginController;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.entity.Order;
import firma.hibernate.entity.OrderPosition;
import firma.hibernate.service.employee.EmployeeService;
import firma.hibernate.service.order.OrderService;
import firma.hibernate.service.orderPosition.OrderPositionService;
import firma.support.EmployeeRols;
import firma.support.OrderStatus;
import firma.support.StyleRowFactory;
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
import javafx.util.Callback;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class StorageManagerWindow {

    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private OrderService orderService = context.getBean(OrderService.class);
    private OrderPositionService orderPositionService = context.getBean(OrderPositionService.class);
    private static Order currentOrder;

    @FXML
    private static ObservableList<Order> ordersList;

    @FXML
    private static ObservableList<OrderPosition> orderPositionsList;


    @FXML
    private Button btnCheckOrderPosition;

    @FXML
    private Button btnTakeOrder;

    @FXML
    private Button btnExit;

    @FXML
    private CheckBox chBoxInStorage;

    @FXML
    private CheckBox chBoxNew;

    @FXML
    private TableView<Order> tableOrders;

    @FXML
    private TableColumn<Order, String> columnOrderNumber;

    @FXML
    private TableColumn<Order, Date> columnOrderCreateDate;

    @FXML
    private TableColumn<Order, Double> columnOrderCost;

    @FXML
    private TableColumn<Order, OrderStatus> columnOrderStatus;

    @FXML
    private TableColumn<Order, String> columnOrderCustomer;

    @FXML
    private TableColumn<Order, Date> columnReadyDate;

    @FXML
    private TableColumn<Order, Date> columnSaledDate;

    @FXML
    private TableView<OrderPosition> TableViewPositions;

    @FXML
    private TableColumn<OrderPosition, String> columnProductCode;

    @FXML
    private TableColumn<OrderPosition, Integer> columnProductAmount;

    @FXML
    private TableColumn<OrderPosition, String> columnProductName;

    @FXML
    private TableColumn<OrderPosition, Double> columnProductCost;

    @FXML
    private Label lbCurrentOrder;

    @FXML
    private Button btnTest;

    @FXML
    private void pressTest() {
//        EmployeeService employeeService = context.getBean(EmployeeService.class);
//        List<Order> list = orderService.getOrdersByEmployee(employeeService.read(13L));
//        for (Order el:list){
//            System.out.println(el);
//        }
//        EmployeeService employeeService = context.getBean(EmployeeService.class);
//        List<Order> list = orderService.getOrdersWithoutCashier();
//        for (Order el : list) {
//            System.out.println(el.getNumber());
//        }
    }

    public static Order getCurrentOrder() {
        return currentOrder;
    }

    @FXML
    private void initialize() throws ParseException {
        chBoxInStorage.setSelected(true);
        chBoxNew.setSelected(true);

        ordersList = FXCollections.observableArrayList();
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
        ordersList.addAll(orderService.getNewOrdersStorManager());
        sortOrderList();
        tableOrders.setItems(ordersList);

        columnOrderNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        columnOrderCreateDate.setCellValueFactory(new PropertyValueFactory<>("createOrder"));
        columnOrderCost.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        columnOrderStatus.setCellValueFactory(new PropertyValueFactory<>("orderConditions"));
        columnOrderCustomer.setCellValueFactory(new PropertyValueFactory<>("client"));
        columnReadyDate.setCellValueFactory(new PropertyValueFactory<>("orderReady"));
        columnSaledDate.setCellValueFactory(new PropertyValueFactory<>("saledDate"));

        columnSaledDate.setVisible(false);
        columnReadyDate.setVisible(false);

        orderPositionsList = FXCollections.observableArrayList();
        columnProductCode.setCellValueFactory(new PropertyValueFactory<>("positionCode"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        columnProductAmount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        columnProductCost.setCellValueFactory(new PropertyValueFactory<>("totalPriceOfProduct"));
        sortOrderPositionsList();
        TableViewPositions.setItems(orderPositionsList);

        tableOrders.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    if (tableOrders.getSelectionModel().getSelectedItem() != null) {
                        long currentOrderID = tableOrders.getSelectionModel().getSelectedItem().getId();
                        currentOrder = orderService.read(currentOrderID);
                        lbCurrentOrder.setText("ЗАМОВЛЕННЯ #" + currentOrder.getNumber());
                        orderPositionsList = FXCollections.observableArrayList(orderPositionService.getOrderPositionByOrder(currentOrder));
                        sortOrderPositionsList();
                        TableViewPositions.setItems(orderPositionsList);
                        tableOrders.setEffect(null);
                    }
                }
            }
        });

        tableOrders.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) tableOrders.setEffect(null);
        });

        Callback<TableView<Order>,TableRow<Order>> tableRowCallback = value -> {
            TableRow<Order> row = new TableRow<Order>() {
                @Override
                public void updateItem(Order order, boolean empty) {
                    super.updateItem(order, empty);
                    if (order == null)
                        return;
                    if (!order.getStorageManager()) {
                        setStyle("-fx-background-color: rgba(91,255,15,0.41);");
                    } else {
                        setStyle(null);
                    }
                }
            };
            return row;
        };
        tableOrders.setRowFactory(tableRowCallback);
    }

    @FXML
    void pressTakeOrder() throws InterruptedException {//перевірити звязки
        if (tableOrders.getSelectionModel().getSelectedItem() != null) {
            if (tableOrders.getSelectionModel().getSelectedItem().getStorageManager()) {
                try {
                    Stage stage = new Stage();
                    stage.setTitle("Нагадування!");
                    Parent root = FXMLLoader.load(getClass().getResource("/firma/view/storage_manager/CantSatStorageManager.fxml"));
                    Scene scene = new Scene(root);
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(btnTakeOrder.getScene().getWindow());
                    stage.show();
                    TimeUnit.SECONDS.sleep(2);
                    stage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                long id = tableOrders.getSelectionModel().getSelectedItem().getId();
                currentOrder = orderService.read(id);
                Set<EmployeeFirm> setEmployee = currentOrder.getManagers();
                setEmployee.add(LoginController.getCurrentEmployee());
                currentOrder.setManagers(setEmployee);
                currentOrder.setStorageManager(true);
                orderService.update(currentOrder);
                try {
                    Stage stage = new Stage();
                    stage.setTitle("Вітаннячко :)");
                    Parent root = FXMLLoader.load(getClass().getResource("/firma/view/cashier/ConfirmIsOorderYours.fxml"));
                    Scene scene = new Scene(root);
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(btnTakeOrder.getScene().getWindow());
                    stage.show();
                    TimeUnit.SECONDS.sleep(2);
                    stage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ChBoxStorageAction();
                ChBoxNewAction();
                tableOrders.refresh();
            }
        } else {
            btnTakeOrder.requestFocus();
            tableOrders.setEffect(new InnerShadow(5, Color.RED));
        }
    }

    @FXML
    void pressCheckOrderPosition() throws InterruptedException {
        if (tableOrders.getSelectionModel().getSelectedItem() != null && tableOrders.getSelectionModel().getSelectedItem().getStorageManager()) {
            long id = tableOrders.getSelectionModel().getSelectedItem().getId();
            CheckOrderPosition.setSmwController(this);
            currentOrder = orderService.read(id);
            try {
                Stage stage = new Stage();
                stage.setTitle("");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/storage_manager/CheckOrderPosition.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnCheckOrderPosition.getScene().getWindow());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (tableOrders.getSelectionModel().getSelectedItem() == null) {
            btnCheckOrderPosition.requestFocus();
            tableOrders.setEffect(new InnerShadow(5, Color.RED));
        } else if (!tableOrders.getSelectionModel().getSelectedItem().getStorageManager()) {
            try {
                Stage stage = new Stage();
                stage.setTitle("Вітаннячко :)");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/storage_manager/CantChackOPs.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnTakeOrder.getScene().getWindow());
                stage.show();
                TimeUnit.SECONDS.sleep(2);
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void BoxInStorage() {
        tableOrders.refresh();
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxStorageAction();
    }

    @FXML
    void BoxNew() {
        tableOrders.refresh();
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxNewAction();
    }

    private void ChBoxStorageAction() {
        if (chBoxInStorage.isSelected()) {
            ordersList.clear();
            ordersList.setAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
            lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
            if (chBoxNew.isSelected()) {
                ordersList.addAll(orderService.getNewOrdersStorManager());
            }
        } else {
            ordersList.clear();
            if (chBoxNew.isSelected()) {
                ordersList.addAll(orderService.getNewOrdersStorManager());
            }
        }
        sortOrderList();
    }

    private void ChBoxNewAction() {
        if (chBoxNew.isSelected()) {
            ordersList.clear();
            ordersList.setAll(orderService.getNewOrdersStorManager());
            if (chBoxInStorage.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
            }
        } else {
            ordersList.clear();
            if (chBoxInStorage.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
            }
        }
        sortOrderList();
    }

    private void sortOrderList() {
        ordersList.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getCreateOrder().compareTo(o2.getCreateOrder());
            }
        });
    }

    private void sortOrderPositionsList() {
        orderPositionsList.sort(new Comparator<OrderPosition>() {
            @Override
            public int compare(OrderPosition o1, OrderPosition o2) {
                return o1.getPositionCode().compareTo(o2.getPositionCode());
            }
        });
    }

    @FXML
    void pressExit() {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }
}
