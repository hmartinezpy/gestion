package py.com.ait.gestion.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


public class ReporteFacturasBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String cliente;
	private String proceso;
	private String nro_factura;
	private String cheque_nro;
	private String cheque_banco;
	private Date fecha_cobro;
	private Timestamp now;
	
	public String getCliente() {
	
		return cliente;
	}
	
	public void setCliente(String cliente) {
	
		this.cliente = cliente;
	}
	
	public String getProceso() {
	
		return proceso;
	}
	
	public void setProceso(String proceso) {
	
		this.proceso = proceso;
	}
	
	public String getNro_factura() {
	
		return nro_factura;
	}
	
	public void setNro_factura(String nro_factura) {
	
		this.nro_factura = nro_factura;
	}
	

	
	public String getCheque_nro() {
	
		return cheque_nro;
	}

	
	public void setCheque_nro(String cheque_nro) {
	
		this.cheque_nro = cheque_nro;
	}

	
	public String getCheque_banco() {
	
		return cheque_banco;
	}

	
	public void setCheque_banco(String cheque_banco) {
	
		this.cheque_banco = cheque_banco;
	}

	public Date getFecha_cobro() {
	
		return fecha_cobro;
	}
	
	public void setFecha_cobro(Date fecha_cobro) {
	
		this.fecha_cobro = fecha_cobro;
	}

	
	public Timestamp getNow() {
	
		return now;
	}

	
	public void setNow(Timestamp now) {
	
		this.now = now;
	}
	
	

}
