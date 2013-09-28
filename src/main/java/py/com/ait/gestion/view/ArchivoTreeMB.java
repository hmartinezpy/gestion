package py.com.ait.gestion.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.ticpy.tekoporu.stereotype.ViewController;

import py.com.ait.gestion.constant.AppProperties;

@ViewController
public class ArchivoTreeMB {

	@Inject
	private AppProperties appProperties;

	@Inject
	private Logger logger;

	@Inject
	private FacesContext facesContext;

	private TreeNode root;

	private StreamedContent file;

	private TreeNode selectedTreeNode;

	private String docPath = "";

	// private String docPath = "/tmp/jboss/";

	public ArchivoTreeMB() {
		if (appProperties == null) {
			this.docPath = "/tmp/jboss/";
		} else {
			this.docPath = appProperties.getDocumentPath();
		}
	}

	public void crearNodos(String path, TreeNode padre) {

		File[] files = new File(path).listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					TreeNode nodo = new DefaultTreeNode(file.getName(), padre);
					crearNodos(path + file.getName() + "/", nodo);
				} else {
					TreeNode hoja = new DefaultTreeNode("document",
							file.getName(), padre);
				}

			}
		}

	}

	public TreeNode getRoot() {

		// logger.info("-----" + docPath);
		root = new DefaultTreeNode("root", null);

		TreeNode main = new DefaultTreeNode(docPath, root);
		File[] filesMain = new File(docPath).listFiles();

		if (filesMain != null) {
			for (File fileMain : filesMain) {

				if (fileMain.isDirectory()) {
					TreeNode nodo = new DefaultTreeNode(fileMain.getName(),
							main);
					crearNodos(docPath + fileMain.getName() + "/", nodo);
				} else {
					TreeNode hoja = new DefaultTreeNode("document",
							fileMain.getName(), main);
				}
			}
		}

		return root;

	}

	public TreeNode getSelectedTreeNode() {
		return selectedTreeNode;
	}

	public void setSelectedTreeNode(TreeNode selectedTreeNode) {
		this.selectedTreeNode = selectedTreeNode;
	}

	public String getRuta(TreeNode seleccionado) {

		if (seleccionado.getParent().getParent() != null) {
			return getRuta(seleccionado.getParent())
					+ (String) seleccionado.getData() + "/";

		} else {
			return (String) seleccionado.getData();
		}

	}

	public void onNodeSelect(NodeSelectEvent event) {
		try {

			String ruta = getRuta(selectedTreeNode);
			ruta = ruta.substring(0, ruta.length() - 1);
			// logger.info("--------------- " + ruta);
			InputStream stream = new FileInputStream(ruta);
			StreamedContent archivo = new DefaultStreamedContent(stream,
					"application/octet-stream",
					(String) selectedTreeNode.getData());
			setFile(archivo);

			agregarMensaje("Archivo seleccionado: " + selectedTreeNode);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// agregarMensaje("Error descargando el archivo");
		}
	}

	public void setFile(StreamedContent file) {

		this.file = file;
	}

	public StreamedContent getFile() {

		return file;
	}

	public void agregarMensaje(String mensaje) {
		facesContext.addMessage("suceso", new FacesMessage(mensaje));
	}
}
