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
 * Esta clase representa un hilo que maneja las solicitudes de clientes para la biblioteca virtual.
 * 
 * @author Alberto Arroyo Santofimia
 * 
 * @version v1.0
 */

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
				System.out.println("Opcion introducida en el menu");
				texto = entradaBuffer.readLine();
				int opcion =  Integer.parseInt(texto);

				if (opcion == 1) { // Consultar película por ID
					consultarPeliculaPorId(salida, entradaBuffer);
				} else if (opcion == 2) { // Consultar película por título
					consultarPeliculaPorTitulo(salida, entradaBuffer);
				} else if (opcion == 3) { // Consultar películas por director
					consultarPeliculasPorDirector(salida, entradaBuffer);
				}else if (opcion == 4) { // Añadir película
					agregarPelicula(salida, entradaBuffer);						    
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
	/**
     * El metodo buscarPorId() Busca una pelicula por su ID en la lista de peliculas.
     *
     * @param id representa el ID de la pelicula a buscar.
     * @return objeto pelicula que representa la pelicula encontrada o null si no se encuentra ninguna.
     */
	
	//Metodo para encontrar la pelicula por id
	private Pelicula buscarPorId(int id) {
        for (Pelicula pelicula : peliculaLista) {
            if (pelicula.getId() == id) {
                return pelicula;
            }
        }
        return null;
    }
	/**
     * El metodo buscarPorTitulo() Busca una pelicula por su titulo en la lista de peliculas.
     *
     * @param titulo representa el titulo de la pelicula a buscar.
     * @return objeto pelicula que representa la pelicula encontrada o null si no se encuentra ninguna.
     */
	
	//Metodo para encontrar la pelicula por titulo
	private Pelicula buscarPorTitulo(String titulo) {
        for (Pelicula pelicula : peliculaLista) {
            if (pelicula.getTitulo().equalsIgnoreCase(titulo)) {
                return pelicula;
            }
        }
        return null;
    }
	/**
     * El metodo buscarPeliculasPorDirector() busca peliculas por el nombre del director en la lista de películas.
     *
     * @param director representa el nombre del director de las peliculas a buscar.
     * @return Una lista de peliculas que tienen el director especificado.
     */
	
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
	
	 /**
     * El metodo agregarPelicula() agrega una pelicula a la lista de películas de manera sincronizada.
     *
     * @param pelicula La pelicula que se va a agregar.
     */
	
	//Requerimiento 3 metodo sincronizado para que los demas hilos no puedan entrar mientras otro lo usa
	

	
	//************MODULAR LAS OPCIONES************************
	
	/**
	 * El metodo consultarPeliculaPorId() consulta una película por su ID y la muestra en la salida.
	 *
	 * @param salida  representa la conexion de salida de informacion, la información que enviamos al cliente.
	 * @param entradaBuffer representa la conexion de entrada de informacion, la información que vamos a recibir por parte del cliente.
	 * @throws IOException Si ocurre un error de E/S durante la lectura de entrada.
	 */
	
	 private void consultarPeliculaPorId(PrintStream salida, BufferedReader entradaBuffer) throws IOException {
		 	//Espera a que entre desde el cliente el id de la pelicula
		 	System.out.println("Esperando id de la pelicula del cliente");
		 	//salida.println("Pelicula:");
	        int peliculaId = Integer.parseInt(entradaBuffer.readLine());//Parseamos a int el ID de entrada
	        Pelicula pelicula = buscarPorId(peliculaId);
	        salida.println(pelicula);
	        salida.println("FIN_BUSQUEDA");
	 }
	 
	 /**
	  * El metodo consultarPeliculaPorTitulo() consulta una película por su título y la muestra en la salida.
	  *
	  * @param salida  representa la conexion de salida de informacion, la información que enviamos al cliente.
	  * @param entradaBuffer representa la conexion de entrada de informacion, la información que vamos a recibir por parte del cliente.
	  * @throws IOException Si ocurre un error de E/S durante la lectura de entrada.
	  */
	 
	 private void consultarPeliculaPorTitulo(PrintStream salida, BufferedReader entradaBuffer) throws IOException {
	        //Espera a que entre desde el cliente el titulo de la pelicula
		 	System.out.println("Esperando titulo de la pelicula del cliente");
		 	//salida.println("Pelicula:");
		 	String titulo = entradaBuffer.readLine();
	        Pelicula pelicula = buscarPorTitulo(titulo);
	        salida.println(pelicula);
	        salida.println("FIN_BUSQUEDA");
	 }
	 
	 /**
	  *  El metodo consultarPeliculasPorDirector() consulta películas por el nombre del director y las muestra en formato de lista.
	  *
	  * @param salida  representa la conexion de salida de informacion, la información que enviamos al cliente.
	  * @param entradaBuffer representa la conexion de entrada de informacion, la información que vamos a recibir por parte del cliente.
	  * @throws IOException Si ocurre un error de E/S durante la lectura de entrada.
	  */
	 
	 private void consultarPeliculasPorDirector(PrintStream salida, BufferedReader entradaBuffer) throws IOException {
		 	//Espera a que entre desde el cliente el nombre del director de la pelicula
		 	System.out.println("Esperando nombre del director de la pelicula del cliente");
		 	//salida.println("Pelicula:");
	        String director = entradaBuffer.readLine();
	        List<Pelicula> peliculas = buscarPeliculasPorDirector(director);

	        if (peliculas.isEmpty()) {
	            salida.println("No se encontraron películas para el director: " + director);
	            salida.println("FIN_BUSQUEDA");
	        } else {
	            for (Pelicula pelicula : peliculas) {
	                salida.println(pelicula);
	            }
	            salida.println("FIN_BUSQUEDA");
	        }
	   }
	 
	 /**
	  * El metodo agregarPelicula() agrega una película a la lista de películas. Es un metodo sincronizado, bloquearemos el acceso
	  * al resto de hilos el objeto que estamos creando y la lista de peliculas, para que los demás hilos no puedan agregar ninguna
	  * pelicula hasta que el hilo que ha entrado primero no termine.
	  *
	  * @param salida  representa la conexion de salida de informacion, la información que enviamos al cliente.
	  * @param entradaBuffer representa la conexion de entrada de informacion, la información que vamos a recibir por parte del cliente.
	  * @throws IOException Si ocurre un error de E/S durante la lectura de entrada.
	  */
	 
	 private synchronized void agregarPelicula(PrintStream salida, BufferedReader entradaBuffer) throws IOException {
		//Espera a que entre desde el cliente los datos de la pelicula
		//Sincronizo la lista de peliculas para que no puedan acceder otros hilos
		 	System.out.println("Indroduciendo datos de pelicula nueva " + hilo.getName());
		 	synchronized (peliculaLista) {
	            //salida.println("Datos pelicula:");
		 		System.out.println("Esperando id de la pelicula del cliente "+ hilo.getName());
		 		int id = Integer.parseInt(entradaBuffer.readLine());
		 		System.out.println("Esperando titulo de la pelicula del cliente "+ hilo.getName());
	            String title = entradaBuffer.readLine();
	            System.out.println("Esperando nombre del director de la pelicula del cliente "+ hilo.getName());
	            String director = entradaBuffer.readLine();
	            salida.println("Precio de la película:");
	            System.out.println("Esperando precio de la pelicula "+ hilo.getName());
	            double precio = Double.parseDouble(entradaBuffer.readLine());
	            Pelicula pelicula = new Pelicula(id, title, director, precio);

	            if (peliculaLista.contains(pelicula)) {
	            	System.out.println("Película no añadida "+ hilo.getName() );
	                salida.println("Película no añadida, ya existe una película con ese ID");
	                salida.println("FIN_BUSQUEDA");
	            } else {
	                //agregarPelicula(pelicula);
	            	peliculaLista.add(pelicula);
	            	System.out.println("Película añadida correctamente "+ hilo.getName() );
	                salida.println("Película agregada correctamente:\n" + pelicula);
	                salida.println("FIN_BUSQUEDA");
	            }
	            /*	
	    		try {
	    			//Thread.sleep(10000);//parar 10 segundos
	    			wait();
	    		} catch (InterruptedException e) {
	    			e.printStackTrace();
	    		}*/
	        }
			
			 
	    }
	 

}
