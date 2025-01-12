package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaTest {

    @Test
    void assercaoAgrupada() {
        Pessoa pessoa = new Pessoa("Jeferson", "Leal");

        assertAll("Assercoes de Pessoa",
                ()-> assertEquals("Jeferson", pessoa.getNome()),
                ()-> assertEquals("Leal", pessoa.getSobrenome()));
    }
}