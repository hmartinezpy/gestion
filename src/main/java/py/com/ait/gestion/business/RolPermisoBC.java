package py.com.ait.gestion.business;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;

import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Permiso;
import py.com.ait.gestion.domain.Rol;
import py.com.ait.gestion.domain.RolPermiso;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.PermisoDAO;
import py.com.ait.gestion.persistence.RolPermisoDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;


@BusinessController
public class RolPermisoBC extends DelegateCrud<RolPermiso, Long, RolPermisoDAO> {
	private static final long serialVersionUID = 1L;
	
	@Inject private RolPermisoDAO rolPermisoDAO;
	@Inject private PermisoDAO permisoDAO;
	@Inject private AudLogDAO audLogDAO;
	@Inject private UsuarioDAO usuarioDAO;
	@Inject SesionLogDAO sesionLogDAO;
	
	public List<RolPermiso> findPage(int pageSize, int first, String sortField, boolean sortOrderAsc){
		return rolPermisoDAO.findPage(pageSize,first, sortField, sortOrderAsc);
	}
	
	public int count() {
		return rolPermisoDAO.count();
	}
	
	public List<RolPermiso> getPermisosPorRol(Rol rol)
	{
		return rolPermisoDAO.getPermisosPorRol(rol);
	}
	
	public RolPermiso getRolPermiso(Long rolPermisoId)
	{
		return rolPermisoDAO.getRolPermiso(rolPermisoId);
	}
	
	public List<Permiso> getPermisoNoAsociados(Rol rol)
	{
		List<RolPermiso> permisosAsociados = this.getPermisosPorRol(rol);
		List<Permiso> todosLosPermisos = permisoDAO.findAll();
		
	    Iterator<RolPermiso> asociados = permisosAsociados.iterator();
			
		while(asociados.hasNext())
		{
			RolPermiso rp = asociados.next();
			Permiso p = rp.getPermiso();
			todosLosPermisos.remove(p);
		}
		return todosLosPermisos;
		
	}
	
	@Override
	@Transactional
	public void insert(RolPermiso rp) 
	{
		
		super.insert(rp);
		try {
			audLogDAO.log(null, rp,usuarioDAO.getUsuarioActual(), sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), Definiciones.Operacion.Insert,rp.getRolPermisoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	@Transactional
	public void update(RolPermiso rp) 
	{
		RolPermiso viejo = this.getRolPermiso(rp.getRolPermisoId());
		super.update(rp);
		try {
			audLogDAO.log(viejo, rp,usuarioDAO.getUsuarioActual(), sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), Definiciones.Operacion.Update,rp.getRolPermisoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	@Transactional
	public void delete(Long rolPermisoId) 
	{
		RolPermiso rp = this.getRolPermiso(rolPermisoId);
		super.delete(rolPermisoId);
		try {
			audLogDAO.log(rp,null ,usuarioDAO.getUsuarioActual(), sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), Definiciones.Operacion.Delete,rp.getRolPermisoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
