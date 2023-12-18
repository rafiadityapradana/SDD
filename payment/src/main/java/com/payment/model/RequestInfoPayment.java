package com.payment.model;

public class RequestInfoPayment {
     private String otpNumber;
     private String token;
     public String getOtpNumber() {
          return otpNumber;
     }
     public void setOtpNumber(String otpNumber) {
          this.otpNumber = otpNumber;
     }
     public String getToken() {
          return token;
     }
     public void setToken(String token) {
          this.token = token;
     }
}
