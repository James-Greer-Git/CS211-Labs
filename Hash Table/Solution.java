public class Solution {
    public int find(int size, HashTable myTable, String word){




        return 0;
    }
    public String[] fill(int size, String[] array){

        String[] hashTable = new String[size];
        for(int i = 0; i < size; i++){
            hashTable[i] = "";
        }
        return hashTable;
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
