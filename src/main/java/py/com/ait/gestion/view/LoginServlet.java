package py.com.ait.gestion.view;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.web.tomcat.security.login.WebAuthentication;
import org.slf4j.Logger;

import py.com.ait.gestion.business.UsuarioBC;
import py.com.ait.gestion.domain.LogSesion;
import py.com.ait.gestion.persistence.SesionLogDAO;


/**
 * Servlet implementation class ServletTest
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject private Logger logger;
	@Inject private UsuarioBC usuarioBC;
	@Inject private SesionLogDAO sesionLogDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super(); 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get login parameters
		String user = request.getParameter("j_username");
        String pass = request.getParameter("j_password");
        
        String ip = request.getRemoteAddr();
        String host = request.getRemoteHost();
        
        WebAuthentication webAuthentication = new WebAuthentication();
        logger.info("Autenticando user: " + user +" >>> "+ pass);
        if(webAuthentication.login(user, pass))
        {
        	
        	
        	/* COLOCAR LOGICA DE NEGOCIO POST LOGIN */
        	/* ... */
        	
        	//redirect to original url    

        	
        	logger.info("Autenticacion con exito, user: " + user +">>>"+ ip+ ">>>" + host);
        
        	
        	
      	    java.util.Date utilDate = new java.util.Date(); //fecha actual
      	    long lnMilisegundos = utilDate.getTime();
      	    java.sql.Timestamp fechaIntento = new java.sql.Timestamp(lnMilisegundos);
      	    
      	    LogSesion logSesion = new LogSesion();
        	logSesion.setUsuario(usuarioBC.findSpecificUser(user));
        	logSesion.setIp(ip);
        	logSesion.setUser(user);
        	logSesion.setExito("Y");
        	logSesion.setFechaIntento(fechaIntento);
        	
        	sesionLogDAO.InsertLog(logSesion);
        	
        	String referer = request.getHeader("Referer");
        	response.sendRedirect(referer);

            return;
        }
        else
        {
        	
        	//redirect to error page
        	
        	logger.info("Autenticacion con fallo, user: " + user);
        	
      	    java.util.Date utilDate = new java.util.Date(); //fecha actual
      	    long lnMilisegundos = utilDate.getTime();
      	    java.sql.Timestamp fechaIntento = new java.sql.Timestamp(lnMilisegundos);
      	    
      	    LogSesion logSesion = new LogSesion();
      	    
      	    if(usuarioBC.findSpecificUser(user) != null){
            	logSesion.setUsuario(usuarioBC.findSpecificUser(user));
            	logSesion.setObsUsuario("Password incorrecto");
      	    }
      	    else{
      	    	logSesion.setUser(user);
            	logSesion.setObsUsuario("Usuario no Existe");
      	    }
      	    
        	logSesion.setIp(ip);
        	logSesion.setUser(user);
        	logSesion.setExito("N");
        	logSesion.setFechaIntento(fechaIntento);
        	sesionLogDAO.InsertLog(logSesion);

        	response.sendRedirect("loginError.jsp");
        }

	}

}
