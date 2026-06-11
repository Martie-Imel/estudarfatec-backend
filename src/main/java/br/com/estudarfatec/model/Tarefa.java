package br.com.estudarfatec.model;

/**
 * ENTREGA 1 - Fundamentos de POO
 *
 * Classe que representa uma Tarefa no sistema.
 * Demonstra encapsulamento com atributos privados e acesso via getters/setters.
 *
 * Conceitos aplicados:
 * - Encapsulamento (atributos private)
 * - Construtores sobrecarregados
 * - Getters e Setters
 * - toString() para representação do objeto
 */
public class Tarefa {

    // Atributos privados — encapsulamento (Entrega 1)
    private Long id;
    private String titulo;
    private String descricao;
    private boolean concluida;

    // -------------------------------------------------------
    // Construtores
    // -------------------------------------------------------

    /** Construtor padrão (necessário para frameworks como Spring) */
    public Tarefa() {
    }

    /** Construtor completo usado ao criar uma nova tarefa */
    public Tarefa(Long id, String titulo, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = false;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    // -------------------------------------------------------
    // toString — útil para debug e exibição no console (Entrega 1)
    // -------------------------------------------------------

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", concluida=" + concluida +
                '}';
    }

    // -------------------------------------------------------
    // Método main — instancia e imprime objetos (Entrega 1)
    // -------------------------------------------------------

    /**
     * Demonstração do Entrega 1: instanciando e imprimindo Tarefas no console.
     * Execute este main para ver os objetos sendo criados.
     */
    public static void main(String[] args) {
        Tarefa tarefa1 = new Tarefa(1L, "Estudar POO", "Revisar encapsulamento e herança");
        Tarefa tarefa2 = new Tarefa(2L, "Fazer exercícios", "Resolver lista de arrays");

        System.out.println("=== ENTREGA 1 - Fundamentos POO ===");
        System.out.println(tarefa1);
        System.out.println(tarefa2);

        // Demonstrando uso dos setters
        tarefa1.setConcluida(true);
        System.out.println("\nApós marcar tarefa 1 como concluída:");
        System.out.println(tarefa1);
    }
}
