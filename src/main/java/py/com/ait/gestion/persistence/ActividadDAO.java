package py.com.ait.gestion.persistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;
import py.com.ait.gestion.domain.Actividad;

@PersistenceController
public class ActividadDAO extends JPACrud<Actividad, Long> {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	
	public Long getMaxId() {
		
		Query q = em.createQuery("select max(a.id) from Actividad a");
		return ((Long) q.getSingleResult());
	}
	
}
	


