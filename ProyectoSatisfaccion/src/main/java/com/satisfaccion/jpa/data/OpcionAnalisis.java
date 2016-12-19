package com.satisfaccion.jpa.data;


public class OpcionAnalisis {

	private int idopcion;
	private String titulo;
	private int totalOpcion;

	/*GET AND SET*/

	public int getIdopcion() {
		return idopcion;
	}

	public void setIdopcion(int idopcion) {
		this.idopcion = idopcion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getTotalOpcion() {
		return totalOpcion;
	}

	public void setTotalOpcion(int totalOpcion) {
		this.totalOpcion = totalOpcion;
	}
}
