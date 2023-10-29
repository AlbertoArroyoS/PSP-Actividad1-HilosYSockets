package javabean;

import java.util.Objects;


/**
 * La clase Pelicula representa una pelicula con propiedades como ID, titulo, director y precio.
 * 
 * @author Alberto Arroyo Santofimia
 * 
 * @version v1.0
 */


public class Pelicula {
	
    private int id;
    private String titulo;
    private String director;
    private double precio;

    /**
     * Crea un objeto Pelicula con los valores iniciales proporcionados.
     *
     * @param id representa el ID de la pelicula.
     * @param titulo representa el titulo de la pelicula.
     * @param director representa el nombre del director de la pelicula.
     * @param precio representa el precio de la pelicula.
     */
    
    public Pelicula(int id, String titulo, String director, double precio) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.precio = precio;
    }



    public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getTitulo() {
		return titulo;
	}



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	public String getDirector() {
		return director;
	}



	public void setDirector(String director) {
		this.director = director;
	}



	public double getPrecio() {
		return precio;
	}



	public void setPrecio(double precio) {
		this.precio = precio;
	}

	 /**
     * Devuelve una representacion de cadena de la pelicula que incluye su ID, titulo, director y precio.
     *
     * @return Una cadena con los detalles de la pelicula.
     */

	@Override
    public String toString() {
        return "ID: " + id + ", Título: " + titulo + ", Director: " + director + ", Precio: $" + precio;
    }

	/**
     * Calcula un valor hash para el objeto Pelicula basado en su ID.
     *
     * @return El valor hash calculado.
     */

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pelicula other = (Pelicula) obj;
		return id == other.id;
	}



	
	
}

