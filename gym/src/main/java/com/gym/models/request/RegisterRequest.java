package com.gym.models.request;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class RegisterRequest extends DeviceInfo{
     @NotBlank(message = "Email is required")
     @Email(message = "Invalid Email format")
     @Size(max = 50, message = "Email must be at most 50 characters")
     private String email;
     @NotBlank(message = "Password is required")
     @Size( min = 6, message = "Password must be at most min 6 characters")
     private String password;
     @NotBlank(message = "Name is required")
     @Size(max = 70, message = "Name must be at most 70 characters")
     private String name;
     @NotBlank(message = "Phone Number is required")
     @Size(max = 13, min = 11, message = "Phone Number must be at most max 13 and min 11 characters")
     private String phoneNumber;
     @NotBlank(message = "Card Number is required")
     @Size(max = 16, min = 16, message = "Card Number must be at most 16 characters")
     private String cardNumber;
     @NotBlank(message = "CVV Number is required")
     @Size(max = 3, min = 3, message = "CVV Number must be at most 3 characters")
     private String cvv;
     @NotBlank(message = "Expired Date is required")
     @Size(max = 4, min=4, message = "Phone Number must be at most 4 characters")
     private String expiredDate;
     @NotBlank(message = "Cardholder Name is required")
     @Size(max = 70, message = "Cardholder Name must be at most 70 characters")
     private String cardholderName;
     public String getEmail() {
          return email;
     }
     public void setEmail(String email) {
          this.email = email;
     }
     public String getPassword() {
          return password;
     }
     public void setPassword(String password) {
          this.password = password;
     }
     public String getName() {
          return name;
     }
     public void setName(String name) {
          this.name = name;
     }
     public String getPhoneNumber() {
          return phoneNumber;
     }
     public void setPhoneNumber(String phoneNumber) {
          this.phoneNumber = phoneNumber;
     }
     public String getCardNumber() {
          return cardNumber;
     }
     public void setCardNumber(String cardNumber) {
          this.cardNumber = cardNumber;
     }
     public String getCvv() {
          return cvv;
     }
     public void setCvv(String cvv) {
          this.cvv = cvv;
     }
     public String getExpiredDate() {
          return expiredDate;
     }
     public void setExpiredDate(String expiredDate) {
          this.expiredDate = expiredDate;
     }
     public String getCardholderName() {
          return cardholderName;
     }
     public void setCardholderName(String cardholderName) {
          this.cardholderName = cardholderName;
     }
     
     
     
}
