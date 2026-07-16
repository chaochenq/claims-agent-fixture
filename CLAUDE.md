# CLAUDE.md — claims-agent

Guidance for Claude Code (and other coding agents) working in this repository.

## Product

claims-agent is an insurance claims-processing service built on a small in-repo
agent framework. Adjusters chat with a ClaimsAgent that looks up claims, triages
fraud risk (a sub-agent + a Kotlin fraud scorer), and approves payouts.

## Architecture

- `framework/` — the agent framework: base `Agent`, `@Tool` annotation,
  `AiServices` factory, `LlmClient` transport.
- `agent/` — the operational runtimes: `ClaimsAgent` (principal), `TriageAgent`
  (nested sub-agent), and the Kotlin `FraudScorer`.
- `data/` — the JDBC data client. `api/` — the chat controller.
- `examples/` — DEMO agents (not part of the operational product).
- `prompts/` — system-prompt YAML templates.
- `infra/` — Terraform for AWS resources.

## Conventions

- Java 21 + Kotlin, Gradle. Use `./gradlew build` / `./gradlew test`.
- **Claimant scoping is the most important invariant.** Every data-tier read/
  write MUST be scoped to the verified claimant, never from request/model-supplied
  ids.
- Never build SQL by string concatenation — use prepared statements.
- Secrets come from the environment / Secrets Manager, never hardcoded.

## Working with agents in this repo

- **MCP servers** (`.mcp.json`): `trent`, `github` (both external; this repo
  ships no own MCP server).
- **Skills** (`.claude/skills/`): `code-review`, `run-tests`, `deploy-to-prod`.
- Before merging a PR, run `code-review` and address High/Critical findings.
