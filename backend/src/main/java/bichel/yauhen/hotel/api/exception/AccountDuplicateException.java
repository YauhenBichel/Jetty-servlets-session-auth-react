package bichel.yauhen.hotel.api.exception;

/**
 * AccountDuplicateException Exception
 */
public class AccountDuplicateException extends RuntimeException {
    public AccountDuplicateException(String message) {
        super(message);
    }
    /**
     * Exception raising with error message and attached throwable exception
     * @param message String
     * @param ex Throwable
     */
    public AccountDuplicateException(String message, Throwable ex) {
        super(message, ex);
    }
}
