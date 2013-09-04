package py.com.ait.gestion.persistence;

import java.util.Calendar;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;

import py.com.ait.gestion.domain.Proceso;

@PersistenceController
public class ProcesoDAO extends JPACrud<Proceso, Long> {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	
	@Inject
	private Logger logger;

	public Long getMaxId() {

		Query q = em.createQuery("select max(p.id) from Proceso p");
		return ((Long) q.getSingleResult());
	}

	public String getLastSequence() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String nroProceso = "substring(p.nroProceso,1,locate('/',p.nroProceso)-1)";
		String query = "select cast(max(cast("
				+ nroProceso
				+ " as int)) as string)"
				+ " from Proceso p "
				+ "where substring(p.nroProceso,locate('/',p.nroProceso)+1) = '"
				+ year + "'";
		logger.info("ProcesoDAO.getLastSequence() query: " + query);
		Query q = em.createQuery(query);
		String result = ((String) q.getSingleResult());
		if (result == null)
			result = "0";
		logger.info("ProcesoDAO.getLastSequence() result: " + result);
		return result;
	}
}
