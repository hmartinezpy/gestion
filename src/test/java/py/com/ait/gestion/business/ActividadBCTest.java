package py.com.ait.gestion.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ticpy.tekoporu.junit.DemoiselleRunner;

import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.domain.Proceso;
import py.com.ait.gestion.domain.TipoAlarma;
import py.com.ait.gestion.domain.Usuario;
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
	
//	@Before
	public void before() {
		
		
	}

	@Test
	public void testInsertActividadFromCronogramaDetalle() {
		
		Long procesoId = procesoDAO.getMaxId();
		Proceso p = procesoDAO.load(procesoId);		
		CronogramaDetalle cd = cronogramaDetalleDAO.getFirstCronogramaDetalleByCronograma(p.getCronograma());
		actividadBC.insertActividadFromCronogramaDetalle(cd, null, p);		
		Actividad actividad = actividadBC.load(actividadBC.getMaxId());
		assertNotNull(actividad);
		assertEquals(actividad.getMaster().getProcesoId(), p.getProcesoId());
		assertEquals(actividad.getCronogramaDetalle().getCronogramaDetalleId(), cd.getCronogramaDetalleId());
	}

	@Test
	public void resolverActividadAndtInsertNext() {
		Long actividadId=actividadBC.getMaxId();
		if (actividadId == null)
			return;
		Actividad a = actividadBC.load(actividadId);
		a.setEstado(Definiciones.EstadoActividad.Resuelta);
		if (a.getPregunta() != null)
			a.setRespuesta("SI");
		actividadBC.resolveActividad(a, null);
		Actividad actividad = actividadBC.load(actividadBC.getMaxId());

		assertNotNull(actividad);
		assertEquals(actividad.getMaster().getProcesoId(), a.getMaster().getProcesoId());
	}
	
	@Test
	public void insertSubActividad() {
		
		Long actividadId=actividadBC.getMaxId();
		if (actividadId == null)
			return;
		Actividad padre = actividadBC.load(actividadId);
		double rand = Math.round(Math.random() * 100);
		String descripcion = "descripcion "+ rand;
		Usuario responsable = null;
		TipoAlarma alerta= null;
		TipoAlarma alarma= null;
		Actividad subActividad = actividadBC.crearSubActividad(padre, descripcion, responsable, alerta, alarma);
//		Actividad subActividad = actividadBC.load(actividadBC.getMaxId());

		assertNotNull(subActividad);
		assertEquals(subActividad.getMaster().getProcesoId(), padre.getMaster().getProcesoId());
		assertEquals(subActividad.getSuperTarea().getActividadId(), padre.getActividadId());

	}
	
	@Test
	public void devolverActividad() {
		
		Long actividadId=actividadBC.getMaxId();
		Actividad aDevolver = actividadBC.load(actividadId);
		if (actividadId == null)
			return;
		
		Actividad anteriorDeVuelta = null;
		try{
		anteriorDeVuelta = actividadBC.devolverActividad(aDevolver);
//		Actividad subActividad = actividadBC.load(actividadBC.getMaxId());
		} catch (Exception ex){
			ex.printStackTrace();
		}

		assertNotNull(anteriorDeVuelta);
		assertEquals(anteriorDeVuelta.getMaster().getProcesoId(), aDevolver.getMaster().getProcesoId());

	}
	
	
}