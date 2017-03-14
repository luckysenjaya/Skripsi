/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.extension.mail.send;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Rule;
import org.junit.Test;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

public class SendMailConnectorProcessTest {

  @Rule
  public ProcessEngineRule engineRule = new ProcessEngineRule();

  @Rule
  public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.ALL);

  @Test
  @Deployment(resources = "processes/mail-send-text.bpmn")
  public void sendMailWithTextBody() throws MessagingException {
    engineRule.getRuntimeService().startProcessInstanceByKey("send-mail");

    MimeMessage[] mails = greenMail.getReceivedMessages();
    assertThat(mails).hasSize(1);

    MimeMessage mail = mails[0];
    assertThat(mail.getSubject()).isEqualTo("Test");
    assertThat(GreenMailUtil.getBody(mail)).isEqualTo("text body");
  }

  @Test
  @Deployment(resources = "processes/mail-send-template.bpmn")
  public void sendMailWithTemplateTextBody() throws MessagingException {
    engineRule.getRuntimeService().startProcessInstanceByKey("send-mail",
        Variables.createVariables().putValue("user", "Test"));

    MimeMessage[] mails = greenMail.getReceivedMessages();
    assertThat(mails).hasSize(1);

    MimeMessage mail = mails[0];
    assertThat(GreenMailUtil.getBody(mail)).isEqualTo("Hello Test");
  }

  @Test
  @Deployment(resources = "processes/mail-send-file.bpmn")
  public void sendMailWithFileName() throws Exception {
    File attachment = new File(getClass().getResource("/attachment.txt").toURI());
    assertThat(attachment.exists()).isTrue();

    engineRule.getRuntimeService().startProcessInstanceByKey("send-mail",
        Variables.createVariables().putValue("file", attachment.getPath()));

    MimeMessage[] mails = greenMail.getReceivedMessages();
    assertThat(mails).hasSize(1);

    MimeMessage mail = mails[0];

    assertThat(mail.getContent()).isInstanceOf(MimeMultipart.class);
    MimeMultipart multiPart = (MimeMultipart) mail.getContent();

    assertThat(multiPart.getCount()).isEqualTo(1);
    assertThat(GreenMailUtil.getBody(multiPart.getBodyPart(0))).isEqualTo("plain text");
  }

}
