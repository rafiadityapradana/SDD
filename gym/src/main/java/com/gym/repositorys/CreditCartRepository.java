package com.gym.repositorys;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gym.entitys.CreditCartEntity;


@Repository
public interface CreditCartRepository extends JpaRepository<CreditCartEntity, UUID> {

     @Query(value = "SELECT * FROM CREDIT_CART WHERE CUSTOMER_ID =?1", nativeQuery = true)
     CreditCartEntity findByCustomer(UUID fieldName);
}