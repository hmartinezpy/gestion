package py.com.ait.gestion.persistence;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;
import py.com.ait.gestion.domain.Observacion;

@PersistenceController
public class ObservacionDAO extends JPACrud<Observacion, Long> {
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Observacion> getObsProceso(Long idProceso) {
		Query q = em.createQuery("select new Observacion(o.observacionId, o.descripcion, o.fechaHora, o.entidad, o.idEntidad, o.usuario)" +
		" from py.com.ait.gestion.domain.Observacion o where o.entidad = :entidad and o.idEntidad =:idEntidad "+
		" order by o.fechaHora desc");
		
		q.setParameter("entidad", "Proceso");
		q.setParameter("idEntidad", idProceso);
		return q.getResultList();
	
	}
	
	@SuppressWarnings("unchecked")
	public List<Observacion> getObsActividad(Long idActividad) {
		Query q = em.createQuery("select new Observacion(o.observacionId, o.descripcion, o.fechaHora, o.entidad, o.idEntidad, o.usuario)" +
		" from py.com.ait.gestion.domain.Observacion o where o.entidad = :entidad and o.idEntidad =:idEntidad ");
		
		q.setParameter("entidad", "Actividad");
		q.setParameter("idEntidad", idActividad);
		return q.getResultList();
	
	}
	

}
	


