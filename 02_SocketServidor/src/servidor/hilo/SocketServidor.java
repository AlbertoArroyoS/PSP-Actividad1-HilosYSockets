package servidor.hilo;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javabean.Pelicula;

public class SocketServidor {
	
	public static final int PUERTO = 3333;
	private static List<Pelicula> biblioteca = new ArrayList<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		cargarDatos();
		
		System.out.println("      APLICACI�N DE SERVIDOR CON HILOS     ");
		System.out.println("-------------------------------------------");		
		
		int peticion = 0;
		
		try (ServerSocket servidor = new ServerSocket()){			
			InetSocketAddress direccion = new InetSocketAddress(PUERTO);
			servidor.bind(direccion);

			System.out.println("SERVIDOR: Esperando peticion por el puerto " + PUERTO);
			
			while (true) {
				//Por cada peticion de cliente aceptada se me crea un objeto socket diferente
				Socket socketAlCliente = servidor.accept();
				System.out.println("SERVIDOR: peticion numero " + ++peticion + " recibida");
				//Abrimos un hilo nuevo y liberamos el hilo principal para que pueda
				//recibir peticiones de otros clientes
				new HiloBibliotecaVirtual(socketAlCliente, biblioteca);
			}			
		} catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error");
			e.printStackTrace();
		}
	
	}
	// Crear una lista de películas y agregar 5 películas
	private static void cargarDatos() {
		biblioteca.add(new Pelicula(1, "Batman", "Tim Burton", 10.99));
		biblioteca.add(new Pelicula(2, "Terminator", "James Cameron", 12.99));
		biblioteca.add(new Pelicula(3, "Los vengadores", "Joss Whedon", 9.99));
		biblioteca.add(new Pelicula(4, "Spiderman", "Sam Raimi", 14.99));
		biblioteca.add(new Pelicula(5, "Superman", "Richard Donner", 8.99));
		
		
	}

	

}
