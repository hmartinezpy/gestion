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

import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.business.UsuarioRolPermisoBC;
import py.com.ait.gestion.domain.Rol;
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.domain.UsuarioRolPermiso;

import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;
import org.ticpy.tekoporu.transaction.Transactional;

@ViewController

public class UsuarioRolPermisolListMB extends AbstractListPageBean<UsuarioRolPermiso, Long>{

	private static final long serialVersionUID = 1L;
	
	private LazyDataModel<Rol> model;	
	public LazyDataModel<Rol> getModel() {
		return model;
	}


	public int getPageSize() {
		return pageSize;
	}

	private int pageSize = 3;
	private Usuario usuario;
	@Inject
	private UsuarioRolPermisoBC usuarioRolPermisoBC;
	@Inject
	private UsuarioBC usuarioBC;
	
	@PostConstruct
	public void loadLazyModel() {
		 FacesContext facesContext = FacesContext.getCurrentInstance(); 
		 String user = facesContext.getExternalContext().getUserPrincipal().getName(); 
		 usuario= usuarioBC.findSpecificUser(user);
		model = new LazyDataModel<Rol>() {
			private static final long serialVersionUID = 1L;
			public List<Rol> load(int arg0, int arg1, String arg2,
					SortOrder arg3, Map<String, String> arg4) {
				List<Rol> rol = new ArrayList<Rol>();
				rol = usuarioRolPermisoBC.findRoles(usuario);
				return rol;
			}
		};
		
		model.setRowCount(usuarioRolPermisoBC.count());
		model.setPageSize(pageSize);
		
	}
	

	@Override
	protected List<UsuarioRolPermiso> handleResultList() {
		return this.usuarioRolPermisoBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);

			if (delete) {
				usuarioRolPermisoBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}
	
}
