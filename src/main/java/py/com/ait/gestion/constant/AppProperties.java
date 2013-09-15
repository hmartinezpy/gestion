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
	
	public String getDocumentPath() {
		return documentPath;
	}	
}
