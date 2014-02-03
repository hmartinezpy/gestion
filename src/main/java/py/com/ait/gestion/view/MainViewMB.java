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
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;

import py.com.ait.gestion.business.ActividadBC;
import py.com.ait.gestion.business.ActividadChecklistDetalleBC;
import py.com.ait.gestion.business.ClienteBC;
import py.com.ait.gestion.business.CronogramaBC;
import py.com.ait.gestion.business.CronogramaDetalleBC;
import py.com.ait.gestion.business.DocumentoBC;
import py.com.ait.gestion.business.NotificacionBC;
import py.com.ait.gestion.business.ObservacionBC;
import py.com.ait.gestion.business.ProcesoBC;
import py.com.ait.gestion.business.RolBC;
import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.constant.AppProperties;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.constant.Definiciones.Estado;
import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.ActividadChecklistDetalle;
import py.com.ait.gestion.domain.Cliente;
import py.com.ait.gestion.domain.Cronograma;
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.domain.Documento;
import py.com.ait.gestion.domain.Notificacion;
import py.com.ait.gestion.domain.Observacion;
import py.com.ait.gestion.domain.Proceso;
import py.com.ait.gestion.domain.Usuario;

@ViewController
@NextView("/pg/proceso_edit.xhtml")
@PreviousView("/pg/main_view.xhtml")
public class MainViewMB extends AbstractEditPageBean<Proceso, Long>{

	private static final long serialVersionUID = 1L;
	/* Variables para el árbol de la izquierda */
	private TreeNode items;
	private TreeNode selectedItem;
	private Cronograma cronogramaSeleccionado;
	private String filtroEstadoProceso = "A";

	/* Usuario actual de la aplicación*/
	private String currentUser;
	private boolean isAdminUser;
	private Long usuarioId;

	/* Listas para DataTables */
	private List<Proceso> procesos;
	private List<Actividad> actividades;
	private List<Notificacion> notificaciones;
	private List<Observacion> observaciones;
	private List<Documento> documentos;
	private List<ActividadChecklistDetalle> checklist;

	/* Objetos seleccionados en DataTables */
	private Proceso procesoSeleccionado;
	private Actividad actividadSeleccionada;
	private Observacion observacionSeleccionada;
	private Documento documentoSeleccionado;
	private ActividadChecklistDetalle checklistDetalle;

	/* Listas para combos (selectOne) */
	private List<Usuario> usuariosPorRol;
	private List<Usuario> sigteUsuariosPorRol;
	private List<Usuario> allUsuarios;
	private List<String> carpetas;
	private List<Cliente> clientes;
	
	/* Objetos seleccionados en combos (selectOne) */
	private Usuario sigteUsuario;
	private Cliente clienteSeleccionado;

	/* Variable utilizada para guardar la descripción de la observación */
	private String descripcionObservacion;
	/* Variable utilizada para indicar si un archivo debe ir a un subdirectorio */
	private String carpetaFileUpload;

	private CronogramaDetalle sigteCronogramaDetalle;
	private boolean subActividad;

	/* Business Components necesarios */
	@Inject
	private CronogramaBC cronogramaBC;
	@Inject
	private CronogramaDetalleBC cronogramaDetalleBC;
	@Inject
	private ActividadBC actividadBC;
	@Inject
	private UsuarioBC usuarioBC;
	@Inject
	private ProcesoBC procesoBC;
	@Inject
	private NotificacionBC notificacionBC;
	@Inject
	private DocumentoBC documentoBC;
	@Inject
	private AppProperties appProperties;
	@Inject
	private RolBC rolBC;
	@Inject
	private ObservacionBC observacionBC;

	@Inject
	private ActividadChecklistDetalleBC actividadChecklistDetalleBC;

	@Inject
	private ClienteBC clienteBC;

	@PostConstruct
	public void init() {
		getCurrentUserData();
		createTree();
		setClientes(clienteBC.listar());
		if(this.getBean().getProcesoId() != null){
			setProcesoId(this.getBean().getProcesoId());
		}
	}

	private void createTree() {
		items = new DefaultTreeNode("root", null);

		List<Cronograma> cronogramas = cronogramaBC.getCronogramasForUser(filtroEstadoProceso, currentUser);

		@SuppressWarnings("unused")
		TreeNode treeNode = null;

		for(Cronograma item: cronogramas){
			treeNode = new DefaultTreeNode(item, items);
		}

		procesos = new ArrayList<Proceso>();
	}

	public TreeNode getItems() {
		return items;
	}

	public TreeNode getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(TreeNode selectedItem) {
		this.selectedItem = selectedItem;
	}

	public String getFiltroEstadoProceso() {
		return filtroEstadoProceso;
	}

	public void setFiltroEstadoProceso(String filtroEstadoProceso) {
		this.filtroEstadoProceso = filtroEstadoProceso;
	}

	private void getCurrentUserData() {
		currentUser = FacesContext.getCurrentInstance()
				.getExternalContext().getUserPrincipal().getName();

		isAdminUser = this.usuarioBC.isAdminUser(currentUser);

		Usuario usuario = this.usuarioBC.findSpecificUser(currentUser);

		usuarioId = usuario.getUsuarioId();
	}

	public List<Proceso> getProcesos() {
		return procesos;
	}

	public Proceso getProcesoSeleccionado() {
		return procesoSeleccionado;
	}

	public void setProcesoSeleccionado(Proceso proceso) {
		this.procesoSeleccionado = proceso;
	}

	public void agregarMensaje(String mensaje) {
		agregarMensaje(null, mensaje);
	}

	public void agregarMensaje(String titulo, String mensaje) {

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Seleccionado", mensaje);
		FacesContext.getCurrentInstance().addMessage(titulo, message);
	}

	public void agregarMensajeError(String mensaje) {

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null);
		FacesContext.getCurrentInstance().addMessage("Error", message);
	}

	public void onNodeSelect(NodeSelectEvent event) {

		Cronograma nodo = (Cronograma) event.getTreeNode().getData();
		this.cronogramaSeleccionado = nodo;
		this.clienteSeleccionado = null;
		procesos = procesoBC.getProcesosByCronogramaForUser(filtroEstadoProceso, nodo.getCronogramaId(), currentUser);
		actividades = null;

		agregarMensaje(nodo.getNombre());
	}

	public void onProcesoRowSelect(SelectEvent event) {

		Proceso procesoSeleccionado = (Proceso) event.getObject();
		
		elegirProceso(procesoSeleccionado);
	}

	public void onActividadRowSelect(SelectEvent event) {

		Actividad actividadSeleccionada = (Actividad) event.getObject();

		if (actividadSeleccionada.getCronogramaDetalle() != null) {
			this.setSigteCronogramaDetalle(this.cronogramaDetalleBC
					.getNextCronogramaDetalle(
							actividadSeleccionada.getCronogramaDetalle(),
							actividadSeleccionada.getRespuesta()));
			this.setSubActividad(false);
		} else {
			this.setSubActividad(true);
		}

		String titulo = "Actividad "+actividadSeleccionada.getNroActividad()+" Seleccionada";
		String mensaje = actividadSeleccionada.getDescripcion();

		agregarMensaje(titulo, mensaje);
	}

	public void updateFiltroEstadoProceso() {

		createTree();
		procesos = null;
		actividades = null;
	}

	public void filtrarProcesos(){
		Long cronogramaId = null;
		if (cronogramaSeleccionado != null)
			cronogramaId = cronogramaSeleccionado.getCronogramaId();

		Long clienteId = null;
		if (clienteSeleccionado != null)
			clienteId = clienteSeleccionado.getClienteId();

		procesos = procesoBC.getProcesosFilteredForUser(filtroEstadoProceso, cronogramaId, clienteId, currentUser);
		actividades = null;
	}

	public void elegirProceso(Proceso proceso){
		this.procesoSeleccionado = proceso;

		this.setActividades(this.procesoBC.getActividadesByProceso(procesoSeleccionado, currentUser));

		agregarMensaje(procesoSeleccionado.getDescripcion());
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

	public List<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}

	public List<Notificacion> getNotificaciones() {

		this.notificaciones = notificacionBC.getNotificaciones(currentUser, null);

		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public Actividad getActividadSeleccionada() {
		return actividadSeleccionada;
	}

	public void setActividadSeleccionada(Actividad actividad) {
		this.actividadSeleccionada = actividad;
	}

	public boolean isSubActividad() {

		return this.subActividad;
	}

	public void setSubActividad(boolean subActividad) {

		this.subActividad = subActividad;
	}

	public Date calculoFechaFin(DateSelectEvent event) {

		Calendar cal = new GregorianCalendar();
		cal.setTime(event.getDate());
		cal.add(Calendar.DATE, this.actividadSeleccionada
				.getCronogramaDetalle().getDuracionTarea().intValue());
		this.actividadSeleccionada.setFechaFinPrevista(cal.getTime());
		return cal.getTime();
	}

	public boolean getCanControlFactura() {

		return this.procesoBC.canControlFactura(currentUser);
	}

	public boolean getCanCreateProcess() {

		return this.procesoBC.canCreateProcess(currentUser);
	}


	public Long getUsuarioId() {

		return usuarioId;
	}

	public boolean getIsAdminUser() {

		return this.isAdminUser;
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

	public List<Estado> getEstadosActividad() {

		return Definiciones.EstadoActividad.getEstadosActividad();
	}

	public List<Estado> getEstadosSubActividad() {

		return Definiciones.EstadoActividad.getEstadosSubActividad();
	}

	public List<Usuario> getSigteUsuariosPorRol() {
		return sigteUsuariosPorRol;
	}

	public void setSigteUsuariosPorRol(List<Usuario> sigteUsuariosPorRol) {
		this.sigteUsuariosPorRol = sigteUsuariosPorRol;
	}

	public List<Observacion> getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(List<Observacion> observaciones) {
		this.observaciones = observaciones;
	}

	public Observacion getObservacionSeleccionada() {
		return observacionSeleccionada;
	}

	public void setObservacionSeleccionada(Observacion observacionSeleccionada) {
		this.observacionSeleccionada = observacionSeleccionada;
	}

	public String getDescripcionObservacion() {
		return descripcionObservacion;
	}

	public void setDescripcionObservacion(String descripcionObservacion) {
		this.descripcionObservacion = descripcionObservacion;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public List<ActividadChecklistDetalle> getChecklist() {
		return checklist;
	}

	public void setChecklist(List<ActividadChecklistDetalle> checklist) {
		this.checklist = checklist;
	}

	public String getCarpetaFileUpload() {
		return carpetaFileUpload;
	}

	public void setCarpetaFileUpload(String carpetaFileUpload) {
		this.carpetaFileUpload = carpetaFileUpload;
	}

	public List<String> getCarpetas() {
		return carpetas;
	}

	public void setCarpetas(List<String> carpetas) {
		this.carpetas = carpetas;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Usuario getSigteUsuario() {
		return sigteUsuario;
	}

	public void setSigteUsuario(Usuario sigteUsuario) {
		this.sigteUsuario = sigteUsuario;
	}

	public Cliente getClienteSeleccionado() {
		return clienteSeleccionado;
	}

	public void setClienteSeleccionado(Cliente clienteSeleccionado) {
		this.clienteSeleccionado = clienteSeleccionado;
	}

	public Documento getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	public void setDocumentoSeleccionado(Documento documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
	}

	public ActividadChecklistDetalle getChecklistDetalle() {
		return checklistDetalle;
	}

	public void setChecklistDetalle(ActividadChecklistDetalle checklistDetalle) {
		this.checklistDetalle = checklistDetalle;
	}

	public CronogramaDetalle getSigteCronogramaDetalle() {
		return sigteCronogramaDetalle;
	}

	public void setSigteCronogramaDetalle(CronogramaDetalle sigteCronogramaDetalle) {
		this.sigteCronogramaDetalle = sigteCronogramaDetalle;
	}

	public List<Estado> getSiNoList() {

		return Definiciones.getSiNoList();
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

	public List<Usuario> getAllUsuarios() {

		if (this.actividadSeleccionada != null) {

			// Listar todos los usuarios posibles.
			this.allUsuarios = this.usuarioBC.findAll();
		}

		return this.allUsuarios;
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

	public void resolverActividad() {

		if (this.actividadSeleccionada == null) {
			this.agregarMensaje("Actividad no seleccionada");
		} else {
			Actividad actividad = this.actividadSeleccionada;
			if (!this.validarSiPuedeResolverActividad()) {
				elegirProceso(procesoSeleccionado);
				return;
			}

			try {
				this.actividadBC.resolveActividad(actividad,
						this.getSigteUsuario());
				if (actividad.getChecklistDetalle() != null) {
					actividad.setTieneChecklist(true);
				}
				this.registrarObservacion();
				Proceso elProceso = procesoBC.load(actividad.getMaster().getProcesoId());
				this.elegirProceso(elProceso);
				this.agregarMensaje("Ha pasado a la siguiente Actividad");
			} catch (RuntimeException ex) {
				ex.printStackTrace();
				this.agregarMensajeError(ex.getMessage());
			}
			if (actividad.getResponsable() != null) {
				actividad.setResponsable(this.usuarioBC.load(actividad
						.getResponsable().getUsuarioId()));
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
				//XXX: ?!?!
				this.elegirProceso(procesoSeleccionado);
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
		if(actividad.getActividadAnterior() == null) {
			String mensaje = "No puede devolver la primera actividad, ya que no existe actividad previa";
			System.out.println("validarSiPuedeResolverActividad() " + mensaje);
			this.agregarMensajeError(mensaje);
			aret = false;
		}
		if (this.procesoBC.existenSubTareas(actividad)) {

			String mensaje = "No puede devolver la actividad si cuenta con subtareas!";
			System.out.println("validarSiPuedeResolverActividad() " + mensaje);
			this.agregarMensajeError(mensaje);
			aret = false;

		}
		return aret;
	}
	
	private String subActividadDescripcion;
	private long subActividadResponsable;

	public String getSubActividadDescripcion() {

		return subActividadDescripcion;
	}

	
	public void setSubActividadDescripcion(String subActividadDescripcion) {

		this.subActividadDescripcion = subActividadDescripcion;
	}

	
	public long getSubActividadResponsable() {

		return subActividadResponsable;
	}

	
	public void setSubActividadResponsable(long subActividadResponsable) {

		this.subActividadResponsable = subActividadResponsable;
	}

	public void nuevaSubActividad(){
		this.subActividadResponsable=0;
		this.subActividadDescripcion=null;
	}

	public void crearSubActividad() {

		if (this.actividadSeleccionada == null) {
			this.agregarMensaje("Actividad no seleccionada");
		} else {
			try {
				Actividad actividad = this.actividadSeleccionada;
				if (this.subActividadResponsable!=0) {
					actividad.setResponsable(this.usuarioBC.load(subActividadResponsable));
				}
				this.actividadBC
				.crearSubActividad(actividad,
						this.subActividadDescripcion,
						actividad.getResponsable(),
						null, null);
				/*this.actividadBC
						.crearSubActividad(actividad,
								this.actividadSeleccionada.getDescripcion(),
								this.actividadSeleccionada.getResponsable(),
								null, null);*/
				//XXX: ?!?!
				this.elegirProceso(procesoSeleccionado);
				this.agregarMensaje("SubActividad creada");
			} catch (RuntimeException ex) {
				ex.printStackTrace();
				this.agregarMensajeError(ex.getMessage());
			}
		}
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
				//XXX: ?!?!
				this.elegirProceso(procesoSeleccionado);
				this.agregarMensaje("Ha finalizado el Proceso");
			} catch (RuntimeException ex) {
				ex.printStackTrace();
				this.agregarMensajeError(ex.getMessage());
			}
		}
	}

	public void mostrarObsProceso() {

		this.setObservaciones(this.observacionBC
				.getObsProceso(this.procesoSeleccionado.getProcesoId()));

	}

	public void mostrarFileProceso() {
		System.out.println("mostrarFileProceso de proceso "+this.procesoSeleccionado);
		agregarMensaje("Documentos", "Mostrar documentos de proceso: " + procesoSeleccionado.getDescripcion());
		this.setDocumentos(this.documentoBC.getFileProceso(
				this.procesoSeleccionado.getProcesoId(), currentUser));

		//XXX: ?!?!
		this.elegirProceso(procesoSeleccionado);
		this.updateCarpetas();
	}

	public void updateCarpetas() {

		this.setCarpetas(null);
		this.setCarpetaFileUpload("");
		if (this.procesoSeleccionado != null) {

			this.setCarpetas(this.procesoBC
					.getCarpetas(this.procesoSeleccionado));
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

	// CheckList

	public void mostrarChecklist() {

		this.setChecklist(this.actividadChecklistDetalleBC
				.getChecklistByActividad(this.actividadSeleccionada));

		this.agregarMensaje("Actividad seleccionada: "
				+ this.actividadSeleccionada.getNroActividad());
	}

	public void elegirChecklistDetalle() {

		ActividadChecklistDetalle actividadCheklistDetalle = this.getChecklistDetalle();

		this.agregarMensaje("Item de Checklist seleccionado: "
				+ actividadCheklistDetalle.getDescripcion());

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

	// Archivos

	private StreamedContent file;

	public void setFile(StreamedContent file) {

		this.file = file;
	}

	public StreamedContent getFile() {

		return this.file;
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
			this.agregarMensaje("Documento: "
					+ this.documentoSeleccionado.getFilename()
					+ " bloqueado correctamente");
		}
	}

	public void desbloquearDocumento() {

		if (this.documentoSeleccionado != null) {

			this.documentoBC.updateBloqueoDocumento(this.documentoSeleccionado,
					false);

			this.agregarMensaje("Documento: "
					+ this.documentoSeleccionado.getFilename()
					+ " desbloqueado correctamente");
		}
	}

	public void handleFileUpload(FileUploadEvent event) {

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

					// error, el archivo ya existe y está bloqueado por otro
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

	public void registrarObservacion() {

		if (this.procesoSeleccionado == null) {
			this.agregarMensaje("ERROR: Proceso NO seleccionado");
			this.setDescripcionObservacion("");
		} else {
			if (this.descripcionObservacion != null
					&& this.descripcionObservacion.trim().length() != 0) {
			Proceso procesoSelec = this.procesoSeleccionado;

			Observacion obs = new Observacion();

			obs.setDescripcion(this.getDescripcionObservacion());

			Usuario actual = this.usuarioBC.findSpecificUser(currentUser);

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
			this.setDescripcionObservacion("");
			}
		}
	}

	public void eliminarObservacion(ActionEvent actionEvent) {

		if (this.observacionSeleccionada == null) {
			this.agregarMensaje("Observacion no seleccionada");
		} else {

			this.observacionBC.eliminar(this.observacionSeleccionada
					.getObservacionId());
			int index = this.observaciones
					.indexOf(this.observacionSeleccionada);
			this.observaciones.remove(index);

			this.agregarMensaje("Observacion eliminada");

		}
	}

	public void setProcesoId(Long procesoId){
		//Obtener el proceso a partir del id
		Proceso proceso = procesoBC.load(procesoId);
		//Marcar en el árbol el cronograma del proceso recibido como parámetro
		this.cronogramaSeleccionado=proceso.getCronograma();
		setSelectedTreeNode(proceso.getCronograma().toString());

		//Establecer la lista de procesos a los procesos de ese cronograma
		procesos = procesoBC.getProcesosByCronogramaForUser(filtroEstadoProceso, proceso.getCronograma().getCronogramaId(), currentUser);

		//Elegir el proceso con id recibido como parámetro en el datatable
		elegirProceso(proceso);
		
	}

	public void setSelectedTreeNode(String name) {
		List<TreeNode> tree = items.getChildren();
		for(TreeNode node:tree) {
			if(node.getData().toString().contains(name)) {
				System.out.println("found the node to select");
				node.setSelected(true);
				break;
			}
		}
	}

	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insert() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleLoad() {
		this.setBean(this.procesoBC.load(this.getId()));
	}
}
