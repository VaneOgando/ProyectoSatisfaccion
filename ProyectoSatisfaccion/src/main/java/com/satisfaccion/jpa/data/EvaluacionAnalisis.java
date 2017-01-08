package com.satisfaccion.jpa.data;

import java.util.Date;


public class EvaluacionAnalisis {

	private int idenvio;
	private String usuarioEvaluado;
	private String encuesta;
	private Date fechaEnvio;

	//Totales
	private int totalRespuesta;
	private int totalEncuesta;



	/*GET AND SET*/

	public int getIdenvio() {
		return idenvio;
	}

	public void setIdenvio(int idenvio) {
		this.idenvio = idenvio;
	}

	public String getUsuarioEvaluado() {
		return usuarioEvaluado;
	}

	public void setUsuarioEvaluado(String usuarioEvaluado) {
		this.usuarioEvaluado = usuarioEvaluado;
	}

	public String getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(String encuesta) {
		this.encuesta = encuesta;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public int getTotalRespuesta() {
		return totalRespuesta;
	}

	public void setTotalRespuesta(int totalRespuesta) {
		this.totalRespuesta = totalRespuesta;
	}

	public int getTotalEncuesta() {
		return totalEncuesta;
	}

	public void setTotalEncuesta(int totalEncuesta) {
		this.totalEncuesta = totalEncuesta;
	}
}
