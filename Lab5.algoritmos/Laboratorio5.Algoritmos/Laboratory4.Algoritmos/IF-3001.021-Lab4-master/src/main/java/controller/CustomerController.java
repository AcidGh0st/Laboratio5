package controller;

import domain.Customer;
import domain.ListException;
import domain.Node;
import domain.SinglyLinkedList;
import javafx.event.ActionEvent;

import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;


import java.util.Optional;

public class CustomerController
{
    @javafx.fxml.FXML
    private TableView tableView;
    @javafx.fxml.FXML
    private TableColumn<Customer, Integer> idTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Customer, String> emailTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Customer, String> nameTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Customer, Integer> ageTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Customer, String> phoneTableColumn;
    @javafx.fxml.FXML
    private BorderPane bp;
    private SinglyLinkedList customerList;
    Alert alert;

    @javafx.fxml.FXML
    public void initialize() {
        this.alert = util.UtilityFX.alert("Customers Management", "");
        //cargamos la lista global
        this.customerList = util.Utility.getCustomerList();

        //Hay que tener los getters y setters para que funcione
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageTableColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        phoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        try{
            if(customerList!=null && !customerList.isEmpty()) {
                for (int i = 1; i <= customerList.size(); i++) {
                    tableView.getItems().add(customerList.getNode(i).data);
                }
            }
        }catch(ListException ex){
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void addFirstOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("ucr.lab.HelloApplication", "addFirstCustomer.fxml", bp);
    }



    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        //Elimina todos los elementos de la lista
        customerList.clear();

        //Limpia la tabla
        tableView.getItems().clear();
    }



    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) {
        //Obtiene el índice seleccionado en la tabla
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        //Verifica si se seleccionó algún elemento
        if (selectedIndex >= 0) {
            //Elimina el cliente de la lista
            try {
                customerList.remove(selectedIndex + 1); // Se suma 1 porque la lista está basada en índices comenzando desde 1
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
            alert.setContentText("Please select a customer to remove.");
            alert.showAndWait();
        }
    }


    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("ucr.lab.HelloApplication", "addCustomer.fxml", bp);
    }

    @javafx.fxml.FXML
    public void addSortedOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("ucr.lab.HelloApplication", "addSortedCustomer.fxml", bp);
    }

    @javafx.fxml.FXML
    public void getFirstOnAction(ActionEvent actionEvent) {
        try {
            //Verifica si la lista de clientes no está vacía
            if (!customerList.isEmpty()) {
                //Obtiene el primer cliente de la lista
                Customer firstCustomer = (Customer) customerList.getFirst();

                //Muestra una alerta con la información del primer cliente
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("First Customer:\n" +
                        "ID: " + firstCustomer.getId() + "\n" +
                        "Name: " + firstCustomer.getName() + "\n" +
                        "Age: " + firstCustomer.getAge() + "\n" +
                        "Phone: " + firstCustomer.getPhoneNumber() + "\n" +
                        "Email: " + firstCustomer.getEmail());
                alert.showAndWait();
            } else {
                //Muestra un mensaje de alerta si la lista de clientes está vacía
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("The customer list is empty.");
                alert.showAndWait();
            }
        } catch (ListException e) {
            //Manejr la excepción si ocurre al obtener el primer cliente
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }


    @javafx.fxml.FXML
    public void removeFirstOnAction(ActionEvent actionEvent) {
        try {
            //Verifica si la lista de clientes no está vacía
            if (!customerList.isEmpty()) {
                //Elimina el primer cliente de la lista
                Customer removedCustomer = (Customer) customerList.removeFirst();

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
    public void getLastOnAction(ActionEvent actionEvent) {
        try {
            //Verifica si la lista de clientes no está vacía
            if (!customerList.isEmpty()) {
                //Obtiene el último cliente de la lista
                Customer lastCustomer = (Customer) customerList.getLast();

                //Muestra una alerta con la información del último cliente
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Last Customer:\n" +
                        "ID: " + lastCustomer.getId() + "\n" +
                        "Name: " + lastCustomer.getName() + "\n" +
                        "Age: " + lastCustomer.getAge() + "\n" +
                        "Phone: " + lastCustomer.getPhoneNumber() + "\n" +
                        "Email: " + lastCustomer.getEmail());
                alert.showAndWait();
            } else {
                //Muestra un mensaje de alerta si la lista de clientes está vacía
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("The customer list is empty.");
                alert.showAndWait();
            }
        } catch (ListException e) {
            //Maneja la excepción si ocurre al obtener el último cliente
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }


    @javafx.fxml.FXML
    public void containsOnAction(ActionEvent actionEvent) {
        //Obtiene el valor a buscar (en este caso, el ID del cliente)
        String searchValue = ""; // inicializamos con un valor vacío

        //Pedir al usuario que ingrese el ID a buscar utilizando un cuadro de diálogo de JavaFX
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Customer");
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
            for (int i = 1; i <= customerList.size(); i++) {
                Node currentNode = customerList.getNode(i);
                Customer customer = (Customer) currentNode.getData();

                //Busca por ID
                if (Integer.toString(customer.getId()).equals(searchValue)) {
                    found = true;

                    //Muestra la información del cliente encontrado
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Customer Found:");
                    alert.setContentText("ID: " + customer.getId() + "\n" +
                            "Name: " + customer.getName() + "\n" +
                            "Age: " + customer.getAge() + "\n" +
                            "Phone: " + customer.getPhoneNumber() + "\n" +
                            "Email: " + customer.getEmail());
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
            alert.setContentText("Customer with ID '" + searchValue + "' not found.");
            alert.showAndWait();
        }
    }



    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) {
        try {
            int size = customerList.size(); //Obtiene el tamaño de la lista de productos

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