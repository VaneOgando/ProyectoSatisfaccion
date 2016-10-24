package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.EncuestaEntity;
import com.satisfaccion.spring.service.ConsultarEncuestaServicio;

import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.util.comun.MensajesComun;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ConsultarEncuestaBean {

	/*ATRIBUTOS*/
	@ManagedProperty("#{consultarEncuestaServicio}")
	private ConsultarEncuestaServicio consultarEncuestaServicio;

	@ManagedProperty(value = "#{mensajesComun}")
	private MensajesComun mensajesComun;

	private FacesContext context = FacesContext.getCurrentInstance();

	private List<EncuestaEntity> items = new ArrayList<EncuestaEntity>();
	private List<EncuestaEntity> itemsBuscados;
	private EncuestaEntity itemSeleccionado;

	private String estado;
	private String tipoEncuesta;

	private int respuestas = 0;
	private boolean eliminar = false;


/*METODOS*/

	@PostConstruct
	private void init() {

		filtrarEncuestas();

	}

	public void filtrarEncuestas() {

		inicialiazarItems();

		items = consultarEncuestaServicio.filtrarEncuestas(estado, tipoEncuesta);

	}

	public void inicialiazarItems() {

		items = null;
		itemsBuscados = null;
		itemSeleccionado = null;

		RequestContext.getCurrentInstance().execute("PF('itemTabla').clearFilters()");
	}


	public String detalleEncuesta() {

		if (itemSeleccionado != null) {

//			return "detallePregunta.xhtml?faces-redirect=true&id=" + itemSeleccionado.getId();

			return "";
		}

		return "";
	}

	public String modificarEncuesta() {
/*
		if (itemSeleccionado != null) {

			respuestas = consultarEncuestaServicio.consultarNumRespuestas(itemSeleccionado.getId());

			if (respuestas == 0) {
				return "modificarPregunta.xhtml?faces-redirect=true&id=" + itemSeleccionado.getId();
			} else {

				mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_PREGUNTA_RESPONDIDA);
				return "";
			}

		}
*/
		return "";
	}

	public void eliminarEncuesta(){

		if (itemSeleccionado.getEstado().equals("A")){

			respuestas = consultarEncuestaServicio.consultarNumRespuestas(itemSeleccionado.getId());

			if (respuestas > 0){
				//Desactivar encuesta

				eliminar = consultarEncuestaServicio.eliminarEncuesta(false, itemSeleccionado);

				if (eliminar){
					mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_EXITO, Constantes.EX_ELIMINAR_DESACTIVAR);
				}else{
					mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_ELIMINAR_DESACTIVAR);
				}

			}else{
				//Eliminar encuesta

				eliminar = consultarEncuestaServicio.eliminarEncuesta(true, itemSeleccionado);

				if (eliminar){
					mensajesComun.guardarMensaje(true, Constantes.MENSAJE_TIPO_EXITO, Constantes.EX_ELIMINAR_DEFINITIVO);
					items.remove(itemSeleccionado);
				}else{
					mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_ELIMINAR_DEFINITIVO);
				}

			}

		}else{
			mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_ENCUESTA_INACTIVA);
		}

	}

	public String bt_crearEncuesta(){

		return "Crear";
	}

	public String bt_modificarEncuesta() {

		if (itemSeleccionado != null) {

			//Verificar el estado de la encuesta, si esta inactiva confirmar su activacion y continuar

			return "modificarEncuesta.xhtml?faces-redirect=true&id=" + itemSeleccionado.getId();

		}

		return "";
	}

	public String bt_enviarEncuesta(){

		int cantPreguntas;

		if(itemSeleccionado != null) {

			if(itemSeleccionado.getEstado().equals("A")) {
				cantPreguntas = ((Long) consultarEncuestaServicio.cantidadPreguntasActivas(itemSeleccionado.getId())).intValue();

				if (cantPreguntas > 0) {
					return "enviarEncuesta.xhtml?faces-redirect=true&id=" + itemSeleccionado.getId();
				} else {

					mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_ENCUESTA_ENVIO_INVALIDO);
					return "";
				}

			}else{

				mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_ENCUESTA_INACTIVA);
				return "";
			}

		}

		return "";
	}




/* GET & SET */

	public ConsultarEncuestaServicio getConsultarEncuestaServicio() {
		return consultarEncuestaServicio;
	}

	public void setConsultarEncuestaServicio(ConsultarEncuestaServicio consultarEncuestaServicio) {
		this.consultarEncuestaServicio = consultarEncuestaServicio;
	}

	public boolean isEliminar() {
		return eliminar;
	}

	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}

	public int getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(int respuestas) {
		this.respuestas = respuestas;
	}

	public String getTipoEncuesta() {
		return tipoEncuesta;
	}

	public void setTipoEncuesta(String tipoEncuesta) {
		this.tipoEncuesta = tipoEncuesta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public EncuestaEntity getItemSeleccionado() {
		return itemSeleccionado;
	}

	public void setItemSeleccionado(EncuestaEntity itemSeleccionado) {
		this.itemSeleccionado = itemSeleccionado;
	}

	public List<EncuestaEntity> getItemsBuscados() {
		return itemsBuscados;
	}

	public void setItemsBuscados(List<EncuestaEntity> itemsBuscados) {
		this.itemsBuscados = itemsBuscados;
	}

	public List<EncuestaEntity> getItems() {
		return items;
	}

	public void setItems(List<EncuestaEntity> items) {
		this.items = items;
	}

	public FacesContext getContext() {
		return context;
	}

	public void setContext(FacesContext context) {
		this.context = context;
	}

	public MensajesComun getMensajesComun() {
		return mensajesComun;
	}

	public void setMensajesComun(MensajesComun mensajesComun) {
		this.mensajesComun = mensajesComun;
	}
}