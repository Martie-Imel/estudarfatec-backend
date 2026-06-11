package br.com.estudarfatec.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ENTREGA 2 - Estrutura de Dados
 *
 * Classe que representa uma Disciplina, capaz de armazenar
 * múltiplas Tarefas usando ArrayList.
 *
 * Conceitos aplicados:
 * - Composição (Disciplina contém Tarefas)
 * - ArrayList para armazenamento de múltiplos itens
 * - Associação entre classes
 */
public class Disciplina {

    private Long id;
    private String nome;
    private String professor;

    // ArrayList para armazenar as tarefas da disciplina (Entrega 2)
    private List<Tarefa> tarefas;

    // -------------------------------------------------------
    // Construtores
    // -------------------------------------------------------

    public Disciplina() {
        this.tarefas = new ArrayList<>();
    }

    public Disciplina(Long id, String nome, String professor) {
        this.id = id;
        this.nome = nome;
        this.professor = professor;
        this.tarefas = new ArrayList<>();
    }

    // -------------------------------------------------------
    // Métodos de negócio
    // -------------------------------------------------------

    /**
     * Adiciona uma tarefa à lista da disciplina.
     * Demonstração do ArrayList recebendo múltiplos itens (Entrega 2).
     */
    public void adicionarTarefa(Tarefa tarefa) {
        this.tarefas.add(tarefa);
    }

    /**
     * Remove uma tarefa da disciplina pelo ID.
     */
    public boolean removerTarefa(Long tarefaId) {
        return this.tarefas.removeIf(t -> t.getId().equals(tarefaId));
    }

    /**
     * Retorna o total de tarefas pendentes.
     */
    public long totalPendentes() {
        return tarefas.stream()
                .filter(t -> !t.isConcluida())
                .count();
    }

    // -------------------------------------------------------
    // Getters e Setters
    // -------------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", professor='" + professor + '\'' +
                ", totalTarefas=" + tarefas.size() +
                '}';
    }
}
