package com.acme.claims.agent;

import com.acme.claims.data.Db;
import com.acme.claims.framework.Agent;
import com.acme.claims.framework.LlmClient;
import com.acme.claims.framework.Tool;

/**
 * TriageAgent — a nested sub-agent runtime under ClaimsAgent. Read-only tool
 * surface; assesses fraud risk / claim status.
 */
public class TriageAgent extends Agent {
  private final Db db = new Db("jdbc:postgresql://db/claims");
  private String claimId = "";

  public TriageAgent(LlmClient llm) {
    super(llm, 4);
  }

  @Tool(name = "get_claim_status", description = "Get a claim's status", capability = "read")
  public String getClaimStatus() {
    return db.status(claimId);
  }
}
