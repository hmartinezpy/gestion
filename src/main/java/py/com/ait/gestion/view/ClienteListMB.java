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

import py.com.ait.gestion.business.ClienteBC;
import py.com.ait.gestion.domain.Cliente;


@ViewController
@NextView("/pg/cliente_edit.xhtml")
@PreviousView("/pg/cliente_list.xhtml")
public class ClienteListMB extends AbstractListPageBean<Cliente,Long> {


	private static final long serialVersionUID = 1L;

	@Inject 
	private FacesContext facesContext;
	
	@Inject
	private ClienteBC clienteBC;
	
	private List<Cliente> clientes;
	private Cliente clienteSeleccionado;
	
	
	
	public Cliente getClienteSeleccionado() {
		return clienteSeleccionado;
	}


	public void setClienteSeleccionado(Cliente clienteSeleccionado) {
		this.clienteSeleccionado = clienteSeleccionado;
	}


	public List<Cliente> getClientes() {
		clientes = clienteBC.listarOrdenado();
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	public void eliminar(ActionEvent actionEvent) {
		try {
			clienteBC.eliminar(clienteSeleccionado.getClienteId());
			clienteSeleccionado = new Cliente();
	        setClientes(clienteBC.listar());
	        agregarMensaje("Cliente eliminado");
		} catch (Exception e) {
			e.printStackTrace();
			agregarMensajeError("No se puede eliminar el registro, el mismo está siendo utilizado por otros datos del sistema");
		}
		
    }
	
	@Override
	protected List<Cliente> handleResultList() {
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
