package py.com.ait.gestion.domain;


import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the aud_log database table.
 * 
 */
@Entity
@Table(name="aud_log")
public class AudLog implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id	
	@SequenceGenerator(name="AUD_LOG_SEQ", sequenceName="aud_log_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUD_LOG_SEQ")
	@Column(name="id")
	private Long audLogId;
	
	@Column(name = "fecha")
	private Timestamp fecha;
	
	@Column(name = "ip")
	private String ip;

	@Column(name="registro_id")
	private Long registroId;

	@Column(name="valor_anterior")
	private String valorAnterior;

	@Column(name="valor_nuevo")
	private String valorNuevo;

	//bi-directional many-to-one association to AudTabla
    @ManyToOne
	@JoinColumn(name="aud_tabla_id")
	private AudTabla audTabla;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;

    public AudLog() {
    }

	public Long getAudLogId() {
		return this.audLogId;
	}

	public void setAudLogId(Long audLogId) {
		this.audLogId = audLogId;
		
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getRegistroId() {
		return this.registroId;
	}

	public void setRegistroId(Long registroId) {
		this.registroId = registroId;
	}

	public String getValorAnterior() {
		return this.valorAnterior;
	}

	public void setValorAnterior(String valorAnterior) {
		this.valorAnterior = valorAnterior;
	}

	public String getValorNuevo() {
		return this.valorNuevo;
	}

	public void setValorNuevo(String valorNuevo) {
		this.valorNuevo = valorNuevo;
	}

	public AudTabla getAudTabla() {
		return this.audTabla;
	}

	public void setAudTabla(AudTabla audTabla) {
		this.audTabla = audTabla;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}