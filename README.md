# Coding Dojo: Agentes de Qualidade

## Sobre este Dojo

Este é um exercício de Coding Dojo para aprender a criar agentes especializados
no Opencode para análise de código Java.

## O "Simulado"

O código em `src/` é uma API Spring Boot propositalmente mal-feita com:

- **15+ vulnerabilidades de segurança** (SQL injection, hardcoded secrets, etc.)
- **20+ code smells** (classe gigante, métodos longos, duplicação)
- **10+ violações de SOLID** (SRP, OCP, DIP, etc.)
- **ZERO testes** (pasta de testes vazia)
- **Má arquitetura** (camadas misturadas, dependências circulares)

## Como Participar

### Pré-requisitos

- Opencode instalado e configurado
- Este repositório clonado

### Passo 1: Explore o código

Navegue pelo código em `src/main/java/com/example/badservice/`.
Identifique os problemas antes de criar seus agentes.

### Passo 2: Crie seus agentes

Crie os seguintes arquivos em `.opencode/agents/`:

1. `orchestrator.md` - Agente principal que coordena a análise
2. `code-reviewer.md` - Subagente para code smells e SOLID
3. `security-auditor.md` - Subagente para vulnerabilidades
4. `test-generator.md` - Subagente para gaps de teste
5. `architect.md` - Subagente para arquitetura

Dica: Veja os exemplos em `examples/agents/` e `examples/skills/` para entender o formato correto.

### Passo 3: Teste seus agentes

Rode o orchestrator:
```
Analise todo o código Java neste projeto e gere um relatório completo de qualidade
```

### Passo 4: Validação

O moderador rodará o comando `/audit` para validar seus agentes.

## Estrutura de Arquivos Esperada

Cada agente deve seguir o formato:

```JSON
  "agent": {
    "nome do agente primário": {
      "description": "Descrição do agente",
      "mode": "primary",
      "skill": [
        "nome da skill.md"
      ],
      "prompt": "Prompt do seu agente",
      "tools": {
        "bash": true,
        "glob": true,
        "grep": true,
        "read": true
      }
    }, "nome do sub-agente": {
      "description": "Descrição do sub-agente",
      "mode": "primary",
      "skill": [
        "nome da skill.md"
      ],
      "prompt": "Prompt do seu sub-agente",
      "tools": {
        "bash": true,
        "glob": true,
        "grep": true,
        "read": true
      }
    }
  }
```

Para skills:

```markdown
---
name: nome-da-skill
description: "O que a skill faz"
---

## What I do
- O que faz

## When to use me
- Quando usar
```

## Tempo

- Exploração: 5 minutos
- Criação dos agentes: 20 minutos
- Validação: 10 minutos
- Retrospectiva: 10 minutos

Boa sorte!
