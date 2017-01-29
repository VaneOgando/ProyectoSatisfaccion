package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.*;
import com.satisfaccion.spring.service.ConsultarPreguntaServicio;
import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.util.comun.MensajesComun;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ConsultarPreguntaBean {

	/*ATRIBUTOS*/
	@ManagedProperty("#{consultarPreguntaServicio}")
	private ConsultarPreguntaServicio consultarPreguntaServicio;

	@ManagedProperty(value="#{mensajesComun}")
	private MensajesComun mensajesComun;

	private FacesContext context = FacesContext.getCurrentInstance();

	private List<PreguntaEntity> items = new ArrayList<PreguntaEntity>();
	private List<PreguntaEntity> itemsBuscados;
	private PreguntaEntity itemSeleccionado;

	private String estado;
	private String tipoPregunta;
	private String tipoEncuesta;

	private int respuestas = 0;
	private boolean eliminar = false;

	private ArrayList<String> tiposPregunta = Constantes.tipoPreguntas;


/*METODOS*/

	@PostConstruct
	private void init() {

		filtrarPreguntas();

	}

	public void filtrarPreguntas() {

		inicialiazarItems();

		items = consultarPreguntaServicio.filtrarPreguntas(estado, tipoPregunta, tipoEncuesta);

	}

	public void inicialiazarItems(){

		items = null;
		itemsBuscados = null;
		itemSeleccionado = null;

		RequestContext.getCurrentInstance().execute("PF('itemTabla').clearFilters()");
	}


	public String detallePregunta() {

		if (itemSeleccionado != null) {

			return "detallePregunta.xhtml?faces-redirect=true&id=" + itemSeleccionado.getId();

		}

		return "";
	}

	public String modificarPregunta() {

		if (itemSeleccionado != null) {

			respuestas = consultarPreguntaServicio.consultarNumRespuestas(itemSeleccionado.getId());

			if (respuestas == 0){
				return "modificarPregunta.xhtml?faces-redirect=true&id=" + itemSeleccionado.getId();
			}else{

				mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_PREGUNTA_RESPONDIDA);
				return "";
			}

		}

		return "";
	}

	public void eliminarPregunta(){

		if (itemSeleccionado.getEstado().equals("A")){

			respuestas = consultarPreguntaServicio.consultarNumRespuestas(itemSeleccionado.getId());

			if (respuestas > 0){
				//Desactivar pregunta

				eliminar = consultarPreguntaServicio.eliminarPregunta(false, itemSeleccionado);

				if (eliminar){
					mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_EXITO, Constantes.EX_ELIMINAR_DESACTIVAR);
				}else{
					mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_ELIMINAR_DESACTIVAR);
				}

			}else{
				//Eliminar pregunta

				eliminar = consultarPreguntaServicio.eliminarPregunta(true, itemSeleccionado);

				if (eliminar){
					mensajesComun.guardarMensaje(true, Constantes.MENSAJE_TIPO_EXITO, Constantes.EX_ELIMINAR_DEFINITIVO);
					items.remove(itemSeleccionado);
				}else{
					mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_ELIMINAR_DEFINITIVO);
				}

			}

		}else{
			mensajesComun.guardarMensaje(false, Constantes.MENSAJE_TIPO_ERROR, Constantes.ERR_PREGUNTA_INACTIVA);
		}

	}

	public String bt_crearPregunta(){

		return "Crear";
	}

	public String analizarPregunta() {

		if (itemSeleccionado != null) {
			return "analisisEncuesta.xhtml?faces-redirect=true&id=" + itemSeleccionado.getId() + "&e=f";
		}

		return "";
	}




/* GET & SET */

	public ConsultarPreguntaServicio getConsultarPreguntaServicio() {
		return consultarPreguntaServicio;
	}

	public void setConsultarPreguntaServicio(ConsultarPreguntaServicio consultarPreguntaServicio) {
		this.consultarPreguntaServicio = consultarPreguntaServicio;
	}

	public MensajesComun getMensajesComun() {
		return mensajesComun;
	}

	public void setMensajesComun(MensajesComun mensajesComun) {
		this.mensajesComun = mensajesComun;
	}

	public FacesContext getContext() {
		return context;
	}

	public void setContext(FacesContext context) {
		this.context = context;
	}

	public List<PreguntaEntity> getItems() {
		return items;
	}

	public void setItems(List<PreguntaEntity> items) {
		this.items = items;
	}

	public List<PreguntaEntity> getItemsBuscados() {
		return itemsBuscados;
	}

	public void setItemsBuscados(List<PreguntaEntity> itemsBuscados) {
		this.itemsBuscados = itemsBuscados;
	}

	public PreguntaEntity getItemSeleccionado() {
		return itemSeleccionado;
	}

	public void setItemSeleccionado(PreguntaEntity itemSeleccionado) {
		this.itemSeleccionado = itemSeleccionado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoPregunta() {
		return tipoPregunta;
	}

	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}

	public String getTipoEncuesta() {
		return tipoEncuesta;
	}

	public void setTipoEncuesta(String tipoEncuesta) {
		this.tipoEncuesta = tipoEncuesta;
	}

	public int getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(int respuestas) {
		this.respuestas = respuestas;
	}

	public boolean isEliminar() {
		return eliminar;
	}

	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}

	public ArrayList<String> getTiposPregunta() {
		return tiposPregunta;
	}

	public void setTiposPregunta(ArrayList<String> tiposPregunta) {
		this.tiposPregunta = tiposPregunta;
	}
}