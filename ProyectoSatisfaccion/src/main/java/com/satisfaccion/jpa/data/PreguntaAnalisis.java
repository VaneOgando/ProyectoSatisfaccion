package com.satisfaccion.jpa.data;

import java.util.ArrayList;
import java.util.List;

public class PreguntaAnalisis {

	private int idpregunta;
	private String titulo;
	private String tipoPregunta;

	//Simple
	private List<OpcionAnalisis> opciones = new ArrayList<OpcionAnalisis>();
	private int totalAbsoluto = 0;

	//Ranking
	private int escalavaloracion;
	private int totalRanking = 0;

	//Abierta
	private List<ObservacionAnalisis> observaciones;



	/*GET AND SET*/

	public int getIdpregunta() {
		return idpregunta;
	}

	public void setIdpregunta(int idpregunta) {
		this.idpregunta = idpregunta;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTipoPregunta() {
		return tipoPregunta;
	}

	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}

	public List<OpcionAnalisis> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<OpcionAnalisis> opciones) {
		this.opciones = opciones;
	}

	public int getTotalAbsoluto() {
		return totalAbsoluto;
	}

	public void setTotalAbsoluto(int totalAbsoluto) {
		this.totalAbsoluto = totalAbsoluto;
	}

	public int getEscalavaloracion() {
		return escalavaloracion;
	}

	public void setEscalavaloracion(int escalavaloracion) {
		this.escalavaloracion = escalavaloracion;
	}

	public int getTotalRanking() {
		return totalRanking;
	}

	public void setTotalRanking(int totalRanking) {
		this.totalRanking = totalRanking;
	}

	public List<ObservacionAnalisis> getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(List<ObservacionAnalisis> observaciones) {
		this.observaciones = observaciones;
	}
}
