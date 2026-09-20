package config.kafka.event;

import java.math.BigDecimal;

// LOAN = وام
public record LoanLifecyclePayload(
        String loanId,
        String borrowerId,
        String collateralPositionId,
        String status,
        BigDecimal outstandingPrincipal,
        String currency
) implements EventPayload { }
