package py.com.ait.gestion.view;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;

import py.com.ait.gestion.business.PreguntaBC;
import py.com.ait.gestion.domain.Pregunta;


@ViewController
@NextView("/pg/pregunta_edit.xhtml")
@PreviousView("/pg/pregunta_list.xhtml")
public class PreguntaEditMB extends AbstractEditPageBean<Pregunta,Long> {


	private static final long serialVersionUID = 1L;

	
	@Inject
	private PreguntaBC preguntaBC;

	
	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insert() {
		Pregunta pregunta = new Pregunta();
		pregunta.setDescripcion(getBean().getDescripcion());
		
		preguntaBC.registrar(pregunta);
		return getPreviousView();

	}

	@Override
	public String update() {
		Pregunta pregunta = getBean();
		
		preguntaBC.editar(pregunta);
		
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		setBean(this.preguntaBC.load(getId()));
		
	}
	
	@Override
	public Pregunta getBean() {
		Pregunta bean = super.getBean();
		return bean;
	}



}
