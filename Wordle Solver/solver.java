import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class solver{
    private static List<Character> invalidLetters = new ArrayList<>();
    private static List<Character> yellowLetters = new ArrayList<>();
    private static char[] greenLetters = {'0','0','0','0','0'};
    private static List<Character> greenList = new ArrayList<>();
    private static int greens = 0;
    private static boolean gameOver = false;
    public static void main(String args[]) throws FileNotFoundException{
        Scanner scanner = new Scanner(System.in);
        File file = new File("words.txt");
        int wins = 0;

        for(int rounds = 1; rounds <= 10; rounds++){
            System.out.println("Game " + rounds);
            gameOver = false;
            List<String> tempWords = readListFromFile(file);
            List<String> words = tempWords;
            for(int i = 0; i < 6; i++){
                if(gameOver){
                    break;
                }
                String guess = scanner.nextLine();
                while(guess.length() != 5){
                    System.out.println("Please enter a 5 letter guess.");
                    guess = scanner.nextLine();
                    while(!words.contains(guess)){
                        System.out.println("Not an allowable answer");
                        guess = scanner.nextLine();
                    }
                }
                String result = scanner.nextLine(); //Enter 5 integers, 0 for black tile, 1 for green tile, 2 for yellow tile
                while(result.length() != 5){
                    System.out.println("Please enter a 5 digit result.");
                    result = scanner.nextLine();
                }
                //System.out.println("Made it here");
                words = reduceList(words, guess, result);
                System.out.println(words.size());
                if(words.size() == 1){
                    System.out.println("Answer is: " + words.get(0));
                    wins++;
                    invalidLetters = new ArrayList<>();
                    yellowLetters = new ArrayList<>();
                    greenList = new ArrayList<>();
                    char[] temp = {'0','0','0','0','0'};
                    greenLetters = temp;
                    greens = 0;
                    break;
                }
                else{
                    System.out.println("Suggested Guess: " + words.get((int) (Math.random()*words.size())));        
                }
            }
            gameOver = true;
        }
        System.out.println("Winrate: " + (double)(wins/10));
        scanner.close();
    }

    public static List<String> reduceList(List<String> words, String guess, String result){
        List<String> reducedInvalidList = new ArrayList<>();
        for(int i = 0; i < words.size(); i++){  //when I used reducedList = words removing an item from
            reducedInvalidList.add(words.get(i));      //reducedList also removed that element from words
        }
        
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
                }
                break;
            }
        }

        // if(greens == 5){
        //     gameOver = true;
        //     return words;
        // }        


        //reduce word list using black tiles
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
        //reduce word list using yellow tiles
        if(yellowLetters.size() > 0){
            for(int i = 0; i < reducedInvalidList.size(); i++){
                int count = 0;
                for(int j = 0; j < 5; j++){
                    if(!yellowLetters.contains(reducedInvalidList.get(i).charAt(j))){
                        count++;
                    }
                }
                if(count == 5){
                    //System.out.println(reducedInvalidList.get(i) + " does not contain any yellow letters");
                    String word = reducedInvalidList.get(i);
                    reducedYellowList.remove(word);
                }
            }
        }

        List<String> reducedGreenList = new ArrayList<>();
        // for(int i = 0; i < reducedYellowList.size(); i++){
        //     reducedGreenList.add(reducedYellowList.get(i));
        // }
        //reduce word list using green tiles
        for(int i = 0; i < reducedYellowList.size(); i++){
            int greenCount = 0;
            for(int j = 0; j < 5; j++){
                if(greenLetters[j] == '0'){

                }
                else{
                    if(reducedYellowList.get(i).charAt(j) == greenLetters[j]){
                        greenCount++;
                        //System.out.println(reducedYellowList.get(i) + " contains some green letters.");
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
