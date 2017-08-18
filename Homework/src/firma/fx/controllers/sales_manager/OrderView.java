package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Client;
import firma.hibernate.entity.Order;
import firma.hibernate.entity.OrderPosition;
import firma.hibernate.service.order.OrderService;
import firma.hibernate.service.orderPosition.OrderPositionService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Comparator;

public class OrderView {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private OrderService orderService = context.getBean(OrderService.class);
    private OrderPositionService orderPositionService = context.getBean(OrderPositionService.class);
    private static ObservableList<OrderPosition> orderPositionsList;
    private static Order currentOrder;

    @FXML
    private Button btnClose;

    @FXML
    private Label lbOrderNumber;

    @FXML
    private TableView<OrderPosition> tableViewOrdersPositions;

    @FXML
    private TableColumn<OrderPosition, Integer> columnAmount;

    @FXML
    private TableColumn<OrderPosition, String> columnProductCode;

    @FXML
    private TableColumn<OrderPosition, Double> columnCost;

    @FXML
    private TableColumn<OrderPosition, String> columnProductName;

    @FXML
    private Label lbCreateOrderData;

    @FXML
    private Label lbOrderCost;

    @FXML
    private Label lbPhone;

    @FXML
    private Label lbSaleOrderData;

    @FXML
    private Label lbOrderStatus;

    @FXML
    private Label lbName;

    @FXML
    private TextArea areaNotes;

    @FXML
    private Label lbSurname;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbOrderPosNumbers;

    @FXML
    private Label lbLastName;

    @FXML
    private void initialize(){
        columnProductCode.setCellValueFactory(new PropertyValueFactory<>("positionCode"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        columnCost.setCellValueFactory(new PropertyValueFactory<>("totalPriceOfProduct"));
        columnCost.setVisible(false);

        currentOrder = ManagerWindow.getCurrentOrder();
        currentOrder = orderService.read(currentOrder.getId());
        orderPositionsList = FXCollections.observableArrayList(orderPositionService.getOrderPositionByOrder(currentOrder));
        orderPositionsList.sort(new Comparator<OrderPosition>() {
            @Override
            public int compare(OrderPosition o1, OrderPosition o2) {
                return o1.getPositionCode().compareTo(o2.getPositionCode());
            }
        });
        tableViewOrdersPositions.setItems(orderPositionsList);

        lbOrderNumber.setText(currentOrder.getNumber());
        lbCreateOrderData.setText(new SimpleDateFormat("dd.MM.yyyy").format(currentOrder.getCreateOrder()));
        if(currentOrder.getSaledDate() != null){
            lbSaleOrderData.setText(new SimpleDateFormat("dd.MM.yyyy").format(currentOrder.getSaledDate()));
        }else{
            lbSaleOrderData.setText("Замовлення не оплачено");
        }
        lbOrderStatus.setText(currentOrder.getOrderConditions().toString());
        lbOrderCost.setText(currentOrder.getTotalPrice().toString());
        areaNotes.setText(currentOrder.getNoteAboutOrder());

        Client currentClient = currentOrder.getClient();
        lbSurname.setText(currentClient.getSurname());
        lbName.setText(currentClient.getName());
        lbLastName.setText(currentClient.getLastName());
        lbEmail.setText(currentClient.getEmail());
        lbPhone.setText(currentClient.getPhoneNumder());

        updateCountLbl();

        orderPositionsList.addListener(new ListChangeListener<OrderPosition>() {
            @Override
            public void onChanged(Change<? extends OrderPosition> c) {
                updateCountLbl();
            }
        });
    }

    @FXML
    void pressCancel() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void updateCountLbl(){
        lbOrderPosNumbers.setText("Всього позицій: " + orderPositionsList.size());
    }

}