package com.satisfaccion.jpa.data;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table(name="OPCION")

@NamedQueries(value={

		@NamedQuery(name = "HQL_OPCION_POR_PREGUNTA",
				query = "SELECT o FROM OpcionEntity o join o.pregunta p " +
						"WHERE p.id = :idPregunta ")


})

public class OpcionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OPCION_SEQ")
	@SequenceGenerator(name="OPCION_SEQ", sequenceName="OPCION_SEQ", allocationSize = 1)
	@Column(name = "IDOPCION")
	private int id;
	@Column(name = "OPCION")
	private String titulo;
	@Column(name = "VALOR")
	private Float valor;

	@ManyToOne
	@JoinColumn(name = "FKPREGUNTA")
	private PreguntaEntity pregunta;

	@OneToMany(mappedBy = "opcion", fetch = FetchType.LAZY)
	private List<RespuestaEntity> respuestas;


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

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public PreguntaEntity getPregunta() {
		return pregunta;
	}

	public void setPregunta(PreguntaEntity pregunta) {
		this.pregunta = pregunta;
	}

	public List<RespuestaEntity> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<RespuestaEntity> respuestas) {
		this.respuestas = respuestas;
	}
}
