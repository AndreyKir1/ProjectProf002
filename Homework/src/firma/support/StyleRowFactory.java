package firma.support;

import firma.hibernate.entity.Order;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class StyleRowFactory implements Callback<TableView<Order>, TableRow<Order>> {
    @Override
    public TableRow<Order> call(TableView<Order> param) {
        return new TableRow<Order>() {
            @Override
            protected void updateItem(Order order, boolean b) {
                super.updateItem(order, b);
                if (order == null)
                    return;
                if (!order.getStorageManager()) {
                    setStyle("-fx-background-color: rgba(91,255,15,0.41);");
                } else {
                    setStyle(null);
                }

            }
        };
    }
}
