package com.notification.broker;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.model.NotifOtp;
import com.notification.util.EmailUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumerService {
     @Autowired
     ObjectMapper objectMapper;

     @Autowired
     EmailUtil emailUtil;

     @KafkaListener(topics = "PAYMENT", groupId = "PAYMENT_ID")
     public void consume(ConsumerRecord<String, String> payload){log.info("Tópico: {}", "NOTIFS");
          log.info("key: {}", payload.key());
          log.info("Headers: {}", payload.headers());
          log.info("Partion: {}", payload.partition());
          log.info("Order: {}", payload.value());
          try{
              
               NotifOtp notifOtp = convertJsonToYourClass(payload.value());
               // Do something with notifOtp
               System.out.println("Received message: " + notifOtp.getEmail() + " - " + notifOtp.getOtp());
               emailUtil.sendOtpEmail(notifOtp.getEmail(), notifOtp.getOtp());
          }catch (Exception e) {
               // Handle exceptionss
               e.printStackTrace();
          }
         

     }
     public NotifOtp convertJsonToYourClass(String json) throws Exception {
          return objectMapper.readValue(json, NotifOtp.class);
      }
     
}
