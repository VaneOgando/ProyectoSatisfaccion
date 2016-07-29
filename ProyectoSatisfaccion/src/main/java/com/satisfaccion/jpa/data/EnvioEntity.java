package com.satisfaccion.jpa.data;

import javax.persistence.*;
import java.util.Date;


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
	@Column(name = "USUARIO")
	private String usuario;
	@Column(name = "ESTADO")
	private String estado;

	@ManyToOne
	@JoinColumn(name = "FKENCUESTA")
	private EncuestaEntity encuesta;

	@ManyToOne
	@JoinColumn(name = "FKPROYECTO")
	private ProyectoEntity proyecto;


	@OneToOne
	@JoinColumn(name = "envio")
	private RespuestaEntity respuesta;


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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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

	public RespuestaEntity getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(RespuestaEntity respuesta) {
		this.respuesta = respuesta;
	}
}
