package com.kph.demo.reactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class WebClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebClientApplication.class, args);
    }
//    @Bean
//    public SecurityWebFilterChain functionalValidationsSpringSecurityFilterChain(ServerHttpSecurity http) {
//        http.authorizeExchange()
//                .anyExchange()
//                .permitAll();
//        http.csrf().disable();
//        return http.build();
//    }
}
