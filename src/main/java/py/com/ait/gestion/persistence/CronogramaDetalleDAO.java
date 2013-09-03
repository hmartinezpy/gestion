package py.com.ait.gestion.persistence;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;
import py.com.ait.gestion.domain.CronogramaDetalle;

@PersistenceController
public class CronogramaDetalleDAO extends JPACrud<CronogramaDetalle, Long> {
	private static final long serialVersionUID = 1L;




}