package py.com.ait.gestion.domain;
import java.io.Serializable;
import javax.persistence.*;


import java.util.List;


/**
 * The persistent class for the permiso database table.
 * 
 */
@Entity
@Table(name="checklist_detalle")
public class ChecklistDetalle  extends Base  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHECKLISTDETALLE_SEQ", sequenceName="checklist_detalle_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHECKLISTDETALLE_SEQ") 	
	@Column(name="id")
	private Long checklistDetalleId;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="nro_orden")
	private Long nroOrden;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="checklist")
	private Checklist master;
	
	
	public ChecklistDetalle() {
    	super();
    }


	public Long getChecklistDetalleId() {
		return checklistDetalleId;
	}


	public void setChecklistDetalleId(Long checklistDetalleId) {
		this.checklistDetalleId = checklistDetalleId;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Long getNroOrden() {
		return nroOrden;
	}


	public void setNroOrden(Long nroOrden) {
		this.nroOrden = nroOrden;
	}


	public Checklist getMaster() {
		return master;
	}


	public void setMaster(Checklist master) {
		this.master = master;
	}
	
	

	
}
