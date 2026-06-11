package br.com.estudarfatec.patterns.strategy;

/**
 * ENTREGA 6 - Padrão Strategy
 *
 * Interface que define o contrato para todos os métodos de estudo.
 * Cada implementação representa uma estratégia diferente de estudar.
 *
 * O padrão Strategy permite:
 * - Trocar o comportamento de estudo em tempo de execução
 * - Adicionar novas estratégias sem modificar o código existente
 * - Seguir o princípio Aberto/Fechado (Open/Closed Principle)
 *
 * Sem Strategy → if/else gigante no código
 * Com Strategy → cada comportamento em sua própria classe
 */
public interface MetodoEstudo {

    /**
     * Executa a sessão de estudo.
     * Cada implementação define sua própria forma de estudar.
     *
     * @param assunto o conteúdo a ser estudado
     * @return descrição do que foi feito na sessão
     */
    String estudar(String assunto);

    /**
     * Retorna o nome do método (útil para exibição na API).
     */
    String getNome();

    /**
     * Retorna a duração recomendada da sessão em minutos.
     */
    int getDuracaoMinutos();
}
