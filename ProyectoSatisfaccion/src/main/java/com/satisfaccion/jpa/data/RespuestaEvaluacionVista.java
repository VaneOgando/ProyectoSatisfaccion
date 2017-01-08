package com.satisfaccion.jpa.data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="RESPUESTA_EVALUACION")

@NamedQueries(value={

		@NamedQuery(name = "HQL_RESPUESTA_EVALUACION_VISTA_SIN_FECHA",
				query = "Select rv from RespuestaEvaluacionVista rv " +
						"where rv.estado = :estado " +
						"and (:encuesta is null or :encuesta = '0' or rv.idencuesta = :encuesta) and (:usuario is null or :usuario = '0' or rv.usuarioEvaluado = :usuario) "),

		@NamedQuery(name = "HQL_RESPUESTA_EVALUACION_VISTA_CON_FECHA",
				query = "Select rv from RespuestaEvaluacionVista rv " +
						"where rv.estado = :estado " +
						"and (:encuesta is null or :encuesta = '0' or rv.idencuesta = :encuesta) and (:usuario is null or :usuario = '0' or rv.usuarioEvaluado = :usuario) " +
						"and rv.fechaEnvio between :fechaInicio and :fechaFin")

})

public class RespuestaEvaluacionVista {

	@Id
	@Column(name = "IDRESPUESTA")
	private Integer idrespuesta;
	@Column(name = "IDENVIO")
	private Integer idenvio;
	@Column(name = "USUARIOEVALUADO")
	private String usuarioEvaluado;
	@Column(name = "FECHAENVIO")
	private Date fechaEnvio;
	@Column(name = "IDENCUESTA")
	private Integer idencuesta;
	@Column(name = "ENCUESTA")
	private String encuesta;
	@Column(name = "ESTADO")
	private String estado;
	@Column(name = "FKPREGUNTA")
	private Integer idpregunta;
	@Column(name = "FKOPCION")
	private Integer idopcion;

	@Column(name = "VALORR")
	private Integer valorResp;
	@Column(name = "VALORACIONR")
	private Integer valoracionResp;
	@Column(name = "VALORTOTALP")
	private Integer valorTotal;
	@Column(name = "VALORACIONTOTALP")
	private Integer valoracionTotal;



	/*GET AND SET*/

	public Integer getIdrespuesta() {
		return idrespuesta;
	}

	public void setIdrespuesta(Integer idrespuesta) {
		this.idrespuesta = idrespuesta;
	}

	public Integer getIdenvio() {
		return idenvio;
	}

	public void setIdenvio(Integer idenvio) {
		this.idenvio = idenvio;
	}

	public String getUsuarioEvaluado() {
		return usuarioEvaluado;
	}

	public void setUsuarioEvaluado(String usuarioEvaluado) {
		this.usuarioEvaluado = usuarioEvaluado;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Integer getIdencuesta() {
		return idencuesta;
	}

	public void setIdencuesta(Integer idencuesta) {
		this.idencuesta = idencuesta;
	}

	public String getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(String encuesta) {
		this.encuesta = encuesta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getIdpregunta() {
		return idpregunta;
	}

	public void setIdpregunta(Integer idpregunta) {
		this.idpregunta = idpregunta;
	}

	public Integer getIdopcion() {
		return idopcion;
	}

	public void setIdopcion(Integer idopcion) {
		this.idopcion = idopcion;
	}

	public Integer getValorResp() {
		return valorResp;
	}

	public void setValorResp(Integer valorResp) {
		this.valorResp = valorResp;
	}

	public Integer getValoracionResp() {
		return valoracionResp;
	}

	public void setValoracionResp(Integer valoracionResp) {
		this.valoracionResp = valoracionResp;
	}

	public Integer getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Integer valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Integer getValoracionTotal() {
		return valoracionTotal;
	}

	public void setValoracionTotal(Integer valoracionTotal) {
		this.valoracionTotal = valoracionTotal;
	}
}
