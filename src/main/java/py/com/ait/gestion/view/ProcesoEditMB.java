package py.com.ait.gestion.view;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;

import py.com.ait.gestion.business.ClienteBC;
import py.com.ait.gestion.business.CronogramaBC;
import py.com.ait.gestion.business.ProcesoBC;
import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.domain.Cliente;
import py.com.ait.gestion.domain.Cronograma;
import py.com.ait.gestion.domain.Proceso;
import py.com.ait.gestion.domain.Usuario;


@ViewController
@NextView("/pg/proceso_edit.xhtml")
@PreviousView("/pg/proceso_list.xhtml")
public class ProcesoEditMB extends AbstractEditPageBean<Proceso,Long> {


	private static final long serialVersionUID = 1L;

	@Inject
	private ProcesoBC procesoBC;
	
	@Inject
	private UsuarioBC usuarioBC;
	
	@Inject
	private ClienteBC clienteBC;
	
	@Inject
	private CronogramaBC cronogramaBC;
	
	private Long idResponsable;
	private Long idCliente;
	private Long idCronograma;
	
	private List<Usuario> usuarios;
	private List<Cliente> clientes;
	private List<Cronograma> cronogramas;
	
	
	public Long getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(Long idResponsable) {
		this.idResponsable = idResponsable;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Long getIdCronograma() {
		return idCronograma;
	}

	public void setIdCronograma(Long idCronograma) {
		this.idCronograma = idCronograma;
	}
	
	public List<Usuario> getUsuarios() {
		usuarios = usuarioBC.findAll();
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public List<Cliente> getClientes() {
		clientes = clienteBC.listar();
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	public List<Cronograma> getCronogramas() {
		cronogramas = cronogramaBC.listar();
		return cronogramas;
	}

	public void setCronogramas(List<Cronograma> cronogramas) {
		this.cronogramas = cronogramas;
	}
	
	private Usuario getResponsable() {
		
		return usuarioBC.load(getIdResponsable());
	}
	
	private Cliente getCliente() {
		
		return clienteBC.load(getIdCliente());
	}

	private Cronograma getCronograma() {
	
	return cronogramaBC.load(getIdCronograma());
}
	
	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insert() {
		Proceso proceso = new Proceso();
		proceso.setNroProceso(getBean().getNroProceso());
		proceso.setEstado(getBean().getEstado());
		proceso.setDescripcion(getBean().getDescripcion());
		proceso.setFechaInicioContratual(getBean().getFechaInicioContratual());
		proceso.setFechaInicioReal(getBean().getFechaInicioReal());
		proceso.setFechaFinPrevista(getBean().getFechaFinPrevista());
		proceso.setFechaFinReprogramada(getBean().getFechaFinReprogramada());
		proceso.setMotivoReprogramacion(getBean().getMotivoReprogramacion());
		proceso.setClienteNotificado(getBean().getClienteNotificado());
		proceso.setResponsable(getResponsable());
		proceso.setCliente(getCliente());
		proceso.setCronograma(getCronograma());
		
		procesoBC.registrar(proceso);
		return getPreviousView();

	}

	@Override
	public String update() {
		Proceso proceso = getBean();
		proceso.setResponsable(getResponsable());
		proceso.setCliente(getCliente());
		proceso.setCronograma(getCronograma());
		
		procesoBC.editar(proceso);
		
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		setBean(this.procesoBC.load(getId()));
		
	}
	
	@Override
	public Proceso getBean() {
		Proceso bean = super.getBean();
		return bean;
	}



}
