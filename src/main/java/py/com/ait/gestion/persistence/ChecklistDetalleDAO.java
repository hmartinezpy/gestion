
package py.com.ait.gestion.persistence;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;

import py.com.ait.gestion.domain.Checklist;
import py.com.ait.gestion.domain.ChecklistDetalle;

@PersistenceController
public class ChecklistDetalleDAO extends JPACrud<ChecklistDetalle, Long> {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	/**
	 * @param checklist
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ChecklistDetalle> findByChecklist(Checklist checklist) {

		Query q = em
				.createQuery("select cd from ChecklistDetalle cd where cd.master = :c");
		q.setParameter("c", checklist);
		return (List<ChecklistDetalle>) q.getResultList();

	}

}
