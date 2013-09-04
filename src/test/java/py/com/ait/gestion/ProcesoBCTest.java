package py.com.ait.gestion;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ticpy.tekoporu.junit.DemoiselleRunner;

import py.com.ait.gestion.business.ClienteBC;
import py.com.ait.gestion.business.CronogramaBC;
import py.com.ait.gestion.business.ProcesoBC;
import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.domain.Cliente;
import py.com.ait.gestion.domain.Cronograma;
import py.com.ait.gestion.domain.Proceso;
import py.com.ait.gestion.domain.Usuario;

@RunWith(DemoiselleRunner.class)
public class ProcesoBCTest {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Inject
	private ProcesoBC procesoBC;

	@Inject
	private ClienteBC clienteBC;

	@Inject
	private CronogramaBC cronogramaBC;

	@Inject
	private UsuarioBC usuarioBC;

	@Before
	public void before() {
		// for (Proceso p : procesoBC.findAll()) {
		// procesoBC.delete(p.getProcesoId());
		// }
	}

	@Test
	public void testRegistrar() {
		Cliente cliente = clienteBC.load(clienteBC.getMaxId());
		Cronograma cronograma = cronogramaBC.load(cronogramaBC.getMaxId());

		String descripcion = "Proceso Test";
		String estado = "N";
		Date fechaFinPrevista = new Date();
		Date fechaFinReprogramada = new Date();
		Date fechaInicioContratual = new Date();
		Date fechaInicioReal = new Date();
		String motivoReprogramacion = null;
		Usuario responsable = usuarioBC.load(usuarioBC.getMaxId());

		Proceso proceso = new Proceso();
		proceso.setCliente(cliente);
		proceso.setClienteNotificado(null);
		proceso.setCronograma(cronograma);
		proceso.setDescripcion(descripcion);
		proceso.setEstado(estado);
		proceso.setFechaFinPrevista(fechaFinPrevista);
		proceso.setFechaFinReprogramada(fechaFinReprogramada);
		proceso.setFechaInicioContratual(fechaInicioContratual);
		proceso.setFechaInicioReal(fechaInicioReal);
		proceso.setMotivoReprogramacion(motivoReprogramacion);
		proceso.setResponsable(responsable);

		procesoBC.registrar(proceso);

		List<Proceso> listaProcesos = procesoBC.findAll();
		assertNotNull(listaProcesos);

		Proceso procFromDB = procesoBC.load(procesoBC.getMaxId());
		assertTrue(proceso.equals(procFromDB));

	}

}