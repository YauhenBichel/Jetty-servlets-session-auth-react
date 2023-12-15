package bichel.yauhen.hotel.api.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email validator
 */
public class EmailValidator {
    public boolean isValid(String email) {
        Pattern pattern = Pattern.compile("[_A-Za-z0-9]+@[A-Za-z0-9]+(\\.[A-Za-z]{2,})");
        Matcher matcher = pattern.matcher(email);

        return matcher.find();
    }
}
