package com.uet.hocvv.equiz.service.impl;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

@Service
public class EmailServiceImpl {
	@Autowired
	JavaMailSender mailSender;
	@Value("classpath:/static/logoEQuiz.png")
	Resource resourceFile;
	
	@Qualifier("freemarkerClassLoaderConfig")
	@Autowired
	FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Async
	void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		helper.setSubject(subject);
		helper.setText(htmlBody, true);
		helper.addInline("logoEQuiz.png", resourceFile);
		
		mailSender.send(message);
	}
	
	public void sendEmail(String to, String subject,  String templateName, Map<String, Object> params) throws IOException, TemplateException, MessagingException {
		Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration()
				.getTemplate(templateName);
		String body = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, params);
		sendHtmlMessage(to, subject, body);
	}
	
}
