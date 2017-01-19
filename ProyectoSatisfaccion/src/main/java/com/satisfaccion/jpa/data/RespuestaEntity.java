package com.satisfaccion.jpa.data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="RESPUESTA")

@NamedQueries(value={

		@NamedQuery(name = "HQL_RESPUESTA_PREGUNTA_NUMERO",
				query = "SELECT COUNT (r) FROM RespuestaEntity r " +
						"WHERE r.pregunta.id = :idPregunta"),

		@NamedQuery(name = "HQL_RESPUESTA_ENCUESTA_NUMERO",
				query = "SELECT COUNT (r) FROM RespuestaEntity r JOIN r.envio e join e.encuesta enc " +
						"WHERE enc.id = :idEncuesta"),

		@NamedQuery(name = "HQL_RESPUESTA_EVALUACION",
				query = "SELECT r FROM RespuestaEntity r JOIN r.envio e " +
						"WHERE e.id = :envio and r.usuarioEvaluado = :usuario"),

		@NamedQuery(name = "HQL_RESPUESTA_ENCUESTA",
				query = "SELECT r FROM RespuestaEntity r join r.pregunta p join r.envio en join en.encuesta e left join en.proyecto pro " +
						"where p.tipoPregunta in ('simple', 'ranking') and (p.tipoEncuesta = :tipoEncuesta)  and (p.estado = :estado) and en.estado = 'R' " +
						"and (:encuesta = 0 or e.id = :encuesta) and (:proyecto = 0 or pro.id = :proyecto ) and ( (:fechaInicio is null and :fechaFin is null ) or en.fechaEnvio between :fechaInicio and :fechaFin )")

})


public class RespuestaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESPUESTA_SEQ")
	@SequenceGenerator(name="RESPUESTA_SEQ", sequenceName="RESPUESTA_SEQ", allocationSize = 1)
	@Column(name = "IDRESPUESTA")
	private int id;
	@Column(name = "OBSERVACION")
	private String observacion;
	@Column(name = "VALORACION")
	private Integer valoracion;
	@Column(name = "USUARIOEVALUADO")
	private String usuarioEvaluado;

	@ManyToOne
	@JoinColumn(name = "FKPREGUNTA")
	private PreguntaEntity pregunta;

	@ManyToOne
	@JoinColumn(name = "FKOPCION")
	private OpcionEntity opcion;

	@ManyToOne
	@JoinColumn(name = "FKENVIO")
	private EnvioEntity envio;


	/*GET AND SET*/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Integer getValoracion() {
		return valoracion;
	}

	public void setValoracion(Integer valoracion) {
		this.valoracion = valoracion;
	}

	public String getUsuarioEvaluado() {
		return usuarioEvaluado;
	}

	public void setUsuarioEvaluado(String usuarioEvaluado) {
		this.usuarioEvaluado = usuarioEvaluado;
	}

	public PreguntaEntity getPregunta() {
		return pregunta;
	}

	public void setPregunta(PreguntaEntity pregunta) {
		this.pregunta = pregunta;
	}

	public OpcionEntity getOpcion() {
		return opcion;
	}

	public void setOpcion(OpcionEntity opcion) {
		this.opcion = opcion;
	}

	public EnvioEntity getEnvio() {
		return envio;
	}

	public void setEnvio(EnvioEntity envio) {
		this.envio = envio;
	}
}
