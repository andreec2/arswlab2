# lab02_ARSW
# Estructura del proyecto.
![image](https://github.com/user-attachments/assets/6d9efc5a-c604-4b54-b58c-8de00b06a31f)

# Instrucciones para ejecutar el programa.

Clonar el Repositorio: 
Primero, clona el repositorio desde GitHub a tu máquina local. Abre una terminal y ejecuta el siguiente comando:

  git clone https://github.com/andreec2/arswlab2.git
  cd arswlab2

Compilar el Proyecto: 
Navega al directorio raíz del proyecto (donde se encuentra el archivo pom.xml) y ejecuta:

  mvn clean install

Ejecutar el Proyecto:

  Navega hasta la clase "main" del paquete  "wait-notify-excersice" para poder ejecutar el primer punto, o a la clase "SnakeApp" del paquete "ssnae-race-thread-concurrency" para poder ejecutar el segundo    punto.
---------------------------------
# Laboratorio 2 - Arquitecturas de Software

Este repositorio contiene el código y experimentos realizados para el Laboratorio No. 2 de la materia de Arquitecturas de Software del semestre 2024-2. El objetivo principal de este laboratorio es aprender y aplicar conceptos de programación concurrente y sincronización en Java. Vamos a enfocarnos en cómo controlar la ejecución de múltiples hilos, utilizando wait, notify, y notifyAll, para evitar problemas como condiciones de carrera y asegurar la consistencia de los datos.

# Contenido

# Parte l  

objetivo: implementar un mecanismo para detener los hilos despues de un tiempo especifico y esperar a que el usuario continue, por ultimo se buscara identificar problemas de concurrencia y se haran las mejoras necesarias para que el codigo sea mas eficiente 

![image](https://github.com/user-attachments/assets/fcad293b-d394-442d-b20d-9adfcdb5f9c8)

### Verificar que el Usuario Presionó ENTER
![image](https://github.com/user-attachments/assets/8b4b765d-960c-4e06-9013-96e893bebf74)

### Salida para 3000000 datos
![image](https://github.com/user-attachments/assets/bbd853c6-f1af-4dde-a4ec-8c85f614f65f)

# Parte ll 

Para la parte dos el objetivo principal es agregar la funcionalidad de iniciar, pausar y reanudar el juego 

### Creacion de botones para iniciar, pausar y reanudar 
![image](https://github.com/user-attachments/assets/20041748-68fa-40d0-8d9d-a3a4f5ce29dc)

![image](https://github.com/user-attachments/assets/2b549065-957e-4c50-a6d8-1694eff9d4a3)

### ActionListener para los botones de iniciar, pausar y reanudar
![image](https://github.com/user-attachments/assets/07876259-efd2-41f0-8625-fd75635dc105)

por ultimo se mostrara informacion importante del juego en tiempo real para conocer todo lo que no puede ofrecer como tal.

### Metodo para mostrar informacion importante cuando se pausa el juego 
![image](https://github.com/user-attachments/assets/2531f956-1e12-4e2b-83eb-3b95e58a2129)

### Juego 
![image](https://github.com/user-attachments/assets/b9f08e3f-9b8b-47ef-9d5d-e217ca6bfdbd)

## Autores
- **Juan Daniel Murcia Sánchez**
- **Juan David Parroquiano Roldán**
- **Andrés Felipe Montes Ortiz**


