package py.com.ait.gestion.business;

import java.util.List;
import javax.inject.Inject;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Cliente;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.ClienteDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@BusinessController
public class ClienteBC extends DelegateCrud<Cliente, Long, ClienteDAO>{

	private static final long serialVersionUID = 1L;

	@Inject 
	private ClienteDAO clienteDAO ;
	
	@Inject 
	private AudLogDAO audLogDAO;
	
	@Inject 
	private UsuarioDAO usuarioDAO;
	
	@Inject 
	SesionLogDAO sesionLogDAO;


	public List<Cliente> listar() {
		return clienteDAO.findAll();	
	}
	
	public Cliente recuperar(Long id) {
		return clienteDAO.load(id);	
	}
	
	/*****************Auditoria**************************/			
	@Transactional
	public void registrar(Cliente cliente) {
		
		super.insert(cliente);
		
		try {
			audLogDAO.log(null, cliente,usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Insert,cliente.getClienteId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void editar(Cliente cliente) {
		
		Cliente viejo = this.recuperar(cliente.getClienteId());
		super.update(cliente);
		try {
			audLogDAO.log(viejo, cliente,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Update,cliente.getClienteId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//actividadProductivaDAO.update(actividadProductiva);	
	}
	
	
	@Transactional
	public void eliminar(Long id) {
		
		Cliente cliente = this.recuperar(id);
		super.delete(id);
		
		try {
			audLogDAO.log(cliente, null,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Delete,cliente.getClienteId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
	
	