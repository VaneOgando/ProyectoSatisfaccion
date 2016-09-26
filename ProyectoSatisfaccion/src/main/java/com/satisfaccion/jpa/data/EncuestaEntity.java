package com.satisfaccion.jpa.data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ENCUESTA")

@NamedQueries(value= {

		@NamedQuery(name = "HQL_ENCUESTA",
				query = "SELECT e FROM EncuestaEntity e " +
						"WHERE (:estado is null or :estado = '0' or e.estado = :estado) " +
						"AND (:tipoEncuesta is null or :tipoEncuesta = '0' or e.tipoEncuesta = :tipoEncuesta) " +
						"ORDER BY e.estado, e.fechaCreacion DESC ")

})

public class EncuestaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENCUESTA_SEQ")
	@SequenceGenerator(name="ENCUESTA_SEQ", sequenceName="ENCUESTA_SEQ", allocationSize = 1)
	@Column(name = "IDENCUESTA")
	private int id;
	@Column(name = "ENCUESTA")
	private String nombre;
	@Column(name = "TITULO")
	private String titulo;
	@Column(name = "DESCRIPCION")
	private String descripcion;
	@Column(name = "TIPOENCUESTA")
	private String tipoEncuesta;
	@Column(name = "ESTADO")
	private String estado;
	@Column(name = "USUARIOCREADOR")
	private String usuarioCreador;
	@Column(name = "FECHACREACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;


	@OneToMany(mappedBy = "encuesta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EncPreEntity> encPre;

	@OneToMany(mappedBy = "encuesta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EnvioEntity> envios;



	/*GET AND SET*/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public List<EncPreEntity> getEncPre() {
		return encPre;
	}

	public void setEncPre(List<EncPreEntity> encPre) {
		this.encPre = encPre;
	}

	public List<EnvioEntity> getEnvios() {
		return envios;
	}

	public void setEnvios(List<EnvioEntity> envios) {
		this.envios = envios;
	}
}
