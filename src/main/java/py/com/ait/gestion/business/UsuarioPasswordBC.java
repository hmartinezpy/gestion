package py.com.ait.gestion.business;

import javax.inject.Inject;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;

import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.domain.UsuarioPassword;
import py.com.ait.gestion.persistence.UsuarioDAO;
import py.com.ait.gestion.persistence.UsuarioPasswordDAO;


@BusinessController
public class UsuarioPasswordBC extends DelegateCrud<UsuarioPassword, Long, UsuarioPasswordDAO> {
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private UsuarioPasswordDAO usuarioPasswordDAO;
	
	@Inject 
	private UsuarioDAO usuarioDAO;
	
	
	public UsuarioPassword findPaswwordByUser(Usuario user){
		return usuarioPasswordDAO.findPaswwordByUser(user);
	}
	
	
	public  boolean checkPassword(String user,String password)
	{
		Usuario u = usuarioDAO.findSpecificUser(user);
		
		UsuarioPassword up = usuarioPasswordDAO.findPaswwordByUser(u);
		boolean check = true;
		if(up.getPassword().equals(password))
		{
			check = true;
		}
		
		return check;
	}
	@Override
	@Transactional
	public void insert(UsuarioPassword usuarioPassword) {
		getDelegate().insert(usuarioPassword);
	}
	
}
