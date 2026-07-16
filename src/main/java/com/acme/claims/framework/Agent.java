package com.acme.claims.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Base agent runtime (framework). Subclasses annotate their tool methods with
 * {@link Tool}; the base class discovers them by reflection, runs the ReAct loop
 * against an {@link LlmClient}, and dispatches the model's chosen tool.
 *
 * <p>A concrete subclass of this IS an agent runtime. The abstract base itself
 * has no tools of its own.
 */
public abstract class Agent {
  protected final LlmClient llm;
  private final int maxSteps;

  protected Agent(LlmClient llm, int maxSteps) {
    this.llm = llm;
    this.maxSteps = maxSteps;
  }

  /** The tool names this agent exposes to the model (its @Tool methods). */
  public List<String> toolMenu() {
    List<String> names = new ArrayList<>();
    for (Method m : getClass().getDeclaredMethods()) {
      Tool t = m.getAnnotation(Tool.class);
      if (t != null) {
        names.add(t.name());
      }
    }
    return names;
  }

  /** Run the ReAct loop until the model produces a final answer. */
  public String run(String task) {
    StringBuilder scratch = new StringBuilder();
    for (int i = 0; i < maxSteps; i++) {
      LlmClient.Step step = llm.nextStep(toolMenu(), task, scratch.toString());
      if (step.done()) {
        return step.finalAnswer();
      }
      scratch.append("\n").append(dispatch(step.tool()));
    }
    return "step budget exhausted";
  }

  /** Invoke the named @Tool method by reflection. */
  protected String dispatch(String toolName) {
    for (Method m : getClass().getDeclaredMethods()) {
      Tool t = m.getAnnotation(Tool.class);
      if (t != null && t.name().equals(toolName)) {
        try {
          Object r = m.invoke(this);
          return String.valueOf(r);
        } catch (ReflectiveOperationException e) {
          return "error: " + e.getMessage();
        }
      }
    }
    return "unknown tool: " + toolName;
  }
}
