package py.com.ait.gestion.domain;


import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rol_permiso database table.
 * 
 */
@Entity
@Table(name="rol_permiso")
public class RolPermiso  extends Base  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ROL_PERMISO_SEQ", sequenceName="rol_permiso_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROL_PERMISO_SEQ") 
	@Column(name="id")
	private Long rolPermisoId;

	//bi-directional many-to-one association to Permiso
    @ManyToOne
	@JoinColumn(name="permiso_id")
	private Permiso permiso;

	//bi-directional many-to-one association to Rol
    @ManyToOne
	@JoinColumn(name="rol_id")
	private Rol rol;

    public RolPermiso() {
    }
    

	public RolPermiso(Long rolPermisoId, Permiso permiso, Rol rol) {
		super();
		this.rolPermisoId = rolPermisoId;
		this.permiso = permiso;
		this.rol = rol;
	}


	public Long getRolPermisoId() {
		return this.rolPermisoId;
	}

	public void setRolPermisoId(Long rolPermisoId) {
		this.rolPermisoId = rolPermisoId;
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
	
}