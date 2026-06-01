package com.tienda.sipv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del backend SIPV.
 * Arranca el servidor Spring Boot con Tomcat embebido.
 */
@SpringBootApplication
public class SipvApplication {

    public static void main(String[] args) {
        SpringApplication.run(SipvApplication.class, args);
    }
}
