package com.satisfaccion.spring.service;

import com.satisfaccion.jpa.data.EncuestaEntity;
import com.satisfaccion.jpa.data.EnvioEntity;
import com.satisfaccion.jpa.data.PreguntaEntity;
import com.satisfaccion.jpa.data.ProyectoEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//import javax.transaction.Transactional;

@Component
public class EnviarEncuestaServicio {

	/*ATRIBUTO*/
	protected EntityManager entityManager;


/*METODOS*/

	@Transactional
	public List<EncuestaEntity> cargarEncuestasValidas(String tipoEncuesta) throws DataAccessException {

		//Busqueda de encuestas validas para enviar, poseer mas de 1 pregunta activa
		List<Integer> resultList = getEntityManager().createNamedQuery("HQL_ENCUESTA_ENVIO_VALIDO")
				.setParameter("tipoEncuesta", tipoEncuesta)
				.getResultList();

		List<EncuestaEntity> encuestas = new ArrayList<EncuestaEntity>();

		for (Integer id : resultList){
			EncuestaEntity encuesta = entityManager.find(EncuestaEntity.class, id);
			encuestas.add(encuesta);
		}

		return encuestas;
	}

	@Transactional
	public List<ProyectoEntity> cargarProyectos() throws DataAccessException {

		List<ProyectoEntity> resultList = getEntityManager().createNamedQuery("HQL_PROYECTO")
				.getResultList();

		return resultList;
	}

	@Transactional
	public EncuestaEntity buscarEncuestaPorId(int idEncuesta) throws DataAccessException {

		return entityManager.find(EncuestaEntity.class, idEncuesta);
	}

	@Transactional
	public ProyectoEntity obtenerProyectoPorNombre(String nombreProyecto) throws DataAccessException {

		List<ProyectoEntity> resultList = getEntityManager().createNamedQuery("HQL_PROYECTO_POR_NOMBRE")
				.setParameter("nombreProyecto",nombreProyecto)
				.getResultList();

		if (resultList.size() < 1 ){
			return null;
		}else{
			return resultList.get(0);
		}
	}


	@Transactional
	public boolean crearEnvioEncuesta(EnvioEntity envio) throws DataAccessException{

		boolean resultado = false;

		try {

			if(envio.getProyecto() != null) {
				//Creacion de proyecto
				if (envio.getProyecto().getId() == 0) {
					entityManager.persist(envio.getProyecto());
				}
			}

			entityManager.persist(envio);

			resultado = true;

		}catch(Exception e){
			resultado = false;
			throw e;
		}finally {

			entityManager.close();
			return resultado;

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
