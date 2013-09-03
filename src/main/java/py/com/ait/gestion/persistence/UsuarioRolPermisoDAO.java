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

import py.com.ait.gestion.domain.Permiso;
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.domain.UsuarioRolPermiso;

@PersistenceController
public class UsuarioRolPermisoDAO extends JPACrud<UsuarioRolPermiso, Long>{ 
		
		private static final long serialVersionUID = 1L;
		
		@Inject
		@SuppressWarnings("unused")
		private Logger logger;
		
		
		@Inject
		private EntityManager em;
		@SuppressWarnings("unchecked")
		
		public List<UsuarioRolPermiso> findPage(int pageSize, int first, String sortField, boolean sortOrderAsc) {
		
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(UsuarioRolPermiso.class);
		
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
		public  List<UsuarioRolPermiso> findRolPermisoByUser(Usuario user) {
			
			
			List<UsuarioRolPermiso> lista = this.findAll();
			List<UsuarioRolPermiso> resultList = new ArrayList<UsuarioRolPermiso>();
			Iterator<UsuarioRolPermiso> it = lista.iterator();
			while (it.hasNext())
			{
				UsuarioRolPermiso urp = it.next();
				if(urp.getUsuario().getUsuario().equals(user.getUsuario()))
				{
					resultList.add(urp);
				}
			}
			return resultList;

		}
		public int count() {
			Query q = em.createQuery("select count(*) from UsuarioRolPermiso u");
			return ((Long) q.getSingleResult()).intValue();
			
		}
		public boolean tiene(String usuario, String permiso)
		{
			return false;
		}
		public boolean tiene(Permiso permiso,Usuario usuario)
		{
			
			List<UsuarioRolPermiso> lista = this.findAll();
			Iterator<UsuarioRolPermiso> it = lista.iterator();
			while(it.hasNext())
			{
				UsuarioRolPermiso urp = it.next();
				if(urp.getPermiso()!=null)
				{
					if(urp.getPermiso().getPermisoId().equals(permiso.getPermisoId()) && urp.getUsuario().getUsuarioId().equals(usuario.getUsuarioId()) )	
					{
						return true;
					}
				}
			}
			
			return false;
			
		}
		public UsuarioRolPermiso getUsuarioRolPermiso(Long usuarioRolPermisoId)
		{
			List<UsuarioRolPermiso> lista = this.findAll();
			List<UsuarioRolPermiso> resultList = new ArrayList<UsuarioRolPermiso>();
			Iterator<UsuarioRolPermiso> it = lista.iterator();
			while (it.hasNext())
			{
				UsuarioRolPermiso urp = it.next();
				if(urp.getUsuarioRolPermisoId()==usuarioRolPermisoId)
				{
					return urp;
				}
			}
			return null;
		}
}
