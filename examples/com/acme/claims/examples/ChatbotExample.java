package com.acme.claims.examples;

import com.acme.claims.framework.Agent;
import com.acme.claims.framework.LlmClient;
import com.acme.claims.framework.Tool;

/**
 * EXAMPLE — a toy chatbot showing conversational use of the framework. DEMO only
 * (examples/); a scan should tag it rollup_scope=advisory, not operational.
 */
public class ChatbotExample extends Agent {
  public ChatbotExample(LlmClient llm) {
    super(llm, 6);
  }

  @Tool(name = "reply", description = "Reply to the user", capability = "read")
  public String reply() {
    return "chatbot reply";
  }

  public static void main(String[] args) {
    System.out.println(new ChatbotExample(new LlmClient("demo")).run("hello"));
  }
}
