package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CadastroEditorComMockTest {

    @Captor
    ArgumentCaptor<Mensagem> mensagemArgumentCaptor;

    @Mock
    ArmazenamentoEditor armazenamentoEditor;

    @Mock
    GerenciadorEnvioEmail gerenciadorEnvioEmail;

    @InjectMocks
    CadastroEditor cadastroEditor;

    @Nested
    class CadastroComEditorValido {

        @Spy
        Editor editor = new Editor(null, "Jeferson", "jeferson.gleal@outlook.com", BigDecimal.TEN, true);

        @BeforeEach
        void init() {
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
            Mockito.verify(armazenamentoEditor, times(1))
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

        @Test
        void Dado_um_editor_valido_quando_cadastrar_Entao_deve_enviar_email_com_destino_ao_editor() {
            Editor editorSalvo = cadastroEditor.criar(editor);

            Mockito.verify(gerenciadorEnvioEmail).enviarEmail(mensagemArgumentCaptor.capture());

            Mensagem mensagem = mensagemArgumentCaptor.getValue();

            assertEquals(editorSalvo.getEmail(), mensagem.getDestinatario());
        }

        @Test
        void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_verificar_o_email() {

            cadastroEditor.criar(editor);

            Mockito.verify(editor, Mockito.atLeast(1)).getEmail();
        }

        @Test
        void Dado_um_editor_com_email_existente_Quando_cadastrar_Entao_deve_lancar_exception(){
            Mockito.when(armazenamentoEditor.encontrarPorEmail("jeferson.gleal@outlook.com"))
                    .thenReturn(Optional.empty())
                    .thenReturn(Optional.of(editor));
            Editor editorComEmailExistente = new Editor(null, "Jeferson", "jeferson.gleal@outlook.com", BigDecimal.TEN, true);
            cadastroEditor.criar(editor);

            assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editorComEmailExistente));
        }

        @Test
        void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_apos_salvar() {
            cadastroEditor.criar(editor);
            InOrder inOrder = Mockito.inOrder(armazenamentoEditor, gerenciadorEnvioEmail);
            inOrder.verify(armazenamentoEditor, times(1)).salvar(editor);
            inOrder.verify(gerenciadorEnvioEmail, times(1)).enviarEmail(Mockito.any(Mensagem.class));
        }
    }

    @Nested
    class CadastroComEditorNull {

        @Test
        void Dados_um_editor_nulo_Quando_cadastrar_Entao_deve_lancar_exception() {
            assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null));
            Mockito.verify(armazenamentoEditor, Mockito.never()).salvar(Mockito.any());
            Mockito.verify(gerenciadorEnvioEmail, Mockito.never()).enviarEmail(Mockito.any());
        }
    }

    @Nested
    class EdicaoComEditorValido {

        @Spy
        Editor editor = new Editor(null, "Jeferson", "jeferson.gleal@outlook.com", BigDecimal.TEN, true);

        @BeforeEach
        void init() {
            Mockito.when(armazenamentoEditor.salvar(editor)).thenAnswer(invocacao -> invocacao.getArgument(0, Editor.class));
            Mockito.when(armazenamentoEditor.encontrarPorId(1L)).thenReturn(Optional.of(editor));
        }

        @Test
        void Dado_um_edito_valido_Quando_editar_Entao_deve_alterar_editor_salvo() {
            Editor editorAtualizado = new Editor(1L, "Jeferson Leal", "jeferson.leal@outlook.com", BigDecimal.ZERO, false);
            cadastroEditor.editar(editorAtualizado);
            Mockito.verify(editor, times(1)).atualizarComDados(editorAtualizado);

            InOrder inOrder = Mockito.inOrder(editor, armazenamentoEditor);
            inOrder.verify(editor).atualizarComDados(editorAtualizado);
            inOrder.verify(armazenamentoEditor).salvar(editor);
        }
    }
}