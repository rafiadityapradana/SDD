package com.gym.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreditCartRequest extends DeviceInfo{
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
