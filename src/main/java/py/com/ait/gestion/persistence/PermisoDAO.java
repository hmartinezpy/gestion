package py.com.ait.gestion.persistence;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;

import py.com.ait.gestion.domain.Permiso;


@PersistenceController
public class PermisoDAO extends JPACrud<Permiso, Long>{

	private static final long serialVersionUID = -4503908248907553290L;

	@Inject
	@SuppressWarnings("unused")
	private Logger logger;
	
	@Inject
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Permiso> findPage(int pageSize, int first, String sortField, boolean sortOrderAsc) {
		
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Permiso.class);
		
		//add order by
		Order order = Order.asc(sortField);
		if(!sortOrderAsc) 
			order = Order.desc(sortField);
		criteria.addOrder(order);

		//add limit, offset
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		return criteria.list();

	}
	
	public int count() {
		Query q = em.createQuery("select count(*) from Permiso p");
		return ((Long) q.getSingleResult()).intValue();
		
	}
	public Permiso getPermiso(Long permisoId)
	{
		Query query= em.createQuery("select new Permiso(p.permiso,p.descripcion)from Permiso p where p.permisoId = :name");
		query.setParameter("name", permisoId);
		
		return (Permiso) query.getSingleResult();
	}
	public Permiso getPermiso(String permiso)
	{
		Query query= em.createQuery("select new Permiso(p.permiso,p.descripcion)from Permiso p where p.descripcion = :name");
		query.setParameter("name", permiso);
		
		return (Permiso) query.getSingleResult();
	}
	
	
}

