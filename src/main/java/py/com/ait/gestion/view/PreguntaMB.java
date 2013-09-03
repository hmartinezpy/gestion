

package py.com.ait.gestion.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.event.RowEditEvent;
import org.ticpy.tekoporu.stereotype.ViewController;

import py.com.ait.gestion.business.PreguntaBC;
import py.com.ait.gestion.domain.Pregunta;

@ViewController
public class PreguntaMB {
	
	@Inject 
	private FacesContext facesContext;
	
	@Inject
	private PreguntaBC preguntaBC;
	

	private Long idPregunta;
	private String descripcion;
	private Pregunta preguntaSeleccionado;
	private List<Pregunta> preguntas;
	
	
	public Long getIdPregunta() {
		return idPregunta;
	}

	public void setIdPregunta(Long idPregunta) {
		this.idPregunta = idPregunta;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	


	public List<Pregunta> getPreguntas() {
		preguntas = preguntaBC.listar();
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

	public Pregunta getPreguntaSeleccionado() {
		return preguntaSeleccionado;
	}

	public void setPreguntaSeleccionado(Pregunta preguntaSeleccionado) {
		this.preguntaSeleccionado = preguntaSeleccionado;
	}
	
	public void registrarPregunta(){
		Pregunta pregunta = new Pregunta();
		pregunta.setDescripcion(getDescripcion());
		preguntaBC.registrar(pregunta);
		agregarMensaje("Pregunta creada");
		this.limpiarCampos();
	}

	

	private void limpiarCampos() {
		this.setDescripcion("");
		
	}
	


	public void eliminar(ActionEvent actionEvent) {
		preguntaBC.eliminar(preguntaSeleccionado.getPreguntaId());
		preguntaSeleccionado = new Pregunta();
        setPreguntas(preguntaBC.listar());
        agregarMensaje("Pregunta eliminada");
    }
	
    public void onEdit(RowEditEvent event) {
    	Pregunta pregunta = ((Pregunta) event.getObject());
    	preguntaBC.editar(pregunta);
        agregarMensaje("Pregunta Modificada");
    }
    
    public void agregarMensaje(String mensaje){
    	facesContext.addMessage("suceso", new FacesMessage(mensaje));
    }
	
	
	
}
