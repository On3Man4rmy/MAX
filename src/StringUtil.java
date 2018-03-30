/**
 * String utilities
 * @author Melanie Krugel 198991, Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 2.0 08.01.2018
 */
public class StringUtil {
    public static String repeat(String text, int count) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < count; i++) {
            s.append(text);
        }
        return s.toString();
    }
}
