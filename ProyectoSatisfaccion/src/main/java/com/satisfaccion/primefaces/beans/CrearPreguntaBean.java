package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.*;
import com.satisfaccion.spring.service.CrearPreguntaServicio;
import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.util.comun.MensajesComun;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class CrearPreguntaBean {

	/*ATRIBUTOS*/
	@ManagedProperty("#{crearPreguntaServicio}")
	private CrearPreguntaServicio crearPreguntaServicio;

	@ManagedProperty(value="#{mensajesComun}")
	private MensajesComun mensajesComun;

	private PreguntaEntity pregunta = new PreguntaEntity();
	private List<OpcionEntity> opciones = new ArrayList<OpcionEntity>();

	private String tipoPregunta = "simple";
	private Boolean evaluacion = false;

	private Date fechaActual = new Date();
	private String usuarioCreador = "";

	private Boolean creacion = false;


/*METODOS*/

	@PostConstruct
	public void init() {

		pregunta.setTipoPregunta("simple");
		inicializarOpciones();
	}

	public void inicializarOpciones(){

		int i = 1;

		while (i <= Constantes.CANT_MINIMA_OPCIONES){
			opciones.add(new OpcionEntity());
			i++;
		}

	}

	public void cambiarTipoPregunta(){

		opciones = new ArrayList<OpcionEntity>();
		inicializarOpciones();

		//Limpiar Variable de escala
	}

	public void cambiarEvaluacion(){

		if (pregunta.getTipoPregunta().equals("simple")) {
			for (OpcionEntity opcion : opciones) {
				opcion.setValor(null);
			}
		}
	}


	public String bt_crearPregunta(){

		try {

			pregunta.setEstado("A");
			pregunta.setFechaCreacion(fechaActual);

			//Obtener usuario conectado
			//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			//pregunta.setUsuarioCreador(auth.getName);

			pregunta.setUsuarioCreador("vanessa.rodriguez");

			if (evaluacion) {
				pregunta.setTipoEncuesta("E");
			}else{
				pregunta.setTipoEncuesta("N");
			}

			creacion = crearPreguntaServicio.crearPregunta(pregunta, opciones);

			if (creacion) {

				mensajesComun.guardarMensaje(true, Constantes.MENSAJE_TIPO_EXITO, Constantes.EX_CREAR_PREGUNTA);
				return "Exito";

			}else {
				mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_CREAR_PREGUNTA);

				limpiarPregunta();
				return "";

			}

		}catch (Exception e){
			mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_CREAR_PREGUNTA);

			limpiarPregunta();
			return "";

		}

	}

	/*
	*
	* */

	public void agregarOpcion(OpcionEntity opcion) {

		int i = opciones.indexOf(opcion);

		opciones.add(i + 1, new OpcionEntity());

	}

	public void eliminarOpcion(OpcionEntity opcion) {

		if ((opciones.size() - 1) >= Constantes.CANT_MINIMA_OPCIONES){

			int i = opciones.indexOf(opcion);
			opciones.remove(i);

		}else{

			mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_CANT_MINIMA_OPCIONES);

		}

	}

	public void limpiarPregunta(){

		pregunta = new PreguntaEntity();
		opciones = new ArrayList<OpcionEntity>();
		pregunta.setTipoPregunta("simple");
		evaluacion = false;

		inicializarOpciones();
	}

	public String bt_cancelar(){

		return "Cancelar";
	}

/*GET & SET*/

	public CrearPreguntaServicio getCrearPreguntaServicio() {
		return crearPreguntaServicio;
	}

	public void setCrearPreguntaServicio(CrearPreguntaServicio crearPreguntaServicio) {
		this.crearPreguntaServicio = crearPreguntaServicio;
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

	public String getTipoPregunta() {
		return tipoPregunta;
	}

	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}

	public Boolean getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(Boolean evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}
}

