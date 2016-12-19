package com.satisfaccion.jpa.data;

import org.springframework.dao.DataAccessException;

import javax.faces.context.FacesContext;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="RESPUESTA_ENCUESTA")

@NamedQueries(value={

		@NamedQuery(name = "HQL_RESPUESTA_VISTA_TOTAL_OPCION",
				query = "Select count(rv) from RespuestaEncuestaVista rv " +
						"where rv.tipoPregunta in ('simple') and rv.tipoEncuestaPreg = :tipoEncuesta and rv.estadoPreg = :estado " +
						"and (:encuesta is null or :encuesta = '0' or rv.idEncuesta = :encuesta) and (:proyecto is null or :proyecto = '0' or rv.idproyecto = :proyecto) " +
						"and rv.idpregunta = :pregunta and rv.idopcion = :opcion"),

		@NamedQuery(name = "HQL_RESPUESTA_VISTA_TOTAL_RANKING",
				query = "Select AVG(rv.valoracion) from RespuestaEncuestaVista rv " +
						"where rv.tipoPregunta in ('ranking') and rv.tipoEncuestaPreg = :tipoEncuesta and rv.estadoPreg = :estado " +
						"and (:encuesta is null or :encuesta = '0' or rv.idEncuesta = :encuesta) and (:proyecto is null or :proyecto = '0' or rv.idproyecto = :proyecto) " +
						"and rv.idpregunta = :pregunta")

})

public class RespuestaEncuestaVista {

	@Id
	@Column(name = "IDRESPUESTA")
	private int idrespuesta;
	@Column(name = "OBSERVACION")
	private String observacion;
	@Column(name = "VALORACION")
	private Float valoracion;
	@Column(name = "USUARIOEVALUADO")
	private String usuarioEvaluado;
	@Column(name = "IDPREGUNTA")
	private int idpregunta;
	@Column(name = "TITULO")
	private String titulo;
	@Column(name = "TIPOPREGUNTA")
	private String tipoPregunta;
	@Column(name = "TIPOENCUESTAPREG")
	private String tipoEncuestaPreg;
	@Column(name = "ESTADOPREG")
	private String estadoPreg;
	@Column(name = "ESCALAVALORACION")
	private Float escalaValoracion;
	@Column(name = "IDOPCION")
	private int idopcion;
	@Column(name = "OPCION")
	private String opcion;
	@Column(name = "VALOR")
	private Float valor;
	@Column(name = "FECHAENVIO")
	private Date fechaEnvio;
	@Column(name = "IDENVIO")
	private int idEnvio;
	@Column(name = "USUARIOCREADOR")
	private String usuarioCreador;
	@Column(name = "IDPROYECTO")
	private int idproyecto;
	@Column(name = "PROYECTO")
	private String proyecto;
	@Column(name = "IDENCUESTA")
	private int idEncuesta;
	@Column(name = "ENCUESTA")
	private String encuesta;
	@Column(name = "TIPOENCUESTAENC")
	private String tipoEncuestaEnc;
	@Column(name = "ESTADOENC")
	private String estadoEnc;



	/*GET AND SET*/

	public int getIdrespuesta() {
		return idrespuesta;
	}

	public void setIdrespuesta(int idrespuesta) {
		this.idrespuesta = idrespuesta;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Float getValoracion() {
		return valoracion;
	}

	public void setValoracion(Float valoracion) {
		this.valoracion = valoracion;
	}

	public String getUsuarioEvaluado() {
		return usuarioEvaluado;
	}

	public void setUsuarioEvaluado(String usuarioEvaluado) {
		this.usuarioEvaluado = usuarioEvaluado;
	}

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

	public Float getEscalaValoracion() {
		return escalaValoracion;
	}

	public void setEscalaValoracion(Float escalaValoracion) {
		this.escalaValoracion = escalaValoracion;
	}

	public int getIdopcion() {
		return idopcion;
	}

	public void setIdopcion(int idopcion) {
		this.idopcion = idopcion;
	}

	public String getOpcion() {
		return opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public int getIdEnvio() {
		return idEnvio;
	}

	public void setIdEnvio(int idEnvio) {
		this.idEnvio = idEnvio;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public int getIdproyecto() {
		return idproyecto;
	}

	public void setIdproyecto(int idproyecto) {
		this.idproyecto = idproyecto;
	}

	public String getProyecto() {
		return proyecto;
	}

	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}

	public int getIdEncuesta() {
		return idEncuesta;
	}

	public void setIdEncuesta(int idEncuesta) {
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
}
