package py.com.ait.gestion.business;

import java.util.List;
import javax.inject.Inject;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Pregunta;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.PreguntaDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@BusinessController
public class PreguntaBC extends DelegateCrud<Pregunta, Long, PreguntaDAO>{

	private static final long serialVersionUID = 1L;

	@Inject 
	private PreguntaDAO preguntaDAO ;
	
	@Inject 
	private AudLogDAO audLogDAO;
	
	@Inject 
	private UsuarioDAO usuarioDAO;
	
	@Inject 
	SesionLogDAO sesionLogDAO;


	public List<Pregunta> listar() {
		return preguntaDAO.findAll();	
	}
	
	public Pregunta recuperar(Long id) {
		return preguntaDAO.load(id);	
	}
	
	/*****************Auditoria**************************/			
	@Transactional
	public void registrar(Pregunta pregunta) {
		
		super.insert(pregunta);
		
		try {
			audLogDAO.log(null, pregunta,usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Insert,pregunta.getPreguntaId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void editar(Pregunta pregunta) {
		
		Pregunta viejo = this.recuperar(pregunta.getPreguntaId());
		super.update(pregunta);
		try {
			audLogDAO.log(viejo, pregunta,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Update,pregunta.getPreguntaId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//actividadProductivaDAO.update(actividadProductiva);	
	}
	
	
	@Transactional
	public void eliminar(Long id) {
		
		Pregunta pregunta = this.recuperar(id);
		super.delete(id);
		
		try {
			audLogDAO.log(pregunta, null,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Delete,pregunta.getPreguntaId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
	
	