package cliente.hilo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class SocketCliente {
	
	// IP y Puerto a la que nos vamos a conectar
	public static final int PUERTO = 3333;
	public static final String IP_SERVER = "localhost";
	private static Scanner leer;
		
	static {
			leer = new Scanner(System.in);
	}

	public static void main(String[] args) {
		System.out.println("        APLICACI�N CLIENTE         ");
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
				//System.out.println("CLIENTE: Escribe mensaje (FIN para terminar): ");
				
				//Cargamos el menu inicial y recuperamos la opción elegida
				int opcion = menu();
				//Si la opcion está fuera del rango de opciones se repetira el menu
				while (opcion<1 || opcion>3){
					opcion = menu();
				}
				//enviamos la opcion elegida al servidor
				salida.println(String.valueOf(opcion));
				//Segun la opcion elegida, guardamos la respuesta en un string y la enviamos al servidor
				switch(opcion) {
					case 1: // Consultar película por ID
						System.out.println("Introduzca ID de la película");
						texto = sc.nextLine();
						salida.println(texto);
						break;
					case 2: //Consultar película por título
						System.out.println("Introduzca título de la película");
						texto = sc.nextLine();
						salida.println(texto);
						break;
					case 3:// Salir de la aplicación
						continuar = false;
						break;
					}
					
				System.out.println("CLIENTE: Esperando respuesta ...... ");				
				String respuesta = entradaBuffer.readLine();
								
				if("OK".equalsIgnoreCase(respuesta)) {
					continuar = false;
				}else {
					System.out.println("CLIENTE: Servidor responde: " + respuesta);
				}				
			}while(continuar);
			//Cerramos la conexion
			socketAlServidor.close();
		} catch (UnknownHostException e) {
			System.err.println("CLIENTE: No encuentro el servidor en la direcci�n" + IP_SERVER);
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
	
	//Menu inicial de la aplicacion
	public static int menu() {
		
		
		int opcion = 0;
		System.out.println("----------------------------------------------------");
		System.out.println("|                      MENU                        |");
		System.out.println("----------------------------------------------------");
		System.out.println("1. Consultar pelicula por ID");
		System.out.println("2. Consultar película por título ");
		System.out.println("3. Salir de la aplicación");
		System.out.println("----------------------------------------------------");
		System.out.println("Introduzca una opción del 1 al 3, si quiere salir 3");
		System.out.println("----------------------------------------------------");
		opcion = leer.nextInt();
		
		if (opcion<1 || opcion > 4) {
			System.out.println("OPCION INCORRECTA");
		}
		
		
		return opcion;
		
	}
	

}
