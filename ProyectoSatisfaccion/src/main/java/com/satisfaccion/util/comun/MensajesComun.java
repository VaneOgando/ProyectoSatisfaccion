package com.satisfaccion.util.comun;

import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class MensajesComun {

	/*ATRIBUTOS*/


	/*METODOS*/


	public void guardarMensaje(Boolean redireccionar, String tipoMensaje, String mensaje) {

		FacesMessage.Severity severidad = FacesMessage.SEVERITY_INFO;

		if (redireccionar) {
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		}

		if (tipoMensaje == Constantes.MENSAJE_TIPO_ERROR) {
			severidad = FacesMessage.SEVERITY_FATAL;
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, tipoMensaje, mensaje));

	}

	/*GET & SET*/


}

