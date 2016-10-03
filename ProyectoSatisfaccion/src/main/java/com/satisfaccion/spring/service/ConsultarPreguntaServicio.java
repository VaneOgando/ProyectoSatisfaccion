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
public class ConsultarPreguntaServicio {

	/*ATRIBUTOS*/
	protected EntityManager entityManager;


	/*METODOS*/

	@Transactional
	public List<PreguntaEntity> filtrarPreguntas(String estado, String tipoPregunta, String tipoEncuesta) throws DataAccessException {

		List<PreguntaEntity> resultList = getEntityManager().createNamedQuery("HQL_PREGUNTA")
				.setParameter("estado", estado)
				.setParameter("tipoPregunta", tipoPregunta)
				.setParameter("tipoEncuesta", tipoEncuesta)
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
	public boolean eliminarPregunta(boolean eliminar, PreguntaEntity pregunta) throws DataAccessException {

		try{

			if (eliminar){
				//Remove solo funciona si se conoce la entidad, no se puede eliminar en una transaccion nueva
				entityManager.remove(entityManager.contains(pregunta) ? pregunta : entityManager.merge(pregunta));
			}else{
				pregunta.setEstado("I");
				//Eliminar la pregunta de las encuestas a las que estaba asignada
				entityManager.find(PreguntaEntity.class, pregunta.getId()).getEncuestas().clear();

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
