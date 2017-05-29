package pengajuanproposal;


import java.util.List;
import java.util.Properties;
import java.util.Set;
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
import org.camunda.bpm.engine.impl.persistence.entity.IdentityLinkEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.task.IdentityLinkType;


public class TaskAssignmentListener implements TaskListener {
  private static final String HOST = "smtp.gmail.com";
  private static final String USER = "camundasys@gmail.com";
  private static final String PWD = "epW3S4KN";
  
  String assignee;
  String taskId;
  String taskName;
  
  String[] recipient;
  
  static Properties props;
  static Session session;
  static MimeMessage message;
  

  public void notify(DelegateTask delegateTask) {
    assignee = delegateTask.getAssignee();
    taskId = delegateTask.getId();
    taskName = delegateTask.getName();
    delegateTask.getCandidates();
    
    if (assignee != null) {
      IdentityService identityService = Context.getProcessEngineConfiguration().getIdentityService();
      User user = identityService.createUserQuery().userId(assignee).singleResult();
      if (user != null) {
    	  this.sendEmail(user);
      }
    }
    else{
    	TaskEntity task = (TaskEntity)delegateTask;
    	List<IdentityLinkEntity> identityLinks = task.getIdentityLinks();
    	
    	for(IdentityLinkEntity link : identityLinks) {
    		if(link.getType().equals(IdentityLinkType.CANDIDATE)) {
    		    if(link.isUser()) {
	    		     User user = Context.getProcessEngineConfiguration().getIdentityService().createUserQuery().userId(link.getUserId()).singleResult();
	    		     sendEmail(user);
    		    }
    		    if(link.isGroup()) {
    		        List<User> users = Context.getProcessEngineConfiguration().getIdentityService().createUserQuery().memberOfGroup(link.getGroupId()).list();
    		        for(User user : users) {
    		        	sendEmail(user);
    		        }
    		    }
    		}
    	}
    }
  }
  
  public void sendEmail(User user){
      try {
          props = System.getProperties();
          props.put("mail.smtp.port", "587");
          props.put("mail.smtp.auth", "true");
          props.put("mail.smtp.starttls.enable", "true");

          
          session = Session.getDefaultInstance(props, null);
          message = new MimeMessage(session);
          message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
          message.setSubject("Task " + taskName);
          
          String emailBody = user.getFirstName() +",<br>";
          emailBody += "Tolong Selesaikan Task " +taskName + " di bawah ini.<br>";
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
