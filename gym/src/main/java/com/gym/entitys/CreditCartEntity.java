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
@Table(name = "CREDIT_CART")
public class CreditCartEntity {
     @Id
     @Column(name = "ID_CREDIT_CART", nullable = false)
     @GeneratedValue
     private UUID idCreditCart;

     @OneToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID_CUSTOMER")
     private CustomerEntity customer;

     @Column(unique=true, name = "CART_NUMBER",  nullable = false)
     private String cartNumber;

     @Column(unique=true, name = "CVV", nullable = false)
     private String cvv;

     @Column(name = "CART_NAME", nullable = false, length = 70)
     private String cartName;

     @Column(name = "EXPIRED", nullable = false)
     private String expired;

     @Column(name = "CREATED_AT", updatable = false)
     @CreationTimestamp
     private Date createdAt;

     @Column(name = "UPDATED_AT")
     @UpdateTimestamp
     private Date updatedAt;

     
}
