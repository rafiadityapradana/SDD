package com.gym.models.kafka;

public class PaymentOtp {
     
     private String idCustomer;
     private String email;
     private String otp;
     private String token;
     private String idService;
     private Double prices;
     public String getIdCustomer() {
          return idCustomer;
     }
     public void setIdCustomer(String idCustomer) {
          this.idCustomer = idCustomer;
     }
     public String getToken() {
          return token;
     }
     public void setToken(String token) {
          this.token = token;
     }
     public String getIdService() {
          return idService;
     }
     public void setIdService(String idService) {
          this.idService = idService;
     }
     public Double getPrices() {
          return prices;
     }
     public void setPrices(Double prices) {
          this.prices = prices;
     }
     public String getOtp() {
          return otp;
     }
     public void setOtp(String otp) {
          this.otp = otp;
     }
     public String getEmail() {
          return email;
     }
     public void setEmail(String email) {
          this.email = email;
     }
}
