package py.com.ait.gestion.business;

import java.util.List;
import javax.inject.Inject;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Observacion;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.ObservacionDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@BusinessController
public class ObservacionBC extends DelegateCrud<Observacion, Long, ObservacionDAO>{

	private static final long serialVersionUID = 1L;

	@Inject 
	private ObservacionDAO observacionDAO ;
	
	@Inject 
	private AudLogDAO audLogDAO;
	
	@Inject 
	private UsuarioDAO usuarioDAO;
	
	@Inject 
	SesionLogDAO sesionLogDAO;


	public List<Observacion> listar() {
		return observacionDAO.findAll();	
	}
	
	
	public List<Observacion> getObsProceso(Long idProceso) {
		return observacionDAO.getObsProceso(idProceso);	
	}
	
	public List<Observacion> getObsActividad(Long idActividad) {
		return observacionDAO.getObsActividad(idActividad);	
	}
	
	
	public Observacion recuperar(Long id) {
		return observacionDAO.load(id);	
	}
	
	/*****************Auditoria**************************/			
	@Transactional
	public void registrar(Observacion observacion) {
		
		super.insert(observacion);
		
		try {
			audLogDAO.log(null, observacion,usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Insert,observacion.getObservacionId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void editar(Observacion observacion) {
		
		Observacion viejo = this.recuperar(observacion.getObservacionId());
		super.update(observacion);
		try {
			audLogDAO.log(viejo, observacion,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Update,observacion.getObservacionId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Transactional
	public void eliminar(Long id) {
		
		Observacion observacion = this.recuperar(id);
		super.delete(id);
		
		try {
			audLogDAO.log(observacion, null,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Delete,observacion.getObservacionId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
	
	