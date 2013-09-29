package py.com.ait.gestion.util;

import java.util.ArrayList;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;

import py.com.ait.gestion.constant.AppProperties;

public class MailUtil
{
	    
	@Inject
	private Logger logger;
	
	@Inject
	private AppProperties appProperties;
	
	public void sendMail(String to, String subject, String body) {
		
		ArrayList<String> toList = new ArrayList<String>();
		toList.add(to);
		sendMail(toList, subject, body);
	}
	
    public void sendMail(ArrayList<String> toList, String subject, String body)
    {
        try
        {
        	logger.debug(">>>>> SendMail: " + subject);
        	
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", appProperties.getMailHost());
            //props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", appProperties.getMailPort());
            //props.setProperty("mail.smtp.user", "julio");
            props.setProperty("mail.smtp.auth", "false");
            Session session = Session.getDefaultInstance(props);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(appProperties.getMailFrom()));
            for(String to : toList) {
            	message.addRecipient(
            			Message.RecipientType.TO,
            			new InternetAddress(to));
            }
            message.setSubject(subject);
            message.setText(body);

            // Enviar mail
            Transport t = session.getTransport("smtp");
            t.connect();
            t.sendMessage(message, message.getAllRecipients());

            // Cierre
            t.close();            
            logger.debug(">>>>> End SendMail");
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}