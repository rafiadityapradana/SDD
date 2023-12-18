package com.gym.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdatePasswordrequest {
     @NotBlank(message = "Old Password is required")
     @Size(min = 6, message = "Old Password must be at most min 6 characters")
     private String oldPassword;
     @NotBlank(message = "New Password is required")
     @Size(min = 6, message = "New Password must be at most min 6 characters")
     private String newPassword;
     @NotBlank(message = "Confirm Password is required")
     @Size(min = 6, message = "Confirm Password must be at most min 6 characters")
     private String confirmPassword;
     public String getOldPassword() {
          return oldPassword;
     }
     public void setOldPassword(String oldPassword) {
          this.oldPassword = oldPassword;
     }
     public String getNewPassword() {
          return newPassword;
     }
     public void setNewPassword(String newPassword) {
          this.newPassword = newPassword;
     }
     public String getConfirmPassword() {
          return confirmPassword;
     }
     public void setConfirmPassword(String confirmPassword) {
          this.confirmPassword = confirmPassword;
     }
}
