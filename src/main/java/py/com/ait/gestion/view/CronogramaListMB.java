package py.com.ait.gestion.view;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;
import py.com.ait.gestion.business.CronogramaBC;
import py.com.ait.gestion.business.CronogramaDetalleBC;
import py.com.ait.gestion.business.PreguntaBC;
import py.com.ait.gestion.business.RolBC;
import py.com.ait.gestion.business.ChecklistBC;
import py.com.ait.gestion.business.TipoAlarmaBC;
import py.com.ait.gestion.domain.Cronograma;
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.domain.Pregunta;
import py.com.ait.gestion.domain.Checklist;
import py.com.ait.gestion.domain.Rol;
import py.com.ait.gestion.domain.TipoAlarma;

@ViewController
@NextView("/pg/cronograma_edit.xhtml")
@PreviousView("/pg/cronograma_list.xhtml")
public class CronogramaListMB extends AbstractListPageBean<Cronograma, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private CronogramaBC cronogramaBC;

	private List<Cronograma> cronogramas;
	private List<CronogramaDetalle> detalles;
	private Cronograma cronogramaSeleccionado;

	private CronogramaDetalle detalleSeleccionado = new CronogramaDetalle();

	public Cronograma getCronogramaSeleccionado() {

		return cronogramaSeleccionado;
	}

	public void setCronogramaSeleccionado(Cronograma cronogramaSeleccionado) {

		this.cronogramaSeleccionado = cronogramaSeleccionado;
	}

	public CronogramaDetalle getDetalleSeleccionado() {

		return detalleSeleccionado;
	}

	public void setDetalleSeleccionado(CronogramaDetalle detalleSeleccionado) {

		this.detalleSeleccionado = detalleSeleccionado;
	}

	public List<Cronograma> getCronogramas() {

		cronogramas = cronogramaBC.listar();
		return cronogramas;
	}

	public void setCronogramas(List<Cronograma> cronogramas) {

		this.cronogramas = cronogramas;
	}

	public List<CronogramaDetalle> getDetalles() {

		return detalles;
	}

	public void setDetalles(List<CronogramaDetalle> detalles) {

		this.detalles = detalles;
	}

	public void eliminar(ActionEvent actionEvent) {

		try {
			cronogramaBC.eliminar(cronogramaSeleccionado.getCronogramaId());
			cronogramaSeleccionado = new Cronograma();
			setCronogramas(cronogramaBC.listar());
			agregarMensaje("Cronograma eliminado");
		} catch (Exception e) {
			this.logger.info("Fallo al tratar de eliminar cronograma");
			agregarMensajeError("No se puede eliminar el cronograma porque posee procesos asociados");

		}

	}

	public void agregarMensajeError(String mensaje) {

		this.facesContext.addMessage("error", new FacesMessage(
				FacesMessage.SEVERITY_ERROR, mensaje, null));
	}
	public void elegirCronograma() {

		// setDetalles(cronogramaSeleccionado.getCronogramaDetalles());
		setDetalles(cronogramaBC
				.getCronogramaDetallesByCronograma(cronogramaSeleccionado));
		String nombreCronograma = cronogramaSeleccionado.getNombre();
		agregarMensaje("Cronograma seleccionado: " + nombreCronograma);

	}

	@Inject
	private Logger logger;

	public void elegirDetalle() {

		this.detalleSeleccionado = cronogramaDetalleBC
				.load(this.detalleSeleccionado.getCronogramaDetalleId());
		CronogramaDetalle detalle = detalleSeleccionado;
		if (detalle.getPregunta() != null) {
			this.checkPregunta = true;
		} else {
			this.checkPregunta = false;
		}

		agregarMensaje("Detalle seleccionado: " + detalle.getTarea());

	}

	@Override
	protected List<Cronograma> handleResultList() {

		// TODO Auto-generated method stub
		return null;
	}

	public void agregarMensaje(String mensaje) {

		facesContext.addMessage("suceso", new FacesMessage(mensaje));
	}

	// Detalles
	@Inject
	private CronogramaDetalleBC cronogramaDetalleBC;

	@Inject
	private RolBC rolBC;

	@Inject
	private TipoAlarmaBC tipoAlarmaBC;

	@Inject
	private PreguntaBC preguntaBC;

	@Inject
	private ChecklistBC checklistBC;



	private List<TipoAlarma> alarmas;
	private List<TipoAlarma> alertas;
	private List<Pregunta> preguntas;
	private List<Checklist> checklists;
	private List<Rol> roles;

	private Boolean checkPregunta = false;



	public boolean getCheckPregunta() {

		return checkPregunta;
	}

	public void setCheckPregunta(boolean check) {

		this.checkPregunta = check;
		if (check == true){
			this.detalleSeleccionado.setTareaSiguiente(null);
		} else {
			this.detalleSeleccionado.setPregunta(null);
			this.detalleSeleccionado.setRespuestaNo(null);
			this.detalleSeleccionado.setRespuestaSi(null);
		}
	}


	public List<Rol> getRoles() {

		roles = rolBC.findAll();
		return roles;
	}

	public void setRoles(List<Rol> roles) {

		this.roles = roles;
	}


	public List<TipoAlarma> getAlarmas() {

		alarmas = tipoAlarmaBC.getAlarmas();
		return alarmas;
	}

	public void setAlarmas(List<TipoAlarma> alarmas) {

		this.alarmas = alarmas;
	}


	public List<TipoAlarma> getAlertas() {

		alertas = tipoAlarmaBC.getAlertas();
		return alertas;
	}

	public void setAlertas(List<TipoAlarma> tipoAlarmas) {

		this.alertas = tipoAlarmas;
	}




	public List<CronogramaDetalle> getTareasSgtes(){
		this.logger.info("\n\n\nGET TAREAS Siguietenes-------------");
		if(cronogramaSeleccionado != null){
			this.logger.info("!=NULL-------------");
			return cronogramaBC.getCronogramaDetallesByCronograma(cronogramaSeleccionado);
		}else{
			return null;
		}
	}



	public List<Pregunta> getPreguntas() {

		preguntas = preguntaBC.listar();
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {

		this.preguntas = preguntas;
	}

	public List<Checklist> getChecklists() {

		checklists = checklistBC.listar();
		return checklists;
	}

	public void setChecklists(List<Checklist> checklists) {

		this.checklists = checklists;
	}



	public void registrarCronogramaDetalle() {

		if (cronogramaSeleccionado == null) {
			agregarMensaje("ERROR: Cronograma NO seleccionado");
		} else {
			Cronograma cronogSelec = this.cronogramaBC
					.load(cronogramaSeleccionado.getCronogramaId());

	
			this.detalleNuevo.setCronogramaMaster(cronogSelec);
			this.cronogramaDetalleBC.registrar(this.detalleNuevo);
			this.logger.info("----- ID del cronogramaDetalleCreado:"
					+ detalleNuevo.getCronogramaDetalleId());
			if(detalleNuevo.getRolResponsable()!=null){
				detalleNuevo.setRolResponsable(rolBC.load(detalleNuevo.getRolResponsable().getRolId()));
			}
			if(detalleNuevo.getAlarma()!=null){
				detalleNuevo.setAlarma(tipoAlarmaBC.load(detalleNuevo.getAlarma().getTipoAlarmaId()));
			}
			if(detalleNuevo.getAlerta()!=null){
				detalleNuevo.setAlerta(tipoAlarmaBC.load(detalleNuevo.getAlerta().getTipoAlarmaId()));
			}
			if(detalleNuevo.getPregunta()!=null){
				detalleNuevo.setPregunta(preguntaBC.load(detalleNuevo.getPregunta().getPreguntaId()));
			}
			if(detalleNuevo.getChecklist()!=null){
				detalleNuevo.setChecklist(checklistBC.load(detalleNuevo.getChecklist().getChecklistId()));
			}
			if(detalleNuevo.getRespuestaSi()!=null){
				detalleNuevo.setRespuestaSi(cronogramaDetalleBC.load(detalleNuevo.getRespuestaSi().getCronogramaDetalleId()));
			}
			if(detalleNuevo.getRespuestaNo()!=null){
				detalleNuevo.setRespuestaNo(cronogramaDetalleBC.load(detalleNuevo.getRespuestaNo().getCronogramaDetalleId()));
			}
			getDetalles().add(detalleNuevo);
			
			cronogSelec.getCronogramaDetalles().add(
					this.cronogramaDetalleBC.load(this.detalleNuevo
							.getCronogramaDetalleId()));

			this.cronogramaBC.editar(cronogSelec);
			this.cronogramaSeleccionado = cronogSelec;

			
			this.detalleNuevo = new CronogramaDetalle();

			agregarMensaje("Detalle creado");

		}
	}

	public void eliminarDetalle(ActionEvent actionEvent) {

		try {
			cronogramaDetalleBC.eliminar(detalleSeleccionado
					.getCronogramaDetalleId());
			int index = detalles.indexOf(detalleSeleccionado);
			detalles.remove(index);
			agregarMensaje("Detalle eliminado");
		} catch (Exception e) {
			agregarMensajeError("No se puede eliminar el detalle porque posee Actividades asociadas");
		}

		// detalleSeleccionado = new CronogramaDetalle();

	}



	public void editarCronogramaDetalle() {

		if (detalleSeleccionado == null) {
			agregarMensaje("Detalle no seleccionado");
		} else {

			this.logger.info("---ID: "
					+ detalleSeleccionado.getCronogramaDetalleId());
			if (this.detalleSeleccionado.getTareaSiguiente() != null)
				this.logger.info("---Tarea siguiente: "
						+ this.detalleSeleccionado.getTareaSiguiente()
								.getTarea());
			if (this.detalleSeleccionado.getRespuestaSi() != null)
				this.logger.info("---Respuesta si: "
						+ this.detalleSeleccionado
								.getRespuestaSi().getTarea());
	
			cronogramaDetalleBC.editar(detalleSeleccionado);
			agregarMensaje("Detalle editado");
			setDetalles(cronogramaBC
					.getCronogramaDetallesByCronograma(cronogramaSeleccionado));
		}
	}

	private CronogramaDetalle detalleNuevo = new CronogramaDetalle();

	public CronogramaDetalle getDetalleNuevo() {

		return detalleNuevo;
	}

	public void setDetalleNuevo(CronogramaDetalle detalleNuevo) {

		this.detalleNuevo = detalleNuevo;
	}

}
