package com.satisfaccion.util.comun;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class MensajesComun {

	/*ATRIBUTOS*/

	private String tipoMensaje;
	private String mensaje;

	private boolean redireccionar;

	/*METODOS*/

	public void limpiarMensajes(){
		tipoMensaje = null;
		mensaje = null;
	}

	public void guardarMensaje(String tipo){

		if (redireccionar){
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		}

		FacesContext.getCurrentInstance().addMessage(tipo, new FacesMessage(FacesMessage.SEVERITY_ERROR,tipoMensaje, mensaje));

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

	public boolean isRedireccionar() {
		return redireccionar;
	}

	public void setRedireccionar(boolean redireccionar) {
		this.redireccionar = redireccionar;
	}
}

