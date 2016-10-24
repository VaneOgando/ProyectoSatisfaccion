package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.EncuestaEntity;
import com.satisfaccion.jpa.data.EnvioEntity;
import com.satisfaccion.jpa.data.PreguntaEntity;
import com.satisfaccion.jpa.data.ProyectoEntity;
import com.satisfaccion.spring.service.CrearEncuestaServicio;
import com.satisfaccion.spring.service.EnviarEncuestaServicio;
import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.util.comun.MensajesComun;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.xml.soap.SAAJResult;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedBean
@ViewScoped
public class EnviarEncuestaBean{

	/*ATRIBUTOS*/
	@ManagedProperty("#{enviarEncuestaServicio}")
	private EnviarEncuestaServicio enviarEncuestaServicio;

	@ManagedProperty(value="#{mensajesComun}")
	private MensajesComun mensajesComun;

	/*Envio a realizar*/
	private EnvioEntity envio = new EnvioEntity();

	/*Listas a desplegar*/
	private List<EncuestaEntity> encuestas = new ArrayList<EncuestaEntity>();
	private List<ProyectoEntity> proyectos = new ArrayList<ProyectoEntity>();

	/*Listas seleccionadas*/
	private EncuestaEntity encuestaSelect = new EncuestaEntity();
	private ProyectoEntity proyectoSelect = new ProyectoEntity();

	/*Usadas en el manejo de los formularios*/
	private Boolean evaluacion = false;
	private Boolean anonimo = true;
	private String destinatarios;

	private Boolean resumen = false;
	private String destinosValidos = "";
	private String destinosInvalidos = "";

	/*Usadas en el envio de la encuesta*/
	private Date fechaActual = new Date();
	private String usuarioCreador = "";

	private Boolean resultadoEnvio = false;



/*METODOS*/

	@PostConstruct
	public void init(){

		//Obtener parametro por redireccion
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

		if(id != null){
			encuestaSelect = enviarEncuestaServicio.buscarEncuestaPorId(Integer.parseInt(id));

			if (encuestaSelect.getTipoEncuesta().equals("E")){
				evaluacion = true;
			}

		}

		if (evaluacion){
			encuestaSelect.setTipoEncuesta("E");
		}else{
			encuestaSelect.setTipoEncuesta("N");
		}

		encuestas = enviarEncuestaServicio.cargarEncuestasValidas(encuestaSelect.getTipoEncuesta());
		proyectos = enviarEncuestaServicio.cargarProyectos();

	}

	public void cambiarEvaluacion(){

		encuestaSelect = new EncuestaEntity();

		if (evaluacion){
			encuestaSelect.setTipoEncuesta("E");
		}else{
			encuestaSelect.setTipoEncuesta("N");
		}

		encuestas = enviarEncuestaServicio.cargarEncuestasValidas(encuestaSelect.getTipoEncuesta());
	}

	public boolean validarEmail(String email) {

		Pattern pattern = Pattern.compile(Constantes.FORMATO_EMAIL);

		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}




	public void bt_enviarEncuesta(){

		//Obtener los destinatarios ingresados
		String[] listaDestinatario = destinatarios.split(",");

		for (String destino : listaDestinatario){

			try{

				//Email con sintaxis correcta
				Boolean emailValido = validarEmail(destino.trim());

				if(emailValido) {

					envio = new EnvioEntity();

					envio.setEstado("P");
					envio.setFechaEnvio(fechaActual);
					//Obtener usuario conectado
					//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					//encuesta.setUsuarioCreador(auth.getName);

					envio.setUsuarioCreador("vanessa.rodriguez");
					envio.setEncuesta(enviarEncuestaServicio.buscarEncuestaPorId(encuestaSelect.getId()));

					envio.setDestinatario(destino.trim());

					//obtener proyecto seleccionado
					if (!anonimo) {
						if (enviarEncuestaServicio.obtenerProyectoPorNombre(proyectoSelect.getNombre()) != null) {
							envio.setProyecto(enviarEncuestaServicio.obtenerProyectoPorNombre(proyectoSelect.getNombre()));
						} else {
							envio.setProyecto(proyectoSelect);
						}
					} else {
						envio.setProyecto(new ProyectoEntity());
					}

					resultadoEnvio = enviarEncuestaServicio.crearEnvioEncuesta(envio);

					if (resultadoEnvio) {
						//envio en este punto ya posee ID, crear su link unico. Encriptar informacion al aparecer en el URL
						//ENVIAR EL CORREO
						llenarResumen(destino.trim(),true);
					} else {
						llenarResumen(destino.trim(), false);
					}

				}else{
					llenarResumen(destino.trim(), false);
				}

			}catch (Exception e){
				llenarResumen(destino.trim(), false);
			}

		}

		resumen = true;

	}

	public void llenarResumen(String destinatario, Boolean valido){

		if (valido){
			if(destinosValidos.equals("")){
				destinosValidos = destinosValidos + destinatario;
			}else {
				destinosValidos = destinosValidos + ", " + destinatario;
			}
		}else{
			if(destinosInvalidos.equals("")){
				destinosInvalidos = destinosInvalidos +destinatario;
			}else {
				destinosInvalidos = destinosInvalidos + ", " + destinatario;
			}
		}

	}


	public String bt_cancelar(){

		return "Cancelar";
	}


	/*GET & SET*/

	public EnviarEncuestaServicio getEnviarEncuestaServicio() {
		return enviarEncuestaServicio;
	}

	public void setEnviarEncuestaServicio(EnviarEncuestaServicio enviarEncuestaServicio) {
		this.enviarEncuestaServicio = enviarEncuestaServicio;
	}

	public MensajesComun getMensajesComun() {
		return mensajesComun;
	}

	public void setMensajesComun(MensajesComun mensajesComun) {
		this.mensajesComun = mensajesComun;
	}

	public EnvioEntity getEnvio() {
		return envio;
	}

	public void setEnvio(EnvioEntity envio) {
		this.envio = envio;
	}

	public Boolean getResultadoEnvio() {
		return resultadoEnvio;
	}

	public void setResultadoEnvio(Boolean resultadoEnvio) {
		this.resultadoEnvio = resultadoEnvio;
	}

	public List<EncuestaEntity> getEncuestas() {
		return encuestas;
	}

	public void setEncuestas(List<EncuestaEntity> encuestas) {
		this.encuestas = encuestas;
	}

	public List<ProyectoEntity> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<ProyectoEntity> proyectos) {
		this.proyectos = proyectos;
	}

	public EncuestaEntity getEncuestaSelect() {
		return encuestaSelect;
	}

	public void setEncuestaSelect(EncuestaEntity encuestaSelect) {
		this.encuestaSelect = encuestaSelect;
	}

	public ProyectoEntity getProyectoSelect() {
		return proyectoSelect;
	}

	public void setProyectoSelect(ProyectoEntity proyectoSelect) {
		this.proyectoSelect = proyectoSelect;
	}

	public Boolean getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(Boolean evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Boolean getAnonimo() {
		return anonimo;
	}

	public void setAnonimo(Boolean anonimo) {
		this.anonimo = anonimo;
	}

	public String getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
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

	public Boolean getResumen() {
		return resumen;
	}

	public void setResumen(Boolean resumen) {
		this.resumen = resumen;
	}

	public String getDestinosValidos() {
		return destinosValidos;
	}

	public void setDestinosValidos(String destinosValidos) {
		this.destinosValidos = destinosValidos;
	}

	public String getDestinosInvalidos() {
		return destinosInvalidos;
	}

	public void setDestinosInvalidos(String destinosInvalidos) {
		this.destinosInvalidos = destinosInvalidos;
	}
}

