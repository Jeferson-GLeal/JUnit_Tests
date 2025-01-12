package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes unitario de saudacao")
class SaudacaoUtilTest {

    @Test
    @DisplayName("Deve saudar com bom dia")
    public void saudarBomDia() {
        int horaValida = 9;
        String saudacao = SaudacaoUtil.saudar(horaValida);
        assertEquals("Bom dia", saudacao, "Saudacao invalida");
    }

    @Test
    @DisplayName("Deve saudar com boa tarde")
    public void saudarBoaTarde() {
        //Arrange
        int horaValida = 14;

        //Act
        String saudacao = SaudacaoUtil.saudar(horaValida);

        //Assert
        assertEquals("Boa tarde", saudacao, "Saudacao invalida");
    }

    @Test
    @DisplayName("Deve saudar com boa noite")
    public void saudarBoaNoite() {
        //Arrange
        int horaValida = 19;

        //Act
        String saudacao = SaudacaoUtil.saudar(horaValida);

        //Assert
        assertEquals("Boa noite", saudacao, "Saudacao invalida");
    }

    @Test
    @DisplayName("Deve lancar uma Exception")
    public void deveLancarException() {

        //Arrange
        int horaInvalida = -10;

        //Act
        Executable executable = () -> SaudacaoUtil.saudar(horaInvalida);

        //Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Hora invalida", e.getMessage());
    }

    @Test
    @DisplayName("Nao deve lancar uma Exception")
    public void naoDeveLancarException() {

        //Arrange
        int horaValida = 0;

        //Act
        Executable executable = () -> SaudacaoUtil.saudar(horaValida);

        //Assert
        assertDoesNotThrow(executable);
    }
}