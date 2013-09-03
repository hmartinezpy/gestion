package py.com.ait.gestion.view;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;

import py.com.ait.gestion.business.ClienteBC;
import py.com.ait.gestion.domain.Cliente;


@ViewController
@NextView("/pg/cliente_edit.xhtml")
@PreviousView("/pg/cliente_list.xhtml")
public class ClienteEditMB extends AbstractEditPageBean<Cliente,Long> {


	private static final long serialVersionUID = 1L;

	
	@Inject
	private ClienteBC clienteBC;

	
	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insert() {
		Cliente cliente = new Cliente();
		cliente.setNombre(getBean().getNombre());
		cliente.setDireccion(getBean().getDireccion());
		cliente.setTelefono(getBean().getTelefono());
		cliente.setPersonaContacto(getBean().getPersonaContacto());
		
		clienteBC.registrar(cliente);
		return getPreviousView();

	}

	@Override
	public String update() {
		Cliente cliente = getBean();
		
		clienteBC.editar(cliente);
		
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		setBean(this.clienteBC.load(getId()));
		
	}
	
	@Override
	public Cliente getBean() {
		Cliente bean = super.getBean();
		return bean;
	}



}
