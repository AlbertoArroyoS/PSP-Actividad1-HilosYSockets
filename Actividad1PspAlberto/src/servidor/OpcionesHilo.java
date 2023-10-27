package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javabean.Pelicula;

public class OpcionesHilo {
	

	//Metodo para encontrar la pelicula por id
	public Pelicula buscarPorId(int id, List<Pelicula> peliculaLista) {
        for (Pelicula pelicula : peliculaLista) {
            if (pelicula.getId() == id) {
                return pelicula;
            }
        }
        return null;
    }

	
	//Metodo para encontrar la pelicula por titulo
	public Pelicula buscarPorTitulo(String titulo, List<Pelicula> peliculaLista) {
        for (Pelicula pelicula : peliculaLista) {
            if (pelicula.getTitulo().equalsIgnoreCase(titulo)) {
                return pelicula;
            }
        }
        return null;
    }

	
	
	//Requerimiento 2 devolver una lista con los directores
	public List<Pelicula> buscarPeliculasPorDirector(String director, List<Pelicula> peliculaLista) {
        List<Pelicula> peliculasPorDirector = new ArrayList<>();
        for (Pelicula pelicula : peliculaLista) {
            if (pelicula.getDirector().equalsIgnoreCase(director)) {
                peliculasPorDirector.add(pelicula);
            }
        }
        return peliculasPorDirector;
    }
	

	
	 public void consultarPeliculaPorId(PrintStream salida, BufferedReader entradaBuffer, List<Pelicula> peliculaLista) throws IOException {
		 	//Espera a que entre desde el cliente el id de la pelicula
		 	System.out.println("Esperando id de la pelicula del cliente");
		 	//salida.println("Pelicula:");
	        int peliculaId = Integer.parseInt(entradaBuffer.readLine());//Parseamos a int el ID de entrada
	        Pelicula pelicula = buscarPorId(peliculaId, peliculaLista);
	        salida.println(pelicula);
	        salida.println("FIN_BUSQUEDA");
	 }
	 

	 
	 public void consultarPeliculaPorTitulo(PrintStream salida, BufferedReader entradaBuffer, List<Pelicula> peliculaLista) throws IOException {
	        //Espera a que entre desde el cliente el titulo de la pelicula
		 	System.out.println("Esperando titulo de la pelicula del cliente");
		 	//salida.println("Pelicula:");
		 	String titulo = entradaBuffer.readLine();
	        Pelicula pelicula = buscarPorTitulo(titulo, peliculaLista);
	        salida.println(pelicula);
	        salida.println("FIN_BUSQUEDA");
	 }
	 

	 
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
