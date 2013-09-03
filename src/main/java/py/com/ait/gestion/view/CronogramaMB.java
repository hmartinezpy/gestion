

package py.com.ait.gestion.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;

import py.com.ait.gestion.business.CronogramaBC;
import py.com.ait.gestion.domain.Cronograma;


@ViewController
@NextView("/cronograma/cronogramaDetalle_list.xhtml")
public class CronogramaMB extends AbstractListPageBean<Cronograma, Long>{
	
	private static final long serialVersionUID = 1L;

	@Inject 
	private FacesContext facesContext;
	
	@Inject
	private CronogramaBC cronogramaBC;

	private Long idCronograma;
	private String nombre;
	private Cronograma cronogramaSeleccionado;
	private List<Cronograma> cronogramas;
	
	
	public Long getIdCronograma() {
		return idCronograma;
	}

	public void setIdCronograma(Long idCronograma) {
		this.idCronograma= idCronograma;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

	public List<Cronograma> getCronogramas() {
		cronogramas = cronogramaBC.listar();
		return cronogramas;
	}

	public void setCronogramas(List<Cronograma> cronogramas) {
		this.cronogramas = cronogramas;
	}

	public Cronograma getCronogramaSeleccionado() {
		return cronogramaSeleccionado;
	}

	public void setCronogramaSeleccionado(Cronograma cronogramaSeleccionado) {
		this.cronogramaSeleccionado = cronogramaSeleccionado;
	}
	
	public void registrarCronograma(){
		Cronograma cronograma = new Cronograma();
		cronograma.setNombre(getNombre());
		cronogramaBC.registrar(cronograma);
		agregarMensaje("Cronograma creado");
		this.limpiarCampos();
	}

	

	private void limpiarCampos() {
		this.setNombre("");
		
	}
	


	public void eliminar(ActionEvent actionEvent) {
		cronogramaBC.eliminar(cronogramaSeleccionado.getCronogramaId());
		cronogramaSeleccionado = new Cronograma();
        setCronogramas(cronogramaBC.listar());
        agregarMensaje("Cronograma eliminado");
    }
	
    public void onEdit(RowEditEvent event) {
    	Cronograma cronograma = ((Cronograma) event.getObject());
    	cronogramaBC.editar(cronograma);
        agregarMensaje("Cronograma Modificado");
    }
    
    
    public void agregarMensaje(String mensaje){
    	facesContext.addMessage("suceso", new FacesMessage(mensaje));
    }

	@Override
	protected List<Cronograma> handleResultList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
