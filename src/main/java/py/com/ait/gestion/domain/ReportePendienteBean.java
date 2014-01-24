package py.com.ait.gestion.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class ReportePendienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String responsable;
	private String tipo_proceso;
	private String cliente;
	private String nro_proceso;
	private String proceso;
	private String nro_actividad;
	private String actividad;
	private String responsable_proceso;
	private String estado;
	private String tipo;
	private Date inicio_previsto;
	private Date fin_previsto;
	private Timestamp now;
	
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public String getTipo_proceso() {
		return tipo_proceso;
	}
	public void setTipo_proceso(String tipo_proceso) {
		this.tipo_proceso = tipo_proceso;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getNro_proceso() {
		return nro_proceso;
	}
	public void setNro_proceso(String nro_proceso) {
		this.nro_proceso = nro_proceso;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public String getNro_actividad() {
		return nro_actividad;
	}
	public void setNro_actividad(String nro_actividad) {
		this.nro_actividad = nro_actividad;
	}
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getResponsable_proceso() {
	
		return responsable_proceso;
	}
	
	public void setResponsable_proceso(String responsable_proceso) {
	
		this.responsable_proceso = responsable_proceso;
	}
	public Date getInicio_previsto() {
	
		return inicio_previsto;
	}
	
	public void setInicio_previsto(Date inicio_previsto) {
	
		this.inicio_previsto = inicio_previsto;
	}
	
	public Date getFin_previsto() {
	
		return fin_previsto;
	}
	public void setFin_previsto(Date fin_previsto) {
	
		this.fin_previsto = fin_previsto;
	}
	public Timestamp getNow() {
		return now;
	}
	public void setNow(Timestamp now) {
		this.now = now;
	}	
}
