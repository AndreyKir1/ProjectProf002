package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.entity.OrderPosition;
import firma.hibernate.entity.Product;
import firma.hibernate.entity.ProductType;
import firma.hibernate.service.product.ProductService;
import firma.hibernate.service.productType.ProductTypeService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UpdateOrderPosition {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private ProductService productService = context.getBean(ProductService.class);
    private ProductTypeService productTypeService = context.getBean(ProductTypeService.class);

    private static boolean createOrderOwner;
    private static boolean updatreOrdeOwner;

    @FXML
    private static OrderPosition currentOrderPosition;

    @FXML
    private static int indexCurrentOrderPosition;

    @FXML
    private ObservableList products;

    @FXML
    private TableView<Product> tableView;

    @FXML
    private TableColumn<Product, String> columnProductCode;

    @FXML
    private TableColumn<Product, Double> columnProductPrice;

    @FXML
    private TableColumn<Product, String> columnProductName;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private TextField fldAmount;

    @FXML
    private ChoiceBox<ProductType> btnChooseProductCategory;

    @FXML
    private Label lblCountProduct;

    @FXML
    private void initialize(){
        btnChooseProductCategory.getItems().setAll(productTypeService.getAll());
        products = FXCollections.observableArrayList(productService.getAll());
        columnProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        columnProductPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        tableView.setItems(products);

        updateCountLbl();

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {

                }
            }
        });

        products.addListener(new ListChangeListener<EmployeeFirm>() {
            @Override
            public void onChanged(Change<? extends EmployeeFirm> c) {
                updateCountLbl();
            }
        });

        btnChooseProductCategory.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                chooseProductCategory(productTypeService.getAll().get(newValue.intValue()));
            }
        });
    }

    @FXML
    private void updateCountLbl(){
        lblCountProduct.setText("Всього товарів в базі: " + products.size());
    }

    @FXML
    void pressOK() {
        if(fldAmount.getText() != null && fldAmount.getText().length() > 0 && tableView.getSelectionModel().getSelectedItem() != null){
            if(createOrderOwner){
                OrderPosition updated = new OrderPosition();
                updated.setProduct(tableView.getSelectionModel().getSelectedItem());
                updated.setProductAmount(Integer.parseInt(fldAmount.getText()));
                updated.setPositionCode(tableView.getSelectionModel().getSelectedItem().getProductCode());
                updated.setPositionName(tableView.getSelectionModel().getSelectedItem().getProductName());
                updated.setTotalPriceOfProduct(tableView.getSelectionModel().getSelectedItem().getProductPrice() *
                        Integer.parseInt(fldAmount.getText()));

                CreateOrder.setToOrderPosition(indexCurrentOrderPosition, updated);
                fldAmount.clear();
                Stage current = (Stage) btnSave.getScene().getWindow();
                current.close();
                createOrderOwner = false;
            }
            if(updatreOrdeOwner){
                OrderPosition updated = new OrderPosition();
                updated.setProduct(tableView.getSelectionModel().getSelectedItem());
                updated.setProductAmount(Integer.parseInt(fldAmount.getText()));
                updated.setPositionCode(tableView.getSelectionModel().getSelectedItem().getProductCode());
                updated.setPositionName(tableView.getSelectionModel().getSelectedItem().getProductName());
                updated.setTotalPriceOfProduct(tableView.getSelectionModel().getSelectedItem().getProductPrice() *
                        Integer.parseInt(fldAmount.getText()));

                UpdateOrder.setToOrderPosition(indexCurrentOrderPosition, updated);
                fldAmount.clear();
                Stage current = (Stage) btnSave.getScene().getWindow();
                current.close();
                updatreOrdeOwner = false;
            }

        }
    }

    @FXML
    private void chooseProductCategory(ProductType pt) {
        products.clear();
        products.setAll(productService.getByProductType(pt));
    }

    @FXML
    void pressCancel() {
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();
    }

    public static void setCurrentOrderPosition(OrderPosition currentOrderPosition) {
        UpdateOrderPosition.currentOrderPosition = currentOrderPosition;
    }

    public static void setIndexCurrentOrderPosition(int indexCurrentOrderPosition) {
        UpdateOrderPosition.indexCurrentOrderPosition = indexCurrentOrderPosition;
    }

    public static void setCreateOrderOwner(boolean createOrderOwner) {
        UpdateOrderPosition.createOrderOwner = createOrderOwner;
    }

    public static void setUpdatreOrdeOwner(boolean updatreOrdeOwner) {
        UpdateOrderPosition.updatreOrdeOwner = updatreOrdeOwner;
    }
}

