package com.satisfaccion.jpa.data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="PROYECTO")

@NamedQueries(value={

})

public class ProyectoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROYECTO_SEQ")
	@SequenceGenerator(name="PROYECTO_SEQ", sequenceName="PROYECTO_SEQ", allocationSize = 1)
	@Column(name = "IDPROYECTO")
	private int id;
	@Column(name = "PROYECTO")
	private String nombre;

	@OneToMany(mappedBy = "proyecto", fetch = FetchType.LAZY)
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

	public List<EnvioEntity> getEnvios() {
		return envios;
	}

	public void setEnvios(List<EnvioEntity> envios) {
		this.envios = envios;
	}
}
