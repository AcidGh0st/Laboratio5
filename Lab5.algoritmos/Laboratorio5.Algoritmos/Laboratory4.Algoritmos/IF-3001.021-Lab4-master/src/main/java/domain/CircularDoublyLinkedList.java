package domain;

import util.Utility;

import java.io.FileReader;

import static java.lang.StringTemplate.STR;

public class CircularDoublyLinkedList implements List{
    private Node first; //apuntador al inicio de la lista
    private Node last; //apuntador al final de la lista

    public CircularDoublyLinkedList() {
        this.first = null; //la lista no existe
        this.last = null; //la lista no existe
    }


    @Override
    public int size() throws ListException {
        int count = 0;
        Node current = first;

        while (current != last) {//mientas que no llegue al último, por ser circular
            count++;
            current = current.next;
        }

        return count+1;//Para que cuente el último nodo +1
    }


    @Override
    public void clear() {
        this.first = null; //anulamos la lista
        this.last = null; //anulamos la lista
    }

    @Override
    public boolean isEmpty() {
        return this.first == null; //si es nulo está vacía
    }

    @Override
    public boolean contains(Object element) throws ListException {
        Node current = first;

        while (current != last) {
            if (Utility.compare(current.data, element) == 0) {
                return true; // Elemento encontrado en la lista
            }
            current = current.next;
        }

        //Se sale del while, cuando aux==last entonces solo queda verificar si el elemento a buscar está en el último nodo
        return Utility.compare(current.data, element) == 0;//elemento encontrado?
    }


    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            first = last = newNode; //Un solo nodo, ambos apuntan al mismo nodo
        } else {
            last.next = newNode;
            newNode.prev = last; //Establece enlace doble
            last = newNode;
        }
        //hace el enlace circular y doble
        last.next = first;
        first.prev = last;
    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if(isEmpty())
            first = last =  newNode;
        else{
            newNode.next = first;
            //Hago el enlace doble
            first.prev = newNode;
            first = newNode;
        }
        //Garantizo el enlace circular y doble
        last.next = first;
        first.prev = last;

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
        if (isEmpty() || Utility.compare(first.data, element) > 0) {
            // Si la lista está vacía o el elemento es menor que el primer nodo,
            // el nuevo nodo se convierte en el primer nodo
            newNode.next = first;
            first = newNode;
        } else {
            Node current = first;
            Node previous = null;

            // Avanzamos hasta encontrar el lugar correcto para insertar el nuevo nodo
            while (current != null && Utility.compare(current.data, element) <= 0) {
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
            throw new ListException("Circular Doubly Linked List is Empty");
        }
        Node current = first;
        while (current != null) {
            if (Utility.compare(current.data, element) == 0) {
                if (current == first) {
                    first = first.next; //Actualiza el puntero first si el elemento a eliminar es el primer nodo
                } else if (current == last) {
                    last = last.prev; //Actualiza el puntero last si el elemento a eliminar es el último nodo
                } else {
                    current.prev.next = current.next; //Desenlaza el nodo actual
                    current.next.prev = current.prev; //Desenlaza el nodo actual
                }
                //Mantiene el enlace circular y doble
                last.next = first;
                first.prev = last;
                return;
            }
            current = current.next;
        }
        throw new ListException("Element not found in Circular Doubly Linked List");
    }

    @Override
    public Object removeFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Doubly Linked List is empty");
        }
        Object removedData = first.data; //Guarda el valor del primer nodo que se va a eliminar
        if (first == last) {
            //Si solo queda un nodo en la lista
            clear(); //Anula la lista
        } else {
            first = first.next; //Actualiza el puntero first para que apunte al siguiente nodo
            //Mantiene el enlace circular y doble
            last.next = first;
            first.prev = last;
        }
        return removedData;
    }

    @Override
    public Object removeLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Doubly Linked List is empty");
        }
        Object removedData = last.data; //Guarda el valor del último nodo que se va a eliminar
        if (first == last) {
            //Si solo queda un nodo en la lista
            clear(); //anula la lista
        } else {
            last = last.prev; //Actualiza el puntero last para que apunte al nodo anterior
            last.next = first; //Mantiene el enlace circular
            first.prev = last; //Mantiene el enlace doble
        }
        return removedData;
    }


    @Override
    public void sort() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is Empty");
        }

        boolean swapped;
        do {
            swapped = false;
            Node current = first;
            Node next = first.next;
            Node previous = null;

            while (next != null) {
                if (Utility.compare(current.data, next.data) > 0) {
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
            throw new ListException("Circular Linked List is Empty");
        }
        Node aux = first;
        int index=1; //la lista inicia en 1
        while(aux!=last){
            if(Utility.compare(aux.data, element)==0){
                return index;
            }
            index++; //incremento el indice
            aux=aux.next; //muevo aux al sgte nodo
        }

        //Se sale cuando alcanza last, retorne el indice
        if(Utility.compare(aux.data, element)==0){
            return index;
        }

        return -1; //indica q el elemento no existe
    }

    @Override
    public Object getFirst() throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Linked List is Empty");
        }
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Linked List is Empty");
        }
        return last.data;
    }

    @Override
    public Object getPrev(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Doubly Linked List is Empty");
        }
        Node current = first;
        //Busca el elemento en la lista
        while (current != null) {
            if (Utility.compare(current.data, element) == 0) {
                //Si el nodo actual es el primero, no tiene un nodo previo
                if (current == first) {
                    return "It's the first, it has no previous";
                }
                return current.prev.data; //Devuelve el dato del nodo previo
            }
            current = current.next;
        }
        //Si el elemento no se encuentra en la lista
        return "Does not exist in Circular Doubly Linked List";
    }

    @Override
    public Object getNext(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Doubly Linked List is Empty");
        }
        Node current = first;
        //Busca el elemento en la lista
        while (current != null) {
            if (Utility.compare(current.data, element) == 0) {
                //Si el nodo actual es el último, no tiene un nodo siguiente
                if (current == last) {
                    return "It's the last, it has no next";
                }
                return current.next.data; //Devuelve el dato del nodo siguiente
            }
            current = current.next;
        }
        //Si el elemento no se encuentra en la lista
        throw new ListException("Element not found in Circular Doubly Linked List");
    }



    @Override
    public Node getNode(int index) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is Empty");
        }

        Node aux = first;
        int i = 1; //pos del primer nodo
        while(aux !=last){
            if (Utility.compare(i, index)==0) { //Ya encontró el indice
                return aux;
            }
            i++; //incrementos el indice
            aux=aux.next;// muevo el aux al siguiente nodo
        }

        //se sale caundo aux==last
        if (Utility.compare(i, index)==0) { //Ya encontró el indice
            return aux;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Circular Doubly Linked List Content\n\n");
        Node aux = first;
        while (aux != last) {
            result.append(aux.data).append("\n");
            aux = aux.next;
        }
        return result.append(aux.data).toString();//Agrega la data del último nodo
    }

    public void sortName() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular linked list is empty");
        }
        if (size() == 1) {
            // Si solo hay un nodo en la lista, no hay necesidad de ordenar
            return;
        }

        for (int i = 1; i <= size(); i++) {
            for (int j = i + 1; j <= size(); j++) {
                Node nodeI = getNode(i); //declara los nodos, lo que permite compararlos, ya que el Nodo en la posicion j es una posicion despues del nodo i
                Node nodeJ = getNode(j);

                //Verifica si los nodos son nulos antes de acceder a los elementos de cada nodo
                if (nodeI != null && nodeJ != null) {
                    // Obtener los nombres de los clientes en los nodos i y j
                    String nameI = ((Customer) nodeI.data).getName();
                    String nameJ = ((Customer) nodeJ.data).getName();

                    //Se comparan los nombres, ignorando si esta en mayuscula o minuscula la letra, y se cambian de posicion en caso de que el segundo nombre este antes que el primero
                    if (nameI.compareToIgnoreCase(nameJ) > 0) {
                        //Intercambia los elementos si el nombre en el índice i es mayor que el nombre en el índice j
                        Object temp = nodeI.data; //crea una variable temporal en donde se almacenan los datos del primer Nodo
                        nodeI.data = nodeJ.data; //A los datos del primer nodo se les asignan los datos del segundo nodo
                        nodeJ.data = temp; //Los datos del primer nodo original se les asigna al segundo nodo, y con esto se hace el intercambio de elementos entre los nodos
                    }
                } else {
                    //Maneja el caso en el que se obtiene un nodo nulo
                    throw new ListException("Node at index " + i + " or " + j + " is null");
                }
            }
        }
    }
}

/////////////////////////////////////////
//falta el remove last y first, getnext
////////////////////////////////////////