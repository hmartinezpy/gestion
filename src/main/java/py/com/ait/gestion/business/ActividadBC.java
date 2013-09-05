package py.com.ait.gestion.business;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.ticpy.tekoporu.message.MessageContext;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;

import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.domain.Proceso;
import py.com.ait.gestion.persistence.ActividadDAO;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@BusinessController
public class ActividadBC extends DelegateCrud<Actividad, Long, ActividadDAO> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ActividadDAO actividadDAO;

	@Inject
	private AudLogDAO audLogDAO;

	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	SesionLogDAO sesionLogDAO;

	@Inject
	private MessageContext messageContext;

	@Inject
	private CronogramaDetalleBC cronogramaDetalleBC;

	@Inject
	private Logger logger;

	public List<Actividad> listar() {
		return actividadDAO.findAll();
	}

	public Actividad recuperar(Long id) {
		return actividadDAO.load(id);
	}

	/***************** Auditoria **************************/
	@Transactional
	public void registrar(Actividad actividad) {
		if (actividad.getNroActividad() == null
				|| actividad.getNroActividad().equals("")) {

			actividad.setNroActividad(getSiguienteNroActividad());
		}
		super.insert(actividad);

		try {
			audLogDAO.log(null, actividad, usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()),
					Definiciones.Operacion.Insert, actividad.getActividadId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getSiguienteNroActividad() {
		String nroActividadActual = getNumeroActividadActual();
		StringBuilder sb = new StringBuilder();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String nroActividad = sb
				.append((Integer.parseInt(nroActividadActual) + 1)).append("/")
				.append(year).toString();
		return nroActividad;
	}

	@Transactional
	public void resolveActividad(Actividad actividad) {

		// Si se modifica el estado a Resuelta, validar la respuesta a la
		// pregunta (SI o NO)
		logger.info("ActividadBC.editar() Se está resolviendo la actividad");

		if (actividad.getPregunta() != null
				&& !actividad.getPregunta().equals("")
				&& (actividad.getRespuesta() == null || actividad
						.getRespuesta().equals(""))) {

			String mensaje = "No se puede resolver una actividad con pregunta y sin respuesta.";
//			mensaje += "pregunta: " + actividad.getPregunta() + " respuesta: "
//					+ actividad.getRespuesta();
			// messageContext.add(mensaje);
			System.out.println("ActividadBC.editar() "+mensaje);
			logger.error(">>>> " + mensaje);
			throw new RuntimeException(mensaje);

		} else if (actividad.getRespuesta() != null
				&& !actividad.getRespuesta().equals("SI")
				&& !actividad.getRespuesta().equals("NO")) {

			String mensaje = "La respuesta debe ser SI o NO.";
			// messageContext.add(mensaje);
			System.out.println("ActividadBC.editar() "+mensaje);
			logger.error(">>>> " + mensaje);
			throw new RuntimeException(mensaje);

		} else {
			logger.info("ActividadBC.editar() Resolver la actividad! pregunta: "
					+ actividad.getPregunta()
					+ " respuesta: "
					+ actividad.getRespuesta());
			resolveActividadAndInsertNext(actividad);
		}
		System.out.println("ActividadBC.resolveActividad() Marcando actividad como resuelta <<<<<<<");
		actividad.setEstado(Definiciones.EstadoActividad.Resuelta);
		update(actividad);
		
	}

	@Transactional
	public void editar(Actividad actividad) {

		Actividad viejo = this.load(actividad.getActividadId());

		super.update(actividad);

		try {
			audLogDAO.log(viejo, actividad, usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()),
					Definiciones.Operacion.Update, actividad.getActividadId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional
	public void eliminar(Long id) {

		Actividad actividad = this.recuperar(id);
		super.delete(id);

		try {
			audLogDAO.log(actividad, null, usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()),
					Definiciones.Operacion.Delete, actividad.getActividadId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional
	public void insertActividadFromCronogramaDetalle(CronogramaDetalle cd,
			Long actividadAnterior, Proceso proceso) {

		Actividad actividad = new Actividad();
		actividad.setMaster(proceso);
		actividad.setNroActividad(getSiguienteNroActividad());
		actividad.setCronogramaDetalle(cd);
		actividad.setDescripcion(cd.getTarea());
		Calendar cal = new GregorianCalendar();
		actividad.setFechaCreacion(cal.getTime());
		actividad.setFechaInicioPrevisto(cal.getTime());
		cal.add(Calendar.DATE, cd.getDuracionTarea().intValue());
		actividad.setFechaFinPrevista(cal.getTime());
		if (cd.getPregunta() != null)
			actividad.setPregunta(cd.getPregunta().getDescripcion());
		actividad.setEstado(Definiciones.EstadoActividad.Nueva);
		Actividad actAnterior = null;
		if (actividadAnterior != null) {
			actAnterior = new Actividad();
			actAnterior.setActividadId(actividadAnterior);
		}
		actividad.setActividadAnterior(actAnterior);
		actividad.setAlerta(cd.getAlerta());
		actividad.setAlarma(cd.getAlarma());

		insert(actividad);
	}

	public Long getMaxId() {
		return actividadDAO.getMaxId();
	}

	public String getNumeroActividadActual() {
		return actividadDAO.getLastSequence();
	}

	@Transactional
	private void resolveActividadAndInsertNext(Actividad a) {

		CronogramaDetalle cd = cronogramaDetalleBC.getNextCronogramaDetalle(
				a.getCronogramaDetalle(), a.getRespuesta());

		if (cd == null) { // ya no hay siguiente cronograma detalle
			return;
		} else {
			insertActividadFromCronogramaDetalle(cd, a.getActividadId(),
					a.getMaster());
		}
	}
}
