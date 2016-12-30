package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.*;
import com.satisfaccion.spring.service.BuzonSugerenciaServicio;
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
public class BuzonSugerenciaBean {

	/*ATRIBUTOS*/
	@ManagedProperty("#{buzonSugerenciaServicio}")
	private BuzonSugerenciaServicio buzonSugerenciaServicio;

	@ManagedProperty(value = "#{mensajesComun}")
	private MensajesComun mensajesComun;

	private List<ObservacionAnalisis> prueba = new ArrayList<ObservacionAnalisis>();


	private List<PreguntaAnalisis> preguntasBuzon = new ArrayList<PreguntaAnalisis>();
	private List<ObservacionAnalisis> itemsBuscados;

	/*Manejo de filtro*/
	private String estado = "A";
	private EncuestaEntity encuestaSelect = new EncuestaEntity();
	private ProyectoEntity proyectoSelect = new ProyectoEntity();

	/*Fecha*/
	private Date fechaInicio;
	private Date fechaFin;
	private Date hoy = new Date();

	/*Listas de Filtros*/
	private List<EncuestaEntity> encuestas;
	private List<ProyectoEntity> proyectos;


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

		cargarEncuestaFiltro();
		cargarProyectoFiltro();

	}

	public void cargarEncuestaFiltro(){

		//Solo se observaran preguntasBuzon de encuestas
		encuestas = buzonSugerenciaServicio.buscarEncuestas();
	}

	public void cargarProyectoFiltro(){

		if( encuestaSelect.getId() != 0){
			List<EncuestaEntity> encuestas = new ArrayList<EncuestaEntity>();
			encuestas.add(encuestaSelect); //Query busca en lista
			proyectos = buzonSugerenciaServicio.buscarProyectos(encuestas);
		}else{
			proyectos = buzonSugerenciaServicio.buscarProyectos(encuestas);
		}

	}

	public void filtrarPreguntas() {

		Boolean fechaValida = fechasValidas();

		if(fechaValida){
			inicialiazarItems();

			/*Preguntas respondidas dado el filtro*/
			List<PreguntaEntity> preguntas = buzonSugerenciaServicio.buscarPreguntasObservacion(estado, encuestaSelect, proyectoSelect, fechaInicio, fechaFin);

			for(PreguntaEntity pregunta : preguntas){
				PreguntaAnalisis preguntaAnalisis = new PreguntaAnalisis();

				preguntaAnalisis.setIdpregunta(pregunta.getId());
				preguntaAnalisis.setTitulo(pregunta.getTitulo());
				preguntaAnalisis.setTipoPregunta(pregunta.getTipoPregunta());

				List<RespuestaEncuestaVista> observaciones = buzonSugerenciaServicio.buscarObservaciones(pregunta, estado, encuestaSelect, proyectoSelect, fechaInicio, fechaFin);
				List<ObservacionAnalisis> listaObservaciones = new ArrayList<ObservacionAnalisis>();

				for(RespuestaEncuestaVista observacion : observaciones){

					ObservacionAnalisis observacionAnalisis = new ObservacionAnalisis();

					observacionAnalisis.setObservacion(observacion.getObservacion());
					observacionAnalisis.setEncuesta(observacion.getEncuesta());
					observacionAnalisis.setProyecto(observacion.getProyecto());
					observacionAnalisis.setFecha(observacion.getFechaEnvio());

					listaObservaciones.add(observacionAnalisis);
				}

				prueba = listaObservaciones;
				preguntaAnalisis.setObservaciones(listaObservaciones);
				preguntasBuzon.add(preguntaAnalisis);
			}
		}else{
			mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_FECHA_INVALIDA);
		}

	}

	public void inicialiazarItems() {

		preguntasBuzon = new ArrayList<PreguntaAnalisis>();
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

	public BuzonSugerenciaServicio getBuzonSugerenciaServicio() {
		return buzonSugerenciaServicio;
	}

	public void setBuzonSugerenciaServicio(BuzonSugerenciaServicio buzonSugerenciaServicio) {
		this.buzonSugerenciaServicio = buzonSugerenciaServicio;
	}

	public MensajesComun getMensajesComun() {
		return mensajesComun;
	}

	public void setMensajesComun(MensajesComun mensajesComun) {
		this.mensajesComun = mensajesComun;
	}

	public List<PreguntaAnalisis> getPreguntasBuzon() {
		return preguntasBuzon;
	}

	public void setPreguntasBuzon(List<PreguntaAnalisis> preguntasBuzon) {
		this.preguntasBuzon = preguntasBuzon;
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

	public List<ObservacionAnalisis> getItemsBuscados() {
		return itemsBuscados;
	}

	public void setItemsBuscados(List<ObservacionAnalisis> itemsBuscados) {
		this.itemsBuscados = itemsBuscados;
	}

	public List<ObservacionAnalisis> getPrueba() {
		return prueba;
	}

	public void setPrueba(List<ObservacionAnalisis> prueba) {
		this.prueba = prueba;
	}
}