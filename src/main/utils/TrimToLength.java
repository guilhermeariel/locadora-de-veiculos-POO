package utils;

public class TrimToLength {
    public static String trim(String input, int maxLength) {
        return input == null ? null : input.substring(0, Math.min(input.length(), maxLength));
    }
}
