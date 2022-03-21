import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Solution{

    static int arraySize;
    static int collisions = 0;

    // public int find(int size, HashTable myTable, String word){

    // }
    // public String[] fill(int size, String[] array){

    // }
    public static int stringHashFunction(int size, String word){

        int hashValue = 0;
        
        for(int i = 0; i < word.length(); i++){

            int charCode = word.charAt(i);

            int tempValue = hashValue;

            hashValue = ((tempValue) * 29 + charCode) % size;
        }

        return hashValue;
    }

    public static List<String> readWordsFromDictionary(File file) throws Exception{
        List<String> wordList = new ArrayList<>();

        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            wordList.add(scanner.nextLine());
        }
        scanner.close();

        return wordList;
    }

    public static String[] fill(int size, String[] wordsList){

        String[] hashtable = new String[size];

        for(int i = 0; i < wordsList.length; i++){
            int hashedIndex = stringHashFunction(size, wordsList[i]);
            if(hashtable[hashedIndex] != null){
                collisions++;
            }
            hashtable[hashedIndex] = wordsList[i];
        }

        return hashtable;
    }

    public static void main(String args[]) throws Exception{

        File file = new File("words.txt");

        List<String> wordList = readWordsFromDictionary(file);

        String[] wordsToAdd = new String[wordList.size()];
        wordsToAdd = wordList.toArray(wordsToAdd);

        HashTable myTable = new HashTable(fill((int) (wordsToAdd.length/0.9), wordsToAdd)); //HashTable will be at 90% load

        for(int i = 0; i < wordsToAdd.length; i++){
            myTable.check(stringHashFunction((int) (wordsToAdd.length/0.9), wordsToAdd[i]), wordsToAdd[i]);
        }
        System.out.println(myTable.getTotal());
    }
}

class HashTable{

    private String[] hashTable;
    private int total = 0;

    public HashTable(String[] input){
        hashTable = input;
    }

    public boolean check(int slot, String check){
        if(hashTable[slot].equals(check)){
            return true;
        }
        else{
            total++;
            return false;
        }
    }

    public int getTotal(){
        return total;
    }
}
