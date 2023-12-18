package com.gym.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.gym.broker.KafkaProducerService;
import com.gym.configs.JwtTokenProvider;
import com.gym.entitys.CustomerEntity;
import com.gym.entitys.ServiceEntity;
import com.gym.models.kafka.PaymentOtp;
import com.gym.models.request.InfoPaymentRequest;
import com.gym.models.request.InquiryRequest;
import com.gym.models.respone.RegisterRespone;
import com.gym.repositorys.CustomerRepository;
import com.gym.repositorys.ServiceRepository;
import com.gym.utils.OtpUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class SubscriptionService {
     @Autowired
     private OtpUtil otpUtil;

     @Autowired
     JwtTokenProvider jwtUtil;
     @Autowired
     ResponeService responeService;

     @Autowired
     KafkaProducerService kafkaProducerService;

     @Autowired
     ServiceRepository serviceRepository;

     @Autowired
     CustomerRepository customerRepository;

     @Autowired
     JwtTokenProvider jwtTokenProvider;
     public ResponseEntity<Object> serviceList() throws Exception {
          ServiceEntity serviceEntity =  new ServiceEntity();
          serviceEntity.setSeviceName("TEST TERVICE");
          serviceEntity.setPrices(80.0000);
          serviceRepository.saveAndFlush(serviceEntity);
          return new ResponseEntity<>(responeService.GetServiceList(true, "Successfully Load Data Service", serviceRepository.findAll(), (long) serviceRepository.findAll().size()), HttpStatus.OK);
     }

     public ResponseEntity<Object> inquiryService(javax.servlet.http.HttpServletRequest request, InquiryRequest inquiryRequest) throws Exception {
          try{
               String claims = jwtUtil.resolveClaims(request);
               Optional<ServiceEntity> serviceEntity =  serviceRepository.findById(UUID.fromString(inquiryRequest.getIdService()));

               Optional<CustomerEntity> customerEntity =  customerRepository.findById(UUID.fromString(claims));


               if(serviceEntity == null)return new ResponseEntity<>(responeService.ServiceObject(true, "Service Not Found", null), HttpStatus.NOT_FOUND);

               PaymentOtp paymentOtp = new PaymentOtp();
               String otp = otpUtil.generatedOtp();    
               RegisterRespone registerRespone = new RegisterRespone();
               String token = jwtTokenProvider.generateToken(otp, 180000);
               registerRespone.setToken(token);
               registerRespone.setExpirationIn(180000);
               paymentOtp.setIdCustomer(claims);
               paymentOtp.setEmail(customerEntity.get().getEmail());
               paymentOtp.setToken(token);
               paymentOtp.setOtp(otp);
               paymentOtp.setIdService(inquiryRequest.getIdService());
               paymentOtp.setPrices(serviceEntity.get().getPrices());
               Gson gson = new Gson();
               String jString = gson.toJson(paymentOtp);
               kafkaProducerService.sendMessage("PAYMENT", jString);

               return new ResponseEntity<>(responeService.ServiceObject(true, "Successfully inquiry", registerRespone), HttpStatus.CREATED);
          }catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("inquiry failed !");
          } 

          
     }
     public ResponseEntity<Object> paymentService(InfoPaymentRequest infoPaymentRequest) throws Exception {
          String apiUrl = "http://localhost:8002/payment/v1/info";
          Map<String, Object> requestBody = new HashMap<>();
          requestBody.put("otpNumber", infoPaymentRequest.getOtpNumber());
          requestBody.put("token", infoPaymentRequest.getToken());
          String requestBodyJson = convertMapToJson(requestBody);

        // Create HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Create HttpRequest with POST method and set the request body
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                .build();

        // Send the request and retrieve the response
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Print the response status code and body
          System.out.println("Response Code: " + response.statusCode());
          System.out.println("Response Body: " + response.body());
          if(response.body() == null){
               return new ResponseEntity<>(responeService.ServiceObject(false, "Payment failed ", null), HttpStatus.BAD_REQUEST);  

          }
          return new ResponseEntity<>(responeService.ServiceObject(true, "Successfully Payment", null), HttpStatus.CREATED);
     }
     private static String convertMapToJson(Map<String, Object> map) {
          // Implement your logic to convert a Map to JSON
          // You can use libraries like Jackson, Gson, or org.json depending on your project setup
          // Here, we're using a simple approach for demonstration purposes
          StringBuilder jsonBuilder = new StringBuilder("{");
          for (Map.Entry<String, Object> entry : map.entrySet()) {
              jsonBuilder.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");
          }
          if (jsonBuilder.length() > 1) {
              jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); // Remove the trailing comma
          }
          jsonBuilder.append("}");
          return jsonBuilder.toString();
      }
     
}
