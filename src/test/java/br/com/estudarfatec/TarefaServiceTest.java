package br.com.estudarfatec;

import br.com.estudarfatec.model.Tarefa;
import br.com.estudarfatec.repository.DisciplinaRepositoryMemoria;
import br.com.estudarfatec.repository.TarefaRepositoryMemoria;
import br.com.estudarfatec.service.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes unitários do TarefaService")
class TarefaServiceTest {

    private TarefaService tarefaService;

    @BeforeEach
    void configurar() {
        tarefaService = new TarefaService(
                new TarefaRepositoryMemoria(),
                new DisciplinaRepositoryMemoria());
    }

    @Test @DisplayName("Deve cadastrar tarefa com ID gerado")
    void deve_cadastrar_com_id() {
        Tarefa salva = tarefaService.cadastrar(new Tarefa(null, "Estudar Factory", null));
        assertNotNull(salva.getId());
        assertEquals("Estudar Factory", salva.getTitulo());
    }

    @Test @DisplayName("Deve lançar exceção com título vazio")
    void deve_rejeitar_titulo_vazio() {
        assertThrows(IllegalArgumentException.class,
                () -> tarefaService.cadastrar(new Tarefa(null, "", "desc")));
    }

    @Test @DisplayName("Deve lançar exceção com título nulo")
    void deve_rejeitar_titulo_nulo() {
        assertThrows(IllegalArgumentException.class,
                () -> tarefaService.cadastrar(new Tarefa(null, null, "desc")));
    }

    @Test @DisplayName("Deve lançar exceção quando título > 100 chars")
    void deve_rejeitar_titulo_longo() {
        assertThrows(IllegalArgumentException.class,
                () -> tarefaService.cadastrar(new Tarefa(null, "A".repeat(101), "desc")));
    }

    @Test @DisplayName("Deve retornar lista vazia inicialmente")
    void deve_listar_vazio() {
        assertTrue(tarefaService.listarTodas().isEmpty());
    }

    @Test @DisplayName("Deve listar todas as tarefas cadastradas")
    void deve_listar_todas() {
        tarefaService.cadastrar(new Tarefa(null, "T1", null));
        tarefaService.cadastrar(new Tarefa(null, "T2", null));
        assertEquals(2, tarefaService.listarTodas().size());
    }

    @Test @DisplayName("Deve encontrar tarefa pelo ID")
    void deve_buscar_por_id() {
        Tarefa salva = tarefaService.cadastrar(new Tarefa(null, "Tarefa X", null));
        assertEquals("Tarefa X", tarefaService.buscarPorId(salva.getId()).getTitulo());
    }

    @Test @DisplayName("Deve lançar exceção para ID inexistente")
    void deve_falhar_id_invalido() {
        assertThrows(IllegalArgumentException.class, () -> tarefaService.buscarPorId(999L));
    }

    @Test @DisplayName("Deve marcar tarefa como concluída")
    void deve_concluir() {
        Tarefa salva = tarefaService.cadastrar(new Tarefa(null, "Tarefa", null));
        assertTrue(tarefaService.concluir(salva.getId()).isConcluida());
    }

    @Test @DisplayName("Deve lançar exceção ao concluir tarefa já concluída")
    void deve_falhar_concluir_duplicado() {
        Tarefa salva = tarefaService.cadastrar(new Tarefa(null, "Tarefa", null));
        tarefaService.concluir(salva.getId());
        assertThrows(IllegalStateException.class, () -> tarefaService.concluir(salva.getId()));
    }

    @Test @DisplayName("Deve deletar tarefa existente")
    void deve_deletar() {
        Tarefa salva = tarefaService.cadastrar(new Tarefa(null, "Tarefa", null));
        tarefaService.deletar(salva.getId());
        assertThrows(IllegalArgumentException.class,
                () -> tarefaService.buscarPorId(salva.getId()));
    }

    @Test @DisplayName("Deve lançar exceção ao deletar ID inexistente")
    void deve_falhar_deletar_invalido() {
        assertThrows(IllegalArgumentException.class, () -> tarefaService.deletar(999L));
    }

    @Test @DisplayName("Deve lançar exceção ao vincular disciplina inexistente")
    void deve_rejeitar_disciplina_invalida() {
        Tarefa salva = tarefaService.cadastrar(new Tarefa(null, "Tarefa", null));
        assertThrows(IllegalArgumentException.class,
                () -> tarefaService.vincularDisciplina(salva.getId(), 999L));
    }
}
