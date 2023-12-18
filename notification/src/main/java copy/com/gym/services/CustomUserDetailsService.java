package com.gym.services;
import com.gym.entitys.CustomerEntity;
import com.gym.repositorys.CustomerRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

     CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
          System.out.println(" email "+ email);
        CustomerEntity user = customerRepository.findByEmail(email);
       
        System.out.println(" user "+ user.getCustomerName());
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .build();
        return userDetails;
    }
}
