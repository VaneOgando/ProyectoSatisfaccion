package com.satisfaccion.jpa.data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


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
				query = "SELECT p FROM PreguntaEntity p join fetch p.opciones o " +
						"WHERE p.id = :idPregunta")

})

public class PreguntaEntity {
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
	@Column(name = "USUARIOCREADOR")
	private String usuarioCreador;
	@Column(name = "FECHACREACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;

	@OneToMany(mappedBy = "pregunta", fetch = FetchType.LAZY)
	private List<OpcionEntity> opciones;

	@OneToMany(mappedBy = "pregunta", fetch = FetchType.LAZY)
	private List<RespuestaEntity> respuestas;

	@OneToMany(mappedBy = "pregunta", fetch = FetchType.LAZY)
	private List<EncPreEntity> encPre;


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

	public List<EncPreEntity> getEncPre() {
		return encPre;
	}

	public void setEncPre(List<EncPreEntity> encPre) {
		this.encPre = encPre;
	}
}
