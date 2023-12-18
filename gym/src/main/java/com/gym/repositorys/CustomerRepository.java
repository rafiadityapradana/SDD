package com.gym.repositorys;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gym.entitys.CustomerEntity;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

     CustomerEntity findByEmail(String fieldName);

     void saveAndFlush(Optional<CustomerEntity> customerEntity);
     
}