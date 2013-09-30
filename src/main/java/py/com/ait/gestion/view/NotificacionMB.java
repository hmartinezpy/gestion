

package py.com.ait.gestion.view;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.ticpy.tekoporu.stereotype.ViewController;

import py.com.ait.gestion.business.NotificacionBC;
import py.com.ait.gestion.domain.Notificacion;

@ViewController
public class NotificacionMB {
	
	@Inject 
	private FacesContext facesContext;
	
	@Inject
	private NotificacionBC notificacionBC;
	
	private String periodoCorte = "1S";
	
	private List<Notificacion> notificaciones;	
	
    public void agregarMensaje(String mensaje){
    	
    	facesContext.addMessage("suceso", new FacesMessage(mensaje));
    }    
	
    public String getPeriodoCorte() {
		return periodoCorte;
	}

	public void setPeriodoCorte(String periodoCorte) {
		this.periodoCorte = periodoCorte;
	}
	
	public List<Notificacion> getNotificaciones() {
		
		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public void updateNotificaciones() {
    	
    	String currentUser = FacesContext.getCurrentInstance().getExternalContext()
				.getUserPrincipal().getName();
    	
    	this.notificaciones = notificacionBC.getNotificaciones(currentUser, periodoCorte);
    	
    	for(Notificacion n : this.notificaciones) {
    		
    		if(n.getMostrado().equals("N")) {
    			
    			agregarMensaje(n.getTitulo() + "<br>Descripción: " + n.getDescripcion());
    			n.setMostrado("S");
    			n.setFechaMostrado(new Date());
    			notificacionBC.update(n);    			
    		}
    		n.setDescripcion(n.getDescripcion().replaceAll("<br>", ". "));
    	}
    	
    }
    
}
