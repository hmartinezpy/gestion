

package py.com.ait.gestion.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.event.RowEditEvent;
import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;
import org.ticpy.tekoporu.template.AbstractListPageBean;

import py.com.ait.gestion.business.CronogramaBC;
import py.com.ait.gestion.business.CronogramaDetalleBC;
import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.business.TipoAlarmaBC;
import py.com.ait.gestion.business.PreguntaBC;
import py.com.ait.gestion.business.RolBC;
import py.com.ait.gestion.domain.Cronograma;
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.domain.Rol;
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.domain.TipoAlarma;
import py.com.ait.gestion.domain.Pregunta;


@ViewController
@NextView("/cronograma/cronogramaDetalle_new.xhtml")
@PreviousView("/cronograma/cronogramaDetalle_list.xhtml")
public class CronogramaDetalleMB extends AbstractEditPageBean<Cronograma, Long>{
	
	private static final long serialVersionUID = 1L;

	@Inject 
	private FacesContext facesContext;
	
	@Inject
	private CronogramaDetalleBC cronogramaDetalleBC;
	
	@Inject
	private PreguntaBC preguntaBC;
	
	@Inject
	private TipoAlarmaBC tipoAlarmaBC;
	
	@Inject
	private RolBC rolBC;
	
	@Inject
	private CronogramaBC cronogramaBC;

	private String nombreCronograma;
	private Long idCronograma;
	private Long idCronogramaDetalle;
	private Long idPregunta;
	private Long idTareaSi;
	private Long idTareaNo;
	private Long idTareaSgte;
	private Long idRolResponsable;
	private Long idTipoAlarma;
	private Long idAlerta;
	private String tarea;
	private String alertaInicio;
	private Long duracionTarea;
	private List<TipoAlarma> alarmas;
	private List<TipoAlarma> alertas;
	private List<CronogramaDetalle> tareas;
	private List<Pregunta> preguntas;
	private List<Rol> roles;
	private CronogramaDetalle cronogramaDetalleSeleccionado;
	private List<CronogramaDetalle> cronogramaDetalles;
	
	private Boolean checkPregunta = false;
	
	
	public boolean getCheckPregunta(){
		return checkPregunta;
	}
	
	public void setCheckPregunta(boolean check){
		this.checkPregunta = check;
	}
	
	
	public Long getIdAlerta() {
		return idAlerta;
	}

	public void setIdAlerta(Long IdAlerta) {
		this.idAlerta = IdAlerta;
	}
	
	public List<Pregunta> getPreguntas() {
		preguntas = preguntaBC.listar();
		return preguntas;
	}
	
	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}
	
	public List<TipoAlarma> getAlarmas() {
		alarmas = tipoAlarmaBC.getAlarmas();
		return alarmas;
	}
	
	public List<TipoAlarma> getAlertas() {
		alertas = tipoAlarmaBC.getAlertas();
		return alertas;
	}
	
	public void setAlarmas(List<TipoAlarma> tipoAlarmas) {
		this.alarmas = tipoAlarmas;
	}
	
	public void setAlertas(List<TipoAlarma> tipoAlarmas) {
		this.alertas = tipoAlarmas;
	}
	
	public List<CronogramaDetalle> getTareas() {
		Cronograma cronograma = getBean();
		tareas = cronograma.getCronogramaDetalles();
		return tareas;
	}
	
	public void setTareas(List<CronogramaDetalle> tareas) {
		this.tareas = tareas;
	}
	
	public List<Rol> getRoles() {
		roles = rolBC.findAll();
		return roles;
	}
	
	public void setRoless(List<Rol> roles) {
		this.roles = roles;
	}
	
	public String getNombreCronograma(){
		Cronograma cronograma = getBean();
		return cronograma.getNombre();
	}
	
	public Long getIdCronograma(){
		Cronograma cronograma = getBean();
		return cronograma.getCronogramaId();
	}
	
	
	public Long getIdCronogramaDetalle() {
		return idCronogramaDetalle;
	}

	public void setIdCronogramaDetalle(Long idCronogramaDetalle) {
		this.idCronogramaDetalle= idCronogramaDetalle;
	}
	
	public Long getIdPregunta() {
		return idPregunta;
	}

	public void setIdPregunta(Long IdPregunta) {
		this.idPregunta = IdPregunta;
	}
	
	public TipoAlarma getAlerta() {
		return tipoAlarmaBC.load(getIdAlerta());
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
	
	public Long getIdTareaSgte() {
		return idTareaSgte;
	}

	public void setIdTareaSgte(Long IdTareaSgte) {
		this.idTareaSgte = IdTareaSgte;
	}	
	
	public Long getIdRolResponsable() {
		return idRolResponsable;
	}
	
	public void setIdRolResponsable(Long IdRolResponsable) {
		this.idRolResponsable= IdRolResponsable;
	}
	
	public Long getIdTipoAlarma() {
		return idTipoAlarma;
	}

	public void setIdTipoAlarma(Long IdTipoAlarma) {
		this.idTipoAlarma= IdTipoAlarma;
	}
	
	public String getTarea() {
		return tarea;
	}
	
	public void setTarea(String tarea) {
		this.tarea = tarea;
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
	
	public TipoAlarma getTipoAlarma() {
		return tipoAlarmaBC.load(getIdTipoAlarma());
	}
	
	public CronogramaDetalle getCronogramaDetalleSeleccionado() {
		return cronogramaDetalleSeleccionado;
	}

	public void setCronogramaDetalleSeleccionado(CronogramaDetalle cronogramaDetalleSeleccionado) {
		this.cronogramaDetalleSeleccionado = cronogramaDetalleSeleccionado;
	}
	
	private Pregunta getPregunta() {
		
		return preguntaBC.load(getIdPregunta());
	}
	
	private CronogramaDetalle getTareaSgte() {
		
		return cronogramaDetalleBC.load(getIdTareaSgte());
	}
	
	private CronogramaDetalle getTareaSi() {
		
		return cronogramaDetalleBC.load(getIdTareaSi());
	}

	private CronogramaDetalle getTareaNo() {
		
		return cronogramaDetalleBC.load(getIdTareaNo());
	}
	
	private Rol getRolResponsable() {
		
		return rolBC.load(getIdRolResponsable());
	}
	
	public List<CronogramaDetalle> getCronogramaDetalles() {
		Cronograma cronograma = getBean();
		cronogramaDetalles = cronograma.getCronogramaDetalles();
		/*cronogramaDetalles = cronogramaDetalleBC.listar();*/
		return cronogramaDetalles;
	}

	public void setCronogramaDetalles() {
		Cronograma cronograma = getBean();
		this.cronogramaDetalles = cronograma.getCronogramaDetalles();;
	}

	public void registrarCronogramaDetalle(){
		CronogramaDetalle cronogramaDetalle = new CronogramaDetalle();
		cronogramaDetalle.setTarea(getTarea()) ;
		if (!this.checkPregunta)cronogramaDetalle.setPregunta(null) ;
		else cronogramaDetalle.setPregunta(getPregunta()) ;
		if (this.checkPregunta)cronogramaDetalle.setTareaSiguiente(null) ;
		else cronogramaDetalle.setTareaSiguiente(getTareaSgte()) ;
		if (!this.checkPregunta)cronogramaDetalle.setRespuestaSi(null);
		else cronogramaDetalle.setRespuestaSi(getTareaSi());
		if (!this.checkPregunta)cronogramaDetalle.setRespuestaNo(null);
		else cronogramaDetalle.setRespuestaNo(getTareaNo());
		cronogramaDetalle.setAlerta(getAlerta());
		cronogramaDetalle.setRolResponsable(getRolResponsable());
		cronogramaDetalle.setAlertaInicio(getAlertaInicio());
		cronogramaDetalle.setDuracionTarea(getDuracionTarea());
		cronogramaDetalle.setAlarma(getTipoAlarma());
		cronogramaDetalle.setCronogramaMaster(getBean());
		cronogramaDetalleBC.registrar(cronogramaDetalle);
		agregarMensaje("CronogramaDetalle creado");
		this.limpiarCampos();
	}
	
	private void limpiarCampos() {
		this.setTarea("");
		this.setDuracionTarea(null);
		
	}
	
	
	public void eliminar(ActionEvent actionEvent) {
		cronogramaDetalleBC.eliminar(cronogramaDetalleSeleccionado.getCronogramaDetalleId());
		int index = this.cronogramaDetalles.indexOf(cronogramaDetalleSeleccionado);
		this.cronogramaDetalles.remove(index);
		cronogramaDetalleSeleccionado = new CronogramaDetalle();
		/*Cronograma cronograma = getBean();
		this.cronogramaDetalles = cronograma.getCronogramaDetalles();*/
        agregarMensaje("CronogramaDetalle eliminado");
    }
	
	public void onEdit(RowEditEvent event) {
    	CronogramaDetalle cronogramaDetalle = ((CronogramaDetalle) event.getObject());
    	cronogramaDetalle.setPregunta(getPregunta()) ;
    	cronogramaDetalle.setRespuestaSi(getTareaSi());
    	cronogramaDetalle.setRespuestaNo(getTareaNo());
    	cronogramaDetalle.setTareaSiguiente(getTareaSgte());
    	cronogramaDetalle.setAlerta(getAlerta());
    	cronogramaDetalle.setAlarma(getTipoAlarma());
    	cronogramaDetalle.setRolResponsable(getRolResponsable());
    	cronogramaDetalleBC.editar(cronogramaDetalle);
        agregarMensaje("CronogramaDetalle Modificado");
    }

    public void agregarMensaje(String mensaje){
    	facesContext.addMessage("suceso", new FacesMessage(mensaje));
    }

	@Override
	public String delete() {
		// TODO Auto-generated method stub
		cronogramaDetalleBC.eliminar(cronogramaDetalleSeleccionado.getCronogramaDetalleId());
		//cronogramaDetalleSeleccionado = new CronogramaDetalle();
        //setCronogramaDetalles();
        return getPreviousView();
	}

	@Override
	public String insert() {
		/*// TODO Auto-generated method stub
		CronogramaDetalle cronogramaDetalle = new CronogramaDetalle();
		cronogramaDetalle.setTarea(getTarea()) ;
		cronogramaDetalle.setPregunta(getPregunta()) ;
		cronogramaDetalle.setTareaSiguiente(getTareaSgte()) ;
		cronogramaDetalle.setRespuestaSi(getTareaSi());
		cronogramaDetalle.setRespuestaNo(getTareaNo());
		cronogramaDetalle.setRolResponsable(getRolResponsable());
		cronogramaDetalle.setAlertaInicio(getAlertaInicio());
		cronogramaDetalle.setDuracionTarea(getDuracionTarea());
		cronogramaDetalle.setAlarma(getTipoAlarma());
		cronogramaDetalle.setCronogramaMaster(getBean());
		cronogramaDetalleBC.registrar(cronogramaDetalle);
		return getPreviousView();*/
		return null;
	}

	@Override
	public String update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleLoad() {
		setBean(this.cronogramaBC.load(getId()));
	}
	
	
	
}
