package py.com.ait.gestion.domain;import java.io.Serializable;

import java.util.Date;

import javax.persistence.*;
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
	
	
	public Documento() {
		super();
    }

	public Documento(Long documentoId, String filename,String filepath,String filetype, String fileExtension, String bloqueado, Date fechaBloqueo,Date fechaDesbloqueo, String entidad, Long idEntidad ) {
		this.documentoId = documentoId;
		this.filename = filename;
		this.filepath = filepath;
		this.fileExtension = fileExtension;
		this.filetype = filetype;
		this.bloqueado = bloqueado;
		this.fechaBloqueo = fechaBloqueo;
		this.fechaDesbloqueo = fechaDesbloqueo;
		this.entidad = entidad;
		this.idEntidad = idEntidad;
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

	
   	
	

}