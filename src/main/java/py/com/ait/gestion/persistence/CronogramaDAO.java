package py.com.ait.gestion.persistence;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;

import py.com.ait.gestion.domain.Cronograma;
import py.com.ait.gestion.domain.CronogramaDetalle;

@PersistenceController
public class CronogramaDAO extends JPACrud<Cronograma, Long> {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public Long getMaxId() {

		Query q = em.createQuery("select max(c.id) from Cliente c");
		return ((Long) q.getSingleResult());
	}

	@SuppressWarnings("unchecked")
	public List<CronogramaDetalle> getCronogramaDetallesByCronograma(
			Cronograma cronogramaSeleccionado) {

		Query q = em.createQuery("select cd from CronogramaDetalle cd where cd.master.cronogramaId = :cronograma order by cd.nroOrden");
		q.setParameter("cronograma", cronogramaSeleccionado.getCronogramaId());
		
		return ((List<CronogramaDetalle>) q.getResultList());
	}

}