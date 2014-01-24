package py.com.ait.gestion.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import py.com.ait.gestion.domain.Checklist;
import py.com.ait.gestion.domain.Pregunta;

@FacesConverter(forClass = Checklist.class, value = "checklistConverter")
public class ChecklistConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

		Checklist check = null;
		if (arg2 != null && !arg2.equals("")) {
			check = new Checklist();
			check.setChecklistId(Long.parseLong(arg2));
		}
		System.out.println("\n\n>>>CHECKLIST El objeto es: "+check);
		return check;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		String chkId = "";
		if (arg2 != null && arg2 instanceof Checklist) {

			chkId = ((Checklist) arg2).getChecklistId().toString();
		}
		//System.out.println("\n\n>>>CHECKLIST El id es: "+chkId);
		return chkId;
	}
}
