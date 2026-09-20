package config.kafka.event;

public final class EventTypes {
   public static final String LOAN_APPROVED = "LOAN_APPROVED";
   public static final String LOAN_DISBURSED = "LOAN_DISBURSED";
   public static final String REPAYMENT_APPLIED = "REPAYMENT_APPLIED";
   public static final String LOAN_PAID_OFF = "LOAN_PAID_OFF";
   public static final String LOAN_DEFAULTED = "LOAN_DEFAULTED";

   public static final String LEDGER_TRANSACTION_POSTED = "LEDGER_TRANSACTION_POSTED";


   public static final String MARGIN_CALL_TRIGGERED = "MARGIN_CALL_TRIGGERED";
   public static final String MARGIN_CALL_RESOLVED = "MARGIN_CALL_RESOLVED";
   public static final String LIQUIDATION_TRIGGERED = "LIQUIDATION_TRIGGERED";

   private EventTypes() {
   }
}