package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javabean.Pelicula;

/**
 * La clase OpcionesHilo contiene métodos relacionados con la gestión de películas en un sistema. 
 * Proporciona funciones para buscar películas por ID, título o director, y agregar nuevas películas.
 * Además, incluye un método sincronizado para agregar películas de manera segura en entornos con hilos múltiples.
 * 
 * @author Alberto Arroyo Santofimia
 * @version 1.0
 */

public class OpcionesHilo {
	

	//Metodo para encontrar la pelicula por id
	
	 /**
     * Metodo que busca una pelicula por su ID en una lista de peliculas.
     *
     * @param id representa el ID de la pelicula a buscar.
     * @param peliculaLista representa la lista de peliculas en la que se realizara la busqueda.
     * @return la película encontrada o null si no se encuentra ninguna película con el ID especificado.
     */
	public Pelicula buscarPorId(int id, List<Pelicula> peliculaLista) {
        for (Pelicula pelicula : peliculaLista) {
            if (pelicula.getId() == id) {
                return pelicula;
            }
        }
        return null;
    }

	
	//Metodo para encontrar la pelicula por titulo
	
	/**
     * Metodo para buscar una película por su título en una lista de películas.
     *
     * @param titulo representa el título de la película a buscar.
     * @param peliculaLista representa la lista de películas en la que se realizará la búsqueda.
     * @return la película encontrada o null si no se encuentra ninguna película con el título especificado.
     */
	public Pelicula buscarPorTitulo(String titulo, List<Pelicula> peliculaLista) {
        for (Pelicula pelicula : peliculaLista) {
            if (pelicula.getTitulo().equalsIgnoreCase(titulo)) {
                return pelicula;
            }
        }
        return null;
    }

	//Requerimiento 2 devolver una lista con los directores
	
	 /**
     * Metodo para buscar películas por el nombre del director en una lista de películas.
     *
     * @param director     El nombre del director de las películas a buscar.
     * @param peliculaLista La lista de películas en la que se realizará la búsqueda.
     * @return una lista de películas que tienen al director especificado o No se encontraron películas para el director.
     */
	public List<Pelicula> buscarPeliculasPorDirector(String director, List<Pelicula> peliculaLista) {
        List<Pelicula> peliculasPorDirector = new ArrayList<>();
        for (Pelicula pelicula : peliculaLista) {
            if (pelicula.getDirector().equalsIgnoreCase(director)) {
                peliculasPorDirector.add(pelicula);
            }
        }
        return peliculasPorDirector;
    }
	
	 /**
     * Metodo para consultar una película por su ID y envía la respuesta al cliente.
     *
     * @param salida representa el flujo de salida para enviar la respuesta al cliente.
     * @param entradaBuffer representa el flujo de entrada para recibir el ID de la película del cliente.
     * @param peliculaLista representa la lista de películas en la que se buscará la película.
     * @throws IOException si ocurre un error de E/S durante la operación.
     */
	 public void consultarPeliculaPorId(PrintStream salida, BufferedReader entradaBuffer, List<Pelicula> peliculaLista) throws IOException {
		 	//Espera a que entre desde el cliente el id de la pelicula
		 	System.out.println("Esperando id de la pelicula del cliente");
		 	//salida.println("Pelicula:");
	        int peliculaId = Integer.parseInt(entradaBuffer.readLine());//Parseamos a int el ID de entrada
	        Pelicula pelicula = buscarPorId(peliculaId, peliculaLista);
	        salida.println(pelicula);
	        salida.println("FIN_BUSQUEDA");
	 }
	 /**
	 * Metodo para consultar una película por su título y envía la respuesta al cliente.
	 *
	 * @param salida representa el flujo de salida para enviar la respuesta al cliente.
	 * @param entradaBuffer representa el flujo de entrada para recibir el título de la película del cliente.
	 * @param peliculaLista representa la lista de películas en la que se buscará la película.
	 * @throws IOException si ocurre un error de E/S durante la operación.
	  */	 
	 public void consultarPeliculaPorTitulo(PrintStream salida, BufferedReader entradaBuffer, List<Pelicula> peliculaLista) throws IOException {
	        //Espera a que entre desde el cliente el titulo de la pelicula
		 	System.out.println("Esperando titulo de la pelicula del cliente");
		 	//salida.println("Pelicula:");
		 	String titulo = entradaBuffer.readLine();
	        Pelicula pelicula = buscarPorTitulo(titulo, peliculaLista);
	        salida.println(pelicula);
	        salida.println("FIN_BUSQUEDA");
	 }
	 
	 /**
	 * Metodo para consultar películas por el nombre del director y envía la respuesta al cliente.
	 *
	 * @param salida representa el flujo de salida para enviar la respuesta al cliente.
	 * @param entradaBuffer representa el flujo de entrada para recibir el nombre del director del cliente.
	 * @param peliculaLista representa la lista de películas en la que se buscarán las películas.
	 * @throws IOException si ocurre un error de E/S durante la operación.
	 */
	 public void consultarPeliculasPorDirector(PrintStream salida, BufferedReader entradaBuffer, List<Pelicula> peliculaLista) throws IOException {
		 	//Espera a que entre desde el cliente el nombre del director de la pelicula
		 	System.out.println("Esperando nombre del director de la pelicula del cliente");
		 	//salida.println("Pelicula:");
	        String director = entradaBuffer.readLine();
	        List<Pelicula> peliculas = buscarPeliculasPorDirector(director, peliculaLista);

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
	 
	//Requerimiento 3 metodo sincronizado para que los demas hilos no puedan entrar mientras otro lo usa
	    
	 /**
	 * Metodo para agregar una nueva película de forma sincronizada y envía la respuesta al cliente.
	 *
	 * @param salida representa el flujo de salida para enviar la respuesta al cliente.
	 * @param entradaBuffer representa el flujo de entrada para recibir los datos de la película del cliente.
	 * @param peliculaLista representa la lista de películas en la que se agregará la nueva película.
	 * @param hiloNombre representa el nombre del hilo que realiza la operación.
	 * @throws IOException si ocurre un error de E/S durante la operación.
	 */	 
	 public synchronized void agregarPelicula(PrintStream salida, BufferedReader entradaBuffer, List<Pelicula> peliculaLista, String hiloNombre) throws IOException {
		//Espera a que entre desde el cliente los datos de la pelicula
		//Sincronizo la lista de peliculas para que no puedan acceder otros hilos
		 	System.out.println("Indroduciendo datos de pelicula nueva " + hiloNombre);
		 	synchronized (peliculaLista) {
	            //salida.println("Datos pelicula:");
		 		System.out.println("Esperando id de la pelicula del cliente "+ hiloNombre);
		 		int id = Integer.parseInt(entradaBuffer.readLine());
		 		System.out.println("Esperando titulo de la pelicula del cliente "+ hiloNombre);
	            String title = entradaBuffer.readLine();
	            System.out.println("Esperando nombre del director de la pelicula del cliente "+ hiloNombre);
	            String director = entradaBuffer.readLine();
	            salida.println("Precio de la película:");
	            System.out.println("Esperando precio de la pelicula "+ hiloNombre);
	            double precio = Double.parseDouble(entradaBuffer.readLine());
	            Pelicula pelicula = new Pelicula(id, title, director, precio);

	            if (peliculaLista.contains(pelicula)) {
	            	System.out.println("Película no añadida "+ hiloNombre );
	                salida.println("Película no añadida, ya existe una película con ese ID");
	                salida.println("FIN_BUSQUEDA");
	            } else {
	                //agregarPelicula(pelicula);
	            	peliculaLista.add(pelicula);
	            	System.out.println("Película añadida correctamente "+ hiloNombre);
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
