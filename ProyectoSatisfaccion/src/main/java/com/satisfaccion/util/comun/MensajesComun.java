package com.satisfaccion.util.comun;

import com.satisfaccion.jpa.data.*;
import com.satisfaccion.spring.service.CrearRecursoServicio;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class MensajesComun {

	/*ATRIBUTOS*/

	private String tipoMensaje;
	private String mensaje;



	/*METODOS*/

	public void limpiarMenajes(){
		tipoMensaje = null;
		mensaje = null;
	}


	/*GET & SET*/

	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}

