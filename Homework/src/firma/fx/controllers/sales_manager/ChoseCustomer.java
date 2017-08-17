package firma.fx.controllers.sales_manager;

import firma.hibernate.entity.Client;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.service.client.ClientService;
import firma.hibernate.service.order.OrderService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Comparator;

public class ChoseCustomer {
    private static ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
    private static ClientService service = context.getBean(ClientService.class);
    private OrderService orderService = context.getBean(OrderService.class);

    private static CreateOrder createOrderController;
    private static UpdateOrder updateOrderController;

    private static Client currentClient;

    @FXML
    private static ObservableList customersList;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Client> tblView;

    @FXML
    private Label lblCustomerCount;

    @FXML
    private TableColumn<Client, String> columnLastName;

    @FXML
    private TableColumn<Client, String> columnEmail;

    @FXML
    private TableColumn<Client, String> columnPhone;

    @FXML
    private TableColumn<Client, String> columnSurName;

    @FXML
    private TableColumn<Client, String> columnName;

    @FXML
    private void initialize(){
        columnSurName.setCellValueFactory(new PropertyValueFactory<>("surname"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumder"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        customersList = FXCollections.observableArrayList(service.getAll());
        customersList.sort(new Comparator<Client>() {
            @Override
            public int compare(Client o1, Client o2) {
                return o1.getSurname().compareTo(o2.getSurname());
            }
        });
        tblView.setItems(customersList);

        updateCountLbl();

        tblView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {

                }
            }
        });

        customersList.addListener(new ListChangeListener<EmployeeFirm>() {
            @Override
            public void onChanged(Change<? extends EmployeeFirm> c) {
                updateCountLbl();
            }
        });

    }

    private void updateCountLbl() {
        lblCustomerCount.setText("Всього замовників в базі: " + customersList.size());
    }

    @FXML
    public static void updateCustomerList(){
        customersList.clear();
        customersList.setAll(service.getAll());
    }

    @FXML
    void pressAdd() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Додати нового клієнта");
            Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/AddCustomer.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnAdd.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pressUpdate() {
        if(tblView.getSelectionModel().getSelectedItem() != null){
            UpdateCustomer.setCurrentClient(tblView.getSelectionModel().getSelectedItem());
            try {
                Stage stage = new Stage();
                stage.setTitle("Оновити інформацію про клієнта");
                Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/UpdateCustomer.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnUpdate.getScene().getWindow());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void pressDelete() {
        if(tblView.getSelectionModel().getSelectedItem() != null){
            if(orderService.getOrdersByClient(tblView.getSelectionModel().getSelectedItem()).size() == 0){
                DeleteCustomerConfirm.setCurrentClient(tblView.getSelectionModel().getSelectedItem());
                try {
                    Stage stage = new Stage();
                    stage.setTitle("Видалити клієнта з бази");
                    Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/DeleteCustomerConfirm.fxml"));
                    Scene scene = new Scene(root);
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(btnDelete.getScene().getWindow());
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    Stage stage = new Stage();
                    stage.setTitle("Попередження!");
                    Parent root = FXMLLoader.load(getClass().getResource("/firma/view/sales_manager/DeleteCustomerImposible.fxml"));
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
    }

    @FXML
    void pressSave() {
        if(tblView.getSelectionModel().getSelectedItem() != null){
            currentClient = tblView.getSelectionModel().getSelectedItem();

            if(createOrderController != null){
                CreateOrder.setCurrentClient(currentClient);
                createOrderController.setCustomerData(currentClient.getSurname(), currentClient.getName(), currentClient.getLastName(),
                        currentClient.getPhoneNumder(), currentClient.getEmail());
            }
            if(updateOrderController != null){
                UpdateOrder.setCurrentClient(currentClient);
                updateOrderController.setCustomerData(currentClient.getSurname(), currentClient.getName(), currentClient.getLastName(),
                        currentClient.getPhoneNumder(), currentClient.getEmail());
            }
            createOrderController = null;
            updateOrderController = null;
            Stage current = (Stage) btnSave.getScene().getWindow();
            current.close();
        }
    }

    @FXML
    void pressCancel() {
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();
    }

    public static Client getCurrentClient() {
        return currentClient;
    }

    public static void setCreateOrderController(CreateOrder createOrderController) {
        ChoseCustomer.createOrderController = createOrderController;
    }

    public static void setUpdateOrderController(UpdateOrder updateOrderController) {
        ChoseCustomer.updateOrderController = updateOrderController;
    }

}

