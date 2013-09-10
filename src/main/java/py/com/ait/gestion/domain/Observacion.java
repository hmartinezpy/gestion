package py.com.ait.gestion.domain;import java.io.Serializable;

import java.util.Date;

import javax.persistence.*;
/**
 * The persistent class for the observacion database table.
 * 
 */
@Entity
@Table(name="observacion")
public class Observacion  extends Base implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="OBSERVACION_SEQ", sequenceName="observacion_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OBSERVACION_SEQ")
	@Column(name="id")
	private Long observacionId;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="usuario")
	private Usuario usuario;
	
	@Column(name="fecha_hora")
	private Date fechaHora;
	
	@Column(name="entidad")
	private String entidad;
	
	@Column(name="id_entidad")
	private Long idEntidad;
	
	
	public Observacion() {
		super();
    }

	public Observacion(Long observacionId, String descripcion, Date fechaHora, String entidad, Long idEntidad, Usuario usuario ) {
		this.observacionId = observacionId;
		this.descripcion = descripcion;
		this.fechaHora = fechaHora;
		this.entidad = entidad;
		this.idEntidad = idEntidad;
		this.usuario = usuario;
    }

	public Long getObservacionId() {
		return observacionId;
	}


	public void setObservacionId(Long observacionId) {
		this.observacionId = observacionId;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Date getFechaHora() {
		return fechaHora;
	}


	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}


	public String getEntidad() {
		return entidad;
	}


	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}


	public Long getIdEntidad() {
		return idEntidad;
	}


	public void setIdEntidad(Long idEntidad) {
		this.idEntidad = idEntidad;
	}
	
	
   	
	

}