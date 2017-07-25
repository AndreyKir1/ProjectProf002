package firma.fx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeleteVFormController {
    @FXML
    private static Stage stageRoot;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnOK;

    @FXML
    private Label lbtext;

    public static void setStageRoot(Stage stageRoot) {
        DeleteVFormController.stageRoot = stageRoot;
    }

    @FXML
    private void pressCancel(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void pressOK(){
        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.close();
        stageRoot.close();
    }

    @FXML
    private void pressUpdate(){

    }
}
