package py.com.ait.gestion.persistence;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.ticpy.tekoporu.stereotype.PersistenceController;
import py.com.ait.gestion.domain.ReporteFacturasBean;
import py.com.ait.gestion.domain.ReportePendienteBean;
import py.com.ait.gestion.domain.ReporteProcesosBean;

@PersistenceController
public class ReporteDAO {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	private String sqlQuery = "";

	@SuppressWarnings("unchecked")
	public List<ReportePendienteBean> getDatosReportePendientes() {

		this.sqlQuery = "select coalesce(ua.usuario, up.usuario) as responsable, "
				+ "cr.nombre as tipo_proceso, c.nombre as cliente, p.nro_proceso,"
				+ "p.descripcion as proceso, a.nro_actividad, a.descripcion as actividad,"
				+ "up.usuario as responsable_proceso,"
				+ "coalesce(a.estado,p.estado) as estado,"
				+ "case when a.super_tarea is not null then 'SubActividad'"
				+ "when a.id is not null then 'Actividad'  "
				+ "else 'Proceso' end as tipo, "
				+ "coalesce(a.fecha_inicio_reprogramado,a.fecha_inicio_previsto,"
				+ "p.fecha_inicio_real) as inicio_previsto, "
				+ "coalesce(a.fecha_fin_reprogramada,a.fecha_fin_prevista, "
				+ "p.fecha_fin_reprogramada,p.fecha_fin_prevista) as fin_previsto,now() "
				+ "from cliente c, cronograma cr, proceso p  "
				+ "left join actividad a on a.proceso = p.id "
				+ "left join usuario ua on a.responsable = ua.id "
				+ "left join usuario up on p.responsable = up.id "
				+ "where p.cliente = c.id "
				+ "and p.cronograma = cr.id "
				+ "and p.responsable = up.id "
				+ "and p.estado in ('NUE','PRO') "
				+ "and (a.estado in ('NUE','PRO') or a.estado is null) "
				+ "order by responsable, tipo, cliente, inicio_previsto";

		List list = em.createNativeQuery(sqlQuery).getResultList();
		Iterator it = list.iterator();
		Object[] row;
		List<ReportePendienteBean> result = new ArrayList<ReportePendienteBean>();
		ReportePendienteBean bean;
		while (it.hasNext()) {

			row = (Object[]) it.next();
			bean = new ReportePendienteBean();
			bean.setResponsable(row[0].toString());
			bean.setTipo_proceso(row[1].toString());
			bean.setCliente(row[2].toString());
			bean.setNro_proceso(row[3].toString());
			bean.setProceso(row[4].toString());
			if (row[5] != null)
				bean.setNro_actividad(row[5].toString());
			if (row[6] != null)
				bean.setActividad(row[6].toString());
			bean.setResponsable_proceso(row[7].toString());
			bean.setEstado(row[8].toString());
			bean.setTipo(row[9].toString());
			bean.setInicio_previsto((Date) row[10]);
			bean.setFin_previsto((Date) row[11]);
			bean.setNow((Timestamp) row[12]);
			result.add(bean);
		}

		return result;
	}

	public List<ReporteProcesosBean> getDatosReporteProcesos(Date desde,
			Date hasta, long cronogramaId, String estado) {

		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		this.sqlQuery = "select c.nombre as cliente, p.fecha_inicio_real as fecha_inicio, "
				+ "coalesce(p.fecha_fin_reprogramada, p.fecha_fin_prevista) as fecha_fin, "
				+ "cr.nombre as tipo_proceso, p.estado, now() "
				+ "from  cronograma cr, proceso p "
				+ "left join cliente c on c.id = p.cliente "
				+ "where to_char(p.fecha_inicio_real,'YYYY-MM-DD') >= '"
				+ sm.format(desde)
				+ "' "
				+ "and to_char(coalesce(p.fecha_fin_reprogramada, p.fecha_fin_prevista),'YYYY-MM-DD') <= '"
				+ sm.format(hasta) + "' ";

		if (cronogramaId != 0) {
			sqlQuery = sqlQuery + " and cr.id = '" + cronogramaId + "' ";
		}
		if (estado.toLowerCase().compareTo("abierto") == 0) {
			sqlQuery = sqlQuery + " and p.estado in ('NUE','PRO') ";
		} else if (estado.toLowerCase().compareTo("cerrado") == 0) {
			sqlQuery = sqlQuery + " and p.estado in ('RES','CAN') ";
		}

		sqlQuery = sqlQuery + " and cr.id = p.cronograma order by cr.nombre";
		List<ReporteProcesosBean> result = new ArrayList<ReporteProcesosBean>();

		List list = em.createNativeQuery(sqlQuery).getResultList();
		Iterator it = list.iterator();

		Object[] row;

		ReporteProcesosBean bean;
		while (it.hasNext()) {

			row = (Object[]) it.next();
			bean = new ReporteProcesosBean();
			bean.setCliente(row[0].toString());
			bean.setFecha_inicio((Date) row[1]);
			bean.setFecha_fin((Date) row[2]);
			bean.setTipo_proceso(row[3].toString());
			bean.setEstado(row[4].toString());
			bean.setNow((Timestamp) row[5]);
			result.add(bean);
		}
		return result;
	}

	public List<ReporteFacturasBean> getDatosReporteFacturas(Date desde,
			Date hasta, String estado) {

		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		sqlQuery = "select c.nombre as cliente, p.descripcion as proceso, a.fecha_cobro,"
				+ "a.nro_factura, a.cheque_nro, a.cheque_banco "
				+ "from  actividad a "
				+ "join proceso p on p.id = a.proceso "
				+ "join cliente c on c.id = p.cliente where a.nro_factura!='' ";
		if (estado.toLowerCase().compareTo("pendientes") == 0) {
			sqlQuery = sqlQuery + " and a.fecha_cobro is null ";
		} else if (estado.toLowerCase().compareTo("cobradas") == 0) {
			if (desde != null && hasta != null) {
				sqlQuery = sqlQuery + " and "
						+ "to_char(a.fecha_cobro,'YYYY-MM-DD') >= '"
						+ sm.format(desde)
						+ "' and to_char(a.fecha_cobro,'YYYY-MM-DD') <= '"
						+ sm.format(hasta) + "'";
			} else if (desde != null && hasta == null) {
				sqlQuery = sqlQuery + " and "
						+ "to_char(a.fecha_cobro,'YYYY-MM-DD') >= '"
						+ sm.format(desde) + "' ";
			} else if (desde == null && hasta != null) {
				sqlQuery = sqlQuery + " and "
						+ "to_char(a.fecha_cobro,'YYYY-MM-DD') <= '"
						+ sm.format(hasta) + "' ";
			} else {
				sqlQuery = sqlQuery + " and a.fecha_cobro is not null";
			}
		} else {// todas
			if (desde != null && hasta != null) {
				sqlQuery = sqlQuery + "and ( (a.fecha_cobro is null) or ("
						+ "to_char(a.fecha_cobro,'YYYY-MM-DD') >= '"
						+ sm.format(desde)
						+ "' and to_char(a.fecha_cobro,'YYYY-MM-DD') <= '"
						+ sm.format(hasta) + "' ) )";
			} else if (desde != null && hasta == null) {
				sqlQuery = sqlQuery + "and ( (a.fecha_cobro is null) or ("
						+ "to_char(a.fecha_cobro,'YYYY-MM-DD') >= '"
						+ sm.format(desde) + "' ) )";
			} else if (desde == null && hasta != null) {
				sqlQuery = sqlQuery + "and ( (a.fecha_cobro is null) or ("
						+ " to_char(a.fecha_cobro,'YYYY-MM-DD') <= '"
						+ sm.format(hasta) + "' ) )";
			}
		}

		sqlQuery = sqlQuery + " order by c.nombre";
		List<ReporteFacturasBean> result = new ArrayList<ReporteFacturasBean>();

		List list = em.createNativeQuery(sqlQuery).getResultList();
		Iterator it = list.iterator();

		Object[] row;

		ReporteFacturasBean bean;
		while (it.hasNext()) {

			row = (Object[]) it.next();
			bean = new ReporteFacturasBean();
			bean.setCliente(row[0].toString());
			bean.setProceso(row[1].toString());
			bean.setFecha_cobro((Date) row[2]);
			if (row[3] != null) {
				bean.setNro_factura(row[3].toString());
			}
			if (row[4] != null) {
				bean.setCheque_nro(row[4].toString());
			}
			if (row[5] != null) {
				bean.setCheque_banco(row[5].toString());
			}
			result.add(bean);
		}
		return result;
	}

	public String getSqlQuery() {

		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {

		this.sqlQuery = sqlQuery;
	}

}
