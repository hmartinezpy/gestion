package py.com.ait.gestion.domain;


import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the log_sesion database table.
 * 
 */
@Entity
@Table(name="log_sesion")
public class LogSesion extends Base  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOG_SESION_SEQ", sequenceName="log_sesion_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOG_SESION_SEQ") 
	@Column(name="id")
	private Long logSesionId;
	
	@Column(name="exito")
	private String exito;

	@Column(name="fecha_intento")
	private Timestamp fechaIntento;
	
	@Column(name="ip")
	private String ip;

	@Column(name="nro_sesion")
	private Long nroSesion;

	@Column(name="usuario")
	private String user;
	
	@Column(name="observacion")
	private String obsUsuario;
	
	
	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;

    public LogSesion() {
    }

    
	public Long getLogSesionId() {
		return this.logSesionId;
	}

	public void setLogSesionId(Long logSesionId) {
		this.logSesionId = logSesionId;
	}

	public String getExito() {
		return this.exito;
	}

	public void setExito(String exito) {
		this.exito = exito;
	}

	public Timestamp getFechaIntento() {
		return this.fechaIntento;
	}

	public void setFechaIntento(Timestamp fechaIntento) {
		this.fechaIntento = fechaIntento;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getNroSesion() {
		return this.nroSesion;
	}

	public void setNroSesion(Long nroSesion) {
		this.nroSesion = nroSesion;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setObsUsuario(String obsUsuario) {
		this.obsUsuario = obsUsuario;
	}

	public String getObsUsuario() {
		return obsUsuario;
	}
	
}