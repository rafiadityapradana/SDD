package com.gym.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


public class DeviceInfo {
     @NotBlank(message = "DeviceName is required")
     private String deviceName;
     @NotBlank(message = "IpAddress is required")
     @Pattern(regexp = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                     + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                     + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                     + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$",
            message = "Invalid IpAddress format")
     private String ipAddress;
     public String getIpAddress() {
          return ipAddress;
     }
     public void setIpAddress(String ipAddress) {
          this.ipAddress = ipAddress;
     }
     public String getDeviceName() {
          return deviceName;
     }
     public void setDeviceName(String deviceName) {
          this.deviceName = deviceName;
     }
    
}
