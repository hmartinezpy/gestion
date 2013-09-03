package py.com.ait.gestion.persistence;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;
import org.ticpy.tekoporu.transaction.Transactional;

import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.AudLog;
import py.com.ait.gestion.domain.AudTabla;
import py.com.ait.gestion.domain.Base;
import py.com.ait.gestion.domain.Usuario;



@PersistenceController
public class AudLogDAO extends JPACrud<AudLog, Long>{

private static final long serialVersionUID = 1L;
	
	@Inject
	@SuppressWarnings("unused")
	private Logger logger;
	
	@Inject
	private EntityManager em;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	@Inject 
	private AudTablaDAO audTablaDAO;
	
	@SuppressWarnings("unchecked")
	public List<AudLog> findPage(int pageSize, int first, String sortField, boolean sortOrderAsc,  Map<String,String> filters) {
		
		String order = "asc";
		if (!sortOrderAsc) order="desc";
		String query = "select l from py.com.ait.gestion.domain.AudLog l where 1 = 1";
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
	
	public int count() {
		Query q = em.createQuery("select count(*) from AudLog r");
		return ((Long) q.getSingleResult()).intValue();
		
	}
	
	@Transactional
	public void log(Object viejo, Object nuevo,String user, String ip,String tipo,Long id ) throws Exception
	{
		AudLog log = new AudLog();
		//si es una insercion
		if (tipo.equals(Definiciones.Operacion.Insert))
		{
			log.setValorAnterior(Definiciones.TiposRegistros.Nuevo);
			if(nuevo!=null)
			{
				Base baseNuevo = (Base) nuevo;
				
				//log.setValorNuevo(baseNuevo.toAuditoriaLogString());
				log.setValorNuevo(baseNuevo.asXML());
				AudTabla tabla = audTablaDAO.findSpecificTabla(baseNuevo.getTabla());
				log.setAudTabla(tabla);
				log.setRegistroId(id);
				log.setIp(ip);
			}
			
		}
		//si es un delete
		if (tipo.equals(Definiciones.Operacion.Delete))
		{
			if(viejo!=null)
			{
				Base baseViejo = (Base) viejo;
				
				//log.setValorAnterior(baseViejo.toAuditoriaLogString());
				log.setValorNuevo(baseViejo.asXML());
				AudTabla tabla = audTablaDAO.findSpecificTabla(baseViejo.getTabla());
				log.setAudTabla(tabla);
				log.setRegistroId(id);
				log.setIp(ip);
			}
			
			log.setValorNuevo(Definiciones.TiposRegistros.Borrado);
		}
		//si es un update
		if (tipo.equals(Definiciones.Operacion.Update))
		{
			if(viejo!=null && nuevo != null)
			{
				if(!viejo.equals(nuevo))
				{
					Base baseViejo = (Base) viejo;
					//log.setValorAnterior(baseViejo.toAuditoriaLogString());
					log.setValorAnterior(baseViejo.asXML());
					Base baseNuevo = (Base) nuevo;
				
					//log.setValorNuevo(baseNuevo.toAuditoriaLogString());
					log.setValorNuevo(baseNuevo.asXML());
					log.setIp(ip);
					
					AudTabla tabla = audTablaDAO.findSpecificTabla(baseViejo.getTabla());
					log.setAudTabla(tabla);
					log.setRegistroId(id);
				}
				
			}
			
		
		}
		
		Calendar d = Calendar.getInstance();
		log.setFecha(new Timestamp(d.getTimeInMillis()));
		Usuario usuario = usuarioDAO.findSpecificUser(user);
		log.setUsuario(usuario);
		this.insert(log);
	}
	
	public AudLog getRegistro(String idRegistro){
		String query = "";
		AudLog audLog = null;
		if(idRegistro != null && !idRegistro.equals("")){
			query = "select a from AudLog a where a.audLogId = " + idRegistro;
			Query q = em.createQuery(query);
			audLog = (AudLog)q.getSingleResult();
		}
		
		return audLog; 
	}
	
}

