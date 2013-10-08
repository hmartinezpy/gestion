package py.com.ait.gestion.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the actividad database table.
 * 
 */
@Entity
@Table(name="actividad")
public class Actividad  extends Base implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="ACTIVIDAD_SEQ", sequenceName="actividad_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACTIVIDAD_SEQ")
	@Column(name="id")
	private Long actividadId;
	
	@Column(name="nro_actividad")
	private String nroActividad;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="cronograma_detalle")
	private CronogramaDetalle cronogramaDetalle;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="responsable")
	private Usuario responsable;
	
	@Column(name="fecha_creacion")
	private Date fechaCreacion;
	
	@Column(name="fecha_inicio_previsto")
	private Date fechaInicioPrevisto;
	
	@Column(name="fecha_inicio_reprogramado")
	private Date fechaInicioReprogramado;
	
	@Column(name="motivo_reprogramacion_inicio")
	private String motivoReprogramacionInicio;
		
	@Column(name="fecha_fin_prevista")
	private Date fechaFinPrevista;
	
	@Column(name="fecha_fin_reprogramada")
	private Date fechaFinReprogramada;
	
	@Column(name="motivo_reprogramacion")
	private String motivoReprogramacion;
	
	@Column(name="fecha_devuelta")
	private Date fechaDevuelta;
		
	@Column(name="fecha_resuelta")
	private Date fechaResuelta;
	
	@Column(name="fecha_cancelacion")
	private Date fechaCancelacion;
	
	@Column(name="pregunta")
	private String pregunta;
	
	@Column(name="respuesta")
	private String respuesta;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="checklist_completo")
	private String checklistCompleto;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="actividad_anterior")
	private Actividad actividadAnterior;	
	
	@OneToMany(mappedBy="actividadAnterior")
    private List<Actividad> actividadAA;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="alerta")
	private TipoAlarma alerta;	
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="alarma")
	private TipoAlarma alarma;	
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="super_tarea")
	private Actividad superTarea;
	
	@OneToMany(mappedBy="superTarea")
    private List<Actividad> actividadST;

	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="proceso")
	private Proceso master;
	
	@Column(name="fecha_cobro")
	private Date fechaCobro;
	
	@Column(name="nro_factura")
	private String nroFactura;
	
	@Column(name="cheque_nro")
	private String chequeNro;
	
	@Column(name="cheque_banco")
	private String chequeBanco;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="actividad")
    private List<ActividadChecklistDetalle> checklistDetalle;

	@Transient
	private boolean tieneChecklist;
	
	public Actividad() {
		super();
    }


	public List<Actividad> getActividadAA() {
		return actividadAA;
	}


	public void setActividadAA(List<Actividad> actividadAA) {
		this.actividadAA = actividadAA;
	}


	public List<Actividad> getActividadST() {
		return actividadST;
	}


	public void setActividadST(List<Actividad> actividadST) {
		this.actividadST = actividadST;
	}


	public Long getActividadId() {
		return actividadId;
	}


	public void setActividadId(Long actividadId) {
		this.actividadId = actividadId;
	}


	public String getNroActividad() {
		return nroActividad;
	}


	public void setNroActividad(String nroActividad) {
		this.nroActividad = nroActividad;
	}


	public CronogramaDetalle getCronogramaDetalle() {
		return cronogramaDetalle;
	}


	public void setCronogramaDetalle(CronogramaDetalle cronogramaDetalle) {
		this.cronogramaDetalle = cronogramaDetalle;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Usuario getResponsable() {
		return responsable;
	}


	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}


	public Date getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public Date getFechaInicioPrevisto() {
		return fechaInicioPrevisto;
	}


	public void setFechaInicioPrevisto(Date fechaInicioPrevisto) {
		this.fechaInicioPrevisto = fechaInicioPrevisto;
	}


	public Date getFechaInicioReprogramado() {
		return fechaInicioReprogramado;
	}


	public void setFechaInicioReprogramado(Date fechaInicioReprogramado) {
		this.fechaInicioReprogramado = fechaInicioReprogramado;
	}


	public String getMotivoReprogramacionInicio() {
		return motivoReprogramacionInicio;
	}


	public void setMotivoReprogramacionInicio(String motivoReprogramacionInicio) {
		this.motivoReprogramacionInicio = motivoReprogramacionInicio;
	}


	public Date getFechaFinPrevista() {
		return fechaFinPrevista;
	}


	public void setFechaFinPrevista(Date fechaFinPrevista) {
		this.fechaFinPrevista = fechaFinPrevista;
	}


	public Date getFechaFinReprogramada() {
		return fechaFinReprogramada;
	}


	public void setFechaFinReprogramada(Date fechaFinReprogramada) {
		this.fechaFinReprogramada = fechaFinReprogramada;
	}


	public String getMotivoReprogramacion() {
		return motivoReprogramacion;
	}


	public void setMotivoReprogramacion(String motivoReprogramacion) {
		this.motivoReprogramacion = motivoReprogramacion;
	}


	public Date getFechaDevuelta() {
		return fechaDevuelta;
	}


	public void setFechaDevuelta(Date fechaDevuelta) {
		this.fechaDevuelta = fechaDevuelta;
	}


	public Date getFechaResuelta() {
		return fechaResuelta;
	}


	public void setFechaResuelta(Date fechaResuelta) {
		this.fechaResuelta = fechaResuelta;
	}


	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}


	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}


	public String getPregunta() {
		return pregunta;
	}


	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}


	public String getRespuesta() {
		return respuesta;
	}


	public void setRespuesta(String respuesta) {
		if(respuesta != null && respuesta.equals(""))
			this.respuesta = null;
		else
			this.respuesta = respuesta;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		if(estado != null && estado.equals(""))
			this.estado = null;
		else
			this.estado = estado;
	}


	public String getChecklistCompleto() {
		return checklistCompleto;
	}


	public void setChecklistCompleto(String checklistCompleto) {
		this.checklistCompleto = checklistCompleto;
	}


	public Actividad getActividadAnterior() {
		return actividadAnterior;
	}


	public void setActividadAnterior(Actividad actividadAnterior) {
		this.actividadAnterior = actividadAnterior;
	}


	public TipoAlarma getAlerta() {
		return alerta;
	}


	public void setAlerta(TipoAlarma alerta) {
		this.alerta = alerta;
	}


	public TipoAlarma getAlarma() {
		return alarma;
	}


	public void setAlarma(TipoAlarma alarma) {
		this.alarma = alarma;
	}


	public Actividad getSuperTarea() {
		return superTarea;
	}


	public void setSuperTarea(Actividad superTarea) {
		this.superTarea = superTarea;
	}


	public Proceso getMaster() {
		return master;
	}


	public void setMaster(Proceso master) {
		this.master = master;
	}


	public Date getFechaCobro() {
		return fechaCobro;
	}


	public void setFechaCobro(Date fechaCobro) {
		this.fechaCobro = fechaCobro;
	}


	public String getNroFactura() {
		return nroFactura;
	}


	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}


	public String getChequeNro() {
		return chequeNro;
	}


	public void setChequeNro(String chequeNro) {
		this.chequeNro = chequeNro;
	}


	public String getChequeBanco() {
		return chequeBanco;
	}


	public void setChequeBanco(String chequeBanco) {
		this.chequeBanco = chequeBanco;
	}


	public List<ActividadChecklistDetalle> getChecklistDetalle() {
		return checklistDetalle;
	}


	public void setChecklistDetalle(List<ActividadChecklistDetalle> checklistDetalle) {
		this.checklistDetalle = checklistDetalle;
	}


	public boolean isTieneChecklist() {
		boolean aRet=false;
		if (this.checklistDetalle != null){
			if (this.checklistDetalle.size()>0){
				aRet=true;
			}
		}
		return aRet;
	}


	public void setTieneChecklist(boolean tieneChecklist) {
		this.tieneChecklist = tieneChecklist;
	}
	
	
   	

}