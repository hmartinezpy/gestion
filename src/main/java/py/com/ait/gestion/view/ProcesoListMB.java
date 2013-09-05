package py.com.ait.gestion.view;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.event.DateSelectEvent;
import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;

import py.com.ait.gestion.business.ActividadBC;
import py.com.ait.gestion.business.CronogramaDetalleBC;
import py.com.ait.gestion.business.ProcesoBC;
import py.com.ait.gestion.business.TipoAlarmaBC;
import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.constant.Definiciones.Estado;
import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.domain.Proceso;
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.persistence.UsuarioDAO;


@ViewController
@NextView("/pg/proceso_edit.xhtml")
@PreviousView("/pg/proceso_list.xhtml")
public class ProcesoListMB extends AbstractListPageBean<Proceso,Long> {


	private static final long serialVersionUID = 1L;

	@Inject 
	private FacesContext facesContext;
	
	@Inject
	private ProcesoBC procesoBC;
	
	private List<Proceso> procesos;
	private Proceso procesoSeleccionado;
	
	private List<Actividad> actividades;	
	
	
	public Proceso getProcesoSeleccionado() {
		return procesoSeleccionado;
	}


	public void setProcesoSeleccionado(Proceso procesoSeleccionado) {
		this.procesoSeleccionado = procesoSeleccionado;
	}

	public void elegirProceso(){
		setActividades(procesoSeleccionado.getActividades());
		//setCronogramaDetallesporCronograma(procesoSeleccionado.getCronograma().getCronogramaDetalles());
		String numeroProceso = procesoSeleccionado.getNroProceso();		
		agregarMensaje("Proceso seleccionado: " + numeroProceso);		
	}
	
	public List<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}

	public List<Proceso> getProcesos() {
		procesos = procesoBC.listar();
		return procesos;
	}

	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}
	
	public void eliminar(ActionEvent actionEvent) {
        procesoBC.eliminar(procesoSeleccionado.getProcesoId());
        procesoSeleccionado = new Proceso();
        setProcesos(procesoBC.listar());
        agregarMensaje("Proceso eliminado");
    }
	
	@Override
	protected List<Proceso> handleResultList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void agregarMensaje(String mensaje){
	    	facesContext.addMessage("suceso", new FacesMessage(mensaje));
	}
	
	
	//Actividades
	
	
	@Inject
	private ActividadBC actividadBC;
	
	@Inject
	private UsuarioBC usuarioBC;
	
	@Inject
	private CronogramaDetalleBC cronogramaDetalleBC;
	
	@Inject
	private TipoAlarmaBC tipoAlarmaBC;
	
	private Long idResponsable;
	private Long idCronogramaDetalle;
	private Long idActividadAnterior;
	private Long idAlarma;
	private Long idAlerta;
	private Long idSuperTarea;	
	
	private String nroActividad;
	private String descripcion;
	private Date fechaCreacion;
	private Date fechaInicioPrevisto;
	private Date fechaInicioReprogramado;
	private String motivoReprogramacionInicio;
	private Date fechaFinPrevista;
	private Date fechaFinReprogramada;
	private String motivoReprogramacion;
	private Date fechaDevuelta;
	private Date fechaResuelta;
	private Date fechaCancelacion;
	private String pregunta;
	private String respuesta;
	private String estado;
	private String checklistCompleto;

	private List<Usuario> usuariosPorRol;
	private List<CronogramaDetalle> cronogramaDetallesporCronograma;
	private List<Actividad> actividadesPorProceso;


	private Actividad actividadSeleccionada;
	
		
	
	public Actividad getActividadSeleccionada() {
		return actividadSeleccionada;
	}


	public void setActividadSeleccionada(Actividad actividadSeleccionada) {
		this.actividadSeleccionada = actividadSeleccionada;
	}


	public Long getIdResponsable() {
		return idResponsable;
	}


	public void setIdResponsable(Long idResponsable) {
		this.idResponsable = idResponsable;
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


	public String getNroActividad() {
		return nroActividad;
	}


	public void setNroActividad(String nroActividad) {
		this.nroActividad = nroActividad;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Date getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public Date getFechaInicioPrevisto() {
		return fechaInicioPrevisto;
	}


	public void setFechaInicioPrevisto(Date fechaInicioPrevisto) {
		this.fechaInicioPrevisto = fechaInicioPrevisto;
	}


	public Date getFechaInicioReprogramado() {
		return fechaInicioReprogramado;
	}


	public void setFechaInicioReprogramado(Date fechaInicioReprogramado) {
		this.fechaInicioReprogramado = fechaInicioReprogramado;
	}


	public String getMotivoReprogramacionInicio() {
		return motivoReprogramacionInicio;
	}


	public void setMotivoReprogramacionInicio(String motivoReprogramacionInicio) {
		this.motivoReprogramacionInicio = motivoReprogramacionInicio;
	}


	public Date getFechaFinPrevista() {
		return fechaFinPrevista;
	}


	public void setFechaFinPrevista(Date fechaFinPrevista) {
		this.fechaFinPrevista = fechaFinPrevista;
	}


	public Date getFechaFinReprogramada() {
		return fechaFinReprogramada;
	}


	public void setFechaFinReprogramada(Date fechaFinReprogramada) {
		this.fechaFinReprogramada = fechaFinReprogramada;
	}


	public String getMotivoReprogramacion() {
		return motivoReprogramacion;
	}


	public void setMotivoReprogramacion(String motivoReprogramacion) {
		this.motivoReprogramacion = motivoReprogramacion;
	}


	public Date getFechaDevuelta() {
		return fechaDevuelta;
	}


	public void setFechaDevuelta(Date fechaDevuelta) {
		this.fechaDevuelta = fechaDevuelta;
	}


	public Date getFechaResuelta() {
		return fechaResuelta;
	}


	public void setFechaResuelta(Date fechaResuelta) {
		this.fechaResuelta = fechaResuelta;
	}


	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}


	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}


	public String getPregunta() {
		return pregunta;
	}


	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}


	public String getRespuesta() {
		return respuesta;
	}


	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getChecklistCompleto() {
		return checklistCompleto;
	}


	public void setChecklistCompleto(String checklistCompleto) {
		this.checklistCompleto = checklistCompleto;
	}


	public List<Usuario> getUsuariosPorRol() {
		
		if(actividadSeleccionada != null)
			usuariosPorRol = usuarioBC.getUsuariosByRol(
				actividadSeleccionada.getCronogramaDetalle().getRolResponsable().getRolId());
	
		return usuariosPorRol;
	}


	public void setUsuariosPorRol(List<Usuario> usuariosPorRol) {
		this.usuariosPorRol = usuariosPorRol;
	}


	public List<CronogramaDetalle> getCronogramaDetallesporCronograma() {
		cronogramaDetallesporCronograma = cronogramaDetalleBC.listar();
		return cronogramaDetallesporCronograma;
	}


	public void setCronogramaDetallesporCronograma(
			List<CronogramaDetalle> cronogramaDetallesporCronograma) {
		this.cronogramaDetallesporCronograma = cronogramaDetallesporCronograma;
	}


	public List<Actividad> getActividadesPorProceso() {
		actividadesPorProceso = actividadBC.listar();
		return actividadesPorProceso;
	}


	public void setActividadesPorProceso(List<Actividad> actividadesPorProceso) {
		this.actividadesPorProceso = actividadesPorProceso;
	}
	
	private Usuario getResponsable() {
		
		return usuarioBC.load(getIdResponsable());
	}

	private CronogramaDetalle getCronogramaDetalle() {
		
		return cronogramaDetalleBC.load(getIdCronogramaDetalle());
	}	
	

	private Actividad getActividadAnterior() {
		
		return actividadBC.load(getIdActividadAnterior());
	}

	private Actividad getSuperTarea() {
		
		return actividadBC.load(getIdSuperTarea());
	}
	
	public void registrarActividad(){
		if (procesoSeleccionado==null){
			agregarMensaje("ERROR: Proceso NO seleccionado");
			limpiarCamposNuevo();
		}
		else{
			Proceso procesoSelec = procesoSeleccionado;
			
			Actividad actividad = new Actividad();
			
			
			actividad.setNroActividad(getNroActividad());		
			actividad.setDescripcion(getDescripcion());		
			actividad.setFechaCreacion(getFechaCreacion());		
			actividad.setFechaInicioPrevisto(getFechaInicioPrevisto());
			actividad.setFechaInicioReprogramado(getFechaInicioReprogramado());		
			actividad.setMotivoReprogramacionInicio(getMotivoReprogramacionInicio());
			actividad.setFechaFinPrevista(getFechaFinPrevista());		
			actividad.setFechaFinReprogramada(getFechaFinReprogramada());
			actividad.setMotivoReprogramacion(getMotivoReprogramacion());
			actividad.setFechaDevuelta(getFechaDevuelta());
			actividad.setFechaResuelta(getFechaResuelta());
			actividad.setFechaCancelacion(getFechaCancelacion());
			actividad.setPregunta(getPregunta());
			actividad.setRespuesta(getRespuesta());
			actividad.setEstado(getEstado());
			actividad.setChecklistCompleto(getChecklistCompleto());
						

			actividad.setResponsable(getResponsable());
			actividad.setCronogramaDetalle(getCronogramaDetalle());
			actividad.setActividadAnterior(getActividadAnterior());
			actividad.setSuperTarea(getSuperTarea());
			
			/*actividad.setAlarma(getCronogramaDetalle().getAlarma());
			actividad.setAlerta(getCronogramaDetalle().getAlerta());*/
			actividad.setMaster(procesoSelec);
			actividadBC.registrar(actividad);
			actividades.add(actividad);
			agregarMensaje("Actividad creada");
			limpiarCamposNuevo();
		
		}
	}
	
	private void limpiarCamposNuevo() {
		this.setNroActividad("");
		this.setDescripcion("");
		this.setFechaCreacion(null);		
		this.setFechaInicioPrevisto(null);
		this.setFechaInicioReprogramado(null);
		this.setMotivoReprogramacionInicio("");
		this.setFechaFinPrevista(null);
		this.setFechaFinReprogramada(null);
		this.setMotivoReprogramacion("");
		this.setFechaDevuelta(null);
		this.setFechaResuelta(null);
		this.setFechaCancelacion(null);
		this.setPregunta("");
		this.setRespuesta("");
		this.setEstado("");
		this.setChecklistCompleto("");
		
	}
	
	
	public void eliminarActividad(ActionEvent actionEvent) {
		actividadBC.eliminar(actividadSeleccionada.getActividadId());
		int index = actividades.indexOf(actividadSeleccionada);
		actividades.remove(index);
		//detalleSeleccionado = new CronogramaDetalle();
		
        agregarMensaje("Actividad eliminada");
    }
	
	public void elegirActividad(){
		Actividad actividad = actividadSeleccionada;
		
		agregarMensaje("Actividad seleccionada: " + actividad.getNroActividad());
		
	}
	
	public void editarActividad(){
		if (actividadSeleccionada==null){
			agregarMensaje("Actividad no seleccionada");
		}
		else{
			Actividad actividad = actividadSeleccionada;
			actividad.setResponsable(usuarioBC.load(actividad.getResponsable().getUsuarioId()));
			actividadBC.editar(actividad);
			agregarMensaje("Actividad editada");
		}
	}
	
	public List<Estado> getEstadosActividad() {
	
		return Definiciones.EstadoActividad.getEstadosActividad();
	}
	
	public List<Estado> getSiNoList() {
		
		return Definiciones.getSiNoList();
	}
	
	public Long getActividadSeleccionadaResponsable() {
		
		Long usuario = null;
		if(actividadSeleccionada != null && actividadSeleccionada.getResponsable() != null)
			usuario = actividadSeleccionada.getResponsable().getUsuarioId();
		
		return usuario;
	}
	
	public Date calculoFechaFin(DateSelectEvent event) {
		
		Calendar cal = new GregorianCalendar();
		cal.setTime((Date) event.getDate());
		cal.add(Calendar.DATE, actividadSeleccionada.getCronogramaDetalle().getDuracionTarea().intValue());
		actividadSeleccionada.setFechaFinPrevista(cal.getTime());
		return cal.getTime();
	}
	
	public void resolverActividad(){
		if (actividadSeleccionada==null){
			agregarMensaje("Actividad no seleccionada");
		}
		else{
			Actividad actividad = actividadSeleccionada;
			actividadBC.editar(actividad);
			agregarMensaje("Actividad editada");
		}
	}
	
	public boolean getMostrarCampoRespuesta() {
		
		boolean show = false;
		if(actividadSeleccionada != null) {
			
			if(actividadSeleccionada.getPregunta() != null && 
					!actividadSeleccionada.getPregunta().equals("")) { 
				
				show = true;
			}
		}
		return show;
	}
}
