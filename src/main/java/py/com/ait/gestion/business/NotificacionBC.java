package py.com.ait.gestion.business;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;

import py.com.ait.gestion.domain.Notificacion;
import py.com.ait.gestion.persistence.NotificacionDAO;

@BusinessController
public class NotificacionBC extends DelegateCrud<Notificacion, Long, NotificacionDAO> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Logger logger;
	
	@Inject
	private NotificacionDAO notificacionDAO;
	
	public List<Notificacion> getNotificaciones(String usuarioNotificaciones, String periodoCorte) {
		
		return notificacionDAO.getNotificaciones(usuarioNotificaciones, periodoCorte);
	}
	
}
	
	