package com.acme.claims.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method as a tool the agent may invoke (LangChain4j / Spring-AI style).
 * The framework scans an agent's {@code @Tool} methods to build the model's tool
 * menu; a method annotated here is a first-class agent tool.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Tool {
  String name();

  String description() default "";

  /** read / write / exec / egress — used only for posture description. */
  String capability() default "read";
}
