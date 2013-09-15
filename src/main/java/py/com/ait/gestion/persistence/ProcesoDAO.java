package py.com.ait.gestion.persistence;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;

import py.com.ait.gestion.domain.Actividad;
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

	public String getLastSequence(Long cronogramaId) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		/*
		String nroProceso = "substring(p.nroProceso,1,locate('/',p.nroProceso)-1)";		
		String query = "select cast(max(cast("
				+ nroProceso
				+ " as int)) as string)"
				+ " from Proceso p "
				+ "where substring(p.nroProceso,locate('/',p.nroProceso)+1) = '"
				+ year + "'";
		*/		
		String query = "select cast(max(cast( " +
				"substring(p.nroProceso,locate('/', p.nroProceso)+1) " + 
				"as int)) as string) " +
				"from Proceso p  " +
				"where substring(p.nroProceso,locate('_', p.nroProceso)+1,4) = :year " +
				"and p.cronograma.cronogramaId = :cronogramaId";
		
		logger.info("ProcesoDAO.getLastSequence() query: " + query);
		Query q = em.createQuery(query);
		q.setParameter("year", year+"");
		q.setParameter("cronogramaId", cronogramaId);
		String result = ((String) q.getSingleResult());
		if (result == null)
			result = "0";
		logger.info("ProcesoDAO.getLastSequence() result: " + result);
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Actividad> getActividadesByProceso(Proceso procesoSeleccionado) {

		Query q = em.createQuery("select a from Actividad a where a.master.procesoId = :proceso order by a.fechaCreacion");
		q.setParameter("proceso", procesoSeleccionado.getProcesoId());
		
		return ((List<Actividad>) q.getResultList());
	}
}
