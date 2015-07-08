package py.com.ait.gestion.domain;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name="rol")
public class Rol extends Base implements Serializable  {

	private static final long serialVersionUID = 2970302888176488906L;

	@Id	
	@SequenceGenerator(name="ROL_SEQ", sequenceName="rol_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROL_SEQ") 
	@Column(name="id")
	private Long rolId;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@OneToMany(mappedBy="rolResponsable")
	private List<CronogramaDetalle> cronogramaDetalles;
	

	public Rol () {
		super();
	}
	
	public Rol(Long rolId, String descripcion) {
		super();
		this.rolId = rolId;
		this.descripcion = descripcion;
	}

	public Rol (String descripcion) {
		this.descripcion=descripcion;
	}
	
	
	public Long getRolId() {
		return rolId;
	}

	public void setRolId(Long rolId) {
		this.rolId = rolId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public List<CronogramaDetalle> getCronogramaDetalles() {
		
		return cronogramaDetalles;
	}

	public void setCronogramaDetalles(List<CronogramaDetalle> cronogramaDetalles) {	
		this.cronogramaDetalles = cronogramaDetalles;
	}
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (this.rolId != null ? this.rolId.hashCode() : 0);
        return hash;
    }
	
	@Override
	public boolean equals(Object other) {

		boolean comparacion = false;
		if (other != null && other instanceof Rol) {

			if (this.rolId.equals(((Rol) other).getRolId())) {
				comparacion = true;
			}
		}
		return comparacion;
	}
}