package controller;

import domain.DoublyLinkedList;
import domain.ListException;
import domain.Node;
import domain.Product;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class ProductController {
    @javafx.fxml.FXML
    private TableColumn idTableColumn;
    @javafx.fxml.FXML
    private TableColumn nameTableColumn;
    @javafx.fxml.FXML
    private TableView tableView;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TableColumn priceTableColumn;
    @javafx.fxml.FXML
    private TableColumn currentTableColumn;
    private DoublyLinkedList productList;
    Alert alert;

    @javafx.fxml.FXML
    public void initialize() {
        this.alert = util.UtilityFX.alert("Products Management", "");
        //cargamos la lista global
        this.productList = util.Utility.getProductList();

        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        currentTableColumn.setCellValueFactory(new PropertyValueFactory<>("currentStock"));

        try{
            if(productList!=null && !productList.isEmpty()) {
                for (int i = 1; i <= productList.size(); i++) {
                    tableView.getItems().add(productList.getNode(i).data);
                }
            }
        }catch(ListException ex){
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }



    @javafx.fxml.FXML

    public void addFirstOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("ucr.lab.HelloApplication", "addFirstProduct.fxml", bp);
    }



    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        //Elimina todos los elementos de la lista de productos
        productList.clear();

        //Limpia la tabla
        tableView.getItems().clear();
    }


    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) {
        //Obtiene el índice seleccionado en la tabla
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        //Verifica si se seleccionó algún elemento
        if (selectedIndex >= 0) {
            //Elimina el producto de la lista
            try {
                productList.remove(selectedIndex + 1); // Se suma 1 porque la lista está basada en índices comenzando desde 1
            } catch (ListException e) {
                // Maneja la excepción si es lanzada
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                return; // Sale del método
            }

            //Elimina el producto de la tabla
            tableView.getItems().remove(selectedIndex);
        } else {
            //Muestra un mensaje si no se seleccionó ningún elemento
            alert.setContentText("Please select a product to remove.");
            alert.showAndWait();
        }
    }


    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("ucr.lab.HelloApplication", "addProduct.fxml", bp);
    }

    @javafx.fxml.FXML
    public void addSortedOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("ucr.lab.HelloApplication", "addSortedProduct.fxml", bp);
    }

    @javafx.fxml.FXML
    public void getFirstOnAction(ActionEvent actionEvent) {
        try {
            // Verifica si la lista de productos no está vacía
            if (!productList.isEmpty()) {
                // Obtiene el primer producto de la lista
                Product firstProduct = (Product) productList.getFirst();

                // Muestra el primer producto en un mensaje de alerta
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("First product: " + firstProduct.toString());
                alert.showAndWait();
            } else {
                // Muestra un mensaje de alerta si la lista de productos está vacía
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("The product list is empty.");
                alert.showAndWait();
            }
        } catch (ListException e) {
            // Maneja la excepción si ocurre al obtener el primer producto
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }


    @javafx.fxml.FXML
    public void removeFirstOnAction(ActionEvent actionEvent) {
        try {
            //Verifica si la lista de productos no está vacía
            if (!productList.isEmpty()) {
                //Elimina el primer producto de la lista
                Product removedProduct = (Product) productList.removeFirst();

                //Elimina el primer elemento de la tabla
                tableView.getItems().remove(0);

                //Muestra una alerta informando que el producto fue eliminado
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("The first product was removed from the list.");
                alert.showAndWait();
            } else {
                //Muestra un mensaje de alerta si la lista de productos está vacía
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("The product list is empty.");
                alert.showAndWait();
            }
        } catch (ListException e) {
            //Maneja la excepción si ocurre al eliminar el producto
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }


    @javafx.fxml.FXML
    public void getLastOnAction(ActionEvent actionEvent) {
        try {
            // Verifica si la lista de productos no está vacía
            if (!productList.isEmpty()) {
                // Obtiene el último producto de la lista
                Product lastProduct = (Product) productList.getLast();

                // Muestra el último producto en un mensaje de alerta
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Last product: " + lastProduct.toString());
                alert.showAndWait();
            } else {
                // Muestra un mensaje de alerta si la lista de productos está vacía
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("The product list is empty.");
                alert.showAndWait();
            }
        } catch (ListException e) {
            // Maneja la excepción si ocurre al obtener el último producto
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }


    @javafx.fxml.FXML
    public void containsOnAction(ActionEvent actionEvent) {
        //Obtiene el valor a buscar (en este caso, el ID del producto)
        String searchValue = ""; // Inicializamos con un valor vacío

        //Pedir al usuario que ingrese el ID a buscar utilizando un cuadro de diálogo de JavaFX
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Product");
        dialog.setHeaderText("Enter the ID to search:");
        dialog.setContentText("ID:");

        //Muestra el cuadro de diálogo y obtener el resultado
        String result = dialog.showAndWait().orElse(null);

        //Verifica si el resultado es null (el usuario canceló)
        if (result == null) {
            return;
        }

        //Asigna el valor ingresado por el usuario
        searchValue = result;

        //Busca el producto por su ID en la lista de productos
        boolean found = false;
        try {
            for (int i = 1; i <= productList.size(); i++) {
                Node currentNode = productList.getNode(i);
                Product product = (Product) currentNode.getData();

                //Busca por ID
                if (Integer.toString(product.getId()).equals(searchValue)) {
                    found = true;

                    //Muestra la información del producto encontrado
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Product Found:");
                    alert.setContentText("ID: " + product.getId() + "\n" +
                            "Name: " + product.getName() + "\n" +
                            "Price: " + product.getPrice() + "\n" +
                            "Current Stock: " + product.getCurrentStock());
                    alert.showAndWait();

                    break; //Salir del ciclo si se encuentra el producto
                }
            }
        } catch (ListException e) {
            //Maneja la excepción si ocurre al obtener un nodo o al obtener el tamaño de la lista
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        //Muestra un mensaje si no se encuentra el producto con el ID buscado
        if (!found) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Product with ID '" + searchValue + "' not found.");
            alert.showAndWait();
        }
    }


    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) {
        try {
            int size = productList.size(); //Obtiene el tamaño de la lista de productos

            // Muestra el tamaño en una ventana emergente de alerta
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tamaño de la lista de productos");
            alert.setHeaderText(null);
            alert.setContentText("Size of the product list: " + size);
            alert.showAndWait();

        } catch (ListException e) {
            //Maneja la excepción si es lanzada
            e.printStackTrace();
        }
    }
}
