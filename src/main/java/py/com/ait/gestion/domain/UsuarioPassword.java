package py.com.ait.gestion.domain;


import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usuario_password database table.
 * 
 */
@Entity
@Table(name="usuario_password")
public class UsuarioPassword  extends Base  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIO_PASSWORD_SEQ", sequenceName="usuario_password_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_PASSWORD_SEQ")
	@Column(name="id")
	private Long usuarioPasswordId;
	@Column(name="password")
	private String password;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;

    public UsuarioPassword() {
    }

	public UsuarioPassword(Long usuarioPasswordId, 
			Usuario usuario, String password) {
		super();
		this.usuarioPasswordId = usuarioPasswordId;
		this.password = password;
		this.usuario = usuario;
	}

	public Long getUsuarioPasswordId() {
		return this.usuarioPasswordId;
	}

	public void setUsuarioPasswordId(Long usuarioPasswordId) {
		this.usuarioPasswordId = usuarioPasswordId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}