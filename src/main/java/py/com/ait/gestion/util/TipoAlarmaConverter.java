package py.com.ait.gestion.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import py.com.ait.gestion.domain.TipoAlarma;

@FacesConverter(forClass = TipoAlarma.class, value="tipoAlarmaConverter")
public class TipoAlarmaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

		TipoAlarma tipoAlarma =null;
		if (arg2 != null && !arg2.equals("")) {
			tipoAlarma = new TipoAlarma();
			tipoAlarma.setTipoAlarmaId(Long.parseLong(arg2));
		}
		System.out.println("\n\n>>>ALARMA El objeto es: "+tipoAlarma);
		return tipoAlarma;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		String alarmId = "";
		if(arg2 != null && arg2 instanceof TipoAlarma){
			alarmId = ((TipoAlarma) arg2).getTipoAlarmaId().toString();
		}
		//System.out.println("\n\n>>>ALARMA El id es: "+alarmId);
		return alarmId;
	}

}
