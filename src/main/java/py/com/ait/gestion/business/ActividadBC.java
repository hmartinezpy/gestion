package py.com.ait.gestion.business;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.inject.Inject;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;
import py.com.ait.gestion.constant.Definiciones;
import py.com.ait.gestion.domain.Actividad;
import py.com.ait.gestion.domain.CronogramaDetalle;
import py.com.ait.gestion.domain.Proceso;
import py.com.ait.gestion.persistence.AudLogDAO;
import py.com.ait.gestion.persistence.ActividadDAO;
import py.com.ait.gestion.persistence.SesionLogDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@BusinessController
public class ActividadBC extends DelegateCrud<Actividad, Long, ActividadDAO>{

	private static final long serialVersionUID = 1L;

	@Inject 
	private ActividadDAO actividadDAO ;
	
	@Inject 
	private AudLogDAO audLogDAO;
	
	@Inject 
	private UsuarioDAO usuarioDAO;
	
	@Inject 
	SesionLogDAO sesionLogDAO;


	public List<Actividad> listar() {
		return actividadDAO.findAll();	
	}
	
	public Actividad recuperar(Long id) {
		return actividadDAO.load(id);	
	}
	
	/*****************Auditoria**************************/			
	@Transactional
	public void registrar(Actividad actividad) {
		
		super.insert(actividad);
		
		try {
			audLogDAO.log(null, actividad,usuarioDAO.getUsuarioActual(),
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Insert,actividad.getActividadId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void editar(Actividad actividad) {
		
		Actividad viejo = this.recuperar(actividad.getActividadId());
		super.update(actividad);
		try {
			audLogDAO.log(viejo, actividad,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Update,actividad.getActividadId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//actividadProductivaDAO.update(actividadProductiva);	
	}
	
	
	@Transactional
	public void eliminar(Long id) {
		
		Actividad actividad = this.recuperar(id);
		super.delete(id);
		
		try {
			audLogDAO.log(actividad, null,usuarioDAO.getUsuarioActual(), 
					sesionLogDAO.ObtenerIp(usuarioDAO.getUsuarioActual()), 
					Definiciones.Operacion.Delete,actividad.getActividadId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void insertActividadFromCronogramaDetalle(CronogramaDetalle cd, 
							Long actividadAnterior, Proceso proceso) {
		
		Actividad actividad = new Actividad();
		actividad.setMaster(proceso);
		actividad.setNroActividad(cd.getNroOrden()+"/"); //actividadBC.getNroActividad(); //tomar de hector
		actividad.setCronogramaDetalle(cd);
		actividad.setDescripcion(cd.getTarea());
		Calendar cal = new GregorianCalendar();
		actividad.setFechaCreacion(cal.getTime());
		actividad.setFechaInicioPrevisto(cal.getTime());
		cal.add(Calendar.DATE, cd.getDuracionTarea().intValue());
		actividad.setFechaFinPrevista(cal.getTime());
		if(cd.getPregunta() != null)
			actividad.setPregunta(cd.getPregunta().getDescripcion());	
		actividad.setEstado(Definiciones.EstadoActividad.Nueva);
		Actividad actAnterior = null;
		if(actividadAnterior != null) {
			actAnterior = new Actividad();
			actAnterior.setActividadId(actividadAnterior);
		}
		actividad.setActividadAnterior(actAnterior);
		actividad.setAlerta(cd.getAlerta());
		actividad.setAlarma(cd.getAlarma());
		
		insert(actividad);
	}

	public Long getMaxId() {
		
		return actividadDAO.getMaxId();
	}
}
	
	