package py.com.ait.gestion.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import py.com.ait.gestion.business.PermisoBC;
import py.com.ait.gestion.business.RolBC;
import py.com.ait.gestion.business.RolPermisoBC;
import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.business.UsuarioRolPermisoBC;
import py.com.ait.gestion.domain.Permiso;
import py.com.ait.gestion.domain.Rol;
import py.com.ait.gestion.domain.RolPermiso;
import py.com.ait.gestion.domain.Usuario;

import org.primefaces.model.DualListModel;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;
import org.ticpy.tekoporu.transaction.Transactional;

@ViewController
@PreviousView("/admin/rol_list.xhtml")
public class RolEditMB extends AbstractEditPageBean<Rol, Long> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ResourceBundle bundle;
	
	//variables privadas
	private List<RolPermiso> rolesPermisosDisponiblesList;
	private List<RolPermiso> rolesPermisosAsociadosList;
	private DualListModel<String> listaDual;
	List<String> origenList;  
	List<String> destinoList;
	private String mensaje;
	private DualListModel<String> cities;
	private List<String> selectedOptions;
	

	public List<String> getSelectedOptions() {
		return selectedOptions;
	}

	public void setSelectedOptions(List<String> selectedOptions) {
		this.selectedOptions = selectedOptions;
	}

	public DualListModel<String> getCities() {
		
		 //Cities  
        List<String> citiesSource = new ArrayList<String>();  
        List<String> citiesTarget = new ArrayList<String>();  
  
        citiesSource.add("Tipo Violencia");  
        citiesSource.add("Relación");  
        citiesSource.add("Departamento");  
        citiesSource.add("Distrito");  
        citiesSource.add("Institución");  
        citiesSource.add("Sexo"); 
        cities = new DualListModel<String>(citiesSource, citiesTarget); 
		return cities;
	}

	public void setCities(DualListModel<String> cities) {
		this.cities = cities;
	}

	public String getMensaje() {
		return mensaje= "Recuerde Guardar los Cambios!";
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	//injects
	@Inject
	private RolBC rolBC;
	
	@Inject 
	private RolPermisoBC rolPermisoBC;
	
	@Inject
	private PermisoBC permisoBC;
	
	@Inject
	private UsuarioRolPermisoBC usuarioRolPermisoBC;
	
	@Inject
	private UsuarioBC usuarioBC;
	@Override
	@Transactional
	public String delete() {
		this.rolBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		Rol rol = getBean();
		if(rolBC.esUnica(rol))
		{
			this.rolBC.insert(rol);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("data.save"),  null);  
			FacesContext.getCurrentInstance().addMessage(null, message);
			return getPreviousView();
		}else
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rol ya existente",  null);  
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "";
		}
		
	}

	@Override
	@Transactional
	public String update() {
		Rol rol = getBean();
		
		if(rolBC.esUnica(rol))
		{
			this.rolBC.update(rol);
			this.guardarPermisos();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("data.save"),  null);  
			FacesContext.getCurrentInstance().addMessage(null, message);
			return getPreviousView();
		}else
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rol ya existente",  null);  
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "";
		}
		
		
	}

	@Override
	protected void handleLoad() {
		setBean(this.rolBC.load(getId()));
	}
	
	
	//getters and setters
		
	public List<RolPermiso> getRolesPermisosDisponiblesList() {
		return rolPermisoBC.findAll();
	}

	public void setRolesPermisosDisponiblesList(
			List<RolPermiso> rolesPermisosDisponiblesList) {
		this.rolesPermisosDisponiblesList = rolesPermisosDisponiblesList;
	}

	public List<RolPermiso> getRolesPermisosAsociadosList() {
		return rolPermisoBC.getPermisosPorRol(getBean());
	}

	public void setRolesPermisosAsociadosList(
			List<RolPermiso> rolesPermisosAsociadosList) {
		this.rolesPermisosAsociadosList = rolesPermisosAsociadosList;
	}
	public DualListModel<String> getListaDual() {
		 this.setDestinoList(getDestinoList());
		 this.setOrigenList(getOrigenList());
		 this.listaDual= new DualListModel<String>(this.origenList,this.destinoList);
		 return listaDual;
	}

	public void setListaDual(DualListModel<String> listaDual) {
		this.listaDual = listaDual;
	}
	public List<String> getOrigenList() {
		Rol rol = getBean();
		List<String> rl = new ArrayList<String>();
		if(rol!=null)
		{
			List<Permiso> rp = rolPermisoBC.getPermisoNoAsociados(rol);
			Iterator<Permiso> iter = rp.iterator();
			
			while(iter.hasNext()) 
			{
				Permiso r= iter.next();
				rl.add(r.getDescripcion());
			}
		}
		this.origenList=rl;
		return origenList;
	}

	public void setOrigenList(List<String> origenList) {
		this.origenList = origenList;
	}

	public List<String> getDestinoList() {
		List<RolPermiso> rp = this.getRolesPermisosAsociadosList();
		Iterator<RolPermiso> iter = rp.iterator();
		List<String> rl = new ArrayList<String>();
		while(iter.hasNext()) 
		{
			RolPermiso r= iter.next();
			rl.add(r.getPermiso().getDescripcion());
		}
		this.destinoList = rl;
		return destinoList;
	}

	public void setDestinoList(List<String> destinoList) {
		this.destinoList = destinoList;
	}
	public void guardarPermisos()
	{
		//verificar los nuevos
		Iterator<String> nuevo = this.listaDual.getTarget().iterator();
		
		while (nuevo.hasNext())
		{
			Boolean existe = false;
			String ins= nuevo.next();
			Iterator<String> original = this.destinoList.iterator();
			while(original.hasNext())
			{
				if(original.next().equals(ins))
				{
					existe = true;
					
				}
			}
			if(!existe)
			{
				Permiso p = permisoBC.getPermiso(ins);
				RolPermiso rp = new RolPermiso();
				rp.setPermiso(p);
				rp.setRol(getBean());
				rolPermisoBC.insert(rp);
			}
		}
		//se buscan los borrados
		Iterator<String> original2 = this.destinoList.iterator();
		while(original2.hasNext())
		{
			Boolean existe2 = false;
			Iterator<String> nuevo2 = this.listaDual.getTarget().iterator();
			String dl= original2.next();
			
			while(nuevo2.hasNext())
			{
				if(dl.equals(nuevo2.next()))
				{
					existe2=true;
				}
			}
			if(!existe2)
			{
				
				Rol r = getBean();
				
				List<RolPermiso> rpList = rolPermisoBC.getPermisosPorRol(r);
				Iterator<RolPermiso> it= rpList.iterator();
				while(it.hasNext())
				{
					RolPermiso rp = it.next();
					if(rp.getPermiso().getDescripcion().equals(dl))
					{
						
						rolPermisoBC.delete(rp.getRolPermisoId());
					}
				}
				
			}
		}
		
		
	}
private boolean modificarPermisos;	
	
	public boolean isModificarPermisos() {
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		String usuario = facesContext.getExternalContext().getUserPrincipal().getName(); 
		Usuario us = usuarioBC.findSpecificUser(usuario);
		Permiso permiso =permisoBC.getPermiso("roles modificar permisos");
		return 	usuarioRolPermisoBC.tiene(permiso, us);
}

public void setModificarPermisos(boolean modificarPermisos) {
	this.modificarPermisos = modificarPermisos;
}
}

