package com.satisfaccion.jpa.data;

import javax.persistence.*;


@Entity
@Table(name="RESPUESTA_ENCUESTA")

@NamedQueries(value={

		@NamedQuery(name = "HQL_RESPUESTA_ENCUESTA_VISTA",
				query = "SELECT rv " +
						"FROM RespuestaEncuestaVista rv")

})

public class RespuestaEncuestaVista {

	@Id
	@Column(name = "IDRESPUESTA")
	private String idrespuesta;
	@Column(name = "OBSERVACION")
	private String observacion;
	@Column(name = "VALORACION")
	private String valoracion;
	@Column(name = "USUARIOEVALUADO")
	private String usuarioEvaluado;
	@Column(name = "IDPREGUNTA")
	private String idpregunta;
	@Column(name = "TITULO")
	private String titulo;
	@Column(name = "TIPOPREGUNTA")
	private String tipoPregunta;
	@Column(name = "TIPOENCUESTAPREG")
	private String tipoEncuestaPreg;
	@Column(name = "ESTADOPREG")
	private String estadoPreg;
	@Column(name = "ESCALAVALORACION")
	private String escalaValoracion;
	@Column(name = "IDOPCION")
	private String idopcion;
	@Column(name = "OPCION")
	private String opcion;
	@Column(name = "VALOR")
	private String valor;
	@Column(name = "FECHAENVIO")
	private String fechaEnvio;
	@Column(name = "IDENVIO")
	private String idEnvio;
	@Column(name = "USUARIOCREADOR")
	private String usuarioCreador;
	@Column(name = "IDPROYECTO")
	private String idproyecto;
	@Column(name = "PROYECTO")
	private String proyecto;
	@Column(name = "IDENCUESTA")
	private String idEncuesta;
	@Column(name = "ENCUESTA")
	private String encuesta;
	@Column(name = "TIPOENCUESTAENC")
	private String tipoEncuestaEnc;
	@Column(name = "ESTADOENC")
	private String estadoEnc;

	/*private int totalOpcion;
	private Float totalRanking;
*/

	/*GET AND SET*/

	public String getIdrespuesta() {
		return idrespuesta;
	}

	public void setIdrespuesta(String idrespuesta) {
		this.idrespuesta = idrespuesta;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getValoracion() {
		return valoracion;
	}

	public void setValoracion(String valoracion) {
		this.valoracion = valoracion;
	}

	public String getUsuarioEvaluado() {
		return usuarioEvaluado;
	}

	public void setUsuarioEvaluado(String usuarioEvaluado) {
		this.usuarioEvaluado = usuarioEvaluado;
	}

	public String getIdpregunta() {
		return idpregunta;
	}

	public void setIdpregunta(String idpregunta) {
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

	public String getTipoEncuestaPreg() {
		return tipoEncuestaPreg;
	}

	public void setTipoEncuestaPreg(String tipoEncuestaPreg) {
		this.tipoEncuestaPreg = tipoEncuestaPreg;
	}

	public String getEstadoPreg() {
		return estadoPreg;
	}

	public void setEstadoPreg(String estadoPreg) {
		this.estadoPreg = estadoPreg;
	}

	public String getEscalaValoracion() {
		return escalaValoracion;
	}

	public void setEscalaValoracion(String escalaValoracion) {
		this.escalaValoracion = escalaValoracion;
	}

	public String getIdopcion() {
		return idopcion;
	}

	public void setIdopcion(String idopcion) {
		this.idopcion = idopcion;
	}

	public String getOpcion() {
		return opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public String getIdEnvio() {
		return idEnvio;
	}

	public void setIdEnvio(String idEnvio) {
		this.idEnvio = idEnvio;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public String getIdproyecto() {
		return idproyecto;
	}

	public void setIdproyecto(String idproyecto) {
		this.idproyecto = idproyecto;
	}

	public String getProyecto() {
		return proyecto;
	}

	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}

	public String getIdEncuesta() {
		return idEncuesta;
	}

	public void setIdEncuesta(String idEncuesta) {
		this.idEncuesta = idEncuesta;
	}

	public String getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(String encuesta) {
		this.encuesta = encuesta;
	}

	public String getTipoEncuestaEnc() {
		return tipoEncuestaEnc;
	}

	public void setTipoEncuestaEnc(String tipoEncuestaEnc) {
		this.tipoEncuestaEnc = tipoEncuestaEnc;
	}

	public String getEstadoEnc() {
		return estadoEnc;
	}

	public void setEstadoEnc(String estadoEnc) {
		this.estadoEnc = estadoEnc;
	}

	/*	public int getTotalOpcion() {
		return totalOpcion;
	}

	public void setTotalOpcion(int totalOpcion) {
		this.totalOpcion = totalOpcion;
	}

	public Float getTotalRanking() {
		return totalRanking;
	}

	public void setTotalRanking(Float totalRanking) {
		this.totalRanking = totalRanking;
	}*/
}
