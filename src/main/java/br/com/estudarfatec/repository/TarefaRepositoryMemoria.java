package br.com.estudarfatec.repository;

import br.com.estudarfatec.model.Tarefa;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ENTREGA 3 - Camada de Dados
 *
 * Implementação do TarefaRepository que salva os dados em memória (RAM).
 * Perfeito para a fase inicial do projeto, antes de integrar um banco de dados.
 *
 * Conceitos aplicados:
 * - Implementação de interface
 * - ArrayList como banco de dados em memória
 * - AtomicLong para gerar IDs únicos com segurança
 * - @Repository: informa ao Spring que esta classe é um componente de dados
 *
 * Quando quiser migrar para banco de dados (ex: PostgreSQL no Render),
 * basta criar uma nova implementação desta interface — sem mudar Service nem Controller.
 */
@Repository
public class TarefaRepositoryMemoria implements TarefaRepository {

    // "banco de dados" em memória
    private final List<Tarefa> tarefas = new ArrayList<>();

    // gerador de ID automático e thread-safe
    private final AtomicLong contadorId = new AtomicLong(1);

    @Override
    public Tarefa salvar(Tarefa tarefa) {
        // Gera um ID único para a nova tarefa
        tarefa.setId(contadorId.getAndIncrement());
        tarefas.add(tarefa);
        return tarefa;
    }

    @Override
    public Optional<Tarefa> buscarPorId(Long id) {
        return tarefas.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Tarefa> listarTodas() {
        // Retorna uma cópia para proteger a lista interna
        return new ArrayList<>(tarefas);
    }

    @Override
    public boolean deletar(Long id) {
        return tarefas.removeIf(t -> t.getId().equals(id));
    }

    @Override
    public Optional<Tarefa> atualizar(Tarefa tarefaAtualizada) {
        for (int i = 0; i < tarefas.size(); i++) {
            if (tarefas.get(i).getId().equals(tarefaAtualizada.getId())) {
                tarefas.set(i, tarefaAtualizada);
                return Optional.of(tarefaAtualizada);
            }
        }
        // Tarefa não encontrada
        return Optional.empty();
    }
}
