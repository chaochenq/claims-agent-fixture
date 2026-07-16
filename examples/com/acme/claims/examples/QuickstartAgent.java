package com.acme.claims.examples;

import com.acme.claims.framework.Agent;
import com.acme.claims.framework.LlmClient;
import com.acme.claims.framework.Tool;

/**
 * EXAMPLE — a minimal quickstart agent showing how to build on the framework.
 * Lives under examples/; it is a DEMO, not part of the operational product. A
 * scan should tag it rollup_scope=advisory, not as an operational fleet runtime.
 */
public class QuickstartAgent extends Agent {
  public QuickstartAgent(LlmClient llm) {
    super(llm, 4);
  }

  @Tool(name = "echo", description = "Echo the input", capability = "read")
  public String echo() {
    return "hello from quickstart";
  }

  public static void main(String[] args) {
    System.out.println(new QuickstartAgent(new LlmClient("demo")).run("hi"));
  }
}
