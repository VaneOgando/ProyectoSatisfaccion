package com.satisfaccion.spring.service;

import com.satisfaccion.jpa.data.EncuestaEntity;
import com.satisfaccion.jpa.data.OpcionEntity;
import com.satisfaccion.jpa.data.PreguntaEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

//import javax.transaction.Transactional;

@Component
public class CrearEncuestaServicio {

	/*ATRIBUTO*/
	protected EntityManager entityManager;


/*METODOS*/

	@Transactional
	public List<EncuestaEntity> cargarEncuestasActivas() throws DataAccessException {

		//Busqueda de encuestas activas, tanto evaluacion como normales
		List<EncuestaEntity> resultList = getEntityManager().createNamedQuery("HQL_ENCUESTA")
				.setParameter("estado", "A")
				.setParameter("tipoEncuesta", null)
				.getResultList();

		return resultList;
	}

	@Transactional
	public List<PreguntaEntity> cargarPreguntasActivas(String tipoEncuesta) throws DataAccessException {

		//Busqueda de encuestas activas, tanto evaluacion como normales
		List<PreguntaEntity> resultList = getEntityManager().createNamedQuery("HQL_PREGUNTA")
				.setParameter("estado", "A")
				.setParameter("tipoPregunta", null)
				.setParameter("tipoEncuesta", tipoEncuesta)
				.getResultList();

		return resultList;
	}

	@Transactional
	public EncuestaEntity buscarEncuestaCopia(int idEncuestaCopia) throws DataAccessException {

		return entityManager.find(EncuestaEntity.class, idEncuestaCopia);
	}

	@Transactional
	public boolean buscarEncuestaPorNombre(String nombreEncuesta) throws DataAccessException {

		List<String> resultList = getEntityManager().createNamedQuery("HQL_ENCUESTA_POR_NOMBRE")
				.setParameter("nombreEncuesta",nombreEncuesta)
				.getResultList();

		if (resultList.size() > 0){
			return false;
		}else{
			return true;
		}

	}


	@Transactional
	public Set<PreguntaEntity> buscarPreguntasPorEncuesta(int idEncuesta) throws DataAccessException {

		Set<PreguntaEntity> preguntas = entityManager.find(EncuestaEntity.class, idEncuesta).getPreguntas();

		return preguntas;
	}


	@Transactional
	public PreguntaEntity buscarPreguntaPorID(String idPregunta) throws DataAccessException {

		PreguntaEntity pregunta = entityManager.find(PreguntaEntity.class, Integer.parseInt(idPregunta));

		return pregunta;

	}

	@Transactional
	public boolean crearEncuestas(EncuestaEntity encuesta) throws DataAccessException{

		boolean creacion = false;

		try {

			//Eliminar espcios blancos ingresados al inicio
			encuesta.setNombre(encuesta.getNombre().trim());
			encuesta.setTitulo(encuesta.getTitulo().trim());
			encuesta.setDescripcion(encuesta.getDescripcion().trim());

			entityManager.merge(encuesta);

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
