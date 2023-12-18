package com.gym.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     public ResponseEntity<Object> InquiryController() throws Exception {
          return subscriptionService.serviceList();
     }
     @PostMapping("/payment")
     public ResponseEntity<Object> PaymentController() throws Exception {
          return subscriptionService.serviceList();
     }
}
