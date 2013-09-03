package py.com.ait.gestion.view;



import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;
import org.ticpy.tekoporu.transaction.Transactional;

import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.business.UsuarioPasswordBC;
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.domain.UsuarioPassword;
import py.com.ait.gestion.util.HashUtils;

@ViewController
@NextView("/pg/home.xhtml")
@PreviousView("/perfil/usuario_pass.xhtml")
public class UsuarioPasswordMB extends AbstractEditPageBean<UsuarioPassword,Long>{

	private static final long serialVersionUID = 1L;
	
	@Inject private Usuario usuario;
	@Inject private UsuarioPasswordBC usuarioPasswordBC;
	@Inject private UsuarioBC usuarioBC;
	@Inject private UsuarioPassword usuarioPassword; 
	@Inject private Logger logger;
	private String passwordNew;
	private String oldPassword;
    private String passwordNew2;

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public String getPasswordNew() {
		return passwordNew;
	}


	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}


	public void setPasswordNew2(String passwordNew2) {
		this.passwordNew2 = passwordNew2;
	}

	public String getPasswordNew2() {
		return passwordNew2;
	}

	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String insert() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	@Transactional
	public String update() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		 String user = facesContext.getExternalContext().getUserPrincipal().getName();
		 usuario = usuarioBC.findSpecificUser(user);
		 usuarioPassword = usuarioPasswordBC.findPaswwordByUser(usuario);
		 
		 if(passwordNew.equals(passwordNew2)){
			 logger.debug("Password anterior: "+oldPassword);
			 logger.debug("Password nuevo: "+passwordNew2);
			 if(usuarioPassword.getPassword().equals(HashUtils.md5(getOldPassword()))){
				if(!passwordNew.equals(""))
				{
					usuarioPassword.setPassword(HashUtils.md5(passwordNew));
					usuarioPasswordBC.update(usuarioPassword);
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Cambio de contraseña exitoso!","Cambio de contraseña exitoso!");  
					FacesContext.getCurrentInstance().addMessage(null, message);
					return getNextView();
				}
			}else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"BD="+usuarioPassword.getPassword()+"  "+"Meti"+HashUtils.md5(getOldPassword()),"");  
				FacesContext.getCurrentInstance().addMessage(null, message);
				this.oldPassword = "";
				this.passwordNew = "";
				this.passwordNew2 = "";
				return getPreviousView();
			}
		 }
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: Las contraseñas nuevas no coinciden!","");  
			FacesContext.getCurrentInstance().addMessage(null, message);
			this.oldPassword = "";
			this.passwordNew = "";
			this.passwordNew2 = "";
	        return getPreviousView();
	}
	@Override
	protected void handleLoad() {
		// TODO Auto-generated method stub
		
	}


	
}
