

package py.com.ait.gestion.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.event.RowEditEvent;
import org.ticpy.tekoporu.stereotype.ViewController;

import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.business.TipoAlarmaBC;
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.domain.TipoAlarma;

@ViewController
public class TipoAlarmaMB {
	
	@Inject 
	private FacesContext facesContext;
	
	@Inject
	private TipoAlarmaBC tipoAlarmaBC;
	
	@Inject
	private UsuarioBC usuarioBC;
	
	private Long idResponsable1;
	private Long idResponsable2;
	private String descripcion;
	private String tipo;
	private Long dias;
	private Long horas;
	private List<Usuario> usuarios;
	private List<TipoAlarma> tipoAlarmas;
	private TipoAlarma tipoAlarmaSeleccionado;
	
	
	private Boolean checkAutom = true;
	
	
	public boolean getCheckAutom(){
		return checkAutom;
	}
	
	public void setCheckAutom(boolean check){
		this.checkAutom = check;
	}
	
	
	public Long getIdResponsable1() {
		return idResponsable1;
	}

	public void setIdResponsable1(Long idResponsable1) {
		this.idResponsable1 = idResponsable1;
	}
	
	public Long getIdResponsable2() {
		return idResponsable2;
	}

	public void setIdResponsable2(Long idResponsable2) {
		this.idResponsable2 = idResponsable2;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	private String getTipo() {
		return tipo;
	}
	
	private void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
	private Long getDias() {
		return dias;
	}
	
	private void setDias(Long dias) {
		this.dias = dias;
	}
	
	private Long getHoras() {
		return horas;
	}
	
	private void setHoras(Long horas) {
		this.horas = horas;
	}
	

	public List<Usuario> getUsuarios() {
		usuarios = usuarioBC.findAll();
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
    public List<TipoAlarma> getTipoAlarmas() {
    	tipoAlarmas = tipoAlarmaBC.listar();
		return tipoAlarmas;
	}

	public void setTipoAlarmas(List<TipoAlarma> tipoAlarmas) {
		this.tipoAlarmas = tipoAlarmas;
	}

	public TipoAlarma getTipoAlarmaSeleccionado() {
		return tipoAlarmaSeleccionado;
	}

	public void setTipoAlarmaSeleccionado(TipoAlarma tipoAlarmaSeleccionado) {
		this.tipoAlarmaSeleccionado = tipoAlarmaSeleccionado;
	}
	
	public void registrarTipoAlarma(){
		TipoAlarma tipoAlarma = new TipoAlarma();
		tipoAlarma.setDescripcion(getDescripcion());
		tipoAlarma.setTipo(getTipo());
		tipoAlarma.setDias(getDias());
		tipoAlarma.setHoras(getHoras());
		tipoAlarma.setResponsable1(getResponsable1());
		tipoAlarma.setResponsable2(getResponsable2());

		tipoAlarmaBC.registrar(tipoAlarma);
		agregarMensaje("TipoAlarma creado");
		this.limpiarCampos();
	}

	

	private void limpiarCampos() {
		this.setDescripcion("");
		this.setTipo("");
		this.setDias(null);
		this.setHoras(null);	
	}
	

	private Usuario getResponsable1() {
		
		return usuarioBC.load(getIdResponsable1());
	}
	
	private Usuario getResponsable2() {
		
		return usuarioBC.load(getIdResponsable2());
	}

	public void eliminar(ActionEvent actionEvent) {
		tipoAlarmaBC.eliminar(tipoAlarmaSeleccionado.getTipoAlarmaId());
		tipoAlarmaSeleccionado = new TipoAlarma();
        setTipoAlarmas(tipoAlarmaBC.listar());
        agregarMensaje("TipoAlarma eliminado");
    }
	
    public void onEdit(RowEditEvent event) {
    	TipoAlarma tipoAlarma = ((TipoAlarma) event.getObject());
    	tipoAlarma.setResponsable1(getResponsable1());
    	tipoAlarma.setResponsable2(getResponsable2());
    	tipoAlarmaBC.editar(tipoAlarma);
        agregarMensaje("Tipo Alarma Modificado");
    }
    
    public void agregarMensaje(String mensaje){
    	facesContext.addMessage("suceso", new FacesMessage(mensaje));
    }
	
	
	
}
