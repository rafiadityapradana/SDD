package com.gym.repositorys;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gym.entitys.CustomerOtpEntity;

@Repository
public interface CustomerOtpRepository extends JpaRepository<CustomerOtpEntity, UUID> {
     CustomerOtpEntity findByOtpNumber(String fieldName);

     @Query(value = "SELECT * FROM CUSTOMER_OTP WHERE OTP_TOKEN =?1", nativeQuery = true)
     CustomerOtpEntity findByToken(String fieldName);

     @Query(value = "SELECT * FROM CUSTOMER_OTP WHERE CUSTOMER_ID =?1", nativeQuery = true)
     CustomerOtpEntity findByCustomerId(UUID fieldName);
     

     
}
