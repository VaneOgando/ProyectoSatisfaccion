package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.*;
import com.satisfaccion.spring.service.AnalisisEncuestaServicio;
import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.util.comun.MensajesComun;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.*;

@ManagedBean
@ViewScoped
public class AnalisisEncuestaBean {

	/*ATRIBUTOS*/
	@ManagedProperty("#{analisisEncuestaServicio}")
	private AnalisisEncuestaServicio analisisEncuestaServicio;

	@ManagedProperty(value = "#{mensajesComun}")
	private MensajesComun mensajesComun;


	private List<RespuestaEntity> respuestas = new ArrayList<RespuestaEntity>();

	/*Manejo de filtro*/
	private String tipoPregunta = "N";
	private String estado = "A";
	private EncuestaEntity encuestaSelect = new EncuestaEntity();
	private ProyectoEntity proyectoSelect = new ProyectoEntity();
	private UsuarioEntity usuarioSelect = new UsuarioEntity();
	private String usuario;

	/*Fecha*/
	private Date fechaInicio;
	private Date fechaFin;
	private Date hoy = new Date();

	/*Listas de Filtros*/
	private List<EncuestaEntity> encuestas;
	private List<ProyectoEntity> proyectos;
	private List<UsuarioEntity> usuarios;

	/*Para manejo de estadisticas*/
	private int totalRelativo;


/*METODOS*/

	@PostConstruct
	private void init() {

		recargarFiltos();
		filtrarPreguntas();

	}

	public void recargarFiltos(){

		encuestaSelect = new EncuestaEntity();
		proyectoSelect = new ProyectoEntity();
		fechaInicio = fechaFin = null;
		usuario = null;

		cargarEncuestaFiltro();
		cargarProyectoFiltro();
		cargarUsuarioFiltro();

	}

	public void cargarEncuestaFiltro(){

		encuestas = analisisEncuestaServicio.buscarEncuestas(tipoPregunta);
	}

	public void cargarProyectoFiltro(){

		if( encuestaSelect.getId() != 0){
			List<EncuestaEntity> encuestas = new ArrayList<EncuestaEntity>();
			encuestas.add(encuestaSelect);
			proyectos = analisisEncuestaServicio.buscarProyectos(encuestas);
		}else{
			proyectos = analisisEncuestaServicio.buscarProyectos(encuestas);
		}

	}

	public void cargarUsuarioFiltro(){

		if (tipoPregunta.equals("E")){
			//Fata conexion a  LDAP
		}

	}


	public void filtrarPreguntas() {

		Boolean fechaValida = fechasValidas();

		if(fechaValida){
			inicialiazarItems();

			List<Object[]> respuestas = new ArrayList<Object[]>();
			respuestas = analisisEncuestaServicio.filtrarEncuestas(estado, tipoPregunta, encuestaSelect, proyectoSelect, fechaInicio, fechaFin, usuario);

		}else{
			mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_FECHA_INVALIDA);
		}

	}

	public void inicialiazarItems() {

		respuestas = new ArrayList<RespuestaEntity>();
		totalRelativo = 0;
	}

	public Boolean fechasValidas(){

		Boolean fechaValida = true;

		try {

			if( (fechaInicio != null && fechaFin ==  null) || (fechaInicio == null && fechaFin != null) ){
                fechaValida = false;

            }else if( fechaInicio != null && fechaFin != null){

                if(fechaInicio.after(fechaFin)){
                    fechaValida = false;
                }
            }

		} catch (Exception e) {
			fechaValida = false;
		}

		return fechaValida;
	}




/* GET & SET */

	public AnalisisEncuestaServicio getAnalisisEncuestaServicio() {
		return analisisEncuestaServicio;
	}

	public void setAnalisisEncuestaServicio(AnalisisEncuestaServicio analisisEncuestaServicio) {
		this.analisisEncuestaServicio = analisisEncuestaServicio;
	}

	public MensajesComun getMensajesComun() {
		return mensajesComun;
	}

	public void setMensajesComun(MensajesComun mensajesComun) {
		this.mensajesComun = mensajesComun;
	}

	public List<RespuestaEntity> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<RespuestaEntity> respuestas) {
		this.respuestas = respuestas;
	}


	public String getTipoPregunta() {
		return tipoPregunta;
	}

	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public UsuarioEntity getUsuarioSelect() {
		return usuarioSelect;
	}

	public void setUsuarioSelect(UsuarioEntity usuarioSelect) {
		this.usuarioSelect = usuarioSelect;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getHoy() {
		return hoy;
	}

	public void setHoy(Date hoy) {
		this.hoy = hoy;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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

	public List<UsuarioEntity> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioEntity> usuarios) {
		this.usuarios = usuarios;
	}

	public int getTotalRelativo() {
		return totalRelativo;
	}

	public void setTotalRelativo(int totalRelativo) {
		this.totalRelativo = totalRelativo;
	}

}