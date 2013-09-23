package py.com.ait.gestion.persistence;
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

@PersistenceController
public class DocumentoDAO extends JPACrud<Documento, Long> {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Documento> getFileProceso(Long idProceso) {
		
		
		Documento documento = new Documento();
		documento.setEntidad("Proceso");
		documento.setIdEntidad(idProceso);
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Documento.class);
		Example where = Example.create(documento);
		criteria.add(where);
		return (List<Documento>)criteria.list();	
	}
	
	@SuppressWarnings("unchecked")
	public List<Documento> getFileActividad(Long idActividad) {
		Query q = em.createQuery("select new Documento(d.documentoId, d.filename, d.filepath, d.filetype, d.fileExtension, d.bloqueado, d.fechaBloqueo, d.usuarioBloqueo, d.fechaDesbloqueo, d.usuarioDesbloqueo, d.entidad, d.idEntidad, d.usuarioCreacion)" +
		" from py.com.ait.gestion.domain.Documento d where d.entidad = :entidad and d.idEntidad =:idEntidad ");
		
		q.setParameter("entidad", "Actividad");
		q.setParameter("idEntidad", idActividad);
		return q.getResultList();
	
	}

}
	


