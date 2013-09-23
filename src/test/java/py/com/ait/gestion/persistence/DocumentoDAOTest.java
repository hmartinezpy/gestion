package py.com.ait.gestion.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ticpy.tekoporu.junit.DemoiselleRunner;

import py.com.ait.gestion.domain.Documento;

@RunWith(DemoiselleRunner.class)
public class DocumentoDAOTest {

	@Inject
	private DocumentoDAO documentoDAO;

	@Before
	public void before() {
		
		
	}

	@Test
	public void testGetFileProceso() {
		
		long procesoId = 4L;
		List<Documento> list = documentoDAO.getFileProceso(procesoId);
		assertNotNull(list);
		assertTrue(list.size() > 0);
		
		for(Documento d : list) {
		
			System.out.print("DocumentoId: " + d.getDocumentoId());
			if(d.getUsuarioCreacion() != null)
				System.out.print(", UsuarioCreacion: " 
						+ d.getUsuarioCreacion().getUsuario());
			System.out.println();
		}
	}
}