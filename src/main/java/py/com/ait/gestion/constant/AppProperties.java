package py.com.ait.gestion.constant;

import org.ticpy.tekoporu.annotation.Name;
import org.ticpy.tekoporu.configuration.ConfigType;
import org.ticpy.tekoporu.configuration.Configuration;

@Configuration(resource="app",type=ConfigType.PROPERTIES)
public class AppProperties {

	@Name("mail.from")
	private String mailFrom;
	
	@Name("mail.host")
	private String mailHost;
	
	@Name("mail.port")
	private String mailPort;
	
	@Name("mail.tls")
	private String mailTls;
	
	@Name("mail.auth")
	private String mailAuth;
	
	@Name("mail.user")
	private String mailUser;
	
	@Name("mail.pwd")
	private String mailPwd;

	@Name("mail.active")
	private Boolean mailActive;

	@Name("notificacion.intervalo.min")
	private int notificacionIntervaloMin;
	
	@Name("document.path")
	private String documentPath;

	public String getMailFrom() {
		return mailFrom;
	}

	public String getMailHost() {
		return mailHost;
	}

	public String getMailPort() {
		return mailPort;
	}
	
	public String getMailTls() {
		return mailTls;
	}

	public String getMailAuth() {
		return mailAuth;
	}

	public String getMailUser() {
		return mailUser;
	}

	public String getMailPwd() {
		return mailPwd;
	}

	public Boolean getMailActive() {
		return mailActive;
	}

	public int getNotificacionIntervaloMin() {
		return notificacionIntervaloMin;
	}
	
	public String getDocumentPath() {
		return documentPath;
	}	
	
}
