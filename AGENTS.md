# AGENTS.md — claims-agent

Instruction file for coding agents (Codex, Cursor, aider, etc.) operating in this
repo. Mirrors `CLAUDE.md`.

- Build: `./gradlew build`. Test: `./gradlew test`.
- Preserve claimant scoping (verified claimant, never request/model-supplied).
- Prepared statements only; no hardcoded secrets.
- The framework is in `framework/`; operational runtimes are ClaimsAgent,
  TriageAgent, and the Kotlin FraudScorer. `examples/` is demos only.
