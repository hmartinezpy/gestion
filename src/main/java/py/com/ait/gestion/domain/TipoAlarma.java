package py.com.ait.gestion.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the tipo_alarma database table.
 * 
 */
@Entity
@Table(name="tipo_alarma")
public class TipoAlarma  extends Base implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="TIPO_ALARMA_SEQ", sequenceName="tipo_alarma_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPO_ALARMA_SEQ")
	@Column(name="id")
	private Long tipoAlarmaId;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="tipo")
	private String tipo;
	
	@Column(name="dias")
	private Long dias;
	
	@Column(name="horas")
	private Long horas;

	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="responsable1")
	private Usuario responsable1;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="responsable2")
	private Usuario responsable2;
	
	/*@OneToMany(mappedBy="alarma")
	private List<CronogramaDetalle> cronogramaDetallesAlarma;
	
	@OneToMany(mappedBy="alerta")
	private List<CronogramaDetalle> cronogramaDetallesAlerta;*/
	
	public TipoAlarma() {
		super();
    }
   
	public TipoAlarma(Long tipoAlarmaId, String descripcion, String tipo, Long dias, Long horas) {
		
		this.tipoAlarmaId = tipoAlarmaId;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.dias = dias;
		this.horas = horas;


	}
	
	public Long getTipoAlarmaId() {
		return this.tipoAlarmaId;
	}
	
	public void setTipoAlarmaId(Long tipoAlarmaId) {
		this.tipoAlarmaId = tipoAlarmaId;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion= descripcion;
	}
	
	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	public Long getDias() {
		return this.dias;
	}

	public void setDias(Long dias) {
		this.dias = dias;
	}
	
	public Long getHoras() {
		return this.horas;
	}

	public void setHoras(Long horas) {
		this.horas = horas;
	}
	
	
	public Usuario getResponsable2() {
		return this.responsable2;
	}

	public void setResponsable2(Usuario responsable2) {
		this.responsable2 = responsable2;
	}
	
	public Usuario getResponsable1() {
		return this.responsable1;
	}

	public void setResponsable1(Usuario responsable1) {
		this.responsable1 = responsable1;
	}
	/*
	public List<CronogramaDetalle> getCronogramaDetallesAlerta() {
		
		return cronogramaDetallesAlerta;
	}

	public void setCronogramaDetallesAlerta(List<CronogramaDetalle> cronogramaDetalles) {	
		this.cronogramaDetallesAlerta = cronogramaDetalles;
	}
	
	public List<CronogramaDetalle> getCronogramaDetallesAlarma() {
		
		return cronogramaDetallesAlarma;
	}

	public void setCronogramaDetallesAlarma(List<CronogramaDetalle> cronogramaDetalles) {	
		this.cronogramaDetallesAlarma = cronogramaDetalles;
	}*/
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (this.tipoAlarmaId != null ? this.tipoAlarmaId.hashCode() : 0);
        return hash;
    }
	
	@Override
	public boolean equals(Object other) {

		boolean comparacion = false;
		if (other != null && other instanceof TipoAlarma) {

			if (this.tipoAlarmaId == ((TipoAlarma) other).getTipoAlarmaId()) {
				comparacion = true;
			}
		}
		return comparacion;
	}
}