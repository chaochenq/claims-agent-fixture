package com.acme.claims.agent

import com.acme.claims.data.Db
import com.acme.claims.framework.Agent
import com.acme.claims.framework.LlmClient
import com.acme.claims.framework.Tool

/**
 * FraudScorer — a Kotlin agent runtime. Extends the same framework [Agent] base
 * (a JVM runtime written in Kotlin), with its own read-only tool. Exercises the
 * `.kt` language surface of the JVM fixture.
 */
class FraudScorer(llm: LlmClient) : Agent(llm, 6) {
    private val db = Db("jdbc:postgresql://db/claims")
    private var claimId: String = ""

    @Tool(name = "score_claim", description = "Score a claim for fraud risk", capability = "read")
    fun scoreClaim(): String {
        return "risk score for ${db.status(claimId)}"
    }
}
