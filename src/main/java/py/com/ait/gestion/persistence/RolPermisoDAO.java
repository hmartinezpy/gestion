package py.com.ait.gestion.persistence;

import java.util.ArrayList;
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

import py.com.ait.gestion.domain.Rol;
import py.com.ait.gestion.domain.RolPermiso;

@PersistenceController
public class RolPermisoDAO extends JPACrud<RolPermiso, Long>{

private static final long serialVersionUID = 1L;
	
	@Inject
	@SuppressWarnings("unused")
	private Logger logger;
	
	@Inject
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<RolPermiso> findPage(int pageSize, int first, String sortField, boolean sortOrderAsc) {
		
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(RolPermiso.class);
		
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
		Query q = em.createQuery("select count(*) from RolPermiso r");
		return ((Long) q.getSingleResult()).intValue();
		
	}
	public List<RolPermiso> getPermisosPorRol(Rol rol)
	{
		try
		{
			Query query= em.createQuery("select new RolPermiso(rp.rolPermisoId, rp.permiso, rp.rol)from RolPermiso rp where rp.rol = :name");
			query.setParameter("name", rol);	
			return query.getResultList();
		}catch(Exception e)
		{
			return new ArrayList<RolPermiso>();
		}
		
	}
	public RolPermiso findRolPermiso(Long rolPermisoId)
	{
		Query query= em.createQuery("select new Rol(rp.rolPermisoId, rp.permiso, rp.rol)from RolPermiso rp where rp.rolPermisoId = :name");
		query.setParameter("name", rolPermisoId);
		
		return (RolPermiso) query.getSingleResult();
	}
	public RolPermiso getRolPermiso(Long rolPermisoId)
	{
		List<RolPermiso> lista = this.findAll();
		Iterator<RolPermiso> it = lista.iterator();
		while(it.hasNext())
		{
			RolPermiso rp = it.next();
			if(rp.getRolPermisoId()==rolPermisoId)
			{
				return rp;
			}
		}
		return null;
	}
	
	
}

