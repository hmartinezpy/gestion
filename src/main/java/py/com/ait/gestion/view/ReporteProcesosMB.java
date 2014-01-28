package py.com.ait.gestion.view;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import net.sf.jasperreports.engine.JRException;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.slf4j.Logger;
import org.ticpy.tekoporu.report.Report;
import org.ticpy.tekoporu.report.Type;
import org.ticpy.tekoporu.report.annotation.Path;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;
import org.ticpy.tekoporu.util.FileRenderer;
import py.com.ait.gestion.business.CronogramaBC;
import py.com.ait.gestion.domain.Cronograma;
import py.com.ait.gestion.domain.ReporteProcesosBean;
import py.com.ait.gestion.persistence.ReporteDAO;

@ViewController
public class ReporteProcesosMB {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;
	
	@Inject
	private ReporteDAO reporteDAO;

	@Inject
	@Path("reports/repProcesos.jasper")
	private Report reporteProcesos;

	@Inject
	private FileRenderer renderer;
	@NotNull
	private Date hasta = new Date();
	@NotNull
	private Date desde = new Date();
	
	private long cronogramaId;
	private String estado;

	@Inject
	private CronogramaBC cronogramaBC;

	private List<Cronograma> cronogramas;

	@Inject
	private Logger logger;

	public void printPdfProcesos() throws JRException {

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("fecha_desde", this.getDesde());
			param.put("fecha_hasta", this.getHasta());
			param.put("estado_filtro", this.getEstado());
			if (this.getCronogramaId() == 0) {
				param.put("tipo_proceso_filtro", "Todos");
			} else {
				param.put("tipo_proceso_filtro", (this.cronogramaBC.load(this
						.getCronogramaId())).getNombre());
			}
			List<ReporteProcesosBean> list = reporteDAO
					.getDatosReporteProcesos(this.desde, this.hasta,
							this.cronogramaId, this.estado);
			this.logger.info("List.size: " + list.size());
			this.logger.info("Query: " + this.reporteDAO.getSqlQuery());
			byte[] buffer = this.reporteProcesos.export(list, param, Type.PDF);
			this.renderer.render(buffer, FileRenderer.ContentType.PDF,
					"reporteEstadisticoProcesos.pdf");
		
	}
	
	public void agregarMensaje(String mensaje) {

		this.facesContext.addMessage("suceso", new FacesMessage(mensaje));
	}


	public Date getDesde() {

		return desde;
	}

	public void setDesde(Date desde) {

		this.desde = desde;
	}

	public Date getHasta() {

		return hasta;
	}

	public void setHasta(Date hasta) {

		this.hasta = hasta;
	}

	public long getCronogramaId() {

		return cronogramaId;
	}

	public void setCronogramaId(long cronogramaId) {

		this.cronogramaId = cronogramaId;
	}

	public String getEstado() {

		return estado;
	}

	public void setEstado(String estado) {

		this.estado = estado;
	}

	public List<Cronograma> getCronogramas() {

		this.cronogramas = this.cronogramaBC.listar();
		return this.cronogramas;
	}

	public void setCronogramas(List<Cronograma> cronogramas) {

		this.cronogramas = cronogramas;
	}

}
