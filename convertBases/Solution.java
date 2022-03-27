import java.util.Scanner;
import java.lang.Math;

public class Solution {
    public static void main (String args[]) {

        Scanner sc = new Scanner(System.in);

        int old_base = sc.nextInt();
        int new_base = sc.nextInt();
        sc.nextLine();
        String number = sc.nextLine();

        sc.close();

        String number_10 = convertToBaseTen(number, old_base);
        System.out.println(convertToNewBase(number_10, new_base));
    }

    public static String convertToBaseTen (String number, int old_base) {

        int number_10 = 0;
        int power = 0;

        for(int i = number.length() - 1; i >= 0; i--){
            number_10 += (number.charAt(i) - '0')*Math.pow(old_base, power);
            power++;
        }

        return "" + number_10;
    }

    public static String convertToNewBase(String number, int new_base) {

        String number_newBase = "";

        int power = 0;

        int numberToInt = Integer.parseInt(number);

        while(numberToInt > 0){

            while(Math.pow(new_base, power) < numberToInt){
                power++;
            }

            power--;

            int multiple = 1;

            while(Math.pow(new_base,power)*multiple <= numberToInt){
                multiple++;
            }
            
            multiple--;

            numberToInt -= Math.pow(new_base, power)*multiple;

            number_newBase = number_newBase + Integer.toString(multiple);
        }

        return number_newBase;
    }
}
