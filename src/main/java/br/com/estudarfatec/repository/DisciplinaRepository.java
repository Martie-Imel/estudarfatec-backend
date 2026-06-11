package br.com.estudarfatec.repository;

import br.com.estudarfatec.model.Disciplina;

import java.util.List;
import java.util.Optional;

/**
 * ENTREGA 3 - Camada de Dados
 *
 * Interface de repositório para Disciplina.
 * Segue o mesmo padrão do TarefaRepository.
 */
public interface DisciplinaRepository {

    Disciplina salvar(Disciplina disciplina);

    Optional<Disciplina> buscarPorId(Long id);

    List<Disciplina> listarTodas();

    boolean deletar(Long id);

    Optional<Disciplina> atualizar(Disciplina disciplina);
}
