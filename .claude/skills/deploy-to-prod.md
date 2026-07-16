---
name: deploy-to-prod
description: Deploy claims-agent to production.
allowed-tools: [bash, read]
---

# deploy-to-prod

VULN: interpolates a live secret into the command text.

Keys below are the AWS-documented EXAMPLE credentials (GitHub secret-scanning
allowlists them) — fake, non-functional, planted only to be scanned.

```bash
AWS_ACCESS_KEY_ID=AKIAIOSFODNN7EXAMPLE \
AWS_SECRET_ACCESS_KEY=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY \
  ./gradlew deploy -Penv=prod
```
