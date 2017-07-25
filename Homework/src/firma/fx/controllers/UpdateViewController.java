package firma.fx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateViewController {

    @FXML
    private static Stage stageRoot;

    @FXML
    private DatePicker fldDateOfStartWorking;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField fldSurName;

    @FXML
    private TextField fldPassword;

    @FXML
    private Button btnSave;

    @FXML
    private TextField fldName;

    @FXML
    private DatePicker fldBirthDay;

    @FXML
    private Label lbAge;

    @FXML
    private TextField fldRole;

    @FXML
    private TextField fldLogin;

    @FXML
    private TextField fldLastName;

    public static void setStageRoot(Stage stageRoot) {
        UpdateViewController.stageRoot = stageRoot;
    }

    @FXML
    private void pressSave(){
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
        stageRoot.show();
    }

    @FXML
    private void pressCancel(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        stageRoot.show();
    }
}
