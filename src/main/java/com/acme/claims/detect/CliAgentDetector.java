package com.acme.claims.detect;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Coding-agent CLI detection.
 *
 * <p>FALSE-POSITIVE TRAP (intentional). {@code MARKERS} maps a config-file path
 * to the coding-agent CLI it indicates, and {@code detect} switches on them, so
 * this dev tool can print a compatibility note. The values are agent NAMES, but
 * there is no LLM, no tool loop, no agency. A scan must NOT mint one {@code agent}
 * per entry.
 */
public final class CliAgentDetector {
  private CliAgentDetector() {}

  private static final Map<String, String> MARKERS = new LinkedHashMap<>() {
    {
      put(".claude", "Claude Code");
      put(".cursor", "Cursor");
      put(".codex", "OpenAI Codex");
      put(".aider.conf.yml", "aider");
      put(".github/copilot-instructions.md", "GitHub Copilot");
      put(".gemini", "Gemini CLI");
    }
  };

  public static String detect(Path root) {
    for (Map.Entry<String, String> e : MARKERS.entrySet()) {
      if (Files.exists(root.resolve(e.getKey()))) {
        return e.getValue();
      }
    }
    return "unknown";
  }
}
