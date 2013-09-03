package py.com.ait.gestion.business;

import java.util.List;
import javax.inject.Inject;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.CronogramaDetalleDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@BusinessController
public class CronogramaDetalleBC extends DelegateCrud<CronogramaDetalle, Long, CronogramaDetalleDAO>{

	private static final long serialVersionUID = 1L;

	@Inject 
	private CronogramaDetalleDAO cronogramaDetalleDAO ;
	
	@Inject 
	private AudLogDAO audLogDAO;
	
	@Inject 
	private UsuarioDAO usuarioDAO;
	
	@Inject 
	SesionLogDAO sesionLogDAO;


	public List<CronogramaDetalle> listar() {
		return cronogramaDetalleDAO.findAll();	
	}
	
	public CronogramaDetalle recuperar(Long id) {
		return cronogramaDetalleDAO.load(id);	
	}

	
	/*****************Auditoria**************************/			
	@Transactional
	public void registrar(CronogramaDetalle cronogramaDetalle) {
		
		super.insert(cronogramaDetalle);
		
		try {
			audLogDAO.log(null, cronogramaDetalle,usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Insert,cronogramaDetalle.getCronogramaDetalleId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void editar(CronogramaDetalle cronogramaDetalle) {
		
		CronogramaDetalle viejo = this.recuperar(cronogramaDetalle.getCronogramaDetalleId());
		super.update(cronogramaDetalle);
		try {
			audLogDAO.log(viejo, cronogramaDetalle,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Update,cronogramaDetalle.getCronogramaDetalleId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//actividadProductivaDAO.update(actividadProductiva);	
	}
	
	
	@Transactional
	public void eliminar(Long id) {
		
		CronogramaDetalle cronogramaDetalle = this.recuperar(id);
		super.delete(id);
		
		try {
			audLogDAO.log(cronogramaDetalle, null,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Delete,cronogramaDetalle.getCronogramaDetalleId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
	
	