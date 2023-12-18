package com.gym.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class InfoPaymentRequest extends DeviceInfo{
     @NotBlank(message = "Otp is required")
     @Size(max = 6, min = 6, message = "Otp must be at most 650 characters")
     private String otpNumber;
     @NotBlank(message = "Token is required")
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
