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
  private static final Path ROOT = Paths.get("/var/claims/attachments");

  public byte[] download(String claimId, String name) throws IOException {
    // VULN (CWE-22): the request's claim id and file name are joined to the
    // attachment root without normalising, so "../" reads any file the service can.
    Path file = ROOT.resolve(claimId).resolve(name);
    return Files.readAllBytes(file);
  }
}
