package py.com.ait.gestion.persistence;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;

import py.com.ait.gestion.domain.Usuario;


@PersistenceController
public class UsuarioDAO extends JPACrud<Usuario, Long>{ 
	
	private static final long serialVersionUID = 5280096243687530380L;

	@Inject
	@SuppressWarnings("unused")
	private Logger logger;
	
	@Inject
	private EntityManager em;
	@SuppressWarnings("unchecked")
	public List<Usuario> findPage(int pageSize, int first, String sortField, boolean sortOrderAsc) {
		String order = "asc";
		if (!sortOrderAsc) order="desc";
		Query q = em.createQuery("select new Usuario(u.usuarioId,u.apellido,u.email,u.estado,u.nombre,u.nroSesion,u.usuario, u.ultimoLogin)" +
		" from py.com.ait.gestion.domain.Usuario u order by "+sortField +" "+order);
		
		q.setFirstResult(first);
		q.setMaxResults(pageSize);
		return q.getResultList();

	}
	
	public int count() {
		Query q = em.createQuery("select count(*) from Usuario u");
		return ((Long) q.getSingleResult()).intValue();
		
	}
	
	
	
	public Usuario findSpecificUser(String user ) {
		
		try {
			
			Query q = em.createQuery("select new Usuario(u.usuarioId,u.apellido,u.email,u.estado,u.nombre,u.nroSesion,u.usuario,u.ultimoLogin)" +
					" from py.com.ait.gestion.domain.Usuario u  where u.usuario = :name");
			
			q.setParameter("name", user);
					
			return (Usuario) q.getSingleResult(); 
			
		} catch(Exception ex) {
			
			logger.error("Error, findSpecificUser, user: " + user, ex);
			return null;
		}
	}
	
	
	public String getUsuarioActual()
	{
		
		return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
	}
}
