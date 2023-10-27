package servidor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javabean.Pelicula;

/**
 * Clase que representa un hilo que maneja las solicitudes de clientes para la biblioteca virtual.
 * 
 * @author Alberto Arroyo Santofimia
 * 
 * @version v2.0
 */
public class HiloBibliotecaVirtual implements Runnable{
	
	private Thread hilo;
	private static int numCliente = 0;
	private Socket socketAlCliente;	
	private List<Pelicula> peliculaLista;
	
	 /**
     * Constructor para crear un nuevo hilo de manejo de cliente.
     *
     * @param socketAlCliente representa el socket del cliente.
     * @param peliculas representa la lista de peliculas.
     */
	public HiloBibliotecaVirtual(Socket socketAlCliente, List<Pelicula> peliculas) {
		
		numCliente++;
		hilo = new Thread(this, "Cliente_"+numCliente);
		this.socketAlCliente = socketAlCliente;
		this.peliculaLista = peliculas;
		hilo.start();
	}

	/**
	 * Metodo que se ejecuta en un hilo de cliente para procesar las solicitudes entrantes
	 * y la comunicación con un cliente de la biblioteca virtual.
	 * Realiza las siguientes operaciones:
	 * 1. Establece la comunicacion con el cliente.
	 * 2. Procesa las solicitudes del cliente hasta que se recibe el comando de salida.
	 * 3. Proporciona respuestas a las solicitudes del cliente utilizando la instancia de OpcionesHilo.
	 * 4. Cierra la comunicacion con el cliente una vez que se completa la interaccion.
	 *
	 * @see OpcionesHilo
	 */
	@Override
	public void run() {
		String hiloNombre = hilo.getName();
		//Creo el objeto para poder llamar a los metodos de OpcionesCliente
		OpcionesHilo opHilo = new OpcionesHilo();
		System.out.println("Estableciendo comunicacion con " + hiloNombre);
		PrintStream salida = null;
		InputStreamReader entrada = null;
		BufferedReader entradaBuffer = null;
		
		try {
			//Salida del servidor al cliente
			salida = new PrintStream(socketAlCliente.getOutputStream());
			//Entrada del servidor al cliente
			entrada = new InputStreamReader(socketAlCliente.getInputStream());
			entradaBuffer = new BufferedReader(entrada);
			
			String texto = "";
			boolean continuar = true;
			
			//Procesaremos entradas hasta que el texto del cliente sea FIN
			while (continuar) {		
				//trim() es un metodo que quita los espacios en blanco del principio
				//y del final
				System.out.println("Opcion introducida en el menu");
				texto = entradaBuffer.readLine();
				int opcion =  Integer.parseInt(texto);

				if (opcion == 1) { // Consultar película por ID
					opHilo.consultarPeliculaPorId(salida, entradaBuffer, peliculaLista);
				} else if (opcion == 2) { // Consultar película por título
					opHilo.consultarPeliculaPorTitulo(salida, entradaBuffer, peliculaLista);
				} else if (opcion == 3) { // Consultar películas por director
					opHilo.consultarPeliculasPorDirector(salida, entradaBuffer, peliculaLista);
				}else if (opcion == 4) { // Añadir película
					opHilo.agregarPelicula(salida, entradaBuffer, peliculaLista, hiloNombre);						    
				}else if (opcion == 5) { // Salir de la aplicación
                    salida.println("OK");
                    System.out.println(hilo.getName() + " ha cerrado la comunicación");
                    continuar = false;
                } 
						            				
			}
			//Cerramos el socket
			socketAlCliente.close();
			
		} catch (IOException e) {
			System.err.println("HiloBibliotecaVirtual: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("HiloBibliotecaVirtual: Error");
			e.printStackTrace();
		}
		
	}


}
