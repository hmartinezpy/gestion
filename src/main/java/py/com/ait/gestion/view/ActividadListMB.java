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

import py.com.ait.gestion.business.ActividadBC;
import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.CronogramaDetalle;


@ViewController
@NextView("/pg/actividad_edit.xhtml")
@PreviousView("/pg/actividad_list.xhtml")
public class ActividadListMB extends AbstractListPageBean<Actividad,Long> {


	private static final long serialVersionUID = 1L;

	@Inject 
	private FacesContext facesContext;
	
	@Inject
	private ActividadBC actividadBC;
	
	private List<Actividad> actividades;
	private Actividad actividadSeleccionado;
	
	
	public void elegirActividad(){
		Actividad actividad = actividadSeleccionado;		
		agregarMensaje("Actividad seleccionada: " + actividad.getDescripcion());		
	}
	
	public Actividad getActividadSeleccionado() {
		return actividadSeleccionado;
	}


	public void setActividadSeleccionado(Actividad actividadSeleccionado) {
		this.actividadSeleccionado = actividadSeleccionado;
	}


	public List<Actividad> getActividades() {
		actividades = actividadBC.listar();
		return actividades;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}
	
	public void eliminar(ActionEvent actionEvent) {
        actividadBC.eliminar(actividadSeleccionado.getActividadId());
        actividadSeleccionado = new Actividad();
        setActividades(actividadBC.listar());
        agregarMensaje("Actividad eliminado");
    }
	
	@Override
	protected List<Actividad> handleResultList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void agregarMensaje(String mensaje){
	    	facesContext.addMessage("suceso", new FacesMessage(mensaje));
	}

}
