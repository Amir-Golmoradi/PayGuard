package config.kafka;

/**
 * Central registry of Kafka topic names.
 *
 * <p>Every service — producer or consumer — references topics through these constants instead of
 * hardcoded strings, so a rename never turns into a silent runtime mismatch between services.
 */
public final class KafkaTopics {

  /** Emitted by wallet-service whenever a ledger transaction is posted (transfer or capture). */
  public static final String WALLET_TRANSACTIONS = "wallet.transactions";

  /** Emitted by loan-service on approval, disbursement, repayment, default, and payoff. */
  public static final String LOAN_LIFECYCLE = "loan.lifecycle";

  /** Emitted by collateral-service on margin calls and liquidation. */
  public static final String COLLATERAL_RISK_EVENTS = "collateral.risk-events";

  private KafkaTopics() {}
}
