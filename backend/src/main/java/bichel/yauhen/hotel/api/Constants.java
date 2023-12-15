package bichel.yauhen.hotel.api;

/**
 * Utility class for constants in wep application
 */
public final class Constants {
    private Constants() {}

    /**
     * Content type application/json; charset=UTF-8
     */
    public static final String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";
    /**
     * Key for header Access-Control-Allow-Origi
     */
    public static final String KEY_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    /**
     * Key for session user id attribute
     */
    public static final String KEY_SESSION_USER_ID = "userId";

    public static boolean OBTAIN_EXISTING_SESSION_AND_NOT_CREATE_A_NEW_ONE = false;

    public static final String ERROR_PASSWORD_NOT_VALID = "PASSWORD_NOT_VALID";
    public static final String ERROR_EMAIL_NOT_VALID = "EMAIL_NOT_VALID";
    public static final String ERROR_ACCOUNT_DUPLICATE = "USER_EXISTS";
    public static final String ERROR_ACCOUNT_NOT_FOUND = "USER_NOT_FOUND";
}
