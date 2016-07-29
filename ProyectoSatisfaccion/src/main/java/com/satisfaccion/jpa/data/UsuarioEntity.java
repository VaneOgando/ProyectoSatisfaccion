package com.satisfaccion.jpa.data;

public class UsuarioEntity {

	/*ATRIBUTOS*/
	private String nombre;
	private String usuario;


	/*METODOS*/
	public UsuarioEntity(){

	}

	public UsuarioEntity(String nombre, String usuario) {
		this.nombre = nombre;
		this.usuario = usuario;
	}

	/*GET & SET*/
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
