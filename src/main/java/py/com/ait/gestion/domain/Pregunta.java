package py.com.ait.gestion.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the pregunta database table.
 * 
 */
@Entity
@Table(name="pregunta")
public class Pregunta  extends Base implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="PREGUNTA_SEQ", sequenceName="pregunta_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PREGUNTA_SEQ")
	@Column(name="id")
	private Long preguntaId;
	
	@Column(name="descripcion")
	private String descripcion;
	
	/*@OneToMany(mappedBy="pregunta")
	private List<CronogramaDetalle> cronogramaDetalles;*/
	
	
	public Pregunta() {
		super();
    }
   /*
	public Pregunta(Long preguntaId, String descripcion) {
		
		this.preguntaId = preguntaId;
		this.descripcion = descripcion;

	}*/
	
	public Long getPreguntaId() {
		return this.preguntaId;
	}
	
	public void setPreguntaId(Long preguntaId) {
		this.preguntaId = preguntaId;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion= descripcion;
	}
	/*
	public List<CronogramaDetalle> getCronogramaDetalles() {
		
		return cronogramaDetalles;
	}

	public void setCronogramaDetalles(List<CronogramaDetalle> cronogramaDetalles) {	
		this.cronogramaDetalles = cronogramaDetalles;
	}
	*/
}