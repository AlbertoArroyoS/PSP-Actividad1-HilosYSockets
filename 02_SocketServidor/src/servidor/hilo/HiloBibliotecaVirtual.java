package servidor.hilo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javabean.Pelicula;

//Este hilo va a entrar una opcion y devolvera segun la opcion marcada
//La conexion se mantendra abierta hasta que el cliente mande la palabra
//"FIN" al servidor.

//Recibe el socket que abre el servidor con el cliente y con el que
//mantendra la conversacion

public class HiloBibliotecaVirtual implements Runnable{
	
	private Thread hilo;
	private static int numCliente = 0;
	private Socket socketAlCliente;	
	private List<Pelicula> peliculaLista;
	

	public HiloBibliotecaVirtual(Socket socketAlCliente, List<Pelicula> peliculas) {
		numCliente++;
		hilo = new Thread(this, "Cliente_"+numCliente);
		this.socketAlCliente = socketAlCliente;
		this.peliculaLista = peliculas;
		hilo.start();
	}


	@Override
	public void run() {
		System.out.println("Estableciendo comunicacion con " + hilo.getName());
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
				texto = entradaBuffer.readLine();
				int opcion =  Integer.parseInt(texto);

				if (opcion == 1) { // Consultar película por ID
				    texto = entradaBuffer.readLine(); // Lee el ID
				    int peliculaId = Integer.parseInt(texto);
				    Pelicula pelicula = buscarPorId(peliculaId);
				    salida.println(pelicula);
				    salida.println("FIN_BUSQUEDA");
				} else if (opcion == 2) { // Consultar película por título
				    String titulo = entradaBuffer.readLine(); // Lee el título
				    Pelicula pelicula = buscarPorTitulo(titulo);
				    salida.println(pelicula);
				    salida.println("FIN_BUSQUEDA");
				    
				} else if (opcion == 3) { // Consultar películas por director
				    String director = entradaBuffer.readLine(); // Lee el nombre del director
				    List<Pelicula> peliculas = buscarPeliculasPorDirector(director);

				    if (peliculas.isEmpty()) {
				        salida.println("No se encontraron películas para el director: " + director);
				    } else {
				        // Envía cada película al cliente como una cadena, con un marcador especial para el final
				        for (Pelicula pelicula : peliculas) {
				           // String peliculaInfo = pelicula.getId() + "," + pelicula.getTitulo() + "," + pelicula.getDirector() + "," + pelicula.getPrecio();
				           // salida.println(peliculaInfo);
				        	salida.println(pelicula);
				        }
				        salida.println("FIN_BUSQUEDA"); // Marcador para indicar el final de la lista
				    }
				}else if (opcion == 4) { // Añadir película
				    // Solicitar todos los datos de la película al cliente
					//agregarPelicula();
					System.out.println("Introduzca el ID de la película:");
				    int id = Integer.parseInt(entradaBuffer.readLine());
				    System.out.println("Introduzca el título de la película:");
				    String title = entradaBuffer.readLine();
				    System.out.println("Introduzca el director de la película:");
				    String director = entradaBuffer.readLine();
				    System.out.println("Introduzca el precio de la película:");
				    double precio = Double.parseDouble(entradaBuffer.readLine());
				    peliculaLista.add(new Pelicula(id, title, director, precio));				    
				    System.out.println("Pelicula agregada correctamente.");
				    //salida.println("FIN_BUSQUEDA");
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
	//Metodo para encontrar la pelicula por id
	private Pelicula buscarPorId(int id) {
        for (Pelicula pelicula : peliculaLista) {
            if (pelicula.getId() == id) {
                return pelicula;
            }
        }
        return null;
    }
	//Metodo para encontrar la pelicula por titulo
	private Pelicula buscarPorTitulo(String title) {
        for (Pelicula pelicula : peliculaLista) {
            if (pelicula.getTitulo().equalsIgnoreCase(title)) {
                return pelicula;
            }
        }
        return null;
    }
	//Requerimiento 2 devolver una lista con los directores
	private List<Pelicula> buscarPeliculasPorDirector(String director) {
        List<Pelicula> peliculasPorDirector = new ArrayList<>();
        for (Pelicula pelicula : peliculaLista) {
            if (pelicula.getDirector().equalsIgnoreCase(director)) {
                peliculasPorDirector.add(pelicula);
            }
        }
        return peliculasPorDirector;
    }
	//Requerimiento 3 metodo sincronizado para que los demas hilos no puedan entrar mientras otro lo usa
	/*
	public synchronized void agregarPelicula() {
		 	
		 	System.out.println("Introduzca el ID de la película:");
		    int id = Integer.parseInt(entradaBuffer.readLine());
		    System.out.println("Introduzca el título de la película:");
		    String title = entradaBuffer.readLine();
		    System.out.println("Introduzca el director de la película:");
		    String director = entradaBuffer.readLine();
		    System.out.println("Introduzca el precio de la película:");
		    double precio = Double.parseDouble(entradaBuffer.readLine());
		    peliculaLista.add(new Pelicula(id, title, director, precio));
		    
	 }*/

}
