package py.com.ait.gestion.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;

import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Rol;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.RolDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;


@BusinessController
public class RolBC extends DelegateCrud<Rol, Long, RolDAO> {
	
	private static final long serialVersionUID = -1861651313615657681L;
	@Inject private RolDAO rolDAO;
	@Inject private AudLogDAO audLogDAOU;
	@Inject private UsuarioDAO usuarioDAO;
	@Inject private SesionLogDAO sesionLogDAO;
	
	
	public List<Rol> findPage(int pageSize, int first, String sortField, boolean sortOrderAsc){
		return rolDAO.findPage(pageSize,first, sortField, sortOrderAsc);
	}
	
	public int count() {
		return rolDAO.count();
	}
	public Rol getRol(Long rolId)
	{
		return rolDAO.getRol(rolId);
		
	}
	public boolean esUnica(Rol rol)
	{
		List<Rol> rolesList = rolDAO.findAll();
		Iterator<Rol> itRoles = rolesList.iterator();
		while (itRoles.hasNext())
		{
			Rol r = itRoles.next();
			if(r.getDescripcion().equals(rol.getDescripcion()))
			{
				if(!rol.getRolId().equals(r.getRolId()))
				{
					return false;
				}
				
			}
		}
		return true;
	}
	public Rol getRol(String rol)
	{
		
		List<Rol> lista = rolDAO.findAll();
		Iterator<Rol> it = lista.iterator();
		while(it.hasNext())
		{
			Rol r = it.next();
			if(r.getDescripcion().equals(rol))
			{
				return r;	
			}
		}
		
		return null;
	}
	@Override
	@Transactional
	public void insert(Rol rol) 
	{
		
		super.insert(rol);
		
		try {
			audLogDAOU.log(null, rol, usuarioDAO.getUsuarioActual(), sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()),Definiciones.Operacion.Insert,rol.getRolId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	@Transactional
	public void update(Rol rol) 
	{
		Rol viejo = this.getRol(rol.getRolId());
		super.update(rol);
		try {
			audLogDAOU.log(viejo, rol, usuarioDAO.getUsuarioActual(), sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), Definiciones.Operacion.Update, rol.getRolId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<String> getRolesAsString() {
		
		return getRolesFiltradosAsString(null);
	}

	public List<String> getRolesFiltradosAsString(List<String> rolesAFiltrar) {
		
		boolean aplicarFiltrado = (rolesAFiltrar != null);
		List<String> list = new ArrayList<String>();
		for(Rol r : rolDAO.findAll()) {
			
			if(aplicarFiltrado && !rolesAFiltrar.contains(r.getDescripcion()))
				list.add(r.getDescripcion());
			else if(!aplicarFiltrado)
				list.add(r.getDescripcion());
		}
		
		return list;
	}
	
}