package py.com.ait.gestion.business;

import java.util.List;
import javax.inject.Inject;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Documento;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.DocumentoDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@BusinessController
public class DocumentoBC extends DelegateCrud<Documento, Long, DocumentoDAO>{

	private static final long serialVersionUID = 1L;

	@Inject 
	private DocumentoDAO documentoDAO ;
	
	@Inject 
	private AudLogDAO audLogDAO;
	
	@Inject 
	private UsuarioDAO usuarioDAO;
	
	@Inject 
	SesionLogDAO sesionLogDAO;


	public List<Documento> listar() {
		return documentoDAO.findAll();	
	}
	
	public List<Documento> getFileProceso(Long idProceso) {
		return documentoDAO.getFileProceso(idProceso);	
	}
	
	public List<Documento> getFileActividad(Long idActividad) {
		return documentoDAO.getFileActividad(idActividad);	
	}
	
	public Documento recuperar(Long id) {
		return documentoDAO.load(id);	
	}
	
	/*****************Auditoria**************************/			
	@Transactional
	public void registrar(Documento documento) {
		
		super.insert(documento);
		
		try {
			audLogDAO.log(null, documento,usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Insert,documento.getDocumentoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void editar(Documento documento) {
		
		Documento viejo = this.recuperar(documento.getDocumentoId());
		super.update(documento);
		try {
			audLogDAO.log(viejo, documento,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Update,documento.getDocumentoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//actividadProductivaDAO.update(actividadProductiva);	
	}
	
	
	@Transactional
	public void eliminar(Long id) {
		
		Documento documento = this.recuperar(id);
		super.delete(id);
		
		try {
			audLogDAO.log(documento, null,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Delete,documento.getDocumentoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
	
	