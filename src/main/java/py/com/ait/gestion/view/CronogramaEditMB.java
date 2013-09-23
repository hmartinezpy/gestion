package py.com.ait.gestion.view;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;

import py.com.ait.gestion.business.CronogramaBC;
import py.com.ait.gestion.domain.Cronograma;


@ViewController
@NextView("/pg/cronograma_edit.xhtml")
@PreviousView("/pg/cronograma_list.xhtml")
public class CronogramaEditMB extends AbstractEditPageBean<Cronograma,Long> {


	private static final long serialVersionUID = 1L;

	
	@Inject
	private CronogramaBC cronogramaBC;

	
	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insert() {
		cronogramaBC.registrar(getBean());
		return getPreviousView();

	}

	@Override
	public String update() {
		Cronograma cronograma = getBean();
		
		cronogramaBC.editar(cronograma);
		
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		setBean(this.cronogramaBC.load(getId()));
		
	}
	
	@Override
	public Cronograma getBean() {
		Cronograma bean = super.getBean();
		return bean;
	}



}
