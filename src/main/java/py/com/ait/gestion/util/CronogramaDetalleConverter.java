package py.com.ait.gestion.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import py.com.ait.gestion.domain.CronogramaDetalle;

@FacesConverter(forClass = CronogramaDetalle.class, value = "cronogramaDetalleConverter")
public class CronogramaDetalleConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

		CronogramaDetalle crod = null;
		if (arg2 != null && !arg2.equals("")) {
			crod = new CronogramaDetalle();
			crod.setCronogramaDetalleId(Long.parseLong(arg2));
		}
		System.out.println("\n\n>>>CRONODETALLE El objeto es: "+crod);
		return crod;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		String croId = "";
		if(arg2 != null && arg2 instanceof CronogramaDetalle) {
			
			croId = ((CronogramaDetalle) arg2).getCronogramaDetalleId().toString();
		}			
		//System.out.println("\n\n>>>CRONODETALLE El id es: "+croId);
		return croId;
	}

}
