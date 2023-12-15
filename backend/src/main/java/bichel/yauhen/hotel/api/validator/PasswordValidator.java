package bichel.yauhen.hotel.api.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Password validator
 */
public class PasswordValidator {
    /*
    ^                 # start-of-string
    (?=.*[0-9])       # a digit must occur at least once
    (?=.*[a-z])       # a lower case letter must occur at least once
    (?=.*[A-Z])       # an upper case letter must occur at least once
    (?=.*[@#$%^&+=])  # a special character must occur at least once
    (?=\S+$)          # no whitespace allowed in the entire string
    .{8,}             # anything, at least eight places though
    $                 # end-of-string
     */
    private final String PASSWORD_REG_EXPR = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";

    public boolean isValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REG_EXPR);
        Matcher matcher = pattern.matcher(password);

        return matcher.find();
    }
}
