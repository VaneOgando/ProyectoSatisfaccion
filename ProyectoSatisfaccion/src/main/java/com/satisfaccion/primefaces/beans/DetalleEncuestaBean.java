package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.*;
import com.satisfaccion.spring.service.CrearEncuestaServicio;
import com.satisfaccion.spring.service.DetalleEncuestaServicio;
import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.util.comun.Encriptacion;
import com.satisfaccion.util.comun.MensajesComun;
import com.sun.deploy.net.URLEncoder;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@ManagedBean
@ViewScoped
public class DetalleEncuestaBean {

	/*ATRIBUTOS*/
	@ManagedProperty("#{detalleEncuestaServicio}")
	private DetalleEncuestaServicio detalleEncuestaServicio;

	@ManagedProperty(value = "#{mensajesComun}")
	private MensajesComun mensajesComun;

	@ManagedProperty(value = "#{encriptacion}")
	private Encriptacion encriptacion;

	/*Detalle de encuesta*/
	private EnvioEntity envio = new EnvioEntity();
	private EncuestaEntity encuesta = new EncuestaEntity();
	private List<PreguntaEntity> preguntas = new ArrayList<PreguntaEntity>();

	/*Lista para almacenar las respuestas*/
	private List<RespuestaEntity> respuestas = new ArrayList<RespuestaEntity>();

	/*Usadas en el manejo del formulario*/
	private boolean detalleEncuesta = false;
	private boolean errorEncuesta = false;

	/*Usadas en la creacion de la respuesta*/
	private Date fechaActual = new Date();
	private String usuarioCreador = "";

	private Boolean respuesta = false;



/*METODOS*/

	@PostConstruct
	public void init() {

		try {

			String linkEncuesta = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("e");

			//Envio ya viene decodificado
			if(linkEncuesta.contains("=")){
				linkEncuesta = encriptacion.encriptarEnvio(linkEncuesta, false);
			}else {
				linkEncuesta = encriptacion.encriptarEnvio(URLDecoder.decode(linkEncuesta, "UTF-8"), false);
			}

			String[] variables = linkEncuesta.split(";");

			//Mostrar detalle encuesta
			if (variables[1].equals("true")) {
				detalleEncuesta = true;
				encuesta = detalleEncuestaServicio.buscarEncuesta(Integer.parseInt(variables[0]));
				preguntas = new ArrayList<PreguntaEntity>(encuesta.getPreguntas());

				validarPreguntasActivas();

			} else { //Mostrar envio de encuesta
				detalleEncuesta = false;
				envio = detalleEncuestaServicio.buscarEnvio(Integer.parseInt(variables[0]));

				if(envio.getDestinatario().equals(variables[2]) && envio.getEstado().equals("P")){
					cargarDetalleEnvio();

				}else {
					errorEncuesta = true;
				}

			}

		} catch (Exception e) {

			errorEncuesta = true;
		}
	}


	public void cargarDetalleEnvio() {

		encuesta = envio.getEncuesta();
		preguntas = new ArrayList<PreguntaEntity>(encuesta.getPreguntas());

		if (encuesta.getEstado().equals("A")){
			validarPreguntasActivas();

		}else {
			errorEncuesta = true;
		}

	}

	public void validarPreguntasActivas() {

		int i = 0;

		while (i < preguntas.size()) {

			//Eliminar preguntas inactivas
			if (preguntas.get(i).getEstado().equals("I")) {
				preguntas.remove(i);
				i--;
			}
			i++;
		}

		if (!detalleEncuesta) {

			if (preguntas.size() < 1) {

				errorEncuesta = true;
			}

		}
	}

	public String bt_crearEncuesta() {
/*

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
				//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				//encuesta.setUsuarioCreador(auth.getName);

				encuesta.setUsuarioCreador("vanessa.rodriguez");

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
*/
		return "";
	}

	public String bt_listoEncuesta(){

		if(detalleEncuesta){

			return "consultarEncuesta.xhtml?faces-redirect=true";
		}else{

			//guardar las respuestas dadas

			return "";
		}

	}



	/*GET & SET*/

	public DetalleEncuestaServicio getDetalleEncuestaServicio() {
		return detalleEncuestaServicio;
	}

	public void setDetalleEncuestaServicio(DetalleEncuestaServicio detalleEncuestaServicio) {
		this.detalleEncuestaServicio = detalleEncuestaServicio;
	}

	public MensajesComun getMensajesComun() {
		return mensajesComun;
	}

	public void setMensajesComun(MensajesComun mensajesComun) {
		this.mensajesComun = mensajesComun;
	}

	public Encriptacion getEncriptacion() {
		return encriptacion;
	}

	public void setEncriptacion(Encriptacion encriptacion) {
		this.encriptacion = encriptacion;
	}

	public EncuestaEntity getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(EncuestaEntity encuesta) {
		this.encuesta = encuesta;
	}

	public EnvioEntity getEnvio() {
		return envio;
	}

	public void setEnvio(EnvioEntity envio) {
		this.envio = envio;
	}

	public List<PreguntaEntity> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<PreguntaEntity> preguntas) {
		this.preguntas = preguntas;
	}

	public List<RespuestaEntity> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<RespuestaEntity> respuestas) {
		this.respuestas = respuestas;
	}

	public boolean isDetalleEncuesta() {
		return detalleEncuesta;
	}

	public void setDetalleEncuesta(boolean detalleEncuesta) {
		this.detalleEncuesta = detalleEncuesta;
	}

	public boolean isErrorEncuesta() {
		return errorEncuesta;
	}

	public void setErrorEncuesta(boolean errorEncuesta) {
		this.errorEncuesta = errorEncuesta;
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

	public Boolean getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Boolean respuesta) {
		this.respuesta = respuesta;
	}
}

