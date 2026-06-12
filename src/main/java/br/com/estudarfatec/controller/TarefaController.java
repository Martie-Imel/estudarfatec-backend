package br.com.estudarfatec.controller;

import br.com.estudarfatec.model.Tarefa;
import br.com.estudarfatec.service.TarefaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ENTREGA 5 - Fluxo MVC
 * Atualizado: endpoints de vínculo com disciplina +
 *             listar tarefas filtradas por disciplina.
 *
 * Endpoints:
 *   POST   /api/tarefas
 *   GET    /api/tarefas
 *   GET    /api/tarefas?disciplinaId={id}
 *   GET    /api/tarefas/{id}
 *   PUT    /api/tarefas/{id}
 *   PATCH  /api/tarefas/{id}/concluir
 *   PATCH  /api/tarefas/{id}/vincular          body: {"disciplinaId": 1}
 *   DELETE /api/tarefas/{id}/vincular          → remove vínculo
 *   DELETE /api/tarefas/{id}
 */
@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    public ResponseEntity<Tarefa> cadastrar(@RequestBody Tarefa tarefa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.cadastrar(tarefa));
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listar(
            @RequestParam(required = false) Long disciplinaId) {
        if (disciplinaId != null) {
            return ResponseEntity.ok(tarefaService.listarPorDisciplina(disciplinaId));
        }
        return ResponseEntity.ok(tarefaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id,
                                             @RequestBody Tarefa tarefa) {
        return ResponseEntity.ok(tarefaService.atualizar(id, tarefa));
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Tarefa> concluir(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.concluir(id));
    }

    /** Vincula a tarefa a uma disciplina. Corpo: {"disciplinaId": 2} */
    @PatchMapping("/{id}/vincular")
    public ResponseEntity<Tarefa> vincular(@PathVariable Long id,
                                            @RequestBody Map<String, Long> body) {
        Long disciplinaId = body.get("disciplinaId");
        if (disciplinaId == null)
            throw new IllegalArgumentException("Campo 'disciplinaId' é obrigatório.");
        return ResponseEntity.ok(tarefaService.vincularDisciplina(id, disciplinaId));
    }

    /** Remove o vínculo da tarefa com disciplina. */
    @DeleteMapping("/{id}/vincular")
    public ResponseEntity<Tarefa> desvincular(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.desvincularDisciplina(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
