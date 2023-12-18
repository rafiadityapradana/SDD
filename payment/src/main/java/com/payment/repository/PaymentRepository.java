package com.payment.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.payment.entity.PaymentEntity;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
     @Query(value = "SELECT * FROM PAYMENT_OTP WHERE OTP_NUMBER =?1 AND TOKEN =?2", nativeQuery = true)
     PaymentEntity findByOtpAndToken(String OTP, String Tokne);
}
