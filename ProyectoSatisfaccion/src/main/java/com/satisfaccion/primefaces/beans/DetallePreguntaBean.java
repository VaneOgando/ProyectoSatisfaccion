package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.OpcionEntity;
import com.satisfaccion.jpa.data.PreguntaEntity;
import com.satisfaccion.spring.service.ConsultarPreguntaServicio;
import com.satisfaccion.spring.service.DetallePreguntaServicio;
import com.satisfaccion.util.comun.Constantes;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class DetallePreguntaBean {

	/*ATRIBUTOS*/
	@ManagedProperty("#{detallePreguntaServicio}")
	private DetallePreguntaServicio detallePreguntaServicio;


	private PreguntaEntity pregunta = new PreguntaEntity();
	private int respuestas = 0;

	/*METODOS*/

	public void cargarDetallePregunta(){

		pregunta = detallePreguntaServicio.consultarPregunta(pregunta.getId());
		respuestas = detallePreguntaServicio.consultarNumeroRespuestas(pregunta.getId());

	}










/* GET & SET */

	public DetallePreguntaServicio getDetallePreguntaServicio() {
		return detallePreguntaServicio;
	}

	public void setDetallePreguntaServicio(DetallePreguntaServicio detallePreguntaServicio) {
		this.detallePreguntaServicio = detallePreguntaServicio;
	}

	public PreguntaEntity getPregunta() {
		return pregunta;
	}

	public void setPregunta(PreguntaEntity pregunta) {
		this.pregunta = pregunta;
	}

	public int getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(int respuestas) {
		this.respuestas = respuestas;
	}



}