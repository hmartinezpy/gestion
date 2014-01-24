package py.com.ait.gestion.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import py.com.ait.gestion.domain.Rol;

@FacesConverter(forClass = Rol.class, value = "rolConverter")
public class RolConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

		Rol rol = null;
		if (arg2 != null && !arg2.equals("")) {
			rol = new Rol();
			rol.setRolId(Long.parseLong(arg2));
		}
		System.out.println("\n\n>>>ROL El objeto es: "+ rol);
		return rol;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		String rolId = "";
		if(arg2 != null && arg2 instanceof Rol) {
			
			rolId = ((Rol) arg2).getRolId().toString();
		}			
		//System.out.println("\n\n>>>ROL El id es: "+rolId);
		return rolId;
	}

}
