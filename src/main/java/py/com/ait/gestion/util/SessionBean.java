package py.com.ait.gestion.util;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;


@SessionScoped
public class SessionBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long procesoId;

	public Long getProcesoId() {
		return procesoId;
	}

	public void setProcesoId(Long procesoId) {
		this.procesoId = procesoId;
	}
}
