package py.com.ait.gestion.business;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;

import py.com.ait.gestion.constant.AppProperties;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.Cronograma;
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.domain.Documento;
import py.com.ait.gestion.domain.DocumentoRol;
import py.com.ait.gestion.domain.Permiso;
import py.com.ait.gestion.domain.Proceso;
import py.com.ait.gestion.domain.Rol;
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.CronogramaDAO;
import py.com.ait.gestion.persistence.CronogramaDetalleDAO;
import py.com.ait.gestion.persistence.DocumentoRolDAO;
import py.com.ait.gestion.persistence.ProcesoDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@BusinessController
public class ProcesoBC extends DelegateCrud<Proceso, Long, ProcesoDAO> {

	private static final long serialVersionUID = 1L;

	@Inject
	private AppProperties appProperties;

	@Inject
	private ProcesoDAO procesoDAO;

	@Inject
	private AudLogDAO audLogDAO;

	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	SesionLogDAO sesionLogDAO;

	@Inject
	private ActividadBC actividadBC;

	@Inject
	private CronogramaDetalleDAO cronogramaDetalleDAO;

	@Inject
	private CronogramaDAO cronogramaDAO;

	@Inject
	private RolBC rolBC;

	@Inject
	private DocumentoRolDAO documentoRolDAO;

	@Inject
	private UsuarioBC usuarioBC;

	@Inject
	private UsuarioRolPermisoBC usuarioRolPermisoBC;

	@Inject
	private PermisoBC permisoBC;

	public List<Proceso> listar(String filtroEstadoProceso, String currentUser) {

		boolean isAdminUser = this.usuarioBC.isAdminUser(currentUser);
		Usuario usuario = this.usuarioBC.findSpecificUser(currentUser);

		List<Proceso> result = this.procesoDAO.getProcesos(filtroEstadoProceso,
				isAdminUser, usuario.getUsuarioId());
		
		for (Proceso proc : result) {
			proc.setLastActividad(this.actividadBC.getLastActividad(proc));
		}
		List<Proceso> procesosSinActividades = this.procesoDAO
				.getProcesosSinActividades(isAdminUser, usuario.getUsuarioId());

		result.addAll(procesosSinActividades);
		return result;
	}

	public Proceso recuperar(Long id) {

		return this.procesoDAO.load(id);
	}

	/***************** Auditoria **************************/
	@Transactional
	public void registrar(Proceso proceso) {

		/*
		 * if (proceso.getNroProceso() == null ||
		 * proceso.getNroProceso().equals("")) {
		 * 
		 * proceso.setNroProceso(getSiguienteNroProceso()); }
		 */
		String usuarioActual = this.usuarioDAO.getUsuarioActual();
		Usuario responsable = this.usuarioBC.findSpecificUser(usuarioActual);
		proceso.setResponsable(responsable);
		super.insert(proceso);

		try {
			this.audLogDAO.log(null, proceso, this.usuarioDAO
					.getUsuarioActual(), this.sesionLogDAO
					.ObtenerIp(this.usuarioDAO.getUsuarioActual()),
					Definiciones.Operacion.Insert, proceso.getProcesoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional
	public void empezarActividades(Proceso proceso, Usuario responsable) {

		// Actualizar responsable;
		proceso.setResponsable(responsable);
		this.editar(proceso);

		// crear primera actividad
		CronogramaDetalle cd = this.cronogramaDetalleDAO
				.getFirstCronogramaDetalleByCronograma(proceso.getCronograma());
		this.actividadBC.insertActividadFromCronogramaDetalle(cd, null,
				proceso, proceso.getResponsable());

	}

	public String getSiguienteNroProceso(Long cronogramaId) {

		String nroProcesoActual = this.getNumeroProcesoAnual(cronogramaId);
		String nroProcesoSgte = (Integer.parseInt(nroProcesoActual) + 1) + "";
		Cronograma cronograma = this.cronogramaDAO.load(cronogramaId);
		StringBuilder sb = new StringBuilder();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String nroProceso = sb.append(cronograma.getSigla()).append("_")
				.append(year).append("/")
				.append(this.rellenarCerosIzquierda(nroProcesoSgte, 3))
				.toString();
		return nroProceso;
	}

	public String rellenarCerosIzquierda(String num, int tamanhoTotal) {

		String result = num;
		if (result.length() < tamanhoTotal) {

			do {

				result = "0" + result;
			} while (result.length() < tamanhoTotal);
		}

		return result;
	}

	@Transactional
	public void editar(Proceso proceso) {

		Proceso viejo = this.recuperar(proceso.getProcesoId());
		super.update(proceso);
		try {
			this.audLogDAO.log(viejo, proceso, this.usuarioDAO
					.getUsuarioActual(), this.sesionLogDAO
					.ObtenerIp(this.usuarioDAO.getUsuarioActual()),
					Definiciones.Operacion.Update, proceso.getProcesoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// actividadProductivaDAO.update(actividadProductiva);
	}

	@Transactional
	public void eliminar(Long id) {

		Proceso proceso = this.recuperar(id);
		super.delete(id);

		try {
			this.audLogDAO.log(proceso, null, this.usuarioDAO
					.getUsuarioActual(), this.sesionLogDAO
					.ObtenerIp(this.usuarioDAO.getUsuarioActual()),
					Definiciones.Operacion.Delete, proceso.getProcesoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public long getMaxId() {

		return this.procesoDAO.getMaxId();
	}

	public String getNumeroProcesoAnual(Long cronogramaId) {

		return this.procesoDAO.getLastSequence(cronogramaId);
	}

	public List<Actividad> getActividadesByProceso(Proceso procesoSeleccionado,
			String currentUser) {

		boolean isAdminUser = this.usuarioBC.isAdminUser(currentUser);
		Usuario usuario = this.usuarioBC.findSpecificUser(currentUser);
		return this.procesoDAO.getActividadesByProceso(procesoSeleccionado,
				isAdminUser, usuario.getUsuarioId());
	}

	public List<String> getCarpetas(Proceso proceso) {

		List<String> carpetas = new ArrayList<String>();

		try {
			// obtener path del proceso
			String nombreCliente = proceso.getCliente().getNombre();
			String descCronog = proceso.getCronograma().getNombre();
			String nroProceso = proceso.getNroProceso();
			String anho = "";
			int k = nroProceso.lastIndexOf('/');
			if (k > 0) {
				anho = nroProceso.substring(k - 4, k);
				nroProceso = nroProceso.substring(k + 1);
			}
			String path = this.appProperties.getDocumentPath() + nombreCliente
					+ '/' + anho + '/' + descCronog + '/' + nroProceso + '/';

			// obtener lista de carpetas del path del proceso
			File file = new File(path);
			File[] list = file.listFiles();
			if (list != null) {
				for (File listItem : list) {

					if (listItem.isDirectory()) {

						carpetas.add(listItem.getName());
					}
				}
			}

		} catch (Exception ex) {

			throw new RuntimeException(ex.getMessage());
		}

		return carpetas;
	}

	@Transactional
	public void guardarRoles(Documento documento, List<String> roles) {

		try {

			long documentoId = documento.getDocumentoId();

			// verificar los nuevos
			Iterator<String> nuevo = roles.iterator();

			while (nuevo.hasNext()) {
				Boolean existe = false;
				String ins = nuevo.next();
				List<String> lista = this.documentoRolDAO
						.getRolesByDocumentoAsString(documentoId);
				Iterator<String> original = lista.iterator();
				while (original.hasNext()) {
					if (original.next().equals(ins)) {
						existe = true;
						break;
					}
				}
				if (!existe) {
					Rol rol = this.rolBC.getRol(ins);
					if (rol != null) {
						// insertar
						DocumentoRol dr = new DocumentoRol();
						dr.setDocumento(documento);
						dr.setRol(rol);
						this.documentoRolDAO.insert(dr);
					}

				}
			}
			// se buscan los borrados
			Iterator<String> original2 = this.documentoRolDAO
					.getRolesByDocumentoAsString(documentoId).iterator();

			while (original2.hasNext()) {
				Boolean existe2 = false;
				Iterator<String> nuevo2 = roles.iterator();
				String dl = original2.next();

				while (nuevo2.hasNext()) {
					if (dl.equals(nuevo2.next())) {
						existe2 = true;
						break;
					}
				}
				if (!existe2) {
					// delete
					Rol rol = this.rolBC.getRol(dl);
					if (rol != null) {
						// eliminar
						this.documentoRolDAO.deleteByDocumentoRol(
								documento.getDocumentoId(), rol.getRolId());
					}
				}

			}
		} catch (Exception ex) {

			ex.printStackTrace();
			throw new RuntimeException(
					"Ocurrió un error al guardar, intente de nuevo");
		}
	}

	public List<String> getDocumentoRoles(Long documentoId) {

		return this.documentoRolDAO.getRolesByDocumentoAsString(documentoId);
	}

	@Transactional
	public void finalizarProceso(Proceso proceso) {

		proceso.setEstado(Definiciones.EstadoProceso.Resuelto);
		this.update(proceso);

	}

	public boolean canCreateProcess(String currentUser) {

		boolean isAdminUser = this.usuarioBC.isAdminUser(currentUser);
		Usuario usuario = this.usuarioBC.findSpecificUser(currentUser);
		Permiso permiso = this.permisoBC.getPermiso("crear procesos");
		boolean tienePermiso = this.usuarioRolPermisoBC.tiene(permiso, usuario);
		return (isAdminUser || tienePermiso);
	}

	/**
	 * @param user
	 * @return
	 */
	public boolean canControlFactura(String user) {

		boolean isAdminUser = this.usuarioBC.isAdminUser(user);
		Usuario usuario = this.usuarioBC.findSpecificUser(user);
		Permiso permiso = this.permisoBC.getPermiso("controlar facturas");
		boolean tienePermiso = this.usuarioRolPermisoBC.tiene(permiso, usuario);
		return (isAdminUser || tienePermiso);
	}

	/**
	 * @param actividad
	 * @return
	 */
	public boolean existenSubTareasAbiertas(Actividad actividad) {

		// Validar que no posea subtareas abiertas, asignadas a usuarios sin el
		// permiso "controlar facturas"

		List<Actividad> subtareas = this.actividadBC.getSubtareas(actividad);
		if (subtareas != null && subtareas.size() > 0) {
			for (Actividad subtarea : subtareas) {
				boolean esAdministrativa = this.canControlFactura(subtarea
						.getResponsable().getUsuario());

				if (!esAdministrativa) {
					if (Definiciones.EstadoActividad.Nueva.equals(subtarea
							.getEstado())
							|| Definiciones.EstadoActividad.EnProceso
									.equals(subtarea.getEstado())) {
						// Esta es una subtarea "no administrativa" abierta!
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * @param actividad
	 * @return
	 */
	public boolean existenSubTareas(Actividad actividad) {

		List<Actividad> subtareas = this.actividadBC.getSubtareas(actividad);

		if (subtareas != null && subtareas.size() > 0) {
			return true;
		}

		return false;
	}

	public List<Proceso> getProcesosByCronogramaForUser(Long cronogramaId, String currentUser) {

		List<Long> procesosId = getProcesosIdForUser(currentUser);

		List<Proceso> result = this.procesoDAO.getProcesosByCronogramaAndProcesosId(cronogramaId, procesosId);

		for (Proceso proc : result) {
			proc.setLastActividad(this.actividadBC.getLastActividad(proc));
		}

		return result;

	}

	public List<Long> getProcesosIdForUser(String currentUser) {

		boolean isAdminUser = this.usuarioBC.isAdminUser(currentUser);
		Usuario usuario = this.usuarioBC.findSpecificUser(currentUser);

		List<Long> result = this.procesoDAO.getProcesoIdsForUser(isAdminUser, usuario.getUsuarioId());

		return result;

	}
	public String getCountProcesosByCronogramaAndProcesosId(
			Long cronogramaId, List<Long> procesosId) {

		return procesoDAO.getCountProcesosByCronogramaAndProcesosId(cronogramaId, procesosId);
	}

	public List<Proceso> getProcesosFilteredForUser(Long cronogramaId,
			Long clienteId, String currentUser) {

		List<Long> procesosId = getProcesosIdForUser(currentUser);

		List<Proceso> result = this.procesoDAO.getProcesosByCronogramaClienteAndProcesosId(cronogramaId, clienteId, procesosId);

		for (Proceso proc : result) {
			proc.setLastActividad(this.actividadBC.getLastActividad(proc));
		}

		return result;
	}
}
