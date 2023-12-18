package com.gym.entitys;
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
@Table(name = "CUSTOMER")
public class CustomerEntity {
     @Id
     @Column(name = "ID_CUSTOMER", nullable = false)
     @GeneratedValue
     private UUID idCustomer;

     @Column(name = "CUSTOMER_NAME", nullable = false, length = 70)
     private String customerName;

     @Column(unique=true, name = "EMAIL", nullable = false, length = 70)
     private String email;

     @Column(name = "PASSWORD", nullable = false)
     private String password;

     @Column(unique=true, name = "PHONE_NUMBER", nullable = false, length = 13)
     private String phoneNumber;

     @Column(name = "IS_ACTIVE", nullable = false)
     private boolean isActive = Boolean.FALSE;

     @Column(name = "CREATED_AT", updatable = false)
     @CreationTimestamp
     private Date createdAt;

     @Column(name = "UPDATED_AT")
     @UpdateTimestamp
     private Date updatedAt;


     
}
