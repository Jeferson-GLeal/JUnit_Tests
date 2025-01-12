package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SaudacaoUtilTest {

    @Test
    public void Dado_um_horario_matuino_Quando_saudar_Entao_deve_retornar_bom_dia() {
        int horaValida = 9;
        String saudacao = SaudacaoUtil.saudar(horaValida);
        assertEquals("Bom dia", saudacao, "Saudacao invalida");
    }

    @Test
    public void Dado_um_horario_vespertino_Quando_saudar_Entao_deve_retornar_boa_tarde() {
        //Arrange
        int horaValida = 14;

        //Act
        String saudacao = SaudacaoUtil.saudar(horaValida);

        //Assert
        assertEquals("Boa tarde", saudacao, "Saudacao invalida");
    }

    @Test
    public void Dado_um_horario_noturno_Quando_saudar_Entao_deve_retornar_boa_noite() {
        //Arrange
        int horaValida = 19;

        //Act
        String saudacao = SaudacaoUtil.saudar(horaValida);

        //Assert
        assertEquals("Boa noite", saudacao, "Saudacao invalida");
    }

    @Test
    public void Dado_uma_hora_invalida_Quando_saudar_Entao_deve_lancar_exception() {

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
    public void Dado_uma_hora_valida_Quando_saudar_Entao_nao_deve_lancar_exception() {

        //Arrange
        int horaValida = 0;

        //Act
        Executable executable = () -> SaudacaoUtil.saudar(horaValida);

        //Assert
        assertDoesNotThrow(executable);
    }
}