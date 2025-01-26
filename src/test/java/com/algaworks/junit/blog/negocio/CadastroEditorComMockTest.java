package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.ArmazenamentoEditorFixoEmMemoria;
import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CadastroEditorComMockTest {

    Editor editor;

    @Mock
    ArmazenamentoEditor armazenamentoEditor;

    @Mock
    GerenciadorEnvioEmail gerenciadorEnvioEmail;

    @InjectMocks
    CadastroEditor cadastroEditor;

    @BeforeEach
    void init() {
        editor = new Editor(null, "Jeferson", "jeferson.gleal@outlook.com", BigDecimal.TEN, true);

        Mockito.when(armazenamentoEditor.salvar(Mockito.any(Editor.class)))
                //.thenReturn(new Editor(1L, "Jeferson", "jeferson.gleal@outlook.com", BigDecimal.TEN, true));
                .thenAnswer(invocacao -> {
                    Editor editorPassado = invocacao.getArgument(0, Editor.class);
                    editorPassado.setId(1l);
                    return editorPassado;
                });
    }

    @Test
    void Dado_ume_ditor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro() {
        Editor editoSalvo = cadastroEditor.criar(editor);
        Assertions.assertEquals(1L, editoSalvo.getId());
    }

    @Test
    void Dado_um_editor_valido_Quando_criar_Entao_deve_chamar_metodo_salvar_do_armazenamento() {
        cadastroEditor.criar(editor);
        Mockito.verify(armazenamentoEditor, Mockito.times(1))
                .salvar(Mockito.eq(editor));
    }

    @Test
    void Dado_um_editor_valido_Quando_criar_e_lancar_exception_ao_salvar_Entao_nao_deve_enviar_email() {
        Mockito.when(armazenamentoEditor.salvar(editor)).thenThrow(new RuntimeException());
        assertAll( "Nao Deve enviar email quando lancar exception do armazenamento",
                () -> assertThrows(RuntimeException.class, () -> cadastroEditor.criar(editor)),
                () -> Mockito.verify(gerenciadorEnvioEmail, Mockito.never()).enviarEmail(Mockito.any())
        );
    }
}