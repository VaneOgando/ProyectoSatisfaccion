package com.satisfaccion.spring.service;

import com.satisfaccion.jpa.data.OpcionEntity;
import com.satisfaccion.jpa.data.PreguntaEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//import javax.transaction.Transactional;

@Component
public class DetallePreguntaServicio {

	/*ATRIBUTOS*/
	protected EntityManager entityManager;


	/*METODOS*/

	@Transactional
	public PreguntaEntity consultarPregunta(int idPregunta) throws DataAccessException {

		List<PreguntaEntity> resultList = getEntityManager().createNamedQuery("HQL_PREGUNTA_POR_ID")
				.setParameter("idPregunta", idPregunta)
				.getResultList();

		if(resultList.size() < 1){
			return null;
		}else{
			return resultList.get(0);
		}
	}

	@Transactional
	public List<OpcionEntity> consultarOpciones(int idPregunta) throws DataAccessException {

		List<OpcionEntity> resultList = getEntityManager().createNamedQuery("HQL_OPCION_POR_PREGUNTA")
				.setParameter("idPregunta", idPregunta)
				.getResultList();

		return resultList;
	}

	@Transactional
	public int consultarNumRespuestas(int idPregunta) throws DataAccessException {

		List<Object> resultList = getEntityManager().createNamedQuery("HQL_RESPUESTA_PREGUNTA_NUMERO")
				.setParameter("idPregunta", idPregunta)
				.getResultList();

		if(resultList.size() < 1){
			return 0;
		}else{
			return Integer.parseInt(resultList.get(0).toString());
		}

	}

	@Transactional
	public int consultarNumRespuestasPreg(int idPregunta) throws DataAccessException {

		List<Object> resultList = getEntityManager().createNamedQuery("HQL_RESPUESTA_PREGUNTA_NUMERO")
				.setParameter("idPregunta", idPregunta)
				.getResultList();

		if(resultList.size() < 1){
			return 0;
		}else{
			return Integer.parseInt(resultList.get(0).toString());
		}

	}

	@Transactional
	public boolean eliminarPregunta(boolean eliminar, PreguntaEntity pregunta) throws DataAccessException {

		try{

			if (eliminar){
				//Remove solo funciona si se conoce la entidad, no se puede eliinar en una transaccion nueva
				entityManager.remove(entityManager.contains(pregunta) ? pregunta : entityManager.merge(pregunta));
			}else{
				pregunta.setEstado("I");
				entityManager.merge(pregunta);
			}

			eliminar = true;

		}catch(Exception e){
			eliminar = false;
			throw e;

		}finally {

			entityManager.close();
			return eliminar;

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
