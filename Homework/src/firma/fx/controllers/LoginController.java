package firma.fx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button btnCancel;

    @FXML
    private Label lbPassword;

    @FXML
    private PasswordField fldPassword;

    @FXML
    private Button btnOK;

    @FXML
    private Label lbLlogin;

    @FXML
    private TextField fldLogin;

    @FXML
    private Label lbIncorrectLogPass;

    @FXML
    private void pressCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void pressOK() {
        if (!fldLogin.getText().equals("0") && !fldPassword.getText().equals("0")) {
            try {
                Stage stage = new Stage();
                stage.setTitle("База даних працівників підприємства");
                stage.setMinHeight(600);
                stage.setMinWidth(800);
                Parent root = FXMLLoader.load(getClass().getResource("/view/AdminWindow.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                Stage curentStage = (Stage) btnOK.getScene().getWindow();
                curentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else lbIncorrectLogPass.setText("Некоректне введення логіну чи паролю!");

    }

}
