package domain;

public class Product extends Master{
    private double price;
    private  int currentStock;

    public Product(int id, String name, double price, int currentStock) {
        super(id, name);
        this.price = price;
        this.currentStock = currentStock;
    }

    public Product(int id, String name) {
        super(id, name);
    }

    @Override
    String print() {
        return "Product: "+getId()+" "+getName()+" "+price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    @Override
    public String toString() {
        return STR."\{super.toString()} price=\{price}, currentStock=\{currentStock}";
    }
}
