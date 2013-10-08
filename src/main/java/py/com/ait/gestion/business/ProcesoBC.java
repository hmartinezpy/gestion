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
		
		boolean isAdminUser = usuarioBC.isAdminUser(currentUser);
		Usuario usuario = usuarioBC.findSpecificUser(currentUser);
		List<Proceso> result = procesoDAO.getProcesos(filtroEstadoProceso, isAdminUser, usuario.getUsuarioId());
		for (Proceso proc : result){
			proc.setLastActividad(actividadBC.getLastActividad(proc));
		}
		return result;
	}

	public Proceso recuperar(Long id) {
		return procesoDAO.load(id);
	}

	/***************** Auditoria **************************/
	@Transactional
	public void registrar(Proceso proceso) {
		/*if (proceso.getNroProceso() == null
				|| proceso.getNroProceso().equals("")) {
			
			proceso.setNroProceso(getSiguienteNroProceso());
		}*/
		super.insert(proceso);

		//crear primera actividad
		CronogramaDetalle cd = cronogramaDetalleDAO.getFirstCronogramaDetalleByCronograma(
									proceso.getCronograma());
		actividadBC.insertActividadFromCronogramaDetalle(cd, null, proceso, proceso.getResponsable());
		
		try {
			audLogDAO.log(null, proceso, usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()),
					Definiciones.Operacion.Insert, proceso.getProcesoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getSiguienteNroProceso(Long cronogramaId) {
		String nroProcesoActual = getNumeroProcesoAnual(cronogramaId);
		String nroProcesoSgte = (Integer.parseInt(nroProcesoActual) + 1) + "";
		Cronograma cronograma = cronogramaDAO.load(cronogramaId);
		StringBuilder sb = new StringBuilder();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String nroProceso = sb
				.append(cronograma.getSigla())
				.append("_")
				.append(year)
				.append("/")
				.append(rellenarCerosIzquierda(nroProcesoSgte, 3))
				.toString();				
		return nroProceso;
	}
	
	public String rellenarCerosIzquierda(String num, int tamanhoTotal){
		
		String result = num;
		if(result.length() < tamanhoTotal) {
			
			do{
				
				result = "0" + result;
			}while (result.length() < tamanhoTotal);
		}
		
		return result;
	}

	@Transactional
	public void editar(Proceso proceso) {

		Proceso viejo = this.recuperar(proceso.getProcesoId());
		super.update(proceso);
		try {
			audLogDAO.log(viejo, proceso, usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()),
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
			audLogDAO.log(proceso, null, usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()),
					Definiciones.Operacion.Delete, proceso.getProcesoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public long getMaxId() {
		return procesoDAO.getMaxId();
	}

	public String getNumeroProcesoAnual(Long cronogramaId) {
		return procesoDAO.getLastSequence(cronogramaId);
	}

	public List<Actividad> getActividadesByProceso(Proceso procesoSeleccionado, String currentUser) {
		
		boolean isAdminUser = usuarioBC.isAdminUser(currentUser);
		Usuario usuario = usuarioBC.findSpecificUser(currentUser);	
		return procesoDAO.getActividadesByProceso(procesoSeleccionado, isAdminUser, usuario.getUsuarioId());
	}

	public List<String> getCarpetas(Proceso proceso) {
		
		List<String> carpetas = new ArrayList<String>();
		
		try {
			//obtener path del proceso
			String nombreCliente = proceso.getCliente().getNombre();
			String descCronog = proceso.getCronograma().getNombre();
			String nroProceso = proceso.getNroProceso();
			String anho = "";
			int k = nroProceso.lastIndexOf('/');
			if (k > 0) {
				anho = nroProceso.substring(k - 4, k);
				nroProceso = nroProceso.substring(k + 1);
			}
			String path = appProperties.getDocumentPath()
					+ nombreCliente + '/' + anho + '/' + descCronog + '/'
					+ nroProceso + '/';
		
			//obtener lista de carpetas del path del proceso 			
			File file = new File(path);
			File[] list = file.listFiles();
			if(list != null) {
				for(File listItem : list) {
					
					if(listItem.isDirectory()) {
						
						carpetas.add(listItem.getName());
					}
				}
			}
			
		} catch(Exception ex) {
			
			throw new RuntimeException(ex.getMessage()); 
		}
		
		return carpetas;
	}
	
	@Transactional
	public void guardarRoles(Documento documento, List<String> roles) {
		
		
		try{

			long documentoId = documento.getDocumentoId();
		
			//verificar los nuevos
			Iterator<String> nuevo = roles.iterator();
					
			while (nuevo.hasNext())
			{
				Boolean existe = false;
				String ins= nuevo.next();
				List<String> lista = documentoRolDAO.getRolesByDocumentoAsString(documentoId);
				Iterator<String> original = lista.iterator();
				while(original.hasNext())
				{
					if(original.next().equals(ins))
					{
						existe = true;
						break;
					}
				}
				if(!existe)
				{
					Rol rol = rolBC.getRol(ins);
					if(rol!=null)
					{
						//insertar
						DocumentoRol dr = new DocumentoRol();
						dr.setDocumento(documento);
						dr.setRol(rol);
						documentoRolDAO.insert(dr);
					}
					
					
				}
			}
			//se buscan los borrados
			Iterator<String> original2 = documentoRolDAO.getRolesByDocumentoAsString(documentoId).iterator();
			
			while(original2.hasNext())
			{
				Boolean existe2 = false;
				Iterator<String> nuevo2 = roles.iterator();
				String dl= original2.next();
				
				while(nuevo2.hasNext())
				{
					if(dl.equals(nuevo2.next()))
					{
						existe2=true;
						break;
					}
				}
				if(!existe2)
				{
					//delete
					Rol rol = rolBC.getRol(dl);
					if(rol!=null)
					{
						//eliminar
						documentoRolDAO.deleteByDocumentoRol(documento.getDocumentoId(), rol.getRolId());					
					}
				}
				
			}
		}catch(Exception ex) {
			
			ex.printStackTrace();
			throw new RuntimeException("Ocurrió un error al guardar, intente de nuevo");
		}
	}
	
	public List<String> getDocumentoRoles(Long documentoId) {
		
		return documentoRolDAO.getRolesByDocumentoAsString(documentoId);
	}
	

	@Transactional
	public void finalizarProceso(Proceso proceso) {
		proceso.setEstado(Definiciones.EstadoProceso.Resuelto);
		update(proceso);
		
	}
	
	public boolean canCreateProcess(String currentUser){
		
		boolean isAdminUser = usuarioBC.isAdminUser(currentUser);
		Usuario usuario = usuarioBC.findSpecificUser(currentUser);
		Permiso permiso = permisoBC.getPermiso("crear procesos");
		boolean tienePermiso = usuarioRolPermisoBC.tiene(permiso, usuario);
		return (isAdminUser || tienePermiso);
	}

	/**
	 * @param currentUser
	 * @return
	 */
	public boolean canControlFactura(String currentUser) {
		boolean isAdminUser = usuarioBC.isAdminUser(currentUser);
		Usuario usuario = usuarioBC.findSpecificUser(currentUser);
		Permiso permiso = permisoBC.getPermiso("controlar facturas");
		boolean tienePermiso = usuarioRolPermisoBC.tiene(permiso, usuario);
		return (isAdminUser || tienePermiso);
	}	
}
