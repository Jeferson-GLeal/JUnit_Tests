package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.ArmazenamentoEditorFixoEmMemoria;
import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CadastroEditorComMockTest {

    CadastroEditor cadastroEditor;
    Editor editor;

    @BeforeEach
    void init() {
        editor = new Editor(null, "Jeferson", "jeferson.gleal@outlook.com", BigDecimal.TEN, true);

        ArmazenamentoEditor armazenamentoEditor = Mockito.mock(ArmazenamentoEditor.class);
        Mockito.when(armazenamentoEditor.salvar(editor))
                .thenReturn(new Editor(1L, "Jeferson", "jeferson.gleal@outlook.com", BigDecimal.TEN, true));

        GerenciadorEnvioEmail gerenciadorEnvioEmail = Mockito.mock(GerenciadorEnvioEmail.class);

        cadastroEditor = new CadastroEditor(armazenamentoEditor, gerenciadorEnvioEmail);
    }


    @Test
    void Dado_ume_ditor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro() {
        Editor editoSalvo = cadastroEditor.criar(editor);
        Assertions.assertEquals(1L, editoSalvo.getId());
    }
}