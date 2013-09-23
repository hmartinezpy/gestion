package py.com.ait.gestion.persistence;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;

import py.com.ait.gestion.domain.Documento;
import py.com.ait.gestion.domain.DocumentoRol;
import py.com.ait.gestion.domain.Rol;

@PersistenceController
public class DocumentoRolDAO extends JPACrud<DocumentoRol, Long> {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Rol> getRolesByDocumento(Long documentoId){
		
		List<Rol> roles = new ArrayList<Rol>();
		Query q = em.createQuery("select r from DocumentoRol dr, Rol r where dr.rol.rolId = r.rolId " +
									"and dr.documento.documentoId = :documentoId");
				
		q.setParameter("documentoId", documentoId);
		roles = (List<Rol>)q.getResultList();
		
		return roles;
	}

	@SuppressWarnings("unchecked")
	public List<String> getRolesByDocumentoAsString(Long documentoId) {
		
		List<String> roles = new ArrayList<String>();
		Query q = em.createQuery("select r.descripcion from DocumentoRol dr, Rol r where dr.rol.rolId = r.rolId " +
									"and dr.documento.documentoId = :documentoId");
				
		q.setParameter("documentoId", documentoId);
		roles = (List<String>)q.getResultList();
		
		return roles;
	}
	
	@SuppressWarnings("unchecked")
	public void deleteByDocumentoRol(Long documentoId, Long rolId) {
		
		//get documento roles
		Query q = em.createQuery("select dr from DocumentoRol dr where dr.rol.rolId = :rolId " +
				"and dr.documento.documentoId = :documentoId");
		
		q.setParameter("rolId", rolId);
		q.setParameter("documentoId", documentoId);		
		List<DocumentoRol> list = (List<DocumentoRol>)q.getResultList();
		
		//delete
		for(DocumentoRol drol : list) {
			
			delete(drol.getDocumentoRolId());
		}
		
	}
}
	


