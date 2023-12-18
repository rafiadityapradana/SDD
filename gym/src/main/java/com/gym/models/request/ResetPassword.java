package com.gym.models.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ResetPassword {
     @NotBlank(message = "Email is required")
     @Email(message = "Invalid Email format")
     @Size(max = 50, message = "Email must be at most 50 characters")
     private String email;
     @NotBlank(message = "Token is required")
     private String token;
     
     @NotBlank(message = "Password is required")
     @Size(min = 6, message = "Password must be at most min 6 characters")
     private String password;

     public String getPassword() {
          return password;
     }
     public void setPassword(String password) {
          this.password = password;
     }
     @NotBlank(message = "Confirm Password is required")
     @Size(min = 6, message = "Confirm Password must be at most min 6 characters")
     private String confirmPassword;

     public String getConfirmPassword() {
          return confirmPassword;
     }
     public void setConfirmPassword(String confirmPassword) {
          this.confirmPassword = confirmPassword;
     }
     public String getEmail() {
          return email;
     }
     public void setEmail(String email) {
          this.email = email;
     }
     public String getToken() {
          return token;
     }
     public void setToken(String token) {
          this.token = token;
     }
    
    
}


