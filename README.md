# PSP-Actividad1-HilosYSockets

AE-1. Hilos y Sockets - Programación de Servicios y Procesos 

Alberto Arroyo Santofimia


**Requerimiento 1**

**SockerServidor**

![Imagen](imgReadme/i005.png)

**SocketCliente**

Elegimos la **opción 1**

Pedimos el ID 1

![Imagen](imgReadme/i006.png)

En caso de que el ID no sea numérico dará un error y nos dirá que lo introduzcamos de nuevo

![Imagen](imgReadme/i007.png)

El SocketServidor nos va informando de lo que se va pidiendo

![Imagen](imgReadme/i008.png)

Elegimos la **opción 2**

Buscamos la pelicula Terminator

![Imagen](imgReadme/i009.png)

En caso de no encontrar la película nos devolverá null

![Imagen](imgReadme/i010.png)

Se abre un hilo distinto por cada cliente

![Imagen](imgReadme/i026.png)

Elegimos la opción para salir del programa y terminar la conexión 

![Imagen](imgReadme/i011.png)

SocketServidor

![Imagen](imgReadme/i012.png)

**Requerimiento 2**

Buscar películas por director, nos deberá devolver si hay varias películas por director

![Imagen](imgReadme/i013.png)

SocketServidor

![Imagen](imgReadme/i014.png)

**Requerimiento 3**

Añado la opción de añadir películas, si un hilo ha empezado a añadir una película, ningún otro hilo puede añadir ninguna película hasta que el primero haya terminado. 

Cliente 1

![Imagen](imgReadme/i015.png)

SocketServidor

![Imagen](imgReadme/i016.png)

Cliente 2

![Imagen](imgReadme/i017.png)

Vemos que el Cliente2 se ha quedado a la espera de que el Cliente1 termine

Terminamos de añadir la película del Cliente1

![Imagen](imgReadme/i018.png)



Una vez introducidos los datos del Cliente1, el objeto lista de las películas ya deja al otro hilo meter su pelicula.

![Imagen](imgReadme/i019.png)

Compruebo que se han metido las dos películas en la lista de películas 

![Imagen](imgReadme/i020.png)

![Imagen](imgReadme/i021.png)

Si se intenta introducir una película con un ID ya existente la película no se añadirá a la lista y lo notificará

![Imagen](imgReadme/i022.png)

Buscamos si esta la película Spiderman en el ID 1

![Imagen](imgReadme/i023.png)

Si se introduce una cadena en el ID o el precio dará error y lo pedirá de nuevo

![Imagen](imgReadme/i024.png)

Se ha metido correctamente

![Imagen](imgReadme/i025.png)
