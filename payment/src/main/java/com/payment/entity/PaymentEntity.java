package com.payment.entity;

import lombok.*;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PAYMENT_OTP")
public class PaymentEntity {
     @Id
     @Column(name = "ID_PAYMENT", nullable = false)
     @GeneratedValue
     private UUID idPayment;
     @Column(name = "ID_CUSTOMER", nullable = false)
     private UUID idCustomer;
     @Column(unique=true, name = "EMAIL", nullable = false, length = 70)
     private String email;
     @Column(unique=true, name = "OTP_NUMBER",  nullable = false, length = 8)
     private String otpNumber;
     @Column(unique=true, name = "TOKEN",  nullable = false)
     private String token;
     @Column(name = "ID_SERVICE", nullable = false)
     private UUID idService;
     @Column(name = "PRICES",  nullable = false)
     private Double prices;
     @Column(name = "PAYMENT_STATUS", nullable = false)
     private boolean paymentStatus = Boolean.FALSE;
     @Column(name = "CREATED_AT", updatable = false)
     @CreationTimestamp
     private Date createdAt;
     @Column(name = "UPDATED_AT")
     @UpdateTimestamp
     private Date updatedAt;
}
