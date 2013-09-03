package py.com.ait.gestion.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

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
public class CronogramaListMB extends AbstractListPageBean<Cronograma,Long> {


	private static final long serialVersionUID = 1L;

	@Inject 
	private FacesContext facesContext;
	
	@Inject
	private CronogramaBC cronogramaBC;
	
	private List<Cronograma> cronogramas;
	private List<CronogramaDetalle> detalles;
	private Cronograma cronogramaSeleccionado;
	
	private CronogramaDetalle detalleSeleccionado;
	
	
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
		cronogramaBC.eliminar(cronogramaSeleccionado.getCronogramaId());
		cronogramaSeleccionado = new Cronograma();
        setCronogramas(cronogramaBC.listar());
        agregarMensaje("Cronograma eliminado");
    }
	
	public void elegirCronograma(){
		setDetalles(cronogramaSeleccionado.getCronogramaDetalles());
		String nombreCronograma = cronogramaSeleccionado.getNombre();
		agregarMensaje("Cronograma seleccionado: " + nombreCronograma);
		
	}
	
	public void elegirDetalle(){
		CronogramaDetalle detalle = detalleSeleccionado;
		if(detalle.getPregunta()!=null){
			this.checkPregunta = true;
		}else{
			this.checkPregunta = false;			
		}
		agregarMensaje("Detalle seleccionado: " + detalle.getTarea());
		
	}
	
	
	@Override
	protected List<Cronograma> handleResultList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void agregarMensaje(String mensaje){
	    	facesContext.addMessage("suceso", new FacesMessage(mensaje));
	}

	
	//Detalles	
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
	
	private String tarea;
	private Long idRolResponsable;
	private String alertaInicio;
	private Long duracionTarea;
	private Long nroOrden;	
	private Long idAlarma;
	private Long idAlerta;
	private Long idTareaSgte;
	private Long idPregunta;
	private Long idChecklist;
	private Long idTareaSi;
	private Long idTareaNo;	
	
	private List<TipoAlarma> alarmas;
	private List<TipoAlarma> alertas;
	private List<CronogramaDetalle> tareas;
	private List<Pregunta> preguntas;
	private List<Checklist> checklists;
	private List<Rol> roles;

	private Boolean checkPregunta = false;
	
	
	public Long getNroOrden() {
		return nroOrden;
	}


	public void setNroOrden(Long nroOrden) {
		this.nroOrden = nroOrden;
	}


	public boolean getCheckPregunta(){
		return checkPregunta;
	}
	
	public void setCheckPregunta(boolean check){
		this.checkPregunta = check;
	}
	
	public String getTarea() {
		return tarea;
	}
	
	public void setTarea(String tarea) {
		this.tarea = tarea;
	}
	
	public Long getIdRolResponsable() {
		return idRolResponsable;
	}
	
	public void setIdRolResponsable(Long IdRolResponsable) {
		this.idRolResponsable= IdRolResponsable;
	}
	
	public List<Rol> getRoles() {
		roles = rolBC.findAll();
		return roles;
	}
	
	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	
	private Rol getRolResponsable() {
		
		return rolBC.load(getIdRolResponsable());
	}
	
	public String getAlertaInicio() {
		return alertaInicio;
	}
	
	public void setAlertaInicio(String alertaInicio) {
		this.alertaInicio = alertaInicio;
	}
	
	public Long getDuracionTarea() {
		return duracionTarea;
	}
	
	public void setDuracionTarea(Long duracionTarea) {
		this.duracionTarea = duracionTarea;
	}
	
	public Long getIdAlarma() {
		return idAlarma;
	}

	public void setIdAlarma(Long IdAlarma) {
		this.idAlarma= IdAlarma;
	}

	public List<TipoAlarma> getAlarmas() {
		alarmas = tipoAlarmaBC.getAlarmas();
		return alarmas;
	}
	
	public void setAlarmas(List<TipoAlarma> alarmas) {
		this.alarmas = alarmas;
	}
	
	public TipoAlarma getAlarma() {
		return tipoAlarmaBC.load(getIdAlarma());
	}
	
	public Long getIdAlerta() {
		return idAlerta;
	}

	public void setIdAlerta(Long IdAlerta) {
		this.idAlerta = IdAlerta;
	}
	
	public List<TipoAlarma> getAlertas() {
		alertas = tipoAlarmaBC.getAlertas();
		return alertas;
	}	
		
	public void setAlertas(List<TipoAlarma> tipoAlarmas) {
		this.alertas = tipoAlarmas;
	}
	
	public TipoAlarma getAlerta() {
		return tipoAlarmaBC.load(getIdAlerta());
	}
	
	public Long getIdTareaSgte() {
		return idTareaSgte;
	}

	public void setIdTareaSgte(Long IdTareaSgte) {
		this.idTareaSgte = IdTareaSgte;
	}
	
	public List<CronogramaDetalle> getTareas() {
		if (cronogramaSeleccionado != null){
			Cronograma cronograma = cronogramaSeleccionado;
			tareas = cronograma.getCronogramaDetalles();
		}
		
		return tareas;
	}
	
	public void setTareas(List<CronogramaDetalle> tareas) {
		this.tareas = tareas;
	}
	
	private CronogramaDetalle getTareaSgte() {
		
		return cronogramaDetalleBC.load(getIdTareaSgte());
	}
	
	public Long getIdPregunta() {
		return idPregunta;
	}

	public void setIdPregunta(Long IdPregunta) {
		this.idPregunta = IdPregunta;
	}
	
	public Long getIdChecklist() {
		return idChecklist;
	}

	public void setIdChecklist(Long IdChecklist) {
		this.idChecklist = IdChecklist;
	}
	
	public Long getIdTareaSi() {
		return idTareaSi;
	}

	public void setIdTareaSi(Long IdTareaSi) {
		this.idTareaSi = IdTareaSi;
	}
	
	public Long getIdTareaNo() {
		return idTareaNo;
	}

	public void setIdTareaNo(Long IdTareaNo) {
		this.idTareaNo = IdTareaNo;
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
	
	private CronogramaDetalle getTareaSi() {
		
		return cronogramaDetalleBC.load(getIdTareaSi());
	}

	private CronogramaDetalle getTareaNo() {
		
		return cronogramaDetalleBC.load(getIdTareaNo());
	}
	
	private Pregunta getPregunta() {
		
		return preguntaBC.load(getIdPregunta());
	}
	
	private Checklist getChecklist() {
		
		return checklistBC.load(getIdChecklist());
	}

	public void registrarCronogramaDetalle(){
		if (cronogramaSeleccionado==null){
			agregarMensaje("ERROR: Cronograma NO seleccionado");
			limpiarCamposNuevo();
		}
		else{
			Cronograma cronogSelec = cronogramaSeleccionado;
			
			CronogramaDetalle cronogramaDetalle = new CronogramaDetalle();
			cronogramaDetalle.setTarea(getTarea()) ;
			cronogramaDetalle.setRolResponsable(getRolResponsable());
			cronogramaDetalle.setAlertaInicio(getAlertaInicio());
			cronogramaDetalle.setDuracionTarea(getDuracionTarea());
			cronogramaDetalle.setAlarma(getAlarma());
			cronogramaDetalle.setNroOrden(getNroOrden());
			cronogramaDetalle.setAlerta(getAlerta());
			cronogramaDetalle.setChecklist(getChecklist());
			if (!this.checkPregunta)cronogramaDetalle.setPregunta(null) ;
			else cronogramaDetalle.setPregunta(getPregunta()) ;
			if (this.checkPregunta)cronogramaDetalle.setTareaSiguiente(null) ;
			else cronogramaDetalle.setTareaSiguiente(getTareaSgte()) ;
			if (!this.checkPregunta)cronogramaDetalle.setRespuestaSi(null);
			else cronogramaDetalle.setRespuestaSi(getTareaSi());
			if (!this.checkPregunta)cronogramaDetalle.setRespuestaNo(null);
			else cronogramaDetalle.setRespuestaNo(getTareaNo());
						
			cronogramaDetalle.setCronogramaMaster((cronogSelec));
			cronogramaDetalleBC.registrar(cronogramaDetalle);
			detalles.add(cronogramaDetalle);
			agregarMensaje("Detalle creado");
			limpiarCamposNuevo();
		
		}
	}

	public void eliminarDetalle(ActionEvent actionEvent) {
		cronogramaDetalleBC.eliminar(detalleSeleccionado.getCronogramaDetalleId());
		int index = detalles.indexOf(detalleSeleccionado);
		detalles.remove(index);
		//detalleSeleccionado = new CronogramaDetalle();
		
        agregarMensaje("Detalle eliminado");
    }

	private void limpiarCamposNuevo() {
		this.setTarea("");
		this.setDuracionTarea(null);
		this.setNroOrden(null);		
		
	}
	
	public void editarCronogramaDetalle(){
		if (detalleSeleccionado==null){
			agregarMensaje("Detalle no seleccionado");
		}
		else{
			CronogramaDetalle detalle = detalleSeleccionado;
			detalle.setRolResponsable(getRolResponsable());
			detalle.setAlarma(getAlarma());
			detalle.setAlerta(getAlerta());
			detalle.setChecklist(getChecklist());
	
			if (!this.checkPregunta)detalle.setPregunta(null) ;
			else detalle.setPregunta(getPregunta()) ;
			if (this.checkPregunta)detalle.setTareaSiguiente(null) ;
			else detalle.setTareaSiguiente(getTareaSgte()) ;
			if (!this.checkPregunta)detalle.setRespuestaSi(null);
			else detalle.setRespuestaSi(getTareaSi());
			if (!this.checkPregunta)detalle.setRespuestaNo(null);
			else detalle.setRespuestaNo(getTareaNo());
			
			
			cronogramaDetalleBC.editar(detalle);
			//detalleSeleccionado = new CronogramaDetalle();
			agregarMensaje("Detalle editado");
		}
	}
	
}
