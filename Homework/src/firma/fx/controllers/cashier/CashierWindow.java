package firma.fx.controllers.cashier;

import firma.fx.controllers.LoginController;
import firma.hibernate.entity.*;
import firma.hibernate.service.order.OrderService;
import firma.hibernate.service.orderPosition.OrderPositionService;
import firma.hibernate.service.product.ProductService;
import firma.support.EmployeeRols;
import firma.support.FirmBancAccount;
import firma.support.OrderStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CashierWindow {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private OrderService orderService = context.getBean(OrderService.class);
    private OrderPositionService orderPositionService = context.getBean(OrderPositionService.class);
    private ProductService productService = context.getBean(ProductService.class);
    private static Order currentOrder;

    @FXML
    private static ObservableList<Order> ordersList;

    @FXML
    private static ObservableList<OrderPosition> orderPositionsList;

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
    private CheckBox chBoxCanceled;

    @FXML
    private Button btnCanceledOrder;

    @FXML
    private Button btnTakeOrder;

    @FXML
    private Label lblOrderNumbers;

    @FXML
    private CheckBox chBoxReady;

    @FXML
    private Button btnSaleOrder;

    @FXML
    private CheckBox chBoxInSManager;

    @FXML
    private CheckBox chBoxDone;

    @FXML
    private Label lbCurrentOrder;

    @FXML
    private Button btnExit;

    @FXML
    private void initialize() {
        chBoxInSManager.setSelected(true);
        chBoxReady.setSelected(true);
        chBoxDone.setSelected(true);
        chBoxCanceled.setSelected(true);

        ordersList = FXCollections.observableArrayList();
        ordersList.addAll(orderService.getByStatus(OrderStatus.PROCESSED_BY_SMANAGER));
        ordersList.addAll(orderService.getByStatus(OrderStatus.READY));
        ordersList.addAll(orderService.getByStatus(OrderStatus.DONE));
        ordersList.addAll(orderService.getByStatus(OrderStatus.CANCELED));
        tableOrders.setItems(ordersList);

        columnOrderNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        columnOrderDate.setCellValueFactory(new PropertyValueFactory<>("createOrder"));
        columnOrderCustomer.setCellValueFactory(new PropertyValueFactory<>("client"));
        columnOrderCost.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        columnOrderStatus.setCellValueFactory(new PropertyValueFactory<>("orderConditions"));

        orderPositionsList = FXCollections.observableArrayList();
        columnProductCode.setCellValueFactory(new PropertyValueFactory<>("positionCode"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        columnProductAmount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        columnProductCost.setCellValueFactory(new PropertyValueFactory<>("totalPriceOfProduct"));
        tableOrderDetails.setItems(orderPositionsList);

        tableOrders.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    if (tableOrders.getSelectionModel().getSelectedItem() != null) {
                        long currentOrderID = tableOrders.getSelectionModel().getSelectedItem().getId();
                        currentOrder = orderService.read(currentOrderID);
                        lbCurrentOrder.setText("ЗАМОВЛЕННЯ #" + currentOrder.getNumber());
                        orderPositionsList = FXCollections.observableArrayList(orderPositionService.getOrderPositionByOrder(currentOrder));
                        tableOrderDetails.setItems(orderPositionsList);
                    }
                }
            }
        });
    }

    @FXML
    void BoxInSManager() {
        orderPositionsList.clear();
        lblOrderNumbers.setText("ЗАМОВЛЕННЯ");
        ChBoxSManagerAction();
    }

    @FXML
    void BoxReady() {
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxReadyAction();
    }

    @FXML
    void BoxDone() {
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxDoneAction();
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
            ordersList.setAll(orderService.getByStatus(OrderStatus.PROCESSED_BY_SMANAGER));
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.READY));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.DONE));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.CANCELED));
            }
        } else {
            ordersList.clear();
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.READY));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.DONE));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.CANCELED));
            }
        }
    }

    private void ChBoxReadyAction() {
        if (chBoxReady.isSelected()) {
            ordersList.clear();
            ordersList.setAll(orderService.getByStatus(OrderStatus.READY));
            if (chBoxInSManager.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.PROCESSED_BY_SMANAGER));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.DONE));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.CANCELED));
            }
        } else {
            ordersList.clear();
            if (chBoxInSManager.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.PROCESSED_BY_SMANAGER));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.DONE));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.CANCELED));
            }
        }
    }

    private void ChBoxDoneAction() {
        if (chBoxDone.isSelected()) {
            ordersList.clear();
            ordersList.setAll(orderService.getByStatus(OrderStatus.DONE));
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.READY));
            }
            if (chBoxInSManager.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.PROCESSED_BY_SMANAGER));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.CANCELED));
            }
        } else {
            ordersList.clear();
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.READY));
            }
            if (chBoxInSManager.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.PROCESSED_BY_SMANAGER));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.CANCELED));
            }
        }
    }

    private void ChBoxCanceledAction() {
        if (chBoxCanceled.isSelected()) {
            ordersList.clear();
            ordersList.setAll(orderService.getByStatus(OrderStatus.CANCELED));
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.READY));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.DONE));
            }
            if (chBoxInSManager.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.PROCESSED_BY_SMANAGER));
            }
        } else {
            ordersList.clear();
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.READY));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.DONE));
            }
            if (chBoxInSManager.isSelected()) {
                ordersList.addAll(orderService.getByStatus(OrderStatus.PROCESSED_BY_SMANAGER));
            }
        }
    }

    @FXML
    void pressSaleOrder() throws InterruptedException {
        if (tableOrders.getSelectionModel().getSelectedItem() != null
                && tableOrders.getSelectionModel().getSelectedItem().getOrderConditions().equals(OrderStatus.READY)
                && tableOrders.getSelectionModel().getSelectedItem().getSaledDate() == null) {
            long id = tableOrders.getSelectionModel().getSelectedItem().getId();
            currentOrder = orderService.read(id);
            currentOrder.setOrderConditions(OrderStatus.DONE);
            currentOrder.setSaledDate(new Date());
            orderService.update(currentOrder);
            try {
                Stage stage = new Stage();
                stage.setTitle("Вітаннячко :)");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/cashier/OrderSaled.fxml"));
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
            orderPositionsList.clear();
            ChBoxSManagerAction();
            ChBoxReadyAction();
            ChBoxDoneAction();
            ChBoxCanceledAction();
        }
        else{
            try {
                Stage stage = new Stage();
                stage.setTitle("Обережно!");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/cashier/OrderCantSaled.fxml"));
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
    void pressCanceledOrder() throws InterruptedException {
        if (tableOrders.getSelectionModel().getSelectedItem() != null
                && tableOrders.getSelectionModel().getSelectedItem().getOrderConditions().equals(OrderStatus.DONE)
                && tableOrders.getSelectionModel().getSelectedItem().getSaledDate() != null) {
            long id = tableOrders.getSelectionModel().getSelectedItem().getId();
            currentOrder = orderService.read(id);
//            if (currentOrder.getSaledDate().getTime() - new Date().getTime() > 14) {
//                System.out.println((new Date().getTime() - currentOrder.getSaledDate().getTime()) / (1000 * 60 * 60 * 24));
//            }
            List<OrderPosition> tempList = orderPositionService.getOrderPositionByOrder(currentOrder);
            Product temp;
            double cost = 0.0;
            for (OrderPosition el:tempList){
                temp = productService.read(el.getProduct().getId());
                temp.setAmountInStorage(temp.getAmountInStorage() +  el.getProductAmount());
                productService.update(temp);
                cost += el.getTotalPriceOfProduct();
            }
            FirmBancAccount.setBankAccount(FirmBancAccount.getBankAccount() - cost);
            currentOrder.setOrderConditions(OrderStatus.CANCELED);
            orderService.update(currentOrder);
            ChBoxSManagerAction();
            ChBoxReadyAction();
            ChBoxDoneAction();
            ChBoxCanceledAction();
            orderPositionsList.clear();
            try {
                Stage stage = new Stage();
                stage.setTitle("Обережно!");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/cashier/CancelOrder.fxml"));
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
    void pressTakeOrder() throws InterruptedException {
        if (tableOrders.getSelectionModel().getSelectedItem() != null) {
            long id = tableOrders.getSelectionModel().getSelectedItem().getId();
            currentOrder = orderService.read(id);
            Set<EmployeeFirm> setEmployee = currentOrder.getManagers();
            boolean cashierExist = false;
            for (EmployeeFirm el : setEmployee) {
                if (el.getEmployeeRols().equals(EmployeeRols.CASHIER)) {
                    cashierExist = true;
                    try {
                        Stage stage = new Stage();
                        stage.setTitle("Нагадування!");
                        Parent root = FXMLLoader.load(getClass().getResource("/firma/view/cashier/CantSatCashier.fxml"));
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
            if (!cashierExist) {
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
    void pressExit() {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }


}
