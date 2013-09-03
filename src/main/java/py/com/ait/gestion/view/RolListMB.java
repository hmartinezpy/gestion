package py.com.ait.gestion.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;
import org.ticpy.tekoporu.transaction.Transactional;

import py.com.ait.gestion.business.PermisoBC;
import py.com.ait.gestion.business.RolBC;
import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.business.UsuarioRolPermisoBC;
import py.com.ait.gestion.domain.Permiso;
import py.com.ait.gestion.domain.Rol;
import py.com.ait.gestion.domain.Usuario;


@ViewController
@NextView("/admin/rol_edit.xhtml")
@PreviousView("/admin/rol_list.xhtml")
public class RolListMB extends AbstractListPageBean<Rol, Long>{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioBC usuarioBC;
	
	@Inject 
	private PermisoBC permisoBC;
	
	@Inject
	private UsuarioRolPermisoBC usuarioRolPermisoBC;
	
	
	
	private LazyDataModel<Rol> model;	
	public LazyDataModel<Rol> getModel() {
		return model;
	}
	


	public int getPageSize() {
		return pageSize;
	}

	private int pageSize = 7;

	@Inject
	private RolBC rolBC;
	
	@PostConstruct
	public void loadLazyModel() {
		
		model = new LazyDataModel<Rol>() {
			private static final long serialVersionUID = 1L;

			public List<Rol> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, String> filters) {
				if(sortField == null) sortField = "rolId"; //default sort field
				
				List<Rol> rol = new ArrayList<Rol>();
				rol = rolBC.findPage(pageSize, first, sortField, true);
				
				return rol;
			}
		};
		
		model.setRowCount(rolBC.count());
		model.setPageSize(pageSize);
		
	}
	

	@Override
	protected List<Rol> handleResultList() {
		return this.rolBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);

			if (delete) {
				rolBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}
	
	//permisos
	private boolean editar;
	private boolean crear;
	private boolean ver;
	private boolean verMenuAdmin;
	private boolean verMenuReportes;
	
	
	
	public boolean isVerMenuAdmin() {
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		String usuario = facesContext.getExternalContext().getUserPrincipal().getName(); 
		Usuario us = usuarioBC.findSpecificUser(usuario);
		Permiso permiso =permisoBC.getPermiso("administracion");
		return usuarioRolPermisoBC.tiene(permiso, us);
		
	}



	public void setVerMenuAdmin(boolean verMenuAdmin) {
		this.verMenuAdmin = verMenuAdmin;
	}

	public boolean isVerMenuReportes() {
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		String usuario = facesContext.getExternalContext().getUserPrincipal().getName(); 
		Usuario us = usuarioBC.findSpecificUser(usuario);
		Permiso permiso =permisoBC.getPermiso("ver menu reportes");
		return 	usuarioRolPermisoBC.tiene(permiso, us);
	}



	public void setVerMenuReportes(boolean verMenuReportes) {
		this.verMenuReportes = verMenuReportes;
	}

	public boolean isVer() {
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		String usuario = facesContext.getExternalContext().getUserPrincipal().getName(); 
		Usuario us = usuarioBC.findSpecificUser(usuario);
		Permiso permiso =permisoBC.getPermiso("roles ver listado");
		return 	usuarioRolPermisoBC.tiene(permiso, us);
	}



	public void setVer(boolean ver) {
		this.ver = ver;
	}



	public boolean isCrear() {
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		String usuario = facesContext.getExternalContext().getUserPrincipal().getName(); 
		Usuario us = usuarioBC.findSpecificUser(usuario);
		Permiso permiso =permisoBC.getPermiso("roles crear");
		return 	usuarioRolPermisoBC.tiene(permiso, us);
	
	}



	public void setCrear(boolean crear) {
		this.crear = crear;
	}



	public boolean isEditar()
	{
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		String usuario = facesContext.getExternalContext().getUserPrincipal().getName(); 
		Usuario us = usuarioBC.findSpecificUser(usuario);
		Permiso permiso =permisoBC.getPermiso("roles modificar");
		return 	usuarioRolPermisoBC.tiene(permiso, us);
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}
}
