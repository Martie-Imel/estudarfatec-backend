package br.com.estudarfatec.patterns.strategy;

/**
 * ENTREGA 6 - Padrão Strategy
 *
 * Estratégia de estudo Pomodoro:
 * 25 minutos de foco total + 5 minutos de pausa.
 *
 * Técnica criada por Francesco Cirillo nos anos 1980,
 * amplamente usada para aumentar produtividade.
 */
public class MetodoPomodoro implements MetodoEstudo {

    private static final int DURACAO_FOCO_MINUTOS = 25;
    private static final int DURACAO_PAUSA_MINUTOS = 5;

    @Override
    public String estudar(String assunto) {
        return String.format(
                "[POMODORO] Iniciando sessão de %d min de foco em '%s'. " +
                "Após o timer, pause por %d min. Elimine todas as distrações!",
                DURACAO_FOCO_MINUTOS, assunto, DURACAO_PAUSA_MINUTOS
        );
    }

    @Override
    public String getNome() {
        return "Pomodoro";
    }

    @Override
    public int getDuracaoMinutos() {
        return DURACAO_FOCO_MINUTOS;
    }
}
