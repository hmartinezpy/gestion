package py.com.ait.gestion.business;

import java.util.ArrayList;
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
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.domain.UsuarioRolPermiso;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;
import py.com.ait.gestion.persistence.UsuarioRolPermisoDAO;


@BusinessController
public class UsuarioRolPermisoBC extends DelegateCrud<UsuarioRolPermiso, Long, UsuarioRolPermisoDAO> {
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private UsuarioRolPermisoDAO usuarioRolPermisoDAO;
	
	@Inject 
	private AudLogDAO audLogDAO;
	
	@Inject 
	private UsuarioDAO usuarioDAO;
	
	@Inject 
	RolBC rolBC;
	
	@Inject 
	PermisoBC permisoBC;
	
	@Inject 
	RolPermisoBC rolPermisoBC;
	
	@Inject SesionLogDAO sesionLogDAO;
	
	public List<UsuarioRolPermiso> findRolesPermisosbyUser(Usuario user){
		return usuarioRolPermisoDAO.findRolPermisoByUser(user);
	}
	public List<Rol> findRoles(Usuario user)
	{
		List<UsuarioRolPermiso> usuarioRolesList= this.findAll();
		List<Rol> rolList = new ArrayList<Rol>();
		Iterator<UsuarioRolPermiso> iter = usuarioRolesList.iterator();
		while(iter.hasNext()) {
			UsuarioRolPermiso usuarioRoles = iter.next();
			if(usuarioRoles.getUsuario().getUsuario().equals(user.getUsuario())){
				rolList.add(usuarioRoles.getRol());
			}
		}
		return rolList;
		
	}
	public List<Permiso> findPermiso(Usuario user)
	{
		List<UsuarioRolPermiso> usuarioRolesList= this.findAll();
		List<Permiso> rolList = new ArrayList<Permiso>();
		Iterator<UsuarioRolPermiso> iter = usuarioRolesList.iterator();
		while(iter.hasNext()) 
		{
			UsuarioRolPermiso usuarioRoles = iter.next();
			if(usuarioRoles.getUsuario().getUsuario().equals(user.getUsuario())){
				rolList.add(usuarioRoles.getPermiso());
			}
		}
		return rolList;
		
	}
	public List<Rol> findRolesDisponibles(Usuario user)
	{
		
		List<Rol> rolDisponiblesList = rolBC.findAll();
		List<Rol> rolAsociados =this.findRoles(user);
		List<Rol> rolDisponiblesList2 = rolBC.findAll();
		Iterator <Rol> itDisponbles = rolDisponiblesList.iterator();
		
		while(itDisponbles.hasNext()) {
			
			Rol dis = itDisponbles.next();
			Iterator <Rol> itAsociados = rolAsociados.iterator();
			while(itAsociados.hasNext())
			{
				
				Rol as = itAsociados.next();
				if(as!= null)
				{
					if(as.equals(dis))
					{
						rolDisponiblesList2.remove(dis);
					}
				}
			}
		}
		return rolDisponiblesList2;
		
	}
	public List<Permiso> findPermisosDisponibles(Usuario user)
	{
		
		List<Permiso> permisoDisponiblesList = permisoBC.findAll();
		List<Permiso> permisoAsociados =this.findPermiso(user);
		List<Permiso> permisoDisponiblesList2 = permisoBC.findAll();
		Iterator <Permiso> itDisponbles = permisoDisponiblesList.iterator();
		
		while(itDisponbles.hasNext()) {
			
			Permiso dis = itDisponbles.next();
			Iterator <Permiso> itAsociados = permisoAsociados.iterator();
			while(itAsociados.hasNext())
			{
				
				Permiso as = itAsociados.next();
				if(as!=null)
				{
					if(as.getPermisoId().equals(dis.getPermisoId()))
					{
						permisoDisponiblesList2.remove(dis);
					}
				}
			}
		}
		return permisoDisponiblesList2;
		
	}
	
	public int count() {
		return usuarioRolPermisoDAO.count();
	}
	
	public boolean tiene(Permiso permiso, Usuario usuario)
	{
		if (permiso == null)
		{
			return false;
		}
		
		boolean clave = usuarioRolPermisoDAO.tiene(permiso, usuario);
		if(!clave)
		{
			List<Rol> listaRoles= this.findRoles(usuario);
			Iterator<Rol> itRoles = listaRoles.iterator();
			while(itRoles.hasNext())
			{
				Rol r =itRoles.next();
				List<RolPermiso> listaPermisos = rolPermisoBC.getPermisosPorRol(r);
				Iterator<RolPermiso> itPermisos = listaPermisos.iterator();
				while(itPermisos.hasNext())
				{
					Permiso p = itPermisos.next().getPermiso();
					if(p!=null)
					{
						if(p.getPermisoId().equals(permiso.getPermisoId()))
						{
							return true;
						}
					}
				}
				
			}
		}
		return clave;
	}
	public UsuarioRolPermiso getUsuarioRolPermiso(Long usuarioRolPermisoId)
	{
		return usuarioRolPermisoDAO.getUsuarioRolPermiso(usuarioRolPermisoId);
	}
	
	@Override
	@Transactional
	public void insert(UsuarioRolPermiso urp) 
	{
		
		super.insert(urp);
		
		try {
			audLogDAO.log(null, urp,usuarioDAO.getUsuarioActual(),sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), Definiciones.Operacion.Insert,urp.getUsuarioRolPermisoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	@Transactional
	public void update(UsuarioRolPermiso urp) 
	{
		UsuarioRolPermiso viejo = this.getUsuarioRolPermiso(urp.getUsuarioRolPermisoId());
		super.update(urp);
		try {
			audLogDAO.log(viejo, urp,usuarioDAO.getUsuarioActual(), sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), Definiciones.Operacion.Update,urp.getUsuarioRolPermisoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	@Transactional
	public void delete(Long usuarioRolPermiso) 
	{
		UsuarioRolPermiso urp = this.getUsuarioRolPermiso(usuarioRolPermiso);
		super.delete(usuarioRolPermiso);
		try {
			audLogDAO.log(urp, null,usuarioDAO.getUsuarioActual(), sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), Definiciones.Operacion.Delete,urp.getUsuarioRolPermisoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
