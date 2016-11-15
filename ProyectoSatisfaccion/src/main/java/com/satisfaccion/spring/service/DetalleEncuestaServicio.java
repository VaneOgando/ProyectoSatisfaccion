package com.satisfaccion.spring.service;

import com.satisfaccion.jpa.data.EncuestaEntity;
import com.satisfaccion.jpa.data.EnvioEntity;
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
public class DetalleEncuestaServicio {

	/*ATRIBUTOS*/
	protected EntityManager entityManager;


	/*METODOS*/

	@Transactional
	public EncuestaEntity buscarEncuesta(int idEncuesta) throws DataAccessException {

		return entityManager.find(EncuestaEntity.class, idEncuesta);
	}

	@Transactional
	public EnvioEntity buscarEnvio(int idEnvio) throws DataAccessException {

		return entityManager.find(EnvioEntity.class, idEnvio);
	}

	@Transactional
	public EnvioEntity buscarPregunta(int idPregunta) throws DataAccessException {

		return entityManager.find(EnvioEntity.class, idPregunta);
	}

	@Transactional
	public OpcionEntity buscarOpcion(int idOpcion) throws DataAccessException {

		return entityManager.find(OpcionEntity.class, idOpcion);
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
