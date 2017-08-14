package firma.fx.controllers.storage_manager;

import firma.hibernate.entity.Order;
import firma.hibernate.entity.OrderPosition;
import firma.hibernate.entity.Product;
import firma.hibernate.service.orderPosition.OrderPositionService;
import firma.hibernate.service.product.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;

public class CheckOrderPosition {

    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private ProductService productService = context.getBean(ProductService.class);
    private OrderPositionService orderPositionService = context.getBean(OrderPositionService.class);

    private Order currentOrder;

    @FXML
    private static ObservableList<Product> storageProductsList;

    @FXML
    private static ObservableList<OrderPosition> orderProductsList;

    @FXML
    private TextField fldSearchProduct;

    @FXML
    private Button btnSearchProduct;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnOK;

    @FXML
    private TextArea fldNote;

    @FXML
    private TableView<OrderPosition> TableViewOrderProducts;

    @FXML
    private TableColumn<OrderPosition, String> columnOrderProduct;

    @FXML
    private TableColumn<OrderPosition, Integer> columnOrderProductAmount;

    @FXML
    private TableView<Product> TableViewStorageProducts;

    @FXML
    private TableColumn<Product, String> columnStorageProduct;

    @FXML
    private TableColumn<Product, Integer> columnStorageProductAmount;

    @FXML
    private void initialize() throws ParseException {
        storageProductsList = FXCollections.observableList(productService.getAll());
        columnStorageProduct.setCellValueFactory(new PropertyValueFactory<>("productName"));
        columnStorageProductAmount.setCellValueFactory(new PropertyValueFactory<>("amountInStorage"));
        TableViewStorageProducts.setItems(storageProductsList);

        currentOrder = StorageManagerWindow.getCurrentOrder();
        orderProductsList = FXCollections.observableList(orderPositionService.getOrderPositionByOrder(currentOrder));
        columnOrderProduct.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        columnOrderProductAmount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        TableViewOrderProducts.setItems(orderProductsList);

        fldNote.setText(currentOrder.getNoteAboutOrder());
    }



}
