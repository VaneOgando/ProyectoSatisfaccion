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
public class ModificarPreguntaServicio {

	/*ATRIBUTO*/
	protected EntityManager entityManager;

/*

	*/
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
	public boolean modificarPregunta(PreguntaEntity pregunta, List<OpcionEntity> opciones) throws DataAccessException{

		boolean modificacion = false;

		try {

			entityManager.persist(pregunta);

			for (OpcionEntity opcion : opciones){
				opcion.setPregunta(pregunta);
				entityManager.persist(opcion);
			}

			modificacion = true;

		}catch(Exception e){
			modificacion = false;
			throw e;
		}finally {

			entityManager.close();
			return modificacion;

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
