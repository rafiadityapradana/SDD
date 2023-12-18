package com.gym.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
     @Autowired
     private JavaMailSender javaMailSender;
     public void sendOtpEmail(String email, String otp) throws MessagingException {
          MimeMessage mimeMessage = javaMailSender.createMimeMessage();
          MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
          mimeMessageHelper.setTo(email);
          mimeMessageHelper.setFrom("mail@rafiadityapradana.com");
          mimeMessageHelper.setSubject("Verify OTP");
          mimeMessageHelper.setText("""
          <div>
          This Is Your OTP Code : %s
          </div>
          """.formatted(otp), true);
  
          javaMailSender.send(mimeMessage);
      }
  

     
}
