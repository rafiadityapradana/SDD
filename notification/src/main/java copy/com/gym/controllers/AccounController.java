package com.gym.controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.gym.broker.KafkaProducerService;
import com.gym.models.request.CreditCartRequest;
import com.gym.models.request.UpdatePasswordrequest;
import com.gym.services.AccounService;


@RestController
@RequestMapping("/account/v1")
@Validated
public class AccounController {

     @Autowired
     private AccounService accounService;

     
     
     @GetMapping("/info")
     public ResponseEntity<Object> AccountInfoController(javax.servlet.http.HttpServletRequest request) throws Exception {
          
          return accounService.AccounInfo(request);
     }

     @PutMapping("/update-credit-cart")
     public ResponseEntity<Object> UpdateCreditCartController(javax.servlet.http.HttpServletRequest request, @Valid @RequestBody CreditCartRequest creditCartRequest) throws Exception {
          return accounService.UpdateCreditCart(request, creditCartRequest);
     }
     @PutMapping("/update-password")
     public ResponseEntity<Object> UpdatePasswordController(javax.servlet.http.HttpServletRequest request, @Valid @RequestBody UpdatePasswordrequest updatePasswordrequest) throws Exception {
          return accounService.UpdatePassword(request, updatePasswordrequest);
     }
}
