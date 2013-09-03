package py.com.ait.gestion.view;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;

import py.com.ait.gestion.business.ActividadBC;
import py.com.ait.gestion.business.CronogramaDetalleBC;
import py.com.ait.gestion.business.ProcesoBC;
import py.com.ait.gestion.business.TipoAlarmaBC;
import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.domain.Proceso;
import py.com.ait.gestion.domain.TipoAlarma;
import py.com.ait.gestion.domain.Usuario;


@ViewController
@NextView("/pg/actividad_edit.xhtml")
@PreviousView("/pg/actividad_list.xhtml")
public class ActividadEditMB extends AbstractEditPageBean<Actividad,Long> {


	private static final long serialVersionUID = 1L;

	@Inject
	private ActividadBC actividadBC;
	
	@Inject
	private UsuarioBC usuarioBC;
	
	@Inject
	private ProcesoBC procesoBC;
	
	@Inject
	private CronogramaDetalleBC cronogramaDetalleBC;
	
	@Inject
	private TipoAlarmaBC tipoAlarmaBC;
	
	
	private Long idResponsable;
	private Long idProceso;
	private Long idCronogramaDetalle;
	private Long idActividadAnterior;
	private Long idAlarma;
	private Long idAlerta;
	private Long idSuperTarea;
	
	private List<Usuario> usuarios;
	private List<Proceso> procesos;
	private List<CronogramaDetalle> cronogramaDetalles;
	private List<Actividad> actividades;
	private List<TipoAlarma> alarmas;
	private List<TipoAlarma> alertas;
	
	
	public Long getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(Long idResponsable) {
		this.idResponsable = idResponsable;
	}

		
	public Long getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}

	public Long getIdCronogramaDetalle() {
		return idCronogramaDetalle;
	}

	public void setIdCronogramaDetalle(Long idCronogramaDetalle) {
		this.idCronogramaDetalle = idCronogramaDetalle;
	}

	public Long getIdActividadAnterior() {
		return idActividadAnterior;
	}

	public void setIdActividadAnterior(Long idActividadAnterior) {
		this.idActividadAnterior = idActividadAnterior;
	}

	public Long getIdAlarma() {
		return idAlarma;
	}

	public void setIdAlarma(Long idAlarma) {
		this.idAlarma = idAlarma;
	}

	public Long getIdAlerta() {
		return idAlerta;
	}

	public void setIdAlerta(Long idAlerta) {
		this.idAlerta = idAlerta;
	}

	public Long getIdSuperTarea() {
		return idSuperTarea;
	}

	public void setIdSuperTarea(Long idSuperTarea) {
		this.idSuperTarea = idSuperTarea;
	}

	public List<Usuario> getUsuarios() {
		usuarios = usuarioBC.findAll();
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public List<Proceso> getProcesos() {
		procesos = procesoBC.findAll();
		return procesos;
	}

	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}
	
	public List<CronogramaDetalle> getCronogramaDetalles() {
		cronogramaDetalles = cronogramaDetalleBC.findAll();
		return cronogramaDetalles;
	}

	public void setCronogramaDetalles(List<CronogramaDetalle> cronogramaDetalles) {
		this.cronogramaDetalles = cronogramaDetalles;
	}
	
	public List<Actividad> getActividades() {
		actividades = actividadBC.findAll();
		return actividades;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}
	
	public List<TipoAlarma> getAlertas() {
		alertas = tipoAlarmaBC.getAlertas();
		return alertas;
	}	
		
	public void setAlertas(List<TipoAlarma> tipoAlarmas) {
		this.alertas = tipoAlarmas;
	}
	
	public List<TipoAlarma> getAlarmas() {
		alarmas = tipoAlarmaBC.getAlarmas();
		return alarmas;
	}	
		
	public void setAlarmas(List<TipoAlarma> alarmas) {
		this.alarmas = alarmas;
	}
	
	
	private Usuario getResponsable() {
		
		return usuarioBC.load(getIdResponsable());
	}
	
	private Proceso getProceso() {
		
		return procesoBC.load(getIdProceso());
	}
	
	private CronogramaDetalle getCronogramaDetalle() {
		
		return cronogramaDetalleBC.load(getIdCronogramaDetalle());
	}	
	
	private Actividad getActividadAnterior() {
		
		return actividadBC.load(getIdActividadAnterior());
	}
	
	private TipoAlarma getAlarma() {
		
		return tipoAlarmaBC.load(getIdAlarma());
	}
	
	private TipoAlarma getAlerta() {
		
		return tipoAlarmaBC.load(getIdAlerta());
	}
	

	private Actividad getSuperTarea() {
		
		return actividadBC.load(getIdSuperTarea());
	}

	
	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insert() {
		Actividad actividad = new Actividad();		
		actividad.setNroActividad(getBean().getNroActividad());		
		actividad.setDescripcion(getBean().getDescripcion());		
		actividad.setFechaCreacion(getBean().getFechaCreacion());		
		actividad.setFechaInicioPrevisto(getBean().getFechaInicioPrevisto());
		actividad.setFechaInicioReprogramado(getBean().getFechaInicioReprogramado());		
		actividad.setMotivoReprogramacionInicio(getBean().getMotivoReprogramacionInicio());
		actividad.setFechaFinPrevista(getBean().getFechaFinPrevista());		
		actividad.setFechaFinReprogramada(getBean().getFechaFinReprogramada());
		actividad.setMotivoReprogramacion(getBean().getMotivoReprogramacion());
		actividad.setFechaDevuelta(getBean().getFechaDevuelta());
		actividad.setFechaResuelta(getBean().getFechaResuelta());
		actividad.setFechaCancelacion(getBean().getFechaCancelacion());
		actividad.setPregunta(getBean().getPregunta());
		actividad.setRespuesta(getBean().getRespuesta());
		actividad.setEstado(getBean().getEstado());
		actividad.setChecklistCompleto(getBean().getChecklistCompleto());
		
		actividad.setResponsable(getResponsable());
		actividad.setMaster(getProceso());
		actividad.setCronogramaDetalle(getCronogramaDetalle());
		actividad.setActividadAnterior(getActividadAnterior());
		actividad.setAlarma(getAlarma());
		actividad.setAlerta(getAlerta());
		actividad.setSuperTarea(getSuperTarea());		
		
		actividadBC.registrar(actividad);
		return getPreviousView();

	}

	@Override
	public String update() {
		Actividad actividad = getBean();
		actividad.setResponsable(getResponsable());
		actividad.setMaster(getProceso());
		actividad.setCronogramaDetalle(getCronogramaDetalle());
		actividad.setActividadAnterior(getActividadAnterior());
		actividad.setAlarma(getAlarma());
		actividad.setAlerta(getAlerta());
		actividad.setSuperTarea(getSuperTarea());	
		
		actividadBC.editar(actividad);
		
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		setBean(this.actividadBC.load(getId()));
		
	}
	
	@Override
	public Actividad getBean() {
		Actividad bean = super.getBean();
		return bean;
	}



}
