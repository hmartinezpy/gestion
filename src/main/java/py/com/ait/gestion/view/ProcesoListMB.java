package py.com.ait.gestion.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.report.Report;
import org.ticpy.tekoporu.report.Type;
import org.ticpy.tekoporu.report.annotation.Path;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;
import org.ticpy.tekoporu.util.FileRenderer;
import py.com.ait.gestion.business.ActividadBC;
import py.com.ait.gestion.business.ActividadChecklistDetalleBC;
import py.com.ait.gestion.business.CronogramaDetalleBC;
import py.com.ait.gestion.business.DocumentoBC;
import py.com.ait.gestion.business.ObservacionBC;
import py.com.ait.gestion.business.ProcesoBC;
import py.com.ait.gestion.business.RolBC;
import py.com.ait.gestion.business.TipoAlarmaBC;
import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.constant.AppProperties;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.constant.Definiciones.Estado;
import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.ActividadChecklistDetalle;
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.domain.Documento;
import py.com.ait.gestion.domain.Observacion;
import py.com.ait.gestion.domain.Proceso;
import py.com.ait.gestion.domain.ReportePendienteBean;
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.persistence.ReporteDAO;

@ViewController
@NextView("/pg/proceso_edit.xhtml")
@PreviousView("/pg/proceso_list.xhtml")
public class ProcesoListMB extends AbstractListPageBean<Proceso, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private AppProperties appProperties;

	@Inject
	private FacesContext facesContext;

	@Inject
	private ProcesoBC procesoBC;

	@Inject
	private RolBC rolBC;

	@Inject
	private ObservacionBC observacionBC;

	@Inject
	private DocumentoBC documentoBC;

	@Inject
	ActividadChecklistDetalleBC actividadChecklistDetalleBC;

	// Reportes
	@Inject
	@Path("reports/reportProc.jasper")
	private Report reporte;

	@Inject
	@Path("reports/repPendientes.jasper")
	private Report reportePendientes;

	@Inject
	private ReporteDAO reporteDAO;

	@Inject
	private FileRenderer renderer;

	private List<Proceso> procesos;
	private Proceso procesoSeleccionado;

	private List<Actividad> actividades;
	private List<Observacion> observaciones;
	private List<Documento> documentos;
	private List<ActividadChecklistDetalle> checklist;

	private String carpetaFileUpload;
	private List<String> carpetas;

	private String filtroEstadoProceso = "A";

	public Proceso getProcesoSeleccionado() {

		return this.procesoSeleccionado;
	}

	public void setProcesoSeleccionado(Proceso procesoSeleccionado) {

		this.procesoSeleccionado = procesoSeleccionado;
	}

	public List<Observacion> getObservaciones() {

		return this.observaciones;
	}

	public void setObservacion(List<Observacion> observaciones) {

		this.observaciones = observaciones;
	}

	public List<Documento> getDocumentos() {

		return this.documentos;
	}

	public void setDocumentos(List<Documento> documentos) {

		this.documentos = documentos;
	}

	public void elegirProceso() {

		String currentUser = FacesContext.getCurrentInstance()
				.getExternalContext().getUserPrincipal().getName();
		this.setActividades(this.procesoBC.getActividadesByProceso(
				this.procesoSeleccionado, currentUser));
		// setCronogramaDetallesporCronograma(procesoSeleccionado.getCronograma().getCronogramaDetalles());
		String numeroProceso = this.procesoSeleccionado.getNroProceso();
		this.agregarMensaje("Proceso seleccionado: " + numeroProceso);
	}

	public void mostrarObsProceso() {

		this.setObservacion(this.observacionBC
				.getObsProceso(this.procesoSeleccionado.getProcesoId()));

		this.elegirProceso();
	}

	public void mostrarFileProceso() {

		this.elegirProceso();

		String currentUser = FacesContext.getCurrentInstance()
				.getExternalContext().getUserPrincipal().getName();
		this.setDocumentos(this.documentoBC.getFileProceso(
				this.procesoSeleccionado.getProcesoId(), currentUser));

		this.updateCarpetas();
		this.elegirProceso();
	}

	public void mostrarFileActividad() {

		this.setDocumentos(this.documentoBC
				.getFileActividad(this.actividadSeleccionada.getActividadId()));

		String numeroActividad = this.actividadSeleccionada.getNroActividad();
		this.agregarMensaje("Actividad seleccionada: " + numeroActividad);
	}

	public void mostrarChecklist() {

		this.setChecklist(this.actividadChecklistDetalleBC
				.getChecklistByActividad(this.actividadSeleccionada));

		this.agregarMensaje("Actividad seleccionada: "
				+ this.actividadSeleccionada.getNroActividad());
	}

	public List<ActividadChecklistDetalle> getChecklist() {

		return this.checklist;
	}

	public void setChecklist(List<ActividadChecklistDetalle> checklist) {

		this.checklist = checklist;
	}

	public List<Actividad> getActividades() {

		return this.actividades;
	}

	public void setActividades(List<Actividad> actividades) {

		this.actividades = actividades;
	}

	public List<Proceso> getProcesos() {

		String currentUser = FacesContext.getCurrentInstance()
				.getExternalContext().getUserPrincipal().getName();
		this.procesos = this.procesoBC.listar(this.filtroEstadoProceso,
				currentUser);
		return this.procesos;
	}

	public void updateFiltroEstadoProceso() {

		this.actividades = null;
		this.getProcesos();
	}

	public String getFiltroEstadoProceso() {

		return this.filtroEstadoProceso;
	}

	public void setFiltroEstadoProceso(String filtroEstadoProceso) {

		this.filtroEstadoProceso = filtroEstadoProceso;
	}

	public void setProcesos(List<Proceso> procesos) {

		this.procesos = procesos;
	}

	public void eliminar(ActionEvent actionEvent) {

		this.procesoBC.eliminar(this.procesoSeleccionado.getProcesoId());
		this.procesoSeleccionado = new Proceso();
		String currentUser = FacesContext.getCurrentInstance()
				.getExternalContext().getUserPrincipal().getName();
		this.setProcesos(this.procesoBC.listar(this.getFiltroEstadoProceso(),
				currentUser));
		this.agregarMensaje("Proceso eliminado");
	}

	@Override
	protected List<Proceso> handleResultList() {

		// TODO Auto-generated method stub
		return null;
	}

	public void agregarMensaje(String mensaje) {

		this.facesContext.addMessage("suceso", new FacesMessage(mensaje));
	}

	public void agregarMensajeError(String mensaje) {

		this.facesContext.addMessage("error", new FacesMessage(
				FacesMessage.SEVERITY_ERROR, mensaje, null));
	}

	// Actividades

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
	private List<Usuario> sigteUsuariosPorRol;
	@NotNull(message="Debe seleccionar un responsable")
	private Usuario sigteUsuario;
	private List<Usuario> allUsuarios;
	private List<CronogramaDetalle> cronogramaDetallesporCronograma;
	private List<Actividad> actividadesPorProceso;

	private Actividad actividadSeleccionada;
	private CronogramaDetalle sigteCronogramaDetalle;
	private boolean subActividad;

	public void mostrarObsActividad() {

		this.setObservacion(this.observacionBC
				.getObsActividad(this.actividadSeleccionada.getActividadId()));

		String numeroActividad = this.actividadSeleccionada.getNroActividad();
		this.agregarMensaje("Actividad seleccionada: " + numeroActividad);
	}

	public Actividad getActividadSeleccionada() {

		return this.actividadSeleccionada;
	}

	public void setActividadSeleccionada(Actividad actividadSeleccionada) {

		this.actividadSeleccionada = actividadSeleccionada;
	}

	public Long getIdResponsable() {

		return this.idResponsable;
	}

	public void setIdResponsable(Long idResponsable) {

		this.idResponsable = idResponsable;
	}

	public Long getIdCronogramaDetalle() {

		return this.idCronogramaDetalle;
	}

	public void setIdCronogramaDetalle(Long idCronogramaDetalle) {

		this.idCronogramaDetalle = idCronogramaDetalle;
	}

	public Long getIdActividadAnterior() {

		return this.idActividadAnterior;
	}

	public void setIdActividadAnterior(Long idActividadAnterior) {

		this.idActividadAnterior = idActividadAnterior;
	}

	public Long getIdAlarma() {

		return this.idAlarma;
	}

	public void setIdAlarma(Long idAlarma) {

		this.idAlarma = idAlarma;
	}

	public Long getIdAlerta() {

		return this.idAlerta;
	}

	public void setIdAlerta(Long idAlerta) {

		this.idAlerta = idAlerta;
	}

	public Long getIdSuperTarea() {

		return this.idSuperTarea;
	}

	public void setIdSuperTarea(Long idSuperTarea) {

		this.idSuperTarea = idSuperTarea;
	}

	public String getNroActividad() {

		return this.nroActividad;
	}

	public void setNroActividad(String nroActividad) {

		this.nroActividad = nroActividad;
	}

	public String getDescripcion() {

		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {

		this.descripcion = descripcion;
	}

	public Date getFechaCreacion() {

		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {

		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaInicioPrevisto() {

		return this.fechaInicioPrevisto;
	}

	public void setFechaInicioPrevisto(Date fechaInicioPrevisto) {

		this.fechaInicioPrevisto = fechaInicioPrevisto;
	}

	public Date getFechaInicioReprogramado() {

		return this.fechaInicioReprogramado;
	}

	public void setFechaInicioReprogramado(Date fechaInicioReprogramado) {

		this.fechaInicioReprogramado = fechaInicioReprogramado;
	}

	public String getMotivoReprogramacionInicio() {

		return this.motivoReprogramacionInicio;
	}

	public void setMotivoReprogramacionInicio(String motivoReprogramacionInicio) {

		this.motivoReprogramacionInicio = motivoReprogramacionInicio;
	}

	public Date getFechaFinPrevista() {

		return this.fechaFinPrevista;
	}

	public void setFechaFinPrevista(Date fechaFinPrevista) {

		this.fechaFinPrevista = fechaFinPrevista;
	}

	public Date getFechaFinReprogramada() {

		return this.fechaFinReprogramada;
	}

	public void setFechaFinReprogramada(Date fechaFinReprogramada) {

		this.fechaFinReprogramada = fechaFinReprogramada;
	}

	public String getMotivoReprogramacion() {

		return this.motivoReprogramacion;
	}

	public void setMotivoReprogramacion(String motivoReprogramacion) {

		this.motivoReprogramacion = motivoReprogramacion;
	}

	public Date getFechaDevuelta() {

		return this.fechaDevuelta;
	}

	public void setFechaDevuelta(Date fechaDevuelta) {

		this.fechaDevuelta = fechaDevuelta;
	}

	public Date getFechaResuelta() {

		return this.fechaResuelta;
	}

	public void setFechaResuelta(Date fechaResuelta) {

		this.fechaResuelta = fechaResuelta;
	}

	public Date getFechaCancelacion() {

		return this.fechaCancelacion;
	}

	public void setFechaCancelacion(Date fechaCancelacion) {

		this.fechaCancelacion = fechaCancelacion;
	}

	public String getPregunta() {

		return this.pregunta;
	}

	public void setPregunta(String pregunta) {

		this.pregunta = pregunta;
	}

	public String getRespuesta() {

		return this.respuesta;
	}

	public void setRespuesta(String respuesta) {

		this.respuesta = respuesta;
	}

	public String getEstado() {

		return this.estado;
	}

	public void setEstado(String estado) {

		this.estado = estado;
	}

	public String getChecklistCompleto() {

		return this.checklistCompleto;
	}

	public void setChecklistCompleto(String checklistCompleto) {

		this.checklistCompleto = checklistCompleto;
	}

	public List<Usuario> getUsuariosPorRol() {

		if (this.actividadSeleccionada != null) {

			if (this.actividadSeleccionada.getCronogramaDetalle() == null
					&& this.actividadSeleccionada.getSuperTarea() != null) {
				// Es una subActividad, listar todos los usuarios posibles.
				this.usuariosPorRol = this.usuarioBC.findAll();
			} else {
				// Listar los usuarios de acuerdo al rol del cronogramaDetalle
				this.usuariosPorRol = this.usuarioBC
						.getUsuariosByRol(this.actividadSeleccionada
								.getCronogramaDetalle().getRolResponsable()
								.getRolId());
			}
		}

		return this.usuariosPorRol;
	}

	public void nextActividadUsuariosPorRol() {

		if (this.actividadSeleccionada != null) {
			// Obtener siguiente cronograma detalle para filtrar posibles
			// responsables
			CronogramaDetalle cd = this.cronogramaDetalleBC
					.getNextCronogramaDetalle(
							this.actividadSeleccionada.getCronogramaDetalle(),
							this.actividadSeleccionada.getRespuesta());

			// Listar los usuarios de acuerdo al rol del cronogramaDetalle
			// siguiente
			this.setSigteUsuariosPorRol(this.usuarioBC.getUsuariosByRol(cd
					.getRolResponsable().getRolId()));
		}

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

	public List<Usuario> getAllUsuarios() {

		if (this.actividadSeleccionada != null) {

			// Listar todos los usuarios posibles.
			this.allUsuarios = this.usuarioBC.findAll();
		}

		return this.allUsuarios;
	}

	public void setUsuariosPorRol(List<Usuario> usuariosPorRol) {

		this.usuariosPorRol = usuariosPorRol;
	}

	public List<CronogramaDetalle> getCronogramaDetallesporCronograma() {

		this.cronogramaDetallesporCronograma = this.cronogramaDetalleBC
				.listar();
		return this.cronogramaDetallesporCronograma;
	}

	public void setCronogramaDetallesporCronograma(
			List<CronogramaDetalle> cronogramaDetallesporCronograma) {

		this.cronogramaDetallesporCronograma = cronogramaDetallesporCronograma;
	}

	public List<Actividad> getActividadesPorProceso() {

		this.actividadesPorProceso = this.actividadBC.listar();
		return this.actividadesPorProceso;
	}

	public void setActividadesPorProceso(List<Actividad> actividadesPorProceso) {

		this.actividadesPorProceso = actividadesPorProceso;
	}

	private Usuario getResponsable() {

		return this.usuarioBC.load(this.getIdResponsable());
	}

	private CronogramaDetalle getCronogramaDetalle() {

		return this.cronogramaDetalleBC.load(this.getIdCronogramaDetalle());
	}

	private Actividad getActividadAnterior() {

		return this.actividadBC.load(this.getIdActividadAnterior());
	}

	private Actividad getSuperTarea() {

		return this.actividadBC.load(this.getIdSuperTarea());
	}

	public void registrarActividad() {

		if (this.procesoSeleccionado == null) {
			this.agregarMensaje("ERROR: Proceso NO seleccionado");
			this.limpiarCamposNuevo();
		} else {
			Proceso procesoSelec = this.procesoSeleccionado;

			Actividad actividad = new Actividad();

			actividad.setNroActividad(this.getNroActividad());
			actividad.setDescripcion(this.getDescripcion());
			actividad.setFechaCreacion(this.getFechaCreacion());
			actividad.setFechaInicioPrevisto(this.getFechaInicioPrevisto());
			actividad.setFechaInicioReprogramado(this
					.getFechaInicioReprogramado());
			actividad.setMotivoReprogramacionInicio(this
					.getMotivoReprogramacionInicio());
			actividad.setFechaFinPrevista(this.getFechaFinPrevista());
			actividad.setFechaFinReprogramada(this.getFechaFinReprogramada());
			actividad.setMotivoReprogramacion(this.getMotivoReprogramacion());
			actividad.setFechaDevuelta(this.getFechaDevuelta());
			actividad.setFechaResuelta(this.getFechaResuelta());
			actividad.setFechaCancelacion(this.getFechaCancelacion());
			actividad.setPregunta(this.getPregunta());
			actividad.setRespuesta(this.getRespuesta());
			actividad.setEstado(this.getEstado());
			actividad.setChecklistCompleto(this.getChecklistCompleto());

			actividad.setResponsable(this.getResponsable());
			actividad.setCronogramaDetalle(this.getCronogramaDetalle());
			actividad.setActividadAnterior(this.getActividadAnterior());
			actividad.setSuperTarea(this.getSuperTarea());

			/*
			 * actividad.setAlarma(getCronogramaDetalle().getAlarma());
			 * actividad.setAlerta(getCronogramaDetalle().getAlerta());
			 */
			actividad.setMaster(procesoSelec);
			this.actividadBC.registrar(actividad);
			this.actividades.add(actividad);
			this.agregarMensaje("Actividad creada");
			this.limpiarCamposNuevo();

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

		this.actividadBC.eliminar(this.actividadSeleccionada.getActividadId());
		int index = this.actividades.indexOf(this.actividadSeleccionada);
		this.actividades.remove(index);
		// detalleSeleccionado = new CronogramaDetalle();

		this.agregarMensaje("Actividad eliminada");
	}

	public void elegirActividad() {

		Actividad actividad = this.actividadSeleccionada;

		if (this.actividadSeleccionada.getCronogramaDetalle() != null) {
			this.setSigteCronogramaDetalle(this.cronogramaDetalleBC
					.getNextCronogramaDetalle(
							this.actividadSeleccionada.getCronogramaDetalle(),
							this.actividadSeleccionada.getRespuesta()));
			this.setSubActividad(false);
		} else {
			this.setSubActividad(true);
		}

		this.agregarMensaje("Actividad seleccionada: "
				+ actividad.getNroActividad());

	}

	public void elegirChecklistDetalle() {

		ActividadChecklistDetalle actividadCheklistDetalle = this.checklistDetalle;

		this.agregarMensaje("Item de Checklist seleccionado: "
				+ actividadCheklistDetalle.getDescripcion());

	}

	public void editarActividad() {

		if (this.actividadSeleccionada == null) {
			this.agregarMensaje("Actividad no seleccionada");
		} else {
			Actividad actividad = this.actividadSeleccionada;
			if (actividad.getResponsable() != null) {
				actividad.setResponsable(this.usuarioBC.load(actividad
						.getResponsable().getUsuarioId()));
			}
			try {
				this.actividadBC.editar(actividad);
				this.agregarMensaje("Actividad editada");
			} catch (Exception ex) {
				this.agregarMensajeError(ex.getMessage());
			}
		}
	}

	public List<Estado> getEstadosActividad() {

		return Definiciones.EstadoActividad.getEstadosActividad();
	}

	public List<Estado> getEstadosSubActividad() {

		return Definiciones.EstadoActividad.getEstadosSubActividad();
	}

	public List<Estado> getSiNoList() {

		return Definiciones.getSiNoList();
	}

	public Long getActividadSeleccionadaResponsable() {

		Long usuario = null;
		if (this.actividadSeleccionada != null
				&& this.actividadSeleccionada.getResponsable() != null) {
			usuario = this.actividadSeleccionada.getResponsable()
					.getUsuarioId();
		}

		return usuario;
	}

	public Date calculoFechaFin(DateSelectEvent event) {

		Calendar cal = new GregorianCalendar();
		cal.setTime(event.getDate());
		cal.add(Calendar.DATE, this.actividadSeleccionada
				.getCronogramaDetalle().getDuracionTarea().intValue());
		this.actividadSeleccionada.setFechaFinPrevista(cal.getTime());
		return cal.getTime();
	}

	public CronogramaDetalle getSigteCronogramaDetalle() {

		return this.sigteCronogramaDetalle;
	}

	public void setSigteCronogramaDetalle(
			CronogramaDetalle sigteCronogramaDetalle) {

		this.sigteCronogramaDetalle = sigteCronogramaDetalle;
	}

	public boolean isSubActividad() {

		return this.subActividad;
	}

	public void setSubActividad(boolean subActividad) {

		this.subActividad = subActividad;
	}
	
	public void resolverActividad() {

		if (this.actividadSeleccionada == null) {
			this.agregarMensaje("Actividad no seleccionada");
		} else {
			Actividad actividad = this.actividadSeleccionada;
			if (actividad.getResponsable() == null) {
				this.agregarMensaje("Debe seleccionar un responsable");
			}else{
				if (!this.validarSiPuedeResolverActividad()) {
					this.elegirProceso();
					return;
				}

				if(this.getSigteUsuario()!=null){
					this.actividadBC.resolveActividad(actividad,
							this.getSigteUsuario());
					if (actividad.getChecklistDetalle() != null) {
						actividad.setTieneChecklist(true);
					}
					this.elegirProceso();
					this.registrarObsP();
					this.agregarMensaje("Ha pasado a la siguiente Actividad");
					if (actividad.getResponsable() != null) {
						actividad.setResponsable(this.usuarioBC.load(actividad
								.getResponsable().getUsuarioId()));
					}
				} else {
					this.agregarMensajeError("Debe asignar un responsable");
				}
				
			}
		}
	}

	/**
	 * @return
	 */
	private boolean validarSiPuedeResolverActividad() {

		Actividad actividad = this.actividadSeleccionada;
		boolean aret = true;
		if (actividad.getPregunta() != null
				&& !actividad.getPregunta().equals("")
				&& (actividad.getRespuesta() == null || actividad
						.getRespuesta().equals(""))) {

			String mensaje = "No se puede resolver una actividad con pregunta y sin respuesta.";
			System.out.println("validarSiPuedeResolverActividad() " + mensaje);
			this.agregarMensajeError(mensaje);
			aret = false;

		} else if (actividad.getRespuesta() != null
				&& !actividad.getRespuesta().equals("SI")
				&& !actividad.getRespuesta().equals("NO")) {

			String mensaje = "La respuesta debe ser SI o NO.";
			System.out.println("validarSiPuedeResolverActividad() " + mensaje);
			this.agregarMensajeError(mensaje);
			aret = false;

		} else if (this.actividadBC.validarChecklistDetalles(actividad) == false) {

			String mensaje = "No puede pasar a la siguiente actividad sin cumplir con todo el checklist.";
			System.out.println("validarSiPuedeResolverActividad() " + mensaje);
			this.agregarMensajeError(mensaje);
			aret = false;

			// } else if
			// (actividadBC.existenFacturasSinCobro(actividad.getMaster())){
			//
			// String mensaje =
			// "No puede pasar a la siguiente actividad con Factura y sin fecha de cobro.";
			// System.out.println("validarSiPuedeResolverActividad() " +
			// mensaje);
			// agregarMensajeError(mensaje);
			// aret = false;

		} else if (this.procesoBC.existenSubTareasAbiertas(actividad)) {

			String mensaje = "No puede pasar a la siguiente actividad con subtareas no administrativas abiertas!";
			System.out.println("validarSiPuedeResolverActividad() " + mensaje);
			this.agregarMensajeError(mensaje);
			aret = false;

		}
		return aret;
	}

	public void finalizarProceso() {

		if (this.actividadSeleccionada == null) {
			this.agregarMensaje("Actividad no seleccionada");
		} else {
			try {
				Actividad actividad = this.actividadSeleccionada;
				if (actividad.getResponsable() != null) {
					actividad.setResponsable(this.usuarioBC.load(actividad
							.getResponsable().getUsuarioId()));
				}
				this.actividadBC.resolveActividad(actividad,
						this.getSigteUsuario());
				this.elegirProceso();
				this.agregarMensaje("Ha finalizado el Proceso");
			} catch (RuntimeException ex) {
				ex.printStackTrace();
				this.agregarMensajeError(ex.getMessage());
			}
		}
	}

	public void devolverActividad() {

		if (this.actividadSeleccionada == null) {
			this.agregarMensaje("Actividad no seleccionada");
		} else {
			try {
				if (!this.validarSiPuedeDevolverActividad()) {
					return;
				}

				Actividad actividad = this.actividadSeleccionada;
				if (actividad.getResponsable() != null) {
					actividad.setResponsable(this.usuarioBC.load(actividad
							.getResponsable().getUsuarioId()));
				}
				this.actividadBC.devolverActividad(actividad);
				this.elegirProceso();
				this.agregarMensaje("Actividad devuelta");
			} catch (RuntimeException ex) {
				ex.printStackTrace();
				this.agregarMensajeError(ex.getMessage());
			}
		}
	}

	/**
	 * @return
	 */
	private boolean validarSiPuedeDevolverActividad() {

		Actividad actividad = this.actividadSeleccionada;
		boolean aret = true;
		if (this.procesoBC.existenSubTareas(actividad)) {

			String mensaje = "No puede devolver la actividad si cuenta con subtareas!";
			System.out.println("validarSiPuedeResolverActividad() " + mensaje);
			this.agregarMensajeError(mensaje);
			aret = false;

		}
		return aret;
	}

	public void crearSubActividad() {

		if (this.actividadSeleccionada == null) {
			this.agregarMensaje("Actividad no seleccionada");
		} else {
			try {
				Actividad actividad = this.actividadSeleccionada;
				if (actividad.getResponsable() != null) {
					actividad.setResponsable(this.usuarioBC.load(actividad
							.getResponsable().getUsuarioId()));
				}
				this.actividadBC
						.crearSubActividad(actividad,
								this.actividadSeleccionada.getDescripcion(),
								this.actividadSeleccionada.getResponsable(),
								null, null);
				this.elegirProceso();
				this.agregarMensaje("SubActividad creada");
			} catch (RuntimeException ex) {
				ex.printStackTrace();
				this.agregarMensajeError(ex.getMessage());
			}
		}
	}

	public boolean getMostrarCampoRespuesta() {

		boolean show = false;
		if (this.actividadSeleccionada != null) {

			if (this.actividadSeleccionada.getPregunta() != null
					&& !this.actividadSeleccionada.getPregunta().equals("")) {

				show = true;
			}
		}
		return show;
	}

	// Observaciones

	private String descripcionObsP;
	private String descripcionObsA;

	public String getDescripcionObsA() {

		return this.descripcionObsA;
	}

	public void setDescripcionObsA(String descripcionObsA) {

		this.descripcionObsA = descripcionObsA;
	}

	private Observacion observacionSeleccionada;

	private Documento documentoSeleccionado;

	private ActividadChecklistDetalle checklistDetalle;

	public Documento getDocumentoSeleccionado() {

		return this.documentoSeleccionado;
	}

	public void setDocumentoSeleccionado(Documento documentoSeleccionado) {

		this.documentoSeleccionado = documentoSeleccionado;
	}

	public ActividadChecklistDetalle getChecklistDetalle() {

		return this.checklistDetalle;
	}

	public void setChecklistDetalle(ActividadChecklistDetalle checklistDetalle) {

		this.checklistDetalle = checklistDetalle;
	}

	public String getDescripcionObsP() {

		return this.descripcionObsP;
	}

	public Observacion getObservacionSeleccionada() {

		return this.observacionSeleccionada;
	}

	public void setObservacionSeleccionada(Observacion observacionSeleccionada) {

		this.observacionSeleccionada = observacionSeleccionada;
	}

	public void setDescripcionObsP(String descripcion) {

		this.descripcionObsP = descripcion;
	}

	public void registrarObsP() {

		if (this.procesoSeleccionado == null) {
			this.agregarMensaje("ERROR: Proceso NO seleccionado");
			this.setDescripcionObsP("");
		} else {
			if (this.descripcionObsP != null
					&& this.descripcionObsP.trim().length() != 0) {
				Proceso procesoSelec = this.procesoSeleccionado;

				Observacion obs = new Observacion();

				obs.setDescripcion(this.getDescripcionObsP());

				String nombreUsu = this.usuarioBC.getUsuarioActual();
				Usuario actual = this.usuarioBC.findSpecificUser(nombreUsu);

				obs.setUsuario(actual);
				obs.setFechaHora(new Date());
				obs.setEntidad("Proceso");
				obs.setIdEntidad(procesoSelec.getProcesoId());

				this.observacionBC.registrar(obs);
				if (this.observaciones != null) {
					List<Observacion> nuevasObservaciones = new ArrayList<Observacion>();
					nuevasObservaciones.add(obs);
					nuevasObservaciones.addAll(this.observaciones);
					this.observaciones = nuevasObservaciones;
				}
				this.agregarMensaje("Observacion creada");
				this.setDescripcionObsP("");
			}
		}
	}

	public void registrarObsA() {

		if (this.actividadSeleccionada == null) {
			this.agregarMensaje("ERROR: Actividad NO seleccionada");
			this.setDescripcionObsP("");
		} else {
			Actividad actividadSelec = this.actividadSeleccionada;

			Observacion obs = new Observacion();

			obs.setDescripcion(this.getDescripcionObsA());

			String nombreUsu = this.usuarioBC.getUsuarioActual();
			Usuario actual = this.usuarioBC.findSpecificUser(nombreUsu);

			obs.setUsuario(actual);
			obs.setFechaHora(new Date());
			obs.setEntidad("Actividad");
			obs.setIdEntidad(actividadSelec.getActividadId());

			this.observacionBC.registrar(obs);
			this.observaciones.add(obs);
			this.agregarMensaje("Observacion creada");
			this.setDescripcionObsA("");

		}
	}

	public void elegirObservacion() {

		Observacion obs = this.observacionSeleccionada;

		this.agregarMensaje("Observacion seleccionada: " + obs.getDescripcion());

	}

	public void editarChecklistDetalle() {

		if (this.checklistDetalle == null) {
			this.agregarMensaje("Item de Checklist no seleccionado");
		} else {
			ActividadChecklistDetalle edited = this.checklistDetalle;
			this.actividadChecklistDetalleBC.editar(edited);
			this.agregarMensaje("Item de Checklist editado");
		}
	}

	public void editarObservacion() {

		if (this.observacionSeleccionada == null) {
			this.agregarMensaje("Observacion no seleccionada");
		} else {
			String actual = this.usuarioBC.getUsuarioActual();
			String obsUsu = this.observacionSeleccionada.getUsuario()
					.getUsuario();
			if (obsUsu.equals(actual)) {
				Observacion obs = this.observacionSeleccionada;
				obs.setFechaHora(new Date());
				this.observacionBC.editar(obs);
				this.agregarMensaje("Observacion editada");

			} else {
				this.agregarMensaje("No tiene permisos para realizar esta operación");
			}
		}
	}

	public void eliminarObservacion(ActionEvent actionEvent) {

		if (this.observacionSeleccionada == null) {
			this.agregarMensaje("Observacion no seleccionada");
		} else {
			// String actual = usuarioBC.getUsuarioActual();
			// String obsUsu =
			// observacionSeleccionada.getUsuario().getUsuario();
			// if (obsUsu.equals(actual)) {

			this.observacionBC.eliminar(this.observacionSeleccionada
					.getObservacionId());
			int index = this.observaciones
					.indexOf(this.observacionSeleccionada);
			this.observaciones.remove(index);

			this.agregarMensaje("Observacion eliminada");

			// }
			// else {
			// agregarMensaje("No tiene permisos para realizar esta operación");
			// }
		}

	}

	// Archivos

	public void handleFileUpload(FileUploadEvent event) {

		// agregarMensaje("Success! " + event.getFile().getFileName() +
		// " is uploaded.");
		// Do what you want with the file
		try {
			this.copyFile(event.getFile().getFileName(), event.getFile()
					.getInputstream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyFile(String fileName, InputStream in) {

		if (this.procesoSeleccionado == null) {
			this.agregarMensaje("ERROR: Proceso NO seleccionado");
		} else {

			try {

				String currentUser = FacesContext.getCurrentInstance()
						.getExternalContext().getUserPrincipal().getName();
				Usuario usuarioActual = this.usuarioBC
						.findSpecificUser(currentUser);

				// get folder name
				String folder = this.carpetaFileUpload;
				if (folder == null) {
					folder = "";
				} else if (folder.equals("---Sin carpeta---")) {
					folder = "";
				} else if (folder.trim().equals("")) {
					folder = "";
				} else {
					folder += '/';
				}

				String nombreCliente = this.procesoSeleccionado.getCliente()
						.getNombre();
				String descCronog = this.procesoSeleccionado.getCronograma()
						.getNombre();
				String nroProceso = this.procesoSeleccionado.getNroProceso();
				String anho = "";

				int k = nroProceso.lastIndexOf('/');
				if (k > 0) {
					anho = nroProceso.substring(k - 4, k);
					nroProceso = nroProceso.substring(k + 1);
				}

				String extension = "";
				String nombreArchivo = "";
				int i = fileName.lastIndexOf('.');
				if (i > 0) {
					extension = fileName.substring(i + 1);
					nombreArchivo = fileName.substring(0, i);
				}

				String filePath = this.appProperties.getDocumentPath()
						+ nombreCliente + '/' + anho + '/' + descCronog + '/'
						+ nroProceso + '/' + folder;

				Documento documentoOrig = this.documentoBC
						.getDocumentoByFileName(nombreArchivo, filePath,
								extension);
				boolean puedoVer = this.documentoBC.puedoVer(documentoOrig,
						usuarioActual);
				if (!puedoVer) {

					// error, el archivo ya existe y el usuario actual no posee
					// privilegios para verlo
					this.agregarMensajeError("Error, el archivo ya existe y no posee privilegios sobre el mismo: "
							+ fileName + " !!");
				} else if (documentoOrig != null
						&& documentoOrig.getBloqueado().equals("Si")
						&& !documentoOrig.getUsuarioBloqueo().getUsuario()
								.equals(currentUser)) {

					// error, el archivoya existe y está bloqueado por otro
					// usuario
					this.agregarMensajeError("Error, el archivo: " + fileName
							+ " está bloqueado para edición!!\n"
							+ "Usuario que bloquéo: "
							+ documentoOrig.getUsuarioBloqueo().getUsuario()
							+ "\n" + "Fecha de bloqueo: "
							+ documentoOrig.getFechaBloqueo().toString());
				} else {

					// copiar el archivo
					File rootFolder = new File(
							this.appProperties.getDocumentPath());
					if (!rootFolder.exists()) {
						rootFolder.mkdir();
					}

					File clienteFolder = new File(
							this.appProperties.getDocumentPath()
									+ nombreCliente + '/');
					if (!clienteFolder.exists()) {
						clienteFolder.mkdir();
					}

					File anhoFolder = new File(
							this.appProperties.getDocumentPath()
									+ nombreCliente + '/' + anho + '/');
					if (!anhoFolder.exists()) {
						anhoFolder.mkdir();
					}

					File cronogFolder = new File(
							this.appProperties.getDocumentPath()
									+ nombreCliente + '/' + anho + '/'
									+ descCronog + '/');
					if (!cronogFolder.exists()) {
						cronogFolder.mkdir();
					}

					File procesoFolder = new File(
							this.appProperties.getDocumentPath()
									+ nombreCliente + '/' + anho + '/'
									+ descCronog + '/' + nroProceso + '/');
					if (!procesoFolder.exists()) {
						procesoFolder.mkdir();
					}

					File procesoSubFolder = new File(
							this.appProperties.getDocumentPath()
									+ nombreCliente + '/' + anho + '/'
									+ descCronog + '/' + nroProceso + '/'
									+ folder);
					if (!procesoSubFolder.exists()) {
						procesoSubFolder.mkdir();
					}

					OutputStream out = new FileOutputStream(new File(filePath
							+ fileName));
					int read = 0;
					byte[] bytes = new byte[1024];

					while ((read = in.read(bytes)) != -1) {
						out.write(bytes, 0, read);
					}

					in.close();
					out.flush();
					out.close();

					Proceso procesoSelec = this.procesoSeleccionado;
					if (documentoOrig == null) {

						// nuevo documento, insertar
						Documento doc = new Documento();
						doc.setFilename(nombreArchivo);
						doc.setFileExtension(extension);
						doc.setBloqueado("No");
						doc.setFilepath(filePath);
						doc.setEntidad("Proceso");
						doc.setIdEntidad(procesoSelec.getProcesoId());
						doc.setFechaUltimoUpdate(new Date());
						doc.setUsuarioCreacion(usuarioActual);

						this.documentoBC.registrar(doc);
						this.documentos.add(doc);
						this.agregarMensaje("Archivo subido correctamente");
					} else {

						// documento ya existente, actualizar
						documentoOrig.setFechaUltimoUpdate(new Date());
						this.documentoBC.editar(documentoOrig);
						this.documentos = this.documentoBC.getFileProceso(
								this.procesoSeleccionado.getProcesoId(),
								currentUser);
						this.agregarMensaje("Archivo subido y actualizado correctamente");
					}
				}

			} catch (IOException e) {
				this.agregarMensaje("Error subiendo el archivo");
				// System.out.println(e.getMessage());
			}
		}
	}

	public void elegirDocumento() {

		Documento doc = this.documentoSeleccionado;

		try {
			String downloadPath = doc.getFilepath();
			String downloadName = doc.getFilename();
			String downloadExt = doc.getFileExtension();

			InputStream stream = new FileInputStream(downloadPath
					+ downloadName + "." + downloadExt);
			StreamedContent archivo = new DefaultStreamedContent(stream,
					"application/octet-stream", downloadName + "."
							+ downloadExt);
			this.setFile(archivo);
			this.agregarMensaje("Archivo seleccionado :" + downloadName + "."
					+ downloadExt);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			this.agregarMensaje("Error seleccionando el archivo");
		}

	}

	private StreamedContent file;

	public void setFile(StreamedContent file) {

		this.file = file;
	}

	public StreamedContent getFile() {

		return this.file;
	}

	public void handleFileUploadA(FileUploadEvent event) {

		// agregarMensaje("Success! " + event.getFile().getFileName() +
		// " is uploaded.");
		// Do what you want with the file
		try {
			this.copyFileA(event.getFile().getFileName(), event.getFile()
					.getInputstream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyFileA(String fileName, InputStream in) {

		if (this.actividadSeleccionada == null) {
			this.agregarMensaje("ERROR: Actividad NO seleccionada");
		} else {

			try {

				String nombreCliente = this.procesoSeleccionado.getCliente()
						.getNombre();
				String descCronog = this.procesoSeleccionado.getCronograma()
						.getNombre();
				String nroProceso = this.procesoSeleccionado.getNroProceso();
				String anho = "";
				String nroActiv = this.actividadSeleccionada.getNroActividad();

				int k = nroProceso.lastIndexOf('/');
				if (k > 0) {
					anho = nroProceso.substring(k + 1);
					nroProceso = nroProceso.substring(0, k);
				}

				int h = nroActiv.lastIndexOf('/');
				if (h > 0) {
					nroActiv = nroActiv.substring(0, h);
				}

				File rootFolder = new File(this.appProperties.getDocumentPath());
				if (!rootFolder.exists()) {
					rootFolder.mkdir();
				}

				File clienteFolder = new File(
						this.appProperties.getDocumentPath() + nombreCliente
								+ '/');
				if (!clienteFolder.exists()) {
					clienteFolder.mkdir();
				}

				File anhoFolder = new File(this.appProperties.getDocumentPath()
						+ nombreCliente + '/' + anho + '/');
				if (!anhoFolder.exists()) {
					anhoFolder.mkdir();
				}

				File cronogFolder = new File(
						this.appProperties.getDocumentPath() + nombreCliente
								+ '/' + anho + '/' + descCronog + '/');
				if (!cronogFolder.exists()) {
					cronogFolder.mkdir();
				}

				File procesoFolder = new File(
						this.appProperties.getDocumentPath() + nombreCliente
								+ '/' + anho + '/' + descCronog + '/'
								+ nroProceso + '/');
				if (!procesoFolder.exists()) {
					procesoFolder.mkdir();
				}

				File activFolder = new File(
						this.appProperties.getDocumentPath() + nombreCliente
								+ '/' + anho + '/' + descCronog + '/'
								+ nroProceso + '/' + nroActiv + '/');
				if (!activFolder.exists()) {
					activFolder.mkdir();
				}

				// write the inputStream to a FileOutputStream
				OutputStream out = new FileOutputStream(new File(
						this.appProperties.getDocumentPath() + nombreCliente
								+ '/' + anho + '/' + descCronog + '/'
								+ nroProceso + '/' + nroActiv + '/' + fileName));

				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}

				in.close();
				out.flush();
				out.close();

				String extension = "";
				String nombreArchivo = "";

				int i = fileName.lastIndexOf('.');
				if (i > 0) {
					extension = fileName.substring(i + 1);
					nombreArchivo = fileName.substring(0, i);
				}

				Actividad activSelec = this.actividadSeleccionada;

				Documento doc = new Documento();

				doc.setFilename(nombreArchivo);
				doc.setFileExtension(extension);
				doc.setBloqueado("No");
				doc.setFechaBloqueo(new Date());
				doc.setFechaDesbloqueo(new Date());
				doc.setFilepath(this.appProperties.getDocumentPath()
						+ nombreCliente + '/' + anho + '/' + descCronog + '/'
						+ nroProceso + '/' + nroActiv + '/');

				String nombreUsu = this.usuarioBC.getUsuarioActual();
				Usuario actual = this.usuarioBC.findSpecificUser(nombreUsu);
				doc.setUsuarioBloqueo(actual);
				doc.setUsuarioDesbloqueo(actual);

				doc.setEntidad("Actividad");
				doc.setIdEntidad(activSelec.getActividadId());

				this.documentoBC.registrar(doc);
				this.documentos.add(doc);
				this.agregarMensaje("Archivo subido");

			} catch (IOException e) {
				this.agregarMensaje("Error subiendo el archivo");
				// System.out.println(e.getMessage());
			}
		}
	}

	public void eliminarDocumento(ActionEvent actionEvent) {

		try {
			if (this.documentoSeleccionado == null) {
				this.agregarMensaje("Documento no seleccionado");
			} else {
				this.documentoBC.eliminar(this.documentoSeleccionado
						.getDocumentoId());
				int index = this.documentos.indexOf(this.documentoSeleccionado);
				this.documentos.remove(index);

				this.agregarMensaje("Documento eliminado");

			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			this.agregarMensajeError(ex.getMessage());
		}
	}

	public boolean getIsAdminUser() {

		String currentUser = FacesContext.getCurrentInstance()
				.getExternalContext().getUserPrincipal().getName();

		return this.usuarioBC.isAdminUser(currentUser);
	}

	public Long getUsuarioId() {

		String currentUser = FacesContext.getCurrentInstance()
				.getExternalContext().getUserPrincipal().getName();
		Usuario usuario = this.usuarioBC.findSpecificUser(currentUser);
		return usuario.getUsuarioId();
	}

	public boolean getCanCreateProcess() {

		String currentUser = FacesContext.getCurrentInstance()
				.getExternalContext().getUserPrincipal().getName();

		return this.procesoBC.canCreateProcess(currentUser);
	}

	public boolean getCanControlFactura() {

		String currentUser = FacesContext.getCurrentInstance()
				.getExternalContext().getUserPrincipal().getName();

		return this.procesoBC.canControlFactura(currentUser);
	}

	public String getCarpetaFileUpload() {

		return this.carpetaFileUpload;
	}

	public void setCarpetaFileUpload(String carpetaFileUpload) {

		this.carpetaFileUpload = carpetaFileUpload;
	}

	public void updateCarpetas() {

		this.carpetas = null;
		this.carpetaFileUpload = "";
		if (this.procesoSeleccionado != null) {

			this.carpetas = this.procesoBC
					.getCarpetas(this.procesoSeleccionado);
		}
	}

	public List<String> getCarpetas() {

		return this.carpetas;
	}

	/*
	 * Manejo de roles para documentos
	 */
	// Lista dual para el pick list
	private DualListModel<String> listaDual;

	public DualListModel<String> getListaDual() {

		this.listaDual = new DualListModel<String>();
		if (this.documentoSeleccionado != null) {
			List<String> documentoRoles = this.procesoBC
					.getDocumentoRoles(this.documentoSeleccionado
							.getDocumentoId());
			List<String> rolesFiltrado = this.rolBC
					.getRolesFiltradosAsString(documentoRoles);
			this.listaDual = new DualListModel<String>(rolesFiltrado,
					documentoRoles);
		}
		return this.listaDual;
	}

	public void setListaDual(DualListModel<String> listaDual) {

		this.listaDual = listaDual;
	}

	// Manejo de roles
	public void guardarRoles() {

		try {

			if (this.documentoSeleccionado != null) {
				List<String> roles = this.listaDual.getTarget();
				this.procesoBC.guardarRoles(this.documentoSeleccionado, roles);
				this.agregarMensaje("Cambios guardados correctamente");
			}
		} catch (RuntimeException ex) {

			this.agregarMensajeError(ex.getMessage());
		}
	}

	public void bloquearDocumento() {

		if (this.documentoSeleccionado != null) {

			this.documentoBC.updateBloqueoDocumento(this.documentoSeleccionado,
					true);
			// String currentUser =
			// FacesContext.getCurrentInstance().getExternalContext()
			// .getUserPrincipal().getName();
			// this.setDocumentos(documentoBC.getFileProceso(procesoSeleccionado
			// .getProcesoId(), currentUser));
			this.agregarMensaje("Documento: "
					+ this.documentoSeleccionado.getFilename()
					+ " bloqueado correctamente");
		}
	}

	public void desbloquearDocumento() {

		if (this.documentoSeleccionado != null) {

			this.documentoBC.updateBloqueoDocumento(this.documentoSeleccionado,
					false);
			// String currentUser =
			// FacesContext.getCurrentInstance().getExternalContext()
			// .getUserPrincipal().getName();
			// this.setDocumentos(documentoBC.getFileProceso(procesoSeleccionado
			// .getProcesoId(), currentUser));
			this.agregarMensaje("Documento: "
					+ this.documentoSeleccionado.getFilename()
					+ " desbloqueado correctamente");
		}
	}

	public String printPdfProc() throws JRException {

		Map<String, Object> param = new HashMap<String, Object>();
		byte[] buffer = this.reporte
				.export(this.getProcesos(), param, Type.PDF);
		this.renderer.render(buffer, FileRenderer.ContentType.PDF,
				"reporteProc.pdf");
		return this.getNextView();

	}

	public void printPdfPendientes() throws JRException {

		Map<String, Object> param = new HashMap<String, Object>();
		List<ReportePendienteBean> list = reporteDAO
				.getDatosReportePendientes();
		byte[] buffer = this.reportePendientes.export(list, param, Type.PDF);
		this.renderer.render(buffer, FileRenderer.ContentType.PDF,
				"reportePendientes.pdf");
		//return this.getNextView();
	}
}
