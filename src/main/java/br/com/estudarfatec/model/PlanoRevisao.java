package br.com.estudarfatec.model;

import java.util.List;

/**
 * NOVA FUNCIONALIDADE — Plano de Revisão
 *
 * Representa o cronograma gerado automaticamente para uma disciplina
 * com base na data da prova e nas tarefas pendentes.
 */
public class PlanoRevisao {

    private String       disciplinaNome;
    private String       dataProva;
    private int          diasRestantes;
    private int          totalTarefasPendentes;
    private List<DiaRevisao> cronograma;

    public PlanoRevisao() {}

    // Getters e Setters
    public String           getDisciplinaNome()              { return disciplinaNome; }
    public void             setDisciplinaNome(String n)      { this.disciplinaNome = n; }
    public String           getDataProva()                   { return dataProva; }
    public void             setDataProva(String d)           { this.dataProva = d; }
    public int              getDiasRestantes()               { return diasRestantes; }
    public void             setDiasRestantes(int d)          { this.diasRestantes = d; }
    public int              getTotalTarefasPendentes()       { return totalTarefasPendentes; }
    public void             setTotalTarefasPendentes(int t)  { this.totalTarefasPendentes = t; }
    public List<DiaRevisao> getCronograma()                  { return cronograma; }
    public void             setCronograma(List<DiaRevisao> c){ this.cronograma = c; }

    // -------------------------------------------------------
    // Classe interna: representa um dia do cronograma
    // -------------------------------------------------------
    public static class DiaRevisao {
        private int          dia;          // 1, 2, 3...
        private String       data;         // "2025-06-15"
        private List<String> tarefas;      // títulos das tarefas desse dia
        private String       foco;         // sugestão de foco do dia

        public DiaRevisao() {}

        public int           getDia()               { return dia; }
        public void          setDia(int d)          { this.dia = d; }
        public String        getData()              { return data; }
        public void          setData(String d)      { this.data = d; }
        public List<String>  getTarefas()           { return tarefas; }
        public void          setTarefas(List<String> t) { this.tarefas = t; }
        public String        getFoco()              { return foco; }
        public void          setFoco(String f)      { this.foco = f; }
    }
}
