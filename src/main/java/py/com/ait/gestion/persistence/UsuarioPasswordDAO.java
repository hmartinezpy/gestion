package py.com.ait.gestion.persistence;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.JPACrud;

import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.domain.UsuarioPassword;

@BusinessController
public class UsuarioPasswordDAO extends JPACrud<UsuarioPassword, Long>{ 
		
		private static final long serialVersionUID = 1L;
		
		@Inject
		@SuppressWarnings("unused")
		private Logger logger;
	
		@Inject private UsuarioPassword usuarioPassword; 
		
		@Inject
		private EntityManager em;
		
		@SuppressWarnings("unchecked")
		public List<UsuarioPassword> findPage(int pageSize, int first, String sortField, boolean sortOrderAsc) {
		
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(UsuarioPassword.class);
		
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
		public UsuarioPassword findPaswwordByUser(Usuario user) {
			
			
			
			Query q = em.createQuery("select new UsuarioPassword(up.usuarioPasswordId,up.usuario,up.password)" +
					"from py.com.ait.gestion.domain.UsuarioPassword up  where up.usuario = :name");
			
			
			q.setParameter("name", user);
			
			return (UsuarioPassword) q.getSingleResult();

		}				
}
