package com.gym.models.respone;



public class RegisterRespone {
     private Integer expirationIn;
     private String token;
     public Integer getExpirationIn() {
          return expirationIn;
     }
     public void setExpirationIn(Integer expirationInMs) {
          this.expirationIn = expirationInMs;
     }
     public String getToken() {
          return token;
     }
     public void setToken(String token) {
          this.token = token;
     }
}
