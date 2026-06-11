package br.com.estudarfatec.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Tratamento global de exceções da API.
 *
 * Intercepta exceções lançadas pelos Services e Controllers,
 * retornando respostas JSON padronizadas com status HTTP correto.
 *
 * Sem esta classe, o Spring retornaria páginas HTML de erro
 * (inúteis para uma API REST consumida por front-end).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata erros de validação e recursos não encontrados.
     * Retorna HTTP 400 Bad Request.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(
            IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(erroBody(ex.getMessage(), 400));
    }

    /**
     * Trata erros de estado inválido (ex: concluir tarefa já concluída).
     * Retorna HTTP 409 Conflict.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(
            IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(erroBody(ex.getMessage(), 409));
    }

    /**
     * Captura qualquer outra exceção inesperada.
     * Retorna HTTP 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(erroBody("Erro interno no servidor.", 500));
    }

    private Map<String, Object> erroBody(String mensagem, int status) {
        return Map.of(
                "status",    status,
                "erro",      mensagem,
                "timestamp", LocalDateTime.now().toString()
        );
    }
}
