package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class LinkedListsTest {

    /*@Test*/
    void singlyLinkedListTest1() throws ListException {
        try {
            SinglyLinkedList list = new SinglyLinkedList();
            list.add(15);
            list.add(10);
            list.add(2);
            list.add(7);
            System.out.println(list);
            list.addFirst(20);
            System.out.println(list);
            list.addFirst(9);
            System.out.println(list);
            System.out.println(STR."GetFirst: \{list.getFirst()}");
            System.out.println(STR."GetLast: \{list.getLast()}");
            System.out.println(STR."GetPrev(9): \{list.getPrev(9)}");
            System.out.println(STR."GetPrev(2): \{list.getPrev(2)}");
            System.out.println(STR."GetPrev(7): \{list.getPrev(7)}");

            System.out.println(STR."IndexOf(20): \{list.indexOf(20)}");
            System.out.println(STR."IndexOf(10): \{list.indexOf(10)}");
            System.out.println(STR."IndexOf(15): \{list.indexOf(15)}");

            System.out.println("\nPruebas del getNode");
            for (int i = 1; i <= list.size(); i++) {
                System.out.println("list.getNode("+i+"): "+list.getNode(i).data);
            }
            //System.out.println("list.getNode(100): "+list.getNode(100).data);

            list.clear();
            for (int i = 0; i < 50; i++) {
                list.add(util.Utility.getRandom(99));
            }
            System.out.println(list);
            System.out.println("Ordenamos la lista con el metodo sort()");
            list.sort();
            System.out.println(list);
            System.out.println("list.size(): "+list.size());

            System.out.println("Probamos suprimir");
            for (int i = 0; i <10 ; i++) {
                int value = util.Utility.getRandom(99);
                if(list.contains(value)){
                    list.remove(value);
                    System.out.println("Se ha eliminado el elemento: "+value);
                }
            }
            System.out.println(list);

            // Prueba de removeFirst
            System.out.println("Probamos removeFirst()");
            System.out.println("Elemento eliminado al principio: " + list.removeFirst());
            System.out.println("Lista después de removeFirst(): " + list);

            // Prueba de removeLast
            System.out.println("Probamos removeLast()");
            System.out.println("Elemento eliminado al final: " + list.removeLast());
            System.out.println("Lista después de removeLast(): " + list);

        }catch (ListException ex){
            System.out.println(ex.getMessage());
        }
    }

//    @Test
    void customerTest() {
        try {
            SinglyLinkedList customerList = new SinglyLinkedList();

            customerList.add(new Customer(1, "Emma", 21, "111111111", "emma@gmail.com"));
            customerList.add(new Customer(2, "Mateo", 22, "222222222", "mateo@gmail.com) "));
            customerList.add(new Customer(3, "Isabella", 20, "333333333", "isabella@gmail.com"));
            customerList.add(new Customer(5, "Santiago", 21, "555555555", "santi@gmail.com"));
            customerList.add(new Customer(6, "Fabiana", 18, "666666666", "fabi@gmail.com"));
            customerList.add(new Customer(7, "María", 23, "777777777", "maria@gmail.com"));
            customerList.add(new Customer(8, "Carlos", 25, "888888888", "carlos@gmail.com"));
            customerList.add(new Customer(9, "Camila", 19, "999999999", "camila@gmail.com"));
            customerList.add(new Customer(10, "Luka", 30, "101010101", "luka@gmail.com"));
            customerList.add(new Customer(4, "Victoria", 27, "444444444", "victoria@gmail.com"));


            System.out.println(customerList); //Imprimir por consola la lista original


            System.out.println(" ");
            System.out.println("¿Camila Id=9 is a Customer? " + customerList.contains(new Customer(9, "Camila")));
            System.out.println("¿Isabella Id=3 is a Customer?  " + customerList.contains(new Customer(3, "Isabella")));
            System.out.println("¿Carlos Id=5 is a Customer? " + customerList.contains(new Customer(5, "Carlos")));
            System.out.println("¿Carlos Id=8 is a Customer?  " + customerList.contains(new Customer(8, "Carlos")));

            
            //EJERCICIO 3:recorrer la lista y mostrar los elementos almacenados en cada nodo
            System.out.println(" ");
            System.out.println("III. RECORRER LA LISTA Y MOSTRAR LOS ELEMENTOS ALMACENADOS EN CADA NODO");
            System.out.println(" ");
            for (int i = 1; i <= customerList.size(); i++) { //Ciclo que recorre la lista, siempre y cuando la variable i sea menor al tamaño que posee la lista

                try { //Excepcion
                    Node currentNode = customerList.getNode(i); //Se declara una variable de tipo Node la cual obtiene el nodo en la posicion i de la lista
                    Customer customer = (Customer) currentNode.getData(); //Se declara una variable customer de tipo Customer la cual va a obtener los datos o elementos que posee cada Node en la posicion i
                    System.out.println("Customer at position " + i + ": " + customer.toString()); //Se manda un mensaje por consola que muestra cada elemento que tiene el Node en la forma en la que se indico en ele metodo toString de la clase Customer

                } catch (ListException e) {
                    System.out.println(e.getMessage()); //Muestra mensaje si ocurre una excepcion
                }
            }

            /*EJERCICIO 4
            Determinar el índice de ciertos elemento
            En este caso se analizan los id de los customers por medio del metodo indexOf de la clase Single, esto con el fin de analizar si ese id se encuentra almacenado en la lista
            Los demas atributos del constructor van vacios, ya que solo interesa analizar el id del objeto, en caso de que se quiere analizar todo el arreglo, entonces se debe de completar todos los espacios del objeto
            Se llama al metodo indexOf, el cual realiza una comprobacion de que el objeto este dentro de la lista y en cual indice es en el que se encuentra

            En caso de que si se encuentre el id, debe de retornar el indice en el que esta posicionado ese elemento
             */

            System.out.println(" ");
            System.out.println("IV. DETERMINAR LOS INDICES DE CIERTOS ELEMENTOS DE LA LISTA");
            System.out.println("Customer Santiago, Id=5 exists at position: " + customerList.indexOf(new Customer(5, "Santiago", 0, "", "")));
            System.out.println("Customer Fabiana, Id=6 exists at position: " + customerList.indexOf(new Customer(6, "Fabiana", 0, "", "")));
            System.out.println("Customer Luka, Id=10 exists at position: " + customerList.indexOf(new Customer(10, "Luka", 0, "", "")));


            /*
            EJERCICIO 5
            Ordenar la lista de clientes por nombre

            En este caso, se creo un segundo metodo sort, llamado sortName(), debido a que la burbuja creada en el metodo sort() no funcionaba para ordenar los nombres sino que ordenaba los id de los Customers
            Es por esta razon que se decidio crear otra segunda burbuja la cual se encarga de ordenar la lista conforme al nombre de cada objeto
             */
            System.out.println(" ");
            System.out.println("V. ORDENAR LA LISTA DE CUSTOMERS POR NOMBRE");
            System.out.println(" ");
            customerList.sortName(); //Llamar al metodo que ordena la lista conforme al nombre
            System.out.println(customerList); // Mostrar el contenido de la lista ordenada por nombres


            //EJERCICIO 6. REMOVER LOS ID PARES Y ORDENAR LOS ID IMPARES EN UNA NUEVA LISTA

            System.out.println("VI. REMOVER LOS ID PARES Y ORDENAR LOS ID IMPARES EN UNA NUEVA LISTA");

            //Se instancia una segunda lista, la cual va a estar vacia, esto con el fin de que la lista original no se vea alterada al suprimir elementos de la lista
            SinglyLinkedList secondList= new SinglyLinkedList();

            try {
                int index = 1; //Se declara una variable entera con valor inicial 1, es un contador

                while (index <= customerList.size()) { //Se crea un ciclo que verifica si la condicion se cumple, que es cuando el index sea menor o igual al tamaño de la lista original, entonces se sigue el ciclo

                    Node currentNode = customerList.getNode(index);//Declarar una variable de tipo Node que obtenga los nodos de la lista original con respecto al indice
                    Customer customer = (Customer) currentNode.getData(); //Declarar una variable de tipo Customer en donde se almacenan los datos que se extraen del nodo

                    if (customer.getId() % 2 != 0) { //Condicion en el que analiza si el dato obtenido (id) al dividirlo por 2, el producto sea cero, es decir, verifica si el id procesado es un numero par o impar.
                        secondList.add(customer); //En caso de que sea par (que el resultado de la division sea diferente de cero, se debe almacenar ese objeto en la nueva lista
                    }

                    index++; // Incrementador para seguir analizando los demas nodos de la lista


                }
            } catch (ListException e) {
                System.out.println(e.getMessage());
            }

            // Luego de haber almacenado los id impares en la nueva lista, se ordena con el metodo sort(); implementado en la clase Single, esto para ordenar los elementos segun el id
            try {
                secondList.sort();
            } catch (ListException e) {
                System.out.println(e.getMessage());
            }

            // Mostrar el contenido de la lista por consola
            System.out.println(secondList);


            //EJERCICIO 7. SEPARAR LOS ELEMENTOS POR RANGO DE EDAD
            //Rango de edad
            System.out.println(" ");
            System.out.println("VII.SEPARAR LOS ELEMENTOS POR RANGO DE EDAD");
            System.out.println(" ");
            ListAgeRange(customerList, 0, 20); //Se pasa como parametro los rangos de edad y se llama a la lista original para dividir los objetos comforme a su edad
            ListAgeRange(customerList, 20, 23);
            ListAgeRange(customerList, 25, 30);

        }catch (ListException le){

        }


    }

    void ListAgeRange(SinglyLinkedList customerList, int minimumRange, int maximumRange) throws ListException {

        System.out.println("Customers with age range between " + minimumRange + " and " + maximumRange + " years:");

        for (int i = 1; i <= customerList.size(); i++) { //Ciclo para recorrer el tamaño de la lista hasta recorrer todo el tamaño de la lista

            Node currentNode = customerList.getNode(i); //Se declara una variable de tipo Node la cual obtiene el nodo en la posicion i de la lista
            Customer customer = (Customer)currentNode.getData(); //Declarar una variable de tipo Customer en donde se almacenan los datos que se extraen del nodo

            if (customer.getAge() >= minimumRange && customer.getAge() <= maximumRange) { //Condicion que verifica si se cumple que la edad que posee cada customer, este entre el intervalo que se esta pasando como parametro en el metodo, en caso de que no sea asi, entonces ese elemento no formara parte de ese rango de edades
                System.out.println("Customer: " + customer.getId() + " " + customer.getName() + " " + customer.getAge());
            }
        }
    }



    /*@Test*/
        void testDoublyLinkedList() {
            try {
                DoublyLinkedList list = new DoublyLinkedList();
                list.add(15);
                list.add(10);
                list.add(2);
                list.add(7);
                System.out.println(list);
                list.addFirst(20);
                System.out.println(list);
                list.addFirst(9);
                System.out.println(list);
                System.out.println("GetFirst: " + list.getFirst());
                System.out.println("GetLast: " + list.getLast());
                System.out.println("GetPrev(9): " + list.getPrev(9));
                System.out.println("GetPrev(2): " + list.getPrev(2));
                System.out.println("GetPrev(7): " + list.getPrev(7));

                System.out.println("IndexOf(20): " + list.indexOf(20));
                System.out.println("IndexOf(10): " + list.indexOf(10));
                System.out.println("IndexOf(15): " + list.indexOf(15));

                System.out.println("\nPruebas del getNode");
                for (int i = 1; i <= list.size(); i++) {
                    System.out.println("list.getNode(" + i + "): " + list.getNode(i).data);
                }

                list.clear();
                for (int i = 0; i < 50; i++) {
                    list.add(util.Utility.getRandom(99));
                }
                System.out.println(list);
                System.out.println("Ordenamos la lista con el metodo sort()");
                list.sort();
                System.out.println(list);
                System.out.println("list.size(): " + list.size());

                System.out.println("Probamos suprimir");
                for (int i = 0; i < 10; i++) {
                    int value = util.Utility.getRandom(99);
                    if (list.contains(value)) {
                        list.remove(value);
                        System.out.println("Se ha eliminado el elemento: " + value);
                    }
                }
                System.out.println(list);


                // Prueba de removeFirst
                System.out.println("Probamos removeFirst()");
                System.out.println("Elemento eliminado al principio: " + list.removeFirst());
                System.out.println("Lista después de removeFirst(): " + list);

                // Prueba de removeLast
                System.out.println("Probamos removeLast()");
                System.out.println("Elemento eliminado al final: " + list.removeLast());
                System.out.println("Lista después de removeLast(): " + list);

            } catch (ListException ex) {
                System.out.println(ex.getMessage());
            }
        }


            @Test
            void productTest() {
                try {
                    DoublyLinkedList productList = new DoublyLinkedList();
                    productList.add(new Product(1, "Accesorios para baño", 20, 50));
                    productList.add(new Product(2, "Suministros eléctricos", 150, 15));
                    productList.add(new Product(3, "Escaleras", 45, 20));
                    productList.add(new Product(4, "Aspiradoras", 140, 5));
                    productList.add(new Product(5, "Organizadores para garaje", 35, 30));
                    productList.add(new Product(6, "Detergentes líquidos", 30, 200));
                    productList.add(new Product(7, "Acondicionadores para ropa", 25, 150));
                    productList.add(new Product(8, "Aromatizantes de ambiente", 27, 250));
                    productList.add(new Product(9, "Limpiadores de todo tipo", 15, 500));
                    productList.add(new Product(10, "Desinfectantes de superficies", 40, 350));

                    System.out.println(productList);
                    System.out.println("¿Aspiradoras Id=1 are in the product list? " + productList.contains(new Product(1, "Aspiradoras")));
                    System.out.println("¿Escaleras Id=5 are in the product list? " + productList.contains(new Product(5, "Escaleras")));
                    System.out.println("¿Acondicionadores para ropa Id=7 are in the products list? " + productList.contains(new Product(7, "Acondicionadores para ropa")));
                    System.out.println("¿Desinfectantes de superficies Id=10 are in the products list? " + productList.contains(new Product(10, "Desinfectantes de superficies")));


                    //GetNode
                    System.out.println();
                    System.out.println("Elementos en cada nodo ");
                    for (int i = 1; i <= productList.size(); i++) {
                        Node node = productList.getNode(i);
                        System.out.println("Posición " + i + ": " + node.data.toString());
                    }


                    //Indice segun producto
                    Product escaleras = new Product(3, "Escaleras", 0, 0);
                    Product organizadores = new Product(5, "Organizadores para garaje", 0, 0);
                    Product limpiadores = new Product(9, "Limpiadores de todo tipo", 0, 0);

                    System.out.println();
                    System.out.println("Encontrar productos específicos");
                    System.out.println("Product Escaleras, Id=3 stored at position:" + productList.indexOf(escaleras));
                    System.out.println("Product Organizadores para garaje, Id=5 stored at position:" + productList.indexOf(organizadores));
                    System.out.println("Product Limpiadores de todo tipo, Id=9 stored at position:" + productList.indexOf(limpiadores));

                    //Ordenar lista
                    for (int i = 1; i <= productList.size(); i++) {
                        for (int j = i + 1; j <= productList.size(); j++) {

                            String product1 = ((Product) productList.getNode(i).data).getName();
                            String product2 = ((Product) productList.getNode(j).data).getName();

                            //Si a producto2 menor a el 1, entonces los intercambia
                            if (product2.compareTo(product1) < 0) {

                                //Mueve para ser colocado antes del producto1
                                Object aux = productList.getNode(i).data;
                                productList.getNode(i).data = productList.getNode(j).data;
                                productList.getNode(j).data = aux;
                            }
                        }
                    }

                    System.out.println();
                    System.out.println("Productos ordenados por nombre:");
                    System.out.println(productList);


                    // Eliminar productos con idImpar y ordenar la lista
                    DoublyLinkedList secondList = new DoublyLinkedList();

                    int index = 1;
                    while (index <= productList.size()) {
                        Node currentNode = productList.getNode(index);
                        Product product = (Product) currentNode.getData();

                        if (product.getId() % 2 == 0) {
                            secondList.add(product);
                        }
                        index++;
                    }

                    secondList.sort();
                    System.out.println("Productos con IDs impares eliminados y lista ordenada:");
                    System.out.println(secondList);


                    // Mostrar productos por rango de precio
                    boolean foundProduct = false;
                    System.out.println("\nProductos con precio menos de 100:");
                    for (int i = 0; i <= productList.size(); i++) {
                        Node node = productList.getNode(i);
                        if (node != null) {
                            Product product = (Product) productList.getNode(i).data;

                            //Menos de 100
                            if (product.getPrice() < 100) {
                                System.out.println(product);
                                foundProduct = true;
                            }
                            foundProduct = false;
                        }
                    }

                    System.out.println("\nProductos con precio de 100 a 300:");
                    for (int i = 0; i < productList.size(); i++) {
                        Node node = productList.getNode(i);
                        if (node != null) {
                            Product product = (Product) productList.getNode(i).data;
                            if (product.getPrice() >= 100 && product.getPrice() <= 300) {
                                System.out.println(product);
                                foundProduct = true;
                            }
                            foundProduct = false;
                        }
                    }

                    System.out.println("\nProductos con precio más de 300:");
                    for (int i = 0; i < productList.size(); i++) { // Cambio de '<=' a '<'
                        Node node = productList.getNode(i);
                        if (node != null) {
                            Product product = (Product) node.getData();

                            if (product.getPrice() > 300) {
                                System.out.println(product);
                                foundProduct = true;
                            }
                            foundProduct = false;
                        }
                    }

                    if (!foundProduct)
                        System.out.println("No hay productos con precio mayor a 300");


                } catch (ListException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }





