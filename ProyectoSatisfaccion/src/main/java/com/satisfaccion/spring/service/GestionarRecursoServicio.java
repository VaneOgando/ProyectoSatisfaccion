package com.satisfaccion.spring.service;

import com.satisfaccion.jpa.data.*;
import com.satisfaccion.util.comun.Constantes;
//import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class GestionarRecursoServicio {

	/*ATRIBUTOS*/
	protected EntityManager entityManager;
/*

	*/
/*METODOS*//*

	@Transactional
	public List<Object> buscarEquipos(int estado) throws DataAccessException {

		List<Object> resultList = getEntityManager().createNamedQuery("HQL_EQUIPO")
				.setParameter("idEstado", estado)
				.setParameter("idMarca", 0)
				.setParameter("idModelo", 0)
				.getResultList();

		return resultList;
	}


	@Transactional
	public List<Object> buscarAccesorios(int estado) throws DataAccessException {

		List<Object> resultList = getEntityManager().createNamedQuery("HQL_ACCESORIO")
				.setParameter("idEstado", estado)
				.setParameter("idModelo", 0)
				.setParameter("idMarca", 0)
				.setParameter("idCategoria", 0)
				.getResultList();

		return resultList;
	}

	@Transactional
	public String buscarUsuarioAsignadoE(EncuestaEntity equipo) throws DataAccessException {

		List<Object> resultList = getEntityManager().createNamedQuery("SQL_HISTORIAL_USUARIO_ASIGNADO_EQUIPO")
				.setParameter(1, equipo.getNumSerie())
				.setParameter(2, Constantes.D_CAT_HISTORIAL_ASIGNACION)
				.getResultList();

		if(resultList.size() < 1){
			return null;
		}else{
			return resultList.get(0).toString();
		}
	}

	@Transactional
	public String buscarUsuarioAsignadoA(PreguntaEntity accesorio) throws DataAccessException {

		List<Object> resultList = getEntityManager().createNamedQuery("SQL_HISTORIAL_USUARIO_ASIGNADO_ACCESORIO")
				.setParameter(1, accesorio.getId())
				.setParameter(2, Constantes.D_CAT_HISTORIAL_ASIGNACION)
				.getResultList();

		if(resultList.size() < 1){
			return null;
		}else{
			return resultList.get(0).toString();
		}
	}

	@Transactional
	public EncuestaEntity obtenerEquipo(String numSerie) throws DataAccessException {

		List<EncuestaEntity> resultList = getEntityManager().createNamedQuery("HQL_EQUIPO_POR_NUMSERIE")
				.setParameter("numSerie", numSerie)
				.getResultList();
		if(resultList.size() < 1){
			return null;
		}else{
			return resultList.get(0);
		}

	}

	@Transactional
	public PreguntaEntity obtenerAccesorio(int idAccesorio) throws DataAccessException {

		List<PreguntaEntity> resultList = getEntityManager().createNamedQuery("HQL_ACCESORIO_POR_ID")
				.setParameter("idAccesorio", idAccesorio)
				.getResultList();
		if(resultList.size() < 1){
			return null;
		}else{
			return resultList.get(0);
		}

	}


	@Transactional
	public OpcionEntity obtenerCategoria(int idCategoria) throws DataAccessException {

		List<OpcionEntity> resultList = getEntityManager().createNamedQuery("HQL_CATEGORIA_POR_ID")
										.setParameter("idCategoria", idCategoria)
										.getResultList();

		if(resultList.size() < 1){
			return null;
		}else{
			return resultList.get(0);
		}

	}

	@Transactional
	public EncPreEntity obtenerEstado(int idEstado) throws DataAccessException {

		List<EncPreEntity> resultList = getEntityManager().createNamedQuery("HQL_ESTADO_POR_ID")
				.setParameter("idEstado", idEstado)
				.getResultList();

		if(resultList.size() < 1){
			return null;
		}else{
			return resultList.get(0);
		}

	}


	@Transactional
	public Boolean gestionarRecurso(EnvioEntity historial,EncuestaEntity equipo, List<PreguntaEntity> accesorios, EncPreEntity estado) throws DataAccessException {

		Boolean gestion = false;

		try {

			if (equipo.getNumSerie() != null) {

				equipo.setEstado(estado);
				entityManager.merge(equipo);

				historial.setEquipo(equipo);
				entityManager.merge(historial);  //persist rastrea futuros cambios "setEquipo(null)"

				historial.setEquipo(null);
			}

			for (PreguntaEntity accesorio : accesorios){

				accesorio.setEstado(estado);
				entityManager.merge(accesorio);

				historial.setAccesorio( accesorio);
				entityManager.merge(historial);

				historial.setAccesorio(null);

			}

		gestion = true;

		}catch(Exception e){
			gestion = false;
			throw e;
		}finally {

			entityManager.close();
			return gestion;

		}

	}

*/
	/*GET & SET*/

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
