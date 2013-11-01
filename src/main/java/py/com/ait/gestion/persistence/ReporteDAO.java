
package py.com.ait.gestion.persistence;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.ticpy.tekoporu.stereotype.PersistenceController;

import py.com.ait.gestion.domain.ReportePendienteBean;

@PersistenceController
public class ReporteDAO {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<ReportePendienteBean> getDatosReportePendientes() {

		String sqlQuery = "select coalesce(ua.usuario, up.usuario) as responsable, " + 
					"cr.nombre as tipo_proceso, c.nombre as cliente, p.nro_proceso, " +
					"p.descripcion as proceso, a.nro_actividad, a.descripcion as actividad, " + 
					"coalesce(a.estado,p.estado) as estado, " +
					"case when a.super_tarea is not null then 'SubActividad' " + 
					"when a.id is not null then 'Actividad'  " +
					"else 'Proceso' end as tipo, " +
					"coalesce(a.fecha_inicio_reprogramado,a.fecha_inicio_previsto, " +
					"p.fecha_inicio_real) as inicio_previsto,  " +
					"coalesce(a.fecha_fin_reprogramada,a.fecha_fin_prevista, " +
					"p.fecha_fin_reprogramada,p.fecha_fin_prevista) as fin_previsto,now() " +
					"from cliente c, cronograma cr, usuario up, proceso p  " +
					"left join actividad a on a.proceso = p.id " +
					"left join usuario ua on a.responsable = ua.id " +
					"where p.cliente = c.id " +
					"and p.cronograma = cr.id " +
					"and p.responsable = up.id " +
					"and p.estado in ('NUE','PRO') " +
					"and (a.estado in ('NUE','PRO') or a.estado is null) " +
					"order by responsable, tipo, cliente, inicio_previsto";
		
		List list = em.createNativeQuery(sqlQuery).getResultList();
		Iterator it = list.iterator();
		Object[] row;
		List<ReportePendienteBean> result =  new ArrayList<ReportePendienteBean>();
		ReportePendienteBean bean;
		while(it.hasNext()) {
			
			row = (Object[]) it.next();
			bean = new ReportePendienteBean();
			bean.setResponsable(row[0].toString());
			bean.setTipo_proceso(row[1].toString());
			bean.setCliente(row[2].toString());
			bean.setNro_proceso(row[3].toString());
			bean.setProceso(row[4].toString());
			if(row[5] != null) 
				bean.setNro_actividad(row[5].toString());
			if(row[6] != null)
				bean.setActividad(row[6].toString());
			bean.setEstado(row[7].toString());
			bean.setTipo(row[8].toString());
			bean.setInicio_previsto((Timestamp)row[9]);
			bean.setFin_previsto((Timestamp)row[10]);
			bean.setNow((Timestamp)row[11]);
			result.add(bean);
		}
		
		return result;
	}	

}