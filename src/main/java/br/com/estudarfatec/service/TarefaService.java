package br.com.estudarfatec.service;

import br.com.estudarfatec.model.Tarefa;
import br.com.estudarfatec.repository.DisciplinaRepository;
import br.com.estudarfatec.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ENTREGA 4 - Regras de Negócio
 * Atualizado: validação de disciplinaId + vincular/desvincular tarefa.
 */
@Service
public class TarefaService {

    private final TarefaRepository     tarefaRepository;
    private final DisciplinaRepository disciplinaRepository;

    public TarefaService(TarefaRepository tarefaRepository,
                         DisciplinaRepository disciplinaRepository) {
        this.tarefaRepository     = tarefaRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    // -------------------------------------------------------
    // CRUD básico
    // -------------------------------------------------------

    public Tarefa cadastrar(Tarefa tarefa) {
        validarTarefa(tarefa);
        validarDisciplina(tarefa.getDisciplinaId());
        return tarefaRepository.salvar(tarefa);
    }

    public List<Tarefa> listarTodas() {
        return tarefaRepository.listarTodas();
    }

    public List<Tarefa> listarPorDisciplina(Long disciplinaId) {
        validarDisciplina(disciplinaId);
        return tarefaRepository.listarTodas().stream()
                .filter(t -> disciplinaId.equals(t.getDisciplinaId()))
                .toList();
    }

    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Tarefa não encontrada com ID: " + id));
    }

    public Tarefa atualizar(Long id, Tarefa nova) {
        buscarPorId(id);
        validarTarefa(nova);
        validarDisciplina(nova.getDisciplinaId());
        nova.setId(id);
        return tarefaRepository.atualizar(nova)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Erro ao atualizar tarefa ID: " + id));
    }

    public Tarefa concluir(Long id) {
        Tarefa t = buscarPorId(id);
        if (t.isConcluida()) throw new IllegalStateException("Tarefa já está concluída.");
        t.setConcluida(true);
        return tarefaRepository.atualizar(t).orElseThrow();
    }

    public void deletar(Long id) {
        buscarPorId(id);
        tarefaRepository.deletar(id);
    }

    // -------------------------------------------------------
    // Vincular / Desvincular disciplina
    // -------------------------------------------------------

    /** Vincula uma tarefa a uma disciplina existente. */
    public Tarefa vincularDisciplina(Long tarefaId, Long disciplinaId) {
        Tarefa t = buscarPorId(tarefaId);
        validarDisciplina(disciplinaId);          // garante que a disciplina existe
        t.setDisciplinaId(disciplinaId);
        return tarefaRepository.atualizar(t).orElseThrow();
    }

    /** Remove o vínculo da tarefa com qualquer disciplina. */
    public Tarefa desvincularDisciplina(Long tarefaId) {
        Tarefa t = buscarPorId(tarefaId);
        t.setDisciplinaId(null);
        return tarefaRepository.atualizar(t).orElseThrow();
    }

    // -------------------------------------------------------
    // Validações privadas
    // -------------------------------------------------------

    private void validarTarefa(Tarefa tarefa) {
        if (tarefa == null)
            throw new IllegalArgumentException("Tarefa não pode ser nula.");
        if (tarefa.getTitulo() == null || tarefa.getTitulo().isBlank())
            throw new IllegalArgumentException("O título da tarefa é obrigatório.");
        if (tarefa.getTitulo().length() > 100)
            throw new IllegalArgumentException("Título: máx. 100 caracteres.");
        if (tarefa.getDescricao() != null && tarefa.getDescricao().length() > 500)
            throw new IllegalArgumentException("Descrição: máx. 500 caracteres.");
    }

    private void validarDisciplina(Long disciplinaId) {
        if (disciplinaId == null) return;   // vínculo é opcional
        disciplinaRepository.buscarPorId(disciplinaId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Disciplina não encontrada com ID: " + disciplinaId));
    }
}
