package com.gym.models.request;

import javax.validation.constraints.NotBlank;

public class InquiryRequest extends DeviceInfo{
     @NotBlank(message = "IdService is required")
     private String idService;

     public String getIdService() {
          return idService;
     }

     public void setIdService(String idService) {
          this.idService = idService;
     }
}
