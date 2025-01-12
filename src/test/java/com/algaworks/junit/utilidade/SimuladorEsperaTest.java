package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class SimuladorEsperaTest {

    @Test
    //@Disabled("Este teste nao eh mais necessario")
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "DEV")
    void deveEsperarENaoDarTimeout() {
        //assertTimeout(Duration.ofSeconds(1), () -> SimuladorEspera.esperar(Duration.ofSeconds(10)));
        //Assumptions.assumeTrue("PROD".equals(System.getenv("ENV")), () -> "Abortando Teste: Nao deve ser executado em PROD");
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> SimuladorEspera.esperar(Duration.ofMillis(10)));
    }
}