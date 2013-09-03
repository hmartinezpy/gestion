package py.com.ait.gestion.business;


import java.util.List;
import javax.inject.Inject;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.ChecklistDetalle;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.ChecklistDetalleDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@BusinessController
public class ChecklistDetalleBC extends DelegateCrud<ChecklistDetalle, Long, ChecklistDetalleDAO>{

	private static final long serialVersionUID = 1L;

	@Inject 
	private ChecklistDetalleDAO checklistDetalleDAO ;
	

	
	@Inject 
	private AudLogDAO audLogDAO;
	
	@Inject 
	private UsuarioDAO usuarioDAO;
	
	@Inject 
	SesionLogDAO sesionLogDAO;


	public List<ChecklistDetalle> listar() {
		return checklistDetalleDAO.findAll();	
	}
	
	public ChecklistDetalle recuperar(Long id) {
		return checklistDetalleDAO.load(id);	
	}
	
	
	/*****************Auditoria**************************/			
	@Transactional
	public void registrar(ChecklistDetalle checklistDetalle) {
		
		super.insert(checklistDetalle);
		
		try {
			audLogDAO.log(null, checklistDetalle,usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Insert,checklistDetalle.getChecklistDetalleId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void editar(ChecklistDetalle checklistDetalle) {
		
		ChecklistDetalle viejo = this.recuperar(checklistDetalle.getChecklistDetalleId());
		super.update(checklistDetalle);
		try {
			audLogDAO.log(viejo, checklistDetalle,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Update,checklistDetalle.getChecklistDetalleId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//actividadProductivaDAO.update(actividadProductiva);	
	}
	
	
	@Transactional
	public void eliminar(Long id) {
		
		ChecklistDetalle checklistDetalle = this.recuperar(id);
		super.delete(id);
		
		try {
			audLogDAO.log(checklistDetalle, null,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Delete,checklistDetalle.getChecklistDetalleId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
	

