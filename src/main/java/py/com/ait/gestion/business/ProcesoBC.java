package py.com.ait.gestion.business;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;

import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.domain.Proceso;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.CronogramaDetalleDAO;
import py.com.ait.gestion.persistence.ProcesoDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@BusinessController
public class ProcesoBC extends DelegateCrud<Proceso, Long, ProcesoDAO> {

	private static final long serialVersionUID = 1L;

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
	private ActividadBC actividadDAO;

	public List<Proceso> listar() {
		return procesoDAO.findAll();
	}

	public Proceso recuperar(Long id) {
		return procesoDAO.load(id);
	}

	/***************** Auditoria **************************/
	@Transactional
	public void registrar(Proceso proceso) {
		if (proceso.getNroProceso() == null
				|| proceso.getNroProceso().equals("")) {
			
			proceso.setNroProceso(getSiguienteNroProceso());
		}
		super.insert(proceso);

		//crear primera actividad
		CronogramaDetalle cd = cronogramaDetalleDAO.getFirstCronogramaDetalleByCronograma(
									proceso.getCronograma());
		actividadBC.insertActividadFromCronogramaDetalle(cd, null, proceso);
		
		try {
			audLogDAO.log(null, proceso, usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()),
					Definiciones.Operacion.Insert, proceso.getProcesoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getSiguienteNroProceso() {
		String nroProcesoActual = getNumeroProcesoAnual();
		StringBuilder sb = new StringBuilder();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String nroProceso = sb
				.append((Integer.parseInt(nroProcesoActual) + 1))
				.append("/").append(year).toString();
		return nroProceso;
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

	public String getNumeroProcesoAnual() {
		return procesoDAO.getLastSequence();
	}

	public List<Actividad> getActividadesByProceso(Proceso procesoSeleccionado) {
		return procesoDAO.getActividadesByProceso(procesoSeleccionado);
	}

}
