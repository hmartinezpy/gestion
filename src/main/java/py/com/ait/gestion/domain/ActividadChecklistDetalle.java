package py.com.ait.gestion.domain;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the permiso database table.
 * 
 */
@Entity
@Table(name="actividad_checlikst_detalle")
public class ActividadChecklistDetalle  extends Base  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACTIVIDADCHECKLISTDETALLE_SEQ", sequenceName="actividad_checlikst_detalle_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACTIVIDADCHECKLISTDETALLE_SEQ") 	
	@Column(name="id")
	private Long id;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="respuesta")
	private String respuesta;
	
	@Column(name="fecha_hora")
	private Date fechaHora;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="actividad")
	private Actividad actividad;
	
	
	public ActividadChecklistDetalle() {
    	super();
    }


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getRespuesta() {
		return respuesta;
	}


	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}


	public Date getFechaHora() {
		return fechaHora;
	}


	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}


	public Actividad getActividad() {
		return actividad;
	}


	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	
}
