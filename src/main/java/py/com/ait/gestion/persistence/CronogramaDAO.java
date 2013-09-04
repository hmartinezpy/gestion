package py.com.ait.gestion.persistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;
import py.com.ait.gestion.domain.Cronograma;

@PersistenceController
public class CronogramaDAO extends JPACrud<Cronograma, Long> {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public Long getMaxId() {

		Query q = em.createQuery("select max(c.id) from Cliente c");
		return ((Long) q.getSingleResult());
	}

}