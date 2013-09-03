package py.com.ait.gestion.business;

import java.util.List;
import javax.inject.Inject;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.TipoAlarma;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.TipoAlarmaDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@BusinessController
public class TipoAlarmaBC extends DelegateCrud<TipoAlarma, Long, TipoAlarmaDAO>{

	private static final long serialVersionUID = 1L;

	@Inject 
	private TipoAlarmaDAO tipoAlarmaDAO ;
	
	@Inject 
	private AudLogDAO audLogDAO;
	
	@Inject 
	private UsuarioDAO usuarioDAO;
	
	@Inject 
	SesionLogDAO sesionLogDAO;


	public List<TipoAlarma> listar() {
		return tipoAlarmaDAO.findAll();	
	}
	public List<TipoAlarma> getAlarmas(){
		return tipoAlarmaDAO.getAlarmas();
	}
	public List<TipoAlarma> getAlertas(){
		return tipoAlarmaDAO.getAlertas();
	}
	public TipoAlarma recuperar(Long id) {
		return tipoAlarmaDAO.load(id);	
	}
	
	/*****************Auditoria**************************/			
	@Transactional
	public void registrar(TipoAlarma tipoAlarma) {
		
		super.insert(tipoAlarma);
		
		try {
			audLogDAO.log(null, tipoAlarma,usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Insert,tipoAlarma.getTipoAlarmaId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void editar(TipoAlarma tipoAlarma) {
		
		TipoAlarma viejo = this.recuperar(tipoAlarma.getTipoAlarmaId());
		super.update(tipoAlarma);
		try {
			audLogDAO.log(viejo, tipoAlarma,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Update,tipoAlarma.getTipoAlarmaId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//actividadProductivaDAO.update(actividadProductiva);	
	}
	
	
	@Transactional
	public void eliminar(Long id) {
		
		TipoAlarma tipoAlarma = this.recuperar(id);
		super.delete(id);
		
		try {
			audLogDAO.log(tipoAlarma, null,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Delete,tipoAlarma.getTipoAlarmaId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
	
	