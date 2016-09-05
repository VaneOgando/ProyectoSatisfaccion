package com.satisfaccion.jpa.data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="RESPUESTA")

@NamedQueries(value={

		@NamedQuery(name = "HQL_RESPUESTA_PREGUNTA_NUMERO",
				query = "SELECT COUNT (r) FROM RespuestaEntity r " +
						"WHERE r.pregunta.id = :idPregunta")

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
	private int valoracion;
	@Column(name = "USUARIOEVALUADO")
	private String usuarioEvaluado;

	@ManyToOne
	@JoinColumn(name = "FKPREGUNTA")
	private PreguntaEntity pregunta;

	@ManyToOne
	@JoinColumn(name = "FKOPCION")
	private OpcionEntity opcion;

	@OneToOne
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

	public float getValoracion() {
		return valoracion;
	}

	public void setValoracion(int valoracion) {
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
