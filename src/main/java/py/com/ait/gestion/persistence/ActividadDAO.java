package py.com.ait.gestion.persistence;

import java.util.Calendar;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;

import py.com.ait.gestion.domain.Actividad;

@PersistenceController
public class ActividadDAO extends JPACrud<Actividad, Long> {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	
	@Inject
	private Logger logger;
	
	public Long getMaxId() {
		
		Query q = em.createQuery("select max(a.id) from Actividad a");
		return ((Long) q.getSingleResult());
	}

	public String getLastSequence() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String nroActividad = "substring(a.nroActividad,1,locate('/',a.nroActividad)-1)";
		String query = "select cast(max(cast("
				+ nroActividad
				+ " as int)) as string)"
				+ " from Actividad a "
				+ "where substring(a.nroActividad,locate('/',a.nroActividad)+1) = '"
				+ year + "'";
		logger.info("ActividadDAO.getLastSequence() query: " + query);
		Query q = em.createQuery(query);
		String result = ((String) q.getSingleResult());
		if (result == null)
			result = "0";
		logger.info("ActividadDAO.getLastSequence() result: " + result);
		return result;
	}
}
