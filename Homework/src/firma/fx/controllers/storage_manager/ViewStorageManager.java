package firma.fx.controllers.storage_manager;

import firma.hibernate.entity.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewStorageManager {
    private static Order orderController;

    @FXML
    private Label lbName;

    @FXML
    private Label lbSurname;

    @FXML
    private Label lbLastName;

    public static void setOrderController(Order orderController) {
        ViewStorageManager.orderController = orderController;
    }
}