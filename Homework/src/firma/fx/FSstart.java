package firma.fx;

import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.service.AccountService;
import firma.hibernate.service.EmployeeService;
import firma.support.EmployeeRols;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FSstart extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Авторизація користувача");
        Parent root = FXMLLoader.load(getClass().getResource("/firma/view/Login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch();

    }
}
