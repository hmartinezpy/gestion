package py.com.ait.gestion.domain;


import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the aud_tabla database table.
 * 
 */
@Entity
@Table(name="aud_tabla")
public class AudTabla extends Base implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AUD_TABLA_SEQ", sequenceName="aud_tabla_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUD_TABLA_SEQ") 
	@Column(name="id")
	private Long audTablaId;
	
	@Column(name="nombre")
	private String nombre;

	//bi-directional many-to-one association to AudLog
	@OneToMany(mappedBy="audTabla")
	private List<AudLog> audLogs;

    public AudTabla() {
    }

	public Long getAudTablaId() {
		return this.audTablaId;
	}

	public void setAudTablaId(Long audTablaId) {
		this.audTablaId = audTablaId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<AudLog> getAudLogs() {
		return this.audLogs;
	}

	public void setAudLogs(List<AudLog> audLogs) {
		this.audLogs = audLogs;
	}
	
}