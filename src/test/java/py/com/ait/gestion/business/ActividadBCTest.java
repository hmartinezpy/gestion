package py.com.ait.gestion.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ticpy.tekoporu.junit.DemoiselleRunner;

import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.Cronograma;
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.domain.Proceso;
import py.com.ait.gestion.persistence.CronogramaDetalleDAO;
import py.com.ait.gestion.persistence.ProcesoDAO;

@RunWith(DemoiselleRunner.class)
public class ActividadBCTest {

	@Inject
	private ActividadBC actividadBC;

	@Inject
	private CronogramaDetalleDAO cronogramaDetalleDAO;
	
	@Inject
	private ProcesoDAO procesoDAO;
	
	@Before
	public void before() {
		
		
	}

	@Test
	public void testInsertActividadFromCronogramaDetalle() {
		
		Long procesoId = 1L;
		Proceso p = procesoDAO.load(procesoId);		
		CronogramaDetalle cd = cronogramaDetalleDAO.getFirstCronogramaDetalleByCronograma(p.getCronograma());
		actividadBC.insertActividadFromCronogramaDetalle(cd, null, p);		
		Actividad actividad = actividadBC.load(actividadBC.getMaxId());
		assertNotNull(actividad);
		assertEquals(actividad.getMaster().getProcesoId(), p.getProcesoId());
		assertEquals(actividad.getCronogramaDetalle().getCronogramaDetalleId(), cd.getCronogramaDetalleId());
	}
	
}