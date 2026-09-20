package config.kafka.event;

import java.math.BigDecimal;
import java.util.List;

public record WalletTransactionPayload(
    String transactionId, String transactionType, String idempotencyKey, List<Entry> entries)
    implements EventPayload {
  public record Entry(String accountId, BigDecimal amount, String currency) {}
}
