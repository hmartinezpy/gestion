package py.com.ait.gestion.business;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;

import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Documento;
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.domain.UsuarioRolPermiso;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.DocumentoDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;
import py.com.ait.gestion.domain.DocumentoRol;

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
	private UsuarioBC usuarioBC;
	
	@Inject 
	SesionLogDAO sesionLogDAO;


	public List<Documento> listar() {
		return documentoDAO.findAll();	
	}
	
	public List<Documento> getFileProceso(Long idProceso, String currentUser) {
		
		boolean isAdminUser = usuarioBC.isAdminUser(currentUser);
		Usuario usuario = usuarioBC.findSpecificUser(currentUser);
		return documentoDAO.getFileProceso(idProceso, isAdminUser, usuario.getUsuarioId());	
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
		String filePath = documento.getFilepath() + documento.getFilename() + "." + documento.getFileExtension();
		super.delete(id);		
		
		try{
			
			File file = new File(filePath);
			if(!file.delete()) //lanzar excepcion para no commitear transaccion
				throw new RuntimeException("Error al eliminar el documento! Intente de nuevo");
		} catch(Exception ex) { //lanzar excepcion para no commitear transaccion
			throw new RuntimeException("Error al eliminar el documento! Intente de nuevo");
		}
		
		try {
			audLogDAO.log(documento, null,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Delete,documento.getDocumentoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateBloqueoDocumento(Documento documento, boolean bloquear) {

		String nombreUsu = usuarioBC.getUsuarioActual();
		Usuario actual = usuarioBC.findSpecificUser(nombreUsu);
		Date fecha = new Date();
		if(bloquear) {

			documento.setBloqueado("Si");
			documento.setFechaBloqueo(fecha);		
			documento.setUsuarioBloqueo(actual);
		} else {
			
			documento.setBloqueado("No");
			documento.setFechaDesbloqueo(fecha);		
			documento.setUsuarioDesbloqueo(actual);
		}		
		editar(documento);		
	}
	
	public Documento getDocumentoByFileName(String fileName, String filePath, String fileExtension) {
		
		return documentoDAO.getDocumentoByFileName(fileName, filePath, fileExtension);
	}

	public boolean puedoVer(Documento documento, Usuario usuarioActual) {
		
		boolean puedoVer = false;		
		if(documento == null) {
			puedoVer = true; //el documento no existe aún
		} else {
			boolean isAdminUser = usuarioBC.isAdminUser(usuarioActual.getUsuario());		
			if(isAdminUser){ 
				puedoVer = true; //soy admin
			} else if(documento.getUsuarioCreacion().getUsuario().equals(usuarioActual.getUsuario())) {					
				puedoVer = true; //soy owner
			} else {
				//checkear para cada rol habilitado si posee alguno
				for(DocumentoRol dr : documento.getDocumentoRoles()){
					
					for(UsuarioRolPermiso urp : usuarioActual.getUsuarioRolPermisos()) {
						
						if(urp.getRol() != null && urp.getRol().getRolId() == dr.getRol().getRolId()) {
							puedoVer = true; //tengo un rol habilitado
							break;
						}
					}
					if(puedoVer) break;
				}
			}
		}
		return puedoVer;
	}
	
}
	
	