package com.gym.models.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class VerifyAccountRequest extends DeviceInfo{
     @NotBlank(message = "Email is required")
     @Email(message = "Invalid Email format")
     @Size(max = 50, message = "Email must be at most 50 characters")
     private String Email;
     @NotBlank(message = "Otp is required")
     @Size(max = 6, min = 6, message = "Otp must be at most 650 characters")
     private String Otp;
     @NotBlank(message = "Token is required")
     private String Token;
     
     public String getEmail() {
          return Email;
     }
     public void setEmail(String email) {
          Email = email;
     }
     public String getOtp() {
          return Otp;
     }
     public void setOtp(String otp) {
          Otp = otp;
     }
     public String getToken() {
          return Token;
     }
     public void setToken(String token) {
          Token = token;
     }
     
}
