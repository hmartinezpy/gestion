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



@Entity
@Table(name="checklist")
public class Checklist extends Base implements Serializable  {

	private static final long serialVersionUID = 2970302888176488906L;

	@Id	
	@SequenceGenerator(name="CHECKLIST_SEQ", sequenceName="checklist_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHECKLIST_SEQ") 
	@Column(name="id")
	private Long checklistId;
	
	@Column(name="descripcion")
	private String descripcion;


	@OneToMany(mappedBy="master",cascade=CascadeType.ALL)
	private List<ChecklistDetalle> checklistDetalles;

	public Checklist () {
		super();
	}
	
	public Checklist(Long checklistId, String descripcion) {
		this.checklistId = checklistId;
		this.descripcion = descripcion;
	}

	
	public Long getChecklistId() {
		return checklistId;
	}

	public void setChecklistId(Long checklistId) {
		this.checklistId = checklistId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	public List<ChecklistDetalle> getChecklistDetalles(){
		return this.checklistDetalles;
	}
	
	public void setChecklistDetalles(List<ChecklistDetalle> checklistDetalles){
		this.checklistDetalles = checklistDetalles;
	}
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (this.checklistId != null ? this.checklistId.hashCode() : 0);
        return hash;
    }
	
	@Override
	public boolean equals(Object other) {

		boolean comparacion = false;
		if (other != null && other instanceof Checklist) {

			if (this.checklistId == ((Checklist) other).getChecklistId()) {
				comparacion = true;
			}
		}
		return comparacion;
	}
	
}