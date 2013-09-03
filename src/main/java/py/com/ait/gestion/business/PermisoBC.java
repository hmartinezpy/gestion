package py.com.ait.gestion.business;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;

import py.com.ait.gestion.domain.Permiso;
import py.com.ait.gestion.persistence.PermisoDAO;


@BusinessController
public class PermisoBC extends DelegateCrud<Permiso, Long, PermisoDAO>{
		
	private static final long serialVersionUID = -9170868326183378953L;
	@Inject
	private PermisoDAO permisoDAO;
	
	
	@Override
	public List<Permiso> findAll(){
		return permisoDAO.findAll();
	}
	
	public int count() {
		return permisoDAO.count();
	}
	
	public Permiso getPermiso(Long permisoId)
	{
		return permisoDAO.getPermiso(permisoId);
	}
	
	public Permiso getPermiso(String permiso)
	{
		List<Permiso> lista = permisoDAO.findAll();
		Iterator<Permiso> iter= lista.iterator();
		Permiso p= new Permiso();
		while(iter.hasNext())
		{
			 p= iter.next();
			if(p.getDescripcion().equals(permiso))
			{
				return p;
			}
		}
		
		
		
		return null;
	}
	
	
}
