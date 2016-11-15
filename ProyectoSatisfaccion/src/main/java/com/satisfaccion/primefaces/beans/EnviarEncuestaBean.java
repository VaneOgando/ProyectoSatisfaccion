package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.EncuestaEntity;
import com.satisfaccion.jpa.data.EnvioEntity;
import com.satisfaccion.jpa.data.ProyectoEntity;
import com.satisfaccion.spring.service.EnviarEncuestaServicio;
import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.spring.service.EmailServicio;
import com.satisfaccion.util.comun.Encriptacion;
import com.satisfaccion.util.comun.MensajesComun;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.net.URLEncoder;
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

	@ManagedProperty(value="#{encriptacion}")
	private Encriptacion encriptacion;

	@ManagedProperty(value="#{emailServicio}")
	private EmailServicio emailServicio;

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

	private Boolean creacionEnvio = false;
	private String linkEncriptado;

	private Integer progress;


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

	public void bt_enviarEncuesta(){

		//Obtener los destinatarios ingresados
		String[] listaDestinatario = destinatarios.split(",");

		for (String destino : listaDestinatario){

			try {

				//Email con sintaxis correcta
				Boolean emailValido = emailServicio.validarSintaxisEmail(destino.trim());

				//Servidor de correo existente
				String[] servidorCorreo = destino.split("@");
				Boolean servidorValido = emailServicio.validarExistenciaServidor(servidorCorreo[1]);

				if(emailValido && servidorValido) {

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
						envio.setProyecto(null);
					}

					//Crear en BD el envio
					creacionEnvio = enviarEncuestaServicio.crearEnvioEncuesta(envio);

					if (creacionEnvio) {

						//Crear link unico. Encriptar informacion a enviar
						linkEncriptado = encriptacion.encriptarEnvio(envio.getId() + ";false;" + envio.getDestinatario(), true);

						String url = Constantes.URL_ENCUESTA + "detalleEncuesta.xhtml?e=" + URLEncoder.encode(linkEncriptado, "UTF-8");

						//Envio del correo electronico
						Boolean envioExitoso = emailServicio.enviarCorreo(envio.getDestinatario(), url, evaluacion);

						llenarResumen(destino.trim(), envioExitoso);

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

	public Encriptacion getEncriptacion() {
		return encriptacion;
	}

	public void setEncriptacion(Encriptacion encriptacion) {
		this.encriptacion = encriptacion;
	}

	public EmailServicio getEmailServicio() {
		return emailServicio;
	}

	public void setEmailServicio(EmailServicio emailServicio) {
		this.emailServicio = emailServicio;
	}

	public EnvioEntity getEnvio() {
		return envio;
	}

	public void setEnvio(EnvioEntity envio) {
		this.envio = envio;
	}

	public Boolean getCreacionEnvio() {
		return creacionEnvio;
	}

	public void setCreacionEnvio(Boolean creacionEnvio) {
		this.creacionEnvio = creacionEnvio;
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

