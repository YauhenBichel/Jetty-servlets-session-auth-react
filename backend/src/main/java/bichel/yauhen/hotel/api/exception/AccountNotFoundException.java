package bichel.yauhen.hotel.api.exception;

/**
 * AccountNotFoundException Exception
 */
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
    /**
     * Exception raising with error message and attached throwable exception
     * @param message String
     * @param ex Throwable
     */
    public AccountNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }
}
