package config.kafka.topic;

/** Central registry of Kafka topic names. */
public final class PayGuardKafkaTopics {
  public static final String WALLET_TRANSACTIONS = "wallet.transactions";
  public static final String LOAN_LIFECYCLE = "loan.lifecycle";
  public static final String COLLATERAL_RISK_EVENTS = "collateral.risk-events";
  public static final String WALLET_TRANSACTIONS_DLT = WALLET_TRANSACTIONS + ".DLT";
  public static final String LOAN_LIFECYCLE_DLT = LOAN_LIFECYCLE + ".DLT";
  public static final String COLLATERAL_RISK_EVENTS_DLT = COLLATERAL_RISK_EVENTS + ".DLT";

  private PayGuardKafkaTopics() {}
}
