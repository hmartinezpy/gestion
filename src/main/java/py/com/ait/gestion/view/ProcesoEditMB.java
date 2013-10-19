package py.com.ait.gestion.view;

import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
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
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.domain.Proceso;
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.persistence.CronogramaDetalleDAO;

@ViewController
@NextView("/pg/proceso_edit.xhtml")
@PreviousView("/pg/proceso_list.xhtml")
public class ProcesoEditMB extends AbstractEditPageBean<Proceso, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProcesoBC procesoBC;

	@Inject
	private UsuarioBC usuarioBC;

	@Inject
	private ClienteBC clienteBC;

	@Inject
	private CronogramaBC cronogramaBC;

	@Inject
	private CronogramaDetalleDAO cronogramaDetalleDAO;

	private Long idResponsable;
	private Long idCliente = null;
	private Long idCronograma;

	private List<Usuario> usuarios;
	private List<Cliente> clientes;
	private List<Cronograma> cronogramas;
	private List<Usuario> sigteUsuariosPorRol;
	private Usuario sigteUsuario;

	public Long getIdResponsable() {

		return this.idResponsable;
	}

	public void setIdResponsable(Long idResponsable) {

		this.idResponsable = idResponsable;
	}

	public Long getIdCliente() {

		return this.idCliente;
	}

	public void setIdCliente(Long idCliente) {

		this.idCliente = idCliente;
	}

	public Long getIdCronograma() {

		return this.idCronograma;
	}

	public void setIdCronograma(Long idCronograma) {

		this.idCronograma = idCronograma;
	}

	public List<Usuario> getUsuarios() {

		this.usuarios = this.usuarioBC.findAll();
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {

		this.usuarios = usuarios;
	}

	public List<Cliente> getClientes() {

		this.clientes = this.clienteBC.listar();
		return this.clientes;
	}

	public void setClientes(List<Cliente> clientes) {

		this.clientes = clientes;
	}

	public List<Cronograma> getCronogramas() {

		this.cronogramas = this.cronogramaBC.listar();
		return this.cronogramas;
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
		proceso.setNroProceso(this.getBean().getNroProceso());
		proceso.setEstado(this.getBean().getEstado());
		proceso.setDescripcion(this.getBean().getDescripcion());
		proceso.setFechaInicioContratual(this.getBean()
				.getFechaInicioContratual());
		proceso.setFechaInicioReal(this.getBean().getFechaInicioReal());
		proceso.setFechaFinPrevista(this.getBean().getFechaFinPrevista());
		proceso.setFechaFinReprogramada(this.getBean()
				.getFechaFinReprogramada());
		proceso.setMotivoReprogramacion(this.getBean()
				.getMotivoReprogramacion());
		proceso.setClienteNotificado("No");
		if (this.getBean().getResponsable() != null) {
			proceso.setResponsable(this.usuarioBC.load(this.getBean()
					.getResponsable().getUsuarioId()));
		} else {
			proceso.setResponsable(null);
		}
		if (this.getBean().getCliente() != null) {
			proceso.setCliente(this.clienteBC.load(this.getBean().getCliente()
					.getClienteId()));
		} else {
			proceso.setCliente(null);
		}
		if (this.getBean().getCronograma() != null) {
			proceso.setCronograma(this.cronogramaBC.load(this.getBean()
					.getCronograma().getCronogramaId()));
		} else {
			proceso.setCronograma(null);
		}

		this.procesoBC.registrar(proceso);
		return this.getPreviousView();

	}

	@Override
	public String update() {

		Proceso proceso = this.getBean();
		if (proceso.getResponsable() != null) {
			proceso.setResponsable(this.usuarioBC.load(proceso.getResponsable()
					.getUsuarioId()));
		} else {
			proceso.setResponsable(null);
		}
		if (proceso.getCliente() != null) {
			proceso.setCliente(this.clienteBC.load(proceso.getCliente()
					.getClienteId()));
		} else {
			proceso.setCliente(null);
		}
		this.procesoBC.editar(proceso);

		return this.getPreviousView();
	}

	@Override
	protected void handleLoad() {

		this.setBean(this.procesoBC.load(this.getId()));

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

		return this.getPreviousView();
	}

	public String calculoNroProceso(AjaxBehaviorEvent event) {

		Proceso proceso = this.getBean();
		String nroProceso = "";
		if (proceso.getCronograma() != null) {

			Cronograma cronograma = proceso.getCronograma();
			nroProceso = this.procesoBC.getSiguienteNroProceso(cronograma
					.getCronogramaId());

		}
		proceso.setNroProceso(nroProceso);
		return nroProceso;
	}

	public void nextActividadUsuariosPorRol() {

		// Obtener siguiente cronograma detalle para filtrar posibles
		// responsables
		CronogramaDetalle cd = this.cronogramaDetalleDAO
				.getFirstCronogramaDetalleByCronograma(this.getBean()
						.getCronograma());

		// Listar los usuarios de acuerdo al rol del cronogramaDetalle
		// siguiente
		this.setSigteUsuariosPorRol(this.usuarioBC.getUsuariosByRol(cd
				.getRolResponsable().getRolId()));

	}

	public List<Usuario> getSigteUsuariosPorRol() {

		return this.sigteUsuariosPorRol;
	}

	public void setSigteUsuariosPorRol(List<Usuario> sigteUsuariosPorRol) {

		this.sigteUsuariosPorRol = sigteUsuariosPorRol;
	}

	public Usuario getSigteUsuario() {

		return this.sigteUsuario;
	}

	public void setSigteUsuario(Usuario sigteUsuario) {

		this.sigteUsuario = sigteUsuario;
	}

	public String empezarActividades() {

		this.procesoBC.empezarActividades(this.getBean(), this.sigteUsuario);
		return this.getPreviousView();
	}

	public boolean isTieneActividades() {

		boolean result = true;
		if (this.getBean().getActividades() == null
				|| this.getBean().getActividades().size() < 1) {
			result = false;
		}
		return result;
	}
}
