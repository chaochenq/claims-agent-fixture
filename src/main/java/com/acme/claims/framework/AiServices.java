package com.acme.claims.framework;

/**
 * Framework factory (LangChain4j {@code AiServices}-style). Builds a configured
 * agent runtime from a subclass + model id. A framework helper, not itself a
 * runtime.
 */
public final class AiServices {
  private AiServices() {}

  public static <T extends Agent> T create(java.util.function.Function<LlmClient, T> ctor, String modelId) {
    return ctor.apply(new LlmClient(modelId));
  }
}
