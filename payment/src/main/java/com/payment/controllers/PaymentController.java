package com.payment.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.model.RequestInfoPayment;
import com.payment.repository.PaymentRepository;

@RestController
@RequestMapping("/payment/v1")
@Validated
public class PaymentController {
     @Autowired
     PaymentRepository paymentRepository;
 
     @PostMapping("/info")
     public ResponseEntity<Object> AccountInfoController(@RequestBody RequestInfoPayment requestInfoPayment) throws Exception {
          return new ResponseEntity<>(ServiceObject(true, "Successfully Load Otp Pyment", paymentRepository.findByOtpAndToken(requestInfoPayment.getOtpNumber(), requestInfoPayment.getToken())), HttpStatus.OK);
     }
     
      public HashMap<String, Object> ServiceObject(Boolean Status, String Message, Object Data){
        HashMap<String, Object> finalMap = new HashMap<>();
        finalMap.put("status", Status);
        finalMap.put("message", Message);
        finalMap.put("data", Data);
        return finalMap;
    }
}

