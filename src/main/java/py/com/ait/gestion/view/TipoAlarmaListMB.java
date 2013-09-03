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

import py.com.ait.gestion.business.TipoAlarmaBC;
import py.com.ait.gestion.domain.TipoAlarma;


@ViewController
@NextView("/pg/tipo_alarma_edit.xhtml")
@PreviousView("/pg/tipo_alarma_list.xhtml")
public class TipoAlarmaListMB extends AbstractListPageBean<TipoAlarma,Long> {


	private static final long serialVersionUID = 1L;

	@Inject 
	private FacesContext facesContext;
	
	@Inject
	private TipoAlarmaBC tipoAlarmaBC;
	
	private List<TipoAlarma> tipoAlarmas;
	private TipoAlarma tipoAlarmaSeleccionado;
	
	
	
	public TipoAlarma getTipoAlarmaSeleccionado() {
		return tipoAlarmaSeleccionado;
	}


	public void setTipoAlarmaSeleccionado(TipoAlarma tipoAlarmaSeleccionado) {
		this.tipoAlarmaSeleccionado = tipoAlarmaSeleccionado;
	}


	public List<TipoAlarma> getTipoAlarmas() {
		tipoAlarmas = tipoAlarmaBC.listar();
		return tipoAlarmas;
	}

	public void setTipoAlarmas(List<TipoAlarma> tipoAlarmas) {
		this.tipoAlarmas = tipoAlarmas;
	}
	
	public void eliminar(ActionEvent actionEvent) {
		tipoAlarmaBC.eliminar(tipoAlarmaSeleccionado.getTipoAlarmaId());
		tipoAlarmaSeleccionado = new TipoAlarma();
        setTipoAlarmas(tipoAlarmaBC.listar());
        agregarMensaje("TipoAlarma eliminado");
    }
	
	@Override
	protected List<TipoAlarma> handleResultList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void agregarMensaje(String mensaje){
	    	facesContext.addMessage("suceso", new FacesMessage(mensaje));
	}

}
