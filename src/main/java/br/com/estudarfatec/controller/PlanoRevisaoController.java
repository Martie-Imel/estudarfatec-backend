package br.com.estudarfatec.controller;

import br.com.estudarfatec.model.PlanoRevisao;
import br.com.estudarfatec.service.PlanoRevisaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * NOVA FUNCIONALIDADE — Plano de Revisão
 *
 * POST /api/plano-revisao
 * Body: { "disciplinaId": 1, "dataProva": "2025-07-20" }
 *
 * Retorna cronograma com tarefas pendentes distribuídas
 * pelos dias até a data da prova.
 */
@RestController
@RequestMapping("/api/plano-revisao")
@CrossOrigin(origins = "*")
public class PlanoRevisaoController {

    private final PlanoRevisaoService planoRevisaoService;

    public PlanoRevisaoController(PlanoRevisaoService planoRevisaoService) {
        this.planoRevisaoService = planoRevisaoService;
    }

    @PostMapping
    public ResponseEntity<PlanoRevisao> gerar(@RequestBody Map<String, Object> body) {

        Object discIdObj = body.get("disciplinaId");
        String dataProva = (String) body.get("dataProva");

        if (discIdObj == null)
            throw new IllegalArgumentException("Campo 'disciplinaId' é obrigatório.");
        if (dataProva == null || dataProva.isBlank())
            throw new IllegalArgumentException("Campo 'dataProva' é obrigatório (YYYY-MM-DD).");

        Long disciplinaId = ((Number) discIdObj).longValue();
        return ResponseEntity.ok(planoRevisaoService.gerar(disciplinaId, dataProva));
    }
}
