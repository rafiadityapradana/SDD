package com.gym.entitys;
import lombok.*;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMER_OTP")
public class CustomerOtpEntity {

     @Id
     @Column(name = "ID_CUSTOMER_OTP", nullable = false)
     @GeneratedValue
     private UUID idCustoemrOtp;

     @Column(name = "CUSTOMER_ID", nullable = false)
     private UUID customerId;

     @Column(unique=true, name = "OTP_NUMBER",  nullable = false, length = 8)
     private String otpNumber;

     @Column(name = "IP_ADDRESS",  nullable = false, length = 20)
     private String ipAddress;

     @Column(name = "DEVICE_NAME",  nullable = false)
     private String deviceName;

     @Enumerated(EnumType.STRING)
     @Column(name = "OTP_TYPE", nullable = false)
     private OtpType otpType;

     @Column(unique=true, name = "OTP_TOKEN", nullable = false)
     private String otpToken;

     @Column(name = "OTP_STATUS", nullable = false)
     private boolean otpStatus = Boolean.FALSE;

     @Column(name = "CREATED_AT", updatable = false)
     @CreationTimestamp
     private Date createdAt;

     @Column(name = "UPDATED_AT")
     @UpdateTimestamp
     private Date updatedAt;
     

     
}


