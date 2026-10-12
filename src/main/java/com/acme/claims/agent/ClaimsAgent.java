package com.acme.claims.agent;

import com.acme.claims.data.Db;
import com.acme.claims.framework.Agent;
import com.acme.claims.framework.LlmClient;
import com.acme.claims.framework.Tool;

/**
 * ClaimsAgent — the principal claims-processing runtime.
 *
 * <p>Extends the framework {@link Agent}; its {@code @Tool} methods are the tool
 * surface the framework advertises to the model. Delegates fraud checks to the
 * {@link com.acme.claims.agent.TriageAgent} sub-agent.
 */
public class ClaimsAgent extends Agent {
  private final Db db = new Db("jdbc:postgresql://db/claims");
  private final TriageAgent triage;
  // Request context — claimantId is derived from the (unverified) request, then
  // used by the tools below, breaking claimant scoping.
  private String claimantId = "unknown";
  private String claimId = "";

  public ClaimsAgent(LlmClient llm) {
    super(llm, 12);
    this.triage = new TriageAgent(new LlmClient("claude-3-5-haiku"));
  }

  public ClaimsAgent forRequest(String claimantId, String claimId) {
    this.claimantId = claimantId;
    this.claimId = claimId;
    return this;
  }

  @Tool(name = "lookup_claim", description = "Look up a claim", capability = "read")
  public String lookupClaim() {
    return db.claim(claimantId, claimId);
  }

  @Tool(name = "approve_payout", description = "Approve a claim payout", capability = "write")
  public String approvePayout() {
    String verdict = triage.run("assess fraud risk for " + claimId);
    return db.approvePayout(claimantId, claimId, "1000") + " (" + verdict + ")";
  }

  private static final java.util.regex.Pattern CLAIM_ID = java.util.regex.Pattern.compile("^[A-Za-z0-9_-]{1,64}$");
  private static final String POLICY_HOST = "policies.internal";

  @Tool(name = "fetch_policy", description = "Fetch an external policy document", capability = "egress")
  public String fetchPolicy() {
    if (!CLAIM_ID.matcher(claimId).matches()) {
      return "error: invalid claim id";
    }
    try {
      var uri = new java.net.URI("https", POLICY_HOST, "/policies/" + claimId, null);
      if (!POLICY_HOST.equals(uri.getHost())) {
        return "error: policy host not allowed";
      }
      return uri.toURL().getHost();
    } catch (Exception e) {
      return "error";
    }
  }

  @Tool(name = "run_report", description = "Run a reporting script", capability = "exec")
  public String runReport() {
    try {
      Process p = new ProcessBuilder("report", claimId).start();
      return new String(p.getInputStream().readAllBytes());
    } catch (Exception e) {
      return "error";
    }
  }
}
