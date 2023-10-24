package cliente.hilo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Esta clase representa un cliente para la aplicación de biblioteca virtual que se comunica
 * con el servidor a través de sockets. Creara un hilo nuevo por cada cliente nuevo
 * 
 * @author Alberto Arroyo Santofimia
 * 
 * @version v1.0
 */

public class SocketCliente {
	
	// IP y Puerto a la que nos vamos a conectar
	public static final int PUERTO = 3333;
	public static final String IP_SERVER = "localhost";
	private static Scanner leer;
		
	static {
			leer = new Scanner(System.in);
	}

	public static void main(String[] args) {
		System.out.println("-----------------------------------");
		System.out.println("   APLICACION CLIENTE BIBLIOTECA   ");
		System.out.println("-----------------------------------");

		InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);
		
		try (Scanner sc = new Scanner(System.in)){
						
			System.out.println("CLIENTE: Esperando a que el servidor acepte la conexion");
			Socket socketAlServidor = new Socket();
			socketAlServidor.connect(direccionServidor);
			System.out.println("CLIENTE: Conexion establecida... a " + IP_SERVER + 
					" por el puerto " + PUERTO);

			InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
			BufferedReader entradaBuffer = new BufferedReader(entrada);
			
			PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
			
			String texto = "";
			boolean continuar = true;
			do {
				
				//Cargamos el menu inicial y recuperamos la opción elegida
				int opcion = menu();
				//Si la opcion está fuera del rango de opciones se repetira el menu
				while (opcion<1 || opcion>5){
					opcion = menu();
				}
				//enviamos la opcion elegida al servidor como string
				salida.println(String.valueOf(opcion));
				//Segun la opcion elegida, guardamos la respuesta en un string y la enviamos al servidor
				switch(opcion) {
					case 1: // Consultar película por ID
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
						break;
					case 2: //Consultar película por título
						System.out.println("Introduzca título de la película");
						texto = sc.nextLine();
						salida.println(texto);
						break;
					case 3: //Consultar película por nombre del director
						System.out.println("Introduzca nombre del director");
						texto = sc.nextLine();
						salida.println(texto);
						break;
					case 4://Añadir pelicula nueva
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
					    break;				    
					case 5:// Salir de la aplicación
						salida.println(String.valueOf(opcion));
						break;
					default:
                        System.out.println("Opción no válida, intente de nuevo.");
											
				}
					
				System.out.println("CLIENTE: Esperando respuesta ...... ");				
				String respuesta = entradaBuffer.readLine();
				//String peliculaInfo;
				
				
				if("OK".equalsIgnoreCase(respuesta)) {
					continuar = false;
				}else {
					
					System.out.println("CLIENTE: Servidor responde: \n" + respuesta);
				   
					while (!(respuesta = entradaBuffer.readLine()).equals("FIN_BUSQUEDA")) {
				        // Procesa cada línea de película
						
						System.out.println(respuesta);
						
				    /*    String[] datosPelicula = respuesta.split(",");
				        int id = Integer.parseInt(datosPelicula[0]);
				        String titulo = datosPelicula[1];
				        String director = datosPelicula[2];
				        double precio = Double.parseDouble(datosPelicula[3]);

				        System.out.println("ID: " + id + ", Título: " + titulo + ", Director: " + director + ", Precio: " + precio);
				        */
						//peliculaInfo = entradaBuffer.readLine();
						
				    }
				}				
			}while(continuar);
			//Cerramos la conexion
			socketAlServidor.close();
		} catch (UnknownHostException e) {
			System.err.println("CLIENTE: No encuentro el servidor en la direccion" + IP_SERVER);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("CLIENTE: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("CLIENTE: Error -> " + e);
			e.printStackTrace();
		}
		leer.close();
		System.out.println("CLIENTE: Fin del programa");

	}
	/**
     *  El metodo menu() muestra un menú de opciones y permite al usuario seleccionar una.
     *
     * @return un numero entero, que representa la opción seleccionada por el usuario.
     */
	
	//Menu inicial de la aplicacion
	public static int menu() {
			
		int opcion = 0;
		System.out.println("----------------------------------------------------");
		System.out.println("|                      MENU                        |");
		System.out.println("----------------------------------------------------");
		System.out.println("1. Consultar pelicula por ID");
		System.out.println("2. Consultar película por título ");
		System.out.println("3. Consultar películas por director ");
		System.out.println("4. Añadir pelicula ");
		System.out.println("5. Salir de la aplicación");
		System.out.println("----------------------------------------------------");
		System.out.println("Introduzca una opción del 1 al 5, si quiere salir 5");
		System.out.println("----------------------------------------------------");
		
		try {
			opcion = leer.nextInt();
			
		} catch (java.util.InputMismatchException e) {
	        // Atrapar la excepción si se ingresa algo que no es un entero
	        System.out.println("Entrada no válida. Ingrese un número entero.");
	        leer.next(); // Limpiar el búfer de entrada para evitar un bucle infinito
	    }
		
		if (opcion<1 || opcion > 5) {
			System.out.println("OPCION INCORRECTA");
		}
		
		return opcion;
		
	}
	

}
