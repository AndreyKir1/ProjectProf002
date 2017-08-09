package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CreateOrderPosition {

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
    private ChoiceBox<?> btnChooseProductCategory;

    @FXML
    private Label lblCountProduct;

    @FXML
    void pressSave() {

    }

    @FXML
    void pressCancel() {

    }

}

