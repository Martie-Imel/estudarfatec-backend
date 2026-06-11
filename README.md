# EstudarFatec API

API REST desenvolvida em Java com Spring Boot para gerenciamento de tarefas e métodos de estudo, construída ao longo das 8 entregas do projeto.

---

## Estrutura do Projeto

```
br/com/estudarfatec/
├── EstudarFatecApplication.java       ← ponto de entrada Spring Boot
│
├── model/
│   ├── Tarefa.java                    ← Entrega 1 (POO / encapsulamento)
│   └── Disciplina.java                ← Entrega 2 (ArrayList)
│
├── repository/
│   ├── TarefaRepository.java          ← Entrega 3 (interface)
│   ├── TarefaRepositoryMemoria.java   ← Entrega 3 (implementação em memória)
│   ├── DisciplinaRepository.java
│   └── DisciplinaRepositoryMemoria.java
│
├── service/
│   ├── TarefaService.java             ← Entrega 4 (regras de negócio + validações)
│   └── DisciplinaService.java
│
├── controller/
│   ├── TarefaController.java          ← Entrega 5 (MVC REST)
│   ├── DisciplinaController.java
│   ├── EstudoController.java          ← Entrega 6 + 7 (Strategy + Factory)
│   └── GlobalExceptionHandler.java    ← tratamento global de erros
│
├── view/
│   └── Menu.java                      ← Entrega 5 (menu interativo console)
│
├── patterns/
│   ├── strategy/
│   │   ├── MetodoEstudo.java          ← Entrega 6 (interface Strategy)
│   │   ├── MetodoPomodoro.java        ← Entrega 6 (implementação)
│   │   └── MetodoRevisao.java         ← Entrega 6 (implementação)
│   └── factory/
│       └── MetodoEstudoFactory.java   ← Entrega 7 (Factory)
│
├── singleton/
│   └── ConfiguracaoSistema.java       ← Singleton thread-safe
│
└── util/
    └── InputUtil.java                 ← utilitários de console

test/
├── TarefaTest.java                    ← Entrega 8 (JUnit 5)
└── TarefaServiceTest.java             ← Entrega 8 (JUnit 5)
```

---

## Endpoints da API

### Tarefas — `/api/tarefas`

| Método | Rota                        | Descrição              | Status de sucesso |
|--------|-----------------------------|------------------------|-------------------|
| POST   | `/api/tarefas`              | Cadastrar nova tarefa  | 201 Created       |
| GET    | `/api/tarefas`              | Listar todas           | 200 OK            |
| GET    | `/api/tarefas/{id}`         | Buscar por ID          | 200 OK            |
| PUT    | `/api/tarefas/{id}`         | Atualizar tarefa       | 200 OK            |
| PATCH  | `/api/tarefas/{id}/concluir`| Marcar como concluída  | 200 OK            |
| DELETE | `/api/tarefas/{id}`         | Deletar tarefa         | 204 No Content    |

**Corpo para POST/PUT:**
```json
{
  "titulo": "Estudar padrões de projeto",
  "descricao": "Revisar Strategy e Factory"
}
```

---

### Disciplinas — `/api/disciplinas`

| Método | Rota                    | Descrição             | Status |
|--------|-------------------------|-----------------------|--------|
| POST   | `/api/disciplinas`      | Cadastrar disciplina  | 201    |
| GET    | `/api/disciplinas`      | Listar todas          | 200    |
| GET    | `/api/disciplinas/{id}` | Buscar por ID         | 200    |
| PUT    | `/api/disciplinas/{id}` | Atualizar             | 200    |
| DELETE | `/api/disciplinas/{id}` | Deletar               | 204    |

**Corpo para POST/PUT:**
```json
{
  "nome": "Programação Orientada a Objetos",
  "professor": "Prof. Silva"
}
```

---

### Métodos de Estudo — `/api/metodos-estudo`

| Método | Rota                          | Descrição                   |
|--------|-------------------------------|-----------------------------|
| GET    | `/api/metodos-estudo`         | Lista métodos disponíveis   |
| POST   | `/api/metodos-estudo/iniciar` | Inicia sessão de estudo     |

**Corpo para POST `/iniciar`:**
```json
{
  "metodo": "pomodoro",
  "assunto": "Padrões de Projeto"
}
```

**Resposta:**
```json
{
  "metodo": "Pomodoro",
  "assunto": "Padrões de Projeto",
  "duracao": "25 minutos",
  "instrucoes": "[POMODORO] Iniciando sessão de 25 min..."
}
```

Tipos disponíveis: `pomodoro`, `revisao`

---

## Como executar localmente

### Pré-requisitos
- Java 17+
- Maven 3.8+

### Rodando a API
```bash
# Compilar e iniciar
mvn spring-boot:run

# A API estará disponível em:
# http://localhost:8080
```

### Rodando o Menu interativo (console)
```bash
mvn compile
mvn exec:java -Dexec.mainClass="br.com.estudarfatec.view.Menu"
```

### Rodando os testes
```bash
mvn test
```

---

## Deploy no Render

1. Suba o projeto para um repositório GitHub
2. No [Render](https://render.com), crie um novo **Web Service**
3. Conecte ao repositório GitHub
4. Configure:
   - **Build Command:** `mvn clean package -DskipTests`
   - **Start Command:** `java -jar target/estudarfatec-1.0.0.jar`
   - **Environment:** Java
5. A variável `PORT` é injetada automaticamente pelo Render — o `application.properties` já está configurado para isso

---

## Conceitos aplicados por entrega

| Entrega | Conceito             | Arquivo principal          |
|---------|----------------------|----------------------------|
| 1       | POO / Encapsulamento | `Tarefa.java`              |
| 2       | ArrayList            | `Disciplina.java`          |
| 3       | Interface / Repository | `TarefaRepository.java`  |
| 4       | Regras de negócio    | `TarefaService.java`       |
| 5       | MVC / REST           | `TarefaController.java`    |
| 6       | Strategy             | `MetodoEstudo.java`        |
| 7       | Factory              | `MetodoEstudoFactory.java` |
| 8       | JUnit 5              | `TarefaServiceTest.java`   |
