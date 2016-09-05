package com.satisfaccion.spring.service;

import com.satisfaccion.jpa.data.EncPreEntity;
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
	public boolean modificarPregunta(PreguntaEntity pregunta, List<OpcionEntity> opciones, Boolean banderaPregunta, List<OpcionEntity> opcionesEliminar, Boolean banderaEvaluacion, Boolean evaluacion) throws DataAccessException{

		boolean modificacion = false;

		try {
			//Se modifico tipo de pregunta, eliminar opciones anteriores
			if (banderaPregunta){
				eliminarOpciones(opcionesEliminar);
			}

			//Modifico el tipo de evaluacion, eliminar de encuestas asignadas
			if (banderaEvaluacion){
				eliminarDeEncuesta(evaluacion, pregunta.getId());
			}

			entityManager.merge(pregunta);

			if (pregunta.getTipoPregunta().equals("simple")){

				for (OpcionEntity opcion : opciones){
					opcion.setPregunta(pregunta);
					entityManager.merge(opcion);
				}

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

	@Transactional
	public void eliminarOpciones(List<OpcionEntity> opciones) throws Exception {

		try{

			for (OpcionEntity opcion : opciones){

				entityManager.remove(entityManager.contains(opcion) ? opcion : entityManager.merge(opcion));
			}

		}catch(Exception e){
			throw e;
		}

	}

	@Transactional
	public void eliminarDeEncuesta(boolean evaluacion, int idPregunta) throws Exception {

		String tipoEncuesta;

		if(evaluacion){
			tipoEncuesta = "N";
		}else{
			tipoEncuesta = "E";
		}

		List<EncPreEntity> resultList = getEntityManager().createNamedQuery("HQL_ENC_PRE_POR_EVALUACION_PREGUNTA")
				.setParameter("tipoEncuesta", tipoEncuesta)
				.setParameter("idPregunta", idPregunta)
				.getResultList();

		for (EncPreEntity encpre : resultList){

			entityManager.remove(entityManager.contains(encpre) ? encpre : entityManager.merge(encpre));
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
