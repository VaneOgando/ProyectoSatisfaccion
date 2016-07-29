package com.satisfaccion.spring.service;

import com.satisfaccion.jpa.data.*;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CrearRecursoServicio {

	/*ATRIBUTO*/
	protected EntityManager entityManager;

/*

	*/
/*METODOS*//*

	@Transactional
	public List<OpcionEntity> cargarCategorias(String tipoCategoria) throws DataAccessException {

		List<OpcionEntity> resultList = getEntityManager().createNamedQuery("HQL_CATEGORIA_POR_TIPO")
				.setParameter("tipoCategoria", tipoCategoria)
				.getResultList();

		return resultList;
	}

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

	@Transactional
	public boolean crearRecurso(ProyectoEntity marca, RespuestaEntity modelo, OpcionEntity categoria, EncPreEntity estado, EnvioEntity historial, EncuestaEntity equipo, PreguntaEntity accesorio, String opcion) throws DataAccessException{

		boolean creacion = false;

		try {

			if (marca.getId() == 0){ //Marca no existe
				entityManager.persist(marca);
			}

			modelo.setMarca(marca);

			if(modelo.getId() == 0){//modelo no existe
				entityManager.persist(modelo);
			}

			if(opcion.equals("E")) {

				equipo.setEstado(estado);
				equipo.setModelo(modelo);
				entityManager.persist(equipo);

				historial.setEquipo(equipo);

			}else if(opcion.equals("A")){

				if(categoria.getId() == 0){//categoria no existe
					categoria.setTipoCategoria("accesorio");
					entityManager.persist(categoria);
				}

				accesorio.setEstado(estado);
				accesorio.setModelo(modelo);
				accesorio.setCategoria(categoria);
				entityManager.persist(accesorio);

				historial.setAccesorio(accesorio);

			}

			entityManager.persist(historial);

			creacion = true;

		}catch(Exception e){
			creacion = false;
			throw e;
		}finally {

			entityManager.close();
			return creacion;

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
