package com.satisfaccion.spring.service;

import com.satisfaccion.jpa.data.*;
import org.hibernate.Hibernate;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
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
	public List<PreguntaEntity> buscarPreguntasAnalisis(String estado, String tipoPregunta, EncuestaEntity encuestaSelect, ProyectoEntity proyectoSelect, Date fechaInicio, Date fechaFin, PreguntaEntity preguntaSelect) throws DataAccessException {

		List<PreguntaEntity> resultList = null;

		if(fechaInicio == null && fechaFin == null){

			 resultList = getEntityManager().createNamedQuery("HQL_PREGUNTA_ANALISIS_SIN_FECHA")
					.setParameter("estado", estado)
					.setParameter("tipoEncuesta", tipoPregunta)
					.setParameter("encuesta", encuestaSelect.getId())
					.setParameter("proyecto", proyectoSelect.getId())
					 .setParameter("pregunta", preguntaSelect.getId())
					.getResultList();

		}else{

			resultList = getEntityManager().createNamedQuery("HQL_PREGUNTA_ANALISIS_CON_FECHA")
					.setParameter("estado", estado)
					.setParameter("tipoEncuesta", tipoPregunta)
					.setParameter("encuesta", encuestaSelect.getId())
					.setParameter("proyecto", proyectoSelect.getId())
					.setParameter("pregunta", preguntaSelect.getId())
					.setParameter("fechaInicio", fechaInicio)
					.setParameter("fechaFin", fechaFin)
					.getResultList();

		}


		return resultList;
	}

	@Transactional
	public int buscarTotalOpcion(PreguntaEntity pregunta, OpcionEntity opcion, String estado, String tipoPregunta, EncuestaEntity encuestaSelect, ProyectoEntity proyectoSelect, Date fechaInicio, Date fechaFin) throws DataAccessException {

		List<Long> resultList = getEntityManager().createNamedQuery("HQL_RESPUESTA_VISTA_TOTAL_OPCION")
				.setParameter("estado", estado)
				.setParameter("tipoEncuesta", tipoPregunta)
				.setParameter("encuesta", encuestaSelect.getId())
				.setParameter("proyecto", proyectoSelect.getId())
/*
				.setParameter("fechaInicio", fechaInicio, TemporalType.DATE)
				.setParameter("fechaFin", fechaFin, TemporalType.DATE)
*/
				.setParameter("pregunta", pregunta.getId())
				.setParameter("opcion", opcion.getId())
				.getResultList();

		if(resultList.size() > 0){
			return resultList.get(0).intValue();
		}else{
			return 0;
		}
	}

	@Transactional
	public Float buscarTotalRanking(PreguntaEntity pregunta,String estado, String tipoPregunta, EncuestaEntity encuestaSelect, ProyectoEntity proyectoSelect, Date fechaInicio, Date fechaFin) throws DataAccessException {

		List<Double> resultList = getEntityManager().createNamedQuery("HQL_RESPUESTA_VISTA_TOTAL_RANKING")
				.setParameter("estado", estado)
				.setParameter("tipoEncuesta", tipoPregunta)
				.setParameter("encuesta", encuestaSelect.getId())
				.setParameter("proyecto", proyectoSelect.getId())
/*
				.setParameter("fechaInicio", fechaInicio, TemporalType.DATE)
				.setParameter("fechaFin", fechaFin, TemporalType.DATE)
*/
				.setParameter("pregunta", pregunta.getId())
				.getResultList();

		if(resultList.size() > 0){
			return resultList.get(0).floatValue();
		}else{
			return new Float(0);
		}
	}

	@Transactional
	public List<RespuestaEvaluacionVista> buscarEvaluaciones(String estado, EncuestaEntity encuestaSelect, String usuario, Date fechaInicio, Date fechaFin, PreguntaEntity preguntaSelect) throws DataAccessException {

		List<RespuestaEvaluacionVista> resultList = null;

		if(fechaInicio == null && fechaFin == null){

			resultList = getEntityManager().createNamedQuery("HQL_RESPUESTA_EVALUACION_VISTA_SIN_FECHA")
					.setParameter("estado", estado)
					.setParameter("encuesta", encuestaSelect.getId())
					.setParameter("pregunta", preguntaSelect.getId())
					.setParameter("usuario", usuario)
					.getResultList();

		}else{

			resultList = getEntityManager().createNamedQuery("HQL_RESPUESTA_EVALUACION_VISTA_CON_FECHA")
					.setParameter("estado", estado)
					.setParameter("encuesta", encuestaSelect.getId())
					.setParameter("pregunta", preguntaSelect.getId())
					.setParameter("usuario", usuario)
					.setParameter("fechaInicio", fechaInicio)
					.setParameter("fechaFin", fechaFin)
					.getResultList();

		}

		return resultList;
	}

	@Transactional
	public PreguntaEntity buscarPreguntaPorId(int idPregunta) throws DataAccessException {

		return entityManager.find(PreguntaEntity.class, idPregunta);
	}

	@Transactional
	public EncuestaEntity buscarEncuestaPorId(int idEncuesta) throws DataAccessException {

		return entityManager.find(EncuestaEntity.class, idEncuesta);
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
