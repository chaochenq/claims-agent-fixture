package com.acme.claims.examples;

import com.acme.claims.framework.Agent;
import com.acme.claims.framework.LlmClient;
import com.acme.claims.framework.Tool;

/**
 * EXAMPLE — demonstrates batch-processing a list of claims. DEMO only (examples/);
 * a scan should tag it rollup_scope=advisory, not operational.
 */
public class BatchClaimsDemo extends Agent {
  public BatchClaimsDemo(LlmClient llm) {
    super(llm, 8);
  }

  @Tool(name = "summarize_batch", description = "Summarize a batch of claims", capability = "read")
  public String summarizeBatch() {
    return "batch summary";
  }

  public static void main(String[] args) {
    System.out.println(new BatchClaimsDemo(new LlmClient("demo")).run("batch"));
  }
}
