package com.gym.controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.gym.models.request.InfoPaymentRequest;
import com.gym.models.request.InquiryRequest;
import com.gym.services.SubscriptionService;

@RestController
@RequestMapping("/subscription/v1")
@Validated
public class SubscriptionController {

     @Autowired
     private SubscriptionService subscriptionService;
     
     @GetMapping("/service")
     public ResponseEntity<Object> serviceController() throws Exception {
          return subscriptionService.serviceList();
     }

     @PostMapping("/inquiry")
     public ResponseEntity<Object> InquiryController(javax.servlet.http.HttpServletRequest request,@Valid @RequestBody InquiryRequest inquiryRequest) throws Exception {
          return subscriptionService.inquiryService(request,inquiryRequest);
     }
     @PostMapping("/payment")
     public ResponseEntity<Object> PaymentController(@Valid @RequestBody InfoPaymentRequest infoPaymentRequest) throws Exception {
          return subscriptionService.paymentService(infoPaymentRequest);
     }
}
