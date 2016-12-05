package com.satisfaccion.spring.service;

import com.satisfaccion.jpa.data.EncuestaEntity;
import com.satisfaccion.jpa.data.ProyectoEntity;
import com.satisfaccion.jpa.data.RespuestaEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class AnalisisEncuestaServicio {

	/*ATRIBUTOS*/
	protected EntityManager entityManager;


	/*METODOS*/

	@Transactional
	public List<EncuestaEntity> buscarEncuestas(String tipoEncuesta) throws DataAccessException {

		List<EncuestaEntity> resultList = getEntityManager().createNamedQuery("HQL_ENCUESTA")
				.setParameter("tipoEncuesta", tipoEncuesta)
				.setParameter("estado", null)
				.getResultList();

		return resultList;
	}

	@Transactional
	public List<ProyectoEntity> buscarProyectos(List<EncuestaEntity> encuestas) throws DataAccessException {

		List<Integer> encuestaList = new ArrayList<Integer>();

		Iterator<EncuestaEntity> iterator = encuestas.iterator();

		while (iterator.hasNext()){
			EncuestaEntity enc = iterator.next();
			encuestaList.add(enc.getId());
		}

		List<ProyectoEntity> resultList = getEntityManager().createNamedQuery("HQL_PROYECTO_ENCUESTA")
				.setParameter("encuestaList", encuestaList)
				.getResultList();

		return resultList;
	}

	@Transactional
	public List<RespuestaEntity> filtrarEncuestas(String estado, String tipoPregunta, EncuestaEntity encuestaSelect, ProyectoEntity proyectoSelect, Date fechaInicio, Date fechaFin, String usuario) throws DataAccessException {

		List<RespuestaEntity> resultList = getEntityManager().createNamedQuery("HQL_RESPUESTA_PRUEBA")
				.setParameter("estado", estado)
				.setParameter("tipoEncuesta", tipoPregunta)
				.setParameter("encuesta", encuestaSelect.getId())
				.setParameter("proyecto", proyectoSelect.getId())
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
