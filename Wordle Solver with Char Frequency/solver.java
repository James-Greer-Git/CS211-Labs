import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class solver{
    private static List<Character> invalidLetters = new ArrayList<>();
    private static List<Character> yellowLetters = new ArrayList<>();
    private static char[] greenLetters = {'0','0','0','0','0'};
    private static List<Character> greenList = new ArrayList<>();
    private static int greens = 0;
    private static List<String> guesses = new ArrayList<>();
    private static int yellows = 0;

    public static void main(String args[]) throws FileNotFoundException{
        Scanner scanner = new Scanner(System.in);
        File file = new File("words.txt");
        List<String> words = readListFromFile(file);

        for(int i = 0; i < 6; i++){

            String guess = scanner.nextLine();
            while(guess.length() != 5){
                System.out.println("Please Enter a 5 Letter Guess");
                guess = scanner.nextLine();
            }
            guesses.add(guess);
            words.remove(guess);
            String result = scanner.nextLine();
            while(result.length() != 5){
                result = scanner.nextLine();
            }
            
            words = reduceList(words, guess, result);
            guessWord(words);
        }

        scanner.close();
    }

    public static void guessWord(List<String> words){
        //want to create map containing each letter as a key and a frequency list as the value
        //can score each word by summing the frequency of each character, then suggesting to guess the highest scoring word

        Map<Character, int[]> charFreq = new HashMap<>();
        //Loop to count frequency of each character
        for(int i = 0; i < words.size(); i++){
            for(int j = 0; j < 5; j++){
                if(charFreq.containsKey(words.get(i).charAt(j))){
                    int[] tempFreq = charFreq.get(words.get(i).charAt(j));
                    tempFreq[j]++;
                    charFreq.put(words.get(i).charAt(j), tempFreq);
                }
                else{
                    int[] freq = new int[5];
                    freq[j]++;
                    charFreq.put(words.get(i).charAt(j), freq);
                }
            }
        }
        //System.out.println("Calculated Frequency Map");

        int max_score = 0;
        String current_best = "";
        for(int i = 0; i < words.size(); i++){
            int score = 0;
            for(int j = 0; j < 5; j++){
                int [] tempFreqArray = charFreq.get(words.get(i).charAt(j));
                score += tempFreqArray[j];
            }
            if(score > max_score){
                max_score = score;
                current_best = words.get(i);
            }
        }
        System.out.println("Suggested Guess: " + current_best);
    }

    public static List<String> reduceList(List<String> words, String guess, String result){
        List<String> reducedInvalidList = new ArrayList<>();
        for(int i = 0; i < words.size(); i++){  //when I used reducedList = words removing an item from
            reducedInvalidList.add(words.get(i));      //reducedList also removed that element from words
        }
        char[] yellowIndex = {'0','0','0','0','0'};
        for(int i = 0; i < guess.length(); i++){
            switch(result.charAt(i)){
                case '0': 
                if(!invalidLetters.contains(guess.charAt(i))){
                    invalidLetters.add(guess.charAt(i));
                }
                break;
                case '1': 
                if(greenLetters[i] == '0'){
                    greenLetters[i] = guess.charAt(i);
                    greenList.add(guess.charAt(i));
                    greens++;
                }
                
                break;
                case '2': 
                if(!yellowLetters.contains(guess.charAt(i))){
                    yellowLetters.add(guess.charAt(i));
                    yellows++;
                    yellowIndex[i] = guess.charAt(i);
                }
                break;
            }
        }
        for(int i = 0; i < words.size(); i++){
            for(int j = 0; j < 5; j++){
                if(invalidLetters.contains(words.get(i).charAt(j)) && !yellowLetters.contains(words.get(i).charAt(j)) && !greenList.contains(words.get(i).charAt(j))){
                    String word = words.get(i);
                    reducedInvalidList.remove(word);
                }
            }
        }
        List<String> reducedYellowList = new ArrayList<>();
        for(int i = 0; i < reducedInvalidList.size(); i++){
            reducedYellowList.add(reducedInvalidList.get(i));
        }
        if(yellows > 0){
            for(int i = 0; i < reducedInvalidList.size(); i++){
                int count = 0;
                int yellowCount = 0;
                for(int j = 0; j < 5; j++){
                    if(reducedInvalidList.get(i).charAt(j) == yellowIndex[j]){
                        String word = reducedInvalidList.get(i);
                        reducedInvalidList.remove(word);
                        break;
                    }
                    if(!yellowLetters.contains(reducedInvalidList.get(i).charAt(j))){
                        count++;
                    }
                    else{
                        yellowCount++;
                    }
                }
                if(count == 5){
                    String word = reducedInvalidList.get(i);
                    reducedYellowList.remove(word);
                }
                if(yellowCount != yellows){
                    String word = reducedInvalidList.get(i);
                    reducedYellowList.remove(word);
                }
            }
        }

        List<String> reducedGreenList = new ArrayList<>();
        for(int i = 0; i < reducedYellowList.size(); i++){
            int greenCount = 0;
            for(int j = 0; j < 5; j++){
                if(greenLetters[j] == '0'){

                }
                else{
                    if(reducedYellowList.get(i).charAt(j) == greenLetters[j]){
                        greenCount++;
                    }
                }
            }
            if(greenCount == greens){
                String word = reducedYellowList.get(i);
                if(!reducedGreenList.contains(word)){
                    reducedGreenList.add(word);
                }
            }
        }
        for(int i = 0; i < reducedGreenList.size(); i++){
            System.out.println(reducedGreenList.get(i));
        }
        return reducedGreenList;
    }

    public static List<String> readListFromFile(File file) throws FileNotFoundException{
        List<String> words = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        
        while(scanner.hasNext()){
            String line = scanner.next();
            words.add(line);
        }
        scanner.close();
    
        return words;
    }
}
