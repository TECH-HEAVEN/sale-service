package com.icebear2n2.saleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SaleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaleServiceApplication.class, args);
    }

}
