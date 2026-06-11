package br.com.estudarfatec.controller;

import br.com.estudarfatec.patterns.factory.MetodoEstudoFactory;
import br.com.estudarfatec.patterns.strategy.MetodoEstudo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ENTREGA 6 e 7 - Strategy + Factory via REST
 *
 * Controller que expõe o sistema de métodos de estudo como API.
 * Usa a Factory para instanciar o método sem "new" direto aqui.
 *
 * Endpoints:
 *   GET  /api/metodos-estudo               → lista métodos disponíveis
 *   POST /api/metodos-estudo/iniciar        → inicia uma sessão de estudo
 */
@RestController
@RequestMapping("/api/metodos-estudo")
@CrossOrigin(origins = "*")
public class EstudoController {

    /**
     * Lista todos os tipos de método disponíveis.
     * Útil para o front-end popular um <select> ou lista de opções.
     */
    @GetMapping
    public ResponseEntity<String[]> listarMetodos() {
        return ResponseEntity.ok(MetodoEstudoFactory.tiposDisponiveis());
    }

    /**
     * Inicia uma sessão de estudo com o método e assunto informados.
     *
     * Corpo esperado (JSON):
     * {
     *   "metodo": "pomodoro",
     *   "assunto": "Padrões de Projeto"
     * }
     */
    @PostMapping("/iniciar")
    public ResponseEntity<Map<String, Object>> iniciarSessao(
            @RequestBody Map<String, String> body) {

        String metodo  = body.get("metodo");
        String assunto = body.get("assunto");

        if (assunto == null || assunto.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "O campo 'assunto' é obrigatório."));
        }

        // Factory cria a estratégia correta — sem "new" direto no Controller (Entrega 7)
        MetodoEstudo estrategia = MetodoEstudoFactory.criar(metodo);

        // Strategy executa o comportamento de estudo (Entrega 6)
        String resultado = estrategia.estudar(assunto);

        return ResponseEntity.ok(Map.of(
                "metodo",    estrategia.getNome(),
                "assunto",   assunto,
                "duracao",   estrategia.getDuracaoMinutos() + " minutos",
                "instrucoes", resultado
        ));
    }
}
