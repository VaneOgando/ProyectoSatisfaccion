package com.satisfaccion.jpa.data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="ENC_PRE")

@NamedQueries(value={

})

public class EncPreEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENC_PRE_SEQ")
	@SequenceGenerator(name="ENC_PRE_SEQ", sequenceName="ENC_PRE_SEQ", allocationSize = 1)
	@Column(name = "IDENC_PRE")
	private int id;

	@ManyToOne
	@JoinColumn(name = "FKENCUESTA")
	private EncuestaEntity encuesta;

	@ManyToOne
	@JoinColumn(name = "FKPREGUNTA")
	private PreguntaEntity pregunta;


	/*GET AND SET*/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EncuestaEntity getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(EncuestaEntity encuesta) {
		this.encuesta = encuesta;
	}

	public PreguntaEntity getPregunta() {
		return pregunta;
	}

	public void setPregunta(PreguntaEntity pregunta) {
		this.pregunta = pregunta;
	}
}
