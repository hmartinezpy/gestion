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
import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.business.UsuarioRolPermisoBC;
import py.com.ait.gestion.domain.Permiso;
import py.com.ait.gestion.domain.Usuario;



@ViewController
@NextView("/admin/usuario_edit.xhtml")
@PreviousView("/admin/usuario_list.xhtml")
public class UsuarioListMB extends AbstractListPageBean<Usuario,Long> {
	
	private static final long serialVersionUID = 1L;
	private LazyDataModel<Usuario> model;	

	private int pageSize = 7;

	@Inject
	private UsuarioBC usuarioBC;
	
	@Inject 
	private PermisoBC permisoBC;
	
	@Inject
	private UsuarioRolPermisoBC usuarioRolPermisoBC;
	
	@PostConstruct
	public  void loadLazyModel(){
		model = new LazyDataModel<Usuario>() {
			private static final long serialVersionUID = 1L;
			public List<Usuario> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, String> filters) {
				if(sortField == null) sortField = "usuarioId"; //default sort field
				
				List<Usuario> usuario = new ArrayList<Usuario>();
				
				usuario = usuarioBC.findPage(pageSize, first, sortField, true);
				
				return usuario;
			}
		};
		
		model.setRowCount(usuarioBC.count());
		model.setPageSize(pageSize);
	}
	
	
	
	@Override
	protected List<Usuario> handleResultList() {
		return this.usuarioBC.findAll();
	}
	
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);

			if (delete) {
				usuarioBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}
 

	
	
	public LazyDataModel<Usuario> getModel() {
		return model;
	}
	public int getPageSize() {
		return pageSize;
	}
	
	//permisos
	
	private boolean editar;
	private boolean crear;
	private boolean ver;
	
	
	
	public boolean isVer() {
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		String usuario = facesContext.getExternalContext().getUserPrincipal().getName(); 
		Usuario us = usuarioBC.findSpecificUser(usuario);
		Permiso permiso =permisoBC.getPermiso("usuarios ver listado");
		return 	usuarioRolPermisoBC.tiene(permiso, us);
	}



	public void setVer(boolean ver) {
		this.ver = ver;
	}



	public boolean isCrear() {
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		String usuario = facesContext.getExternalContext().getUserPrincipal().getName(); 
		Usuario us = usuarioBC.findSpecificUser(usuario);
		Permiso permiso =permisoBC.getPermiso("usuarios crear");
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
		Permiso permiso =permisoBC.getPermiso("usuarios modificar");
		return 	usuarioRolPermisoBC.tiene(permiso, us);
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

}

