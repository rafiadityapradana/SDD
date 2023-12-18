package com.notification.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
@Configuration
public class EmailConfig {
      @Bean
    public JavaMailSender getJavaMailSender() {
     JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
     mailSender.setHost("sandbox.smtp.mailtrap.io");
     mailSender.setPort(587);
     mailSender.setUsername("41c18d75d17209");
     mailSender.setPassword("7a0021c5f69bfd");

     Properties props = mailSender.getJavaMailProperties();
     props.put("mail.transport.protocol", "smtp");
     props.put("mail.smtp.auth", "true");
     props.put("mail.smtp.starttls.enable", "true");
     props.put("mail.debug", "true");

     return mailSender;
    }
}
