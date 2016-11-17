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

	/*Lista para almacenar y desplegar las respuestas*/
	private List<RespuestaEntity> respuestas = new ArrayList<RespuestaEntity>();

	/*Usadas en el manejo del formulario*/
	private boolean detalleEncuesta = false;
	private boolean errorEncuesta = false;
	private String usuarioEvaluado;

	/*Usadas en la creacion de la respuesta*/
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

				cargarDetalleEnvio();

			} else { //Mostrar envio de encuesta
				detalleEncuesta = false;
				envio = detalleEncuestaServicio.buscarEnvio(Integer.parseInt(variables[0]));

				if(envio.getDestinatario().equals(variables[2]) && envio.getEstado().equals("P")){
					encuesta = envio.getEncuesta();
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

		preguntas = new ArrayList<PreguntaEntity>(encuesta.getPreguntas());

		int i = 0;
		while (i < preguntas.size()){

			RespuestaEntity respuesta = new RespuestaEntity();

			if(preguntas.get(i).getEstado().equals("A")){

				respuesta.setPregunta(preguntas.get(i));

				//Para preguntas simples inicializar las opciones
				if(preguntas.get(i).getTipoPregunta().equals("simple")){
					respuesta.setOpcion( new OpcionEntity());
				}

				//Almacenar envio cuando aplique
				if(!detalleEncuesta){
					respuesta.setEnvio(envio);
				}

				respuestas.add(respuesta);

			}

			i++;
		}

		//En envio, si encuesta esta inactiva o no tiene preguntas activas es error
		if(!detalleEncuesta && ( encuesta.getEstado().equals("I") || respuestas.size() < 1 )){
			errorEncuesta = true;
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

			boolean fallo = false;

			for (RespuestaEntity respuesta : respuestas){
				
				if(respuesta.getPregunta().getTipoEncuesta().equals("simple") && respuesta.getOpcion().getId() != 0){
					fallo = false;
					break;
				}



			}

			//Validar preguntas respondidas
			//Almacenar en BD

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

	public String getUsuarioEvaluado() {
		return usuarioEvaluado;
	}

	public void setUsuarioEvaluado(String usuarioEvaluado) {
		this.usuarioEvaluado = usuarioEvaluado;
	}

	public Boolean getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Boolean respuesta) {
		this.respuesta = respuesta;
	}
}

