package com.acme.claims.api;

import com.acme.claims.agent.ClaimsAgent;
import com.acme.claims.framework.AiServices;

/**
 * POST /v1/chat — the adjuster entry point.
 *
 * <p>Builds the ClaimsAgent and runs it on the message. Untrusted adjuster/
 * claimant text enters the agent loop here — the head of the cross-surface chain
 * (prompt-injection -> claim tools -> cross-claimant data or fraudulent payout).
 */
public class ChatController {

  public String handleChat(String message, String jwt) {
    // NOTE: claimant is derived from the (unverified) JWT claim; the agent's
    // tools then operate with request-supplied ids, breaking scoping.
    String claimant = claimantFromJwt(jwt);
    ClaimsAgent agent = AiServices.create(ClaimsAgent::new, "claude-3-5-sonnet").forRequest(claimant, "CLM-1");
    return agent.run(message);
  }

  private String claimantFromJwt(String jwt) {
    String[] parts = jwt.split("\\.");
    return parts.length > 1 ? parts[1] : "claimant-unknown";
  }
}
