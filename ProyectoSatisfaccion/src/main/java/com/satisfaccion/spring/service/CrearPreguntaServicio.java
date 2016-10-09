package com.satisfaccion.spring.service;

import com.satisfaccion.jpa.data.*;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CrearPreguntaServicio {

	/*ATRIBUTO*/
	protected EntityManager entityManager;


/*METODOS*/

	@Transactional
	public boolean crearPregunta(PreguntaEntity pregunta, List<OpcionEntity> opciones) throws DataAccessException{

		boolean creacion = false;

		try {

			//Eliminar espcios blancos ingresados al inicio
			pregunta.setTitulo(pregunta.getTitulo().trim());
			pregunta.setAyuda(pregunta.getAyuda().trim());
			entityManager.persist(pregunta);

			if (pregunta.getTipoPregunta().equals("simple")) {

				for (OpcionEntity opcion : opciones) {
					opcion.setPregunta(pregunta);
					opcion.setTitulo(opcion.getTitulo().trim());
					entityManager.persist(opcion);
				}
			}

			creacion = true;

		}catch(Exception e){
			creacion = false;
			throw e;
		}finally {

			entityManager.close();
			return creacion;

		}

	}



	/*GET & SET*/
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
