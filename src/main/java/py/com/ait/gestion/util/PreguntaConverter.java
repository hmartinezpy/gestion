package py.com.ait.gestion.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import py.com.ait.gestion.domain.Pregunta;

@FacesConverter(forClass = Pregunta.class, value = "preguntaConverter")
public class PreguntaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

		Pregunta pre = null;
		if (arg2 != null && !arg2.equals("")) {
			pre = new Pregunta();
			pre.setPreguntaId(Long.parseLong(arg2));
		}
		System.out.println("\n\n>>>PREGUNTA El objeto es: "+ pre);
		return pre;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		String preId = "";
		if (arg2 != null && arg2 instanceof Pregunta) {

			preId = ((Pregunta) arg2).getPreguntaId().toString();
		}
		//System.out.println("\n\n>>>PREGUNTA El id es: "+ preId);
		return preId;
	}
}
