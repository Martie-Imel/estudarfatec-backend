package br.com.estudarfatec;

import br.com.estudarfatec.model.Tarefa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ENTREGA 8 - Qualidade e Testes
 *
 * Testes unitários da classe Tarefa com JUnit 5.
 *
 * Cada método @Test valida um comportamento específico.
 * Convenção de nomenclatura: deve_[resultado]_quando_[condição]
 */
@DisplayName("Testes unitários da classe Tarefa")
class TarefaTest {

    // -------------------------------------------------------
    // Testes de criação
    // -------------------------------------------------------

    @Test
    @DisplayName("Deve criar tarefa com todos os atributos corretamente")
    void deve_criarTarefa_comAtributosCorretos() {
        Tarefa tarefa = new Tarefa(1L, "Estudar POO", "Revisar herança e polimorfismo");

        assertEquals(1L, tarefa.getId());
        assertEquals("Estudar POO", tarefa.getTitulo());
        assertEquals("Revisar herança e polimorfismo", tarefa.getDescricao());
    }

    @Test
    @DisplayName("Deve criar tarefa com concluída = false por padrão")
    void deve_criarTarefa_naoConcluidaPorPadrao() {
        Tarefa tarefa = new Tarefa(1L, "Tarefa nova", "descrição");

        assertFalse(tarefa.isConcluida(),
                "Tarefa recém-criada deve estar não concluída");
    }

    @Test
    @DisplayName("Deve criar tarefa com construtor padrão sem lançar exceção")
    void deve_criarTarefa_comConstrutorPadrao() {
        assertDoesNotThrow(() -> {
            Tarefa tarefa = new Tarefa();
            assertNull(tarefa.getId());
            assertNull(tarefa.getTitulo());
        });
    }

    // -------------------------------------------------------
    // Testes de setters
    // -------------------------------------------------------

    @Test
    @DisplayName("Deve atualizar título via setter")
    void deve_atualizarTitulo_viaSetters() {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Novo título");

        assertEquals("Novo título", tarefa.getTitulo());
    }

    @Test
    @DisplayName("Deve marcar tarefa como concluída via setter")
    void deve_marcarConcluida_viaSetConcluida() {
        Tarefa tarefa = new Tarefa(1L, "Tarefa", "desc");
        assertFalse(tarefa.isConcluida());

        tarefa.setConcluida(true);

        assertTrue(tarefa.isConcluida());
    }

    // -------------------------------------------------------
    // Testes de toString
    // -------------------------------------------------------

    @Test
    @DisplayName("toString deve conter o título da tarefa")
    void deve_conterTitulo_emToString() {
        Tarefa tarefa = new Tarefa(1L, "Minha Tarefa", "desc");

        assertTrue(tarefa.toString().contains("Minha Tarefa"));
    }
}
