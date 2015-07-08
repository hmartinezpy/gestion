package py.com.ait.gestion.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@Table(name="cliente")
public class Cliente  extends Base implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="CLIENTE_SEQ", sequenceName="cliente_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CLIENTE_SEQ")
	@Column(name="id")
	private Long clienteId;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="direccion")
	private String direccion;
	
	@Column(name="telefono")
	private String telefono;
	
	@Column(name="persona_contacto")
	private String personaContacto;
	
	
	
	public Cliente() {
		super();
    }
   
	/*public Cliente( Long clienteId, String nombre, String direccion,
			String telefono, String personaContacto) {
		this.clienteId = clienteId;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.personaContacto = personaContacto;

	}*/
	
	public Long getClienteId() {
		return this.clienteId;
	}
	
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre= nombre;
	}
	
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	

	public String getPersonaContacto() {
		return this.personaContacto;
	}

	public void setPersonaContacto(String personaContacto) {
		this.personaContacto = personaContacto;
	}
	
	@Override
	public boolean equals(Object other) {
		
		boolean comparacion = false;
		if(other != null && other instanceof Cliente) {
			
			if(this.clienteId.equals(((Cliente) other).getClienteId()))
				comparacion = true;
		}
		return comparacion;
	}
	
}
