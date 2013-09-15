package py.com.ait.gestion.business;

import java.util.Calendar;
import java.util.Date;
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
import py.com.ait.gestion.domain.TipoAlarma;
import py.com.ait.gestion.domain.Usuario;
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

			actividad.setNroActividad(getSiguienteNroActividad(null));
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

	public String getSiguienteNroActividad(Actividad a) {
		String nroActividadActual = getNumeroActividadActual(a);
		StringBuilder sb = new StringBuilder();
		// int year = Calendar.getInstance().get(Calendar.YEAR);
		String nroActividad = sb.append(
				(Integer.parseInt(nroActividadActual) + 1)).toString();
		// .append("/")
		// .append(year).toString();
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
			// mensaje += "pregunta: " + actividad.getPregunta() +
			// " respuesta: "
			// + actividad.getRespuesta();
			// messageContext.add(mensaje);
			System.out.println("ActividadBC.editar() " + mensaje);
			logger.error(">>>> " + mensaje);
			throw new RuntimeException(mensaje);

		} else if (actividad.getRespuesta() != null
				&& !actividad.getRespuesta().equals("SI")
				&& !actividad.getRespuesta().equals("NO")) {

			String mensaje = "La respuesta debe ser SI o NO.";
			// messageContext.add(mensaje);
			System.out.println("ActividadBC.editar() " + mensaje);
			logger.error(">>>> " + mensaje);
			throw new RuntimeException(mensaje);

		} else {
			logger.info("ActividadBC.editar() Resolver la actividad! pregunta: "
					+ actividad.getPregunta()
					+ " respuesta: "
					+ actividad.getRespuesta());
			resolveActividadAndInsertNext(actividad);
		}
		System.out
				.println("ActividadBC.resolveActividad() Marcando actividad como resuelta <<<<<<<");
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
			Actividad actividadAnterior, Proceso proceso) {

		Actividad actividad = new Actividad();
		actividad.setMaster(proceso);
		actividad.setNroActividad(getSiguienteNroActividad(actividadAnterior));
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
			actAnterior.setActividadId(actividadAnterior.getActividadId());
		}
		actividad.setActividadAnterior(actAnterior);
		actividad.setAlerta(cd.getAlerta());
		actividad.setAlarma(cd.getAlarma());

		insert(actividad);
	}

	public Long getMaxId() {
		return actividadDAO.getMaxId();
	}

	public String getNumeroActividadActual(Actividad a) {
		return actividadDAO.getLastNroActividadByActividad(a);
	}

	public String getNumeroSubActividadByActividad(Actividad actividad) {
		return actividadDAO.getCurrentNumeroSubActividadByActividad(actividad);
	}

	@Transactional
	private void resolveActividadAndInsertNext(Actividad a) {

		CronogramaDetalle cd = cronogramaDetalleBC.getNextCronogramaDetalle(
				a.getCronogramaDetalle(), a.getRespuesta());

		if (cd == null) { // ya no hay siguiente cronograma detalle
			return;
		} else {
			insertActividadFromCronogramaDetalle(cd, a, a.getMaster());
		}
	}

	@Transactional
	public Actividad crearSubActividad(Actividad padre, String descripcion,
			Usuario responsable, TipoAlarma alerta, TipoAlarma alarma) {
		Actividad result = new Actividad();

		result.setMaster(padre.getMaster());
		result.setNroActividad(getNextNroSubActividad(padre));
		result.setCronogramaDetalle(null);
		result.setDescripcion(descripcion);
		result.setResponsable(responsable);
		result.setFechaCreacion(new Date());
		result.setEstado(Definiciones.EstadoActividad.Nueva);
		result.setAlerta(alerta);
		result.setAlarma(alarma);
		result.setSuperTarea(padre);

		insert(result);

		return result;
	}

	private String getNextNroSubActividad(Actividad actividad) {
		String NroSubActividadActual = getNumeroSubActividadByActividad(actividad);
		System.out
				.println("ActividadBC.getNextNroSubActividad() NroSubActividadActual = "
						+ NroSubActividadActual);

		String currentNroActividad = actividad.getNroActividad();

		int nextSubActividad = Integer.parseInt(NroSubActividadActual);
		nextSubActividad++;
		String nextNroSubActividad = currentNroActividad + "."
				+ nextSubActividad;
		return nextNroSubActividad;
	}

	@Transactional
	public Actividad devolverActividad(Actividad aDevolver) {
		Actividad anterior = load(aDevolver.getActividadAnterior()
				.getActividadId());

		if (anterior.getEstado() == Definiciones.EstadoActividad.Resuelta) {
			String mensaje = "No se puede devolver una actividad que ya está RESUELTA";
			System.out.println("ActividadBC.devolverActividad() " + mensaje);
			logger.error(">>>> " + mensaje);
			throw new RuntimeException(mensaje);
		}

		// Al devolver una actividad, se toman la mayoría de los datos de la
		// actividad anterior a la devuelta, y se crea una nueva instancia
		// con algunos datos establecidos a nuevos.
		Actividad actividadDevuelta = new Actividad();
		actividadDevuelta.setActividadAnterior(anterior.getActividadAnterior());
		actividadDevuelta.setAlarma(anterior.getAlarma());
		actividadDevuelta.setAlerta(anterior.getAlerta());
		actividadDevuelta.setChecklistCompleto(anterior.getChecklistCompleto());
		actividadDevuelta.setCronogramaDetalle(anterior.getCronogramaDetalle());
		actividadDevuelta.setDescripcion(anterior.getDescripcion());
		actividadDevuelta.setPregunta(anterior.getPregunta());
		actividadDevuelta.setRespuesta(anterior.getRespuesta());
		// Es una nueva actividad, cuyo estado irá directamente a "En proceso"
		actividadDevuelta.setEstado(Definiciones.EstadoActividad.EnProceso);
		actividadDevuelta.setFechaCreacion(new Date());
		actividadDevuelta.setMaster(anterior.getMaster());
		actividadDevuelta.setNroActividad(getSiguienteNroActividad(aDevolver));

		aDevolver.setEstado(Definiciones.EstadoActividad.Devuelta);

		update(aDevolver);
		System.out.println("ActividadBC.devolverActividad() Se marcó como devuelta la actividad con id: "+aDevolver.getActividadId());
		insert(actividadDevuelta);
		System.out.println("ActividadBC.devolverActividad() Se insertó la actividad con id: "+actividadDevuelta.getActividadId());

		return actividadDevuelta;
	}
}
