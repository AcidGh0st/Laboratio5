package domain;

public class DoublyLinkedList implements List{
    private Node first; //apuntador al inicio de la lista

    public DoublyLinkedList() {
        this.first = null; //la lista no existe
    }

    @Override
    public int size() throws ListException {
        if(isEmpty()){
            throw new ListException("Doubly Linked List is empty");
        }
        Node aux = first;
        int count=0;
        while(aux!=null){
            count++;
            aux = aux.next; //lo movemos al sgte nodo
        }
        return count;
    }

    @Override
    public void clear() {
        this.first = null; //anulamos la lista
    }

    @Override
    public boolean isEmpty() {
        return this.first == null; //si es nulo está vacía
    }

    @Override
    public boolean contains(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Doubly Linked List is empty");
        }
        Node aux = first;
        while(aux!=null){
            if(util.Utility.compare(aux.data, element)==0){
                return true;
            }
            aux = aux.next; //lo movemos al sgte nodo
        }
        return false; //indica q el elemento no existe
    }

    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            first = newNode;
        }else{
            Node aux = first;
            //mientras no llegue al ult nodo
            while(aux.next!=null){
                aux=aux.next;
            }
            //una vez que se sale del while, quiere decir q
            //aux esta en el ult nodo, por lo q lo podemos enlazar
            //con el nuevo nodo
            aux.next = newNode;
            //hago el doble enlace
            newNode.prev = aux;
        }

    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            first = newNode;
        }else{
            newNode.next = first;
            //hago el doble enlace
            first.prev = newNode;
            first = newNode;
        }

    }

    @Override
    public void addLast(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            first = newNode;
        } else {
            Node aux = first;
            // Recorrer la lista hasta el último nodo
            while(aux.next != null){
                aux = aux.next;
            }
            // Una vez que se sale del bucle, aux está en el último nodo,
            // por lo que podemos enlazarlo con el nuevo nodo
            aux.next = newNode;
            // Hacer el doble enlace
            newNode.prev = aux;
        }
    }


    @Override
    public void addInSortedList(Object element) {
        // Agregamos el nuevo elemento al final de la lista
        addLast(element);
        // Ordenamos la lista después de agregar el nuevo elemento
        try {
            sort();
        } catch (ListException e) {
            // Manejo de la excepción en caso de que ocurra algo inesperado
            System.out.println("Error: " + e.getMessage());
        }
    }





    @Override
    public void remove(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Doubly Linked List is Empty");
        }
        //Caso 1. El elemento a suprimir esta al inicio
        if(util.Utility.compare(first.data, element)==0){
            first = first.next; //saltamos el primer nodo
        }else{  //Caso 2. El elemento a suprimir puede estar al medio o final
            Node prev = first; //dejo un apuntador al nodo anterior
            Node aux = first.next;
            while(aux!=null && !(util.Utility.compare(aux.data, element)==0)){
                prev = aux;
                aux = aux.next;
            }
            //se sale cuando alcanza nulo o cuando encuentra el elemento
            if(aux!=null && util.Utility.compare(aux.data, element)==0){
                //ya lo encontro, procedo a desenlazar el nodo
                prev.next = aux.next;
                //mantengo el doble enlace
                if(aux.next!=null){
                    aux.next.prev = aux.prev;
                }
            }
        }
    }

    @Override
    public Object removeFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("Doubly Linked List is Empty");
        }

        // Guardamos el valor del primer nodo antes de eliminarlo
        Object removedValue = first.data;

        // Movemos el apuntador al siguiente nodo
        first = first.next;

        // Si hay un nuevo primer nodo, actualizamos su enlace previo a null
        if (first != null) {
            first.prev = null;
        }

        // Devolvemos el valor del nodo eliminado
        return removedValue;
    }


    @Override
    public Object removeLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("Doubly Linked List is Empty");
        }

        // Si solo hay un nodo en la lista
        if (first.next == null) {
            Object removedValue = first.data;
            first = null; // La lista queda vacía
            return removedValue;
        }

        // Encontramos el penúltimo nodo
        Node prevToLast = first;
        while (prevToLast.next.next != null) {
            prevToLast = prevToLast.next;
        }

        // Guardamos el valor del último nodo antes de eliminarlo
        Object removedValue = prevToLast.next.data;

        // Eliminamos el último nodo
        prevToLast.next = null;

        return removedValue;
    }


    @Override
    public void sort() throws ListException {
        if(isEmpty()){
            throw new ListException("Doubly Linked List is Empty");
        }
        for (int i = 1; i <= size() ; i++) {
            for (int j = i+1; j <= size() ; j++) {
                if(util.Utility.compare(getNode(j).data, getNode(i).data)<0){
                    Object aux = getNode(i).data;
                    getNode(i).data = getNode(j).data;
                    getNode(j).data = aux;
                }
            }
        }
    }

    @Override
    public int indexOf(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Doubly Linked List is Empty");
        }
        Node aux = first;
        int index=1; //la lista inicia en 1
        while(aux!=null){
            if(util.Utility.compare(aux.data, element)==0){
                return index;
            }
            index++; //incremento el indice
            aux=aux.next; //muevo aux al sgte nodo
        }
        return -1; //indica q el elemento no existe
    }

    @Override
    public Object getFirst() throws ListException {
        if(isEmpty()){
            throw new ListException("Doubly Linked List is Empty");
        }
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if(isEmpty()){
            throw new ListException("Doubly Linked List is Empty");
        }
        Node aux = first;
        //mientras no llegue al ult nodo
        while(aux.next!=null){
            aux=aux.next;
        }
        //se sale del while cuando aux esta en el ult nodo
        return aux.data;
    }

    @Override
    public Object getPrev(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Doubly Linked List is Empty");
        }
        if(util.Utility.compare(first.data, element)==0){
            return "It's the first, it has no previous";
        }
        Node aux = first;
        //mientras no llegue al ult nodo
        while(aux.next!=null){
            if(util.Utility.compare(aux.next.data, element)==0){
                return aux.data; //retornamos la data del nodo actual
            }
            aux=aux.next;
        }
        return "Does not exist in Doubly Linked List";
    }

    @Override
    public Object getNext(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Doubly Linked List is Empty");
        }

        Node current = first;

        // Buscamos el nodo que contiene el elemento especificado
        while (current != null && !current.data.equals(element)) {
            current = current.next;
        }

        // Si no se encuentra el elemento en la lista
        if (current == null) {
            return "Element not found in Doubly Linked List";
        }

        // Si el nodo encontrado no es el último nodo de la lista
        if (current.next != null) {
            return current.next.data;
        } else {
            return "It's the last, it has no next";
        }
    }


    @Override
    public Node getNode(int index) throws ListException {
        if(isEmpty()){
            throw new ListException("Doubly Linked List is Empty");
        }
        Node aux = first;
        int i = 1; // pos del primer nodo
        while(aux!=null){
            if(util.Utility.compare(i, index)==0) {  //ya encontro el indice
                return aux;
            }
            i++; //incremento la var local
            aux = aux.next; //muevo aux al sgte nodo
        }
        return null; //si llega aqui es xq no encontro el index
    }

    @Override
    public String toString() {
        String result = "Doubly Linked List Content\n\n";
        Node aux = first;
        while(aux!=null){
            result+= STR."\{aux.data}\n";
            aux = aux.next;
        }
        return result;
    }

    public void sortByName() {
        if (isEmpty()) {
            return;
        }

        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            Node current = first;
            while (current.next != null) {
                if (((Product) current.data).getName().compareTo(((Product) current.next.data).getName()) > 0) {
                    // Intercambiar los datos de los nodos si el nombre actual es mayor que el siguiente
                    Product temp = (Product) current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    sorted = false;
                }
                current = current.next;
            }
        }
    }
}
