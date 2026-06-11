package br.com.estudarfatec.util;

import java.util.Scanner;

/**
 * Utilitários de leitura de entrada do usuário no console.
 * Centraliza a lógica de validação de input para o Menu (Entrega 5).
 */
public class InputUtil {

    private InputUtil() {
        // Classe utilitária — não deve ser instanciada
    }

    /**
     * Lê um inteiro do console, repetindo até receber um valor válido.
     */
    public static int lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    /**
     * Lê uma string não vazia do console.
     */
    public static String lerStringObrigatoria(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = scanner.nextLine().trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.out.println("Campo obrigatório. Digite um valor.");
        }
    }

    /**
     * Lê uma string opcional (pode ser vazia — retorna null nesse caso).
     */
    public static String lerStringOpcional(Scanner scanner, String mensagem) {
        System.out.print(mensagem);
        String valor = scanner.nextLine().trim();
        return valor.isEmpty() ? null : valor;
    }
}
