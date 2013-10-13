package py.com.ait.gestion.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the estado database table.
 * 
 */
@Entity
@Table(name="notificaciones")
public class Notificacion  extends Base implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="NOTIFICACION_SEQ", sequenceName="notificaciones_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NOTIFICACION_SEQ")
	@Column(name="id")
	private Long notificacionId;
	
	@Column(name="entidad")
	private String entidad;
	
	@Column(name="id_entidad")
	private Long entidadId;
	
	@Column(name="mostrado")
	private String mostrado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="usuario")
	private Usuario usuario;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="responsable")
	private Usuario responsable;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_mostrado")
	private Date fechaMostrado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="titulo")
	private String titulo;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="tipo")
	private String tipo;
	
	public Notificacion() {
		super();
    }

	public Notificacion(String entidad, Long entidadId,
			String mostrado, Usuario usuario, Usuario responsable,
			Date fechaMostrado, Date fechaCreacion, String estado,
			String titulo, String descripcion) {
		super();
		this.entidad = entidad;
		this.entidadId = entidadId;
		this.mostrado = mostrado;
		this.usuario = usuario;
		this.responsable = responsable;
		this.fechaMostrado = fechaMostrado;
		this.fechaCreacion = fechaCreacion;
		this.estado = estado;
		this.titulo = titulo;
		this.descripcion = descripcion;
	}

	public Long getNotificacionId() {
		return notificacionId;
	}

	public void setNotificacionId(Long notificacionId) {
		this.notificacionId = notificacionId;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public Long getEntidadId() {
		return entidadId;
	}

	public void setEntidadId(Long entidadId) {
		this.entidadId = entidadId;
	}

	public String getMostrado() {
		return mostrado;
	}

	public void setMostrado(String mostrado) {
		this.mostrado = mostrado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	public Date getFechaMostrado() {
		return fechaMostrado;
	}

	public void setFechaMostrado(Date fechaMostrado) {
		this.fechaMostrado = fechaMostrado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}