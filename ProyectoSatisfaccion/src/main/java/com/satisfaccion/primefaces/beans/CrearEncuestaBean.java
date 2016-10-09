package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.EncuestaEntity;
import com.satisfaccion.jpa.data.PreguntaEntity;
import com.satisfaccion.spring.service.CrearEncuestaServicio;
import com.satisfaccion.spring.service.CrearPreguntaServicio;
import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.util.comun.MensajesComun;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class CrearEncuestaBean {

	/*ATRIBUTOS*/
	@ManagedProperty("#{crearEncuestaServicio}")
	private CrearEncuestaServicio crearEncuestaServicio;

	@ManagedProperty(value="#{mensajesComun}")
	private MensajesComun mensajesComun;

	/*Encuesta a crear*/
	private EncuestaEntity encuesta = new EncuestaEntity();

	/*Encuestas existentes para copiar*/
	private List<EncuestaEntity> encuestasCopia = new ArrayList<EncuestaEntity>();

	private List<PreguntaEntity> listaPreguntas = new ArrayList<PreguntaEntity>();
	private PreguntaEntity preguntaSeleccionada = new PreguntaEntity();
	private List<PreguntaEntity> preguntas = new ArrayList<PreguntaEntity>();

	/*Usadas en el manejo de los formularios*/
	private boolean preCreacion = true;
	private int tabActiva = 0;

	/*Usadas en la creacion de la encuesta*/
	private Date fechaActual = new Date();
	private String usuarioCreador = "";

	private Boolean creacion = false;


/*METODOS*/

	@PostConstruct
	public void init(){

		encuestasCopia = crearEncuestaServicio.cargarEncuestasActivas();
	}

	public void seleccionEncuestaCopia(){

		encuesta = crearEncuestaServicio.buscarEncuestaCopia(encuesta.getId());
		encuesta.setNombre(Constantes.NOMBRE_ENCUESTA_COPIA + encuesta.getNombre() );
	}

	public void bt_continuar(){

		preCreacion = false;

		if(encuesta.getId() != 0){
			//Encuesta Copia, cargas las preguntas
		}else{
			//Encuesta nueva
		}


	}


/*
	public String bt_crearEncuesta(){

		try {

			//Validacion de envio de opciones vacias en tipo "simple", true si esta vacio
			if (validarOpcionesVacias()){
				mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_OPCION_VACIA);
				return "";
			}

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
*/

	public void limpiarEncuesta(){

		encuesta = new EncuestaEntity();


	}

	public String bt_cancelar(){

		return "Cancelar";
	}

/*GET & SET*/

	public CrearEncuestaServicio getCrearEncuestaServicio() {
		return crearEncuestaServicio;
	}

	public void setCrearEncuestaServicio(CrearEncuestaServicio crearEncuestaServicio) {
		this.crearEncuestaServicio = crearEncuestaServicio;
	}

	public MensajesComun getMensajesComun() {
		return mensajesComun;
	}

	public void setMensajesComun(MensajesComun mensajesComun) {
		this.mensajesComun = mensajesComun;
	}

	public EncuestaEntity getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(EncuestaEntity encuesta) {
		this.encuesta = encuesta;
	}

	public List<EncuestaEntity> getEncuestasCopia() {
		return encuestasCopia;
	}

	public void setEncuestasCopia(List<EncuestaEntity> encuestasCopia) {
		this.encuestasCopia = encuestasCopia;
	}

	public List<PreguntaEntity> getListaPreguntas() {
		return listaPreguntas;
	}

	public void setListaPreguntas(List<PreguntaEntity> listaPreguntas) {
		this.listaPreguntas = listaPreguntas;
	}

	public PreguntaEntity getPreguntaSeleccionada() {
		return preguntaSeleccionada;
	}

	public void setPreguntaSeleccionada(PreguntaEntity preguntaSeleccionada) {
		this.preguntaSeleccionada = preguntaSeleccionada;
	}

	public List<PreguntaEntity> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<PreguntaEntity> preguntas) {
		this.preguntas = preguntas;
	}

	public boolean isPreCreacion() {
		return preCreacion;
	}

	public void setPreCreacion(boolean preCreacion) {
		this.preCreacion = preCreacion;
	}

	public int getTabActiva() {
		return tabActiva;
	}

	public void setTabActiva(int tabActiva) {
		this.tabActiva = tabActiva;
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

	public Boolean getCreacion() {
		return creacion;
	}

	public void setCreacion(Boolean creacion) {
		this.creacion = creacion;
	}

}

