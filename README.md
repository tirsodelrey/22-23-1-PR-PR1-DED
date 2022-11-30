[
# ****Practica DED Otoño 2022 - Tirso del Rey Gumb****

## Introducción

Durante la práctica se implementaron una serie de TAD (Tipos Abstractos de Datos) para dar soporte a una aplicación de gestión de eventos deportivos, para la cual también se modelaron los objetos correspondientes, y las estructuras que los gestionan, estudiadas en la PEC1 de la asignatura.

Se desarrollarón también una batería de excepciones que contemplan e informan de los posibles errores o limitaciones al interactuar con la aplicación.

Por último, se añadieron test adicionales para evaluar el correcto funcionamiento de los TADs implementados (OrderedVector y DictionaryOrderedVector).

Se analiza en más detalle a continuación los cambios realizados a partir de la plantilla proporcionada por el profesorado:

### **Excepciones**

Se implementaron las siguientes excepciones:
* LimitExceededException: devuelve un error cuando se excede el límite de inscripciones de jugadores en un evento.
* NoFilesException: devuelve un error cuando se busca actualizar una ficha y esta no existe.
* NoRatingsException: lanza un mensaje de error cuando se quieren obtener los ratings de un evento deportivo y este no tiene.
* NoSportEventsException: lanza un mensaje de error cuando se quieren devolver los eventos deportivos, ya sean totales, de un jugador o de una organización y no existen.
* OrganizingEntityNotFound: lanza un mensaje de error cuando no se encuentra la organziación buscada.
* PlayerNotFoundException: lanza un mensaje de error cuando el jugador que se pretende encontrar no existe.
* PlayerNotInSportEventException: devuelve un mensaje de error cuando el jugador buscado no se encuentra en el evento deportivo seleccionado.

### **Modelos**

Se implementaron los modelos `File`, `OrganizingEntity`, `Player`, `Rating` y `SportEvent`. Que sirven para dar la capa de abstracción necesaria a la aplicación gestora de eventos deportivos de los cuales conviene destacar los siguiente métodos o estructuras implementados por su importancia en el funcionamiento global de la aplicación:
* File: Implementa el método `update()` que permite cambiar el estado de una ficha y de esta manera aprobar un evento deportivo, que pasará a formar parte de una organización. También permite crear un nuevo evento deportivo mediante `newSportEvent()`;
* OrganizingEntity: Cuenta con una lista encadenada `LinkedList<SportEvent>`como se indicó en la PEC1. Permite añadir eventos deportivos a la lista encadenada `addSportEvent`.
* Player: Cuenta con un atributo de lista encadadenada `LinkedList<SportEvent>` para almacenar los eventos deportivos en los que participa, como se definió en la solución de la PEC1. Implementa un método auxuliar `isInEvent` que permite determinar si el jugador participa en el evento pasado por parámetro.
* Rating: Modelo sencillo que representa las valoraciones.
* SportEvent: Modela el evento deportivo. Cuenta con un atributo `QueueArrayImpl<Player> inscriptions` que es una cola que contiene las inscripciones de los jugadores al evento (FIFO). También implementa  una lista encadenada de valoraciones del evento `LinkedList<Rating> ratings`. Ambas estructuras definidas en la PEC1. También es preciso destacar que implementa la clase `Comparable`, que permitirá clasificar los eventos deportivos en función de sus ratings o del Id del evento (dependiendo de la estructura) en las estructuras que almacenan eventos deportivos, que se analizarán a continuación en la clase principal de la aplicación `Sports4ClubImpl`

### **Sports4ClubImpl**

#### En cuanto a las **estructuras de datos:** 

* Almacena un vector de jugadores `Player[] players` según lo especificado en la PEC1.
* Una cola de ficheros 'QueueArrayImpl<File>' según lo definido en la PEC1.
* Un vector para almacenar las organizaciones `OrganizingEntity[] organizingEntities`.
* Para almacenar los eventos deportivos **se desarrolló un nuevo TAD** `DictionaryOrderedVector<String, SportEvent>` que, por una parte, permite insertar los eventos deportivos de manera ordenada en función de su identificador (razón de ser del Comparable en el modelado de la clase SportEvent) mediante el método `put(K key, V value)`y acceder a los valores mediante las 'keys' asociadas haciendo uso de las funciones del TAD `DictionaryArrayImpl`, sin embargo, se modificó el metodo `get(key)` para que utilice la búsqueda dicotómica mediante el método auxiliar `BinarySearch` en este caso se hace en tiempo LogN.
* Para almacenar los eventos ordenados por valoraciones se implementó otro nuevo TAD `OrderedVector<SportEvent> sportEventsByRating` que ordena los eventos al insertarlos en base al parámetro de comparable (Rating) asociado al SportEvent pasado por parámetro en el método propio del TAD `update`.
* Esto permite que el evento mejor valorado sea fácilmente localizable si apuntamos a la primera posición del vector ordenado anterior:
  `public SportEvent bestSportEvent() throws SportEventNotFoundException {
  if(sportEventsByRating.size() == 0){
  throw new SportEventNotFoundException();
  }
  return sportEventsByRating.elementAt(0);`

#### Métodos relevantes implementados en la clase:
Se implementaron los métodos cuyas firmas se encontraban definidas en la interfaz `SportEventsClub`

Se resaltan algunos por la relevancia que tienen al trabajar con los TAD referenciados anteriormente.
* `updateFile` : Hace uso de las funciones del TAD Cola como `poll()` para devolver el primer elemento y el `update` de File descrito anteriormente en el apartado de modelos. Si la ficha no se aprueba `file.isEnabled())`se aumenta en una unidad el numero de ficheros rechazados.
* `signUpEvent`: Función para inscribir a un jugador en un evento que hace uso de las funciones auxiliares:
  * sportEvent.inscribePlayer(player);
  *  player.addSportEvent(sportEvent);
  * updateMostActivePlayer(player);
 
    Que actualizan la cola de inscripciones de SportEvent, la lista encadenada de eventos de un jugador y el apuntador al jugador con más eventos asociados, respectivamente.
* `updateMostActivePlayer` actualiza el jugador con más eventos deportivos asociados comparando dos jugadores y sus número de eventos asociados mediante la función auxiliar `numEvents()`.
* `addRating`: Añade una valoración a un evento deportivo con lo que llama primero la función `addRating` de SportEvent y luego utiliza la función `update` implementada en el `OrderedVector` de eventos deportivos explicado anteriormente.


### **ResourceUtil**

Se implementó de cero la clase `ResourceUtil` que permite a partir del _flag_ devuelto tras introducir diversos bytes como input al método `getFlag` determinar qué recursos tiene asignados un evento deportivo. Esto se consigue observando que bits se encuentran activos en el flag devuelto:

En `getFlag`se utilizó el operador _bitwise_ OR para almacenar todos los bits en el mismo flag:

        for(byte arg: args)
            flag |= arg;

        return flag;

Posteriormente, para determinar que bits se encontraban activos (1), se examinaba el byte devuelto por flag bit a bit en cada uno de los métodos a continuación, desde el bit más a la izquierda hasta el método final que comprueba si todos están activos:

    public static boolean hasPrivateSecurity(byte resource){
        return (resource & (1 << 0)) != 0;
    }

Y en función de ello devolver un booleano (true si activo, false si no) para cada uno de los bits.

    public static boolean hasPublicSecurity(byte resource){
        return (resource & (1 <<1)) != 0;
    }
    public static boolean hasVolunteers(byte resource){
        return (resource & (1 <<2)) != 0;
    }
    public static boolean hasBasicLifeSupport(byte resource){
        return (resource & (1 <<3)) != 0;
    }
    public static boolean hasAllOpts(byte resource){
       return hasPrivateSecurity(resource) && hasPublicSecurity(resource) && hasVolunteers(resource) && hasBasicLifeSupport(resource);
    }

### Test adicionales

Finalmente se desarrollaron las clases `DictionaryOrderedVectorTest` y `OrderedVectorTest` que comprueban que los nuevos TAD implementados para la práctica cumplan con unos estándares de funcionalidad en lo que respecta a sus métodos principales, ya que la implementación de estos no viene contemplada en la batería de test proporcionados por los profesores.
