package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.*;
import com.satisfaccion.spring.service.CrearRecursoServicio;

import com.satisfaccion.util.comun.*;
import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class CrearRecursoBean {

	/*ATRIBUTOS*/
	@ManagedProperty("#{crearRecursoServicio}")
	private CrearRecursoServicio crearRecursoServicio;

	private EncuestaEntity equipo;
	private PreguntaEntity accesorio;

	private String opcionRecurso = "E";
	private String observacion;
	private Boolean creacion = false;

	private Date fechaActual = new Date();

	private List<ProyectoEntity> marcas;
	private ProyectoEntity marca;

	private List<RespuestaEntity> modelos;
	private RespuestaEntity modelo;

	private List<OpcionEntity> categorias;
	private OpcionEntity categoria;

	private EncPreEntity estado;
	private EnvioEntity historial;
/*

	*/
/*METODOS*//*

	@PostConstruct
	public void init(){

		cambiarRecursoOpcion();
	}

	public void cambiarRecursoOpcion() {

		inicializarListas();
		marcas = cargarMarcas();

		if(opcionRecurso.equals("A")){
			categorias = cargarCategorias();
		}

	}

	public List<ProyectoEntity> cargarMarcas() {

		List<ProyectoEntity> results = crearRecursoServicio.cargarMarcas();

		return results;
	}

	public void cargarModelos() {

		if(!marca.getNombre().equals("")){

			//Validar existencia de marca
			if (crearRecursoServicio.obtenerMarcaPorNombre(marca.getNombre()) != null){

				setMarca(crearRecursoServicio.obtenerMarcaPorNombre(marca.getNombre()));
				modelos = crearRecursoServicio.cargarModelos(getMarca());

			}else{
				marca.setId(0);
				modelos = new ArrayList<RespuestaEntity>();
			}

		}else{
			modelos = new ArrayList<RespuestaEntity>();
		}

	}

	public List<OpcionEntity> cargarCategorias() {

		List<OpcionEntity> results = crearRecursoServicio.cargarCategorias("accesorio");

		return results;
	}

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

	public void crearHistorial(){

		historial.setFechaGestion(fechaActual);

		//Obtener usuario conectado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		historial.setResponsableSoporte( auth.getName() );
		historial.setCategoria(crearRecursoServicio.obtenerCategoriaHistorial(Constantes.D_CAT_HISTORIAL_CREACION));

		if(!observacion.equals("")){
			historial.setDescripcion(observacion);
		}else{

			if(opcionRecurso.equals("E")) {
				historial.setDescripcion(Constantes.D_DESC_HISTORIAL_CREACION_EQ + equipo.getNombre());
			}else{
				historial.setDescripcion(Constantes.D_DESC_HISTORIAL_CREACION_ACC + accesorio.getNombre());
			}
		}

	}


	public void inicializarListas(){

		equipo 	   = new EncuestaEntity();
		accesorio  = new PreguntaEntity();

		marcas     = new ArrayList<ProyectoEntity>();
		modelos    = new ArrayList<RespuestaEntity>();
		categorias = new ArrayList<OpcionEntity>();

		marca 	  = new ProyectoEntity();
		modelo 	  = new RespuestaEntity();
		categoria = new OpcionEntity();

		estado 	   = new EncPreEntity();
		historial  = new EnvioEntity();
	}

	public String bt_cancelar(){

		return "Cancelar";
	}


	*/
/*GET & SET*//*

	public CrearRecursoServicio getCrearRecursoServicio() {
		return crearRecursoServicio;
	}

	public void setCrearRecursoServicio(CrearRecursoServicio crearRecursoServicio) {
		this.crearRecursoServicio = crearRecursoServicio;
	}

	public EncuestaEntity getEquipo() {
		return equipo;
	}

	public void setEquipo(EncuestaEntity equipo) {
		this.equipo = equipo;
	}

	public PreguntaEntity getAccesorio() {
		return accesorio;
	}

	public void setAccesorio(PreguntaEntity accesorio) {
		this.accesorio = accesorio;
	}

	public String getOpcionRecurso() {
		return opcionRecurso;
	}

	public void setOpcionRecurso(String opcionRecurso) {
		this.opcionRecurso = opcionRecurso;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Boolean getCreacion() {
		return creacion;
	}

	public void setCreacion(Boolean creacion) {
		this.creacion = creacion;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public List<ProyectoEntity> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<ProyectoEntity> marcas) {
		this.marcas = marcas;
	}

	public ProyectoEntity getMarca() {
		return marca;
	}

	public void setMarca(ProyectoEntity marca) {
		this.marca = marca;
	}

	public List<RespuestaEntity> getModelos() {
		return modelos;
	}

	public void setModelos(List<RespuestaEntity> modelos) {
		this.modelos = modelos;
	}

	public RespuestaEntity getModelo() {
		return modelo;
	}

	public void setModelo(RespuestaEntity modelo) {
		this.modelo = modelo;
	}

	public OpcionEntity getCategoria() {
		return categoria;
	}

	public void setCategoria(OpcionEntity categoria) {
		this.categoria = categoria;
	}

	public List<OpcionEntity> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<OpcionEntity> categorias) {
		this.categorias = categorias;
	}

	public EncPreEntity getEstado() {
		return estado;
	}

	public void setEstado(EncPreEntity estado) {
		this.estado = estado;
	}

	public EnvioEntity getHistorial() {
		return historial;
	}

	public void setHistorial(EnvioEntity historial) {
		this.historial = historial;
	}
*/

}

