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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class StorageManagerWindow {

    private ApplicationContext context = new ClassPathXmlApplicationContext(new String [] {"firma/Config.xml"});
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
    private CheckBox chBoxReady;

    @FXML
    private CheckBox chBoxInSManager;

    @FXML
    private CheckBox chBoxCanceled;

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
    private void pressTest(){
//        EmployeeService employeeService = context.getBean(EmployeeService.class);
//        List<Order> list = orderService.getOrdersByEmployee(employeeService.read(13L));
//        for (Order el:list){
//            System.out.println(el);
//        }
    }

    public static Order getCurrentOrder(){
        return currentOrder;
    }

    @FXML
    private void initialize() throws ParseException {
        chBoxInSManager.setSelected(true);
        chBoxReady.setSelected(true);
        chBoxInStorage.setSelected(true);
        chBoxCanceled.setSelected(true);

        ordersList = FXCollections.observableArrayList();
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_IN_STOREGE));
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_BY_SMANAGER));
        tableOrders.setItems(ordersList);

        columnOrderNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        columnOrderCreateDate.setCellValueFactory(new PropertyValueFactory<>("createOrder"));
        columnOrderCost.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        columnOrderStatus.setCellValueFactory(new PropertyValueFactory<>("orderConditions"));
        columnOrderCustomer.setCellValueFactory(new PropertyValueFactory<>("client"));

        orderPositionsList = FXCollections.observableArrayList();
        columnProductCode.setCellValueFactory(new PropertyValueFactory<>("positionCode"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        columnProductAmount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        columnProductCost.setCellValueFactory(new PropertyValueFactory<>("totalPriceOfProduct"));
        TableViewPositions.setItems(orderPositionsList);

        tableOrders.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1){
                    if (tableOrders.getSelectionModel().getSelectedItem() != null) {
                        long currentOrderID = tableOrders.getSelectionModel().getSelectedItem().getId();
                        currentOrder = orderService.read(currentOrderID);
                        lbCurrentOrder.setText("ЗАМОВЛЕННЯ #" + currentOrder.getNumber());
                        orderPositionsList = FXCollections.observableArrayList(orderPositionService.getOrderPositionByOrder(currentOrder));
                        TableViewPositions.setItems(orderPositionsList);
                    }
                }
            }
        });
    }

//    @FXML
//    private void showCurrentOrderDetails() {
//        if (TableViewOrders.getSelectionModel().getSelectedItem() != null){
//            currentOrder = TableViewOrders.getSelectionModel().getSelectedItem();
//            Long id = currentOrder.getId();
//            currentOrder = orderService.read(id);
//
//            currentOrderPositionsList = FXCollections.observableList(orderPositionService.getOrderPositionByOrder(currentOrder));
//            columnProductName.setCellValueFactory(new PropertyValueFactory<>("positionName"));
//            columnProductAmount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
//            columnProductPrice.setCellValueFactory(new PropertyValueFactory<>("totalPriceOfProduct"));
//            TableViewPositions.setItems(currentOrderPositionsList);
//        }
//    }

    @FXML
    void pressTakeOrder() throws InterruptedException {//перевірити звязки
        if (tableOrders.getSelectionModel().getSelectedItem() != null) {
            long id = tableOrders.getSelectionModel().getSelectedItem().getId();
            currentOrder = orderService.read(id);
            Set<EmployeeFirm> setEmployee = currentOrder.getManagers();
            boolean storageExist = false;
            for (EmployeeFirm el : setEmployee) {
                if (el.getEmployeeRols().equals(EmployeeRols.STORAGE_MANAGER)) {
                    storageExist = true;
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
                    break;
                }
            }
            if (!storageExist) {
                setEmployee.add(LoginController.getCurrentEmployee());
                currentOrder.setManagers(setEmployee);
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
            }
        }
    }

    @FXML
    void pressCheckOrderPosition() {
        if (tableOrders.getSelectionModel().getSelectedItem() != null
                && (tableOrders.getSelectionModel().getSelectedItem().getOrderConditions().equals(OrderStatus.PROCESSED_BY_SMANAGER)
                || tableOrders.getSelectionModel().getSelectedItem().getOrderConditions().equals(OrderStatus.PROCESSED_IN_STOREGE))) {
            long id = tableOrders.getSelectionModel().getSelectedItem().getId();
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
        }
    }

    @FXML
    void BoxInSManager() {
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxSManagerAction();
    }

    @FXML
    void BoxInStorage() {
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxStorageAction();
    }

    @FXML
    void BoxReady() {
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxReadyAction();
    }

    @FXML
    void BoxCanceled() {
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxCanceledAction();
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
            if (chBoxInSManager.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.PROCESSED_BY_SMANAGER));
            }
        }
    }

    @FXML
    void pressExit() {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }
}
