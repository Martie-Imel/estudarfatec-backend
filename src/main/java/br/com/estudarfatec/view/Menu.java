package br.com.estudarfatec.view;

import br.com.estudarfatec.model.Tarefa;
import br.com.estudarfatec.repository.TarefaRepositoryMemoria;
import br.com.estudarfatec.repository.DisciplinaRepositoryMemoria;
import br.com.estudarfatec.service.TarefaService;

import java.util.List;
import java.util.Scanner;

/**
 * ENTREGA 5 - Fluxo MVC (versão console)
 *
 * Menu interativo para demonstrar o fluxo MVC sem precisar do Spring.
 * O usuário digita opções no teclado para cadastrar e listar tarefas.
 *
 * Execute o método main desta classe para interagir via terminal.
 *
 * Conceitos aplicados:
 * - View: responsável pela interação com o usuário (entrada/saída)
 * - Controller lógico embutido no menu (separação clara de camadas)
 * - Loop de menu com switch
 */
public class Menu {

    // Instancia as camadas diretamente para demonstração sem Spring
    private static final TarefaService tarefaService = new TarefaService(
        new TarefaRepositoryMemoria(),
        new DisciplinaRepositoryMemoria()
    );

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║    EstudarFatec - Menu       ║");
        System.out.println("╚══════════════════════════════╝");

        boolean rodando = true;
        while (rodando) {
            exibirOpcoes();
            int opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarTarefa();
                case 2 -> listarTarefas();
                case 3 -> buscarTarefaPorId();
                case 4 -> concluirTarefa();
                case 5 -> deletarTarefa();
                case 0 -> {
                    System.out.println("\nAté logo!");
                    rodando = false;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    // -------------------------------------------------------
    // Operações do menu
    // -------------------------------------------------------

    private static void exibirOpcoes() {
        System.out.println("\n--- MENU ---");
        System.out.println("1 - Cadastrar tarefa");
        System.out.println("2 - Listar todas as tarefas");
        System.out.println("3 - Buscar tarefa por ID");
        System.out.println("4 - Concluir tarefa");
        System.out.println("5 - Deletar tarefa");
        System.out.println("0 - Sair");
    }

    private static void cadastrarTarefa() {
        System.out.println("\n--- CADASTRAR TAREFA ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();

        System.out.print("Descrição (Enter para pular): ");
        String descricao = scanner.nextLine().trim();

        try {
            Tarefa nova = new Tarefa(null, titulo, descricao.isEmpty() ? null : descricao);
            Tarefa salva = tarefaService.cadastrar(nova);
            System.out.println("✔ Tarefa cadastrada com sucesso! ID: " + salva.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("✘ Erro: " + e.getMessage());
        }
    }

    private static void listarTarefas() {
        System.out.println("\n--- LISTA DE TAREFAS ---");
        List<Tarefa> tarefas = tarefaService.listarTodas();

        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
            return;
        }

        tarefas.forEach(t -> System.out.printf(
                "[%d] %s | %s | %s%n",
                t.getId(),
                t.getTitulo(),
                t.getDescricao() != null ? t.getDescricao() : "sem descrição",
                t.isConcluida() ? "✔ CONCLUÍDA" : "⏳ PENDENTE"
        ));
    }

    private static void buscarTarefaPorId() {
        Long id = (long) lerInteiro("ID da tarefa: ");
        try {
            Tarefa tarefa = tarefaService.buscarPorId(id);
            System.out.println("\n" + tarefa);
        } catch (IllegalArgumentException e) {
            System.out.println("✘ " + e.getMessage());
        }
    }

    private static void concluirTarefa() {
        Long id = (long) lerInteiro("ID da tarefa a concluir: ");
        try {
            tarefaService.concluir(id);
            System.out.println("✔ Tarefa marcada como concluída!");
        } catch (Exception e) {
            System.out.println("✘ " + e.getMessage());
        }
    }

    private static void deletarTarefa() {
        Long id = (long) lerInteiro("ID da tarefa a deletar: ");
        try {
            tarefaService.deletar(id);
            System.out.println("✔ Tarefa removida com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("✘ " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // Utilitários de leitura
    // -------------------------------------------------------

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}
