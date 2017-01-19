package com.satisfaccion.spring.service;

import com.satisfaccion.jpa.data.*;
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
	public boolean almacenarRespuestas(EnvioEntity envio, List<RespuestaEntity> respuestas, String usuaioEvaluado) throws DataAccessException {

		boolean crecionRespuestas = false;

		try{

			//Encuestas basicas cambiar estado de envio
			if(envio.getEncuesta().getTipoEncuesta().equals("N")){
				envio.setEstado("R");
				entityManager.merge(envio);
			}

			//Eliminar las respuestas existentes (solo para evaluaciones)
			if(envio.getEncuesta().getTipoEncuesta().equals("E")){
				List<RespuestaEntity> existentes = respuestasExistentesEvaluacion(envio, usuaioEvaluado);

				for(RespuestaEntity respEliminar : existentes){
					entityManager.remove(entityManager.contains(respEliminar) ? respEliminar : entityManager.merge(respEliminar));
				}
			}

			for (RespuestaEntity respuesta : respuestas){

				//Para encuestas almacenar usuario
				if(envio.getEncuesta().getTipoEncuesta().equals("E")){
					respuesta.setUsuarioEvaluado(usuaioEvaluado);
				}

				entityManager.persist(respuesta);

			}

			crecionRespuestas = true;

		}catch(Exception e){
			crecionRespuestas = false;

		}finally {

			entityManager.close();
			return crecionRespuestas;

		}

	}

	@Transactional
	public List<RespuestaEntity> respuestasExistentesEvaluacion(EnvioEntity envio, String usuaioEvaluado) throws DataAccessException {

		List<RespuestaEntity> resultList = getEntityManager().createNamedQuery("HQL_RESPUESTA_EVALUACION")
				.setParameter("envio", envio.getId())
				.setParameter("usuario", usuaioEvaluado)
				.getResultList();

		return resultList;
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
