
package py.com.ait.gestion.persistence;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;

import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.ActividadChecklistDetalle;

@PersistenceController
public class ActividadChecklistDetalleDAO extends JPACrud<ActividadChecklistDetalle, Long> {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	/**
	 * @param actividadSeleccionada
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ActividadChecklistDetalle> getChecklistByActividad(
			Actividad actividadSeleccionada) {

		Query q = em
				.createQuery("select acd from ActividadChecklistDetalle acd where acd.actividad = :a");
		q.setParameter("a", actividadSeleccionada);
		return (List<ActividadChecklistDetalle>) q.getResultList();
	}

	/**
	 * @param actividad
	 * @return
	 */
	public Long validarCumplimiento(Actividad actividad) {
		String query = "select count(*) from ActividadChecklistDetalle a";
		String where = " where actividad = :actividad" +
					" and a.respuesta not in ('SI')";

		Query q = em
				.createQuery(query+where);
		q.setParameter("actividad", actividad);
		System.out.println("ActividadChecklistDetalleDAO.validarCumplimiento() query "+query+where);
		return ((Long) q.getSingleResult());
	}

}
