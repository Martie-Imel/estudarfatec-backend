package br.com.estudarfatec.controller;

import br.com.estudarfatec.patterns.factory.MetodoEstudoFactory;
import br.com.estudarfatec.patterns.strategy.MetodoEstudo;
import br.com.estudarfatec.patterns.strategy.MetodoPomodoroCustomizado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ENTREGA 6+7 - Strategy + Factory via REST
 * Atualizado: suporte a pomodoro_custom com minutosFoco e minutosPausa.
 *
 * POST /api/metodos-estudo/iniciar
 * Body padrão:        { "metodo": "pomodoro",       "assunto": "POO" }
 * Body customizado:   { "metodo": "pomodoro_custom", "assunto": "POO",
 *                       "minutosFoco": 45, "minutosPausa": 10 }
 */
@RestController
@RequestMapping("/api/metodos-estudo")
@CrossOrigin(origins = "*")
public class EstudoController {

    @GetMapping
    public ResponseEntity<String[]> listarMetodos() {
        return ResponseEntity.ok(MetodoEstudoFactory.tiposDisponiveis());
    }

    @PostMapping("/iniciar")
    public ResponseEntity<Map<String, Object>> iniciarSessao(
            @RequestBody Map<String, Object> body) {

        String  metodo      = (String) body.get("metodo");
        String  assunto     = (String) body.get("assunto");
        Integer minutosFoco  = body.get("minutosFoco")  != null
                ? ((Number) body.get("minutosFoco")).intValue()  : null;
        Integer minutosPausa = body.get("minutosPausa") != null
                ? ((Number) body.get("minutosPausa")).intValue() : null;

        if (assunto == null || assunto.isBlank())
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "O campo 'assunto' é obrigatório."));

        MetodoEstudo estrategia =
                MetodoEstudoFactory.criar(metodo, minutosFoco, minutosPausa);
        String resultado = estrategia.estudar(assunto);

        // Monta resposta enriquecida para pomodoro customizado
        if (estrategia instanceof MetodoPomodoroCustomizado custom) {
            return ResponseEntity.ok(Map.of(
                    "metodo",        estrategia.getNome(),
                    "assunto",       assunto,
                    "duracaoFoco",   custom.getMinutosFoco()  + " minutos",
                    "duracaoPausa",  custom.getMinutosPausa() + " minutos",
                    "duracao",       custom.getDuracaoMinutos() + " minutos",
                    "instrucoes",    resultado,
                    "customizado",   true
            ));
        }

        return ResponseEntity.ok(Map.of(
                "metodo",      estrategia.getNome(),
                "assunto",     assunto,
                "duracao",     estrategia.getDuracaoMinutos() + " minutos",
                "instrucoes",  resultado,
                "customizado", false
        ));
    }
}
