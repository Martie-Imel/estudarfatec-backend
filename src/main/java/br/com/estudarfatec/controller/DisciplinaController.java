package br.com.estudarfatec.controller;

import br.com.estudarfatec.model.Disciplina;
import br.com.estudarfatec.service.DisciplinaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ENTREGA 5 - Fluxo MVC
 *
 * Controller REST para Disciplinas.
 *
 * Endpoints:
 *   POST   /api/disciplinas       → cadastrar disciplina
 *   GET    /api/disciplinas       → listar todas
 *   GET    /api/disciplinas/{id}  → buscar por ID
 *   PUT    /api/disciplinas/{id}  → atualizar
 *   DELETE /api/disciplinas/{id}  → deletar
 */
@RestController
@RequestMapping("/api/disciplinas")
@CrossOrigin(origins = "*")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @PostMapping
    public ResponseEntity<Disciplina> cadastrar(@RequestBody Disciplina disciplina) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(disciplinaService.cadastrar(disciplina));
    }

    @GetMapping
    public ResponseEntity<List<Disciplina>> listarTodas() {
        return ResponseEntity.ok(disciplinaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(disciplinaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> atualizar(
            @PathVariable Long id,
            @RequestBody Disciplina disciplina) {
        return ResponseEntity.ok(disciplinaService.atualizar(id, disciplina));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        disciplinaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
