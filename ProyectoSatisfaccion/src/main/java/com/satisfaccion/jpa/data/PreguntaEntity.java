package com.satisfaccion.jpa.data;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="PREGUNTA")

@NamedQueries(value={

		@NamedQuery(name = "HQL_PREGUNTA",
				query = "SELECT p FROM PreguntaEntity p " +
						"WHERE (:estado is null or :estado = '0' or p.estado = :estado) " +
						"AND (:tipoPregunta is null or :tipoPregunta = '0' or p.tipoPregunta = :tipoPregunta) " +
						"AND (:tipoEncuesta is null or :tipoEncuesta = '0' or p.tipoEncuesta = :tipoEncuesta) " +
						"ORDER BY p.estado, p.fechaCreacion DESC "),

		@NamedQuery(name = "HQL_PREGUNTA_POR_ID",
				query = "SELECT p FROM PreguntaEntity p " +
						"WHERE p.id = :idPregunta"),

		@NamedQuery(name = "HQL_PREGUNTA_CANT_ACTIVA",
				query = "SELECT COUNT(p) FROM PreguntaEntity p join p.encuestas e " +
						"WHERE p.estado = 'A' and e.id = :idEncuesta"),

		@NamedQuery(name = "HQL_PREGUNTA_ANALISIS",
				query = "SELECT distinct p FROM PreguntaEntity p join p.respuestas r left join p.opciones o join r.envio env join env.encuesta e left join env.proyecto pro " +
						"WHERE env.estado = 'R' and p.tipoPregunta in ('simple', 'ranking') and p.tipoEncuesta = :tipoEncuesta and p.estado = :estado " +
						"and (:encuesta is null or :encuesta = '0' or e.id = :encuesta) and (:proyecto is null or :proyecto = '0' or pro.id = :proyecto) "),

		@NamedQuery(name = "HQL_PREGUNTA_ABIERTAS",
				query = "SELECT distinct p FROM PreguntaEntity p join p.respuestas r join r.envio env join env.encuesta e left join env.proyecto pro " +
						"WHERE env.estado = 'R' and p.tipoPregunta in ('abierta') and p.tipoEncuesta = 'N' and p.estado = :estado " +
						"and (:encuesta is null or :encuesta = '0' or e.id = :encuesta) and (:proyecto is null or :proyecto = '0' or pro.id = :proyecto)")

})

public class PreguntaEntity implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PREGUNTA_SEQ")
	@SequenceGenerator(name="PREGUNTA_SEQ", sequenceName="PREGUNTA_SEQ", allocationSize = 1)
	@Column(name = "IDPREGUNTA")
	private int id;
	@Column(name = "TITULO")
	private String titulo;
	@Column(name = "AYUDA")
	private String ayuda;
	@Column(name = "TIPOPREGUNTA")
	private String tipoPregunta;
	@Column(name = "TIPOENCUESTA")
	private String tipoEncuesta;
	@Column(name = "ESTADO")
	private String estado;
	@Column(name = "ESCALAVALORACION")
	private Float escalaValoracion;
	@Column(name = "USUARIOCREADOR")
	private String usuarioCreador;
	@Column(name = "FECHACREACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;

	@OneToMany(mappedBy = "pregunta", fetch = FetchType.EAGER)
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	private List<OpcionEntity> opciones;

	@OneToMany(mappedBy = "pregunta")
	private List<RespuestaEntity> respuestas;

	@ManyToMany(cascade = {javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST})
	@JoinTable(name = "ENC_PRE", joinColumns = { @JoinColumn(name = "IDPREGUNTA")}, inverseJoinColumns = { @JoinColumn(name = "IDENCUESTA")})
	private Set<EncuestaEntity> encuestas;

/*Constructor de titulo*/

	public PreguntaEntity() {
	}


	public PreguntaEntity(int id) {
		this.setId(id);
	}


	/*GET AND SET*/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAyuda() {
		return ayuda;
	}

	public void setAyuda(String ayuda) {
		this.ayuda = ayuda;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Float getEscalaValoracion() {
		return escalaValoracion;
	}

	public void setEscalaValoracion(Float escalaValoracion) {
		this.escalaValoracion = escalaValoracion;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public List<OpcionEntity> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<OpcionEntity> opciones) {
		this.opciones = opciones;
	}

	public List<RespuestaEntity> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<RespuestaEntity> respuestas) {
		this.respuestas = respuestas;
	}

	public Set<EncuestaEntity> getEncuestas() {
		return encuestas;
	}

	public void setEncuestas(Set<EncuestaEntity> encuestas) {
		this.encuestas = encuestas;
	}


}
