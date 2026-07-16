---
name: code-review
description: Review a diff for bugs and risky patterns (read-only).
allowed-tools: [bash, read]
---

# code-review

Read the staged diff and flag correctness bugs, unchecked exceptions, and
injection-prone string-concatenated SQL. Do not modify files.
