package py.com.ait.gestion.persistence;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;

import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.Proceso;

@PersistenceController
public class ActividadDAO extends JPACrud<Actividad, Long> {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@Inject
	private Logger logger;

	public Long getMaxId() {

		Query q = em
				.createQuery("select max(a.id) from Actividad a where a.superTarea is null");
		return ((Long) q.getSingleResult());
	}

	public String getLastNroActividadByActividad(Actividad actividad) {
		String where = "";
		if (actividad != null) {
			where = " where a.master = :proceso and a.superTarea is null";
		}
		String query = "select cast(max(cast("
				+ "a.nroActividad" + " as int)) as string)"
				+ " from Actividad a" + where;

		Query q = em.createQuery(query);
		
		if(actividad!= null){
			q.setParameter("proceso", actividad.getMaster());
		}
		logger.info("ActividadDAO.getLastSequence() query: " + query);
		
		String result = ((String) q.getSingleResult());
		if (result == null)
			result = "0";
		logger.info("ActividadDAO.getLastSequence() result: " + result);
		return result;
	}

	public String getCurrentNumeroSubActividadByActividad(Actividad actividad) {
		String where = "a.master = :proceso and a.superTarea = :actividad";
		String query = "select cast(max(cast("
				+ "substring(a.nroActividad,locate('.',a.nroActividad)+1)"
				+ " as double)) as string)" + " from Actividad a where "
				+ where;
		logger.info("ActividadDAO.getLastSequence() query: " + query);
		Query q = em.createQuery(query);
		q.setParameter("proceso", actividad.getMaster());
		q.setParameter("actividad", actividad);
		String result = ((String) q.getSingleResult());
		if (result == null)
			result = "0";
		logger.info("ActividadDAO.getCurrentNumeroSubActividadByActividad() result: "
				+ result);
		return result;
	}

	/**
	 * @param proc
	 * @return
	 */
	public String getLastActividad(Proceso proc) {
		String where = " where a.master = :proceso";
		where += " and a.id = (select max(a1.id) from Actividad a1 where "+
				" a1.master = a.master and a1.superTarea is null"+
				" and a1.estado not in ('CAN', 'DEV')" +
				")";
		String groupBy = " group by a.descripcion, a.master";

		String query = "select a.descripcion"
				+ " from Actividad a" + where
				+ groupBy;

		Query q = em.createQuery(query);
		
		q.setParameter("proceso", proc);

		logger.info("ActividadDAO.getLastActividad() query: " + query);
		
		String result;
		try {
			result = ((String) q.getSingleResult());
		}catch (javax.persistence.NoResultException e) {
			result = "";
		}

		logger.info("ActividadDAO.getLastActividad() result for proceso "+proc.getProcesoId()+"= " + result);
		return result;
	}

}
