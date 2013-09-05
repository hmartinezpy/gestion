package py.com.ait.gestion.domain;


import java.io.Serializable;
import javax.persistence.*;



import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
public class Usuario  extends Base implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIO_SEQ", sequenceName="usuario_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_SEQ")
	@Column(name="id")
	private Long usuarioId;
	
	@Column(name="apellido")
	private String apellido;
	
	@Column(name="email")
	private String email;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="nombre")
	private String nombre;

	@Column(name="nro_sesion")
	private Long nroSesion;

	@Column(name="ultimo_login")
	private Date ultimoLogin;
	
	@Column(name="usuario")
	private String usuario;

	//bi-directional many-to-one association to AudLog
	@OneToMany(mappedBy="usuario")
	private List<AudLog> audLogs;

	//bi-directional many-to-one association to LogSesion
	@OneToMany(mappedBy="usuario")
	private List<LogSesion> logSesions;

	//bi-directional many-to-one association to UsuarioPassword
	@OneToMany(mappedBy="usuario")
	private List<UsuarioPassword> usuarioPasswords;

	//bi-directional many-to-one association to UsuarioRolPermiso
	@OneToMany(mappedBy="usuario")
	private List<UsuarioRolPermiso> usuarioRolPermisos;
	
	/*@OneToMany(mappedBy="personaContacto")
	private List<Cliente> clientes;*/
	
    public Usuario() {
    }
   
	public Usuario(Long usuarioId, String apellido, String email,
			String estado, String nombre, Long nroSesion,
			 String usuario, Date ultimoLogin) {
		
		this.usuarioId = usuarioId;
		this.apellido = apellido;
		this.email = email;
		this.estado = estado;
		this.nombre = nombre;
		this.nroSesion = nroSesion;
		this.usuario = usuario;
		this.ultimoLogin = ultimoLogin;
		
		
	}
	public Usuario(Long usuarioId, String apellido, String email,
			String estado, String nombre, Long nroSesion,
			 String usuario) {
		
		this.usuarioId = usuarioId;
		this.apellido = apellido;
		this.email = email;
		this.estado = estado;
		this.nombre = nombre;
		this.nroSesion = nroSesion;
		this.usuario = usuario;
		
	}

	public Long getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getNroSesion() {
		return this.nroSesion;
	}

	public void setNroSesion(Long nroSesion) {
		this.nroSesion = nroSesion;
	}

	public Date getUltimoLogin() {
		return this.ultimoLogin;
	}

	public void setUltimoLogin(Timestamp ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<AudLog> getAudLogs() {
		return this.audLogs;
	}

	public void setAudLogs(List<AudLog> audLogs) {
		this.audLogs = audLogs;
	}
	
	public List<LogSesion> getLogSesions() {
		return this.logSesions;
	}

	public void setLogSesions(List<LogSesion> logSesions) {
		this.logSesions = logSesions;
	}
	
	public List<UsuarioPassword> getUsuarioPasswords() {
		return this.usuarioPasswords;
	}

	public void setUsuarioPasswords(List<UsuarioPassword> usuarioPasswords) {
		this.usuarioPasswords = usuarioPasswords;
	}
	
	public List<UsuarioRolPermiso> getUsuarioRolPermisos() {
		return this.usuarioRolPermisos;
	}

	public void setUsuarioRolPermisos(List<UsuarioRolPermiso> usuarioRolPermisos) {
		this.usuarioRolPermisos = usuarioRolPermisos;
	}

	@Override
	public boolean equals(Object other) {
		
		boolean comparacion = false;
		if(other != null && other instanceof Usuario) {
			
			if(this.usuarioId == ((Usuario) other).getUsuarioId())
				comparacion = true;
		}
		return comparacion;
	}
	
	/*public List<Cliente> getClientes() {
		
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {	
		this.clientes = clientes;
	}*/
}