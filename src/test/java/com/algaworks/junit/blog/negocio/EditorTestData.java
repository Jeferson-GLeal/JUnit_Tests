package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;

import java.math.BigDecimal;

public class EditorTestData {

    private EditorTestData() {
    }

    public static Editor.Builder umEditorNovo() {
        return Editor.builder()
                .withName("Jeferson")
                .withEmail("jeferson.gleal@outlook.com")
                .comValorPagoPorPalavra(BigDecimal.TEN)
                .withPremium(true);
    }

    public static Editor.Builder umEditorExistente() {
        return umEditorNovo().withId(1L);
    }

    public static Editor.Builder umEditorComIdInexistente() {
        return umEditorNovo().withId(99L);
    }

}
