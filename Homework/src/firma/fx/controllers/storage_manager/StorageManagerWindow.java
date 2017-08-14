package firma.fx.controllers.storage_manager;

import firma.hibernate.entity.Order;
import firma.hibernate.entity.OrderPosition;
import firma.hibernate.service.order.OrderService;
import firma.hibernate.service.orderPosition.OrderPositionService;
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

public class StorageManagerWindow {

    private ApplicationContext context = new ClassPathXmlApplicationContext(new String [] {"firma/Config.xml"});
    private OrderService orderService = context.getBean(OrderService.class);
    private OrderPositionService orderPositionService = context.getBean(OrderPositionService.class);

    @FXML
    private static ObservableList<Order> ordersList;

    @FXML
    private static ObservableList<OrderPosition> currentOrderPositionsList;

    private static Order currentOrder;


    @FXML
    private Button btnNewOrder;

    @FXML
    private CheckBox chBoxFinished;

    @FXML
    private CheckBox chBoxWorked;

    @FXML
    private CheckBox chBoxNew;

    @FXML
    private CheckBox chBoxAll;

    @FXML
    private ChoiceBox<?> choiseBoxOrderStatus;

    @FXML
    private Button btnExit;

    @FXML
    private TableView<Order> TableViewOrders;

    @FXML
    private TableColumn<Order, String> columnOrderNumber;

    @FXML
    private TableColumn<Order, Date> columnOrderCreateDate;

    @FXML
    private TableColumn<Order, Double> columnOrderTotalPrice;

    @FXML
    private TableColumn<Order, OrderStatus> columnOrderStatus;

    @FXML
    private TableView<OrderPosition> TableViewPositions;

    @FXML
    private TableColumn<OrderPosition, String> columnProductName;

    @FXML
    private TableColumn<OrderPosition, String> columnProductAmount;

    @FXML
    private TableColumn<OrderPosition, String> columnProductPrice;

    @FXML
    private void initialize() throws ParseException {
        ordersList = FXCollections.observableList(orderService.getAll());
        columnOrderNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        columnOrderCreateDate.setCellValueFactory(new PropertyValueFactory<>("createOrder"));
        columnOrderTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        columnOrderStatus.setCellValueFactory(new PropertyValueFactory<>("orderConditions"));

        TableViewOrders.setItems(ordersList);
        TableViewOrders.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1){
                    showCurrentOrderDetails();
                }
            }
        });
    }

    @FXML
    private void showCurrentOrderDetails() {
        if (TableViewOrders.getSelectionModel().getSelectedItem() != null){
            currentOrder = TableViewOrders.getSelectionModel().getSelectedItem();
            Long id = currentOrder.getId();
            currentOrder = orderService.read(id);

            currentOrderPositionsList = FXCollections.observableList(orderPositionService.getOrderPositionByOrder(currentOrder));
            columnProductName.setCellValueFactory(new PropertyValueFactory<>("positionName"));
            columnProductAmount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
            columnProductPrice.setCellValueFactory(new PropertyValueFactory<>("totalPriceOfProduct"));
            TableViewPositions.setItems(currentOrderPositionsList);
        }
    }



    @FXML
    private void pressShowDetails(ActionEvent event) throws ParseException {
        try {
            Stage stage = new Stage();
            stage.setTitle("");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/storage_manager/CheckOrderPosition.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnNewOrder.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
