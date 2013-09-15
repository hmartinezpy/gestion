package py.com.ait.gestion.view;

import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;

import py.com.ait.gestion.business.ClienteBC;
import py.com.ait.gestion.business.CronogramaBC;
import py.com.ait.gestion.business.ProcesoBC;
import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.constant.Definiciones.Estado;
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
	private Long idCliente = null;
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
		
	@Override
	public String delete() {
		
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
		if(getBean().getResponsable() != null)
			proceso.setResponsable(usuarioBC.load(getBean().getResponsable().getUsuarioId()));
		else
			proceso.setResponsable(null);
		if(getBean().getCliente() != null)
			proceso.setCliente(clienteBC.load(getBean().getCliente().getClienteId()));
		else
			proceso.setCliente(null);
		if(getBean().getCronograma() != null)
			proceso.setCronograma(cronogramaBC.load(getBean().getCronograma().getCronogramaId()));
		else
			proceso.setCronograma(null);
		
		procesoBC.registrar(proceso);
		return getPreviousView();

	}

	@Override
	public String update() {
		Proceso proceso = getBean();
		if(proceso.getResponsable() != null)
			proceso.setResponsable(usuarioBC.load(proceso.getResponsable().getUsuarioId()));
		else
			proceso.setResponsable(null);
		if(proceso.getCliente() != null)
			proceso.setCliente(clienteBC.load(proceso.getCliente().getClienteId()));
		else
			proceso.setCliente(null);		
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

	public List<Estado> getEstadosProceso() {
		
		return Definiciones.EstadoProceso.getEstadosList();
	}
	
	public String volver() {
		
		return getPreviousView();
	}

	public String calculoNroProceso(AjaxBehaviorEvent event) {
		
		Proceso proceso = getBean();
		String nroProceso = "";
		if(proceso.getCronograma() != null) {
			
			Cronograma cronograma = proceso.getCronograma();
			nroProceso = procesoBC.getSiguienteNroProceso(cronograma.getCronogramaId());
			
		}
		proceso.setNroProceso(nroProceso);
		return nroProceso;
	}
}
