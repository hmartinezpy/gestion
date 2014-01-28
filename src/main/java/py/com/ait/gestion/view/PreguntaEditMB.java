package py.com.ait.gestion.view;

import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;
import py.com.ait.gestion.business.PreguntaBC;
import py.com.ait.gestion.domain.Pregunta;

@ViewController
@NextView("/pg/pregunta_edit.xhtml")
@PreviousView("/pg/pregunta_list.xhtml")
public class PreguntaEditMB extends AbstractEditPageBean<Pregunta, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PreguntaBC preguntaBC;

	@Override
	public String delete() {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insert() {

		if (getBean().getDescripcion() != null
				&& getBean().getDescripcion().trim().length() != 0) {
			Pregunta pregunta = new Pregunta();
			pregunta.setDescripcion(getBean().getDescripcion());

			preguntaBC.registrar(pregunta);
			return getPreviousView();

		} else {
			agregarMensajeError("La descripción no puede estar vacía");
			return getNextView();
		}

	}

	@Inject
	private FacesContext facesContext;

	public void agregarMensaje(String mensaje) {

		this.facesContext.addMessage("suceso", new FacesMessage(mensaje));
	}

	public void agregarMensajeError(String mensaje) {

		this.facesContext.addMessage("error", new FacesMessage(
				FacesMessage.SEVERITY_ERROR, mensaje, null));
	}

	@Override
	public String update() {

		if (getBean().getDescripcion() != null
				&& getBean().getDescripcion().trim().length() != 0) {
			Pregunta pregunta = getBean();

			preguntaBC.editar(pregunta);

			return getPreviousView();
		} else {
			agregarMensajeError("La descripción no puede estar vacía");
			return getNextView();
		}
	}

	@Override
	protected void handleLoad() {

		setBean(this.preguntaBC.load(getId()));

	}

	@Override
	public Pregunta getBean() {

		Pregunta bean = super.getBean();
		return bean;
	}

}
