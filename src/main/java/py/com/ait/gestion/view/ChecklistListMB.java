package py.com.ait.gestion.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;

import py.com.ait.gestion.business.ChecklistBC;
import py.com.ait.gestion.business.ChecklistDetalleBC;
import py.com.ait.gestion.business.CronogramaDetalleBC;
import py.com.ait.gestion.business.PreguntaBC;
import py.com.ait.gestion.business.RolBC;
import py.com.ait.gestion.business.TipoAlarmaBC;
import py.com.ait.gestion.domain.Checklist;
import py.com.ait.gestion.domain.ChecklistDetalle;
import py.com.ait.gestion.domain.Cronograma;
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.domain.Pregunta;
import py.com.ait.gestion.domain.Rol;
import py.com.ait.gestion.domain.TipoAlarma;

@ViewController
@NextView("/pg/checklist_edit.xhtml")
@PreviousView("/pg/checklist_list.xhtml")
public class ChecklistListMB extends AbstractListPageBean<Checklist,Long> {


	private static final long serialVersionUID = 1L;

	@Inject 
	private FacesContext facesContext;
	
	@Inject
	private ChecklistBC checklistBC;
	
	private List<Checklist> checklists;
	private List<ChecklistDetalle> detalles;
	private Checklist checklistSeleccionado;
	
	private ChecklistDetalle detalleSeleccionado;
	
	
	public Checklist getChecklistSeleccionado() {
		return checklistSeleccionado;
	}


	public void setChecklistSeleccionado(Checklist checklistSeleccionado) {
		this.checklistSeleccionado = checklistSeleccionado;
	}

	public ChecklistDetalle getDetalleSeleccionado() {
		return detalleSeleccionado;
	}


	public void setDetalleSeleccionado(ChecklistDetalle detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

	public List<Checklist> getChecklists() {
		checklists = checklistBC.listar();
		return checklists;
	}

	public void setChecklists(List<Checklist> checklists) {
		this.checklists = checklists;
	}
	
	public List<ChecklistDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<ChecklistDetalle> detalles) {
		this.detalles = detalles;
	}
	
	public void eliminar(ActionEvent actionEvent) {
		checklistBC.eliminar(checklistSeleccionado.getChecklistId());
		checklistSeleccionado = new Checklist();
        setChecklists(checklistBC.listar());
        agregarMensaje("Checklist eliminado");
    }
	
	public void elegirChecklist(){
		setDetalles(checklistSeleccionado.getChecklistDetalles());
		String nombreChecklist = checklistSeleccionado.getDescripcion();
		detalleSeleccionado = new ChecklistDetalle();
		agregarMensaje("Checklist seleccionado: " + nombreChecklist);
		
	}
	
	public void elegirDetalle(){
		String nombreDetalle = detalleSeleccionado.getDescripcion();
		agregarMensaje("Detalle seleccionado: " + nombreDetalle);
		
	}
	
	
	@Override
	protected List<Checklist> handleResultList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void agregarMensaje(String mensaje){
	    	facesContext.addMessage("suceso", new FacesMessage(mensaje));
	}

	

	//Detalles	
	@Inject
	private ChecklistDetalleBC checklistDetalleBC;
		
	private String descripcion;
	private Long nroOrden;	
	
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Long getNroOrden() {
		return nroOrden;
	}
	
	public void setNroOrden(Long nroOrden) {
		this.nroOrden = nroOrden;
	}


	public void registrarChecklistDetalle(){
		if (checklistSeleccionado==null){
			agregarMensaje("ERROR: Checklist NO seleccionado");
			limpiarCamposNuevo();
		}
		else{
			Checklist checklistSelec = checklistSeleccionado;
			
			ChecklistDetalle checklistDetalle = new ChecklistDetalle();
			checklistDetalle.setDescripcion(getDescripcion()) ;
			checklistDetalle.setNroOrden(getNroOrden());
			
			checklistDetalle.setMaster((checklistSelec));
			checklistDetalleBC.registrar(checklistDetalle);
			detalles.add(checklistDetalle);
			agregarMensaje("Detalle creado");
			limpiarCamposNuevo();
		
		}
	}

	public void eliminarDetalle(ActionEvent actionEvent) {
		checklistDetalleBC.eliminar(detalleSeleccionado.getChecklistDetalleId());
		int index = detalles.indexOf(detalleSeleccionado);
		detalles.remove(index);
		//detalleSeleccionado = new CronogramaDetalle();
		
        agregarMensaje("Detalle eliminado");
    }

	private void limpiarCamposNuevo() {
		this.setDescripcion("");
		this.setNroOrden(null);
		
	}
	
	public void editarChecklistDetalle(){
		if (detalleSeleccionado==null){
			agregarMensaje("Detalle no seleccionado");
		}
		else{
			ChecklistDetalle detalle = detalleSeleccionado;
			
			checklistDetalleBC.editar(detalle);
			//detalleSeleccionado = new ChecklistDetalle();
			agregarMensaje("Detalle editado");
		}
	}

	

}
