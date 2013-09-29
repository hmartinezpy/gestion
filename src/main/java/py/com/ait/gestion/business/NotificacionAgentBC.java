package py.com.ait.gestion.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.weld.Container;
import org.jboss.weld.context.bound.BoundRequestContext;
import org.slf4j.Logger;
import org.ticpy.tekoporu.annotation.Startup;
import org.ticpy.tekoporu.scheduler.ISchedulerAction;
import org.ticpy.tekoporu.scheduler.Scheduler;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;

import py.com.ait.gestion.domain.Notificacion;
import py.com.ait.gestion.persistence.NotificacionDAO;
import py.com.ait.gestion.util.MailUtil;

@Scheduler(expression = "00:00:00 EVERY_HOUR 3")
@BusinessController
public class NotificacionAgentBC extends DelegateCrud<Notificacion, Long, NotificacionDAO> implements ISchedulerAction {

	private static final long serialVersionUID = 1L;
	private static NotificacionAgentBC instance = null;
	private BoundRequestContext context;
	private Map<String, Object> request;	
	
	@Inject
	private Logger logger;
	
	@Inject
	private NotificacionDAO notificacionDAO;	
	
	@Inject
	private MailUtil mailUtil;
	
	@Startup
	public void onInit() {
		
		instance = this;		
	}
	
	private void initContext() {
	 
		BoundRequestContext context = Container.instance().deploymentManager().instance().select(BoundRequestContext.class).get();
		Map<String, Object> request = new HashMap<String, Object>();
		context.associate(request);
		context.activate();
	}
	 
	private void closeContext() {
		
		if(context != null && context.isActive()) {
			context.deactivate();
			context.dissociate(request);
		}
	}
	
	private void run() {
		
		logger.info(">>>>> Perform NotificacionBC.run()...");		
		
		//check notificaciones nuevas
		List<Notificacion> list = new ArrayList<Notificacion>();
		list.addAll(notificacionDAO.getAlertasInicio());
		list.addAll(notificacionDAO.getAlertasReprogramacionProcesos());
		list.addAll(notificacionDAO.getAlertasActividades());
		list.addAll(notificacionDAO.getAlarmasActividades());
		
		//insertar notificaciones nuevas y enviar mails
		for(Notificacion notificacion : list) {
			
			notificacionDAO.insert(notificacion);
			
			if(notificacion.getUsuario().getEmail() != null
				&& !notificacion.getUsuario().getEmail().trim().equals("")) {
			
				try {
					mailUtil.sendMail(notificacion.getUsuario().getEmail(), 
							notificacion.getTitulo(), notificacion.getDescripcion());
				} catch(Exception ex) {
					
					logger.error(">>>>>Error al enviar mail de notificacion, Mail: " + 
									notificacion.getUsuario().getEmail() + ", NotificacionID: " + 
									notificacion.getNotificacionId());
					logger.error(">>>>>Exception Message: " + ex.getMessage());
				}
			}
		}
		
		logger.info(">>>>> End NotificacionBC.run()");
	}

	@Override
	public void execute() {
				
		NotificacionAgentBC notificacionAgentBC = instance;
		if(notificacionAgentBC != null) {
			
			notificacionAgentBC.initContext();
			notificacionAgentBC.run();
			notificacionAgentBC.closeContext();
		}
	}

	
}
	
	