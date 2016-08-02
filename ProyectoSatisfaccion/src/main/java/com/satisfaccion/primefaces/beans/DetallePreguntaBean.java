package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.OpcionEntity;
import com.satisfaccion.jpa.data.PreguntaEntity;
import com.satisfaccion.spring.service.ConsultarPreguntaServicio;
import com.satisfaccion.spring.service.DetallePreguntaServicio;
import com.satisfaccion.primefaces.beans.ComunBean;
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
public class DetallePreguntaBean{

	/*ATRIBUTOS*/
	@ManagedProperty("#{detallePreguntaServicio}")
	private DetallePreguntaServicio detallePreguntaServicio;

	@ManagedProperty(value="#{comunBean}")
	private ComunBean comunBean;

	private PreguntaEntity pregunta = new PreguntaEntity();
	private List<OpcionEntity> opciones = new ArrayList<OpcionEntity>();
	private int respuestas = 0;


	/*METODOS*/

	public void cargarDetallePregunta() {

		pregunta = detallePreguntaServicio.consultarPregunta(pregunta.getId());
		opciones = detallePreguntaServicio.consultarOpcionesPreg(pregunta.getId());
		respuestas = detallePreguntaServicio.consultarNumRespuestasPreg(pregunta.getId());

	}

	public void analizar(){

	comunBean.setTipoMensaje("Exito");
		comunBean.setMensaje("Ya se encuentran registradas respuestas para esta pregunta, por lo que no puede ser modificada ni eliminada. La misma fue desactivada y no podra ser utilizada en futuras encuestas.");

	}

	public void eliminar(){

		comunBean.setTipoMensaje("Error");
		comunBean.setMensaje("mensaje prueba de error");

	}



/* GET & SET */

	public DetallePreguntaServicio getDetallePreguntaServicio() {
		return detallePreguntaServicio;
	}

	public void setDetallePreguntaServicio(DetallePreguntaServicio detallePreguntaServicio) {
		this.detallePreguntaServicio = detallePreguntaServicio;
	}

	public ComunBean getComunBean() {
		return comunBean;
	}

	public void setComunBean(ComunBean comunBean) {
		this.comunBean = comunBean;
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