package controller;

import domain.Customer;
import domain.SinglyLinkedList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class AddSortedCustomerController
{
    @javafx.fxml.FXML
    private TextField idTextField;
    @javafx.fxml.FXML
    private TextField phoneTextField;
    @javafx.fxml.FXML
    private TextField emailTextField;
    @javafx.fxml.FXML
    private TextField nameTextField;
    @javafx.fxml.FXML
    private TextField ageTextField;
    @javafx.fxml.FXML
    private BorderPane bp;
    private SinglyLinkedList customerList;
    Alert alert;

    @javafx.fxml.FXML
    public void initialize() {
        this.alert = util.UtilityFX.alert("Adding Customers", "");
        //cargamos la lista global
        this.customerList = util.Utility.getCustomerList();
    }

    @javafx.fxml.FXML
    public void addSortedOnAction(ActionEvent actionEvent) {
        if(isValid()){
            Customer newCustomer = new Customer(
                    Integer.parseInt(this.idTextField.getText()),
                    this.nameTextField.getText(),
                    Integer.parseInt(this.ageTextField.getText()),
                    this.phoneTextField.getText(),
                    this.emailTextField.getText()
            );
            this.customerList.addInSortedList(newCustomer);
            //tambien se debe settear la lista global
            util.Utility.setCustomerList(this.customerList);

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("The customer was added");
        }else{
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Please fill in all fields");
        }
        alert.showAndWait();
    }

    @javafx.fxml.FXML


    private boolean isValid() {
        return !this.idTextField.getText().isEmpty();
    }

    @javafx.fxml.FXML
    public void cleanOnAction(ActionEvent actionEvent) {
        // Limpiar todos los campos de entrada de texto
        idTextField.clear();
        phoneTextField.clear();
        emailTextField.clear();
        nameTextField.clear();
        ageTextField.clear();
    }


    @javafx.fxml.FXML
    public void closeOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("ucr.lab.HelloApplication", "customer.fxml", bp);
    }
}
