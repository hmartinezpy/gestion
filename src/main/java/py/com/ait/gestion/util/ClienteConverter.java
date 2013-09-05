package py.com.ait.gestion.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import py.com.ait.gestion.domain.Cliente;

@FacesConverter(forClass=Cliente.class, value="clienteConverter")
public class ClienteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		
		Cliente cliente = null;
		if(arg2 != null && !arg2.equals("")) {
			cliente = new Cliente();
			cliente.setClienteId(Long.parseLong(arg2));
		}
		
		return cliente;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		
		String clienteId = "";
		if(arg2 != null && arg2 instanceof Cliente) {
			
			clienteId = ((Cliente) arg2).getClienteId().toString();
		}			
		return clienteId;
	}
	
}
