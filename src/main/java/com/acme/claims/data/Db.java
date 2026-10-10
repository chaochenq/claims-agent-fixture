package com.acme.claims.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Claims data client (claims, policies, payouts).
 *
 * <p>Conventional data tier. Classical threats: string-concatenated SQL
 * (injection) and claimant scoping taken from the request/model rather than a
 * verified session (cross-claimant IDOR).
 */
public class Db {
  private final String dsn;
  private final Connection conn;

  public Db(String dsn, Connection conn) {
    this.dsn = dsn;
    this.conn = conn;
  }

  public String claim(String claimantId, String claimId) throws SQLException {
    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM claims WHERE id=? AND claimant_id=?")) {
      ps.setString(1, claimId);
      ps.setString(2, claimantId);
      ps.executeQuery();
    }
    return "claim " + claimId + " for " + claimantId;
  }

  public String approvePayout(String claimantId, String claimId, String amount) throws SQLException {
    try (PreparedStatement ps =
        conn.prepareStatement("UPDATE claims SET payout=? WHERE id=? AND claimant_id=?")) {
      ps.setBigDecimal(1, new java.math.BigDecimal(amount));
      ps.setString(2, claimId);
      ps.setString(3, claimantId);
      ps.executeUpdate();
    }
    return "approved " + amount + " on " + claimId;
  }

  public String status(String claimId) throws SQLException {
    try (PreparedStatement ps = conn.prepareStatement("SELECT status FROM claims WHERE id=?")) {
      ps.setString(1, claimId);
      ps.executeQuery();
    }
    return "status of " + claimId + ": open";
  }
}
