package py.com.ait.gestion.view;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;

import py.com.ait.gestion.business.ChecklistBC;
import py.com.ait.gestion.domain.Checklist;


@ViewController
@NextView("/pg/checklist_edit.xhtml")
@PreviousView("/pg/checklist_list.xhtml")
public class ChecklistEditMB extends AbstractEditPageBean<Checklist,Long> {


	private static final long serialVersionUID = 1L;

	
	@Inject
	private ChecklistBC checklistBC;

	
	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insert() {
		Checklist checklist = new Checklist();
		checklist.setDescripcion(getBean().getDescripcion());
		
		checklistBC.registrar(checklist);
		return getPreviousView();

	}

	@Override
	public String update() {
		Checklist checklist = getBean();
		
		checklistBC.editar(checklist);
		
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		setBean(this.checklistBC.load(getId()));
		
	}
	
	@Override
	public Checklist getBean() {
		Checklist bean = super.getBean();
		return bean;
	}



}
