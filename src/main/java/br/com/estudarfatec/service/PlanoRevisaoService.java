package br.com.estudarfatec.service;

import br.com.estudarfatec.model.Disciplina;
import br.com.estudarfatec.model.PlanoRevisao;
import br.com.estudarfatec.model.Tarefa;
import br.com.estudarfatec.repository.DisciplinaRepository;
import br.com.estudarfatec.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * NOVA FUNCIONALIDADE — Plano de Revisão Automático
 *
 * Dado uma disciplina e a data da prova, gera um cronograma
 * distribuindo as tarefas pendentes pelos dias restantes.
 *
 * Regras de distribuição:
 *  - Dia 1 → Revisão geral (introdução)
 *  - Dias intermediários → tarefas distribuídas uniformemente
 *  - Último dia → Revisão final e simulado
 *  - Se houver mais tarefas do que dias, agrupa por dia
 *  - Dias sem tarefas recebem sugestão de revisão livre
 */
@Service
public class PlanoRevisaoService {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE;

    private final DisciplinaRepository disciplinaRepository;
    private final TarefaRepository     tarefaRepository;

    public PlanoRevisaoService(DisciplinaRepository disciplinaRepository,
                                TarefaRepository tarefaRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.tarefaRepository     = tarefaRepository;
    }

    /**
     * Gera o plano de revisão.
     *
     * @param disciplinaId  ID da disciplina alvo
     * @param dataProvaStr  data da prova no formato "YYYY-MM-DD"
     * @return PlanoRevisao com cronograma dia a dia
     */
    public PlanoRevisao gerar(Long disciplinaId, String dataProvaStr) {

        // ---- Validações ----
        Disciplina disciplina = disciplinaRepository.buscarPorId(disciplinaId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Disciplina não encontrada com ID: " + disciplinaId));

        LocalDate dataProva;
        try {
            dataProva = LocalDate.parse(dataProvaStr, FMT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Data inválida. Use o formato YYYY-MM-DD. Ex: 2025-07-20");
        }

        LocalDate hoje = LocalDate.now();
        if (!dataProva.isAfter(hoje)) {
            throw new IllegalArgumentException(
                    "A data da prova deve ser no futuro.");
        }

        // ---- Tarefas pendentes da disciplina ----
        List<Tarefa> pendentes = tarefaRepository.listarTodas().stream()
                .filter(t -> disciplinaId.equals(t.getDisciplinaId()) && !t.isConcluida())
                .toList();

        long diasRestantes = hoje.until(dataProva).getDays();
        if (diasRestantes > 365) {
            throw new IllegalArgumentException(
                    "Plano suportado para até 365 dias antes da prova.");
        }

        // ---- Monta cronograma ----
        List<PlanoRevisao.DiaRevisao> cronograma = new ArrayList<>();

        if (pendentes.isEmpty()) {
            // Sem tarefas → cronograma genérico de revisão
            for (int d = 1; d <= diasRestantes; d++) {
                PlanoRevisao.DiaRevisao dia = new PlanoRevisao.DiaRevisao();
                dia.setDia(d);
                dia.setData(hoje.plusDays(d - 1).format(FMT));
                dia.setTarefas(Collections.emptyList());
                dia.setFoco(d == diasRestantes
                        ? "Revisão final e descanso mental"
                        : "Revisão livre — sem tarefas pendentes cadastradas");
                cronograma.add(dia);
            }
        } else {
            // Distribui tarefas pelos dias
            int totalDias    = (int) diasRestantes;
            int totalTarefas = pendentes.size();

            // Quantas tarefas por dia (arredondado para cima)
            int tarefasPorDia = (int) Math.ceil((double) totalTarefas / Math.max(totalDias - 1, 1));

            int tarefaIdx = 0;
            for (int d = 1; d <= totalDias; d++) {
                PlanoRevisao.DiaRevisao dia = new PlanoRevisao.DiaRevisao();
                dia.setDia(d);
                dia.setData(hoje.plusDays(d - 1).format(FMT));

                if (d == 1) {
                    // Primeiro dia: visão geral
                    dia.setTarefas(Collections.emptyList());
                    dia.setFoco("Leitura geral do material — entenda o mapa da disciplina");

                } else if (d == totalDias) {
                    // Último dia: revisão final
                    dia.setTarefas(Collections.emptyList());
                    dia.setFoco("Revisão final: releia suas anotações e descanse bem!");

                } else {
                    // Dias intermediários: distribui tarefas
                    List<String> titulosDoDia = new ArrayList<>();
                    int limite = Math.min(tarefaIdx + tarefasPorDia, totalTarefas);
                    while (tarefaIdx < limite) {
                        titulosDoDia.add(pendentes.get(tarefaIdx).getTitulo());
                        tarefaIdx++;
                    }

                    dia.setTarefas(titulosDoDia);
                    dia.setFoco(titulosDoDia.isEmpty()
                            ? "Revisão livre — consolide o que já estudou"
                            : "Foco em: " + String.join(", ", titulosDoDia));
                }

                cronograma.add(dia);
            }
        }

        // ---- Monta resposta ----
        PlanoRevisao plano = new PlanoRevisao();
        plano.setDisciplinaNome(disciplina.getNome());
        plano.setDataProva(dataProvaStr);
        plano.setDiasRestantes((int) diasRestantes);
        plano.setTotalTarefasPendentes(pendentes.size());
        plano.setCronograma(cronograma);
        return plano;
    }
}
