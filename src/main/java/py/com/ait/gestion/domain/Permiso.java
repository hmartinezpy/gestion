package py.com.ait.gestion.domain;


import java.io.Serializable;
import javax.persistence.*;

import py.com.ait.gestion.domain.RolPermiso;

import java.util.List;


/**
 * The persistent class for the permiso database table.
 * 
 */
@Entity
@Table(name="permiso")
public class Permiso  extends Base  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PERMISO_SEQ", sequenceName="permiso_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PERMISO_SEQ") 	
	@Column(name="id")
	private Long permisoId;
	
	@Column(name="descripcion")
	private String descripcion;

	//bi-directional many-to-one association to RolPermiso
	@OneToMany(mappedBy="permiso")
	private List<RolPermiso> rolPermisos;


	//bi-directional many-to-one association to UsuarioRolPermiso
	@OneToMany(mappedBy="permiso")
	private List<UsuarioRolPermiso> usuarioRolPermisos;

    public Permiso() {
    }

	public Permiso(Long permisoId, String descripcion) {
		super();
		this.permisoId = permisoId;
		this.descripcion = descripcion;
	}

	public Long getPermisoId() {
		return this.permisoId;
	}

	public void setPermisoId(Long permisoId) {
		this.permisoId = permisoId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<RolPermiso> getRolPermisos(){
		return this.rolPermisos;
	}
	
	public void setRolPermisos(List<RolPermiso> rolPermisos){
		this.rolPermisos = rolPermisos;
	}

	
	public List<UsuarioRolPermiso> getUsuarioRolPermisos() {
		return this.usuarioRolPermisos;
	}

	public void setUsuarioRolPermisos(List<UsuarioRolPermiso> usuarioRolPermisos) {
		this.usuarioRolPermisos = usuarioRolPermisos;
	}
	
}