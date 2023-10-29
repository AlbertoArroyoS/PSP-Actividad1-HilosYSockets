package servidor;

import java.util.LinkedList;
import java.util.Queue;

import javabean.Pelicula;

public class Bolsa {
		//Número máximo objetos Pelicula que tiene la bolsa
		public final static int MAX_ELEMENTOS = 1;
		
		//Una cola es ideal para implementar este ejemplo
		//FIFO -> First In First Out 
		private Queue<Pelicula> bolsa = new LinkedList<>();
		
		public synchronized Pelicula obtenerPeliculaBolsa(){
			//Si la bolsa está vacia no debemos intentar sacar ningún elemento más
			//por lo que esperamos a que otro hilo ponga un elemento pelicula
			while(bolsa.size() == 0){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//Sacamos un elemento de la cola
			Pelicula peliculaBolsa = bolsa.poll();
			//Despertamos a un hilo que esté en estado 'wait'
			notify();
			return peliculaBolsa;
		}	
		
		public synchronized void añadirPeliculaBolsa(Pelicula pelicula){
			//Si la bolsa está llena no debemos introducir ninguna pelicula más
			//por lo que esperamos a que otro hilo libere espacio
			while(bolsa.size() == MAX_ELEMENTOS){//1
				try {
					wait();			
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//Añadimos un elemento a la cola
			bolsa.offer(pelicula);
			//Despertamos a un hilo que esté en estado 'wait'
			notify();
		}
}
