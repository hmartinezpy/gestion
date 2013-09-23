package py.com.ait.gestion.domain;


import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usuario_rol_permiso database table.
 * 
 */
@Entity
@Table(name="documento_rol")
public class DocumentoRol  extends Base  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DOCUMENTO_ROL_SEQ", sequenceName="documento_rol_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOCUMENTO_ROL_SEQ")
	@Column(name="id")
	private Long documentoRolId;

	//bi-directional many-to-one association to Permiso
    @ManyToOne
	@JoinColumn(name="documento")
	private Documento documento;

	//bi-directional many-to-one association to Rol
    @ManyToOne
	@JoinColumn(name="rol")
	private Rol rol;

    public DocumentoRol() {
    }
    

	public DocumentoRol(Long documentoRolId, Documento documento,
			Rol rol) {
		super();
		this.documentoRolId = documentoRolId;
		this.documento = documento;
		this.rol = rol;
	}


	public Long getDocumentoRolId() {
		return documentoRolId;
	}


	public void setDocumentoRolId(Long documentoRolId) {
		this.documentoRolId = documentoRolId;
	}


	public Documento getDocumento() {
		return documento;
	}


	public void setDocumento(Documento documento) {
		this.documento = documento;
	}


	public Rol getRol() {
		return rol;
	}


	public void setRol(Rol rol) {
		this.rol = rol;
	}	
	
}