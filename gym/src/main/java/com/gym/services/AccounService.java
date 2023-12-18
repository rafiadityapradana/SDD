package com.gym.services;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.gym.configs.JwtTokenProvider;
import com.gym.entitys.CreditCartEntity;
import com.gym.entitys.CustomerEntity;
import com.gym.models.request.CreditCartRequest;
import com.gym.models.request.UpdatePasswordrequest;
import com.gym.repositorys.CreditCartRepository;
import com.gym.repositorys.CustomerRepository;
import com.gym.utils.EncryptionService;


@Service
public class AccounService {

     @Autowired
    JwtTokenProvider jwtUtil;
     @Autowired
     BCryptPasswordEncoder bCryptPasswordEncoder;

     @Autowired
     ResponeService responeService;

     @Autowired
     CustomerRepository customerRepository;
     @Autowired
     CreditCartRepository creditCartRepository;

     @Autowired 
     EncryptionService encryptionService;

     public ResponseEntity<Object> AccounInfo(javax.servlet.http.HttpServletRequest request) throws Exception {
          try {  
               String claims = jwtUtil.resolveClaims(request);

               Optional<CustomerEntity> customerEntity = customerRepository.findById(UUID.fromString(claims));
               return new ResponseEntity<>(responeService.ServiceObject(true, "Successfully Load Account", customerEntity), HttpStatus.OK);
          }catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Inteernal Server Error !");
          } 
     }

     public ResponseEntity<Object> UpdateCreditCart(javax.servlet.http.HttpServletRequest request, @Valid @RequestBody CreditCartRequest creditCartRequest) throws Exception {
          try {  
               String claims = jwtUtil.resolveClaims(request);

               CreditCartEntity creditCartEntity = creditCartRepository.findByCustomer(UUID.fromString(claims));

               creditCartEntity.setCartName(creditCartRequest.getCardholderName());
               creditCartEntity.setCartNumber(creditCartRequest.getCardNumber());
               creditCartEntity.setCartNumber(encryptionService.encrypt(creditCartRequest.getCardNumber()));
               creditCartEntity.setCvv(encryptionService.encrypt(creditCartRequest.getCardNumber()));
               creditCartEntity.setExpired(encryptionService.encrypt(creditCartRequest.getExpiredDate()));
               creditCartRepository.saveAndFlush(creditCartEntity);
               return new ResponseEntity<>(responeService.ServiceObject(true, "Successfully Load Account", creditCartEntity), HttpStatus.CREATED);
          }catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Inteernal Server Error !");
          } 
     }
     public ResponseEntity<Object> UpdatePassword(javax.servlet.http.HttpServletRequest request, @Valid @RequestBody UpdatePasswordrequest req) throws Exception {
          try {  
               String claims = jwtUtil.resolveClaims(request);
               Optional<CustomerEntity> customerEntity = customerRepository.findById(UUID.fromString(claims));

               if(!bCryptPasswordEncoder.matches(req.getOldPassword(), customerEntity.get().getPassword())) return new ResponseEntity<>(responeService.ServiceObject(false, "Incorrect email or password", null), HttpStatus.BAD_REQUEST);

               if(!req.getConfirmPassword().toString().equals(req.getNewPassword().toString())) return new ResponseEntity<>(responeService.ServiceObject(false, "Password Not match !", null), HttpStatus.BAD_REQUEST);

               customerEntity.get().setPassword(bCryptPasswordEncoder
               .encode(req.getNewPassword()));
              
               customerRepository.saveAndFlush(customerEntity);
               return new ResponseEntity<>(responeService.ServiceObject(true, "Reset Password Successfully", null), HttpStatus.CREATED);
          }catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Inteernal Server Error !");
          } 
     }

     
     
     
}
