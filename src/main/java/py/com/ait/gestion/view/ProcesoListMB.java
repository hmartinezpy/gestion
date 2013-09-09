package py.com.ait.gestion.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.FileUploadEvent;
import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;

import py.com.ait.gestion.business.ActividadBC;
import py.com.ait.gestion.business.CronogramaDetalleBC;
import py.com.ait.gestion.business.DocumentoBC;
import py.com.ait.gestion.business.ObservacionBC;
import py.com.ait.gestion.business.ProcesoBC;
import py.com.ait.gestion.business.TipoAlarmaBC;
import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.constant.Definiciones.Estado;
import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.domain.Documento;
import py.com.ait.gestion.domain.Observacion;
import py.com.ait.gestion.domain.Proceso;
import py.com.ait.gestion.domain.Usuario;

@ViewController
@NextView("/pg/proceso_edit.xhtml")
@PreviousView("/pg/proceso_list.xhtml")
public class ProcesoListMB extends AbstractListPageBean<Proceso, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private ProcesoBC procesoBC;

	@Inject
	private ObservacionBC observacionBC;

	@Inject
	private DocumentoBC documentoBC;

	private List<Proceso> procesos;
	private Proceso procesoSeleccionado;

	private List<Actividad> actividades;
	private List<Observacion> observaciones;
	private List<Documento> documentos;

	public Proceso getProcesoSeleccionado() {
		return procesoSeleccionado;
	}

	public void setProcesoSeleccionado(Proceso procesoSeleccionado) {
		this.procesoSeleccionado = procesoSeleccionado;
	}

	public List<Observacion> getObservaciones() {
		return observaciones;
	}

	public void setObservacion(List<Observacion> observaciones) {
		this.observaciones = observaciones;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public void elegirProceso() {
		setActividades(procesoBC.getActividadesByProceso(procesoSeleccionado));
		// setCronogramaDetallesporCronograma(procesoSeleccionado.getCronograma().getCronogramaDetalles());
		String numeroProceso = procesoSeleccionado.getNroProceso();
		agregarMensaje("Proceso seleccionado: " + numeroProceso);
	}

	public void mostrarObsProceso() {

		this.setObservacion(observacionBC.getObsProceso(procesoSeleccionado
				.getProcesoId()));

		String numeroProceso = procesoSeleccionado.getNroProceso();
		agregarMensaje("Proceso seleccionado: " + numeroProceso);
	}

	public void mostrarFileProceso() {

		this.setDocumentos(documentoBC.getFileProceso(procesoSeleccionado
				.getProcesoId()));

		String numeroProceso = procesoSeleccionado.getNroProceso();
		agregarMensaje("Proceso seleccionado: " + numeroProceso);
	}

	public void mostrarFileActividad() {

		this.setDocumentos(documentoBC.getFileActividad(actividadSeleccionada
				.getActividadId()));

		String numeroActividad = actividadSeleccionada.getNroActividad();
		agregarMensaje("Actividad seleccionada: " + numeroActividad);
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

	public void agregarMensaje(String mensaje) {
		facesContext.addMessage("suceso", new FacesMessage(mensaje));
	}

	public void agregarMensajeError(String mensaje) {
		facesContext.addMessage("error", new FacesMessage(
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
	private List<CronogramaDetalle> cronogramaDetallesporCronograma;
	private List<Actividad> actividadesPorProceso;

	private Actividad actividadSeleccionada;

	public void mostrarObsActividad() {

		this.setObservacion(observacionBC.getObsActividad(actividadSeleccionada
				.getActividadId()));

		String numeroActividad = actividadSeleccionada.getNroActividad();
		agregarMensaje("Actividad seleccionada: " + numeroActividad);
	}

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

		if (actividadSeleccionada != null)
			usuariosPorRol = usuarioBC.getUsuariosByRol(actividadSeleccionada
					.getCronogramaDetalle().getRolResponsable().getRolId());

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

	public void registrarActividad() {
		if (procesoSeleccionado == null) {
			agregarMensaje("ERROR: Proceso NO seleccionado");
			limpiarCamposNuevo();
		} else {
			Proceso procesoSelec = procesoSeleccionado;

			Actividad actividad = new Actividad();

			actividad.setNroActividad(getNroActividad());
			actividad.setDescripcion(getDescripcion());
			actividad.setFechaCreacion(getFechaCreacion());
			actividad.setFechaInicioPrevisto(getFechaInicioPrevisto());
			actividad.setFechaInicioReprogramado(getFechaInicioReprogramado());
			actividad
					.setMotivoReprogramacionInicio(getMotivoReprogramacionInicio());
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

			/*
			 * actividad.setAlarma(getCronogramaDetalle().getAlarma());
			 * actividad.setAlerta(getCronogramaDetalle().getAlerta());
			 */
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
		// detalleSeleccionado = new CronogramaDetalle();

		agregarMensaje("Actividad eliminada");
	}

	public void elegirActividad() {
		Actividad actividad = actividadSeleccionada;

		agregarMensaje("Actividad seleccionada: " + actividad.getNroActividad());

	}

	public void editarActividad() {
		if (actividadSeleccionada == null) {
			agregarMensaje("Actividad no seleccionada");
		} else {
			Actividad actividad = actividadSeleccionada;
			if (actividad.getResponsable() != null) {
				actividad.setResponsable(usuarioBC.load(actividad
						.getResponsable().getUsuarioId()));
			}
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
		if (actividadSeleccionada != null
				&& actividadSeleccionada.getResponsable() != null)
			usuario = actividadSeleccionada.getResponsable().getUsuarioId();

		return usuario;
	}

	public Date calculoFechaFin(DateSelectEvent event) {

		Calendar cal = new GregorianCalendar();
		cal.setTime((Date) event.getDate());
		cal.add(Calendar.DATE, actividadSeleccionada.getCronogramaDetalle()
				.getDuracionTarea().intValue());
		actividadSeleccionada.setFechaFinPrevista(cal.getTime());
		return cal.getTime();
	}

	public void resolverActividad() {
		if (actividadSeleccionada == null) {
			agregarMensaje("Actividad no seleccionada");
		} else {
			try {
				Actividad actividad = actividadSeleccionada;
				if (actividad.getResponsable() != null) {
					actividad.setResponsable(usuarioBC.load(actividad
							.getResponsable().getUsuarioId()));
				}
				actividadBC.resolveActividad(actividad);
				elegirProceso();
				agregarMensaje("Actividad editada");
			} catch (RuntimeException ex) {
				ex.printStackTrace();
				agregarMensajeError(ex.getMessage());
			}
		}
	}

	public boolean getMostrarCampoRespuesta() {

		boolean show = false;
		if (actividadSeleccionada != null) {

			if (actividadSeleccionada.getPregunta() != null
					&& !actividadSeleccionada.getPregunta().equals("")) {

				show = true;
			}
		}
		return show;
	}

	// Observaciones

	private String descripcionObsP;
	private String descripcionObsA;

	public String getDescripcionObsA() {
		return descripcionObsA;
	}

	public void setDescripcionObsA(String descripcionObsA) {
		this.descripcionObsA = descripcionObsA;
	}

	private Observacion observacionSeleccionada;

	private Documento documentoSeleccionado;

	public Documento getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	public void setDocumentoSeleccionado(Documento documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
	}

	public String getDescripcionObsP() {
		return descripcionObsP;
	}

	public Observacion getObservacionSeleccionada() {
		return observacionSeleccionada;
	}

	public void setObservacionSeleccionada(Observacion observacionSeleccionada) {
		this.observacionSeleccionada = observacionSeleccionada;
	}

	public void setDescripcionObsP(String descripcion) {
		this.descripcionObsP = descripcion;
	}

	public void registrarObsP() {
		if (procesoSeleccionado == null) {
			agregarMensaje("ERROR: Proceso NO seleccionado");
			this.setDescripcionObsP("");
		} else {
			Proceso procesoSelec = procesoSeleccionado;

			Observacion obs = new Observacion();

			obs.setDescripcion(getDescripcionObsP());

			String nombreUsu = usuarioBC.getUsuarioActual();
			Usuario actual = usuarioBC.findSpecificUser(nombreUsu);

			obs.setUsuario(actual);
			obs.setFechaHora(new Date());
			obs.setEntidad("Proceso");
			obs.setIdEntidad(procesoSelec.getProcesoId());

			observacionBC.registrar(obs);
			observaciones.add(obs);
			agregarMensaje("Observacion creada");
			this.setDescripcionObsP("");

		}
	}

	public void registrarObsA() {
		if (actividadSeleccionada == null) {
			agregarMensaje("ERROR: Actividad NO seleccionada");
			this.setDescripcionObsP("");
		} else {
			Actividad actividadSelec = actividadSeleccionada;

			Observacion obs = new Observacion();

			obs.setDescripcion(getDescripcionObsA());

			String nombreUsu = usuarioBC.getUsuarioActual();
			Usuario actual = usuarioBC.findSpecificUser(nombreUsu);

			obs.setUsuario(actual);
			obs.setFechaHora(new Date());
			obs.setEntidad("Actividad");
			obs.setIdEntidad(actividadSelec.getActividadId());

			observacionBC.registrar(obs);
			observaciones.add(obs);
			agregarMensaje("Observacion creada");
			this.setDescripcionObsA("");

		}
	}

	public void elegirObservacion() {
		Observacion obs = observacionSeleccionada;

		agregarMensaje("Observacion seleccionada: " + obs.getDescripcion());

	}

	public void editarObservacion() {
		if (observacionSeleccionada == null) {
			agregarMensaje("Observacion no seleccionada");
		} else {
			String actual = usuarioBC.getUsuarioActual();
			String obsUsu = observacionSeleccionada.getUsuario().getUsuario();
			// if( obsUsu.equals(actual)){
			Observacion obs = observacionSeleccionada;
			obs.setFechaHora(new Date());
			observacionBC.editar(obs);
			agregarMensaje("Observacion editada");

			// }else{
			// agregarMensaje("No tiene permisos para realizar esta operación");
			// }
		}
	}

	public void eliminarObservacion(ActionEvent actionEvent) {

		if (observacionSeleccionada == null) {
			agregarMensaje("Observacion no seleccionada");
		} else {
			// if(observacionSeleccionada.getUsuario().getUsuario() ==
			// usuarioBC.getUsuarioActual()){
			observacionBC.eliminar(observacionSeleccionada.getObservacionId());
			int index = observaciones.indexOf(observacionSeleccionada);
			observaciones.remove(index);

			agregarMensaje("Observacion eliminada");

			// }else{
			// agregarMensaje("No tiene permisos para realizar esta operación");
			// }
		}

	}

	// Archivos

	private String destination = "/tmp/jboss";

	public void handleFileUpload(FileUploadEvent event) {

		// agregarMensaje("Success! " + event.getFile().getFileName() +
		// " is uploaded.");
		// Do what you want with the file
		try {
			copyFile(event.getFile().getFileName(), event.getFile()
					.getInputstream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyFile(String fileName, InputStream in) {
		if (procesoSeleccionado == null) {
			agregarMensaje("ERROR: Proceso NO seleccionado");
		} else {

			try {

				// write the inputStream to a FileOutputStream
				OutputStream out = new FileOutputStream(new File(destination
						+ fileName));

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

				Proceso procesoSelec = procesoSeleccionado;

				Documento doc = new Documento();

				doc.setFilename(nombreArchivo);
				doc.setFileExtension(extension);
				doc.setBloqueado("Si");
				doc.setFechaBloqueo(new Date());
				doc.setFilepath(destination);

				String nombreUsu = usuarioBC.getUsuarioActual();
				Usuario actual = usuarioBC.findSpecificUser(nombreUsu);
				doc.setUsuarioBloqueo(actual);

				doc.setEntidad("Proceso");
				doc.setIdEntidad(procesoSelec.getProcesoId());

				documentoBC.registrar(doc);
				documentos.add(doc);
				agregarMensaje("Archivo subido");

			} catch (IOException e) {
				agregarMensaje("Error subiendo el archivo");
				// System.out.println(e.getMessage());
			}
		}
	}

	public void handleFileUploadA(FileUploadEvent event) {

		// agregarMensaje("Success! " + event.getFile().getFileName() +
		// " is uploaded.");
		// Do what you want with the file
		try {
			copyFileA(event.getFile().getFileName(), event.getFile()
					.getInputstream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyFileA(String fileName, InputStream in) {
		if (actividadSeleccionada == null) {
			agregarMensaje("ERROR: Actividad NO seleccionada");
		} else {

			try {

				// write the inputStream to a FileOutputStream
				OutputStream out = new FileOutputStream(new File(destination
						+ fileName));

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

				Actividad actividadSelec = actividadSeleccionada;

				Documento doc = new Documento();

				doc.setFilename(nombreArchivo);
				doc.setFileExtension(extension);
				doc.setBloqueado("Si");
				doc.setFechaBloqueo(new Date());
				doc.setFilepath(destination);

				String nombreUsu = usuarioBC.getUsuarioActual();
				Usuario actual = usuarioBC.findSpecificUser(nombreUsu);
				doc.setUsuarioBloqueo(actual);

				doc.setEntidad("Actividad");
				doc.setIdEntidad(actividadSelec.getActividadId());

				documentoBC.registrar(doc);
				documentos.add(doc);
				agregarMensaje("Archivo subido");

			} catch (IOException e) {
				agregarMensaje("Error subiendo el archivo");
				// System.out.println(e.getMessage());
			}
		}
	}

	public void eliminarDocumento(ActionEvent actionEvent) {

		if (documentoSeleccionado == null) {
			agregarMensaje("Documento no seleccionado");
		} else {
			documentoBC.eliminar(documentoSeleccionado.getDocumentoId());
			int index = documentos.indexOf(documentoSeleccionado);
			documentos.remove(index);

			agregarMensaje("Documento eliminado");

		}

	}

}
