import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class collatzCalculator{
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        int min = scanner.nextInt();
        int max = scanner.nextInt();
        int index = scanner.nextInt();
        scanner.close();

        HashMap<Integer, Integer> unsortedMap = collatzLengthFromRange(min, max);
        System.out.println(getElement(unsortedMap, min, max, index));
    }
    public static HashMap<Integer, Integer> collatzLengthFromRange(int min, int max){
        HashMap<Integer, Integer> collatzLengthMap = new HashMap<Integer,Integer>();
        
        for(int i = min; i <= max; i++){
            collatzLengthMap.put(i, collatzLength(i));
        }
        return collatzLengthMap;
    }
    public static int collatzLength(int element){
        if(element == 0 || element == 1){
            return 0;
        }
        int length = 0;

        while(element != 1){
            if(element%2 == 0){     //if element is even
                element = element/2;
            }
            else{                   //if element is odd
                element = 3*element + 1;
            }
            length++;
        }

        return length;
    }
    public static int getElement(HashMap<Integer, Integer> unsortedMap, int min, int max, int index){
        if((max - min) <= index){
            return 0;
        }
            
        List<Integer> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        for(int i = min; i <= max; i++){
            keys.add(i);
            values.add(unsortedMap.get(i));
        }
        sort(keys, values, 0, values.size() - 1);
        
        
        return keys.get(index - 1);
    }
    static void swap(List<Integer> keys, List<Integer> values, int i, int j){
        int tempVal = values.get(i);
        int tempKey = keys.get(i);
        values.set(i, values.get(j));
        keys.set(i, keys.get(j));
        values.set(j, tempVal);
        keys.set(j, tempKey);
    }
    static int partition(List<Integer> keys, List<Integer> values, int low, int high){
        int pivot = values.get(high);
        int i = (low - 1);
        for(int j = low; j <= high - 1; j++){
            if(values.get(j) < pivot){
                i++;
                swap(keys, values, i, j);
            }
        }
        swap(keys, values, i + 1, high);
        return i + 1;
    }
    static void sort(List<Integer> keys, List<Integer> values, int low, int high){
        if(low < high){
            int p = partition(keys, values, low, high);
            sort(keys, values, low, p - 1);
            sort(keys, values, p + 1, high);
        }
    }
}