package com.gym.models.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RequestResetPassword extends DeviceInfo{
     @NotBlank(message = "Email is required")
     @Email(message = "Invalid Email format")
     @Size(max = 50, message = "Email must be at most 50 characters")
     private String email;

     public String getEmail() {
          return email;
     }

     public void setEmail(String email) {
          this.email = email;
     }
     
}
