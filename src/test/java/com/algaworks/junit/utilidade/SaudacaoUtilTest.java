package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class SaudacaoUtilTest {

    @Test
    public void saudarBomDia() {
        int horaValida = 9;
        String saudacao = SaudacaoUtil.saudar(horaValida);
        assertEquals("Bom dia", saudacao, "Saudacao invalida");
    }

    @Test
    public void saudarBoaTarde() {
        //Arrange
        int horaValida = 14;

        //Act
        String saudacao = SaudacaoUtil.saudar(horaValida);

        //Assert
        assertEquals("Boa tarde", saudacao, "Saudacao invalida");
    }

    @Test
    public void saudarBoaNoite() {
        //Arrange
        int horaValida = 19;

        //Act
        String saudacao = SaudacaoUtil.saudar(horaValida);

        //Assert
        assertEquals("Boa noite", saudacao, "Saudacao invalida");
    }

    @Test
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
    public void naoDeveLancarException() {

        //Arrange
        int horaValida = 0;

        //Act
        Executable executable = () -> SaudacaoUtil.saudar(horaValida);

        //Assert
        assertDoesNotThrow(executable);
    }
}