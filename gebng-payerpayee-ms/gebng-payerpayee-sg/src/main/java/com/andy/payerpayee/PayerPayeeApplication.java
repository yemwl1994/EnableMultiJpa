package com.andy.payerpayee;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.andy"})
public class PayerPayeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayerPayeeApplication.class, args);
    }
}
