package py.com.ait.gestion.business;


import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;

import py.com.ait.gestion.domain.LogSesion;
import py.com.ait.gestion.persistence.SesionLogDAO;
@BusinessController
public class SesionLogBC extends DelegateCrud<LogSesion, Long, SesionLogDAO> {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private SesionLogDAO logSesionDAO;
	
	
	public List<LogSesion> findPage(int pageSize, int first, String sortField, boolean sortOrderAsc, Map<String,String> filters){
		return logSesionDAO.findPage(pageSize, first, sortField, sortOrderAsc,filters );
	}
	
	public int count() {
		return logSesionDAO.count();
	}
	
	/*public Usuario findSpecificUser(String user){
		return usuarioDAO.findSpecificUser(user);
	}*/

}
