package controller;

import domain.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import util.Utility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static util.Utility.compare;

public class OrdesCircularLinkedController
{
    @javafx.fxml.FXML
    private TableColumn <List<String>, String> idTable;
    @javafx.fxml.FXML
    private TableColumn <List<String>, String> dateTable;
    @javafx.fxml.FXML
    private TableColumn <List<String>, String> productTable;
    @javafx.fxml.FXML
    private TableColumn <List<String>, String> quantityTable;
    @javafx.fxml.FXML
    private TableColumn <List<String>, String> unitPriceTable;
    @javafx.fxml.FXML
    private TableColumn <List<String>, String> totalPriceTable1;
    @javafx.fxml.FXML
    private Button addButton;
    @javafx.fxml.FXML
    private Button sortIDButton;
    @javafx.fxml.FXML
    private Button sortName;
    @javafx.fxml.FXML
    private Button containsButton;
    @javafx.fxml.FXML
    private Button sizeButton;
    @javafx.fxml.FXML
    private Button getPrevTable;
    @javafx.fxml.FXML
    private Button getNextButton;
    @javafx.fxml.FXML
    private Button removeButton;
    @javafx.fxml.FXML
    private Button removeFirtsButton;
    @javafx.fxml.FXML
    private Button clearButton;
    @javafx.fxml.FXML
    private TableView tableView;
    @javafx.fxml.FXML
    private BorderPane bp;
    private CircularLinkedList orderList;
    private DoublyLinkedList productList;
    Alert alert;

    @javafx.fxml.FXML
    public void initialize() {
        //cargamos las listas globales
        this.orderList = util.Utility.getOrderList();
        this.productList = util.Utility.getProductList();
        this.alert = util.UtilityFX.alert("Order..", "");
        this.idTable.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().get(0))); //defino variable data
        this.dateTable.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().get(1)));
        this.productTable.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().get(2)));
        this.quantityTable.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().get(3)));
        this.unitPriceTable.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().get(4)));
        this.totalPriceTable1.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().get(5)));

        //Cargamos los datos en el tableView

            if(orderList!=null && !orderList.isEmpty())
                this.tableView.setItems(getData());

    }

    private ObservableList<List<String>> getData() {
        ObservableList<List<String>> data = FXCollections.observableArrayList();

        if (orderList != null && !orderList.isEmpty()) {
            try {

                for (int i = 1; i <= orderList.size(); i++) {
                    Order order = (Order) orderList.getNode(i).data;
                    double unitPrice = getProduct(order.getProductId()).getPrice();
                    List<String> arrayList = new ArrayList<>();
                    arrayList.add(String.valueOf(order.getId()));//envuelvo con un string valor entero
                    arrayList.add(String.valueOf(order.getOrderDate()));
                    arrayList.add(getProduct(order.getProductId()).getName());
                    arrayList.add(String.valueOf(order.getQuantity()));
                    arrayList.add("$" + unitPrice);
                    arrayList.add("$" + unitPrice * order.getQuantity());

                    //agregamos al arrayList
                    data.add(arrayList);
                }

            } catch (ListException ex) {
                alert.setContentText("There was an error in the process");
            }
        }

        return data;
    }

    private Product getProduct(int productId) {

        if (productList.isEmpty()) return null;

        try {
            for (int i = 1; i < productList.size() ; i++) {
                Product product = (Product) productList.getNode(i).data;
                if (product.getId() == productId){
                    return product;
                }
            }
        }catch (ListException ex){
            alert.setContentText(ex.getMessage());
        }
        return null;
    }

    @javafx.fxml.FXML
    public void addAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("ucr.lab.HelloApplication", "addOders.fxml", bp);
    }

    @javafx.fxml.FXML

    public void sortyNameAction(ActionEvent actionEvent) {
        // Obtener los datos actuales de la tabla
        ObservableList<List<String>> data = tableView.getItems();

        // Ordenar la lista de productos por nombre
        Utility.getProductList().sortByName();

        // Actualizar los datos de la tabla con los productos ordenados
        util.Utility.setOrderList(this.orderList);
    }


    @javafx.fxml.FXML
    public void containsAction(ActionEvent actionEvent) {

            //Obtiene el valor a buscar (en este caso, el ID del cliente)
            String searchValue = ""; // inicializamos con un valor vacío

            //Pedir al usuario que ingrese el ID a buscar utilizando un cuadro de diálogo de JavaFX
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Search Order");
            dialog.setHeaderText("Enter the ID to search:");
            dialog.setContentText("ID:");

            //muestra el cuadro de diálogo y obtener el resultado
            String result = dialog.showAndWait().orElse(null);

            //Verifica si el resultado es null (el usuario canceló)
            if (result == null) {
                //
                return;
            }

            //Asigna el valor ingresado por el usuario
            searchValue = result;

            //Busca el cliente por su ID en la lista de clientes
            boolean found = false;
            try {
                for (int i = 1; i <= orderList.size(); i++) {
                    Node currentNode = orderList.getNode(i);
                    Order order = (Order) currentNode.getData();

                    //Busca por ID
                    if (Integer.toString(order.getId()).equals(searchValue)) {
                        found = true;

                        //Muestra la información del cliente encontrado
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Order Found:");

                        alert.showAndWait();

                        break; //Salir del ciclo si se encuentra el cliente
                    }
                }
            } catch (ListException e) {
                //Maneja la excepción si ocurre al obtener un nodo o al obtener el tamaño de la lista
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                return;
            }

            //Muestra un mensaje si no se encuentra el cliente con el ID buscado
            if (!found) {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("Order with ID '" + searchValue + "' not found.");
                alert.showAndWait();
            }
        }


    @javafx.fxml.FXML

    public void sizeAction(ActionEvent actionEvent) {
        try {
            int size = 0;
            if (orderList!=null) {
                size = orderList.size(); // Obtiene el tamaño de la lista de productos
            }

            // Muestra el tamaño en una ventana emergente de alerta
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tamaño de la lista de ordenes");
            alert.setHeaderText(null);
            alert.setContentText("Size of the order list: " + size);
            alert.showAndWait();

        } catch (ListException e) {
            // Maneja la excepción si es lanzada
            e.printStackTrace();
        }

    }

    @javafx.fxml.FXML
    public void getPrevAction(ActionEvent actionEvent) {
        
    }





    @javafx.fxml.FXML
        public void getNextAction(ActionEvent actionEvent) {
            try {
                // Verificar si la lista no está vacía
                if (!orderList.isEmpty()) {
                    // Pedir al usuario que ingrese el ID del elemento actual utilizando un cuadro de diálogo de JavaFX
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Search Next Element");
                    dialog.setHeaderText("Enter the ID of the current element:");
                    dialog.setContentText("ID:");

                    // Mostrar el cuadro de diálogo y obtener el resultado
                    Optional<String> result = dialog.showAndWait();

                    // Verificar si el resultado es presente (el usuario ingresó un valor)
                    if (result.isPresent()) {
                        // Asignar el valor ingresado por el usuario
                        String currentElementId = result.get();

                        // Obtener el siguiente elemento en la lista
                        Object nextElement = orderList.getNext(Integer.parseInt(currentElementId));

                        // Mostrar una alerta con la información del siguiente elemento
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Next Element");
                        alert.setHeaderText(null);
                        alert.setContentText("Next Element: " + nextElement.toString());
                        alert.showAndWait();
                    }
                } else {
                    // Mostrar un mensaje de alerta si la lista está vacía
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Empty List");
                    alert.setHeaderText(null);
                    alert.setContentText("The circular list is empty.");
                    alert.showAndWait();
                }
            } catch (ListException | NumberFormatException e) {
                // Manejar la excepción si ocurre al obtener el siguiente elemento
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error: " + e.getMessage());
                alert.showAndWait();
            }
        }






    public void removeAction(ActionEvent actionEvent) {
        //Obtiene el índice seleccionado en la tabla
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        //Verifica si se seleccionó algún elemento
        if (selectedIndex >= 0) {
            //Elimina el cliente de la lista
            try {
                orderList.remove(selectedIndex + 1); // Se suma 1 porque la lista está basada en índices comenzando desde 1
            } catch (ListException e) {
                //Maneja la excepción si es lanzada
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                return; //Sale del método
            }

            //Elimina el cliente de la tabla
            tableView.getItems().remove(selectedIndex);
        } else {
            //Muestra un mensaje si no se seleccionó ningún elemento
            alert.setContentText("Please select a order to remove.");
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void removeFirtsAction(ActionEvent actionEvent) {
        try {
            //Verifica si la lista de clientes no está vacía
            if (!orderList.isEmpty()) {
                //Elimina el primer cliente de la lista
                Order removedOrder = (Order) orderList.removeFirst();

                //Elimina el primer elemento de la tabla
                tableView.getItems().remove(0);

                //Muestra una alerta informando que el cliente fue eliminado
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("The first customer was removed from the list.");
                alert.showAndWait();
            } else {
                //Muestra un mensaje de alerta si la lista de clientes está vacía
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("The customer list is empty.");
                alert.showAndWait();
            }
        } catch (ListException e) {
            //Maneja la excepción si ocurre al eliminar el cliente
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void clearAction(ActionEvent actionEvent) {
        //Elimina todos los elementos de la lista
        orderList.clear();

        //Limpia la tabla
        tableView.getItems().clear();
    }
}