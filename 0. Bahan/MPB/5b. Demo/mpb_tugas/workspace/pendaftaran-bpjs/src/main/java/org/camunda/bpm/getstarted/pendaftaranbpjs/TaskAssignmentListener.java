package org.camunda.bpm.getstarted.pendaftaranbpjs;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;


import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.context.Context;

public class TaskAssignmentListener implements TaskListener {

  // TODO: Set Mail Server Properties
  private static final String HOST = "smtp.gmail.com";
  private static final String USER = "okazakiryu28@gmail.com";
  private static final String PWD = "2L2u1c4z481";
  
  
  static Properties mailServerProperties;
  static Session getMailSession;
  static MimeMessage generateMailMessage;
  
  private final static Logger LOGGER = Logger.getLogger(TaskAssignmentListener.class.getName());

  public void notify(DelegateTask delegateTask) {
	LOGGER.info("enter class");
    String assignee = delegateTask.getAssignee();
    String taskId = delegateTask.getId();

    
    
    if (assignee != null) {
      
      // Get User Profile from User Management
      IdentityService identityService = Context.getProcessEngineConfiguration().getIdentityService();
      User user = identityService.createUserQuery().userId(assignee).singleResult();

      if (user != null) {
       Address a;
        // Get Email Address from User Profile
        String recipient = user.getEmail();
        if (recipient != null && !recipient.isEmpty()) {
           try {
               // Step1
        	   LOGGER.info("\n 1st ===> setup Mail Server Properties..");
               mailServerProperties = System.getProperties();
               mailServerProperties.put("mail.smtp.port", "587");
               mailServerProperties.put("mail.smtp.auth", "true");
               mailServerProperties.put("mail.smtp.starttls.enable", "true");
               //LOGGER.info("Mail Server Properties have been setup successfully..");
               
               // Step2
               LOGGER.info("\n\n 2nd ===> get Mail Session..");
               getMailSession = Session.getDefaultInstance(mailServerProperties, null);
               generateMailMessage = new MimeMessage(getMailSession);
               generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
               generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(recipient));
               generateMailMessage.setSubject("TEST");
               String emailBody = "Test email";
               emailBody += "Task assigned: " + delegateTask.getName()+"\n";
               emailBody += user.getFirstName() +" with email :"+ user.getEmail() + " Please complete : http://localhost:1234/camunda/app/tasklist/default/#/?searchQuery=task="+taskId;// %5B%5D&filter=e0553560-278a-11e7-92f8-902b3437070c&sorting=%5B%7B%22sortBy%22:%22created%22,%22sortOrder%22:%22desc%22%7D%5D&task="+ taskId;
               generateMailMessage.setContent(emailBody, "text/html");
               LOGGER.info("Mail Session has been created successfully..");
               
               // Step3
               LOGGER.info("\n\n 3rd ===> Get Session and Send mail");
               Transport transport = getMailSession.getTransport("smtp");
               
               // Enter your correct gmail UserID and Password
               // if you have 2FA enabled then provide App Specific Password
               transport.connect("smtp.gmail.com", USER, PWD);
               transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
               transport.close();
           } catch (NoSuchProviderException ex) {
               Logger.getLogger(TaskAssignmentListener.class.getName()).log(Level.SEVERE, null, ex);
           } catch (MessagingException ex) {
               Logger.getLogger(TaskAssignmentListener.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
      }

    }

  }

}
