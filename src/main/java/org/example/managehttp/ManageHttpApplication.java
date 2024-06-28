package org.example.managehttp;

import org.example.managehttp.utils.SSLUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.lattice")
public class ManageHttpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageHttpApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        SSLUtils.disableSSLVerification();
        return new RestTemplate();
    }
}
