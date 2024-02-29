package com.gym.services;


import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.gym.broker.KafkaProducerService;
import com.gym.configs.JwtTokenProvider;
import com.gym.entitys.*;
import com.gym.models.kafka.NotifOtp;
import com.gym.models.request.*;
import com.gym.models.respone.*;
import com.gym.repositorys.*;
import com.gym.utils.*;
@Service
public class AuthService {
     @Autowired
     private OtpUtil otpUtil;


     @Autowired
     ResponeService responeService;
     
     @Autowired
     JwtTokenProvider jwtTokenProvider;

     @Autowired
     CustomerRepository customerRepository;

     @Autowired
     CreditCartRepository creditCartRepository;

     @Autowired
     CustomerOtpRepository customerOtpRepository; 

     @Autowired
     BCryptPasswordEncoder bCryptPasswordEncoder;

     @Autowired
     AuthenticationManager authenticationManager;

     @Autowired 
     EncryptionService encryptionService;

     @Autowired
     KafkaProducerService kafkaProducerService;


     private String extractSqlErrorCode(Throwable throwable) {
        if (throwable instanceof SQLException) {
            // Extract the SQL error code from the SQLException
            return ((SQLException) throwable).getSQLState();
        } else {
            // Handle other cases or return a default value
            return "UNKNOWN_ERROR";
        }
    }
    
    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

     public ResponseEntity<Object> registerService(RegisterRequest req) throws Exception {

          RegisterRespone registerRespone = new RegisterRespone();
          try {
               String accessToken = jwtTokenProvider.generateToken("dfsdffs", 520000);
               System.out.println("accessToken "+accessToken);
               String otp = otpUtil.generatedOtp();     
               CustomerEntity customerEntity = new CustomerEntity();
               CreditCartEntity creditCartEntity = new CreditCartEntity();
               CustomerOtpEntity customerOtpEntity = new CustomerOtpEntity();
               customerEntity.setCustomerName(req.getName());
               customerEntity.setEmail(req.getEmail());
               customerEntity.setPassword(bCryptPasswordEncoder
               .encode(req.getPassword()));
               customerEntity.setPhoneNumber(req.getPhoneNumber());
               customerRepository.saveAndFlush(customerEntity);
               String token = jwtTokenProvider.generateToken(otp, 120000);
               System.out.println("jwtToken "+ token);
               System.out.println("UID "+ customerEntity.getIdCustomer().toString());
               creditCartEntity.setCustomer(customerEntity);
               creditCartEntity.setCartName(req.getCardholderName());
               creditCartEntity.setCartNumber(encryptionService
               .encrypt(req.getCardNumber()));
               creditCartEntity.setCvv(encryptionService
               .encrypt(req.getCardNumber()));

               creditCartEntity.setExpired(encryptionService
               .encrypt(req.getExpiredDate()));

               creditCartRepository.saveAndFlush(creditCartEntity);
               customerOtpEntity.setCustomerId(customerEntity.getIdCustomer());
               customerOtpEntity.setOtpNumber(otp);
               customerOtpEntity.setOtpToken(token);
               customerOtpEntity.setOtpType(OtpType.ACTIVATION);
               customerOtpEntity.setDeviceName(req.getDeviceName());
               customerOtpEntity.setIpAddress(req.getIpAddress());

               customerOtpRepository.saveAndFlush(customerOtpEntity);
               registerRespone.setToken(token);
               registerRespone.setExpirationIn(120000);
               NotifOtp message = new NotifOtp();
               message.setEmail(customerEntity.getEmail());
               message.setOtp(otp);
               Gson gson = new Gson();
               String jString = gson.toJson(message);
               kafkaProducerService.sendMessage("NOTIF", jString);

          } catch (DataIntegrityViolationException e) {
               Throwable rootCause = e.getRootCause();
               String sqlErrorCode = extractSqlErrorCode(rootCause);
               if(sqlErrorCode.equals("23505")) return new ResponseEntity<>(responeService.ServiceObject(false, rootCause.getMessage().toString(), null), HttpStatus.CONFLICT);
               
               return new ResponseEntity<>(responeService.ServiceObject(false, "An error occurred during registration !", null), HttpStatus.INTERNAL_SERVER_ERROR);
          }
          
          return new ResponseEntity<>(responeService.ServiceObject(true, "Successfully Register", registerRespone), HttpStatus.CREATED);

     }

     public ResponseEntity<Object> verifyAccountService(VerifyAccountRequest req) throws Exception {
          try {
               if(!jwtTokenProvider.validateToken(req.getToken())) return new ResponseEntity<>(responeService.ServiceObject(false, "Otp Expired !", null), HttpStatus.BAD_REQUEST);
               
               CustomerOtpEntity customerOtpEntity = customerOtpRepository.findByOtpNumber(req.getOtp());

               if(customerOtpEntity == null) return new ResponseEntity<>(responeService.ServiceObject(false, "Otp Not Found !", null), HttpStatus.NOT_FOUND);
               
               if(!req.getDeviceName().equals(customerOtpEntity.getDeviceName())) return new ResponseEntity<>(responeService.ServiceObject(false, "Incompatible Device !", null), HttpStatus.NOT_FOUND);

               if(!req.getIpAddress().equals(customerOtpEntity.getIpAddress())) return new ResponseEntity<>(responeService.ServiceObject(false, "Inappropriate IP!", null), HttpStatus.NOT_FOUND);

               customerOtpEntity.setOtpStatus(true);
               customerOtpRepository.saveAndFlush(customerOtpEntity);
               Optional<CustomerEntity> customerEntity = customerRepository.findById(customerOtpEntity.getCustomerId());
               customerEntity.get().setActive(true);
               customerRepository.saveAndFlush(customerEntity.get());
               

               return new ResponseEntity<>(responeService.ServiceObject(true, "Activate Account Successfully", null), HttpStatus.CREATED);
          } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("verify account failed !");
          } 
          

          
     }
      public ResponseEntity<Object> loginService(LoginRequest req) throws Exception {
          try {   
               LoginRespone loginRespone =  new LoginRespone();
               CustomerEntity customerEntity =  customerRepository.findByEmail(req.getEmail());
               if (customerEntity == null)  return new ResponseEntity<>(responeService.ServiceObject(false, "Customer "+req.getEmail()+" Not Found !", null), HttpStatus.NOT_FOUND);

               if(!bCryptPasswordEncoder.matches(req.getPassword(), customerEntity.getPassword())) return new ResponseEntity<>(responeService.ServiceObject(false, "Incorrect email or password", null), HttpStatus.BAD_REQUEST);
               
               CustomerOtpEntity customerOtpEntity = customerOtpRepository.findByCustomerId(customerEntity.getIdCustomer());

               if(!customerEntity.isActive() && !customerOtpEntity.isOtpStatus()) return new ResponseEntity<>(responeService.ServiceObject(false, "Inactive customers please activate first ", null), HttpStatus.BAD_REQUEST);  

               Authentication authentication =
                        new UsernamePasswordAuthenticationToken(customerEntity.getIdCustomer().toString(),"",new ArrayList<>());

               String accessToken = jwtTokenProvider.generateToken(customerEntity.getIdCustomer().toString(), 520000);
               String refreshToken = jwtTokenProvider.generateToken(customerEntity.getIdCustomer().toString(), 1020000);
               loginRespone.setAccessToken(accessToken);
               loginRespone.setRefreshToken(refreshToken);
               return new ResponseEntity<>(responeService.ServiceObject(true, "Successfully Login", loginRespone), HttpStatus.CREATED);
          } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Login account failed !");
          } 
          
     }
      public ResponseEntity<Object> RequestResetPasswordService(RequestResetPassword req) throws Exception {
          try {   
               RegisterRespone registerRespone = new RegisterRespone();

               String otp = otpUtil.generatedOtp(); 
               String token = jwtTokenProvider.generateToken(otp, 120000);
               CustomerEntity customerEntity =  customerRepository.findByEmail(req.getEmail());
               if (customerEntity == null)  return new ResponseEntity<>(responeService.ServiceObject(false, "Customer "+req.getEmail()+" Not Found !", null), HttpStatus.NOT_FOUND); 

               if(!customerEntity.isActive()) return new ResponseEntity<>(responeService.ServiceObject(false, "Inactive customers please activate first ", null), HttpStatus.BAD_REQUEST);   
               CustomerOtpEntity customerOtpEntity =  new CustomerOtpEntity();
               customerOtpEntity.setCustomerId(customerEntity.getIdCustomer());
               customerOtpEntity.setOtpNumber(otp);
               customerOtpEntity.setOtpToken(token);
               customerOtpEntity.setOtpType(OtpType.RESET_PASSWORD);
               customerOtpEntity.setDeviceName(req.getDeviceName());
               customerOtpEntity.setIpAddress(req.getIpAddress());
               customerOtpRepository.saveAndFlush(customerOtpEntity);
               registerRespone.setToken(token);
               registerRespone.setExpirationIn(120000);
               NotifOtp message = new NotifOtp();
               message.setEmail(customerEntity.getEmail());
               message.setOtp(otp);
               Gson gson = new Gson();
               String jString = gson.toJson(message);
               kafkaProducerService.sendMessage("NOTIF", jString);

               return new ResponseEntity<>(responeService.ServiceObject(true, "Successfully Request Reset Password", registerRespone), HttpStatus.CREATED);
          } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Request Reset Password failed !");
          } 
          
     }
     public ResponseEntity<Object> regenerateOtpService(RegenerateOtpRequest req) throws Exception {
          try {   
               String otp = otpUtil.generatedOtp(); 
               String token = jwtTokenProvider.generateToken(otp, 120000);
               RegisterRespone registerRespone = new RegisterRespone();
               CustomerOtpEntity customerOtpEntity = customerOtpRepository.findByToken(req.getToken());
               if(customerOtpEntity == null) return new ResponseEntity<>(responeService.ServiceObject(false, "Otp Not Found !", null), HttpStatus.NOT_FOUND);
               
               if(!req.getDeviceName().equals(customerOtpEntity.getDeviceName())) return new ResponseEntity<>(responeService.ServiceObject(false, "Incompatible Device !", null), HttpStatus.NOT_FOUND);

               if(!req.getIpAddress().equals(customerOtpEntity.getIpAddress())) return new ResponseEntity<>(responeService.ServiceObject(false, "Inappropriate IP!", null), HttpStatus.NOT_FOUND);
               customerOtpEntity.setOtpNumber(otp);
               customerOtpEntity.setOtpToken(token);

               customerOtpRepository.saveAndFlush(customerOtpEntity);
               registerRespone.setToken(token);
               registerRespone.setExpirationIn(120000);
              NotifOtp message = new NotifOtp();
               message.setEmail(req.getEmail());
               message.setOtp(otp);
               Gson gson = new Gson();
               String jString = gson.toJson(message);
               kafkaProducerService.sendMessage("NOTIF", jString);

               return new ResponseEntity<>(responeService.ServiceObject(true, "Regenerate Otp Successfully", registerRespone), HttpStatus.CREATED);
          } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Regenerate Otp failed !");
          } 
          
     }


      public ResponseEntity<Object> ResetPasswordService(ResetPassword req) throws Exception {
          try{

               CustomerEntity customerEntity =  customerRepository.findByEmail(req.getEmail());
               if (customerEntity == null)  return new ResponseEntity<>(responeService.ServiceObject(false, "Customer "+req.getEmail()+" Not Found !", null), HttpStatus.NOT_FOUND);
               CustomerOtpEntity customerOtpEntity = customerOtpRepository.findByToken(req.getToken());
               if(customerOtpEntity == null) return new ResponseEntity<>(responeService.ServiceObject(false, "Token Not Found !", null), HttpStatus.NOT_FOUND);

               if(!req.getConfirmPassword().toString().equals(req.getPassword().toString())) return new ResponseEntity<>(responeService.ServiceObject(false, "Password Not match !", null), HttpStatus.BAD_REQUEST);
               customerOtpEntity.setOtpStatus(true);
               customerOtpRepository.saveAndFlush(customerOtpEntity);

               customerEntity.setPassword(bCryptPasswordEncoder
               .encode(req.getPassword()));
               customerRepository.saveAndFlush(customerEntity);

               return new ResponseEntity<>(responeService.ServiceObject(true, "Reset Password Successfully", null), HttpStatus.CREATED);
          }catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Reset Password failed !");
          } 
      }
    

     
}
