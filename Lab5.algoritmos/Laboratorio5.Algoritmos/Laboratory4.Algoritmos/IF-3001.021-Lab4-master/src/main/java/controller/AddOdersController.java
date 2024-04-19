package controller;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

public class AddOdersController
{
    @javafx.fxml.FXML
    private TextField IDtextField;
    @javafx.fxml.FXML
    private TextField quantityTestField;
    @javafx.fxml.FXML
    private TextField UPriceText;
    @javafx.fxml.FXML
    private TextField totalPriceText;
    @javafx.fxml.FXML
    private ChoiceBox choiceBox;
    @javafx.fxml.FXML
    private DatePicker  datePicker;
    @javafx.fxml.FXML
    private TextField idTextField;
    @javafx.fxml.FXML
    private Button addButton;
    @javafx.fxml.FXML
    private Button cleanButton;
    @javafx.fxml.FXML
    private Button closeButton;
    @javafx.fxml.FXML
    private BorderPane bp;
    Alert alert;

    private CircularLinkedList orderList;
    private DoublyLinkedList productList;

    private Product selectedProduct;

    @javafx.fxml.FXML
    public void initialize() {
        //cargamos las listas globales
        this.orderList = util.Utility.getOrderList();
        if (orderList == null) {
            orderList = new CircularLinkedList(); // o cualquier otro método que uses para inicializarla
        }
        this.productList = util.Utility.getProductList();
        this.alert = util.UtilityFX.alert("Order..", "Add Order...");
        //seteo el id con el valor maximo encontrado en la lista +1
        this.idTextField.setText(String.valueOf(getMaxId()));

        this.choiceBox.setItems(getProductList());

        // Manejar cambios en el ChoiceBox
        choiceBox.setOnAction(event -> {
            int selectedIndex = choiceBox.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                try {
                    selectedProduct = (Product) productList.getNode(selectedIndex + 1).data;
                    updatePrices();
                } catch (ListException ex) {
                    ex.printStackTrace(); // Manejar la excepción adecuadamente
                }
            }
        });

        // Manejar cambios en la cantidad
        quantityTestField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                updatePrices();
            }
        });
    }



    private ObservableList<List<String>>  getProductList() {
        ObservableList<List<String>> data = FXCollections.observableArrayList();

        if (productList != null && !productList.isEmpty()) {
            try {
                for (int i = 1; i <= productList.size(); i++) {
                    Product product = (Product) productList.getNode(i).data;
                    List<String> arrayList = new ArrayList<>();
                    arrayList.add(String.valueOf(product.getId()));//envuelvo con un string valor entero
                    arrayList.add(String.valueOf(product.getName()));
                    arrayList.add(String.valueOf(product.getCurrentStock()));
                    arrayList.add(String.valueOf(product.getPrice()));
                    data.add(arrayList);
                }

            } catch (ListException ex) {
                alert.setContentText("There was an error in the process");
            }
        }
        return data;
    }

    private void updatePrices() {
        if (selectedProduct != null && !quantityTestField.getText().isEmpty()) {
            int quantity = Integer.parseInt(quantityTestField.getText());
            double unitPrice = selectedProduct.getPrice();
            double totalPrice = unitPrice * quantity;

            UPriceText.setText(String.valueOf(unitPrice));
            totalPriceText.setText(String.valueOf(totalPrice));
        }
    }

    private int getMaxId() {
        int maxId = 0;
        if (orderList==null) {
            return 1; // Si la lista de órdenes es nula, devuelve 1 como ID inicial
        }
        try {
            for (int i = 1; i <= orderList.size(); i++) {
                Order order = (Order) orderList.getNode(i).data;
                maxId = Math.max(maxId, order.getId());
            }
        } catch (ListException ex) {
            alert.setContentText(ex.getMessage());
        }
        return maxId + 1; // +1 para la siguiente orden
    }



    @javafx.fxml.FXML
    public void addAction(ActionEvent actionEvent) {
        if (isValid()) {
            // Obtener el nombre del producto seleccionado del ChoiceBox
            String productName = getSelectedProductName();

            // Verificar si el nombre del producto es válido
            if (productName != null) {
                // Obtener el producto correspondiente al nombre
                Product selectedProduct = getProductByName(productName);

                // Verificar si el producto seleccionado es válido
                if (selectedProduct != null) {
                    try {
                        // Crear una nueva orden con los detalles proporcionados por el usuario
                        Order newOrder = new Order(
                                Integer.parseInt(this.idTextField.getText()), // ID de la orden
                                this.datePicker.getValue(), // Fecha de la orden
                                selectedProduct.getId(), // ID del producto
                                Integer.parseInt(this.quantityTestField.getText()) // Cantidad
                        );

                        // Agregar la nueva orden a la lista de órdenes
                        this.orderList.add(newOrder);

                        // Actualizar la lista global de órdenes
                        util.Utility.setOrderList(this.orderList);

                        // Mostrar un mensaje de éxito
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setContentText("The order was added successfully.");
                    } catch (NumberFormatException ex) {
                        // Manejar errores de conversión de números
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText("Invalid number format. Please enter a valid number.");
                    }
                } else {
                    // El producto seleccionado no fue encontrado
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Selected product not found.");
                }
            } else {
                // No se seleccionó ningún producto
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Please select a product.");
            }
        } else {
            // Algunos campos requeridos no están llenos
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Please fill in all fields.");
        }

        // Mostrar la alerta al usuario
        alert.showAndWait();
    }
    // Método para obtener el nombre del producto seleccionado del ChoiceBox
    private String getSelectedProductName() {
        // Obtener el índice del elemento seleccionado en el ChoiceBox
        int selectedIndex = choiceBox.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            // Obtener el elemento seleccionado del ChoiceBox
            Object selectedItem = choiceBox.getItems().get(selectedIndex);
            // Verificar si el elemento seleccionado es una lista de strings
            if (selectedItem instanceof List) {
                // Convertir el elemento seleccionado a una lista de strings
                List<String> productData = (List<String>) selectedItem;
                // Obtener el nombre del producto (suponiendo que el nombre del producto está en la posición 1 de la lista)
                String productName = productData.get(1);
                return productName;
            }
        }
        return null; // Devolver null si no se puede obtener el nombre del producto
    }

    // Método para obtener un producto por su nombre
    private Product getProductByName(String productName) {
        if (productList != null && !productList.isEmpty()) {
            try {
                for (int i = 1; i <= productList.size(); i++) {
                    Product product = (Product) productList.getNode(i).data;
                    if (product.getName().equals(productName)) {
                        return product; // Devolver el producto si se encuentra el nombre correspondiente
                    }
                }
            } catch (ListException ex) {
                // Manejar excepciones de la lista
                alert.setContentText("Error while searching for product: " + ex.getMessage());
            }
        }
        return null; // Devolver null si no se encuentra el producto con el nombre especificado
    }


    // Método para obtener un producto por su nombre


    private boolean isValid() {
        return !idTextField.getText().isEmpty() &&
                choiceBox.getValue() != null &&
                !quantityTestField.getText().isEmpty() &&
                !UPriceText.getText().isEmpty() &&
                !totalPriceText.getText().isEmpty();
    }

    private double getUnitPrice(String productName) {
        // Buscar el producto en la lista de productos y devolver su precio unitario
        try {
            for (int i = 1; i <= productList.size(); i++) {
                Product product = (Product) productList.getNode(i).data;
                if (product.getName().equals(productName)) {
                    return product.getPrice();
                }
            }
        } catch (ListException ex) {
            alert.setContentText("Failed to get unit price. Please try again.");
        }
        return 0; // Si no se encuentra el producto, devolver 0 como precio unitario
    }
    @javafx.fxml.FXML
    public void cleanAction(ActionEvent actionEvent) {
        idTextField.clear();
        quantityTestField.clear();
        UPriceText.clear();
        totalPriceText.clear();
        choiceBox.getSelectionModel().clearSelection();
        datePicker.getEditor().clear();
    }

    @javafx.fxml.FXML
    public void closeAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("ucr.lab.HelloApplication", "OrdesCircularLinked.fxml", bp);
    }

    @javafx.fxml.FXML
    public void quantityOnKyPressed(Event event) {
        updatePrices();    }

    @javafx.fxml.FXML
    public void quantityOnKeyReleased(Event event) {
        updatePrices();
    }
}