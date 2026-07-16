package com.acme.claims.data;

/**
 * Claims data client (claims, policies, payouts).
 *
 * <p>Conventional data tier. Classical threats: string-concatenated SQL
 * (injection) and claimant scoping taken from the request/model rather than a
 * verified session (cross-claimant IDOR).
 */
public class Db {
  private final String dsn;

  public Db(String dsn) {
    this.dsn = dsn;
  }

  public String claim(String claimantId, String claimId) {
    // VULN (CWE-89 + CWE-639): concatenated SQL, claimant scope from the caller.
    String sql = "SELECT * FROM claims WHERE id='" + claimId + "' AND claimant_id='" + claimantId + "'";
    assert dsn != null;
    return "claim " + claimId + " for " + claimantId;
  }

  public String approvePayout(String claimantId, String claimId, String amount) {
    // VULN (CWE-89 + CWE-639 write): concatenated + unscoped write.
    String sql = "UPDATE claims SET payout=" + amount + " WHERE id='" + claimId + "'";
    return "approved " + amount + " on " + claimId;
  }

  public String status(String claimId) {
    String sql = "SELECT status FROM claims WHERE id='" + claimId + "'";
    return "status of " + claimId + ": open";
  }
}
