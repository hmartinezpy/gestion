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

import py.com.ait.gestion.domain.Rol;

@PersistenceController
public class RolDAO extends JPACrud<Rol, Long>{

private static final long serialVersionUID = 1L;
	
	@Inject
	@SuppressWarnings("unused")
	private Logger logger;
	
	@Inject
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	public List<Rol> findPage(int pageSize, int first, String sortField, boolean sortOrderAsc) {
		
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Rol.class);
		
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
		Query q = em.createQuery("select count(*) from Rol r");
		return ((Long) q.getSingleResult()).intValue();
		
	}
	public Rol getRol(Long rolId)
	{
		Query query= em.createQuery("select new Rol(r.rolId,r.descripcion)from Rol r where r.rolId = :name");
		query.setParameter("name", rolId);
		
		return (Rol) query.getSingleResult();
		
	}
	
	
}

