package com.satisfaccion.primefaces.beans;

import com.satisfaccion.jpa.data.*;
import com.satisfaccion.spring.service.ConsultarPreguntaServicio;
import com.satisfaccion.util.comun.Constantes;
import com.satisfaccion.util.comun.MensajesComun;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;

import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ConsultarPreguntaBean {

	/*ATRIBUTOS*/
	@ManagedProperty("#{consultarPreguntaServicio}")
	private ConsultarPreguntaServicio consultarPreguntaServicio;

	private List<PreguntaEntity> items = new ArrayList<PreguntaEntity>();
	private List<PreguntaEntity> itemsBuscados;
	private PreguntaEntity itemSeleccionado;

	private String estado;
	private String tipoPregunta;
	private String tipoEncuesta;

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






/* GET & SET */

	public ConsultarPreguntaServicio getConsultarPreguntaServicio() {
		return consultarPreguntaServicio;
	}

	public void setConsultarPreguntaServicio(ConsultarPreguntaServicio consultarPreguntaServicio) {
		this.consultarPreguntaServicio = consultarPreguntaServicio;
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

	public ArrayList<String> getTiposPregunta() {
		return tiposPregunta;
	}

	public void setTiposPregunta(ArrayList<String> tiposPregunta) {
		this.tiposPregunta = tiposPregunta;
	}
}