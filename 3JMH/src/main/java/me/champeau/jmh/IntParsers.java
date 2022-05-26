package me.champeau.jmh;

import java.util.regex.Pattern;

public class IntParsers {

    private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");

    public static boolean parseInt(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static boolean parseChars(String str) {
        for (int i = 0, n = str.length(); i < n; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean parseRegex(String str) {
        return INT_PATTERN.matcher(str).matches();
    }

    public static boolean parseRegex2(String str)
    {
        return Pattern.compile("^\\d+$").matcher(str).matches();
    }
}