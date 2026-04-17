# TRAVELBOOKER DAO
Nombre: Sara Romero Peralta
Curso: 1ºDAW

## CE 7.c - Librerías y clases utilizadas

### Clases utilizadas

En la implementación de la práctica se han utilizado varias clases propias del proyecto y del lenguaje Kotlin para gestionar el acceso a datos mediante ficheros.

Las clases más relevantes son:

- `ReservaDAO`: Clase encargada de implementar el acceso a datos mediante ficheros, siguiendo el patrón DAO. Gestiona las operaciones de lectura y escritura sobre el archivo.
  🔗 https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-Sromerop0610/blob/c52d3589d8db39aa1885ce95dd4e7023a584edd7/src/main/kotlin/es/iesra/datos/ReservaDAO.kt#L6

- `IReservaDAO`: Interfaz que define las operaciones CRUD (guardar, obtener, actualizar y eliminar), permitiendo desacoplar la lógica de acceso a datos.
  🔗 https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-Sromerop0610/blob/c52d3589d8db39aa1885ce95dd4e7023a584edd7/src/main/kotlin/es/iesra/datos/IReservaDAO.kt#L5-L10

- `ReservaRepository`: Clase que actúa como intermediario entre la capa de servicio y el DAO, delegando en este último el acceso a los datos.
  🔗 https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-Sromerop0610/blob/c52d3589d8db39aa1885ce95dd4e7023a584edd7/src/main/kotlin/es/iesra/datos/ReservaRepository.kt#L5

Estas clases permiten mantener la separación de responsabilidades dentro de la arquitectura por capas.


### Librerías utilizadas

Para el manejo de ficheros se han utilizado clases de la librería estándar de Java:

- `java.io.File`: Representa el fichero donde se almacenan las reservas y permite realizar operaciones de lectura y escritura.
  https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-Sromerop0610/blob/c52d3589d8db39aa1885ce95dd4e7023a584edd7/src/main/kotlin/es/iesra/datos/ReservaDAO.kt#L4
### Métodos más relevantes

Dentro de la clase `File` se han utilizado los siguientes métodos:

- `exists()`: Comprueba si el archivo ya existe.
- `createNewFile()`: Crea el fichero si no existe.
- `appendText(String)`: Añade una nueva línea al final del archivo sin sobrescribirlo.
- `readLines()`: Lee todas las líneas del fichero y las devuelve como una lista.
- `writeText(String)`: Sobrescribe completamente el contenido del fichero.

Estos métodos han sido fundamentales para implementar el almacenamiento persistente en archivos de texto.
## CE 7.d

### 2.a ¿Qué formato se ha utilizado para almacenar la información?

Se ha utilizado un formato de texto plano estructurado, donde cada línea del fichero representa una reserva.

Cada línea comienza con un identificador que indica el tipo de reserva:

- "HOTEL" para reservas de hotel
- "VUELO" para reservas de vuelo

Los diferentes campos se separan mediante el carácter "|" (pipe), lo que facilita su posterior lectura y procesamiento.

Ejemplos de líneas almacenadas:

HOTEL|1|Vacaciones|Madrid|3|2026-04-16T12:00  
VUELO|2|Trabajo|Sevilla|Paris|15:30|2026-04-16T13:00

Este formato permite transformar fácilmente cada línea en un objeto del dominio mediante el uso del método `split("|")`.

🔗 https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-Sromerop0610/blob/c52d3589d8db39aa1885ce95dd4e7023a584edd7/src/main/kotlin/es/iesra/datos/ReservaDAO.kt#L14-L29

---

### 2.b ¿Qué estrategia se ha usado para trabajar con los ficheros?

Se ha utilizado una estrategia basada en un único fichero de texto para almacenar todas las reservas.

- Carpeta: `data/`
- Fichero: `reservas.txt`

El fichero se crea automáticamente al inicializar el DAO, si no existe previamente, utilizando el método `createNewFile()`.

🔗 https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-Sromerop0610/blob/c52d3589d8db39aa1885ce95dd4e7023a584edd7/src/main/kotlin/es/iesra/datos/ReservaDAO.kt#L7-L12

Las operaciones se realizan de la siguiente manera:

- **Crear (guardar):** se añade una nueva línea al final del fichero con `appendText()`
- **Leer:** se leen todas las líneas del fichero con `readLines()`
- **Actualizar y eliminar:** se sigue una estrategia de:
    1. Leer todo el fichero
    2. Modificar los datos en memoria
    3. Reescribir completamente el fichero con `writeText()`

Esto se debe a que los ficheros de texto no permiten modificar directamente una línea concreta.

🔗 https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-Sromerop0610/blob/c52d3589d8db39aa1885ce95dd4e7023a584edd7/src/main/kotlin/es/iesra/datos/ReservaDAO.kt#L61-L97

---

### 2.c ¿Cómo se gestionan los errores?

La gestión de errores se ha realizado en varias capas del sistema:

#### Validaciones en el dominio

Se utilizan validaciones mediante `require()` para asegurar la integridad de los datos:

- Número de noches mayor que 0 en reservas de hotel
- Formato correcto de la hora en reservas de vuelo mediante expresiones regulares

🔗 https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-Sromerop0610/blob/c52d3589d8db39aa1885ce95dd4e7023a584edd7/src/main/kotlin/es/iesra/dominio/ReservaHotel.kt#L32-L37
🔗 https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-Sromerop0610/blob/c52d3589d8db39aa1885ce95dd4e7023a584edd7/src/main/kotlin/es/iesra/dominio/ReservaVuelo.kt#L34-L41

#### Excepciones

Se lanzan excepciones del tipo `IllegalArgumentException` cuando:

- Los datos no cumplen las validaciones
- El tipo de reserva no es reconocido al procesar el fichero

🔗 https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-Sromerop0610/blob/c52d3589d8db39aa1885ce95dd4e7023a584edd7/src/main/kotlin/es/iesra/datos/ReservaDAO.kt#L41-L56

#### Control en la interfaz de usuario

En la capa de presentación se utilizan bloques `try-catch` para capturar errores y evitar que la aplicación finalice de forma inesperada.

🔗 https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-Sromerop0610/blob/c52d3589d8db39aa1885ce95dd4e7023a584edd7/src/main/kotlin/es/iesra/presentacion/ConsolaUI.kt#L38-L40

Este enfoque permite separar la validación de datos, el control de errores y la interacción con el usuario, manteniendo una arquitectura limpia y robusta.

## CE 7.e

### 3.a Describe la forma de acceso para leer información

La lectura de información se realiza accediendo al fichero de texto mediante la clase `File` y el método `readLines()`, que devuelve una lista de líneas.

Cada línea representa una reserva, por lo que se procesa individualmente. Para reconstruir los objetos del dominio, se utiliza el método `split("|")` para separar los campos.

Posteriormente, se identifica el tipo de reserva (HOTEL o VUELO) y se crea el objeto correspondiente mediante los métodos `creaInstancia()`.

Este proceso permite transformar los datos almacenados en texto en objetos utilizables dentro de la aplicación.

🔗 https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-Sromerop0610/blob/c52d3589d8db39aa1885ce95dd4e7023a584edd7/src/main/kotlin/es/iesra/datos/ReservaDAO.kt#L31-L59

---

### 3.b Describe la forma de acceso para escribir información

La escritura de información se realiza utilizando el método `appendText()` de la clase `File`.

Antes de escribir, el objeto `Reserva` se transforma en una cadena de texto siguiendo un formato estructurado con separadores ("|").

Cada nueva reserva se añade al final del fichero como una nueva línea, sin sobrescribir el contenido existente.

Este enfoque permite almacenar múltiples registros de forma secuencial en el archivo.

🔗 https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-Sromerop0610/blob/c52d3589d8db39aa1885ce95dd4e7023a584edd7/src/main/kotlin/es/iesra/datos/ReservaDAO.kt#L14-L29

---

### 3.c Describe la forma de acceso para actualizar información

La actualización de información en un fichero de texto no puede hacerse directamente sobre una línea concreta, por lo que se utiliza la siguiente estrategia:

1. Se leen todas las líneas del fichero con `readLines()`
2. Se recorre cada línea y se identifica aquella que corresponde a la reserva a modificar (mediante el id)
3. Se sustituye la línea antigua por una nueva con los datos actualizados
4. Se reescribe completamente el fichero utilizando `writeText()`

Este mismo enfoque se utiliza también para la eliminación de registros, filtrando las líneas que no se desean conservar.

Este método es necesario debido a las limitaciones del acceso secuencial en archivos de texto.

🔗 https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-Sromerop0610/blob/c52d3589d8db39aa1885ce95dd4e7023a584edd7/src/main/kotlin/es/iesra/datos/ReservaDAO.kt#L71-L97
🔗 https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-Sromerop0610/blob/c52d3589d8db39aa1885ce95dd4e7023a584edd7/src/main/kotlin/es/iesra/datos/ReservaDAO.kt#L61-L70