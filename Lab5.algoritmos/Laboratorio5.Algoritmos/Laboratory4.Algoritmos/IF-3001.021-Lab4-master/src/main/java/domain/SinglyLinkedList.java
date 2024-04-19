package domain;

import util.Utility;

public class SinglyLinkedList implements List{
    private Node first; //apuntador al inicio de la lista

    public SinglyLinkedList() {
        this.first = null; //la lista no existe
    }


    @Override
    public int size() throws ListException {
        int count = 0;
        Node current = first;

        while (current != null) {
            count++;
            current = current.next;
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
        Node current = first;

        while (current != null) {
            if (util.Utility.compare(current.data, element) == 0) {
                return true; // Elemento encontrado en la lista
            }
            current = current.next;
        }

        return false; // Elemento no encontrado en la lista
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
        }

    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            first = newNode;
        }else{
            newNode.next = first;
            first = newNode;
        }

    }


    @Override
    public void addLast(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            first = newNode; // Si la lista está vacía, el nuevo nodo se convierte en el primer nodo
        } else {
            Node aux = first;
            // Iteramos hasta llegar al último nodo
            while (aux.next != null) {
                aux = aux.next;
            }
            // Enlazamos el último nodo con el nuevo nodo
            aux.next = newNode;
        }
    }


    @Override
    public void addInSortedList(Object element) {
        Node newNode = new Node(element);
        if (isEmpty() || util.Utility.compare(first.data, element) > 0) {
            // Si la lista está vacía o el elemento es menor que el primer nodo,
            // el nuevo nodo se convierte en el primer nodo
            newNode.next = first;
            first = newNode;
        } else {
            Node current = first;
            Node previous = null;

            // Avanzamos hasta encontrar el lugar correcto para insertar el nuevo nodo
            while (current != null && util.Utility.compare(current.data, element) <= 0) {
                previous = current;
                current = current.next;
            }

            // Insertamos el nuevo nodo entre previous y current
            previous.next = newNode;
            newNode.next = current;
        }
    }


    @Override
    public void remove(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is Empty");
        }
        //CASO 1 el elemento a suprimir está al inicio:
        if (util.Utility.compare(first.data,element)==0){
            first=first.next; //saltamos al primer nodo
        }else {  //caso 2 suprimir el ultimo
            Node prev = first; //dejo un apuntador al nodo anterior
            Node aux= first.next;
            while (aux!=null && !(util.Utility.compare(aux.data, element)==0)){
                prev = aux;
                aux= aux.next;
            }
            //se sale cuando encuentra el elemento
            if (aux!=null && util.Utility.compare(aux.data, element)==0){
                //ya lo encontró procede a desenlazar el nodo
                prev.next= aux.next;
            }
        }


    }

    @Override
    public Object removeFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is empty");
        }
        Object removedData = first.data; // Guardamos el valor del primer nodo que se va a eliminar
        first = first.next; // Actualizamos el puntero first para que apunte al siguiente nodo
        return removedData; // Devolvemos el valor del primer nodo eliminado
    }

    @Override
    public Object removeLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is empty");
        }
        if (first.next == null) {
            // Si solo hay un nodo en la lista, eliminamos ese nodo
            Object removedData = first.data;
            first = null;
            return removedData;
        }
        Node aux = first;
        Node prev = null;
        // Avanzamos hasta el último nodo
        while (aux.next != null) {
            prev = aux;
            aux = aux.next;
        }
        // Guardamos el valor del último nodo que se va a eliminar
        Object removedData = aux.data;
        // Eliminamos el último nodo
        prev.next = null;
        return removedData; // Devolvemos el valor del último nodo eliminado
    }

    @Override
    public void sort() throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is Empty");
        }

        boolean swapped;
        do {
            swapped = false;
            Node current = first;
            Node next = first.next;
            Node previous = null;

            while (next != null) {
                if (util.Utility.compare(current.data, next.data) > 0) {
                    if (previous != null) {
                        previous.next = next;
                    } else {
                        first = next;
                    }
                    current.next = next.next;
                    next.next = current;

                    previous = next;
                    next = current.next;
                    swapped = true;
                } else {
                    previous = current;
                    current = next;
                    next = next.next;
                }
            }
        } while (swapped);
    }


    @Override
    public int indexOf(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is Empty");
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
            throw new ListException("Singly Linked List is Empty");
        }
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is Empty");
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
            throw new ListException("Singly Linked List is Empty");
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
        return "Does not exist in Single Linked List";
    }

    @Override
    public Object getNext(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is Empty");
        }

        Node aux = first;
        // Buscamos el elemento en la lista
        while (aux != null) {
            if (util.Utility.compare(aux.data, element) == 0) {
                // Si el elemento actual no es el último, retornamos el siguiente
                if (aux.next != null) {
                    return aux.next.data;
                } else {
                    return "It's the last, it has no next";
                }
            }
            aux = aux.next;
        }
        // Si el elemento no se encuentra en la lista
        return "Does not exist in Single Linked List";
    }


    @Override
    public Node getNode(int index) throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is Empty");
        }

        Node aux = first;
        int i = 1; //pos del primer nodo
        while(aux !=null){
            if (util.Utility.compare(i, index)==0) { //Ya encontró el indice
                return aux;
            }
            i++; //incrementos el indice
            aux=aux.next;// muevo el aux al siguiente nodo
        }

        return null;

    }

    @Override
    public String toString() {
        String result = "Singly Linked List Content\n\n";
        Node aux = first;
        while(aux!=null){
            result+= STR."\{aux.data}\n ";
            aux = aux.next;
        }
        return result;
    }
    public void sortName() throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly linked list is empty");
        }

        for (int i = 1; i <= size(); i++) {
            for (int j = i + 1; j <= size(); j++) {
                Node nodeI = getNode(i); //Se declaran los nodos, lo que permite compararlos, ya que el Nodo en la posicion j es una posicion despues del nodo i
                Node nodeJ = getNode(j);

                // Verificar si los nodos son nulos antes de acceder a los elementos de cada nodo
                if (nodeI != null && nodeJ != null) {

                    // Obtener los nombres de los clientes en los nodos i y j

                    String nameI = ((Customer) nodeI.data).getName();
                    String nameJ = ((Customer) nodeJ.data).getName();

                    //Se comparan los nombres, ignorando si esta en mayuscula o minuscula la letra, y se cambian de posicion en caso de que el segundo nombre este antes que el primero
                    if (nameI.compareToIgnoreCase(nameJ) > 0) {
                        // Intercambiar los elementos si el nombre en el índice i es mayor que el nombre en el índice j
                        Object temp = nodeI.data;// Se crea una variable temporal en donde se almacenan los datos del primer Nodo
                        nodeI.data = nodeJ.data; //A los datos del primer nodo se les asignan los datos del segundo nodo
                        nodeJ.data = temp; //Los datos del primer nodo original se les asigna al segundo nodo, y con esto se hace el intercambio de elementos entre los nodos
                    }
                } else {
                    // Manejar el caso en el que se obtiene un nodo nulo
                    throw new ListException("Node at index " + i + " or " + j + " is null");
                }
            }
        }
    }
}

