package py.com.ait.gestion.view;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.ticpy.tekoporu.report.Report;
import org.ticpy.tekoporu.report.Type;
import org.ticpy.tekoporu.report.annotation.Path;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.util.FileRenderer;
import py.com.ait.gestion.domain.ReporteFacturasBean;
import py.com.ait.gestion.persistence.ReporteDAO;

@ViewController
public class ReporteFacturasMB {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private ReporteDAO reporteDAO;

	@Inject
	@Path("reports/repFacturas.jasper")
	private Report reporteFacturas;

	@Inject
	private FileRenderer renderer;
	private Date hasta = new Date();
	private Date desde = new Date();
	private String estado = "";

	@Inject
	private Logger logger;

	public void printPdfFacturas() throws JRException {

		Map<String, Object> param = new HashMap<String, Object>();
		if (this.estado.compareTo("Pendientes") == 0) {
			param.put("titulo", "REPORTE DE FACTURAS PENDIENTES");
			param.put("fecha_desde", null);
			param.put("fecha_hasta", null);

		} else {
			if (this.estado.compareTo("Todas") == 0) {
				param.put("titulo", "REPORTE DE FACTURAS");
				param.put("fecha_desde", this.desde);
				param.put("fecha_hasta", this.hasta);
			} else {
				param.put("titulo", "REPORTE DE FACTURAS COBRADAS");
				param.put("fecha_desde", this.desde);
				param.put("fecha_hasta", this.hasta);
			}
		}
		List<ReporteFacturasBean> list = reporteDAO.getDatosReporteFacturas(
				this.desde, this.hasta, this.estado);
		logger.info("Query: " + reporteDAO.getSqlQuery());
		this.logger.info(">>>>TAMANIO DE RESULTADO: "+list.size());
		byte[] buffer = this.reporteFacturas.export(list, param, Type.PDF);
		this.renderer.render(buffer, FileRenderer.ContentType.PDF,
				"reporteFacturas.pdf");
	}
	
	private boolean disabledFechas =false;
	
	
	public boolean isDisabledFechas() {
	
		return this.getEstado().compareTo("Pendientes") == 0;
	}

	public void setDisabledFechas(boolean disabledFechas) {
	
		this.disabledFechas = disabledFechas;
	}

	public void agregarMensaje(String mensaje) {

		this.facesContext.addMessage("suceso", new FacesMessage(mensaje));
	}

	public Date getHasta() {

		return hasta;
	}

	public void setHasta(Date hasta) {

		this.hasta = hasta;
	}

	public Date getDesde() {

		return desde;
	}

	public void setDesde(Date desde) {

		this.desde = desde;
	}

	public String getEstado() {

		return estado;
	}

	public void setEstado(String estado) {

		this.estado = estado;
	}

}
