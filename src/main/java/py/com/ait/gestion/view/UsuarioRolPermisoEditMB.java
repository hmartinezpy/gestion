package py.com.ait.gestion.view;



import java.util.ResourceBundle;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;

import py.com.ait.gestion.business.UsuarioRolPermisoBC;
import py.com.ait.gestion.domain.UsuarioRolPermiso;

@ViewController
@PreviousView("/admin/usuario_edit.xhtml")
public class UsuarioRolPermisoEditMB extends AbstractEditPageBean<UsuarioRolPermiso,Long>{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ResourceBundle bundle;
	
	@Inject
	@SuppressWarnings("unused")
	private Logger logger;
	
	@Inject
	private UsuarioRolPermisoBC usuarioRolPermisoBC;
	
	
	
	
	
	
	//metodos sobre escritos
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
	public String update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleLoad() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
