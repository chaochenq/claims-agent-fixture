package com.acme.claims.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * GET /v1/claims/{claimId}/attachments/{name} — returns a file an adjuster or
 * claimant uploaded to a claim (photos, repair estimates, police reports).
 */
public class AttachmentController {
  private static final Path ROOT = Paths.get("/var/claims/attachments").toAbsolutePath().normalize();

  public byte[] download(String claimId, String name) throws IOException {
    Path claimDir = ROOT.resolve(claimId).normalize();
    Path file = claimDir.resolve(name).normalize();
    if (!claimDir.getParent().equals(ROOT) || !file.getParent().equals(claimDir)) {
      throw new IllegalArgumentException("attachment is outside its claim");
    }
    if (!Files.isRegularFile(file)) {
      throw new IllegalArgumentException("no such attachment");
    }
    return Files.readAllBytes(file);
  }
}
