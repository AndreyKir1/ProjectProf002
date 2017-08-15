package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Client;
import firma.hibernate.entity.Order;
import firma.hibernate.entity.OrderPosition;
import firma.hibernate.service.order.OrderService;
import firma.hibernate.service.orderPosition.OrderPositionService;
import firma.support.OrderStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class UpdateOrder {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private OrderPositionService orderPositionService = context.getBean(OrderPositionService.class);
    private OrderService orderService = context.getBean(OrderService.class);
    private Order currentOrder;

    private static ManagerWindow managerWindowController;
    private static int currentOrderRow;

    @FXML
    private static Client currentClient;

    @FXML
    private static ObservableList<OrderPosition> orderPositions;

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
    private Button btnCreateOrderPosition;

    @FXML
    private Button btnChooseFromDB;

    @FXML
    private Label lbSurname;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbLastName;

    @FXML
    private void initialize() {
        columnProductCode.setCellValueFactory(new PropertyValueFactory<>("positionCode"));
        columnProductNane.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        columnCost.setCellValueFactory(new PropertyValueFactory<>("totalPriceOfProduct"));

        currentOrder = ManagerWindow.getCurrentOrder();
        lbOrderNumber.setText(currentOrder.getNumber());
        lbOrderDarte.setText(new SimpleDateFormat("dd.MM.yyyy").format(currentOrder.getCreateOrder()));
        orderPositions = FXCollections.observableArrayList(orderPositionService.getOrderPositionByOrder(currentOrder));
        tableOrderPosition.setItems(orderPositions);

        btnOrderStatus.getItems().setAll(OrderStatus.values());
        btnOrderStatus.setValue(currentOrder.getOrderConditions());

        Client temp = currentOrder.getClient();
        lbSurname.setText(temp.getSurname());
        lbName.setText(temp.getName());
        lbLastName.setText(temp.getLastName());
        lbEmail.setText(temp.getEmail());
        lbPhone.setText(temp.getEmail());

        lblrderCost.setText("Вартість замовлення, грн: " + currentOrder.getTotalPrice());

        if (currentOrder.getOrderReady() != null) {
            fldReadyOrderDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(currentOrder.getOrderReady())));
        }

        tableOrderPosition.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {

                }
            }
        });
    }

    @FXML
    void pressChooseFromDB() {
        ChoseCustomer.setUpdateOrderController(this);
        try {
            Stage stage = new Stage();
            stage.setTitle("Вибрати замовника з бази");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/ChoseCustomer.fxml"));
            Scene scene = new Scene(root);
            stage.setMinWidth(600);
            stage.setMinHeight(400);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnChooseFromDB.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pressAddNewToDB() {
//        try {
//            Stage stage = new Stage();
//            stage.setTitle("Вибрати замовника з бази");
//            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/AddCustomer.fxml"));
//            Scene scene = new Scene(root);
//            stage.setResizable(false);
//            stage.setScene(scene);
//            stage.initModality(Modality.WINDOW_MODAL);
//            stage.initOwner(btnAddNewToDB.getScene().getWindow());
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void pressDeleteOrderPosition() {
        if (tableOrderPosition.getSelectionModel().getSelectedItem() != null) {
            DeletePositionInOrder.setCreateOrderOwner(false);
            DeletePositionInOrder.setUpdatreOrdeOwner(true);
            DeletePositionInOrder.setUpdateOrderController(this);
            DeletePositionInOrder.setCurrentOrderPosition(tableOrderPosition.getSelectionModel().getSelectedItem());
            DeletePositionInOrder.setCurrentOrder(currentOrder);
            try {
                Stage stage = new Stage();
                stage.setTitle("Видалити позицію в замовленні");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/DeletePositionInOrder.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnDeleteOrderPosition.getScene().getWindow());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void pressUpdateOrderPosition() {
        if (tableOrderPosition.getSelectionModel().getSelectedItem() != null) {
            UpdateOrderPosition.setCreateOrderOwner(false);
            UpdateOrderPosition.setUpdatreOrdeOwner(true);
            UpdateOrderPosition.setUpdateOrderController(this);
            UpdateOrderPosition.setCurrentOrderPosition(tableOrderPosition.getSelectionModel().getSelectedItem());
            UpdateOrderPosition.setIndexCurrentOrderPosition(orderPositions.indexOf(tableOrderPosition.getSelectionModel().getSelectedItem()));

            try {
                Stage stage = new Stage();
                stage.setTitle("Редагувати позицію в замовленні");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/UpdateOrderPosition.fxml"));
                Scene scene = new Scene(root);
                stage.setMinWidth(825);
                stage.setMinHeight(484);
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnUpdateOrderPosition.getScene().getWindow());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void pressCreateOrderPosition() {
        CreateOrderPosition.setCreateOrderOwner(false);
        CreateOrderPosition.setUpdatreOrdeOwner(true);
        CreateOrderPosition.setCurrentOrder(currentOrder);
        CreateOrderPosition.setUpdateOrderController(this);
        try {
            Stage stage = new Stage();
            stage.setTitle("Обрати товар");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/CreateOrderPosition.fxml"));
            Scene scene = new Scene(root);
            stage.setMinWidth(825);
            stage.setMinHeight(484);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnCreateOrderPosition.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pressStorageManager() {
        if (btnStorageManager.getText() != null) {
            try {
                Stage stage = new Stage();
                stage.setTitle("Інформація про кладовщика");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/storage_manager/ViewStorageManager.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnStorageManager.getScene().getWindow());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void pressOK() {
//        if (orderPositions.size() == 0 && orderPositionService.getOrderPositionByOrder(currentOrder).size() == 0) {
        if (orderPositions.size() == 0 && orderPositionService.getOrderPositionByOrder(currentOrder).size() > 0) {
            List<OrderPosition> list = orderPositionService.getOrderPositionByOrder(currentOrder);
            for (OrderPosition el : list) {
                orderPositionService.delete(el);
            }
            currentOrder.setTotalPrice(0.0);
        } else if (orderPositions.size() > 0 && orderPositionService.getOrderPositionByOrder(currentOrder).size() == 0) {
            for (OrderPosition el : orderPositions) {
                el.setOrder(currentOrder);
                orderPositionService.create(el);
            }
            currentOrder.setTotalPrice(Double.parseDouble(lblrderCost.getText().substring(26)));
        } else if (orderPositions.size() > 0 && orderPositionService.getOrderPositionByOrder(currentOrder).size() > 0) {
            List<OrderPosition> list = orderPositionService.getOrderPositionByOrder(currentOrder);
            for (OrderPosition el : list) {
                orderPositionService.delete(el);
            }
            for (OrderPosition el : orderPositions) {
                el.setOrder(currentOrder);
                orderPositionService.create(el);
            }
            currentOrder.setTotalPrice(Double.parseDouble(lblrderCost.getText().substring(26)));
        }
        if (currentClient != null) {
            currentOrder.setClient(currentClient);
            currentClient = null;
        }

        if (areaNote.getText() != null && areaNote.getText().length() > 0) {
            currentOrder.setNoteAboutOrder(areaNote.getText());
        }

        if (fldReadyOrderDate.getValue() != null) {
            currentOrder.setOrderReady(Date.from(fldReadyOrderDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        currentOrder.setOrderConditions(btnOrderStatus.getValue());
        orderService.update(currentOrder);
        new ManagerWindow().updateOrdersList();

        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.close();
        managerWindowController.BoxInSManager();
        managerWindowController.BoxInStorage();
        managerWindowController.BoxReady();
        managerWindowController.BoxDone();
        managerWindowController.BoxCanceled();
        managerWindowController.updateOrderPositions(currentOrder);
        managerWindowController.tableOrders.getSelectionModel().select(currentOrderRow);
    }

    @FXML
    void pressCancel() {
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();
    }

    @FXML
    public static void setToOrderPosition(int index, OrderPosition orderPosition) {
        orderPositions.set(index, orderPosition);
    }

    @FXML
    public static void addToorderPositions(OrderPosition orderPosition) {
        orderPositions.add(orderPosition);
    }

    @FXML
    public static void deleteOrderPositions(OrderPosition orderPosition) {
        orderPositions.remove(orderPosition);
    }

    @FXML
    public void updateOrderPositions() {
        orderPositions.clear();
        orderPositions.setAll(orderPositionService.getOrderPositionByOrder(currentOrder));
    }

    @FXML
    void setCustomerData(String surName, String name, String lastName, String phoneNumber, String email) {
        lbSurname.setText(surName);
        lbName.setText(name);
        lbLastName.setText(lastName);
        lbPhone.setText(phoneNumber);
        lbEmail.setText(email);
    }

    public static void setCurrentClient(Client currentClient) {
        UpdateOrder.currentClient = currentClient;
    }

    @FXML
    void setOrderCostValue() {
        Double value = 0.0;
        if(orderPositions.size() > 0){
            for (OrderPosition el:orderPositions ){
                value += el.getTotalPriceOfProduct();
            }
        }
        lblrderCost.setText("Вартість замовлення, грн: " + value.toString());
    }

    public static void setManagerWindowController(ManagerWindow managerWindowController) {
        UpdateOrder.managerWindowController = managerWindowController;
    }

    public static void setCurrentOrderRow(int currentOrderRow) {
        UpdateOrder.currentOrderRow = currentOrderRow;
    }
}

