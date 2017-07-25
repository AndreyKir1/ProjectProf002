package firma.fx.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeleteController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnOK;

    @FXML
    private Label lbtext;

    @FXML
    private void pressOK(){
        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void pressCancel(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}