package uk.co.idv.identity.adapter.eligibility.external.data.alias;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstDigitIncrementer {

    private static final Pattern FIRST_DIGIT_PATTERN = Pattern.compile("\\d{1}");

    private FirstDigitIncrementer() {
        // utility class
    }

    public static String incrementFirstDigit(String value) {
        Matcher matcher = FIRST_DIGIT_PATTERN.matcher(value);
        if (matcher.find()) {
            String firstDigit = matcher.group(0);
            return value.replaceFirst(firstDigit, increment(firstDigit));
        }
        throw new NoDigitsFoundException(value);
    }

    private static String increment(String value) {
        int intValue = Integer.parseInt(value);
        if (intValue >= 9) {
            return "0";
        }
        return Integer.toString(intValue + 1);
    }

}
