package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.EncuestaEntity;
import com.satisfaccion.jpa.data.PreguntaEntity;
import com.satisfaccion.spring.service.CrearEncuestaServicio;
import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.util.comun.MensajesComun;
import org.primefaces.model.DualListModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.*;

@ManagedBean
@ViewScoped
public class CrearEncuestaBean implements Converter {

	/*ATRIBUTOS*/
	@ManagedProperty("#{crearEncuestaServicio}")
	private CrearEncuestaServicio crearEncuestaServicio;

	@ManagedProperty(value="#{mensajesComun}")
	private MensajesComun mensajesComun;

	/*Encuesta a crear*/
	private EncuestaEntity encuesta = new EncuestaEntity();

	/*Encuestas existentes para copiar*/
	private List<EncuestaEntity> encuestasCopia = new ArrayList<EncuestaEntity>();

	/*Listas para el picklist*/
	private DualListModel<PreguntaEntity> preguntasPickList;

	private List<PreguntaEntity> preguntasDisponibles = new ArrayList<PreguntaEntity>();
	private List<PreguntaEntity> preguntasSelecconadas = new ArrayList<PreguntaEntity>();


	/*Usadas en el manejo de los formularios*/
	private boolean preCreacion = true;
	private int tabActiva = 0;
	private Boolean evaluacion = false;

	/*Usadas en la creacion de la encuesta*/
	private Date fechaActual = new Date();
	private String usuarioCreador = "";

	private Boolean creacion = false;



/*METODOS*/

	@PostConstruct
	public void init(){

		encuestasCopia = crearEncuestaServicio.cargarEncuestasActivas();

		cargarPreguntasTipoEncuesta();
		preguntasPickList = new DualListModel<PreguntaEntity>(preguntasDisponibles, preguntasSelecconadas);


	}

	public void cambiarEvaluacion(){

		preguntasDisponibles = new ArrayList<PreguntaEntity>();
		preguntasSelecconadas = new ArrayList<PreguntaEntity>();

		cargarPreguntasTipoEncuesta();

	}

	public void cargarPreguntasTipoEncuesta(){

		String tipoEncuesta;

		if (evaluacion){
			tipoEncuesta = "E";
		}else{
			tipoEncuesta = "N";
		}

		/*Carga de preguntas disponibles para el tipo seleccionado*/
		preguntasDisponibles = crearEncuestaServicio.cargarPreguntasActivas(tipoEncuesta);

		//Elimina de las preguntas disponibles las ya seleccionadas
		eliminarDisponiblesYaSeleccionadas();

		//Armar el picklist final para la encusta copiada
		preguntasPickList = new DualListModel<PreguntaEntity>(preguntasDisponibles, preguntasSelecconadas);

		validarPickList();

	}

	/*Accion cuando se selecciona una encuesta a copiar, agrega el string "Copia de" al nombre existente*/
	public void seleccionEncuestaCopia(){

		encuesta = crearEncuestaServicio.buscarEncuestaCopia(encuesta.getId());
		encuesta.setNombre(Constantes.NOMBRE_ENCUESTA_COPIA + encuesta.getNombre() );
	}

	/*Metodo para validar que el nombre de la encuesta no exista, evitando malentendidos*/
	public boolean validarNombre(){

		boolean valido;

		valido = crearEncuestaServicio.buscarEncuestaPorNombre(encuesta.getNombre().trim());

		return valido;
	}

	/*Validacion de picklist que no se encuentre vacio en ambas listas,
	generar mensaje de error dependiendo del tab seleccionado*/
	public void validarPickList() {

		if (preguntasPickList.getSource().size() == 0 && preguntasPickList.getTarget().size() == 0){

			mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_SIN_PREGUNTAS);
		}

	}


	public void bt_continuar(){

		if(validarNombre()) {

			preCreacion = false;

			if (encuesta.getId() != 0) {   //Encuesta copia, cargar sus datos

				if (encuesta.getTipoEncuesta().equals("E")) {
					evaluacion = true;
				}

				//Cargar preguntas de la encuesta seleccionada
				preguntasSelecconadas = new ArrayList<PreguntaEntity>(crearEncuestaServicio.buscarPreguntasPorEncuesta(encuesta.getId()));

				//Cargar todas las preguntas disponibles y crea el picklist
				cargarPreguntasTipoEncuesta();

			}
		}else {

			mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_NOMBRE_ENCUESTA);
			encuesta.setNombre(null);
		}

	}


	public void eliminarDisponiblesYaSeleccionadas(){

		int i = 0;
		while ( i < preguntasSelecconadas.size()){

			//Eliminar preguntas inactivas
			if (preguntasSelecconadas.get(i).getEstado().equals("I")){
				preguntasSelecconadas.remove(i);

				i--;
			}else{

				int j = 0;
				while (j < preguntasDisponibles.size()){

					//Pregunta ya seleccionada, eliminar de las disponibles
					if(preguntasSelecconadas.get(i).getId() == preguntasDisponibles.get(j).getId()){

						preguntasDisponibles.remove(j);
						j = preguntasDisponibles.size();
					}
					j++;
				}

			}

			i++;
		}

	}


	public String bt_crearEncuesta(){

		try {

			//Eliminar ID si es una copia, evitar modificar el actual
			if(encuesta.getId() != 0){
				encuesta.setId(0);
			}

			//Validacion de al menos 1 pregunta seleccionada
			if (preguntasPickList.getTarget().size() > 0){

				encuesta.setEstado("A");
				encuesta.setFechaCreacion(fechaActual);

				//Obtener usuario conectado
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				encuesta.setUsuarioCreador(auth.getName());

				if (evaluacion) {
					encuesta.setTipoEncuesta("E");
				}else{
					encuesta.setTipoEncuesta("N");
				}

				encuesta.setPreguntas( new HashSet<PreguntaEntity>(preguntasPickList.getTarget()));

				creacion = crearEncuestaServicio.crearEncuestas(encuesta);

				if (creacion) {

					mensajesComun.guardarMensaje(true, Constantes.MENSAJE_TIPO_EXITO, Constantes.EX_CREAR_ENCUESTA);
					return "Exito";

				}else {
					mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_CREAR_ENCUESTA);

					limpiarEncuesta();
					return "";

				}

			}else{
				mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_CANT_MINIMA_PREGUNTAS);
				return "";
			}

		}catch (Exception e){
			mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_CREAR_ENCUESTA);

			limpiarEncuesta();
			return "";

		}

	}


	public void limpiarEncuesta(){

		encuesta = new EncuestaEntity();
		evaluacion = false;

		cambiarEvaluacion();

	}

	public String bt_cancelar(){

		return "Cancelar";
	}

	/*METODOS DEL CONVERTER*/

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		PreguntaEntity pregunta = crearEncuestaServicio.buscarPreguntaPorID(value);

		return pregunta;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
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

	public DualListModel<PreguntaEntity> getPreguntasPickList() {
		return preguntasPickList;
	}

	public void setPreguntasPickList(DualListModel<PreguntaEntity> preguntasPickList) {
		this.preguntasPickList = preguntasPickList;
	}

	public List<PreguntaEntity> getPreguntasDisponibles() {
		return preguntasDisponibles;
	}

	public void setPreguntasDisponibles(List<PreguntaEntity> preguntasDisponibles) {
		this.preguntasDisponibles = preguntasDisponibles;
	}

	public List<PreguntaEntity> getPreguntasSelecconadas() {
		return preguntasSelecconadas;
	}

	public void setPreguntasSelecconadas(List<PreguntaEntity> preguntasSelecconadas) {
		this.preguntasSelecconadas = preguntasSelecconadas;
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

	public Boolean getCreacion() {
		return creacion;
	}

	public void setCreacion(Boolean creacion) {
		this.creacion = creacion;
	}

}

