package com.satisfaccion.spring.service;

import com.satisfaccion.jpa.data.*;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Component
public class BuzonSugerenciaServicio {

	/*ATRIBUTOS*/
	protected EntityManager entityManager;


	/*METODOS*/

	@Transactional
	public List<EncuestaEntity> buscarEncuestas() throws DataAccessException {

		List<EncuestaEntity> resultList = getEntityManager().createNamedQuery("HQL_ENCUESTA")
				.setParameter("tipoEncuesta", "N")
				.setParameter("estado", null)
				.getResultList();

		return resultList;
	}

	@Transactional
	public List<ProyectoEntity> buscarProyectos(List<EncuestaEntity> encuestas) throws DataAccessException {

		List<Integer> encuestaList = new ArrayList<Integer>();

		Iterator<EncuestaEntity> iterator = encuestas.iterator();

		while (iterator.hasNext()) {
			EncuestaEntity enc = iterator.next();
			encuestaList.add(enc.getId());
		}

		List<ProyectoEntity> resultList = getEntityManager().createNamedQuery("HQL_PROYECTO_ENCUESTA")
				.setParameter("encuestaList", encuestaList)
				.getResultList();

		return resultList;
	}

	@Transactional
	public List<PreguntaEntity> buscarPreguntasObservacion(String estado, EncuestaEntity encuestaSelect, ProyectoEntity proyectoSelect, Date fechaInicio, Date fechaFin) throws DataAccessException {

		List<PreguntaEntity> resultList = getEntityManager().createNamedQuery("HQL_PREGUNTA_ABIERTAS")
				.setParameter("estado", estado)
				.setParameter("encuesta", encuestaSelect.getId())
				.setParameter("proyecto", proyectoSelect.getId())
				.getResultList();

		return resultList;
	}

	@Transactional
	public List<RespuestaEncuestaVista> buscarObservaciones(PreguntaEntity pregunta, String estado, EncuestaEntity encuestaSelect, ProyectoEntity proyectoSelect, Date fechaInicio, Date fechaFin) throws DataAccessException {

		List<RespuestaEncuestaVista> resultList = getEntityManager().createNamedQuery("HQL_RESPUESTA_VISTA_OBSERVACIONES")
				.setParameter("estado", estado)
				.setParameter("encuesta", encuestaSelect.getId())
				.setParameter("proyecto", proyectoSelect.getId())
				.setParameter("pregunta", pregunta.getId())
				.getResultList();
/*
				.setParameter("fechaInicio", fechaInicio, TemporalType.DATE)
				.setParameter("fechaFin", fechaFin, TemporalType.DATE)
*/

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
