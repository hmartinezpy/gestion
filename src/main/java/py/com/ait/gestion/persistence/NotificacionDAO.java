package py.com.ait.gestion.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;

import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.Notificacion;
import py.com.ait.gestion.domain.Proceso;
import py.com.ait.gestion.domain.Rol;
import py.com.ait.gestion.domain.Usuario;

@PersistenceController
public class NotificacionDAO extends JPACrud<Notificacion, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;

	@Inject
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Rol> findPage(int pageSize, int first, String sortField,
			boolean sortOrderAsc) {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Rol.class);

		// add order by
		Order order = Order.asc(sortField);
		if (!sortOrderAsc)
			order = Order.desc(sortField);
		criteria.addOrder(order);

		// add limit, offset
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		return criteria.list();

	}

	public int count() {
		Query q = em.createQuery("select count(*) from Notificacion n");
		return ((Long) q.getSingleResult()).intValue();

	}

	public Long getMaxId() {

		Query q = em.createQuery("select max(n.id) from Notificacion n");
		return ((Long) q.getSingleResult());
	}

	@SuppressWarnings("unchecked")
	public List<Notificacion> getAlertasInicio() {

		List<Notificacion> list = new ArrayList<Notificacion>();
		Query q = em
				.createQuery("select a from Actividad a, CronogramaDetalle cd "
						+ "where a.cronogramaDetalle.cronogramaDetalleId = cd.cronogramaDetalleId "
						+ "and a.estado = 'NUE' and cd.alertaInicio = 'S'");
		List<Actividad> listAct = (List<Actividad>) q.getResultList();
		Calendar fechaInicioCal = Calendar.getInstance();
		Date fechaInicio;
		Date fechaActual = new Date();
		String descripcion = "";
		String titulo = "";
		for (Actividad a : listAct) {

			// utilizar inicio reprogramado si no es nulo, sino inicio previsto
			fechaInicio = (a.getFechaInicioReprogramado() != null) ? a
					.getFechaInicioReprogramado() : a.getFechaInicioPrevisto();

			// setear fechaInicio y parte horaria a 00:00:00.000
			fechaInicioCal.setTime(fechaInicio);
			fechaInicioCal.set(Calendar.HOUR_OF_DAY, 0);
			fechaInicioCal.set(Calendar.MINUTE, 0);
			fechaInicioCal.set(Calendar.SECOND, 0);
			fechaInicioCal.set(Calendar.MILLISECOND, 0);

			// checkear si fecha actual ya supero fecha de inicio para alertar
			if (fechaActual.after(fechaInicioCal.getTime())) {

				// alertar responsable de la actividad
				titulo = "Alerta de Inicio - Actividad " + a.getNroActividad() 
							+ "-" + a.getMaster().getNroProceso();
				descripcion = "INICIO DE ACTIVIDAD TRANSCURRIDO Y AUN EN ESTADO NUEVO!!<br>"
						+ "ACTIVIDAD: " + a.getDescripcion() + "<br>"
						+ "PROCESO: " + a.getMaster().getDescripcion() + "<br>"
						+ "RESPONSABLE: " + a.getResponsable().getUsuario() + "<br>"
						+ "FECHA INICIO: " + fechaInicioCal.getTime().toString();
				list.add(crearNotificacion(titulo, descripcion, a, a.getResponsable()));

			}// end if fechaActual is after fechaInicio

		}// end for actividad

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Notificacion> getAlertasReprogramacionProcesos() {

		List<Notificacion> list = new ArrayList<Notificacion>();
		Query q = em
				.createQuery("select p from Proceso p where p.estado = 'PRO' " +
							 "and p.fechaFinReprogramada is not null " +
							 "and (p.clienteNotificado is null or p.clienteNotificado = 'No')");
		List<Proceso> listProc = (List<Proceso>) q.getResultList();
		String descripcion = "";
		String titulo = "";
		for (Proceso p : listProc) {

			// alertar responsable del proceso
			titulo = "Alarma de Reprogramación de Fin - Proceso " + p.getNroProceso();
			descripcion = "FECHA DE FIN DE PROCESO REPROGRAMADA Y CLIENTE NO FUE NOTIFICADO AUN!!<br>"
					+ "PROCESO: " + p.getDescripcion() + "<br>"
					+ "RESPONSABLE: " + p.getResponsable().getUsuario() + "<br>"
					+ "FECHA FIN REPROGRAMADA: " + p.getFechaFinReprogramada().toString();
			list.add(crearNotificacion(titulo, descripcion, p, p.getResponsable()));

		}// end for proceso
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Notificacion> getAlertasActividades() {

		List<Notificacion> list = new ArrayList<Notificacion>();
		Query q = em
				.createQuery("select a from Actividad a where a.estado = 'PRO' and a.alerta is not null");
		List<Actividad> listAct = (List<Actividad>) q.getResultList();
		Calendar fechaFinCal = Calendar.getInstance();
		Date fechaFin;
		Date fechaActual = new Date();
		String descripcion = "";
		String titulo = "";
		for (Actividad a : listAct) {

			// utilizar inicio reprogramado si no es nulo, sino inicio previsto
			fechaFin = (a.getFechaFinReprogramada() != null) ? a
					.getFechaFinReprogramada() : a.getFechaFinPrevista();

			// setear fechaFin y restar dias/horas
			fechaFinCal.setTime(fechaFin);
			fechaFinCal.add(Calendar.DAY_OF_MONTH, a.getAlerta().getDias().intValue() * (-1));
			fechaFinCal.add(Calendar.HOUR_OF_DAY, a.getAlerta().getHoras().intValue() * (-1));

			// checkear si fecha actual ya supero fecha de fin menos alerta para notificar
			if (fechaActual.after(fechaFinCal.getTime())) {

				// alertar responsable de la actividad
				titulo = "Alerta de Actividad " + a.getActividadId()
							+ "-" + a.getMaster().getNroProceso();
				descripcion = "FIN DE ACTIVIDAD CERCANO Y AUN EN ESTADO EN PROCESO!!<br>"
						+ "ACTIVIDAD: " + a.getDescripcion() + "<br>"
						+ "PROCESO: " + a.getMaster().getDescripcion() + "<br>"
						+ "RESPONSABLE: " + a.getResponsable().getUsuario() + "<br>"
						+ "FECHA FIN: " + fechaFin.toString();
				list.add(crearNotificacion(titulo, descripcion, a, a.getResponsable()));

				// alertar a responsable1 y responsable2 segun cronograma detalle
				if (a.getAlerta().getResponsable1() != null
					&& a.getAlerta().getResponsable1().getUsuarioId() != a.getResponsable().getUsuarioId()) {

					// alertar responsable 1
					list.add(crearNotificacion(titulo, descripcion, a, 
								a.getAlerta().getResponsable1()));
				}

				if (a.getAlerta().getResponsable2() != null
					&& a.getAlerta().getResponsable2().getUsuarioId() != a.getResponsable().getUsuarioId()) {

					// alertar responsable 2
					list.add(crearNotificacion(titulo, descripcion, a, 
								a.getAlerta().getResponsable2()));
				}
			}// end if fechaActual is after fechaFin

		}// end for actividad
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Notificacion> getAlarmasActividades() {

		List<Notificacion> list = new ArrayList<Notificacion>();
		Query q = em
				.createQuery("select a from Actividad a where a.estado = 'PRO' and a.alarma is not null");
		List<Actividad> listAct = (List<Actividad>) q.getResultList();
		Calendar fechaFinCal = Calendar.getInstance();
		Date fechaFin;
		Date fechaActual = new Date();
		String descripcion = "";
		String titulo = "";
		for (Actividad a : listAct) {

			// utilizar inicio reprogramado si no es nulo, sino inicio previsto
			fechaFin = (a.getFechaFinReprogramada() != null) ? a
					.getFechaFinReprogramada() : a.getFechaFinPrevista();

			// setear fechaFin y restar dias/horas
			fechaFinCal.setTime(fechaFin);			
			fechaFinCal.add(Calendar.DAY_OF_MONTH, a.getAlarma().getDias().intValue());
			fechaFinCal.add(Calendar.HOUR_OF_DAY, a.getAlarma().getHoras().intValue());

			// checkear si fecha actual ya supero fecha de fin menos alerta para notificar
			if (fechaActual.after(fechaFinCal.getTime())) {

				// alertar responsable de la actividad
				titulo = "Alarma de Actividad " + a.getActividadId()
							+ "-" + a.getMaster().getNroProceso();
				descripcion = "FIN DE ACTIVIDAD TRANSCURRIDO Y AUN EN ESTADO EN PROCESO!!<br>"
						+ "ACTIVIDAD: " + a.getDescripcion() + "<br>"
						+ "PROCESO: " + a.getMaster().getDescripcion() + "<br>"
						+ "RESPONSABLE: " + a.getResponsable().getUsuario() + "<br>"
						+ "FECHA FIN: " + fechaFin.toString();
				list.add(crearNotificacion(titulo, descripcion, a, a.getResponsable()));

				// alertar a responsable1 y responsable2 segun cronograma detalle
				if (a.getAlarma().getResponsable1() != null
					&& a.getAlarma().getResponsable1().getUsuarioId() != a.getResponsable().getUsuarioId()) {

					// alertar responsable 1					
					list.add(crearNotificacion(titulo, descripcion, a, 
								a.getAlarma().getResponsable1()));
				}

				if (a.getAlarma().getResponsable2() != null
					&& a.getAlarma().getResponsable2().getUsuarioId() != a.getResponsable().getUsuarioId()) {

					// alertar responsable 2
					list.add(crearNotificacion(titulo, descripcion, a, 
								a.getAlarma().getResponsable2()));
				}
			}// end if fechaActual is after fechaFin

		}// end for actividad
		return list;
	}
	
	private Notificacion crearNotificacion(String titulo, String descripcion,
											Object entidad, Usuario usuarioNotificacion) {
		
		String entidadDesc = "";
		Long entidadId = null;
		Usuario responsable = null;		
		if(entidad instanceof Actividad) {
			
			entidadDesc = "Actividad";
			entidadId = ((Actividad) entidad).getActividadId();
			responsable = ((Actividad) entidad).getResponsable();
		} else if(entidad instanceof Proceso) {
			
			entidadDesc = "Proceso";
			entidadId = ((Proceso) entidad).getProcesoId();
			responsable = ((Proceso) entidad).getResponsable();
		}
		
		Notificacion notificacion = new Notificacion();
		notificacion.setTitulo(titulo);		
		notificacion.setDescripcion(descripcion);
		notificacion.setEntidad(entidadDesc);
		notificacion.setEntidadId(entidadId);
		notificacion.setResponsable(responsable);
		notificacion.setUsuario(usuarioNotificacion);
		notificacion.setEstado(Definiciones.EstadoNotificacion.Activo);
		notificacion.setMostrado("N");
		notificacion.setFechaMostrado(null);
		notificacion.setFechaCreacion(new Date());
		
		return notificacion;
	}

	/*
	 *	Retorna notificaciones del usuario recibido.
	 *	PeriodoCorte indica desde cuando traer notificaciones, utiliza el siguiente formato:
	 *	  nF, donde n es un número entero mayor a 0 y F toma {S, M, A} significando Semanas, Meses, Años
	 *	  Ejs: 1S indica 1 Semana atrás, 1M indica 1 Mes atrás, 1A indica 1 Año atrás
	 *	  Por default toma 1S, en caso de recibir nulo, vacío o un valor con formato incorrecto
	 */
	@SuppressWarnings("unchecked")
	public List<Notificacion> getNotificaciones(String usuarioNotificaciones, String periodoCorte) {
		
		String filtroCorte = "";
		int dateField;
		
		//corte por default y calculo de cantidad
		if(periodoCorte == null) periodoCorte = "1S";
		else if(periodoCorte.trim().equals("")) periodoCorte = "1S";		
		int cantidad = 1; //default 1
		try {
			cantidad = Integer.parseInt(periodoCorte.substring(0, periodoCorte.length()-1));
			if(cantidad <= 0) { //formato incorrecto, usar default
				cantidad = 1;
				periodoCorte = "1S"; 
			}
		} catch(Exception ex) { 
			//periodoCorte con formato incorrecto, utilizar 1 semana
			periodoCorte = "1S"; //para garantizar que se aplique 1 semana
		}
		
		//tomar fechaActual para aplicar substracción de acuerdo al periodo de corte
		Calendar fechaCorte = Calendar.getInstance();
		fechaCorte.set(Calendar.HOUR_OF_DAY, 0);
		fechaCorte.set(Calendar.MINUTE, 0);
		fechaCorte.set(Calendar.SECOND, 0);
		fechaCorte.set(Calendar.MILLISECOND, 0);
		
		//calcular campo: semana (default), mes o año
		if(periodoCorte.toUpperCase().endsWith("M")) dateField = Calendar.MONTH;
		else if(periodoCorte.toUpperCase().endsWith("A")) dateField = Calendar.YEAR;
		else dateField = Calendar.WEEK_OF_MONTH;
		
		//substraer periodo de corte a fecha actual y calcular filtro
		fechaCorte.add(dateField, cantidad * (-1));
		filtroCorte = " and n.fechaCreacion >= :fechaCorte";
		
		Query q = em
				.createQuery("select n from Notificacion n where n.estado <> 'INA'" +
							 " and n.usuario.usuario = :usuario " + filtroCorte +
							 " order by n.fechaCreacion desc, n.notificacionId desc");
		q.setParameter("usuario", usuarioNotificaciones);
		q.setParameter("fechaCorte", fechaCorte.getTime());
		logger.debug(">>>>>Usuario de notificacion: " + usuarioNotificaciones);
		logger.debug(">>>>>Fecha de corte: " + fechaCorte.getTime().toString());
		return (List<Notificacion>) q.getResultList();
	}
}
