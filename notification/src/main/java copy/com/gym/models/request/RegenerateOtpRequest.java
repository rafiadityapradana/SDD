package com.gym.models.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class RegenerateOtpRequest extends DeviceInfo{
     @NotBlank(message = "Email is required")
     @Email(message = "Invalid Email format")
     @Size(max = 50, message = "Email must be at most 50 characters")
     private String Email;
     @NotBlank(message = "Token is required")
     private String Token;
     @NotBlank(message = "TypeOtp is require")
     private String typeOtp;
     
     public String getTypeOtp() {
          return typeOtp;
     }
     public void setTypeOtp(String typeOtp) {
          this.typeOtp = typeOtp;
     }
     public String getEmail() {
          return Email;
     }
     public void setEmail(String email) {
          Email = email;
     }
     public String getToken() {
          return Token;
     }
     public void setToken(String token) {
          Token = token;
     }
     
}
