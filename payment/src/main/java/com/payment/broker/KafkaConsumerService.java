package com.payment.broker;

import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.entity.PaymentEntity;
import com.payment.model.PaymentOtp;
import com.payment.repository.PaymentRepository;
import com.payment.util.EmailUtil;

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

     @Autowired
     PaymentRepository paymentRepository;

     @KafkaListener(topics = "PAYMENT", groupId = "PAYME_IDs")
     public void consume(ConsumerRecord<String, String> payload){log.info("Tópico: {}", "PAYMENT");
          log.info("key: {}", payload.key());
          log.info("Headers: {}", payload.headers());
          log.info("Partion: {}", payload.partition());
          log.info("Order: {}", payload.value());
          try{
               PaymentEntity paymentEntity = new PaymentEntity();
               PaymentOtp notifOtp = convertJsonToYourClass(payload.value());
               // Do something with notifOtp
               System.out.println("Received message: " + notifOtp.getEmail() + " - " + notifOtp.getOtp());

               paymentEntity.setEmail(notifOtp.getEmail());

               paymentEntity.setIdCustomer(UUID.fromString(notifOtp.getIdCustomer()));
               
               paymentEntity.setIdService(UUID.fromString(notifOtp.getIdService()));
               
               paymentEntity.setOtpNumber(notifOtp.getOtp());
               
               paymentEntity.setToken(notifOtp.getToken());
               
               paymentEntity.setPrices(notifOtp.getPrices());

               paymentRepository.saveAndFlush(paymentEntity);
               emailUtil.sendOtpEmail(notifOtp.getEmail(), notifOtp.getOtp());
          }catch (Exception e) {
               // Handle exceptionss
               e.printStackTrace();
          }
         

     }
     public PaymentOtp convertJsonToYourClass(String json) throws Exception {
          return objectMapper.readValue(json, PaymentOtp.class);
      }
     
}
