package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.*;
import com.satisfaccion.spring.service.AnalisisEncuestaServicio;
import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.util.comun.MensajesComun;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.*;

@ManagedBean
@ViewScoped
public class AnalisisEncuestaBean {

	/*ATRIBUTOS*/
	@ManagedProperty("#{analisisEncuestaServicio}")
	private AnalisisEncuestaServicio analisisEncuestaServicio;

	@ManagedProperty(value = "#{mensajesComun}")
	private MensajesComun mensajesComun;


	private List<PreguntaAnalisis> preguntas = new ArrayList<PreguntaAnalisis>();
	private List<EvaluacionAnalisis> evaluaciones = new ArrayList<EvaluacionAnalisis>();
	private List<EvaluacionAnalisis> itemsBuscados = null;


	/*Manejo de filtro*/
	private String tipoPregunta = "N";
	private String estado = "A";
	private EncuestaEntity encuestaSelect = new EncuestaEntity();
	private ProyectoEntity proyectoSelect = new ProyectoEntity();
	private UsuarioEntity usuarioSelect = new UsuarioEntity();
	private PreguntaEntity preguntaSelect = new PreguntaEntity();

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

		//Obtener parametro por redireccion
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

		if(id != null){

			preguntaSelect = analisisEncuestaServicio.buscarPreguntaPorId(Integer.parseInt(id));
			tipoPregunta = preguntaSelect.getTipoEncuesta();
		}

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

			if(tipoPregunta.equals("N")){
				preguntasEncuesta();
			}else{
				preguntasEvaluacion();
			}


		}else{
			mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_FECHA_INVALIDA);
		}

	}

	private void preguntasEncuesta() {

    /*Preguntas respondidas dado el filtro*/
		List<PreguntaEntity> preguntas = analisisEncuestaServicio.buscarPreguntasAnalisis(estado, tipoPregunta, encuestaSelect, proyectoSelect, fechaInicio, fechaFin, preguntaSelect);

		for(PreguntaEntity pregunta : preguntas){
            PreguntaAnalisis preguntaAnalisis = new PreguntaAnalisis();

            preguntaAnalisis.setIdpregunta(pregunta.getId());
            preguntaAnalisis.setTitulo(pregunta.getTitulo());
            preguntaAnalisis.setTipoPregunta(pregunta.getTipoPregunta());

            if("simple".equals(pregunta.getTipoPregunta())){  //Posee opciones

                List<OpcionAnalisis> listaOpciones = new ArrayList<OpcionAnalisis>();
                int totalAbsoluto = 0;

                for (OpcionEntity opcion : pregunta.getOpciones()){
                    OpcionAnalisis opcionAnalisis = new OpcionAnalisis();

                    opcionAnalisis.setIdopcion(opcion.getId());
                    opcionAnalisis.setTitulo(opcion.getTitulo());

                    int totalOpcion = analisisEncuestaServicio.buscarTotalOpcion(pregunta,opcion, estado, tipoPregunta, encuestaSelect, proyectoSelect, fechaInicio, fechaFin);
                    totalAbsoluto += totalOpcion;

                    opcionAnalisis.setTotalOpcion(totalOpcion);

                    listaOpciones.add(opcionAnalisis);
                }

                preguntaAnalisis.setOpciones(listaOpciones);
                preguntaAnalisis.setTotalAbsoluto(totalAbsoluto);

            }else{  //Tipo ranking

                preguntaAnalisis.setEscalavaloracion(Math.round(pregunta.getEscalaValoracion()));

                Float totalRanking = analisisEncuestaServicio.buscarTotalRanking(pregunta, estado, tipoPregunta, encuestaSelect, proyectoSelect, fechaInicio, fechaFin);
                preguntaAnalisis.setTotalRanking(Math.round(totalRanking));
            }

            this.preguntas.add(preguntaAnalisis);
        }

		if(preguntas.size() < 1){
            mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.NO_REGISTROS);
        }
	}

	private void preguntasEvaluacion() {

		/*Evaluaciones registradas dado el filtro*/
		List<RespuestaEvaluacionVista> evaluacionVista = analisisEncuestaServicio.buscarEvaluaciones(estado, encuestaSelect, usuario, fechaInicio, fechaFin, preguntaSelect);

		for (RespuestaEvaluacionVista vista : evaluacionVista){

			EvaluacionAnalisis evaluacion = null;

			for(EvaluacionAnalisis evaluacionAlmacenada : evaluaciones){

				//Mismo envio y usuario = misma respuesta
				if(evaluacionAlmacenada.getIdenvio() == vista.getIdenvio() && evaluacionAlmacenada.getUsuarioEvaluado().equals(vista.getUsuarioEvaluado())){
					evaluacion = evaluacionAlmacenada;
					evaluaciones.remove(evaluacion);
					break;
				}
			}

			//Nueva evaluacion
			if(evaluacion == null){

				evaluacion = new EvaluacionAnalisis();

				evaluacion.setIdenvio(vista.getIdenvio());
				evaluacion.setEncuesta(vista.getEncuesta());
				evaluacion.setFechaEnvio(vista.getFechaEnvio());
				evaluacion.setUsuarioEvaluado(vista.getUsuarioEvaluado());

				//Respuesta a opcion
				if(vista.getValorResp() != null) {
					evaluacion.setTotalRespuesta( vista.getValorResp() );
					evaluacion.setTotalEncuesta( vista.getValorTotal());

				}else{ //respuesta a ranking
					evaluacion.setTotalRespuesta(vista.getValoracionResp());
					evaluacion.setTotalEncuesta( vista.getValoracionTotal());

				}

			}else{ //Sumar las puntuaciones

				//Respuesta a opcion
				if(vista.getValorResp() != null) {
					evaluacion.setTotalRespuesta( evaluacion.getTotalRespuesta() + vista.getValorResp() );
					evaluacion.setTotalEncuesta( evaluacion.getTotalEncuesta() + vista.getValorTotal());

				}else{ //respuesta a ranking
					evaluacion.setTotalRespuesta( evaluacion.getTotalRespuesta() + vista.getValoracionResp());
					evaluacion.setTotalEncuesta( evaluacion.getTotalEncuesta() + vista.getValoracionTotal());

				}
			}

			evaluaciones.add(evaluacion);

		}

		//filtrar en nuesta tabla
		//ciclar por los envios iguales y usuarios iguales, sumando los totales de la respuesta y de la encuesta

	}

	public void inicialiazarItems() {

		preguntas = new ArrayList<PreguntaAnalisis>();
		evaluaciones = new ArrayList<EvaluacionAnalisis>();
		itemsBuscados =  null;
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

	public List<PreguntaAnalisis> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<PreguntaAnalisis> preguntas) {
		this.preguntas = preguntas;
	}

	public List<EvaluacionAnalisis> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(List<EvaluacionAnalisis> evaluaciones) {
		this.evaluaciones = evaluaciones;
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

	public List<EvaluacionAnalisis> getItemsBuscados() {
		return itemsBuscados;
	}

	public void setItemsBuscados(List<EvaluacionAnalisis> itemsBuscados) {
		this.itemsBuscados = itemsBuscados;
	}

	public PreguntaEntity getPreguntaSelect() {
		return preguntaSelect;
	}

	public void setPreguntaSelect(PreguntaEntity preguntaSelect) {
		this.preguntaSelect = preguntaSelect;
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