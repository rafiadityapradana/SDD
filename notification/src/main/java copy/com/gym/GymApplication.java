package com.gym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class GymApplication {
    public static void main( String[] args )
    {
        SpringApplication.run(GymApplication.class, args);
        

    }

}
