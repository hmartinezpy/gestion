package py.com.ait.gestion.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//@Entity
//@Table(name="aud_navegacion")
public class AudNavegacion extends Base implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="aud_navegacion_seq", sequenceName="aud_navegacion_aud_navegacion_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="aud_navegacion_seq")
	@Column(name="aud_navegacion_id")
	private Long audNavegacionId;

	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	
	@Column(name="url")
	private String url;
	
	@Column(name="fecha")
	private Timestamp fecha;
	
	@Column(name="nro_sesion")
	private Long nroSesion;
	
	public AudNavegacion(){
		
	}

	public Long getAudNavegacionId() {
		return audNavegacionId;
	}

	public void setAudNavegacionId(Long audNavegacionId) {
		this.audNavegacionId = audNavegacionId;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public Long getNroSesion() {
		return nroSesion;
	}

	public void setNroSesion(Long nroSesion) {
		this.nroSesion = nroSesion;
	}
	
	
	
}
