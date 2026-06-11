package br.com.estudarfatec.repository;

import br.com.estudarfatec.model.Tarefa;

import java.util.List;
import java.util.Optional;

/**
 * ENTREGA 3 - Camada de Dados
 *
 * Interface que define o contrato de acesso aos dados de Tarefa.
 * Isola COMO os dados são salvos do restante do sistema.
 *
 * Conceitos aplicados:
 * - Interface como contrato
 * - Separação de responsabilidades (camada de dados isolada)
 * - Optional para evitar NullPointerException
 *
 * Futuramente, pode ser implementada com banco de dados
 * sem alterar nenhuma outra camada da aplicação.
 */
public interface TarefaRepository {

    /**
     * Salva uma nova tarefa ou atualiza uma existente.
     * @param tarefa objeto a ser persistido
     * @return tarefa salva (com ID gerado, se novo)
     */
    Tarefa salvar(Tarefa tarefa);

    /**
     * Busca uma tarefa pelo seu ID.
     * @param id identificador único
     * @return Optional contendo a tarefa, ou vazio se não encontrar
     */
    Optional<Tarefa> buscarPorId(Long id);

    /**
     * Retorna todas as tarefas armazenadas.
     * @return lista imutável de tarefas
     */
    List<Tarefa> listarTodas();

    /**
     * Remove uma tarefa pelo ID.
     * @param id identificador da tarefa a remover
     * @return true se removida com sucesso, false se não encontrada
     */
    boolean deletar(Long id);

    /**
     * Atualiza os dados de uma tarefa existente.
     * @param tarefa objeto com dados atualizados
     * @return Optional com a tarefa atualizada, ou vazio se não existir
     */
    Optional<Tarefa> atualizar(Tarefa tarefa);
}
