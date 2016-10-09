package com.satisfaccion.spring.service;

import com.satisfaccion.jpa.data.EncuestaEntity;
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
public class CrearEncuestaServicio {

	/*ATRIBUTO*/
	protected EntityManager entityManager;


/*METODOS*/

	@Transactional
	public List<EncuestaEntity> cargarEncuestasActivas() throws DataAccessException {

		//Busqueda de encuestas activas, tanto evaluacion como normales
		List<EncuestaEntity> resultList = getEntityManager().createNamedQuery("HQL_ENCUESTA")
				.setParameter("estado", "A")
				.setParameter("tipoEncuesta", null)
				.getResultList();

		return resultList;
	}

	@Transactional
	public EncuestaEntity buscarEncuestaCopia(int idEncuestaCopia) throws DataAccessException {

		return entityManager.find(EncuestaEntity.class, idEncuestaCopia);
	}

/*
	@Transactional
	public List<ProyectoEntity> cargarMarcas() throws DataAccessException {

		List<ProyectoEntity> resultList = getEntityManager().createNamedQuery("HQL_MARCA")
										.getResultList();

		return resultList;
	}

	@Transactional
	public ProyectoEntity obtenerMarcaPorNombre(String nombreMarca) throws DataAccessException {

		List<ProyectoEntity> resultList = getEntityManager().createNamedQuery("HQL_MARCA_POR_NOMBRE")
				.setParameter("nombreMarca", nombreMarca)
				.getResultList();

		if (resultList.size() < 1 ){
			return null;
		}else{
			return resultList.get(0);
		}

	}

	@Transactional
	public List<RespuestaEntity> cargarModelos(ProyectoEntity marca) throws DataAccessException {

		List<RespuestaEntity> resultList = getEntityManager().createNamedQuery("HQL_MODELO_POR_MARCA")
				.setParameter("idMarca", marca.getId())
				.getResultList();

		return resultList;
	}

	@Transactional
	public EncPreEntity obtenerEstado(int idEstadoDefecto) throws DataAccessException {

		List<EncPreEntity> resultList = getEntityManager().createNamedQuery("HQL_ESTADO_POR_ID")
										.setParameter("idEstado", idEstadoDefecto)
										.getResultList();

		if(resultList.size() < 1){
			return null;
		}else{
			return resultList.get(0);
		}

	}

	@Transactional
	public OpcionEntity obtenerCategoriaHistorial(int idCategoriaDefecto) throws DataAccessException {

		List<OpcionEntity> resultList = getEntityManager().createNamedQuery("HQL_CATEGORIA_POR_ID")
				.setParameter("idCategoria", idCategoriaDefecto)
				.getResultList();

		if(resultList.size() < 1){
			return null;
		}else{
			return resultList.get(0);
		}

	}

	@Transactional
	public RespuestaEntity obtenerModeloPorNombre(String nombreModelo, int idMarca) throws DataAccessException {

		List<RespuestaEntity> resultList = getEntityManager().createNamedQuery("HQL_MODELO_POR_NOMBRE")
				.setParameter("nombreModelo", nombreModelo)
				.setParameter("idMarca", idMarca)
				.getResultList();

		if (resultList.size() < 1 ){
			return null;
		}else{
			return resultList.get(0);
		}

	}

	@Transactional
	public OpcionEntity obtenerCategoriaPorNombre(String nombreCategoria, String tipoCategoria) throws DataAccessException {

		List<OpcionEntity> resultList = getEntityManager().createNamedQuery("HQL_CATEGORIA_POR_NOMBRE_Y_TIPO")
				.setParameter("nombreCategoria", nombreCategoria)
				.setParameter("tipoCategoria", tipoCategoria)
				.getResultList();

		if (resultList.size() < 1 ){
			return null;
		}else{
			return resultList.get(0);
		}

	}
*/
	@Transactional
	public boolean crearPregunta(PreguntaEntity pregunta, List<OpcionEntity> opciones) throws DataAccessException{

		boolean creacion = false;

		try {

			//Eliminar espcios blancos ingresados al inicio
			pregunta.setTitulo(pregunta.getTitulo().trim());
			pregunta.setAyuda(pregunta.getAyuda().trim());
			entityManager.persist(pregunta);

			if (pregunta.getTipoPregunta().equals("simple")) {

				for (OpcionEntity opcion : opciones) {
					opcion.setPregunta(pregunta);
					opcion.setTitulo(opcion.getTitulo().trim());
					entityManager.persist(opcion);
				}
			}

			creacion = true;

		}catch(Exception e){
			creacion = false;
			throw e;
		}finally {

			entityManager.close();
			return creacion;

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
