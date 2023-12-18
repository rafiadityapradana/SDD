package com.gym.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class SubscriptionService {
     @Autowired
     ResponeService responeService;

     public ResponseEntity<Object> serviceList() throws Exception {

          
          return new ResponseEntity<>(responeService.GetServiceList(true, "Successfully Load Data Service", null, (long) 0), HttpStatus.CREATED);
     }
     
}
