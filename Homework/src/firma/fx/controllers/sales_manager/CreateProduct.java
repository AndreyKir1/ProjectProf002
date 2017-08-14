package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Product;
import firma.hibernate.entity.ProductType;
import firma.hibernate.service.product.ProductService;
import firma.hibernate.service.productType.ProductTypeService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CreateProduct {
    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private ProductService productService = context.getBean(ProductService.class);
    private ProductTypeService productTypeService = context.getBean(ProductTypeService.class);

    @FXML
    private Button btnCancel;

    @FXML
    private ChoiceBox<ProductType> btnProductCategory;

    @FXML
    private CustomTextField fldProductPrice;

    @FXML
    private Button btnSave;

    @FXML
    private CustomTextField fldProductName;

    @FXML
    private CustomTextField fldProductCode;

    @FXML
    private CustomTextField fldProductAmount;

    @FXML
    private void initialize(){
        btnProductCategory.getItems().setAll(productTypeService.getAll());
    }

    @FXML
    void pressSave() {
        if(fldProductCode.getText() != null && fldProductName.getText() != null && fldProductPrice.getText() != null && fldProductAmount.getText() != null && btnProductCategory.getValue() != null &&
                fldProductCode.getText().length() > 0 && fldProductName.getText().length() > 0 && fldProductPrice.getText().length() > 0 && fldProductAmount.getText().length() > 0){
            productService.create(new Product(fldProductCode.getText(), fldProductName.getText(), Double.parseDouble(fldProductPrice.getText()),
                    Integer.parseInt(fldProductAmount.getText()), btnProductCategory.getValue()));
            new CreateOrderPosition().updateListProducts();
            Stage current = (Stage) btnSave.getScene().getWindow();
            current.close();
        }

    }

    @FXML
    void pressCancel() {
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();
    }

}
