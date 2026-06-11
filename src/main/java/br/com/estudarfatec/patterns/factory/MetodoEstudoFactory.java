package br.com.estudarfatec.patterns.factory;

import br.com.estudarfatec.patterns.strategy.MetodoEstudo;
import br.com.estudarfatec.patterns.strategy.MetodoPomodoro;
import br.com.estudarfatec.patterns.strategy.MetodoRevisao;

/**
 * ENTREGA 7 - Padrão Factory
 *
 * Fábrica responsável por criar instâncias de MetodoEstudo.
 * O Controller não usa "new" diretamente — delega à Factory.
 *
 * Benefícios:
 * - Centraliza a lógica de criação em um único lugar
 * - Adicionar um novo método de estudo = adicionar um case aqui
 * - Controller permanece enxuto e sem lógica de criação
 *
 * Padrão: Factory Method
 */
public class MetodoEstudoFactory {

    // Constantes públicas para evitar strings mágicas no código
    public static final String POMODORO = "pomodoro";
    public static final String REVISAO  = "revisao";

    /**
     * Cria e retorna a implementação de MetodoEstudo correspondente ao tipo.
     *
     * @param tipo identificador do método ("pomodoro" ou "revisao")
     * @return instância concreta de MetodoEstudo
     * @throws IllegalArgumentException se o tipo for desconhecido
     */
    public static MetodoEstudo criar(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException(
                    "Tipo de método de estudo não pode ser vazio.");
        }

        return switch (tipo.toLowerCase().trim()) {
            case POMODORO -> new MetodoPomodoro();
            case REVISAO  -> new MetodoRevisao();
            default -> throw new IllegalArgumentException(
                    "Método de estudo desconhecido: '" + tipo + "'. " +
                    "Tipos válidos: pomodoro, revisao");
        };
    }

    /**
     * Retorna os tipos disponíveis (útil para o front-end popular um select).
     */
    public static String[] tiposDisponiveis() {
        return new String[]{ POMODORO, REVISAO };
    }
}
