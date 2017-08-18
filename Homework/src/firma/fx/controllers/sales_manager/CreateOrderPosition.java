package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.*;
import firma.hibernate.service.order.OrderService;
import firma.hibernate.service.orderPosition.OrderPositionService;
import firma.hibernate.service.product.ProductService;
import firma.hibernate.service.productType.ProductTypeService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Comparator;

public class CreateOrderPosition {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private ProductService productService = context.getBean(ProductService.class);
    private ProductTypeService productTypeService = context.getBean(ProductTypeService.class);
    private OrderPositionService orderPositionService = context.getBean(OrderPositionService.class);
    private OrderService orderService = context.getBean(OrderService.class);

    private static boolean createOrderOwner;
    private static boolean updatreOrdeOwner;

    private static CreateOrder createOrderController;
    private static UpdateOrder updateOrderController;

    private static Order currentOrder;

    @FXML
    private static ObservableList products;

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
    private CustomTextField fldAmount;

    @FXML
    private ChoiceBox<ProductType> btnChooseProductCategory;

    @FXML
    private Label lblCountProduct;

    @FXML
    private Button btnAddNewProduct;

    @FXML
    private void initialize() {
        btnChooseProductCategory.getItems().setAll(productTypeService.getAll());
        products = FXCollections.observableArrayList(productService.getAll());
        columnProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        columnProductPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        products.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getProductCode().compareTo(o2.getProductCode());
            }
        });
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

        fldAmount.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldAmount.setEffect(null);
        });

        tableView.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) tableView.setEffect(null);
        });

        setClearInCTF(fldAmount);
    }

    @FXML
    private void updateCountLbl() {
        lblCountProduct.setText("Всього товарів в базі: " + products.size());
    }

    @FXML
    public void updateListProducts() {
        products.clear();
        products.setAll(productService.getAll());
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
    void pressSave() {
        if (fldAmount.getText() != null && tableView.getSelectionModel().getSelectedItem() != null && fldAmount.getText().length() > 0) {
            if(createOrderOwner){
                CreateOrder.addToorderPositions(new OrderPosition(Integer.parseInt(fldAmount.getText()), tableView.getSelectionModel().getSelectedItem()));
                fldAmount.clear();
                createOrderController.setOrderCostValue();
            }
            if(updatreOrdeOwner){
                UpdateOrder.addToorderPositions(new OrderPosition(Integer.parseInt(fldAmount.getText()), tableView.getSelectionModel().getSelectedItem()));
                fldAmount.clear();
                updateOrderController.setOrderCostValue();
            }

        }else{
            if(fldAmount.getText() == null || fldAmount.getText().length() == 0){
                fldAmount.setEffect(new InnerShadow(5, Color.RED));
            }
            if(tableView.getSelectionModel().getSelectedItem() == null){
                tableView.setEffect(new InnerShadow(5, Color.RED));
            }
        }
    }

    @FXML
    void pressCancel() {
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();
    }

    @FXML
    private void chooseProductCategory(ProductType pt) {
        products.clear();
        products.setAll(productService.getByProductType(pt));
    }

    @FXML
    private void AddNewProduct() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Додати новий товар");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/CreateProduct.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnAddNewProduct.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setCreateOrderOwner(boolean createOrderOwner) {
        CreateOrderPosition.createOrderOwner = createOrderOwner;
    }

    public static void setUpdatreOrdeOwner(boolean updatreOrdeOwner) {
        CreateOrderPosition.updatreOrdeOwner = updatreOrdeOwner;
    }

    public static void setCurrentOrder(Order currentOrder) {
        CreateOrderPosition.currentOrder = currentOrder;
    }

    public static void setCreateOrderController(CreateOrder createOrderController) {
        CreateOrderPosition.createOrderController = createOrderController;
    }

    public static void setUpdateOrderController(UpdateOrder updateOrderController) {
        CreateOrderPosition.updateOrderController = updateOrderController;
    }
}

