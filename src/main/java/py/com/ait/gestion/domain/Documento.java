package py.com.ait.gestion.domain;import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * The persistent class for the documento database table.
 * 
 */
@Entity
@Table(name="documento")
public class Documento  extends Base implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="DOCUMENTO_SEQ", sequenceName="documento_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOCUMENTO_SEQ")
	@Column(name="id")
	private Long documentoId;
	
	@Column(name="filename")
	private String filename;
	
	@Column(name="filepath")
	private String filepath;
	
	@Column(name="filetype")
	private String filetype;
	
	@Column(name="file_extension")
	private String fileExtension;
	
	@Column(name="bloqueado")
	private String bloqueado;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="usuario_bloqueo")
	private Usuario usuarioBloqueo;
	
	@Column(name="fecha_bloqueo")
	private Date fechaBloqueo;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="usuario_desbloqueo")
	private Usuario usuarioDesbloqueo;
	
	@Column(name="fecha_desbloqueo")
	private Date fechaDesbloqueo;
	
	@Column(name="entidad")
	private String entidad;
	
	@Column(name="id_entidad")
	private Long idEntidad;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(optional=true)
	@JoinColumn(name="usuario_creacion", nullable=true)
	private Usuario usuarioCreacion;
	
	//bi-directional many-to-one association to Usuario
	@OneToMany(fetch=FetchType.LAZY, targetEntity=DocumentoRol.class, mappedBy="documento")
	private List<DocumentoRol> documentoRoles;

	@Column(name="fecha_ultimo_update")
	private Date fechaUltimoUpdate;

	public Documento() {
		super();
    }

	public Documento(Long documentoId, String filename,String filepath, String filetype, String fileExtension, String bloqueado, Date fechaBloqueo,Usuario usuarioBloqueo, Date fechaDesbloqueo,Usuario usuarioDesbloqueo, String entidad, Long idEntidad, Usuario usuarioCreacion ) {
		this.documentoId = documentoId;
		this.filename = filename;
		this.filepath = filepath;
		this.filetype = filetype;
		this.fileExtension = fileExtension;
		this.bloqueado = bloqueado;
		this.fechaBloqueo = fechaBloqueo;
		this.usuarioBloqueo = usuarioBloqueo;
		this.fechaDesbloqueo = fechaDesbloqueo;
		this.usuarioDesbloqueo = usuarioDesbloqueo;
		this.entidad = entidad;
		this.idEntidad = idEntidad;
		this.usuarioCreacion = usuarioCreacion;
    }

	public Long getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(String bloqueado) {
		this.bloqueado = bloqueado;
	}

	public Usuario getUsuarioBloqueo() {
		return usuarioBloqueo;
	}

	public void setUsuarioBloqueo(Usuario usuarioBloqueo) {
		this.usuarioBloqueo = usuarioBloqueo;
	}

	public Date getFechaBloqueo() {
		return fechaBloqueo;
	}

	public void setFechaBloqueo(Date fechaBloqueo) {
		this.fechaBloqueo = fechaBloqueo;
	}

	public Usuario getUsuarioDesbloqueo() {
		return usuarioDesbloqueo;
	}

	public void setUsuarioDesbloqueo(Usuario usuarioDesbloqueo) {
		this.usuarioDesbloqueo = usuarioDesbloqueo;
	}

	public Date getFechaDesbloqueo() {
		return fechaDesbloqueo;
	}

	public void setFechaDesbloqueo(Date fechaDesbloqueo) {
		this.fechaDesbloqueo = fechaDesbloqueo;
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
 
	public Usuario getUsuarioCreacion() {
		
		return this.usuarioCreacion;
	}
	
	public void setUsuarioCreacion(Usuario usuarioCreacion) {
		
		this.usuarioCreacion = usuarioCreacion;
	}

	public List<DocumentoRol> getDocumentoRoles() {
		return documentoRoles;
	}

	public void setDocumentoRoles(List<DocumentoRol> documentoRoles) {
		this.documentoRoles = documentoRoles;
	}
	
	public Date getFechaUltimoUpdate() {
		return fechaUltimoUpdate;
	}

	public void setFechaUltimoUpdate(Date fechaUltimoUpdate) {
		this.fechaUltimoUpdate = fechaUltimoUpdate;
	}
}