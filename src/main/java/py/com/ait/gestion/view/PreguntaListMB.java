package py.com.ait.gestion.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;

import py.com.ait.gestion.business.PreguntaBC;
import py.com.ait.gestion.domain.Pregunta;


@ViewController
@NextView("/pg/pregunta_edit.xhtml")
@PreviousView("/pg/pregunta_list.xhtml")
public class PreguntaListMB extends AbstractListPageBean<Pregunta,Long> {


	private static final long serialVersionUID = 1L;	
	
	@Inject 
	private FacesContext facesContext;
	
	@Inject
	private PreguntaBC preguntaBC;
	
	private List<Pregunta> preguntas;
	private Pregunta preguntaSeleccionado;
	
	
	
	public Pregunta getPreguntaSeleccionado() {
		return preguntaSeleccionado;
	}


	public void setPreguntaSeleccionado(Pregunta preguntaSeleccionado) {
		this.preguntaSeleccionado = preguntaSeleccionado;
	}


	public List<Pregunta> getPreguntas() {
		preguntas = preguntaBC.listar();
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}
	
	public void eliminar(ActionEvent actionEvent) {
		try {
			preguntaBC.eliminar(preguntaSeleccionado.getPreguntaId());
			preguntaSeleccionado = new Pregunta();
	        setPreguntas(preguntaBC.listar());
	        agregarMensaje("Pregunta eliminada");
		} catch (Exception e) {
			e.printStackTrace();
			agregarMensajeError("No se puede eliminar el registro, el mismo está siendo utilizado por otros datos del sistema");
		}
		
    }
	
	@Override
	protected List<Pregunta> handleResultList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void agregarMensaje(String mensaje){
	    	facesContext.addMessage("suceso", new FacesMessage(mensaje));
	}
	public void agregarMensajeError(String mensaje) {

		this.facesContext.addMessage("error", new FacesMessage(
				FacesMessage.SEVERITY_ERROR, mensaje, null));
	}

}
