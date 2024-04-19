package domain;

import util.Utility;

import static java.lang.StringTemplate.STR;

public class CircularLinkedList implements List {
    private Node first; //apuntador al inicio de la lista
    private Node last; //apuntador al final de la lista

    public CircularLinkedList() {
        this.first = null; //la lista no existe
        this.last = null; //la lista no existe
    }


    @Override
    public int size() throws ListException {

        if (isEmpty()) {
            return 0;
        }

        int count = 0;
        Node current = first;

        do {
            count++;
            current = current.next;
        } while (current != first);

        return count;
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
            first = last = newNode; //un solo nodo, ambos apuntan al mismo nodo
        } else {
            last.next = newNode;
            //ponemos last a apuntar al ultimo nodo
            last = newNode;
        }

        //Se hace el enlace circular
        last.next = first;
    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if (isEmpty())
            first = last = newNode;
        else {
            newNode.next = first;
            first = newNode;
        }
        //Se hace el enlace circular
        last.next = first;
    }


    @Override
    public void addLast(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            first = last = newNode; //Si la lista está vacía, el nuevo nodo se convierte en el primer y último nodo
        } else {
            last.next = newNode; //Enlaza el último nodo actual con el nuevo nodo
            last = newNode; //Actualiza el puntero last para que apunte al nuevo nodo agregado
        }
        //hace el enlace circular
        last.next = first;
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
            throw new ListException("Circular Linked List is Empty");
        }
        //CASO 1 el elemento a suprimir está al inicio:
        if (Utility.compare(first.data, element) == 0) {
            first = first.next; //saltamos al primer nodo
        } else {  //caso 2 suprimir el ultimo
            Node prev = first; //dejo un apuntador al nodo anterior
            Node aux = first.next;
            while (aux != last && !(Utility.compare(aux.data, element) == 0)) {
                prev = aux;
                aux = aux.next;
            }
            //se sale cuando encuentra el elemento
            if (Utility.compare(aux.data, element) == 0) {
                //ya lo encontró procede a desenlazar el nodo
                prev.next = aux.next;
            }

            //Que pasas si el elemento a suprimir esta en el ultimo nodo
            if (aux == last && Utility.compare(aux.data, element) == 0) {
                last = prev; //desenlaza el ultimo nodo
            }
        }

        //Mantengo el enlace circular
        last.next = first;

        //Otro caso:
        //Si solo queda un nodo y es el que quiero eliminar
        if (first == last && util.Utility.compare(first.data, element) == 0) {
            clear();//anulo la lista
        }
    }

    @Override
    public Object removeFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is empty");
        }
        Object removedData = first.data; //Guarda el valor del primer nodo que se va a eliminar
        first = first.next; //Actualizamos el puntero first para que apunte al siguiente nodo
        return removedData; //Devolvemos el valor del primer nodo eliminado
    }

    @Override
    public Object removeLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is empty");
        }
        Object removedData = last.data; //Guardamos el valor del último nodo que se va a eliminar
        //Si solo hay un nodo en la lista
        if (first == last) {
            clear(); //anulo la lista
        } else {
            Node current = first;
            while (current.next != last) {
                current = current.next;
            }
            last = current; //desenlazo el último nodo
            //Mantengo el enlace circular
            last.next = first;
            first.prev = last;
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

            while (next != first) {
                if (Utility.compare(current.data, next.data) > 0) {
                    if (previous != null) {
                        previous.next = next;
                    } else {
                        first = next;
                    }
                    current.next = next.next;
                    next.next = current;

                    if (next == first) {
                        //Actualiza first si el nodo que movimos es ahora el primero
                        first = next;
                    }

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
        if (isEmpty()) {
            throw new ListException("Circular Linked List is Empty");
        }
        Node aux = first;
        int index = 1; //la lista inicia en 1
        while (aux != last) {
            if (Utility.compare(aux.data, element) == 0) {
                return index;
            }
            index++; //incremento el indice
            aux = aux.next; //muevo aux al sgte nodo
        }

        //Se sale cuando alcanza last, retorne el indice
        if (Utility.compare(aux.data, element) == 0) {
            return index;
        }

        return -1; //indica q el elemento no existe
    }

    @Override
    public Object getFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is Empty");
        }
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is Empty");
        }
        return last.data;
    }

    @Override
    public Object getPrev(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is Empty");
        }
        Node current = first;
        //Busca el elemento en la lista
        while (current != last) {
            if (Utility.compare(current.next.data, element) == 0) {
                //Si el siguiente nodo tiene el elemento, retornamos el dato del nodo actual
                return current.data;
            }
            current = current.next;
        }
        //sale del bucle cuando alcanza el último nodo
        if (Utility.compare(current.next.data, element) == 0) {
            //Si el último nodo tiene el elemento, retornamos el dato del último nodo
            return current.data;
        }
        //Si el elemento no se encuentra en la lista
        return "Does not exist in Circular Linked List";
    }

    @Override
    public Object getNext(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is Empty");
        }
        Node current = first;
        //Busca el elemento en la lista
        while (current != last) {
            if (Utility.compare(current.data, element) == 0) {
                //Si el nodo actual tiene el elemento, retornamos el dato del siguiente nodo
                return current.next.data;
            }
            current = current.next;
        }
        //sale del bucle cuando alcanza el último nodo
        if (Utility.compare(current.data, element) == 0) {
            //Si el último nodo tiene el elemento, retornamos el dato del primer nodo
            return first.data;
        }
        //Si el elemento no se encuentra en la lista
        return "Does not exist in Circular Linked List";
    }



    @Override
    public Node getNode(int index) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is Empty");
        }

        Node aux = first;
        int i = 1; //pos del primer nodo
        while (aux != last) {
            if (Utility.compare(i, index) == 0) { //Ya encontró el indice
                return aux;
            }
            i++; //incrementos el indice
            aux = aux.next;// muevo el aux al siguiente nodo
        }

        //se sale caundo aux==last
        if (Utility.compare(i, index) == 0) { //Ya encontró el indice
            return aux;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Circular Linked List Content\n\n");
        Node aux = first;
        while (aux != last) {
            result.append(aux.data).append("\n");
            aux = aux.next;
        }
        result.append(aux.data); //Agrega la data del último nodo
        return result.toString();
    }

    private void sortName() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular linked list is empty");
        }

        for (int i = 1; i <= size(); i++) {
            for (int j = i + 1; j <= size(); j++) {
                Node nodeI = getNode(i);
                Node nodeJ = getNode(j);

                if (nodeI != null && nodeJ != null) {
                    String nameI = ((Customer) nodeI.data).getName();
                    String nameJ = ((Customer) nodeJ.data).getName();

                    if (nameI.compareToIgnoreCase(nameJ) > 0) {
                        Object temp = nodeI.data;
                        nodeI.data = nodeJ.data;
                        nodeJ.data = temp;
                    }
                } else {
                    throw new ListException("Node at index " + i + " or " + j + " is null");
                }
            }
        }
    }
}


/////////////////////////////////////////
//falta el remove last y first, getnext
////////////////////////////////////////