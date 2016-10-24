package com.satisfaccion.jpa.data;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="ENVIO")

@NamedQueries(value={

/*		@NamedQuery(name = "HQL_HISTORIAL_POR_EQUIPO",
				query = "SELECT h FROM HistorialInventarioEntity h JOIN h.equipo e JOIN h.categoria ca " +
						"WHERE e.numSerie = :numSerie AND ca.tipoCategoria = 'historial' " +
						"ORDER BY h.id DESC"),
		*/
})

@NamedNativeQueries({
/*
		@NamedNativeQuery(name = "SQL_HISTORIAL_USUARIO_ASIGNADO_EQUIPO", query="Select usuario FROM (SELECT h.usuarioAsig as usuario FROM HistorialInventario h " +
																									"WHERE h.fkequipo = ? AND h.FKCATEGORIA = ? ORDER BY h.IDHISTORIALINV DESC) " +
																				"where rownum = 1"),
*/
})

public class EnvioEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENVIO_SEQ")
	@SequenceGenerator(name="ENVIO_SEQ", sequenceName="ENVIO_SEQ", allocationSize = 1)
	@Column(name = "IDENVIO")
	private int id;
	@Column(name = "FECHAENVIO")
	@Temporal(TemporalType.DATE)
	private Date fechaEnvio;
	@Column(name = "USUARIOCREADOR")
	private String usuarioCreador;
	@Column(name = "DESTINATARIO")
	private String destinatario;
	@Column(name = "ESTADO")
	private String estado;

	@ManyToOne
	@JoinColumn(name = "FKENCUESTA")
	private EncuestaEntity encuesta;

	@ManyToOne
	@JoinColumn(name = "FKPROYECTO")
	private ProyectoEntity proyecto;

	@OneToMany(mappedBy = "envio", fetch = FetchType.LAZY)
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	private List<RespuestaEntity> respuestas;


	/*GET AND SET*/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public EncuestaEntity getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(EncuestaEntity encuesta) {
		this.encuesta = encuesta;
	}

	public ProyectoEntity getProyecto() {
		return proyecto;
	}

	public void setProyecto(ProyectoEntity proyecto) {
		this.proyecto = proyecto;
	}

	public List<RespuestaEntity> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<RespuestaEntity> respuestas) {
		this.respuestas = respuestas;
	}
}
