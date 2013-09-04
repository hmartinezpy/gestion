package py.com.ait.gestion.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ticpy.tekoporu.junit.DemoiselleRunner;

import py.com.ait.gestion.domain.Cronograma;
import py.com.ait.gestion.domain.CronogramaDetalle;

@RunWith(DemoiselleRunner.class)
public class CronogramaDetalleDAOTest {

	@Inject
	private CronogramaDetalleDAO cronogramaDetalleDAO;

	@Before
	public void before() {
		
		
	}

	@Test
	public void testFindByCronograma() {
		
		Cronograma c = new Cronograma();
		c.setCronogramaId(1L);		
		List<CronogramaDetalle> list = cronogramaDetalleDAO.findByCronograma(c);
		assertNotNull(list);
		assertTrue(list.size() > 0);
	}
	
	@Test
	public void testGetFirstCronogramaDetalleByCronograma() {
		
		long cronogramaId = 1L;
		Cronograma c = new Cronograma();
		c.setCronogramaId(cronogramaId);
		CronogramaDetalle cd = cronogramaDetalleDAO.getFirstCronogramaDetalleByCronograma(c);
		assertNotNull(cd);
		System.out.println("Cronograma: " + cronogramaId);
		System.out.println("FirstCronogramaDetalle: " + cd.getCronogramaDetalleId());
	}

}