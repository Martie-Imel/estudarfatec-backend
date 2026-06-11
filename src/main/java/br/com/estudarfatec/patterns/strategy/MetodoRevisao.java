package br.com.estudarfatec.patterns.strategy;

/**
 * ENTREGA 6 - Padrão Strategy
 *
 * Estratégia de estudo por Revisão:
 * Baseada na técnica de repetição espaçada (Spaced Repetition).
 * Ideal para fixar conteúdo já visto antes.
 */
public class MetodoRevisao implements MetodoEstudo {

    private static final int DURACAO_REVISAO_MINUTOS = 45;

    @Override
    public String estudar(String assunto) {
        return String.format(
                "[REVISÃO] Revisando '%s' com repetição espaçada por %d min. " +
                "Leia os anotações, feche-as e tente reescrever de memória. " +
                "Confira o que errou e repita os pontos fracos.",
                assunto, DURACAO_REVISAO_MINUTOS
        );
    }

    @Override
    public String getNome() {
        return "Revisão Espaçada";
    }

    @Override
    public int getDuracaoMinutos() {
        return DURACAO_REVISAO_MINUTOS;
    }
}
