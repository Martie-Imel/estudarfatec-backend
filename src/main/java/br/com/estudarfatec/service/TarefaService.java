package br.com.estudarfatec.service;

import br.com.estudarfatec.model.Tarefa;
import br.com.estudarfatec.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ENTREGA 4 - Regras de Negócio
 *
 * Serviço responsável pelas regras de negócio das Tarefas.
 * Valida os dados ANTES de enviar ao repositório.
 *
 * Conceitos aplicados:
 * - Separação de responsabilidades (Service cuida de lógica, Repository cuida de dados)
 * - Injeção de dependência via construtor
 * - Lançamento de exceções para erros de validação
 * - @Service: informa ao Spring que esta classe contém lógica de negócio
 */
@Service
public class TarefaService {

    // Injeção de dependência: o Service conhece a interface, não a implementação concreta
    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    // -------------------------------------------------------
    // Cadastrar — com validações (Entrega 4)
    // -------------------------------------------------------

    /**
     * Cadastra uma nova tarefa após validar os dados.
     *
     * @param tarefa objeto com os dados da nova tarefa
     * @return tarefa salva com ID gerado
     * @throws IllegalArgumentException se os dados forem inválidos
     */
    public Tarefa cadastrar(Tarefa tarefa) {
        validarTarefa(tarefa);
        return tarefaRepository.salvar(tarefa);
    }

    // -------------------------------------------------------
    // Listar
    // -------------------------------------------------------

    /**
     * Retorna todas as tarefas cadastradas.
     */
    public List<Tarefa> listarTodas() {
        return tarefaRepository.listarTodas();
    }

    /**
     * Busca uma tarefa pelo ID.
     *
     * @throws IllegalArgumentException se a tarefa não existir
     */
    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Tarefa não encontrada com ID: " + id));
    }

    // -------------------------------------------------------
    // Atualizar
    // -------------------------------------------------------

    /**
     * Atualiza os dados de uma tarefa existente.
     *
     * @throws IllegalArgumentException se os dados forem inválidos ou a tarefa não existir
     */
    public Tarefa atualizar(Long id, Tarefa tarefaAtualizada) {
        // Verifica se existe
        buscarPorId(id);

        // Valida os novos dados
        validarTarefa(tarefaAtualizada);

        tarefaAtualizada.setId(id);
        return tarefaRepository.atualizar(tarefaAtualizada)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Erro ao atualizar tarefa com ID: " + id));
    }

    // -------------------------------------------------------
    // Concluir
    // -------------------------------------------------------

    /**
     * Marca uma tarefa como concluída.
     */
    public Tarefa concluir(Long id) {
        Tarefa tarefa = buscarPorId(id);

        if (tarefa.isConcluida()) {
            throw new IllegalStateException("Tarefa já está concluída.");
        }

        tarefa.setConcluida(true);
        return tarefaRepository.atualizar(tarefa)
                .orElseThrow();
    }

    // -------------------------------------------------------
    // Deletar
    // -------------------------------------------------------

    /**
     * Remove uma tarefa pelo ID.
     *
     * @throws IllegalArgumentException se a tarefa não existir
     */
    public void deletar(Long id) {
        buscarPorId(id); // garante que existe antes de deletar
        tarefaRepository.deletar(id);
    }

    // -------------------------------------------------------
    // Validações privadas (Entrega 4 — regras de negócio)
    // -------------------------------------------------------

    /**
     * Valida os dados obrigatórios de uma Tarefa.
     * Lança exceção descritiva para cada violação encontrada.
     */
    private void validarTarefa(Tarefa tarefa) {
        if (tarefa == null) {
            throw new IllegalArgumentException("Tarefa não pode ser nula.");
        }

        // Título é obrigatório — regra principal da Entrega 4
        if (tarefa.getTitulo() == null || tarefa.getTitulo().isBlank()) {
            throw new IllegalArgumentException("O título da tarefa é obrigatório.");
        }

        if (tarefa.getTitulo().length() > 100) {
            throw new IllegalArgumentException(
                    "O título não pode ter mais de 100 caracteres.");
        }

        if (tarefa.getDescricao() != null && tarefa.getDescricao().length() > 500) {
            throw new IllegalArgumentException(
                    "A descrição não pode ter mais de 500 caracteres.");
        }
    }
}
