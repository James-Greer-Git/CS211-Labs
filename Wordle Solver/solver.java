import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class solver{
    private static List<Character> invalidLetters = new ArrayList<>();
    private static List<Character> yellowLetters = new ArrayList<>();
    private static HashMap<Character, Integer> greenLetters = new HashMap<>();
    public static void main(String args[]) throws FileNotFoundException{
        Scanner scanner = new Scanner(System.in);
        File file = new File("words.txt");
        List<List<Character>> words = readListFromFile(file);

        //play first round
        for(int i = 0; i < 6; i++){
            String guess = scanner.nextLine();
            while(guess.length() != 5){
                System.out.println("Please enter a 5 letter guess.");
                guess = scanner.nextLine();
            }
            String result = scanner.nextLine(); //Enter 5 integers, 0 for black tile, 1 for green tile, 2 for yellow tile
            while(result.length() != 5){
                System.out.println("Please enter a 5 digit result.");
                result = scanner.nextLine();
            }
            words = reduceList(guess, result, words);
        }
        scanner.close();

    }
    public static List<List<Character>> reduceList(String guess, String result, List<List<Character>> words){
        // List<List<Character>> greenList = new ArrayList<>();
        // List<List<Character>> yellowList = new ArrayList<>();
        List<List<Character>> reducedList = new ArrayList<>();

        for(int i = 0; i < guess.length(); i++){
            switch(result.charAt(i)){
                case '0':   invalidLetters.add(guess.charAt(i));
                break;
                case '1':   greenLetters.put(guess.charAt(i), i);
                break;
                case '2':   yellowLetters.add(guess.charAt(i));
                break;
            }
        }
        //loop to remove words with invalid letters
        for(int i = 0; i < words.size(); i++){
            for(int j = 0; j < invalidLetters.size(); j++){
                if(words.get(i).contains(invalidLetters.get(j))){
                    words.remove(i);
                    i = 0;
                }
            }
        }
        //loop to remove words without yellow letters
        for(int i = 0; i < words.size(); i++){
            for(int j = 0; j < yellowLetters.size(); j++){
                if(!words.get(i).contains(yellowLetters.get(j))){
                    words.remove(i);
                    i = 0;
                }
            }
        }
        //loop to remove words without green letters
        for(int i = 0; i < words.size(); i++){
            for(int j = 0; j < greenLetters.size(); j++){
                //System.out.println(words.get(i).get(j));
                if(greenLetters.get(words.get(i).get(j)) != null){
                    //System.out.println(greenLetters.get(words.get(i).get(j)));
                    if(greenLetters.get(words.get(i).get(j)) != j){
                        words.remove(i);
                    }
                }
            }
        }


        System.out.println("Reduced list size: " + words.size());

        return words;
    }
    public static List<List<Character>> readListFromFile(File file) throws FileNotFoundException{
        List<List<Character>> words = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        
        while(scanner.hasNext()){
            String line = scanner.next();
            List<Character> word = new ArrayList<>();
            for(int i = 0; i < 5; i++){
                word.add(line.charAt(i));
            }
            words.add(word);
        }
        scanner.close();
    
        return words;
    }
}
