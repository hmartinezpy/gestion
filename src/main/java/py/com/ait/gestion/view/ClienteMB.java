

package py.com.ait.gestion.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.event.RowEditEvent;
import org.ticpy.tekoporu.stereotype.ViewController;

import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.business.ClienteBC;
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.domain.Cliente;

@ViewController
public class ClienteMB {
	
	@Inject 
	private FacesContext facesContext;
	
	@Inject
	private ClienteBC clienteBC;
	
	@Inject
	private UsuarioBC usuarioBC;
	
	private Long idUsuario;
	private String nombre;
	private String direccion;
	private String telefono;
	private String personaContacto;
	private List<Cliente> clientes;
	private Cliente clienteSeleccionado;
	
	
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	private String getDireccion() {
		return direccion;
	}
	
	private void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	private String getTelefono() {
		return telefono;
	}
	
	private void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	private String getPersonaContacto() {
		return personaContacto;
	}
	
	private void setPersonaContacto(String personaContacto) {
		this.personaContacto = personaContacto;
	}

	
    public List<Cliente> getClientes() {
    	clientes = clienteBC.listar();
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getClienteSeleccionado() {
		return clienteSeleccionado;
	}

	public void setClienteSeleccionado(Cliente clienteSeleccionado) {
		this.clienteSeleccionado = clienteSeleccionado;
	}
	
	public void registrarCliente(){
		Cliente cliente = new Cliente();
		cliente.setNombre(getNombre());
		cliente.setDireccion(getDireccion());
		cliente.setTelefono(getTelefono());
		cliente.setPersonaContacto(getPersonaContacto());
		clienteBC.registrar(cliente);
		agregarMensaje("Cliente creado");
		this.limpiarCampos();
	}

	

	private void limpiarCampos() {
		this.setNombre("");
		this.setDireccion("");
		this.setTelefono("");
		this.setPersonaContacto("");	
	}
	

	public void eliminar(ActionEvent actionEvent) {
        clienteBC.eliminar(clienteSeleccionado.getClienteId());
        clienteSeleccionado = new Cliente();
        setClientes(clienteBC.listar());
        agregarMensaje("Cliente eliminado");
    }
	
    public void onEdit(RowEditEvent event) {
    	Cliente cliente = ((Cliente) event.getObject());
    	clienteBC.editar(cliente);
        agregarMensaje("Cliente Modificado");
    }
    
    public void agregarMensaje(String mensaje){
    	facesContext.addMessage("suceso", new FacesMessage(mensaje));
    }
	
	
	
}
