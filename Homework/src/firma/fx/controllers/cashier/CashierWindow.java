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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Comparator;
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
    private TableColumn<Order, Date> columnReadyDate;

    @FXML
    private TableColumn<Order, Date> columnSaledDate;

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
    private CheckBox chBoxNew;

    @FXML
    private Button btnSaleOrder;

    @FXML
    private CheckBox chBoxDone;

    @FXML
    private Label lbCurrentOrder;

    @FXML
    private Button btnExit;

    @FXML
    private void initialize() {
        chBoxReady.setSelected(true);
        chBoxDone.setSelected(true);
        chBoxCanceled.setSelected(true);
        chBoxNew.setSelected(true);

        ordersList = FXCollections.observableArrayList();
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
        ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
        ordersList.addAll(orderService.getNewOrdersCashier());
        orderListSort();
        tableOrders.setItems(ordersList);

        columnOrderNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        columnOrderDate.setCellValueFactory(new PropertyValueFactory<>("createOrder"));
        columnOrderCustomer.setCellValueFactory(new PropertyValueFactory<>("client"));
        columnOrderCost.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        columnOrderStatus.setCellValueFactory(new PropertyValueFactory<>("orderConditions"));
        columnReadyDate.setCellValueFactory(new PropertyValueFactory<>("orderReady"));
        columnSaledDate.setCellValueFactory(new PropertyValueFactory<>("saledDate"));

        columnSaledDate.setVisible(false);
        columnReadyDate.setVisible(false);

        orderPositionsList = FXCollections.observableArrayList();
        columnProductCode.setCellValueFactory(new PropertyValueFactory<>("positionCode"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        columnProductAmount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        columnProductCost.setCellValueFactory(new PropertyValueFactory<>("totalPriceOfProduct"));
        orderPositionsList.sort(new Comparator<OrderPosition>() {
            @Override
            public int compare(OrderPosition o1, OrderPosition o2) {
                return o1.getPositionCode().compareTo(o2.getPositionCode());
            }
        });
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

        Callback<TableView<Order>,TableRow<Order>> tableRowCallback = value -> {
            TableRow<Order> row = new TableRow<Order>() {
                @Override
                public void updateItem(Order order, boolean empty) {
                    super.updateItem(order, empty);
                    if (order == null)
                        return;
                    if (!order.getCashier()) {
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

    private void orderListSort() {
        ordersList.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getCreateOrder().compareTo(o2.getCreateOrder());
            }
        });
    }

    @FXML
    void BoxReady() {
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxReadyAction();
        tableOrders.refresh();
    }

    @FXML
    void BoxDone() {
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxDoneAction();
        tableOrders.refresh();
    }

    @FXML
    void BoxCanceled() {
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxCanceledAction();
        tableOrders.refresh();
    }

    @FXML
    void BoxNew() {
        orderPositionsList.clear();
        lbCurrentOrder.setText("ЗАМОВЛЕННЯ");
        ChBoxNewAction();
        tableOrders.refresh();
    }

    private void ChBoxReadyAction() {
        if (chBoxReady.isSelected()) {
            ordersList.clear();
            ordersList.setAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
            }
            if (chBoxNew.isSelected()) {
                ordersList.addAll(orderService.getNewOrdersCashier());
            }
        } else {
            ordersList.clear();
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
            }
            if (chBoxNew.isSelected()) {
                ordersList.addAll(orderService.getNewOrdersCashier());
            }
        }
        orderListSort();
    }

    private void ChBoxDoneAction() {
        if (chBoxDone.isSelected()) {
            ordersList.clear();
            ordersList.setAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
            }
            if (chBoxNew.isSelected()) {
                ordersList.addAll(orderService.getNewOrdersCashier());
            }
        } else {
            ordersList.clear();
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
            }
            if (chBoxCanceled.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
            }
            if (chBoxNew.isSelected()) {
                ordersList.addAll(orderService.getNewOrdersCashier());
            }
        }
        orderListSort();
    }

    private void ChBoxCanceledAction() {
        if (chBoxCanceled.isSelected()) {
            ordersList.clear();
            ordersList.setAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.CANCELED));
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
            }
            if (chBoxNew.isSelected()) {
                ordersList.addAll(orderService.getNewOrdersCashier());
            }
        } else {
            ordersList.clear();
            if (chBoxReady.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.READY));
            }
            if (chBoxDone.isSelected()) {
                ordersList.addAll(orderService.getOrdersByEmployee(LoginController.getCurrentEmployee(), OrderStatus.DONE));
            }
            if (chBoxNew.isSelected()) {
                ordersList.addAll(orderService.getNewOrdersCashier());
            }
        }
        orderListSort();
    }

    private void ChBoxNewAction() {
        if (chBoxNew.isSelected()) {
            ordersList.clear();
            ordersList.addAll(orderService.getNewOrdersCashier());
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
        orderListSort();
    }

    @FXML
    void pressSaleOrder() throws InterruptedException {
        if (tableOrders.getSelectionModel().getSelectedItem() != null
                && tableOrders.getSelectionModel().getSelectedItem().getOrderConditions().equals(OrderStatus.READY)
                && tableOrders.getSelectionModel().getSelectedItem().getSaledDate() == null && tableOrders.getSelectionModel().getSelectedItem().getCashier() == true) {
            int row = tableOrders.getSelectionModel().getSelectedIndex();
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
            ChBoxReadyAction();
            ChBoxDoneAction();
            ChBoxCanceledAction();
            tableOrders.getSelectionModel().select(row);
        } else if (tableOrders.getSelectionModel().getSelectedItem().getCashier() == false) {
            try {
                Stage stage = new Stage();
                stage.setTitle("Обережно!");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/cashier/NotSetCashier.fxml"));
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
        } else if (tableOrders.getSelectionModel().getSelectedItem().getOrderConditions().equals(OrderStatus.DONE) ||
                tableOrders.getSelectionModel().getSelectedItem().getOrderConditions().equals(OrderStatus.CANCELED)) {
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
                && tableOrders.getSelectionModel().getSelectedItem().getSaledDate() != null && tableOrders.getSelectionModel().getSelectedItem().getCashier()) {
            int row = tableOrders.getSelectionModel().getSelectedIndex();
            long id = tableOrders.getSelectionModel().getSelectedItem().getId();
            currentOrder = orderService.read(id);
            List<OrderPosition> tempList = orderPositionService.getOrderPositionByOrder(currentOrder);
            Product temp;
            double cost = 0.0;
            for (OrderPosition el : tempList) {
                temp = productService.read(el.getProduct().getId());
                temp.setAmountInStorage(temp.getAmountInStorage() + el.getProductAmount());
                productService.update(temp);
                cost += el.getTotalPriceOfProduct();
            }
            FirmBancAccount firmBancAccount = new FirmBancAccount();
            double cost1 = FirmBancAccount.getBankAccount();
            FirmBancAccount.setBankAccount(cost1 - cost);
            currentOrder.setOrderConditions(OrderStatus.CANCELED);
            orderService.update(currentOrder);
            ChBoxReadyAction();
            ChBoxDoneAction();
            ChBoxCanceledAction();

            tableOrders.getSelectionModel().select(row);
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
        } else {
            try {
                Stage stage = new Stage();
                stage.setTitle("Обережно!");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/cashier/CantCancelOrder.fxml"));
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
            if (tableOrders.getSelectionModel().getSelectedItem().getCashier() == true) {
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
            } else if (tableOrders.getSelectionModel().getSelectedItem().getCashier() == false){
                int row = tableOrders.getSelectionModel().getSelectedIndex();
                long id = tableOrders.getSelectionModel().getSelectedItem().getId();
                currentOrder = orderService.read(id);
                Set<EmployeeFirm> setEmployee = currentOrder.getManagers();
                setEmployee.add(LoginController.getCurrentEmployee());
                currentOrder.setManagers(setEmployee);
                currentOrder.setCashier(true);
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
                ChBoxReadyAction();
                ChBoxDoneAction();
                ChBoxCanceledAction();
                tableOrders.refresh();
                tableOrders.getSelectionModel().select(row);
            }
        }
    }

    @FXML
    void pressExit() {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }


}
