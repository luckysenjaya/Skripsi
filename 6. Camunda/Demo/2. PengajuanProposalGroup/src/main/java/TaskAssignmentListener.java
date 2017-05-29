

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

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.context.Context;

public class TaskAssignmentListener implements TaskListener {
  private static final String HOST = "smtp.gmail.com";
  private static final String USER = "camundasys@gmail.com";
  private static final String PWD = "epW3S4KN";
  
  
  static Properties props;
  static Session session;
  static MimeMessage message;
  

  public void notify(DelegateTask delegateTask) {
    String assignee = delegateTask.getAssignee();
    String taskId = delegateTask.getId();
  
    if (assignee != null) {
      IdentityService identityService = Context.getProcessEngineConfiguration().getIdentityService();
      User user = identityService.createUserQuery().userId(assignee).singleResult();
      if (user != null) {
        String recipient = user.getEmail();
        if (recipient != null && !recipient.isEmpty()) {
           try {
               props = System.getProperties();
               props.put("mail.smtp.port", "587");
               props.put("mail.smtp.auth", "true");
               props.put("mail.smtp.starttls.enable", "true");

               
               session = Session.getDefaultInstance(props, null);
               message = new MimeMessage(session);
               message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
               message.setSubject("Task " + delegateTask.getName());
               
               String emailBody = user.getFirstName() +",<br>";
               emailBody += "Tolong Selesaikan Task " +delegateTask.getName() + " di bawah ini.<br>";
               emailBody += "http://localhost:1234/camunda/app/tasklist/default/#/?task="+taskId;
               message.setContent(emailBody, "text/html");
               

               Transport transport = session.getTransport("smtp");            
               transport.connect(HOST, USER, PWD);
               transport.sendMessage(message, message.getAllRecipients());
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
