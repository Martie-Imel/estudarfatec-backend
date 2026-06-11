package br.com.estudarfatec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação EstudarFatec.
 * Ponto de entrada da API REST hospedada no Render.
 */
@SpringBootApplication
public class EstudarFatecApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstudarFatecApplication.class, args);
    }
}
