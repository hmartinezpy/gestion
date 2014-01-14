package py.com.ait.gestion.view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;

import py.com.ait.gestion.business.CronogramaBC;
import py.com.ait.gestion.business.ProcesoBC;
import py.com.ait.gestion.domain.Cronograma;
import py.com.ait.gestion.domain.Proceso;

@ViewController
@NextView("/pg/main_view.xhtml")
@PreviousView("/pg/main_view.xhtml")
public class MainViewMB {
	private TreeNode items;

    private List<Proceso> procesos;

    private Proceso proceso;

    private TreeNode selectedItem;
    
    @Inject
    private CronogramaBC cronogramaBC;
    
    @Inject
    private ProcesoBC procesoBC;

    @PostConstruct
    public void init() {
        items = new DefaultTreeNode("root", null);

        List<Cronograma> cronogramas = cronogramaBC.findAll();
        
        @SuppressWarnings("unused")
		TreeNode treeNode = null;
        
        for(Cronograma item: cronogramas){
        	treeNode = new DefaultTreeNode(item, items);
        }
        
        procesos = new ArrayList<Proceso>();
    }

    public TreeNode getItems() {
        return items;
    }

    public List<Proceso> getProcesos() {
        return procesos;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public TreeNode getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(TreeNode selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void send() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mail Sent!"));
    }
    
    public void onNodeSelect(NodeSelectEvent event) {  

    	Cronograma nodo = (Cronograma) event.getTreeNode().getData();
//        procesos = new ArrayList<Proceso>();
		String currentUser = FacesContext.getCurrentInstance()
				.getExternalContext().getUserPrincipal().getName();
        procesos = procesoBC.getProcesosByCronograma(nodo.getCronogramaId(), currentUser);
        
//        procesos.add(new Mail("id:"+nodo.getCronogramaId()+"sigla:"+nodo.getSigla()+"@nomeimporta.com", nodo.getNombre(), "FUNCIONAAAA!!!", new Date()));

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Seleccionado", nodo.getNombre());
        FacesContext.getCurrentInstance().addMessage(null, message);  
  
    }

    public void onRowSelect(SelectEvent event) {  
        FacesMessage msg = new FacesMessage("Proceso Selected", ((Proceso) event.getObject()).getDescripcion());  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }

}
