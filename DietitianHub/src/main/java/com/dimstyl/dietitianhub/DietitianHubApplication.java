package com.dimstyl.dietitianhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DietitianHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(DietitianHubApplication.class, args);
    }

}
