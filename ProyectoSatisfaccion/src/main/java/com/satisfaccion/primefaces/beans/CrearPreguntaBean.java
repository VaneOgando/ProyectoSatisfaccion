package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.*;
import com.satisfaccion.spring.service.CrearPreguntaServicio;
import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.util.comun.MensajesComun;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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

/*
	public String bt_crearRecurso(){

		try {

			if (opcionRecurso.equals("E")) {
				if (equipo.getFechaCompra() == null) {
					equipo.setFechaCompra(fechaActual);
				}
			} else {
				if (accesorio.getFechaCompra() == null) {
					accesorio.setFechaCompra(fechaActual);
				}

				//Validar existencia de categoria
				if (crearRecursoServicio.obtenerCategoriaPorNombre(categoria.getNombre(), "accesorio") != null) {
					setCategoria(crearRecursoServicio.obtenerCategoriaPorNombre(categoria.getNombre(), "accesorio"));
				} else {
					categoria.setId(0);
				}

			}

			estado = crearRecursoServicio.obtenerEstado(Constantes.D_ID_ESTADO_CREACION);
			crearHistorial();

			//Validar existencia de modelo
			if (crearRecursoServicio.obtenerModeloPorNombre(modelo.getNombre(), marca.getId()) != null) {
				setModelo(crearRecursoServicio.obtenerModeloPorNombre(modelo.getNombre(), marca.getId()));
			} else {
				modelo.setId(0);
			}

			creacion = crearRecursoServicio.crearRecurso(marca, modelo, categoria, estado, historial, equipo, accesorio, opcionRecurso);

			if (creacion == true) {

				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constantes.EX_CREAR, null));

				return "Exito";

			}else {
				FacesContext.getCurrentInstance().addMessage("mensajesError", new FacesMessage(FacesMessage.SEVERITY_FATAL, Constantes.ERR_CREAR, null));
				RequestContext.getCurrentInstance().update("mensajesError");

				inicializarListas();
				return "";

			}

		}catch (Exception e){
			FacesContext.getCurrentInstance().addMessage("mensajesError", new FacesMessage(FacesMessage.SEVERITY_FATAL, Constantes.ERR_CREAR, null));
			RequestContext.getCurrentInstance().update("mensajesError");

			inicializarListas();
			return "";

		}

	}
*/
	/*
	* 		//Obtener usuario conectado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		historial.setResponsableSoporte( auth.getName() );

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

			mensajesComun.setTipoMensaje(Constantes.MENSAJE_TIPO_ERROR);
			mensajesComun.setMensaje(Constantes.ERR_CANT_MINIMA_OPCIONES);
			mensajesComun.guardarMensaje(Constantes.GROWL_ERROR);

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

