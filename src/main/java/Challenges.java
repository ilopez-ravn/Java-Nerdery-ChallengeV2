/* (C)2024 */
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/* (C)2024 */
public class Challenges {

    /* *****
    Challenge 1

    "Readable Time"

    The function "readableTime" accepts a positive number as argument,
    you should be able to modify the function to return the time from seconds
    into a human readable format.

    Example:

    Invoking "readableTime(3690)" should return "01:01:30" (HH:MM:SS)
    ***** */

    public String readableTime(Integer seconds) {
        int minutes = 0;
        int hours = 0;
        if(seconds >= 60 ) {
            minutes = seconds / 60;
            seconds %= 60;
        }

        if (minutes >= 60) {
            hours = minutes / 60;
            minutes %= 60;
        }

        return String.format("%02d",hours)+":"+
                String.format("%02d",minutes)+":"+
                String.format("%02d",seconds);
    }
    ;

    /* *****
    Challenge 2

    "Circular Array"

    Given the following array "COUNTRY_NAMES", modify the function "circularArray"
    to return an array that meets the following criteria:

    - The index number passed to the function should be the first element in the resulting array
    - The resulting array must have the same length as the initial array
    - The value of the argument "index" will always be a positive number

    Example:

    Invoking "circularArray(2)" should return "["Island", "Japan", "Israel", "Germany", "Norway"]"
    ***** */

    public String[] circularArray(int index) {
        String[] COUNTRY_NAMES = {"Germany", "Norway", "Island", "Japan", "Israel"};
        String[] COUNTRY_NAMES_CP = {"","","","",""};
        for(int i = 0; i < COUNTRY_NAMES.length; ++i)
            COUNTRY_NAMES_CP[i] = COUNTRY_NAMES[(index+i)%COUNTRY_NAMES.length];
        return COUNTRY_NAMES_CP;
    }
    ;

    /* *****
    Challenge 3

    "Own Powers"

    The function "ownPower" accepts two arguments. "number" and "lastDigits".

    The "number" indicates how long is the series of numbers you are going to work with, your
    job is to multiply each of those numbers by their own powers and after that sum all the results.

    "lastDigits" is the length of the number that your function should return, as a string!.
    See example below.

    Example:

    Invoking "ownPower(10, 3)" should return "317"
    because 1^1 + 2^2 + 3^3 + 4^4 + 5^5 + 6^6 + 7^7 + 8^8 + 9^9 + 10^10 = 10405071317
    The last 3 digits for the sum of powers from 1 to 10 is "317"
    ***** */

    public long Power(int n, int length) {
        long result = n;
        // Only save length digits
        for(int i = 1; i < n; ++i) {
            result = (result * n) % ((long) Math.pow(10,length));
        }
        return result;
    }

    public String ownPower(int number, int lastDigits) {
        long result = 1;
        for(int i=2; i<=number; ++i) {
            result += Power(i, lastDigits+1);
        }

        String resultString = String.valueOf(result);
        return resultString.substring(resultString.length()-lastDigits);
    }
    ;

    /* *****
    Challenge 4

    "Sum of factorial digits"

    A factorial (x!) means x * (x - 1)... * 3 * 2 * 1.
    For example: 10! = 10 × 9 × ... × 3 × 2 × 1 = 3628800

    Modify the function "digitSum" to return a number that
    equals to the sum of the digits in the result of 10!

    Example:

    Invoking "digitSum(10)" should return "27".
    Since 10! === 3628800 and you sum 3 + 6 + 2 + 8 + 8 + 0 + 0
    ***** */

    public Integer digitSum(int n) {
        // save number on array for large numbers
        int maxLength = 100000;

        int[] factorial = new int[maxLength];
        int[] result = new int[maxLength];
        factorial[(maxLength-1)] = 1; // init value for mult
        int remainder = 0;
        int factorialLength = 1;

        for(int i=1;i<=n;++i) {
            int[] number = new int [100];

            Arrays.fill(result, 0);

            // save number into array
            String num = String.valueOf(i);
            for (int j=0; j < num.length(); ++j) {
                number[99-j] = Integer.parseInt(String.valueOf(num.charAt(num.length()-j-1)));
            }

            // do array multiplication
            int position = (maxLength-1);
            for(int ni = 99; ni > 99 - num.length(); --ni) {
                for(int fi = (maxLength-1); fi > (maxLength-1) - factorialLength; --fi) {
                    position = fi - (99 - ni);
                    int op = factorial[fi] * number[ni];
                    while (op >= 10) { op-=10; remainder++; }

                    result[position] += op;
                    while (result[position] >= 10 ) {
                        result[position] -= 10;
                        remainder++;
                    }
                    result[position-1] += remainder;
                    if((maxLength-1)-factorialLength  == fi - 1 )
                        if(remainder>0) position--;
                    remainder = 0;
                    position--;
                }

            }

            factorialLength = maxLength - position;

            // copy value of result into factorial
            factorial = result.clone();

        }

        String strFact = "";
        for(int i=(maxLength-1) - factorialLength - 1; i <= (maxLength-1); ++i) {
            strFact += result[i];
        }

        System.out.println();
        int resultInt = 0;
        while (!strFact.isEmpty()) {
            int digit = Integer.parseInt(strFact.substring(0, 1));
            strFact = strFact.substring(1);
            resultInt += digit;
        }

        return resultInt;
    }

    /**
     * Decryption.
     * Create a decryption function that takes as parameter an array of ASCII values.The addition between values is the ascii value decrypted.
     * decrypt([ 72, 33, -73, 84, -12, -3, 13, -13, -68 ]) ➞ "Hi there!"
     * H = 72, the sum of H 72 and 33 gives 105 which ascii value is i;
     * The function must return the string encoded using the encryption function below.
     *
     * @param ascivalues  hand, player2 hand
     */
    public String decrypt(List<Integer> ascivalues) {
        int sum = 0;
        String strValue = "";
        for(int i = 0; i < ascivalues.size(); ++i){
            sum += ascivalues.get(i);
            strValue += (char) sum;
        }
        return strValue;
    }

    /**
     * Encryption Function.
     * Create am encryption function that takes a string and converts into an array of ASCII character values.
     * encrypt("Hello") ➞ [72, 29, 7, 0, 3]
     * // H = 72, the difference between the H and e is 29
     * The function must return an array of integer ascii values.
     *
     * @param text  hand, player2 hand
     */
    public List<Integer> encrypt(String text) {
        List<Integer> ascivalues = new ArrayList<>();
        int num = (int) text.charAt(0);
        ascivalues.add(num);

        for(int i = 1; i < text.length(); ++i) {
            num = (int) (text.charAt(i)) - (int) (text.charAt(i-1));
            ascivalues.add(num);
        }
        return ascivalues;
    }
}
