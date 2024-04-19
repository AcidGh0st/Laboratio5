package domain;

import java.time.LocalDate;

public class Order {
    private int id;
    private LocalDate orderDate;
    private int productId;
    private int quantity;
    private static int idCounter;

    //Constructor
    public Order(int id, LocalDate orderDate, int productId, int quantity) {
        this.id = id;
        this.orderDate = orderDate;
        this.productId = productId;
        this.quantity = quantity;
    }

    //Constructor sobrecargado 1
    public Order(LocalDate orderDate, int productId, int quantity) {
        this.id = ++idCounter;//Es auto incrementable
        this.orderDate = orderDate;
        this.productId = productId;
        this.quantity = quantity;
    }

    //Constructor sobrecargado 2
    //Se utilizaré en las busquedas
    public Order(int id, LocalDate value, String string) {
        this.id = id;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
