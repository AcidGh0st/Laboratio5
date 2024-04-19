package domain;

public class Node {
    public Object data;
    public Node prev; //apuntador al nodo anterior
    public Node next; //apuntador al nodo siguiente

    public Node(Object data) { //Recibe la data y cetea los otros atributos apuntadores a nulo
        this.data = data;
        this.prev = this.next = null;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

