package py.com.ait.gestion.domain;


import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usuario_rol_permiso database table.
 * 
 */
@Entity
@Table(name="usuario_rol_permiso")
public class UsuarioRolPermiso  extends Base  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIO_ROL_PERMISO_SEQ", sequenceName="usuario_rol_permiso_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_ROL_PERMISO_SEQ")
	@Column(name="id")
	private Long usuarioRolPermisoId;

	//bi-directional many-to-one association to Permiso
    @ManyToOne
	@JoinColumn(name="permiso_id")
	private Permiso permiso;

	//bi-directional many-to-one association to Rol
    @ManyToOne
	@JoinColumn(name="rol_id")
	private Rol rol;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;

    public UsuarioRolPermiso() {
    }
    

	public UsuarioRolPermiso(Long usuarioRolPermisoId, Permiso permiso,
			Rol rol, Usuario usuario) {
		super();
		this.usuarioRolPermisoId = usuarioRolPermisoId;
		this.permiso = permiso;
		this.rol = rol;
		this.usuario = usuario;
	}


	public Long getUsuarioRolPermisoId() {
		return this.usuarioRolPermisoId;
	}

	public void setUsuarioRolPermisoId(Long usuarioRolPermisoId) {
		this.usuarioRolPermisoId = usuarioRolPermisoId;
	}

	public Permiso getPermiso() {
		return this.permiso;
	}

	public void setPermiso(Permiso permiso) {
		this.permiso = permiso;
	}
	
	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}