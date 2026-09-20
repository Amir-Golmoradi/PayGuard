package exception;

public class InvalidKafkaEventException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public InvalidKafkaEventException(String message) {
    super(message);
  }

  public InvalidKafkaEventException(String message, Throwable cause) {
    super(message, cause);
  }
}
