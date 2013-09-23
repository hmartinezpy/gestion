package py.com.ait.gestion.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ticpy.tekoporu.junit.DemoiselleRunner;

import py.com.ait.gestion.domain.Rol;

@RunWith(DemoiselleRunner.class)
public class DocumentoRolDAOTest {

	@Inject
	private DocumentoRolDAO documentoRolDAO;

	@Before
	public void before() {
		
		
	}

	@Test
	public void testGetRolesByDocumento() {
		
		long documentoId = 1L;
		List<Rol> list = documentoRolDAO.getRolesByDocumento(documentoId);
		assertNotNull(list);
		assertTrue(list.size() > 0);
		
		for(Rol r : list){
			
			System.out.println(">>>>>RolId: " + r.getRolId() + ", Descripcion: " + r.getDescripcion());
		}
	}
}