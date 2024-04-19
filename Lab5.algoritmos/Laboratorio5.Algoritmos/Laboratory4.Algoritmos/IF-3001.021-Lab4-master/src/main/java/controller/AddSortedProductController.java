package controller;

import domain.DoublyLinkedList;
import domain.Product;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class AddSortedProductController {
    @javafx.fxml.FXML
    private TextField idTextField;
    @javafx.fxml.FXML
    private TextField currentTextField;
    @javafx.fxml.FXML
    private TextField nameTextField;
    @javafx.fxml.FXML
    private TextField priceTextField;
    @javafx.fxml.FXML
    private BorderPane bp;

    private DoublyLinkedList productList;
    Alert alert;

    @javafx.fxml.FXML
    public void initialize() {
        this.alert = util.UtilityFX.alert("Adding Products", "");
        //cargamos la lista global
        this.productList = util.Utility.getProductList();
    }

    private boolean isValid() {
        return !this.idTextField.getText().isEmpty() &&
                !this.nameTextField.getText().isEmpty() &&
                !this.priceTextField.getText().isEmpty() &&
                !this.currentTextField.getText().isEmpty();
    }



    @javafx.fxml.FXML
    public void addSortedOnAction(ActionEvent actionEvent) {
        if (isValid()) {
            Product newProduct = new Product(
                    Integer.parseInt(this.idTextField.getText()),
                    this.nameTextField.getText(),
                    Double.parseDouble(this.priceTextField.getText()),
                    Integer.parseInt(this.currentTextField.getText())
            );
            this.productList.addInSortedList(newProduct);
            // También se debe settear la lista global
            util.Utility.setProductList(this.productList);

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("The product was added");
        } else {
            // Mostrar mensaje de alerta si falta información
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Please fill in all fields");
        }
        alert.showAndWait();
    }


    @javafx.fxml.FXML
    public void cleanOnAction(ActionEvent actionEvent) {
        //Limpia los campos de texto
        this.idTextField.clear();
        this.currentTextField.clear();
        this.nameTextField.clear();
        this.priceTextField.clear();
    }
    @javafx.fxml.FXML
    public void closeOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("ucr.lab.HelloApplication", "product.fxml", bp);
    }
}
