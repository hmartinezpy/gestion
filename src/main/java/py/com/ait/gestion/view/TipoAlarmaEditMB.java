package py.com.ait.gestion.view;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;

import py.com.ait.gestion.business.TipoAlarmaBC;
import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.domain.TipoAlarma;
import py.com.ait.gestion.domain.Usuario;


@ViewController
@NextView("/pg/tipo_alarma_edit.xhtml")
@PreviousView("/pg/tipo_alarma_list.xhtml")
public class TipoAlarmaEditMB extends AbstractEditPageBean<TipoAlarma,Long> {


	private static final long serialVersionUID = 1L;

	@Inject
	private TipoAlarmaBC tipoAlarmaBC;
	
	@Inject
	private UsuarioBC usuarioBC;
	
	
	private Long idResponsable1;
	private Long idResponsable2;
	
	private List<Usuario> usuarios;
	
	
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
	
	public List<Usuario> getUsuarios() {
		
		usuarios = usuarioBC.findAll();
	
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	private Usuario getResponsable1() {
		
		return usuarioBC.load(getIdResponsable1());
	}
	
	private Usuario getResponsable2() {
		
		return usuarioBC.load(getIdResponsable2());
	}
	
	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insert() {
		TipoAlarma tipoAlarma = new TipoAlarma();
		tipoAlarma.setDescripcion(getBean().getDescripcion());
		tipoAlarma.setTipo(getBean().getTipo());
		tipoAlarma.setDias(getBean().getDias());
		tipoAlarma.setHoras(getBean().getHoras());		
		tipoAlarma.setResponsable1(getResponsable1());
		tipoAlarma.setResponsable2(getResponsable2());
		
		tipoAlarmaBC.registrar(tipoAlarma);
		return getPreviousView();

	}

	@Override
	public String update() {
		TipoAlarma tipoAlarma = getBean();
		tipoAlarma.setResponsable1(getResponsable1());
		tipoAlarma.setResponsable2(getResponsable2());
		
		tipoAlarmaBC.editar(tipoAlarma);
		
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		setBean(this.tipoAlarmaBC.load(getId()));
		
	}
	
	@Override
	public TipoAlarma getBean() {
		TipoAlarma bean = super.getBean();
		return bean;
	}



}
