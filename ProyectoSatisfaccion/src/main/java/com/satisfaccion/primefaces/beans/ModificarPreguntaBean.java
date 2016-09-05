package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.OpcionEntity;
import com.satisfaccion.jpa.data.PreguntaEntity;
import com.satisfaccion.spring.service.CrearPreguntaServicio;
import com.satisfaccion.spring.service.ModificarPreguntaServicio;
import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.util.comun.MensajesComun;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class ModificarPreguntaBean {

	/*ATRIBUTOS*/
	@ManagedProperty("#{modificarPreguntaServicio}")
	private ModificarPreguntaServicio modificarPreguntaServicio;

	@ManagedProperty(value="#{mensajesComun}")
	private MensajesComun mensajesComun;

	private PreguntaEntity pregunta = new PreguntaEntity();
	private List<OpcionEntity> opciones = new ArrayList<OpcionEntity>();

	private Boolean evaluacion;

	private Boolean modificacion = false;

	private String tipoPregunta;
	private Boolean banderaPregunta = false;
	private Boolean banderaEvaluacion = false;
	private List<OpcionEntity> opcionesEliminar = new ArrayList<OpcionEntity>();


/*METODOS*/

	@PostConstruct
	public void cargarDetallePregunta(){

		//Obtener parametro por redireccion
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

		pregunta.setId(Integer.parseInt(id));

		pregunta = modificarPreguntaServicio.consultarPregunta(pregunta.getId());
		opciones = modificarPreguntaServicio.consultarOpciones(pregunta.getId());

		if (pregunta.getTipoEncuesta().equals("E")){
			evaluacion = true;
		}else{
			evaluacion = false;
		}

		//Referencia a tipo de pregunta antes de modificacion
		tipoPregunta = pregunta.getTipoPregunta();
	}

	public void inicializarOpciones(){

		int i = 1;

		while (i <= Constantes.CANT_MINIMA_OPCIONES){
			opciones.add(new OpcionEntity());
			i++;
		}

	}

	public void cambiarTipoPregunta() {

		if (tipoPregunta.equals("simple")) {
			//Almaceno opciones iniciales, ya que no puedo perder IDs
			opcionesEliminar = opciones;
			banderaPregunta = true;
		}

		tipoPregunta = pregunta.getTipoPregunta();

		opciones = new ArrayList<OpcionEntity>();
		inicializarOpciones();

		//Limpiar Variable de escala

	}

	public void cambiarEvaluacion(){

		//Bandera señalando que se modifico la evaluacion, para posteriormente eliminarlo de encuesta
		if(banderaEvaluacion){
			banderaEvaluacion = false; //Vuelve estado original
		}else {
			banderaEvaluacion = true;
		}

		if (pregunta.getTipoPregunta().equals("simple")) {
			for (OpcionEntity opcion : opciones) {
				opcion.setValor(null);
			}
		}
	}


	public String bt_modificarPregunta(){

		try {

			//Validacion de envio de opciones vacias en tipo "simple", true si esta
			if (validarOpcionesVacias()){
				mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_OPCION_VACIA);
				return "";
			}

			if (evaluacion) {
				pregunta.setTipoEncuesta("E");
			}else{
				pregunta.setTipoEncuesta("N");
			}

			modificacion = modificarPreguntaServicio.modificarPregunta(pregunta, opciones, banderaPregunta, opcionesEliminar, banderaEvaluacion, evaluacion );

			if (modificacion) {

				mensajesComun.guardarMensaje(true, Constantes.MENSAJE_TIPO_EXITO, Constantes.EX_MODIFICAR_PREGUNTA);
				return "Exito";

			}else {
				mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_MODIFICAR_PREGUNTA);

				return "";

			}

		}catch (Exception e){
			mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_MODIFICAR_PREGUNTA);

			return "";

		}

	}

	public boolean validarOpcionesVacias(){

		if (pregunta.getTipoPregunta().equals("simple")){

			for (OpcionEntity opcion : opciones){

				if (opcion.getTitulo() == null || opcion.getTitulo().trim().equals("")){
					return true;
				}

			}

		}

		return false;
	}

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

	public String bt_cancelar(){

		return "Cancelar";
	}

/*GET & SET*/

	public ModificarPreguntaServicio getModificarPreguntaServicio() {
		return modificarPreguntaServicio;
	}

	public void setModificarPreguntaServicio(ModificarPreguntaServicio modificarPreguntaServicio) {
		this.modificarPreguntaServicio = modificarPreguntaServicio;
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

	public Boolean getBanderaPregunta() {
		return banderaPregunta;
	}

	public void setBanderaPregunta(Boolean banderaPregunta) {
		this.banderaPregunta = banderaPregunta;
	}

	public Boolean getBanderaEvaluacion() {
		return banderaEvaluacion;
	}

	public void setBanderaEvaluacion(Boolean banderaEvaluacion) {
		this.banderaEvaluacion = banderaEvaluacion;
	}

	public List<OpcionEntity> getOpcionesEliminar() {
		return opcionesEliminar;
	}

	public void setOpcionesEliminar(List<OpcionEntity> opcionesEliminar) {
		this.opcionesEliminar = opcionesEliminar;
	}
}

