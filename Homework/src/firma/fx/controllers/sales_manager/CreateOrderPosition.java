package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.*;
import firma.hibernate.service.orderPosition.OrderPositionService;
import firma.hibernate.service.product.ProductService;
import firma.hibernate.service.productType.ProductTypeService;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class CreateOrderPosition {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private ProductService productService = context.getBean(ProductService.class);
    private ProductTypeService productTypeService = context.getBean(ProductTypeService.class);
    private OrderPositionService orderPositionService = context.getBean(OrderPositionService.class);

    private static boolean createOrderOwner;
    private static boolean updatreOrdeOwner;

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
    private TextField fldAmount;

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
    private void updateCountLbl() {
        lblCountProduct.setText("Всього товарів в базі: " + products.size());
    }

    @FXML
    public void updateListProducts() {
        products.clear();
        products.setAll(productService.getAll());
    }

    @FXML
    void pressSave() {
        if (fldAmount.getText() != null && tableView.getSelectionModel().getSelectedItem() != null && fldAmount.getText().length() > 0) {
            if(createOrderOwner){
                CreateOrder.addToorderPositions(new OrderPosition(Integer.parseInt(fldAmount.getText()), tableView.getSelectionModel().getSelectedItem()));
                fldAmount.clear();
            }
            if(updatreOrdeOwner){
                OrderPosition newOrderPosition = new OrderPosition(Integer.parseInt(fldAmount.getText()), tableView.getSelectionModel().getSelectedItem());
                newOrderPosition.setOrder(currentOrder);
                orderPositionService.create(newOrderPosition);
                new UpdateOrder().updateOrderPositions();
                fldAmount.clear();
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
}

