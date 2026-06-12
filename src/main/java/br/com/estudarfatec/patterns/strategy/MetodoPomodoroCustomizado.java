package br.com.estudarfatec.patterns.strategy;

/**
 * NOVA FUNCIONALIDADE - Padrão Strategy
 *
 * Pomodoro Customizado: o usuário define livremente
 * o tempo de foco e o tempo de pausa (em minutos).
 *
 * Demonstra que o padrão Strategy aceita implementações
 * parametrizadas — o comportamento varia não apenas pela
 * classe, mas também pelos dados injetados no construtor.
 */
public class MetodoPomodoroCustomizado implements MetodoEstudo {

    private final int minutosFoco;
    private final int minutospausa;

    /**
     * @param minutosFoco  duração do bloco de foco (1–120 min)
     * @param minutosPausa duração da pausa entre blocos (1–60 min)
     */
    public MetodoPomodoroCustomizado(int minutosFoco, int minutosPausa) {
        if (minutosFoco  < 1 || minutosFoco  > 120)
            throw new IllegalArgumentException(
                    "Tempo de foco deve estar entre 1 e 120 minutos.");
        if (minutosPausa < 1 || minutosPausa > 60)
            throw new IllegalArgumentException(
                    "Tempo de pausa deve estar entre 1 e 60 minutos.");
        this.minutosFoco   = minutosFoco;
        this.minutospausa  = minutosPausa;
    }

    @Override
    public String estudar(String assunto) {
        return String.format(
                "[POMODORO CUSTOMIZADO] Sessão de %d min de foco em '%s', " +
                "seguida de %d min de pausa. Ciclos personalizados para o seu ritmo!",
                minutosFoco, assunto, minutospausa);
    }

    @Override
    public String getNome() {
        return String.format("Pomodoro Custom (%d/%d)", minutosFoco, minutospausa);
    }

    @Override
    public int getDuracaoMinutos() {
        return minutosFoco;
    }

    public int getMinutosFoco()  { return minutosFoco; }
    public int getMinutosPausa() { return minutospausa; }
}
