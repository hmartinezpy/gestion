package py.com.ait.gestion.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


public class ReporteProcesosBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String tipo_proceso;
	private String cliente;
	private String estado;
	private Date fecha_inicio;
	private Date fecha_fin;
	private Timestamp now;
	
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
	
	public String getEstado() {
	
		return estado;
	}
	
	public void setEstado(String estado) {
	
		this.estado = estado;
	}
	
	
	public Date getFecha_inicio() {
	
		return fecha_inicio;
	}

	
	public void setFecha_inicio(Date fecha_inicio) {
	
		this.fecha_inicio = fecha_inicio;
	}

	public Date getFecha_fin() {
	
		return fecha_fin;
	}
	
	public void setFecha_fin(Date fecha_fin) {
	
		this.fecha_fin = fecha_fin;
	}
	
	public Timestamp getNow() {
	
		return now;
	}
	
	public void setNow(Timestamp now) {
	
		this.now = now;
	}

	
}
