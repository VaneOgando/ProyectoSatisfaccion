package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.OpcionEntity;
import com.satisfaccion.jpa.data.PreguntaEntity;
import com.satisfaccion.spring.service.DetallePreguntaServicio;
import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.util.comun.MensajesComun;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class DetallePreguntaBean{

	/*ATRIBUTOS*/
	@ManagedProperty("#{detallePreguntaServicio}")
	private DetallePreguntaServicio detallePreguntaServicio;

	@ManagedProperty(value="#{mensajesComun}")
	private MensajesComun mensajesComun;

	private PreguntaEntity pregunta = new PreguntaEntity();
	private List<OpcionEntity> opciones = new ArrayList<OpcionEntity>();
	private int respuestas = 0;

	private boolean eliminar = false;

	/*METODOS*/

	@PostConstruct
	public void cargarDetallePregunta() {

		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

		pregunta.setId(Integer.parseInt(id));

		pregunta = detallePreguntaServicio.consultarPregunta(pregunta.getId());
		opciones = detallePreguntaServicio.consultarOpciones(pregunta.getId());
		respuestas = detallePreguntaServicio.consultarNumRespuestas(pregunta.getId());

		//Transformar escala de float a int para desplegar valoracion


	}

	public String bt_eliminarPregunta() throws IOException {

		if (pregunta.getEstado().equals("A")){

			if (respuestas > 0){
				//Desactivar pregunta

				eliminar = detallePreguntaServicio.eliminarPregunta(false, pregunta);

				if (eliminar){
					mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_EXITO, Constantes.EX_ELIMINAR_DESACTIVAR);
				}else{
					mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_ELIMINAR_DESACTIVAR);
				}

				return "";

			}else{
				//Eliminar pregunta

				eliminar = detallePreguntaServicio.eliminarPregunta(true, pregunta);

				if (eliminar){

					mensajesComun.guardarMensaje(true, Constantes.MENSAJE_TIPO_EXITO, Constantes.ERR_ELIMINAR_DEFINITIVO);
					return "Exito";

				}else{

					mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_ELIMINAR_DEFINITIVO);
					return "";
				}

			}

		}else{

			mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_PREGUNTA_INACTIVA);
			return "";
		}

	}

	public String bt_modificarPregunta() {

		if (respuestas == 0){
			return "Exito";
		}else{

			mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_PREGUNTA_RESPONDIDA);
			return "";
		}

	}




/* GET & SET */

	public DetallePreguntaServicio getDetallePreguntaServicio() {
		return detallePreguntaServicio;
	}

	public void setDetallePreguntaServicio(DetallePreguntaServicio detallePreguntaServicio) {
		this.detallePreguntaServicio = detallePreguntaServicio;
	}

	public MensajesComun getMensajesComun() {
		return mensajesComun;
	}

	public void setMensajesComun(MensajesComun mensajesComun) {
		this.mensajesComun = mensajesComun;
	}

	public PreguntaEntity getPregunta() {
		return pregunta;
	}

	public void setPregunta(PreguntaEntity pregunta) {
		this.pregunta = pregunta;
	}

	public List<OpcionEntity> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<OpcionEntity> opciones) {
		this.opciones = opciones;
	}

	public int getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(int respuestas) {
		this.respuestas = respuestas;
	}



}