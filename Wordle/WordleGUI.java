import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.Color;

public class WordleGUI implements ActionListener{
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel gameLabel;
    private static JTextField userText;
    private static JLabel textEntryLabel;
    private static JButton submitGuessButton;
    private static JLabel information;
    private static JLabel success;
    static List<String> words;
    private static String word; 
    private boolean gameOver = false;
    private int rounds = 0;
    private static int pixels = 50;
    static File wordFile = new File("");
    public static void main(String args[]) throws FileNotFoundException{
        words = wordList(wordFile);
        word = words.get((int)(Math.random()*words.size())).toUpperCase();

        panel = new JPanel();
        frame = new JFrame();

        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        gameLabel = new JLabel("Wordle");
        gameLabel.setBounds(170, 25, 80, 10);
        panel.add(gameLabel);

        textEntryLabel = new JLabel("Enter your guess");
        textEntryLabel.setBounds(50, 270, 150 , 25);
        panel.add(textEntryLabel);

        userText = new JTextField();
        userText.setBounds(200, 270, 165, 25);
        panel.add(userText);

        submitGuessButton = new JButton("Submit!");
        submitGuessButton.setBounds(264, 300, 100, 25);
        submitGuessButton.addActionListener(new WordleGUI());
        panel.add(submitGuessButton);

        information = new JLabel("");
        information.setBounds(50, 230, 300, 25);
        panel.add(information);

        success = new JLabel("");
        success.setBounds(50, 240, 350, 25);
        panel.add(success);

        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameOver){
            return;
        }
        String guess = userText.getText().toUpperCase();
        if(guess.length() != 5){
            information.setText("Guess must consist of 5 letters");
            return;
        }
        if(!words.contains(guess)){
            information.setText("This is not acceptable answer, enter another guess");
            return;
        }
        else{
            if(guess.equals(word.toUpperCase())){
                
                success.setText("Congratulations, you guessed the word!");
                gameOver = true;
            }
            rounds++;
            information.setText("Tries left: " + (6 - rounds));
            userText.setText("");
            if(rounds > 5 && !gameOver){
                success.setText("Sorry, you've run out of tries.");
                gameOver = true;
            }
        }
        compareGuess(guess);
    }
    public void compareGuess(String guess){
        JTextPane colouredGuess = new JTextPane();
        boolean[] greenFix = new boolean[5];    //boolean array to keep track of what positions are solved
        colouredGuess.setText(guess);
        colouredGuess.setBounds(150, pixels, 100, 25);
        colouredGuess.setFont(colouredGuess.getFont().deriveFont(20.0f));
        panel.add(colouredGuess);

        SimpleAttributeSet green = new SimpleAttributeSet();
        StyleConstants.setForeground(green, Color.GREEN);
        SimpleAttributeSet yellow = new SimpleAttributeSet();
        StyleConstants.setForeground(yellow, Color.YELLOW);
        StyledDocument doc = colouredGuess.getStyledDocument();

        for(int i = 0; i < guess.length(); i++){
            if(word.charAt(i) == guess.charAt(i)){
                doc.setCharacterAttributes(i, 1, green, false);
                greenFix[i] = true;
                System.out.println("Green: " + guess.charAt(i));
            }
        }
        for(int i = 0; i < guess.length(); i++){
            for(int j = 0; j < guess.length(); j++){  
                if(word.charAt(i) == guess.charAt(j) && i != j && !greenFix[i] && !greenFix[j]){
                    doc.setCharacterAttributes(j, 1, yellow, false);
                    System.out.println("Yellow: " + guess.charAt(j));
                }
            }
        }
        pixels += 25;
    }
    static List<String> wordList(File file) throws FileNotFoundException{
        Scanner scanner = new Scanner(file);
        List<String> wordListFromFile = new ArrayList<>();

        while(scanner.hasNextLine()){
            wordListFromFile.add(scanner.nextLine());
        }

        scanner.close();
        return wordListFromFile;
    }
}
