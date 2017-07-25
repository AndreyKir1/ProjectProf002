package firma.fx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewController {

    @FXML
    private Button btnCancel;

    @FXML
    private Label lbPassword;

    @FXML
    private Label lbName;

    @FXML
    private Label lbDayOfStartWorking;

    @FXML
    private Button btnDelete;

    @FXML
    private Label lbBirthDay;

    @FXML
    private Label lbAge;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lbRole;

    @FXML
    private Label lbSurname;

    @FXML
    private Label lbLogin;

    @FXML
    private Label lbLastName;


    @FXML
    public void prerssCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void pressUpdate() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Редагування даних");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/UpdateViewForm.fxml"));
            Scene scene = new Scene(root);
            stage.setMinWidth(315);
            stage.setMinHeight(500);
            stage.setMaxWidth(415);
            stage.setMaxHeight(500);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnDelete.getScene().getWindow());
            Stage stageRoot = (Stage) btnUpdate.getScene().getWindow();
            stageRoot.hide();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressDelete() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Видалення співробітника");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/DeleteViewForm.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnDelete.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
