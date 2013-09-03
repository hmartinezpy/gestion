package py.com.ait.gestion.business;

import java.util.List;
import javax.inject.Inject;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Checklist;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.ChecklistDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@BusinessController
public class ChecklistBC extends DelegateCrud<Checklist, Long, ChecklistDAO>{

	private static final long serialVersionUID = 1L;

	@Inject 
	private ChecklistDAO checklistDAO ;
	

	
	@Inject 
	private AudLogDAO audLogDAO;
	
	@Inject 
	private UsuarioDAO usuarioDAO;
	
	@Inject 
	SesionLogDAO sesionLogDAO;


	public List<Checklist> listar() {
		return checklistDAO.findAll();	
	}
	
	public Checklist recuperar(Long id) {
		return checklistDAO.load(id);	
	}
	
	
	/*****************Auditoria**************************/			
	@Transactional
	public void registrar(Checklist checklist) {
		
		super.insert(checklist);
		
		try {
			audLogDAO.log(null, checklist,usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Insert,checklist.getChecklistId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void editar(Checklist checklist) {
		
		Checklist viejo = this.recuperar(checklist.getChecklistId());
		super.update(checklist);
		try {
			audLogDAO.log(viejo, checklist,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Update,checklist.getChecklistId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//actividadProductivaDAO.update(actividadProductiva);	
	}
	
	
	@Transactional
	public void eliminar(Long id) {
		
		Checklist checklist = this.recuperar(id);
		super.delete(id);
		
		try {
			audLogDAO.log(checklist, null,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Delete,checklist.getChecklistId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
	
