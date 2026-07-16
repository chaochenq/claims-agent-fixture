# claims-agent — insurance claims-processing agent framework (security validation fixture)

> ## ⚠️ DELIBERATELY VULNERABLE — DO NOT USE
> This repository is a **synthetic security-scanner validation fixture** written
> in Java + Kotlin. It contains intentionally planted vulnerabilities (SQL
> injection, cross-claimant IDOR, SSRF, RCE, public S3 bucket, over-broad IAM,
> committed credentials, prompt injection, insecure CI/CD coding-agent
> workflows) and only fake/placeholder secrets and fake AWS accounts. **Do not
> deploy it, do not copy any code or workflow from it, and do not treat any
> pattern here as guidance.** It exists only to be scanned.

## What this is

**claims-agent** is a fictional insurance-claims service built on a small
in-repo agent **framework**. The framework defines a base `Agent` runtime,
`@Tool`-annotated methods, and an `AiServices` factory (LangChain4j / Spring-AI
style). On top of it, the operational product runs a `ClaimsAgent` (principal) +
`TriageAgent` (nested sub-agent) + a Kotlin `FraudScorer`, and an `examples/`
directory ships demo agents. It validates all of Trent's analyses at once:

1. **Threat model** — a conventional JVM web/data service on AWS with classical
   threats (SQLi, IDOR, SSRF, RCE, IAM, encryption, secrets).
2. **Agentic inventory posture** — JVM idioms: a base-class + `@Tool`-annotation
   framework, concrete runtimes (Java + Kotlin), a nested sub-agent, an
   **MCP-client-only** setup (no own server), and `prompts/*.yaml` templates.
   The `examples/` demo agents must be tagged **advisory**, not operational.
3. **Agentic deployment-automation posture** — CI/CD runs Claude Code, OpenAI
   Codex, and a shelled-in aider agent via GitHub Actions, with a mix of safe
   and unsafe config.

## JVM agentic idioms exercised (the recall target)

| Construct | Where | Idiom |
|---|---|---|
| Tool | `@Tool`-annotated methods | annotation-based tools (LangChain4j/Spring-AI) |
| Framework base | `framework/Agent.java` | base runtime + `AiServices` factory |
| Principal runtime | `agent/ClaimsAgent.java` | extends `Agent`, `@Tool` methods |
| Sub-agent | `agent/TriageAgent.java` | nested, read-only |
| Kotlin runtime | `agent/FraudScorer.kt` | Kotlin agent on the same framework |
| Example agents | `examples/*.java` | DEMO agents → advisory rollup |
| Prompt templates | `prompts/*.yaml` | NOT tools/skills (FP trap) |
| MCP clients | `.mcp.json` | trent + github (external); **no own server** |

## False-positive traps (must NOT be inventoried as agents/tools)

- `framework/LlmClient.java` — model transport, no agency.
- `detect/CliAgentDetector.java` — recognizes coding-agent CLIs by config-file
  probing. Agent *names*, switched on, but no runtime.
- `prompts/*.yaml` — prompt templates, not tools or skills.
- `examples/*.java` — real agents, but DEMOS → advisory, not operational fleet.

## Layer → threat map

| Layer | Carries threats of type |
|---|---|
| `api/` | JWT/claimant-context integrity, authorization |
| `data/` | SQL injection, cross-claimant IDOR, PII handling |
| `infra/` | public buckets, encryption-at-rest, IAM least-privilege, committed creds |
| `agent/` + `framework/` | prompt injection, RCE/SSRF tools, inter-agent trust |
| `.github/workflows/` | CI/CD coding-agent posture (untrusted-input, token scope, trifecta) |

## Note for maintainers

The ground-truth label set lives with the Trent design docs, not in this repo.

## Build / test

```bash
./gradlew build   # Gradle (Kotlin + Java); or javac src/main/java + kotlinc src/main/kotlin
```
