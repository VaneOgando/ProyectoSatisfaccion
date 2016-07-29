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
	public int consultarNumeroRespuestas(int idPregunta) throws DataAccessException {

		List<Object> resultList = getEntityManager().createNamedQuery("HQL_RESPUESTA_PREGUNTA_NUMERO")
				.setParameter("idPregunta", idPregunta)
				.getResultList();

		if(resultList.size() < 1){
			return 0;
		}else{
			return Integer.parseInt(resultList.get(0).toString());
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
