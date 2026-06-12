package br.com.estudarfatec.patterns.factory;

import br.com.estudarfatec.patterns.strategy.MetodoEstudo;
import br.com.estudarfatec.patterns.strategy.MetodoPomodoro;
import br.com.estudarfatec.patterns.strategy.MetodoPomodoroCustomizado;
import br.com.estudarfatec.patterns.strategy.MetodoRevisao;

/**
 * ENTREGA 7 - Padrão Factory
 * Atualizado: suporte a pomodoro_custom com parâmetros de tempo.
 */
public class MetodoEstudoFactory {

    public static final String POMODORO        = "pomodoro";
    public static final String REVISAO         = "revisao";
    public static final String POMODORO_CUSTOM = "pomodoro_custom";

    /**
     * Cria a estratégia de estudo pelo tipo.
     *
     * Para pomodoro_custom, forneça minutosFoco e minutosPausa.
     * Para os demais tipos, esses parâmetros são ignorados.
     */
    public static MetodoEstudo criar(String tipo,
                                     Integer minutosFoco,
                                     Integer minutosPausa) {
        if (tipo == null || tipo.isBlank())
            throw new IllegalArgumentException("Tipo de método não pode ser vazio.");

        return switch (tipo.toLowerCase().trim()) {
            case POMODORO -> new MetodoPomodoro();
            case REVISAO  -> new MetodoRevisao();
            case POMODORO_CUSTOM -> {
                int foco   = (minutosFoco  != null) ? minutosFoco  : 25;
                int pausa  = (minutosPausa != null) ? minutosPausa : 5;
                yield new MetodoPomodoroCustomizado(foco, pausa);
            }
            default -> throw new IllegalArgumentException(
                    "Método desconhecido: '" + tipo + "'. Válidos: pomodoro, revisao, pomodoro_custom");
        };
    }

    /** Atalho para métodos sem parâmetros extras (retrocompatível). */
    public static MetodoEstudo criar(String tipo) {
        return criar(tipo, null, null);
    }

    public static String[] tiposDisponiveis() {
        return new String[]{ POMODORO, REVISAO, POMODORO_CUSTOM };
    }
}
