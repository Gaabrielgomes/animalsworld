package animalsworld.api.infra.exception;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) { super(message); }
}
