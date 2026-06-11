package br.com.estudarfatec.service;

import br.com.estudarfatec.model.Disciplina;
import br.com.estudarfatec.repository.DisciplinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ENTREGA 4 - Regras de Negócio
 *
 * Serviço de Disciplinas com validações de dados.
 */
@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public Disciplina cadastrar(Disciplina disciplina) {
        validarDisciplina(disciplina);
        return disciplinaRepository.salvar(disciplina);
    }

    public List<Disciplina> listarTodas() {
        return disciplinaRepository.listarTodas();
    }

    public Disciplina buscarPorId(Long id) {
        return disciplinaRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Disciplina não encontrada com ID: " + id));
    }

    public Disciplina atualizar(Long id, Disciplina disciplinaAtualizada) {
        buscarPorId(id);
        validarDisciplina(disciplinaAtualizada);
        disciplinaAtualizada.setId(id);
        return disciplinaRepository.atualizar(disciplinaAtualizada)
                .orElseThrow();
    }

    public void deletar(Long id) {
        buscarPorId(id);
        disciplinaRepository.deletar(id);
    }

    private void validarDisciplina(Disciplina disciplina) {
        if (disciplina == null) {
            throw new IllegalArgumentException("Disciplina não pode ser nula.");
        }
        if (disciplina.getNome() == null || disciplina.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome da disciplina é obrigatório.");
        }
        if (disciplina.getNome().length() > 80) {
            throw new IllegalArgumentException(
                    "O nome não pode ter mais de 80 caracteres.");
        }
    }
}
