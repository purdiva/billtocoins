package com.purdiva.billtocoins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
@SpringBootApplication
public class BilltocoinsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BilltocoinsApplication.class, args);
    }

}
