package cliente;

import java.io.PrintStream;
import java.util.Scanner;

public class OpcionesCliente {
	
	//**********MODULAR LAS OPCIONES
	
	/**
	 * El metodo consultarPeliculaPorId() consulta una película por su ID y envia la solicitud al servidor.
	 *
	 * @param sc  representa la entrada del usuario desde su teclado.
	 * @param salida representa la conexion de salida de informacion, la informacion que enviamos al servidor.
	 */
	
	public void consultarPeliculaPorId(Scanner sc, PrintStream salida) {
		System.out.println("Introduzca ID de la película:");
		int peliculaId = 0;
		boolean entradaValida = false;
		//bucle para comprobar si hemos metido un numero y en caso de que no, se repita
		while (!entradaValida) {
		    try {
		        peliculaId = Integer.parseInt(sc.nextLine());
		        entradaValida = true; // Si son numeros los pasa a true
		    } catch (NumberFormatException e) {
		        System.out.println("Entrada no válida. Ingrese un número entero.");
		        System.out.println("Introduzca ID de la película:");
		    }
		}
		salida.println(peliculaId);
    }
	
	/**
	 * El metodo consultarPeliculaPorTitulo() consulta una pelicula por su titulo y envia la solicitud al servidor.
	 *
	 * @param sc  representa la entrada del usuario desde su teclado.
	 * @param salida representa la conexion de salida de informacion, la informacion que enviamos al servidor.
	 */
	
    public void consultarPeliculaPorTitulo(Scanner sc, PrintStream salida) {
    	System.out.println("Introduzca título de la película");
		String texto = sc.nextLine();
		salida.println(texto);
    }
    
    /**
     * El metodo consultarPeliculasPorDirector() consulta peliculas por el nombre del director y envia la solicitud al servidor.
     *
     * @param sc  representa la entrada del usuario desde su teclado.
	 * @param salida representa la conexion de salida de informacion, la informacion que enviamos al servidor.
	 */

    public void consultarPeliculasPorDirector(Scanner sc, PrintStream salida) {
    	System.out.println("Introduzca nombre del director");
		String texto = sc.nextLine();
		salida.println(texto);
    }
    
    /**
     * El metodo agregarPelicula() agrega una película con detalles proporcionados por el usuario y envia la solicitud al servidor.
     *
     * @param sc  representa la entrada del usuario desde su teclado.
	 * @param salida representa la conexion de salida de informacion, la informacion que enviamos al servidor.
	 */

    public void agregarPelicula(Scanner sc, PrintStream salida) {
    	System.out.println("Introduzca ID de la película:");
		int id = 0;
		boolean entradaValidaId2 = false;
		//bucle para comprobar si hemos metido un numero y en caso de que no, se repita
		while (!entradaValidaId2) {
		    try {
		        id = Integer.parseInt(sc.nextLine());
		        entradaValidaId2 = true; // Si son numeros los pasa a true
		    } catch (NumberFormatException e) {
		        System.out.println("Entrada no válida. Ingrese un número entero.");
		        System.out.println("Introduzca ID de la película:");
		    }
		}
	    salida.println(id);
	  //  sc.nextLine(); // Limpiar el búfer de nueva línea
	    System.out.println("Introduzca el título de la película:");
	    String title = sc.nextLine();
	    salida.println(title);
	    System.out.println("Introduzca el director de la película:");
	    String director = sc.nextLine();
	    salida.println(director);
	    System.out.println("Introduzca el precio de la película:");
	    double precio = 0;
		boolean entradaValidaPrecio = false;
		//bucle para comprobar si hemos metido un numero y en caso de que no, se repita
		while (!entradaValidaPrecio) {
		    try {
		        precio = Double.parseDouble(sc.nextLine());
		        entradaValidaPrecio = true; // Establece la bandera en verdadera si la conversión tiene éxito
		    } catch (NumberFormatException e) {
		        System.out.println("Entrada no válida. Ingrese un valor numérico.");
		        System.out.println("Introduzca el precio de la película:");
		    }
		}
		salida.println(precio);
	  //  leer.nextLine(); // Limpiar el búfer de nueva línea
	   // opcion = menu();
    }

}
