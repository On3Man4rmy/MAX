import java.util.List;
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

    // https://stackoverflow.com/a/47232576
    public static int randomRange(int start, int end, List<Integer> excepts) {
        Random ran = new Random();
        int size = excepts.size();
        int range = end - start + 1 - size;
        int randNum = ran.nextInt(range) + start;
        excepts.sort(null); // sort excluding values in ascending order
        int i=0;
        for(int except : excepts) {
            if(randNum < except-i){
                return randNum + i;
            }
            i++;
        }
        return randNum + i;
    }
}
