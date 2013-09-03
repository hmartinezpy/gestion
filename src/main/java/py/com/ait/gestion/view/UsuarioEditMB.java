package py.com.ait.gestion.view;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.exception.ExceptionHandler;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;
import org.ticpy.tekoporu.transaction.Transactional;

import py.com.ait.gestion.business.PermisoBC;
import py.com.ait.gestion.business.RolBC;
import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.business.UsuarioPasswordBC;
import py.com.ait.gestion.business.UsuarioRolPermisoBC;
import py.com.ait.gestion.domain.Permiso;
import py.com.ait.gestion.domain.Rol;
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.domain.UsuarioPassword;
import py.com.ait.gestion.domain.UsuarioRolPermiso;
import py.com.ait.gestion.util.HashUtils;


@ViewController
@PreviousView("/admin/usuario_list.xhtml")
@NextView("/admin/usuario_edit.xhtml")
public class UsuarioEditMB extends AbstractEditPageBean<Usuario,Long>{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ResourceBundle bundle;
	
	@Inject
	@SuppressWarnings("unused")
	private Logger logger;
	
	@Inject
	private UsuarioBC usuarioBC;
	
	@Inject
	UsuarioPasswordBC usuarioPasswordBC;
	
	@Inject
	RolBC rolBC;
	
	@Inject
	PermisoBC permisoBC;
	
	@Inject
	UsuarioRolPermisoBC usuarioRolPermisoBC;
	
	private long usuarioId;
	private String usuario;
	private String nombre;
	private String apellido;
	private String estado;
	private String email;
	private String usuarioActual;
	
	
	private String password;
	private String bienvenida;
	private String lectura = "false";
	
	private List<UsuarioPassword> usuarioPasswordList;
	
	//Listado de Roles 
	private List<Rol> rolesAsociadosList;
	private List<Rol> rolesDisponiblesList;
	
	//Listado de Nombre de roles
	private List<String> listadoRolesDisponibles;
	private List<String> listadoRolesAsociados;
	//Lista dual para el pick list
	private DualListModel<String> listaDual;
	
	//Listado de Permisos
	private List<Permiso> permisoAsociadosList;
	private List<Permiso> permisoDisponiblesList;
	//Listado de Nombres de permisos
	private List<String> listadoPermisoDisponibles;
	private List<String> listadoPermisoAsociados;
	//Lista dual para el pick list
	private DualListModel<String> listaDualPermiso;
	
	//manejo de permisos
	public List<String> getListadoPermisoDisponibles() {
		
		this.listadoPermisoDisponibles = new ArrayList<String>();
		Iterator<Permiso> it = this.getPermisosDisponiblesList().iterator();
		
		while(it.hasNext())
		{
			Permiso r = it.next();
			listadoPermisoDisponibles.add(r.getDescripcion());
		}
		
		return listadoPermisoDisponibles;
	}
	
	public void setListadoPermisoDisponibles(List<String> listadoPermisoDisponibles) {
		this.listadoPermisoDisponibles = listadoPermisoDisponibles;
	}
	public List<String> getListadoPermisosAsociados() {
		 this.listadoPermisoAsociados= new ArrayList<String>();
		Iterator<Permiso> it = this.getPermisosAsociadosList().iterator();
		
		while(it.hasNext())
		{
			
			Permiso r = it.next();
			if(r!=null)
			{
				listadoPermisoAsociados.add(r.getDescripcion());
			}
		}
		
		return listadoPermisoAsociados;
	}
	public void setListadoPermisosAsociados(List<String> listadoPermisoAsociados) {
		this.listadoPermisoAsociados = listadoPermisoAsociados;
	}
	public DualListModel<String> getListaDualPermiso() {
		
		listaDualPermiso = new DualListModel<String>(this.getListadoPermisoDisponibles(), this.getListadoPermisosAsociados());
		return listaDualPermiso;
	}

	public void setListaDualPermiso(DualListModel<String> listaDualPermiso) {
		this.listaDualPermiso = listaDualPermiso;
	}
	public List<Permiso> getPermisosDisponiblesList() {
		Usuario us = getBean();
		permisoDisponiblesList=usuarioRolPermisoBC.findPermisosDisponibles(us);
		return permisoDisponiblesList;
	}

	public void setPermisosDisponiblesList(List<Permiso> permisoDisponiblesList) {
		this.permisoDisponiblesList = permisoDisponiblesList;
	}

	public List<Permiso> getPermisosAsociadosList() {
		Usuario us = getBean();
		permisoAsociadosList=usuarioRolPermisoBC.findPermiso(us);
		return permisoAsociadosList;
	}

	public void setPermisosAsociadosList(List<Permiso> permisoAsociadosList) {
		this.permisoAsociadosList = permisoAsociadosList;
	}
	//manejo de roles
	public List<String> getListadoRolesDisponibles() {
		
		this.listadoRolesDisponibles = new ArrayList<String>();
		Iterator<Rol> it = this.getRolesDisponiblesList().iterator();
		
		while(it.hasNext())
		{
			Rol r = it.next();
			listadoRolesDisponibles.add(r.getDescripcion());
		}
		
		return listadoRolesDisponibles;
	}

	public void setListadoRolesDisponibles(List<String> listadoRolesDisponibles) {
		this.listadoRolesDisponibles = listadoRolesDisponibles;
	}

	public List<String> getListadoRolesAsociados() {
		 this.listadoRolesAsociados= new ArrayList<String>();
		Iterator<Rol> it = this.getRolesAsociadosList().iterator();
		
		while(it.hasNext())
		{
			Rol r = it.next();
			if(r!=null)
			{
				listadoRolesAsociados.add(r.getDescripcion());
			}
		}
		
		return listadoRolesAsociados;
	}

	public void setListadoRolesAsociados(List<String> listadoRolesAsociados) {
		this.listadoRolesAsociados = listadoRolesAsociados;
	}
	
	public DualListModel<String> getListaDual() {
		
		listaDual = new DualListModel<String>(this.getListadoRolesDisponibles(), this.getListadoRolesAsociados());
		return listaDual;
	}

	public void setListaDual(DualListModel<String> listaDual) {
		this.listaDual = listaDual;
	}

	public List<Rol> getRolesDisponiblesList() {
		Usuario us = getBean();
		rolesDisponiblesList=usuarioRolPermisoBC.findRolesDisponibles(us);
		return rolesDisponiblesList;
	}

	public void setRolesDisponiblesList(List<Rol> rolesDisponiblesList) {
		this.rolesDisponiblesList = rolesDisponiblesList;
	}

	public List<Rol> getRolesAsociadosList() {
		Usuario us = getBean();
		rolesAsociadosList=usuarioRolPermisoBC.findRoles(us);
		return rolesAsociadosList;
	}

	public void setRolesAsociadosList(List<Rol> rolesList) {
		this.rolesAsociadosList = rolesList;
	}

	
	//Bienvenida
	
	public String getBienvenida() {
		
		try {
		
			String usuario = usuarioBC.getUsuarioActual();
			Usuario us = usuarioBC.findSpecificUser(usuario);
			bienvenida = us.getNombre()+" "+us.getApellido() + ", Bienvenido al Sistema Ruvig";
			
		} catch(Exception ex) {
			
			bienvenida = "";
			logger.error("Error mostrando texto de bienvenida...", ex);
		}
		
		return bienvenida;
	}
	
	public void setUsuarioActual(String usuarioActual) {
		this.usuarioActual = usuarioActual;
	}
	public String getUsuarioActual()
	{
		 
		
		this.usuarioActual = usuarioBC.getUsuarioActual(); 
		return usuarioActual;
	}
	
	
	public void setBienvenida(String bienvenida) {
		this.bienvenida = bienvenida;
	}
	public String getLectura() {
		return lectura;
	}

	public void setLectura(String lectura) {
		this.lectura = lectura;
	}


	// Desde aqui manejo de usuario
	@Override
	@Transactional
	public String delete() {
		this.usuarioBC.delete(getId());
		return getPreviousView();
	}
	
	
	@Override
	@Transactional
	public String update() {
		this.usuarioBC.update(getBean());
		UsuarioPassword usuarioPassword = usuarioPasswordBC.findPaswwordByUser(getBean());
		if(!password.equals(""))
		{
			usuarioPassword.setPassword( HashUtils.md5(password));
		}
		
		usuarioPasswordBC.update(usuarioPassword);
		guardarRoles();
		guardarPermisos();
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		setBean(this.usuarioBC.load(getId()));		
			}
	
	@Override
	public Usuario getBean() {
		Usuario bean = super.getBean();
		return bean;
	}
	
//	@Transactional
//	public String checkSave() throws ConstraintViolation {
//		
//		Usuario usuario = getBean();
//		String view;
//		if(isUpdateMode()) {
//			view = update();
//		} else {
//			view = insert();
//		}
//		
//		return view;
//	}
	
	
	@ExceptionHandler
	public void tratar(ConstraintViolation e) {
		logger.error(e.getMessage());
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("codigo.error"),  null);  
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	@Override
	@Transactional
	public String insert() {
		
		Usuario usuario = new Usuario();
		if(usuarioBC.checkUnique(getBean().getUsuario()))
		{
			//se crea la entidad para el usuario
			usuario.setUsuario(getBean().getUsuario());
			usuario.setNombre(getBean().getNombre());
			usuario.setApellido(getBean().getApellido());
			usuario.setEmail(getBean().getEmail());
			usuario.setEstado(getBean().getEstado());
			
			this.usuarioBC.insert(usuario);
			UsuarioPassword usuarioPassword = new UsuarioPassword();
			if(!password.equals(""))
			{
				usuarioPassword.setPassword( HashUtils.md5(password));
			}else
			{
				usuarioPassword.setPassword(HashUtils.md5(""));
			}
			
			usuarioPassword.setUsuario(usuario);
			this.usuarioPasswordBC.insert(usuarioPassword);
			
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("data.save"),  null);  
			FacesContext.getCurrentInstance().addMessage(null, message);
			
			return getPreviousView();
		}else
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("usuario.duplicado"),null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "";
		}
	}
	
	public void setUsuarioId(long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public long getUsuarioId() {
		return usuarioId;
	}
	
	


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getNombre() {
		return nombre;
	}



	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}



	public String getUsuario() {
		return usuario;
	}



	public void setApellido(String apellido) {
		this.apellido = apellido;
	}



	public String getApellido() {
		return apellido;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public String getEstado() {
		return estado;
	}



	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	//Manejo de roles
	public void guardarRoles()
	{
		//verificar los nuevos
		Iterator<String> nuevo = this.listaDual.getTarget().iterator();
		
		while (nuevo.hasNext())
		{
			Boolean existe = false;
			String ins= nuevo.next();
			List<String> lista = this.getListadoRolesAsociados();
			Iterator<String> original = lista.iterator();
			while(original.hasNext())
			{
				if(original.next().equals(ins))
				{
					existe = true;
					
				}
			}
			if(!existe)
			{
				Rol rol = rolBC.getRol(ins);
				if(rol!=null)
				{
					UsuarioRolPermiso urp = new UsuarioRolPermiso();
					urp.setRol(rol);
					urp.setUsuario(getBean());
					usuarioRolPermisoBC.insert(urp);
				}
				
				
			}
		}
		//se buscan los borrados
		Iterator<String> original2 = this.getListadoRolesAsociados().iterator();
		List<String> lista = this.listaDual.getTarget();
		
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
				
				Usuario u = getBean();
				
				List<UsuarioRolPermiso> urList = usuarioRolPermisoBC.findRolesPermisosbyUser(u);
				Iterator<UsuarioRolPermiso> it= urList.iterator();
				while(it.hasNext())
				{
					UsuarioRolPermiso urp = it.next();
					if(urp.getRol()!=null)
					{
						if(urp.getRol().getDescripcion().equals(dl))
						{
							
							usuarioRolPermisoBC.delete(urp.getUsuarioRolPermisoId());
						}
					}
				}
				
			}
			
		}
		
		
	}
	//Manejo de permisos
	public void guardarPermisos()
	{
		//verificar los nuevos
		Iterator<String> nuevo = this.listaDualPermiso.getTarget().iterator();
		
		while (nuevo.hasNext())
		{
			Boolean existe = false;
			String ins= nuevo.next();
			List<String> lista = this.getListadoPermisosAsociados();
			Iterator<String> original = lista.iterator();
			while(original.hasNext())
			{
				if(original.next().equals(ins))
				{
					existe = true;
					
				}
			}
			if(!existe)
			{
				Permiso permiso = permisoBC.getPermiso(ins);
				if(permiso!=null)
				{
					UsuarioRolPermiso urp = new UsuarioRolPermiso();
					urp.setPermiso(permiso);
					urp.setUsuario(getBean());
					usuarioRolPermisoBC.insert(urp);
				}
				
				
			}
		}
		//se buscan los borrados
		Iterator<String> original2 = this.getListadoPermisosAsociados().iterator();
		List<String> lista = this.listaDualPermiso.getTarget();
		
		while(original2.hasNext())
		{
			Boolean existe2 = false;
			Iterator<String> nuevo2 = this.listaDualPermiso.getTarget().iterator();
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
				
				Usuario u = getBean();
				
				List<UsuarioRolPermiso> urList = usuarioRolPermisoBC.findRolesPermisosbyUser(u);
				Iterator<UsuarioRolPermiso> it= urList.iterator();
				while(it.hasNext())
				{
					UsuarioRolPermiso urp = it.next();
					if(urp.getPermiso()!=null)
					{
						if(urp.getPermiso().getDescripcion().equals(dl))
						{
							
							usuarioRolPermisoBC.delete(urp.getUsuarioRolPermisoId());
						}
					}
				}
				
			}
			
		}
		
		
	}
private boolean modificarRoles;
private boolean modificarPermisos;	
	
	public boolean isModificarPermisos() {
		String usuario = usuarioBC.getUsuarioActual(); 
		Usuario us = usuarioBC.findSpecificUser(usuario);
		Permiso permiso =permisoBC.getPermiso("usuarios modificar permisos");
		return 	usuarioRolPermisoBC.tiene(permiso, us);
}

public void setModificarPermisos(boolean modificarPermisos) {
	this.modificarPermisos = modificarPermisos;
}

	public boolean isModificarRoles() {
		String usuario = usuarioBC.getUsuarioActual(); 
		Usuario us = usuarioBC.findSpecificUser(usuario);
		Permiso permiso =permisoBC.getPermiso("usuarios modificar roles");
		return 	usuarioRolPermisoBC.tiene(permiso, us);
	}

	public void setModificarRoles(boolean modificarRoles) {
		this.modificarRoles = modificarRoles;
	}

	
}
