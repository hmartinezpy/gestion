package py.com.ait.gestion.business;

import java.util.List;
import javax.inject.Inject;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Cronograma;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.CronogramaDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@BusinessController
public class CronogramaBC extends DelegateCrud<Cronograma, Long, CronogramaDAO>{

	private static final long serialVersionUID = 1L;

	@Inject 
	private CronogramaDAO cronogramaDAO ;
	

	
	@Inject 
	private AudLogDAO audLogDAO;
	
	@Inject 
	private UsuarioDAO usuarioDAO;
	
	@Inject 
	SesionLogDAO sesionLogDAO;


	public List<Cronograma> listar() {
		return cronogramaDAO.findAll();	
	}
	
	public Cronograma recuperar(Long id) {
		return cronogramaDAO.load(id);	
	}
	
	
	/*****************Auditoria**************************/			
	@Transactional
	public void registrar(Cronograma cronograma) {
		
		super.insert(cronograma);
		
		try {
			audLogDAO.log(null, cronograma,usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Insert,cronograma.getCronogramaId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void editar(Cronograma cronograma) {
		
		Cronograma viejo = this.recuperar(cronograma.getCronogramaId());
		super.update(cronograma);
		try {
			audLogDAO.log(viejo, cronograma,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Update,cronograma.getCronogramaId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//actividadProductivaDAO.update(actividadProductiva);	
	}
	
	
	@Transactional
	public void eliminar(Long id) {
		
		Cronograma cronograma = this.recuperar(id);
		super.delete(id);
		
		try {
			audLogDAO.log(cronograma, null,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Delete,cronograma.getCronogramaId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public long getMaxId() {
		return cronogramaDAO.getMaxId();
	}

}