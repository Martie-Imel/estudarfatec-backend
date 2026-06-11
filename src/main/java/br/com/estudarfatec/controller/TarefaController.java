package br.com.estudarfatec.controller;

import br.com.estudarfatec.model.Tarefa;
import br.com.estudarfatec.service.TarefaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ENTREGA 5 - Fluxo MVC
 *
 * Controller REST para operações de Tarefa.
 * Recebe requisições HTTP, delega ao Service e retorna respostas JSON.
 *
 * Endpoints disponíveis:
 *   POST   /api/tarefas         → cadastrar nova tarefa
 *   GET    /api/tarefas         → listar todas as tarefas
 *   GET    /api/tarefas/{id}    → buscar tarefa por ID
 *   PUT    /api/tarefas/{id}    → atualizar tarefa
 *   PATCH  /api/tarefas/{id}/concluir → marcar como concluída
 *   DELETE /api/tarefas/{id}    → deletar tarefa
 *
 * Conceitos aplicados:
 * - @RestController: combina @Controller + @ResponseBody (retorna JSON)
 * - @RequestMapping: define a rota base
 * - ResponseEntity: permite controlar o status HTTP da resposta
 * - Separação clara entre Controller (HTTP) e Service (lógica)
 */
@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*") // Permite que o front-end se conecte a esta API
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    // -------------------------------------------------------
    // POST /api/tarefas — Cadastrar
    // -------------------------------------------------------

    /**
     * Cadastra uma nova tarefa.
     * Retorna 201 Created com a tarefa criada.
     */
    @PostMapping
    public ResponseEntity<Tarefa> cadastrar(@RequestBody Tarefa tarefa) {
        Tarefa tarefaSalva = tarefaService.cadastrar(tarefa);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaSalva);
    }

    // -------------------------------------------------------
    // GET /api/tarefas — Listar todas
    // -------------------------------------------------------

    /**
     * Retorna todas as tarefas cadastradas.
     * Retorna 200 OK com lista (pode ser vazia).
     */
    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTodas() {
        return ResponseEntity.ok(tarefaService.listarTodas());
    }

    // -------------------------------------------------------
    // GET /api/tarefas/{id} — Buscar por ID
    // -------------------------------------------------------

    /**
     * Busca uma tarefa pelo ID.
     * Retorna 200 OK ou 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        Tarefa tarefa = tarefaService.buscarPorId(id);
        return ResponseEntity.ok(tarefa);
    }

    // -------------------------------------------------------
    // PUT /api/tarefas/{id} — Atualizar
    // -------------------------------------------------------

    /**
     * Atualiza os dados de uma tarefa existente.
     * Retorna 200 OK com a tarefa atualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(
            @PathVariable Long id,
            @RequestBody Tarefa tarefa) {
        Tarefa tarefaAtualizada = tarefaService.atualizar(id, tarefa);
        return ResponseEntity.ok(tarefaAtualizada);
    }

    // -------------------------------------------------------
    // PATCH /api/tarefas/{id}/concluir — Marcar como concluída
    // -------------------------------------------------------

    /**
     * Marca uma tarefa como concluída.
     * Retorna 200 OK com a tarefa atualizada.
     */
    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Tarefa> concluir(@PathVariable Long id) {
        Tarefa tarefa = tarefaService.concluir(id);
        return ResponseEntity.ok(tarefa);
    }

    // -------------------------------------------------------
    // DELETE /api/tarefas/{id} — Deletar
    // -------------------------------------------------------

    /**
     * Remove uma tarefa pelo ID.
     * Retorna 204 No Content após exclusão.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
