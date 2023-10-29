package servidor;

import java.util.LinkedList;
import java.util.Queue;

import javabean.Pelicula;
/**
 * La clase Bolsa representa una estructura de datos que almacena objetos Pelicula
 * con una capacidad maxima de 1 elemento y sigue el principio FIFO (First In First Out).
 * El objetivo de esta clase es crear una cola intermedia de la lista de peliculas, en la cual
 * solo puede haber un objeto y los demas hilos tendran que esperar a que el objeto pelicula salga
 * antes de que otro hilo pueda meter su pelicula en la bolsa. El objetivo de esta clase es practicar
 * con las colas, el wait y el notify de los hilos.
 * 
 * @author Alberto Arroyo Santofimia
 * 
 * @version 2.1
 */
public class Bolsa {
		//Número máximo objetos Pelicula que tiene la bolsa
		public final static int MAX_ELEMENTOS = 1;
		
		//Una cola es ideal para implementar este ejemplo
		//FIFO -> First In First Out 
		private Queue<Pelicula> bolsa = new LinkedList<>();
		
		/**
	     * Este metodo obtiene una pelicula de la bolsa. Si la bolsa esta vacia, el hilo se bloquea
	     * y espera a que otro hilo agregue una pelicula antes de continuar.
	     *
	     * @return la pelicula obtenida de la bolsa.
	     */		
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
		/**
	     * Este metodo agrega una pelicula a la bolsa. Si la bolsa esta llena, el hilo se bloquea
	     * y espera a que otro hilo saque una película antes de continuar.
	     *
	     * @param representa pelicula la pelicula que se va a agregar a la bolsa.
	     */
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
