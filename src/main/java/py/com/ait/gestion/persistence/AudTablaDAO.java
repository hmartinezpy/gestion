package py.com.ait.gestion.persistence;


import java.util.Iterator;
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

import py.com.ait.gestion.domain.AudTabla;


@PersistenceController
public class AudTablaDAO extends JPACrud<AudTabla, Long>{

private static final long serialVersionUID = 1L;
	
	@Inject
	@SuppressWarnings("unused")
	private Logger logger;
	
	@Inject
	private EntityManager em;
	
	
	
	@SuppressWarnings("unchecked")
	public List<AudTabla> findPage(int pageSize, int first, String sortField, boolean sortOrderAsc){
		
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(AudTabla.class);

		
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
		Query q = em.createQuery("select count(*) from AudTabla r");
		return ((Long) q.getSingleResult()).intValue();
		
	}
	public AudTabla findSpecificTabla(String tabla)
	{
		List<AudTabla> lista = findAll();
		Iterator<AudTabla> it = lista.iterator();
		while(it.hasNext())
		{
			AudTabla aud = it.next();

			if(aud.getNombre().equals(tabla))
			{
				return aud;
			}
		}
		return null;
	}
	
	
	
}

