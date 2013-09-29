package py.com.ait.gestion.persistence;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;

import py.com.ait.gestion.domain.Documento;
import py.com.ait.gestion.domain.DocumentoRol;

@PersistenceController
public class DocumentoDAO extends JPACrud<Documento, Long> {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Documento> getFileProceso(Long idProceso, boolean isAdminUser, Long currentUserId) {
		
		
		Documento documento = new Documento();
		documento.setEntidad("Proceso");
		documento.setIdEntidad(idProceso);
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Documento.class);
		Example where = Example.create(documento);
		criteria.add(where);
		List<Documento> list = (List<Documento>)criteria.list();
		List<Documento> result = list;	
		
		//si no es admin agregar filtro de usuario actual
		if(!isAdminUser) {
			
			result = new ArrayList<Documento>();
			Query q = em.createQuery("select distinct urp.rol.rolId from UsuarioRolPermiso urp " + 
					"where urp.rol is not null and urp.usuario.usuarioId = :currentUser");

			q.setParameter("currentUser", currentUserId);
			List<Long> listRoles = (List<Long>)q.getResultList();
			
			for(Documento d : list) {
				
				if(d.getUsuarioCreacion().getUsuarioId() == currentUserId) {
					//soy el creador
					result.add(d);				
				} else {
					
					for(DocumentoRol dr : d.getDocumentoRoles()) {
						
						//el rol permitido está entre mis roles
						if(dr.getRol() != null && listRoles.contains(dr.getRol().getRolId()))
							result.add(d);
					}
				}
			}
		}
		
		return result;		
	}
	
	@SuppressWarnings("unchecked")
	public List<Documento> getFileActividad(Long idActividad) {
		Query q = em.createQuery("select new Documento(d.documentoId, d.filename, d.filepath, d.filetype, d.fileExtension, d.bloqueado, d.fechaBloqueo, d.usuarioBloqueo, d.fechaDesbloqueo, d.usuarioDesbloqueo, d.entidad, d.idEntidad, d.usuarioCreacion)" +
		" from py.com.ait.gestion.domain.Documento d where d.entidad = :entidad and d.idEntidad =:idEntidad ");
		
		q.setParameter("entidad", "Actividad");
		q.setParameter("idEntidad", idActividad);
		return q.getResultList();
	
	}

	@SuppressWarnings("unchecked")
	public Documento getDocumentoByFileName(String fileName, String filePath, String fileExtension) {
		
		Documento result = null;
		Documento documento = new Documento();
		documento.setFilename(fileName);
		documento.setFilepath(filePath);
		documento.setFileExtension(fileExtension);
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Documento.class);
		Example where = Example.create(documento);
		criteria.add(where);
		List<Documento> list = (List<Documento>)criteria.list();
		if(list.size() > 0)
			result = list.get(0);
			
		return result;
	}

}
	


