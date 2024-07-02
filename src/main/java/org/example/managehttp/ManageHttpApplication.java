package org.example.managehttp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = "com.example.lattice")
public class ManageHttpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageHttpApplication.class, args);
    }

}
