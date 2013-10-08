package py.com.ait.gestion.business;


import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;

import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.ActividadChecklistDetalle;
import py.com.ait.gestion.persistence.ActividadChecklistDetalleDAO;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@BusinessController
public class ActividadChecklistDetalleBC extends DelegateCrud<ActividadChecklistDetalle, Long, ActividadChecklistDetalleDAO>{

	private static final long serialVersionUID = 1L;

	@Inject 
	private ActividadChecklistDetalleDAO actividadChecklistDetalleDAO ;
	

	
	@Inject 
	private AudLogDAO audLogDAO;
	
	@Inject 
	private UsuarioDAO usuarioDAO;
	
	@Inject 
	SesionLogDAO sesionLogDAO;


	public List<ActividadChecklistDetalle> listar() {
		return actividadChecklistDetalleDAO.findAll();	
	}
	
	public ActividadChecklistDetalle recuperar(Long id) {
		return actividadChecklistDetalleDAO.load(id);	
	}
	
	
	/*****************Auditoria**************************/			
	@Transactional
	public void registrar(ActividadChecklistDetalle actividadChecklistDetalle) {
		
		super.insert(actividadChecklistDetalle);
		
		try {
			audLogDAO.log(null, actividadChecklistDetalle,usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Insert,actividadChecklistDetalle.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void editar(ActividadChecklistDetalle actividadChecklistDetalle) {
		
		ActividadChecklistDetalle viejo = this.recuperar(actividadChecklistDetalle.getId());
		super.update(actividadChecklistDetalle);
		try {
			audLogDAO.log(viejo, actividadChecklistDetalle,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Update,actividadChecklistDetalle.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//actividadProductivaDAO.update(actividadProductiva);	
	}
	
	
	@Transactional
	public void eliminar(Long id) {
		
		ActividadChecklistDetalle actividadChecklistDetalle = this.recuperar(id);
		super.delete(id);
		
		try {
			audLogDAO.log(actividadChecklistDetalle, null,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Delete,actividadChecklistDetalle.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param actividadSeleccionada
	 * @return
	 */
	public List<ActividadChecklistDetalle> getChecklistByActividad(
			Actividad actividadSeleccionada) {
		return actividadChecklistDetalleDAO.getChecklistByActividad(actividadSeleccionada);
	}

	/**
	 * @param actividad
	 * @return
	 */
	public Long validarCumplimiento(Actividad actividad) {
		return actividadChecklistDetalleDAO.validarCumplimiento(actividad);

	}


}
	

