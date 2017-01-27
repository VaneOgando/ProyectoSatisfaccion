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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UsuarioEntity)) return false;

		UsuarioEntity that = (UsuarioEntity) o;

		if (getNombre() != null ? !getNombre().equals(that.getNombre()) : that.getNombre() != null) return false;
		return !(getUsuario() != null ? !getUsuario().equals(that.getUsuario()) : that.getUsuario() != null);

	}

	@Override
	public int hashCode() {
		int result = getNombre() != null ? getNombre().hashCode() : 0;
		result = 31 * result + (getUsuario() != null ? getUsuario().hashCode() : 0);
		return result;
	}
}
