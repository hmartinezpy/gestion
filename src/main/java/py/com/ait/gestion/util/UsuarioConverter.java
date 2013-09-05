package py.com.ait.gestion.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import py.com.ait.gestion.domain.Usuario;

@FacesConverter(forClass=Usuario.class, value="usuarioConverter")
public class UsuarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		
		Usuario usuario = null;
		if(arg2 != null && !arg2.equals("")) {
			usuario = new Usuario();
			usuario.setUsuarioId(Long.parseLong(arg2));
		}
		
		return usuario;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		
		String usuarioId = "";
		if(arg2 != null && arg2 instanceof Usuario) {
			
			usuarioId = ((Usuario) arg2).getUsuarioId().toString();
		}			
		return usuarioId;
	}
	
}
