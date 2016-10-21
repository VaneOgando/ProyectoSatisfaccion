package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.EncuestaEntity;
import com.satisfaccion.jpa.data.PreguntaEntity;
import com.satisfaccion.spring.service.CrearEncuestaServicio;
import com.satisfaccion.spring.service.ModificarEncuestaServicio;
import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.util.comun.MensajesComun;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@ManagedBean
@ViewScoped
public class ModificarEncuestaBean implements Converter {

	/*ATRIBUTOS*/
	@ManagedProperty("#{modificarEncuestaServicio}")
	private ModificarEncuestaServicio modificarEncuestaServicio;

	@ManagedProperty(value="#{mensajesComun}")
	private MensajesComun mensajesComun;

	/*Encuesta a modificar*/
	private EncuestaEntity encuesta = new EncuestaEntity();

	/*Listas para el picklist*/
	private DualListModel<PreguntaEntity> preguntasPickList;

	private List<PreguntaEntity> preguntasDisponibles = new ArrayList<PreguntaEntity>();
	private List<PreguntaEntity> preguntasSelecconadas = new ArrayList<PreguntaEntity>();


	/*Usadas en el manejo de los formularios*/
	private Boolean evaluacion = false;
	private String nombreInicial;

	private Boolean modificacion = false;



/*METODOS*/

	@PostConstruct
	public void cargarDetalleEncuesta(){

		//Obtener parametro por redireccion
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

		encuesta.setId(Integer.parseInt(id));

		encuesta = modificarEncuestaServicio.buscarEncuesta(encuesta.getId());
		nombreInicial = encuesta.getNombre();

		if (encuesta.getTipoEncuesta().equals("E")) {
			evaluacion = true;
		}

		//Cargar preguntas de la encuesta seleccionada
		preguntasSelecconadas = new ArrayList<PreguntaEntity>(modificarEncuestaServicio.buscarPreguntasPorEncuesta(encuesta.getId()));

		//Cargar todas las preguntas disponibles y crea el picklist
		cargarPreguntasTipoEncuesta();

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
		preguntasDisponibles = modificarEncuestaServicio.cargarPreguntasActivas(tipoEncuesta);

		//Elimina de las preguntas disponibles las ya seleccionadas
		eliminarDisponiblesYaSeleccionadas();

		//Armar el picklist final para la encusta copiada
		preguntasPickList = new DualListModel<PreguntaEntity>(preguntasDisponibles, preguntasSelecconadas);

		validarPickList();

	}

	/*Metodo para validar que el nombre de la encuesta no exista, evitando malentendidos*/
	public boolean validarNombre(){

		boolean valido;

		valido = modificarEncuestaServicio.buscarEncuestaPorNombre(encuesta.getNombre().trim());

		return valido;
	}

	/*Validacion de picklist que no se encuentre vacio en ambas listas,
	generar mensaje de error dependiendo del tab seleccionado*/
	public void validarPickList() {

		if (preguntasPickList.getSource().size() == 0 && preguntasPickList.getTarget().size() == 0){

			mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_SIN_PREGUNTAS);
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


	public String bt_modificarEncuesta(){

		try {
			//Si el nombre no cambio no se valida la existencia de otro
			if ( encuesta.getNombre().trim().equals(nombreInicial) || validarNombre()) {

				//Validacion de al menos 1 pregunta seleccionada
				if (preguntasPickList.getTarget().size() > 0) {

					if (evaluacion) {
						encuesta.setTipoEncuesta("E");
					} else {
						encuesta.setTipoEncuesta("N");
					}

					//Toda encuesta que se modifique debe estar activa
					encuesta.setEstado("A");

					encuesta.setPreguntas(new HashSet<PreguntaEntity>(preguntasPickList.getTarget()));

					modificacion = modificarEncuestaServicio.modificarEncuestas(encuesta);

					if (modificacion) {

						mensajesComun.guardarMensaje(true, Constantes.MENSAJE_TIPO_EXITO, Constantes.EX_MODIFICAR_ENCUESTA);
						return "Exito";

					} else {
						mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_MODIFICAR_ENCUESTA);

						return "";

					}

				} else {
					mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_CANT_MINIMA_PREGUNTAS);
					return "";
				}

			}else {

				mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_NOMBRE_ENCUESTA);
				encuesta.setNombre(null);
				return "";
			}

		}catch (Exception e){
			mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_MODIFICAR_ENCUESTA);

			return "";

		}

	}


	public String bt_cancelar(){

		return "Cancelar";
	}

	/*METODOS DEL CONVERTER*/

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		PreguntaEntity pregunta = modificarEncuestaServicio.buscarPreguntaPorID(value);

		return pregunta;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

	/*GET & SET*/

	public ModificarEncuestaServicio getModificarEncuestaServicio() {
		return modificarEncuestaServicio;
	}

	public void setModificarEncuestaServicio(ModificarEncuestaServicio modificarEncuestaServicio) {
		this.modificarEncuestaServicio = modificarEncuestaServicio;
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

	public String getNombreInicial() {
		return nombreInicial;
	}

	public void setNombreInicial(String nombreInicial) {
		this.nombreInicial = nombreInicial;
	}

	public Boolean getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(Boolean evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Boolean getModificacion() {
		return modificacion;
	}

	public void setModificacion(Boolean modificacion) {
		this.modificacion = modificacion;
	}
}

