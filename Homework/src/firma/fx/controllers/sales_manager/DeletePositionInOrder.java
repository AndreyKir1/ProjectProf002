package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Order;
import firma.hibernate.entity.OrderPosition;
import firma.hibernate.service.order.OrderService;
import firma.hibernate.service.orderPosition.OrderPositionService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DeletePositionInOrder {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private OrderPositionService orderPositionService = context.getBean(OrderPositionService.class);

    private static boolean createOrderOwner;
    private static boolean updatreOrdeOwner;

    private static CreateOrder createOrderController;
    private static UpdateOrder updateOrderController;

    private static Order currentOrder;

    @FXML
    private static OrderPosition currentOrderPosition;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnOK;

    @FXML
    private Label lbtext;

    @FXML
    private Label lbPosition;

    @FXML
    private void initialize(){
        lbPosition.setText(currentOrderPosition.getProduct().getProductCode() + " " + currentOrderPosition.getProduct().getProductName() + " " + currentOrderPosition.getProductAmount() + " одиниць?");
    }

    @FXML
    void pressOK() {
        if (createOrderOwner) {
            CreateOrder.deleteOrderPositions(currentOrderPosition);
            createOrderController.setOrderCostValue();
            Stage current = (Stage) btnOK.getScene().getWindow();
            current.close();
        }
        if(updatreOrdeOwner){
            UpdateOrder.deleteOrderPositions(currentOrderPosition);
            updateOrderController.setOrderCostValue();
            Stage current = (Stage) btnOK.getScene().getWindow();
            current.close();
        }

    }

    @FXML
    void pressCancel() {
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();
    }

    public static void setCurrentOrderPosition(OrderPosition currentOrderPosition) {
        DeletePositionInOrder.currentOrderPosition = currentOrderPosition;
    }

    public static void setCreateOrderOwner(boolean createOrderOwner) {
        DeletePositionInOrder.createOrderOwner = createOrderOwner;
    }

    public static void setUpdatreOrdeOwner(boolean updatreOrdeOwner) {
        DeletePositionInOrder.updatreOrdeOwner = updatreOrdeOwner;
    }

    public static void setCurrentOrder(Order currentOrder) {
        DeletePositionInOrder.currentOrder = currentOrder;
    }

    public static void setCreateOrderController(CreateOrder createOrderController) {
        DeletePositionInOrder.createOrderController = createOrderController;
    }

    public static void setUpdateOrderController(UpdateOrder updateOrderController) {
        DeletePositionInOrder.updateOrderController = updateOrderController;
    }
}

