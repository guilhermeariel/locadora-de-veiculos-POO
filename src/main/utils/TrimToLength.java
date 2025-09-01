package utils;

public class TrimToLength {
    public static String trim(String input, int maxLength) {
        if (input == null) {
            return null;
        }
        return input.length() <= maxLength ? input : input.substring(0, maxLength);
    }
}
