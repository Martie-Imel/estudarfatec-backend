package br.com.estudarfatec;

import br.com.estudarfatec.model.Tarefa;
import br.com.estudarfatec.repository.TarefaRepositoryMemoria;
import br.com.estudarfatec.service.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ENTREGA 8 - Qualidade e Testes
 *
 * Testes unitários do TarefaService com JUnit 5.
 *
 * Usa TarefaRepositoryMemoria como dependência real
 * (sem mock, para manter simples no nível da Fatec).
 */
@DisplayName("Testes unitários do TarefaService")
class TarefaServiceTest {

    private TarefaService tarefaService;

    /**
     * @BeforeEach executa antes de cada teste,
     * garantindo um repositório limpo e estado isolado.
     */
    @BeforeEach
    void configurar() {
        tarefaService = new TarefaService(new TarefaRepositoryMemoria());
    }

    // -------------------------------------------------------
    // Cadastrar
    // -------------------------------------------------------

    @Test
    @DisplayName("Deve cadastrar tarefa válida e gerar ID automaticamente")
    void deve_cadastrarTarefa_comIdGerado() {
        Tarefa tarefa = new Tarefa(null, "Estudar Factory", "Implementar padrão Factory");

        Tarefa salva = tarefaService.cadastrar(tarefa);

        assertNotNull(salva.getId(), "ID deve ser gerado automaticamente");
        assertEquals("Estudar Factory", salva.getTitulo());
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar tarefa sem título")
    void deve_lancarExcecao_quandoTituloVazio() {
        Tarefa tarefa = new Tarefa(null, "", "descrição");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> tarefaService.cadastrar(tarefa)
        );

        assertTrue(ex.getMessage().contains("título"),
                "Mensagem deve mencionar o campo 'título'");
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar tarefa com título nulo")
    void deve_lancarExcecao_quandoTituloNulo() {
        Tarefa tarefa = new Tarefa(null, null, "descrição");

        assertThrows(IllegalArgumentException.class,
                () -> tarefaService.cadastrar(tarefa));
    }

    @Test
    @DisplayName("Deve lançar exceção quando título ultrapassa 100 caracteres")
    void deve_lancarExcecao_quandoTituloMuitoLongo() {
        String tituloLongo = "A".repeat(101);
        Tarefa tarefa = new Tarefa(null, tituloLongo, "desc");

        assertThrows(IllegalArgumentException.class,
                () -> tarefaService.cadastrar(tarefa));
    }

    // -------------------------------------------------------
    // Listar
    // -------------------------------------------------------

    @Test
    @DisplayName("Deve retornar lista vazia quando não há tarefas")
    void deve_retornarListaVazia_quandoNaoHaTarefas() {
        List<Tarefa> tarefas = tarefaService.listarTodas();

        assertNotNull(tarefas);
        assertTrue(tarefas.isEmpty());
    }

    @Test
    @DisplayName("Deve listar todas as tarefas cadastradas")
    void deve_listarTodasAsTarefas() {
        tarefaService.cadastrar(new Tarefa(null, "Tarefa 1", null));
        tarefaService.cadastrar(new Tarefa(null, "Tarefa 2", null));
        tarefaService.cadastrar(new Tarefa(null, "Tarefa 3", null));

        List<Tarefa> tarefas = tarefaService.listarTodas();

        assertEquals(3, tarefas.size());
    }

    // -------------------------------------------------------
    // Buscar por ID
    // -------------------------------------------------------

    @Test
    @DisplayName("Deve encontrar tarefa pelo ID correto")
    void deve_encontrarTarefa_peloId() {
        Tarefa salva = tarefaService.cadastrar(new Tarefa(null, "Tarefa X", null));

        Tarefa encontrada = tarefaService.buscarPorId(salva.getId());

        assertEquals(salva.getId(), encontrada.getId());
        assertEquals("Tarefa X", encontrada.getTitulo());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar ID inexistente")
    void deve_lancarExcecao_quandoIdNaoExiste() {
        assertThrows(IllegalArgumentException.class,
                () -> tarefaService.buscarPorId(999L));
    }

    // -------------------------------------------------------
    // Concluir
    // -------------------------------------------------------

    @Test
    @DisplayName("Deve marcar tarefa como concluída")
    void deve_concluirTarefa_comSucesso() {
        Tarefa salva = tarefaService.cadastrar(new Tarefa(null, "Tarefa", null));

        Tarefa concluida = tarefaService.concluir(salva.getId());

        assertTrue(concluida.isConcluida());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar concluir tarefa já concluída")
    void deve_lancarExcecao_quandoTarefaJaConcluida() {
        Tarefa salva = tarefaService.cadastrar(new Tarefa(null, "Tarefa", null));
        tarefaService.concluir(salva.getId());

        // Segunda tentativa deve falhar
        assertThrows(IllegalStateException.class,
                () -> tarefaService.concluir(salva.getId()));
    }

    // -------------------------------------------------------
    // Deletar
    // -------------------------------------------------------

    @Test
    @DisplayName("Deve deletar tarefa existente")
    void deve_deletarTarefa_comSucesso() {
        Tarefa salva = tarefaService.cadastrar(new Tarefa(null, "Tarefa", null));

        tarefaService.deletar(salva.getId());

        assertThrows(IllegalArgumentException.class,
                () -> tarefaService.buscarPorId(salva.getId()));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar ID inexistente")
    void deve_lancarExcecao_aoDeletarIdInexistente() {
        assertThrows(IllegalArgumentException.class,
                () -> tarefaService.deletar(999L));
    }
}
