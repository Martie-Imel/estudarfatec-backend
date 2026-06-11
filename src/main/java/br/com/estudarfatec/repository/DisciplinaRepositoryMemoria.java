package br.com.estudarfatec.repository;

import br.com.estudarfatec.model.Disciplina;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementação em memória do repositório de Disciplinas.
 */
@Repository
public class DisciplinaRepositoryMemoria implements DisciplinaRepository {

    private final List<Disciplina> disciplinas = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    @Override
    public Disciplina salvar(Disciplina disciplina) {
        disciplina.setId(contadorId.getAndIncrement());
        disciplinas.add(disciplina);
        return disciplina;
    }

    @Override
    public Optional<Disciplina> buscarPorId(Long id) {
        return disciplinas.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Disciplina> listarTodas() {
        return new ArrayList<>(disciplinas);
    }

    @Override
    public boolean deletar(Long id) {
        return disciplinas.removeIf(d -> d.getId().equals(id));
    }

    @Override
    public Optional<Disciplina> atualizar(Disciplina disciplinaAtualizada) {
        for (int i = 0; i < disciplinas.size(); i++) {
            if (disciplinas.get(i).getId().equals(disciplinaAtualizada.getId())) {
                disciplinas.set(i, disciplinaAtualizada);
                return Optional.of(disciplinaAtualizada);
            }
        }
        return Optional.empty();
    }
}
