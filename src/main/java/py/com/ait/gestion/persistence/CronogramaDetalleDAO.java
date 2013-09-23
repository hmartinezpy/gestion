package py.com.ait.gestion.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;

import py.com.ait.gestion.domain.Cronograma;
import py.com.ait.gestion.domain.CronogramaDetalle;

@PersistenceController
public class CronogramaDetalleDAO extends JPACrud<CronogramaDetalle, Long> {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<CronogramaDetalle> findByCronograma(Cronograma c) {

		Query q = em
				.createQuery("select cd from CronogramaDetalle cd where cd.master = :c");
		q.setParameter("c", c);
		return (List<CronogramaDetalle>) q.getResultList();
	}

	public CronogramaDetalle getFirstCronogramaDetalleByCronograma(Cronograma c) {

		List<CronogramaDetalle> list = findByCronograma(c);
		Map<Long, Long> map = new HashMap<Long, Long>();
		CronogramaDetalle detalle = null;
		for (CronogramaDetalle cd : list) {

			if (cd.getTareaSiguiente() != null) {
				map.put(cd.getTareaSiguiente().getCronogramaDetalleId(), cd
						.getTareaSiguiente().getCronogramaDetalleId());
			}

			if (cd.getRespuestaNo() != null) {
				map.put(cd.getRespuestaNo().getCronogramaDetalleId(), cd
						.getRespuestaNo().getCronogramaDetalleId());
			}

			if (cd.getRespuestaSi() != null) {
				map.put(cd.getRespuestaSi().getCronogramaDetalleId(), cd
						.getRespuestaSi().getCronogramaDetalleId());
			}
		}

		for (CronogramaDetalle cd : list) {

			if (!map.containsKey(cd.getCronogramaDetalleId())) {

				detalle = cd;
				break;
			}
		}

		return detalle;
	}

	public Long getMaxId() {

		Query q = em.createQuery("select max(c.id) from CronogramaDetalle c");
		return ((Long) q.getSingleResult());
	}

	public CronogramaDetalle getNextCronogramaDetalle(
			CronogramaDetalle cronogramaDetalle, String respuesta) {
		Long nextCronogramaDetalleId = null;
		CronogramaDetalle nextCronogramaDetalle = null;

		if (cronogramaDetalle.getTareaSiguiente() == null
				&& cronogramaDetalle.getRespuestaSi() == null
				&& cronogramaDetalle.getRespuestaNo() == null) {
			// Soy el ultimo cronograma detalle, no hay siguiente
			return null;
		}

		System.out
				.println("CronogramaDetalleDAO.getNextCronogramaDetalle() pregunta: "
						+ cronogramaDetalle.getPregunta());
		System.out
		.println("CronogramaDetalleDAO.getNextCronogramaDetalle() respuesta: "
				+ respuesta);

		if (cronogramaDetalle.getPregunta() == null) {
			nextCronogramaDetalleId = cronogramaDetalle.getTareaSiguiente()
					.getCronogramaDetalleId();
		} else if ("".equals(cronogramaDetalle.getPregunta())) {
			nextCronogramaDetalleId = cronogramaDetalle.getTareaSiguiente()
					.getCronogramaDetalleId();
		} else if ("SI".equals(respuesta)) {
			nextCronogramaDetalleId = cronogramaDetalle.getRespuestaSi()
					.getCronogramaDetalleId();
		} else {
			nextCronogramaDetalleId = cronogramaDetalle.getRespuestaNo()
					.getCronogramaDetalleId();
		}

		nextCronogramaDetalle = load(nextCronogramaDetalleId);
		return nextCronogramaDetalle;
	}
}