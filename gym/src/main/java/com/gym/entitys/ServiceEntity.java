package com.gym.entitys;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SERVICE")
public class ServiceEntity {

     @Id
     @Column(name = "ID_SEREVICE", nullable = false)
     @GeneratedValue
     private UUID idService;

     @Column(name = "SERVICE_NAME", nullable = false)
     private String seviceName;

     @Column(name = "PRICES", nullable = false)
     private Double prices;

     @Column(name = "CREATED_AT", updatable = false)
     @CreationTimestamp
     private Date createdAt;

     @Column(name = "UPDATED_AT")
     @UpdateTimestamp
     private Date updatedAt;
     
     
}
