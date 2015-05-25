
package py.com.ait.gestion.persistence;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;

import py.com.ait.gestion.domain.Cliente;

@PersistenceController
public class ClienteDAO extends JPACrud<Cliente, Long> {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public Long getMaxId() {

		Query q = em.createQuery("select max(c.id) from Cliente c");
		return ((Long) q.getSingleResult());
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> getSortedClientes() {
		
		Query q = em.createQuery("select c from Cliente c order by c.nombre");
		return ((List<Cliente>)q.getResultList());
	}

}