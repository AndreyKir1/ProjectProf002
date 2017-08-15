package firma.fx.controllers.sales_manager;

import firma.fx.controllers.LoginController;
import firma.hibernate.entity.Client;
import firma.hibernate.entity.EmployeeFirm;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class CreateOrder {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private OrderService orderService = context.getBean(OrderService.class);
    private OrderPositionService orderPositionService = context.getBean(OrderPositionService.class);

    private static ManagerWindow managerWindowController;
    private static int currentOrderRow;

    @FXML
    private static Client currentClient;

    @FXML
    private static ObservableList<OrderPosition> orderPositions;

    @FXML
    private TableView<OrderPosition> tableViewOrdersPositions;

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
    private Button btnUpdateOrderPosition;

    @FXML
    private Button btnDeleteOrderPosition;

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
    private Button btnCreateOrderPosition;

    @FXML
    private Label lbLastName;

    @FXML
    private void initialize() {
        orderPositions = FXCollections.observableArrayList();
        columnProductCode.setCellValueFactory(new PropertyValueFactory<>("positionCode"));
        columnProductNane.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        columnCost.setCellValueFactory(new PropertyValueFactory<>("totalPriceOfProduct"));

        tableViewOrdersPositions.setItems(orderPositions);

        btnOrderStatus.getItems().setAll(OrderStatus.values());

        tableViewOrdersPositions.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {

                }
            }
        });
    }

    @FXML
    void pressGenerateOrderNumber() {

    }

    @FXML
    void AddWithDBase() {
        ChoseCustomer.setCreateOrderController(this);
        try {
            Stage stage = new Stage();
            stage.setTitle("Вибрати замовника з бази");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/ChoseCustomer.fxml"));
            Scene scene = new Scene(root);
            stage.setMinWidth(600);
            stage.setMinHeight(400);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnAddWithDBase.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pressAddNewCustomer() {
//        try {
//            Stage stage = new Stage();
//            stage.setTitle("Вибрати замовника з бази");
//            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/AddCustomer.fxml"));
//            Scene scene = new Scene(root);
//            stage.setResizable(false);
//            stage.setScene(scene);
//            stage.initModality(Modality.WINDOW_MODAL);
//            stage.initOwner(btnAddNewCustomer.getScene().getWindow());
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void pressCreateOrderPosition() {
        CreateOrderPosition.setCreateOrderOwner(true);
        CreateOrderPosition.setUpdatreOrdeOwner(false);
        CreateOrderPosition.setCreateOrderController(this);
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
    void pressUpdateOrderPosition() {
        if (tableViewOrdersPositions.getSelectionModel().getSelectedItem() != null) {
            UpdateOrderPosition.setCreateOrderOwner(true);
            UpdateOrderPosition.setUpdatreOrdeOwner(false);
            UpdateOrderPosition.setCreateOrderController(this);
            UpdateOrderPosition.setCurrentOrderPosition(tableViewOrdersPositions.getSelectionModel().getSelectedItem());
            UpdateOrderPosition.setIndexCurrentOrderPosition(orderPositions.indexOf(tableViewOrdersPositions.getSelectionModel().getSelectedItem()));
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
    void pressDeleteOrderPosition() {
        if (tableViewOrdersPositions.getSelectionModel().getSelectedItem() != null) {
            DeletePositionInOrder.setCreateOrderOwner(true);
            DeletePositionInOrder.setUpdatreOrdeOwner(false);
            DeletePositionInOrder.setCreateOrderController(this);
            DeletePositionInOrder.setCurrentOrderPosition(tableViewOrdersPositions.getSelectionModel().getSelectedItem());
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
    void pressSave() {
        if(fldOrderNumber.getText() != null && fldOrderNumber.getText().length() > 0 && fldOrderDate.getValue() != null && lbSurname.getText() != null &&
                lbName.getText() != null && lbLastName.getText() != null && lbEmail.getText() != null && lbPhone.getText() != null && btnOrderStatus.getValue() != null &&
                orderPositions.size() > 0){
            Order order = new Order(fldOrderNumber.getText(),  Date.from(fldOrderDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), btnOrderStatus.getValue(),
                    areaNotes.getText(), currentClient);
            order.setTotalPrice(Double.parseDouble(lbOrderCost.getText().substring(26)));
            Set<EmployeeFirm> setEmployee = new LinkedHashSet<>();
            setEmployee.add(LoginController.getCurrentEmployee());
            order.setManagers(setEmployee);
            orderService.create(order);
            for(OrderPosition el:orderPositions){
                el.setOrder(order);
                orderPositionService.create(el);
            }
            new ManagerWindow().updateOrdersList();
            currentClient = null;
            managerWindowController.chBoxInSManager.setSelected(true);
            managerWindowController.chBoxInStorage.setSelected(true);
            managerWindowController.chBoxReady.setSelected(true);
            managerWindowController.chBoxDone.setSelected(true);
            managerWindowController.chBoxCanceled.setSelected(true);
            managerWindowController.tableOrders.getSelectionModel().selectLast();
            managerWindowController.updateOrderPositions(order);

            Stage current = (Stage) btnSave.getScene().getWindow();
            current.close();
        }
    }

    @FXML
    void pressCancel() {
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();
    }

    @FXML
    void setCustomerData(String surName, String name, String lastName, String phoneNumber, String email){
        lbSurname.setText(surName);
        lbName.setText(name);
        lbLastName.setText(lastName);
        lbPhone.setText(phoneNumber);
        lbEmail.setText(email);
    }

    @FXML
    public static void addToorderPositions(OrderPosition orderPosition){
        orderPositions.add(orderPosition);
    }

    @FXML
    public static void setToOrderPosition(int index, OrderPosition orderPosition){
        orderPositions.set(index, orderPosition);
    }

    @FXML
    public static void deleteOrderPositions(OrderPosition orderPosition){
        orderPositions.remove(orderPosition);
    }

    public static void setCurrentClient(Client currentClient) {
        CreateOrder.currentClient = currentClient;
    }

    void setOrderCostValue() {
        Double value = 0.0;
        if(orderPositions.size() > 0){
            for (OrderPosition el:orderPositions ){
                value += el.getTotalPriceOfProduct();
            }
        }
        lbOrderCost.setText("Вартість замовлення, грн: " + value.toString());
    }

    public static void setManagerWindowController(ManagerWindow managerWindowController) {
        CreateOrder.managerWindowController = managerWindowController;
    }

    public static void setCurrentOrderRow(int currentOrderRow) {
        CreateOrder.currentOrderRow = currentOrderRow;
    }
}

