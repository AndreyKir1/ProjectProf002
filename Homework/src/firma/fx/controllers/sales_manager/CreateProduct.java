package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Product;
import firma.hibernate.entity.ProductType;
import firma.hibernate.service.product.ProductService;
import firma.hibernate.service.productType.ProductTypeService;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;

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
        setClearInCTF(fldProductCode);
        setClearInCTF(fldProductName);
        setClearInCTF(fldProductAmount);
        setClearInCTF(fldProductPrice);

        fldProductCode.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldProductCode.setEffect(null);
        });

        fldProductName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldProductName.setEffect(null);
        });

        fldProductAmount.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldProductAmount.setEffect(null);
        });

        fldProductPrice.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) fldProductPrice.setEffect(null);
        });
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
        }else{
            fldProductCode.setEffect(new InnerShadow(5, Color.RED));
            fldProductName.setEffect(new InnerShadow(5, Color.RED));
            fldProductPrice.setEffect(new InnerShadow(5, Color.RED));
            fldProductAmount.setEffect(new InnerShadow(5, Color.RED));
        }

    }

    @FXML
    void pressCancel() {
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();
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

}
