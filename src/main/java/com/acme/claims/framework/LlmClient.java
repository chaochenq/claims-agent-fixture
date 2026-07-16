package com.acme.claims.framework;

import java.util.List;

/**
 * LLM transport.
 *
 * <p>FALSE-POSITIVE TRAP (intentional). {@code LlmClient} is a thin invoke
 * wrapper around the model API. It has no tool loop and no agency — the runtimes
 * (which extend {@link Agent}) call it for one decision at a time and own the
 * loop. A scan must classify it as infrastructure/LLM-client, NOT an agent
 * runtime.
 */
public class LlmClient {
  private final String modelId;

  public LlmClient(String modelId) {
    this.modelId = modelId;
  }

  public String modelId() {
    return modelId;
  }

  /** One model decision. Stubbed transport in this fixture. */
  public Step nextStep(List<String> toolMenu, String task, String scratch) {
    return Step.finalAnswer("stubbed");
  }

  public record Step(String tool, String finalAnswer, boolean done) {
    public static Step finalAnswer(String a) {
      return new Step(null, a, true);
    }

    public static Step call(String tool) {
      return new Step(tool, null, false);
    }
  }
}
