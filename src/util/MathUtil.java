package util;

import java.util.Random;

/**
 * @author Melanie Krugel 198991, Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 2.0 08.01.2018
 */
public class MathUtil {
    public static int digits(long number) {
        return String.valueOf(number).length();
    }

    // https://stackoverflow.com/a/363732
    public static int randomRange(int min, int max) {
        Random ran = new Random();
        return  min + ran.nextInt(max - min+ 1);
    }


    // https://stackoverflow.com/a/33889423
    public static double roundToDecimalPlaces(double value, int decimalPlaces)
    {
        double shift = Math.pow(10,decimalPlaces);
        return Math.round(value*shift)/shift;
    }
}
