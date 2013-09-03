package py.com.ait.gestion.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;

import py.com.ait.gestion.business.PermisoBC;
import py.com.ait.gestion.business.SesionLogBC;
import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.business.UsuarioRolPermisoBC;
import py.com.ait.gestion.domain.LogSesion;
import py.com.ait.gestion.domain.Permiso;
import py.com.ait.gestion.domain.Usuario;



@ViewController
public class SesionLogListMB extends AbstractListPageBean<LogSesion,Long> {
	
	private static final long serialVersionUID = 1L;
	private LazyDataModel<LogSesion> model;	

	private int pageSize = 15;

	@Inject private SesionLogBC sesionLogBC;
	@Inject private UsuarioBC usuarioBC;
	@Inject private PermisoBC permisoBC;
	@Inject private UsuarioRolPermisoBC usuarioRolPermisoBC;
	
	
	private String usuario;
	private String fechaIntento;
	private String ip;
	
	@PostConstruct
	public void loadLazyModel() {
		
		model = new LazyDataModel<LogSesion>() {
			private static final long serialVersionUID = 1L;

			public List<LogSesion> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, String> filters) {
				
				if(sortField == null) sortField = "logSesionId"; //default sort field
				
				List<LogSesion> logSesion = new ArrayList<LogSesion>();

				logSesion = sesionLogBC.findPage(pageSize, first, sortField, true, filters);
				
				return logSesion;
			}
		};
		
		model.setRowCount(sesionLogBC.count());
		model.setPageSize(pageSize);
		
	}
	

	public LazyDataModel<LogSesion> getModel() {
		return model;
	}
	public int getPageSize() {
		return pageSize;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setFechaIntento(String fechaIntento) {
		this.fechaIntento = fechaIntento;
	}


	public String getFechaIntento() {
		return fechaIntento;
	}


	@Override
	protected List<LogSesion> handleResultList() {
		// TODO Auto-generated method stub
		return null;
	}
		
	
	
	@SuppressWarnings("unused")
	private boolean ver;
	
	public boolean isVer() {
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		String usuario = facesContext.getExternalContext().getUserPrincipal().getName(); 
		Usuario us = usuarioBC.findSpecificUser(usuario);
		Permiso permiso = permisoBC.getPermiso("roles crear");
		return 	usuarioRolPermisoBC.tiene(permiso, us);
	}


	public void setVer(boolean ver) {
		this.ver = ver;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getIp() {
		return ip;
	}


	
}

