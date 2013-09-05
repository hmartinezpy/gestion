package py.com.ait.gestion.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import py.com.ait.gestion.domain.Cronograma;

@FacesConverter(forClass=Cronograma.class, value="cronogramaConverter")
public class CronogramaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		
		Cronograma cronograma = null;
		if(arg2 != null && !arg2.equals("")) {
			cronograma = new Cronograma();
			cronograma.setCronogramaId(Long.parseLong(arg2));
		}
		
		return cronograma;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		
		String cronogramaId = "";
		if(arg2 != null && arg2 instanceof Cronograma) {
			
			cronogramaId = ((Cronograma) arg2).getCronogramaId().toString();
		}			
		return cronogramaId;
	}
	
}
