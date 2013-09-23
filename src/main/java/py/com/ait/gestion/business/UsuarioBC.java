package py.com.ait.gestion.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;

import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Permiso;
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;


@BusinessController
public class UsuarioBC extends DelegateCrud<Usuario, Long, UsuarioDAO> {
	
	private static final long serialVersionUID = -3511112688607750317L;
	@Inject private PermisoBC permisoBC;
	@Inject private UsuarioRolPermisoBC usuarioRolPermisoBC;
	@Inject private UsuarioDAO usuarioDAO;
	@Inject private AudLogDAO audLogDAO;
	@Inject private Logger logger;
	@Inject SesionLogDAO sesionLogDAO;
	
	public List<Usuario> findPage(int pageSize, int first, String sortField, boolean sortOrderAsc){
		return usuarioDAO.findPage(pageSize, first, sortField, sortOrderAsc);
	}
	
	public int count() {
		return usuarioDAO.count();
	}
	
	public Usuario findSpecificUser(String user){
		return usuarioDAO.findSpecificUser(user);
	}
	
	public boolean checkUnique(String user) {
		List<Usuario> list = new ArrayList<Usuario>();
		list = usuarioDAO.findAll();
		boolean check = true;
		Iterator<Usuario> iter = list.iterator();
		while(iter.hasNext()) {
			Usuario usuario = iter.next();
			if(usuario.getUsuario().equals(user)) {
				check = false;
			}
		}
		
		return check;
	}
	
	public String getUsuarioActual()
	{
		return usuarioDAO.getUsuarioActual();
	}
/*****************Auditoria**************************/	
	@Override
	@Transactional
	public void insert(Usuario usuario) 
	{
		super.insert(usuario);
		
		try {
			
			audLogDAO.log(null, usuario,usuarioDAO.getUsuarioActual(),sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()) , Definiciones.Operacion.Insert,usuario.getUsuarioId());
		} catch(Exception ex) {
			
			logger.error("Error Log Audit...", ex);
		}
	}
	
	@Override
	@Transactional
	public void update(Usuario usuario) 
	{
		Usuario viejo = this.findSpecificUser(usuario.getUsuario());
		super.update(usuario);
		
		try {
			audLogDAO.log(viejo, usuario,usuarioDAO.getUsuarioActual(), sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), Definiciones.Operacion.Update,usuario.getUsuarioId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		super.delete(id);
		
	try {
			Usuario usuario = this.findSpecificUser(this.getUsuarioActual());
			if(usuario != null)
			audLogDAO.log(usuario, null, usuarioDAO.getUsuarioActual(), sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), Definiciones.Operacion.Delete,id);
		} catch(Exception ex) {
			
			logger.error("Error Log Audit...", ex);
		}
	}

	public long getMaxId() {
		return usuarioDAO.getMaxId();
	}
	
	public List<Usuario> getUsuariosByRol(Long rolId) {
		
		return usuarioDAO.getUsuariosByRol(rolId);
	}

	public boolean isAdminUser(String currentUser) {

		Usuario us = findSpecificUser(currentUser);
		Permiso permiso =permisoBC.getPermiso("administracion");
		return usuarioRolPermisoBC.tiene(permiso, us);
	}

}
