import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class collatzCalculator{
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        long min = scanner.nextLong();
        long max = scanner.nextLong();
        int index = scanner.nextInt();
        scanner.close();

        List<Long> values = new ArrayList<>();
        List<Long> lengths = new ArrayList<>();
        //HashMap<Long, Long> lengthMap = new HashMap<>();

        for(long i = min; i <= max; i++){
            values.add(i);
            lengths.add(collatzLength(i));
            //lengthMap.put(i, collatzLength(i));
        }
        if(index == 0){
            index++;
        }
        sort(values, lengths, 0, lengths.size() - 1);
        sortByValue(values, lengths);
        
        System.out.println(values.get(index - 1));
    }
    
    public static Long collatzLength(Long element){
        Long length = (long) 0;

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
    static void sortByValue(List<Long> values, List<Long> lengths){
        int tempIndex = 0;
        long tempVal = values.get(0);
        for(int i = 0; i < values.size(); i++){
            if(values.get(i) != tempVal){
                //sort between tempVal and i - 1
                valueSort(values, tempIndex, i - 1);
            }
            tempIndex = i;
            tempVal = values.get(i);   
        }
    }
    static void valueSort(List<Long> values, int low, int high){
        if(low < high){
            int p = valuePartition(values, low, high);
            valueSort(values, low, p - 1);
            valueSort(values, p + 1, high);
        }
    }
    static int valuePartition(List<Long> values, int low, int high){
        Long pivot = values.get(high);
        int i = (low - 1);
        for(int j = low; j <= high - 1; j++){
            if(values.get(j) < pivot){
                i++;
                valueSwap(values, i, j);
            }
        }
        valueSwap(values, i + 1, high);
        return i + 1;
    }
    static void valueSwap(List<Long> values, int i, int j){
        Long tempVal = values.get(i);
        values.set(i, values.get(j));
        values.set(j, tempVal);
    }
    static void swap(List<Long> keys, List<Long> values, int i, int j){
        Long tempVal = values.get(i);
        Long tempKey = keys.get(i);
        values.set(i, values.get(j));
        keys.set(i, keys.get(j));
        values.set(j, tempVal);
        keys.set(j, tempKey);
    }
    static int partition(List<Long> keys, List<Long> values, int low, int high){
        Long pivot = values.get(high);
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
    static void sort(List<Long> keys, List<Long> values, int low, int high){
        if(low < high){
            int p = partition(keys, values, low, high);
            sort(keys, values, low, p - 1);
            sort(keys, values, p + 1, high);
        }
    }
}
