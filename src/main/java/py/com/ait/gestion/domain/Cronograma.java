package py.com.ait.gestion.domain;


import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name="cronograma")
public class Cronograma extends Base implements Serializable  {

	private static final long serialVersionUID = 2970302888176488906L;

	@Id	
	@SequenceGenerator(name="CRONOGRAMA_SEQ", sequenceName="cronograma_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CRONOGRAMA_SEQ") 
	@Column(name="id")
	private Long cronogramaId;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="sigla")
	private String sigla;

	@Transient
	private String cantProcesos;

	@OneToMany(mappedBy="master",cascade=CascadeType.ALL)
	private List<CronogramaDetalle> cronogramaDetalles;

	public Cronograma () {
		super();
	}
	
	public Cronograma(Long cronogramaId, String nombre) {
		this.cronogramaId = cronogramaId;
		this.nombre = nombre;
	}

	
	public Long getCronogramaId() {
		return cronogramaId;
	}

	public void setCronogramaId(Long cronogramaId) {
		this.cronogramaId = cronogramaId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	public String getCantProcesos() {
		return cantProcesos;
	}

	public void setCantProcesos(String cantProcesos) {
		this.cantProcesos = cantProcesos;
	}

	public List<CronogramaDetalle> getCronogramaDetalles(){
		return this.cronogramaDetalles;
	}
	
	public void setCronogramaDetalles(List<CronogramaDetalle> cronogramaDetalles){
		this.cronogramaDetalles = cronogramaDetalles;
	}
	
	@Override
	public boolean equals(Object other) {
		
		boolean comparacion = false;
		if(other != null && other instanceof Cronograma) {
			
			if(this.cronogramaId == ((Cronograma) other).getCronogramaId())
				comparacion = true;
		}
		return comparacion;
	}
	
	@Override
	public String toString(){
		String toRet = getNombre();
		if (cantProcesos != null)
			toRet += " ("+cantProcesos+")";

		return toRet;
	}
}