package py.com.ait.gestion.domain;


import java.io.Serializable;
import javax.persistence.*;

import py.com.ait.gestion.domain.Cronograma;

import java.util.List;


/**
 * The persistent class for the permiso database table.
 * 
 */
@Entity
@Table(name="cronograma_detalle")
public class CronogramaDetalle  extends Base  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CRONOGRAMADETALLE_SEQ", sequenceName="cronograma_detalle_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CRONOGRAMADETALLE_SEQ") 	
	@Column(name="id")
	private Long cronogramaDetalleId;
	
	
	@Column(name="descripcion")
	private String tarea;
	
	@Column(name="nro_orden")
	private Long nroOrden;
		
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="rol")
	private Rol rolResponsable;
	
	
	@Column(name="alerta_inicio")
	private String alertaInicio;
	
	@Column(name="duracion")
	private Long duracionTarea;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="alarma")
	private TipoAlarma alarma;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="alerta")
	private TipoAlarma alerta;

	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="pregunta")
	private Pregunta pregunta;
	

	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="checklist")
	private Checklist checklist;

	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="tarea_si")
	private CronogramaDetalle respuestaSi;

    @OneToMany(mappedBy="respuestaSi")
    private List<CronogramaDetalle> cronogramaDetallesRS;

	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="tarea_no")
	private CronogramaDetalle respuestaNo;

    @OneToMany(mappedBy="respuestaNo")
    private List<CronogramaDetalle> cronogramaDetallesRN;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="tarea_siguiente")
	private CronogramaDetalle tareaSiguiente;

    @OneToMany(mappedBy="tareaSiguiente")
    private List<CronogramaDetalle> cronogramaDetallesTS;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="cronograma")
	private Cronograma master;
	
    public CronogramaDetalle() {
    	super();
    }

	public CronogramaDetalle(Long cronogramaDetalleId, String alertaInicio,
		Long duracionTarea, Long pregunta, Long nroOrden) {		
		this.cronogramaDetalleId = cronogramaDetalleId;
		this.alertaInicio = alertaInicio;
		this.duracionTarea = duracionTarea;
		this.nroOrden = nroOrden;
	}

	public Long getCronogramaDetalleId() {
		return this.cronogramaDetalleId;
	}

	public void setCronogramaDetalleId(Long cronogramaDetalleId) {
		this.cronogramaDetalleId = cronogramaDetalleId;
	}

	
	public Long getNroOrden() {
		return nroOrden;
	}

	public void setNroOrden(Long nroOrden) {
		this.nroOrden = nroOrden;
	}

	public String getTarea() {
		return this.tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;
	}
	public Rol getRolResponsable() {
		return this.rolResponsable;
	}

	public void setRolResponsable(Rol rolResponsable) {
		this.rolResponsable = rolResponsable;
	}
	public String getAlertaInicio() {
		return this.alertaInicio;
	}

	public void setAlertaInicio(String alertaInicio) {
		this.alertaInicio = alertaInicio;
	}
	public Long getDuracionTarea() {
		return this.duracionTarea;
	}

	public void setDuracionTarea(Long duracionTarea) {
		this.duracionTarea = duracionTarea;
	}
	public TipoAlarma getAlarma() {
		return this.alarma;
	}

	public void setAlarma(TipoAlarma alarma) {
		this.alarma = alarma;
	}
	
	public TipoAlarma getAlerta() {
		return this.alerta;
	}

	public void setAlerta(TipoAlarma alerta) {
		this.alerta = alerta;
	}
	public Pregunta getPregunta() {
		return this.pregunta;
	}

	public void setPregunta (Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	public CronogramaDetalle getRespuestaSi() {
		return this.respuestaSi;
	}

	public void setRespuestaSi (CronogramaDetalle respuestaSi) {
		this.respuestaSi = respuestaSi;
	}
	public CronogramaDetalle getRespuestaNo() {
		return this.respuestaNo;
	}

	public void setRespuestaNo(CronogramaDetalle respuestaNo) {
		this.respuestaNo = respuestaNo;
	}
	
	public CronogramaDetalle getTareaSiguiente() {
		return this.tareaSiguiente;
	}

	public void setTareaSiguiente (CronogramaDetalle tareaSiguiente) {
		this.tareaSiguiente = tareaSiguiente;
	}
	public Checklist getChecklist() {
		return this.checklist;
	}

	public void setChecklist (Checklist checklist) {
		this.checklist = checklist;
	}
	
	
	public Cronograma getCronogramaMaster(){
		return this.master;
	}
	
	public void setCronogramaMaster(Cronograma cronogramaMaster){
		this.master = cronogramaMaster;
	}
	
	public List<CronogramaDetalle> getCronogramaDetallesTS() {
		
		return cronogramaDetallesTS;
	}

	public void setCronogramaDetallesTS(List<CronogramaDetalle> cronogramaDetalles) {	
		this.cronogramaDetallesTS = cronogramaDetalles;
	}

	public List<CronogramaDetalle> getCronogramaDetallesRS() {
		
		return cronogramaDetallesRS;
	}

	public void setCronogramaDetallesRS(List<CronogramaDetalle> cronogramaDetalles) {	
		this.cronogramaDetallesRS = cronogramaDetalles;
	}
	
	public List<CronogramaDetalle> getCronogramaDetallesRN() {
		
		return cronogramaDetallesRN;
	}

	public void setCronogramaDetallesRN(List<CronogramaDetalle> cronogramaDetalles) {	
		this.cronogramaDetallesRN = cronogramaDetalles;
	}

}