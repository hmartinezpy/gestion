package py.com.ait.gestion.persistence;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;
import py.com.ait.gestion.domain.TipoAlarma;

@PersistenceController
public class TipoAlarmaDAO extends JPACrud<TipoAlarma, Long> {
	private static final long serialVersionUID = 1L;



	@Inject
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<TipoAlarma> getAlarmas() {
		String a = "Ala";
		Query q = em.createQuery("select new TipoAlarma(tp.tipoAlarmaId, tp.descripcion, tp.tipo, tp.dias, tp.horas)" +
		" from py.com.ait.gestion.domain.TipoAlarma tp where tp.tipo = :name");
		
		q.setParameter("name", a);
		return q.getResultList();
	
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoAlarma> getAlertas() {
		String a = "Ale";
		Query q = em.createQuery("select new TipoAlarma(tp.tipoAlarmaId, tp.descripcion, tp.tipo, tp.dias, tp.horas)" +
		" from py.com.ait.gestion.domain.TipoAlarma tp where tp.tipo = :name");
		
		q.setParameter("name", a);
		return q.getResultList();
	
	}


}