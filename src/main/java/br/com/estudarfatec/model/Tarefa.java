package br.com.estudarfatec.model;

/**
 * ENTREGA 1 - Fundamentos de POO
 * Atualizado: suporte a vínculo com Disciplina (disciplinaId).
 */
public class Tarefa {

    private Long    id;
    private String  titulo;
    private String  descricao;
    private boolean concluida;

    /** ID da disciplina vinculada. null = sem vínculo. */
    private Long disciplinaId;

    public Tarefa() {}

    public Tarefa(Long id, String titulo, String descricao) {
        this.id        = id;
        this.titulo    = titulo;
        this.descricao = descricao;
        this.concluida = false;
    }

    public Long    getId()                      { return id; }
    public void    setId(Long id)               { this.id = id; }
    public String  getTitulo()                  { return titulo; }
    public void    setTitulo(String titulo)     { this.titulo = titulo; }
    public String  getDescricao()               { return descricao; }
    public void    setDescricao(String d)       { this.descricao = d; }
    public boolean isConcluida()                { return concluida; }
    public void    setConcluida(boolean c)      { this.concluida = c; }
    public Long    getDisciplinaId()            { return disciplinaId; }
    public void    setDisciplinaId(Long dId)    { this.disciplinaId = dId; }

    @Override
    public String toString() {
        return "Tarefa{id=" + id + ", titulo='" + titulo + "', concluida=" + concluida
               + ", disciplinaId=" + disciplinaId + '}';
    }

    public static void main(String[] args) {
        Tarefa t1 = new Tarefa(1L, "Estudar POO", "Revisar encapsulamento");
        Tarefa t2 = new Tarefa(2L, "Fazer exercícios", "Resolver lista de arrays");
        t1.setDisciplinaId(10L);
        System.out.println("=== ENTREGA 1 - Fundamentos POO ===");
        System.out.println(t1);
        System.out.println(t2);
        t1.setConcluida(true);
        System.out.println("\nApós concluir tarefa 1:");
        System.out.println(t1);
    }
}
