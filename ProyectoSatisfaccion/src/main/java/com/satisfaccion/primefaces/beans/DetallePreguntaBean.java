package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.OpcionEntity;
import com.satisfaccion.jpa.data.PreguntaEntity;
import com.satisfaccion.spring.service.DetallePreguntaServicio;
import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.util.comun.MensajesComun;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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

	public void cargarDetallePregunta() {

		pregunta = detallePreguntaServicio.consultarPregunta(pregunta.getId());
		opciones = detallePreguntaServicio.consultarOpciones(pregunta.getId());
		respuestas = detallePreguntaServicio.consultarNumRespuestas(pregunta.getId());

	}

	public void bt_eliminarPregunta(){

		if (pregunta.getEstado().equals("A")){

			if (respuestas > 0){
				//Desactivar pregunta

				eliminar = detallePreguntaServicio.eliminarPregunta(false, pregunta);

				if (eliminar){
					mensajesComun.setTipoMensaje(Constantes.MENSAJE_TIPO_EXITO);
					mensajesComun.setMensaje(Constantes.EX_ELIMINAR_DESACTIVAR);
				}else{
					mensajesComun.setTipoMensaje(Constantes.MENSAJE_TIPO_ERROR);
					mensajesComun.setMensaje(Constantes.ERR_ELIMINAR_DESACTIVAR);
				}


			}else{
				//Eliminar pregunta

				eliminar = detallePreguntaServicio.eliminarPregunta(true, pregunta);

				if (eliminar){
					mensajesComun.setTipoMensaje(Constantes.MENSAJE_TIPO_EXITO);
					mensajesComun.setMensaje(Constantes.EX_ELIMINAR_DEFINITIVO);
				}else{
					mensajesComun.setTipoMensaje(Constantes.MENSAJE_TIPO_ERROR);
					mensajesComun.setMensaje(Constantes.ERR_ELIMINAR_DEFINITIVO);
				}

			}

		}else{

			mensajesComun.setTipoMensaje(Constantes.MENSAJE_TIPO_ERROR);
			mensajesComun.setMensaje(Constantes.ERR_PREGUNTA_INACTIVA);

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