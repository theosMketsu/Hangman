import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;

public class Hangman {

    static void printWord(String word, boolean[] wordGuess) {
        int n = word.length();
        int c;
        for (int i = 0; i < n; i++) {
            c = word.charAt(i);
            if ((c > 64 && c <= 90) || (c > 96 && c <= 122)) {
                // For all alphabetic characters
                if (wordGuess[i]) {
                    // Character has been guessed
                    System.out.printf("%c ", c);
                } else {
                    // Character not guessed
                    System.out.printf("_ "); 
                }
            } else {
                // Non alphabetic character will be displayed
                System.out.printf("%c ", c);
            }
        }
        System.out.println();
    }
    public static void main(String[] args) throws IOException {
    
        // 0. Set Gui to true(1) or false(0) in terminal
        boolean gameOver = false;
        char ch;
        int gui = 1; //gui default is true
        int wordLen;
        String word = "";

        if (args.length > 0) {
            if (args[0] != "1") {
                gui = 0;
            }
        }

        // guess word selection
        word = getWord(word);
        word = word.toLowerCase();
        wordLen = word.length();

        boolean[] wordGuess = new boolean[wordLen];
        Scanner sc = new Scanner(System.in);
        int j; // index of character player guessed
        int guesses = 6; // Num of incorrect guesses

        //Play Game
        while (!gameOver) {
            printWord(word,wordGuess);
            //_ _ _ _ _

            System.out.println("Enter a guess");
            ch = sc.nextLine().charAt(0); //only read the first character
            j = word.indexOf(ch); //Get index of character

            if (j == -1) {
                // if guess is incorrect
                guesses--;
            } else {

                // Check if the guessed character in other indexes
                for (int i = j; i < wordLen; i++) {
                    if (ch == word.charAt(i)) {
                        wordGuess[i] = true;
                    }
                }
            }

            // Game over
            if (guesses == 0) {
                // Restart Game or End Game
                System.out.println("YOU LOSE");
                System.out.print("Play again?\n yes(y)\tno(n)\n");
                ch = sc.nextLine().toLowerCase().charAt(0);

                gameOver = true;
                if (ch == 'y') {
                    // Restart the Game
                    gameOver = false; // reset gameover
                    word = getWord(word); // New Word
                    wordLen = word.length();
                    guesses = 6; // Reset number of guesses
                    wordGuess = new boolean[wordLen];
                }
            }

        }
        sc.close();
    }

    static String getWord(String word) throws IOException {
        Random rand = new Random();
        Path filePath = Path.of("../Wordlists/english.txt");

        // Get all lines as a List of Strings
        var lines = Files.readAllLines(filePath);

        // Pick a random word from the list
        int guess = rand.nextInt(lines.size());
        word = lines.get(guess);

        return word;
    }
}