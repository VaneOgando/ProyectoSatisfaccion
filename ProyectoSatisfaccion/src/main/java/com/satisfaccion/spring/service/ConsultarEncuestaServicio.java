package com.satisfaccion.spring.service;

import com.satisfaccion.jpa.data.EncuestaEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Component
public class ConsultarEncuestaServicio {

	/*ATRIBUTOS*/
	protected EntityManager entityManager;


	/*METODOS*/

	@Transactional
	public List<EncuestaEntity> filtrarEncuestas(String estado, String tipoEncuesta) throws DataAccessException {

		List<EncuestaEntity> resultList = getEntityManager().createNamedQuery("HQL_ENCUESTA")
				.setParameter("estado", estado)
				.setParameter("tipoEncuesta", tipoEncuesta)
				.getResultList();

		return resultList;
	}


	@Transactional
	public int consultarNumRespuestas(int idEncuesta) throws DataAccessException {

		List<Object> resultList = getEntityManager().createNamedQuery("HQL_RESPUESTA_ENCUESTA_NUMERO")
				.setParameter("idEncuesta", idEncuesta)
				.getResultList();

		if(resultList.size() < 1){
			return 0;
		}else{
			return Integer.parseInt(resultList.get(0).toString());
		}

	}

	@Transactional
	public boolean eliminarEncuesta(boolean eliminar, EncuestaEntity encuesta) throws DataAccessException {

		try{

			if (eliminar){
				//Elimino las preguntas asociadas a la encuesta a eliminar
				entityManager.find(EncuestaEntity.class, encuesta.getId()).getPreguntas().clear();

				//Remove solo funciona si se conoce la entidad, no se puede eliminar en una transaccion nueva
				entityManager.remove(entityManager.contains(encuesta) ? encuesta : entityManager.merge(encuesta));
			}else{
				encuesta.setEstado("I");
				entityManager.merge(encuesta);

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
