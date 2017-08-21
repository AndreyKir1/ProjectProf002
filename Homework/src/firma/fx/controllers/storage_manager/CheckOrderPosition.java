package firma.fx.controllers.storage_manager;

import firma.hibernate.entity.Order;
import firma.hibernate.entity.OrderPosition;
import firma.hibernate.entity.Product;
import firma.hibernate.service.order.OrderService;
import firma.hibernate.service.orderPosition.OrderPositionService;
import firma.hibernate.service.product.ProductService;
import firma.support.OrderStatus;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;

public class CheckOrderPosition {

    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private ProductService productService = context.getBean(ProductService.class);
    private OrderPositionService orderPositionService = context.getBean(OrderPositionService.class);
    private OrderService orderService = context.getBean(OrderService.class);

    private Order currentOrder;
    private static StorageManagerWindow smwController;

    @FXML
    private static ObservableList<Product> storageProductsList;

    @FXML
    private static ObservableList<OrderPosition> orderProductsList;

    @FXML
    private TableView<Product> TableViewStorageProducts;

    @FXML
    private TableColumn<Product, String> columnStorageProductCode;

    @FXML
    private TableColumn<Product, Integer> columnStorageProductAmount;

    @FXML
    private TableColumn<Product, String> columnStorageProductName;

    @FXML
    private Button btnOK;

    @FXML
    private TableView<OrderPosition> TableViewOrderPositions;

    @FXML
    private TableColumn<OrderPosition, Integer> columnOPAmount;

    @FXML
    private TableColumn<OrderPosition, String> columnOPProductCode;

    @FXML
    private TableColumn<OrderPosition, String> columnOPProductName;

    @FXML
    private Button btnCancel;

    @FXML
    private Label lbCurrentOrder;

    @FXML
    private TextArea fldNote;

    @FXML
    private CustomTextField fldSearchProduct;

    @FXML
    private Button btnSearchProduct;

    @FXML
    private ChoiceBox<OrderStatus> chOrderStatus;

    @FXML
    private void initialize() throws ParseException {
        currentOrder = StorageManagerWindow.getCurrentOrder();

        storageProductsList = FXCollections.observableList(productService.getAll());
        storageProductsList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getProductCode().compareTo(o2.getProductCode());
            }
        });
        columnStorageProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        columnStorageProductAmount.setCellValueFactory(new PropertyValueFactory<>("amountInStorage"));
        columnStorageProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        storageProductsList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getProductCode().compareTo(o2.getProductCode());
            }
        });
        TableViewStorageProducts.setItems(storageProductsList);

        orderProductsList = FXCollections.observableList(orderPositionService.getOrderPositionByOrder(currentOrder));
        columnOPProductName.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        columnOPProductCode.setCellValueFactory(new PropertyValueFactory<>("positionCode"));
        columnOPAmount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        orderProductsList.sort(new Comparator<OrderPosition>() {
            @Override
            public int compare(OrderPosition o1, OrderPosition o2) {
                return o1.getPositionCode().compareTo(o2.getPositionCode());
            }
        });
        TableViewOrderPositions.setItems(orderProductsList);

        fldNote.setText(currentOrder.getNoteAboutOrder());
        lbCurrentOrder.setText("ПОЗИЦІЇ ЗАМОВЛЕННЯ #" + currentOrder.getNumber());

        TableViewOrderPositions.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    if (TableViewOrderPositions.getSelectionModel().getSelectedItem() != null) {
                        long currentOposID = TableViewOrderPositions.getSelectionModel().getSelectedItem().getId();
                        Product currrentProduct = orderPositionService.read(currentOposID).getProduct();
                        storageProductsList.sort(new Comparator<Product>() {
                            @Override
                            public int compare(Product o1, Product o2) {
                                return o1.getProductCode().compareTo(o2.getProductCode());
                            }
                        });
                        storageProductsList.clear();
                        storageProductsList.setAll(productService.getByProductType(currrentProduct.getProductType()));
                        lbCurrentOrder.setText("ЗАМОВЛЕННЯ #" + currentOrder.getNumber());

                        TableViewStorageProducts.setItems(storageProductsList);

                        for (Product el : storageProductsList) {
                            if (el.getProductCode().equals(currrentProduct.getProductCode())) {
                                TableViewStorageProducts.getSelectionModel().select(el);
                                break;
                            }
                        }
                    }
                }
            }
        });

        chOrderStatus.getItems().setAll(OrderStatus.PROCESSED_BY_SMANAGER, OrderStatus.PROCESSED_IN_STOREGE);
        chOrderStatus.setValue(currentOrder.getOrderConditions());

        setClearInCTF(fldSearchProduct);

        Callback<TableView<OrderPosition>,TableRow<OrderPosition>> tableRowCallback = value -> {
            TableRow<OrderPosition> row = new TableRow<OrderPosition>() {
                @Override
                public void updateItem(OrderPosition orderPosition, boolean empty) {
                    super.updateItem(orderPosition, empty);
                    if (orderPosition == null)
                        return;
                    if (orderPosition.getProductAmount() > orderPosition.getProduct().getAmountInStorage()) {
                        setStyle("-fx-background-color: rgba(255,8,0,0.41);");
                    } else {
                        setStyle(null);
                    }
                }
            };
            return row;
        };
        TableViewOrderPositions.setRowFactory(tableRowCallback);
    }

    @FXML
    private void setClearInCTF(CustomTextField ctf) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, ctf, ctf.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pressSearchProduct() {
        if (fldSearchProduct.getText() != null && fldSearchProduct.getText().length() > 0) {
            List<Product> list = productService.getAll();
            storageProductsList.clear();
            for (Product el : list) {
                if (el.getProductCode().toLowerCase().contains(fldSearchProduct.getText()) || el.getProductName().toLowerCase().contains(fldSearchProduct.getText())) {
                    storageProductsList.add(el);
                }
            }
            if (storageProductsList.size() > 0) {
                fldSearchProduct.setText(null);
            }
        }
    }

    @FXML
    void pressOK() {
        currentOrder.setNoteAboutOrder(fldNote.getText());
        currentOrder.setOrderConditions(chOrderStatus.getValue());
        orderService.update(currentOrder);
        smwController.BoxInStorage();
        smwController.BoxNew();

        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.close();
    }

    @FXML
    void pressCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public static void setSmwController(StorageManagerWindow smwController) {
        CheckOrderPosition.smwController = smwController;
    }
}
