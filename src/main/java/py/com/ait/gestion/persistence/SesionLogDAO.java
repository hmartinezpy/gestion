package py.com.ait.gestion.persistence;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;
import org.ticpy.tekoporu.transaction.Transactional;

import py.com.ait.gestion.domain.LogSesion;
import py.com.ait.gestion.domain.Usuario;


@PersistenceController
public class SesionLogDAO extends JPACrud<LogSesion, Long> {
	private static final long serialVersionUID = 1L;
	
	@Inject 
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private EntityManager em;
	
	@Inject 
	private Usuario usuario;
	
	@SuppressWarnings("unused")
	private Logger logger;
	
	public int count() {
		Query q = em.createQuery("select count(*) from LogSesion l");
		return ((Long) q.getSingleResult()).intValue();
		
	}
	
	public String ObtenerIp(String usuario) {
		String ip = null; 
		if(usuario != null && !usuario.equals("")){
			Usuario ousuarUsuario = usuarioDAO.findSpecificUser(usuario);
		     
			String select = "select l.ip from LogSesion l where l.usuario.usuarioId = :Id and l.nroSesion = "
				+ "(select max(l2.nroSesion) from LogSesion l2 where l2.usuario.usuarioId = :Id)";
			
			Query q = em.createQuery(select);
			q.setParameter("Id", ousuarUsuario.getUsuarioId());
			ip = (String)q.getSingleResult();
		}

		 return ip;
		
	}
	
	@SuppressWarnings("unchecked")
	public List <LogSesion> findPage(int pageSize, int first, String sortField, boolean sortOrderAsc,Map<String,String> filters ) {
		String order = "asc";
		if (!sortOrderAsc) order="desc";
		String query = "select l from py.com.ait.gestion.domain.LogSesion l where 1 = 1";
		String colName;
		String values[] = new String[0];
		if (filters != null && filters.size() > 0) {
			
			String[] keys = filters.keySet().toArray(new String[] {});
			values = new String[keys.length];
			for(int i = 0; i < keys.length; i++) {
				colName = keys[i];
				values[i] = filters.get(colName);
				query += " and lower(l."+colName+") like lower(:p)";
			}
			
		}
		
		query += " order by "+ sortField +" "+order;
		Query q = em.createQuery(query);
		//setear parametros para el filtrado si hubieran
		for(int i = 0; i < values.length; i++) {
			q.setParameter("p","%"+ values[i]+ "%");
		}
		q.setFirstResult(first);
		q.setMaxResults(pageSize);
		return q.getResultList();

	}
	
	
	@Transactional
	public void InsertLog(LogSesion log){

	if(log.getExito().equals("Y")){
		usuario =  usuarioDAO.findSpecificUser(log.getUsuario().getUsuario());
		this.insert(log);
		log.setNroSesion(log.getLogSesionId());
		this.update(log);
		usuario.setNroSesion(log.getLogSesionId());
		usuario.setUltimoLogin(log.getFechaIntento());
		usuarioDAO.update(usuario);
	}
	else{
		this.insert(log);
	}
	

	}
	
	
}
