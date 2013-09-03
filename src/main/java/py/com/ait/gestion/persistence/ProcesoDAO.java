package py.com.ait.gestion.persistence;
import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;
import py.com.ait.gestion.domain.Proceso;

@PersistenceController
public class ProcesoDAO extends JPACrud<Proceso, Long> {
	private static final long serialVersionUID = 1L;

}
	


