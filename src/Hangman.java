import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    
    static boolean gameOver;
    static int guesses = 6;

    static boolean guessNumber(int num, int val) {

        if (val == num) return true;

        if (val > num) {
            guesses--;
            if ((val/num) > 3) {
                System.out.println("Guess too Large");
            } else {
                System.out.println("Try a smaller guess");
            }
        } else if (num != 0 && num < val) {
            guesses--;
            if ((num/val) > 3) {
                System.out.println("Guess too Small");
            } else {
                System.out.println("Try a bigger number");
            }
        }

        return false;
    }

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
        char ch;
        int gui = 1; //gui default is true
        int num = 0;
        int wordLen;
        Random rand = new Random();
        String word = "";
        String game = "";
        String guess;

        if (args.length > 0) {
            //For now the gui isn't 
            if (args[0] != "1") {
                gui = 0;
            }
        }

        // Selecting Game option
        System.out.println("Select Game:\t\t\'Enter W or N to select game option\'");
        System.out.println("W - Word Guess \nN - Number Guess");
        Scanner sc = new Scanner(System.in);
        while (game.equals("")) {
            // Ensure that the correct option is selected
            game = sc.next();
            if (game.equals("W")) {
                System.out.println("Guess the Word");
            } else if (game.equals("N")) {
                System.out.println("Guess the Number");
            } else {
                System.err.println("Invalid option, Try again");
                game = "";
            }
        }


        // guess word selection
        word = getWord(word, rand).toLowerCase();
        wordLen = word.length();
        boolean[] wordGuess = new boolean[wordLen];
        
        int j; // index of character player guessed
        int guesses = 6; // Num of incorrect guesses
        int val;
        //Play Game
        while (!gameOver) {

            if (game.equals("W")) {
                printWord(word,wordGuess);
            } else {
                num = rand.nextInt();
            }

            System.out.println("Enter a guess");
            guess = sc.next().toLowerCase();

            // guess Number game
            if (game.equals("N")) {
                try {
                    val = Integer.parseInt(guess);
                } catch (Exception e) {
                    System.err.println("Enter a number");
                    continue;
                }
                guessNumber(num, val);
            }

            // ---word guess---
            ch = guess.charAt(0); //only read the first character
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
                ch = sc.next().toLowerCase().charAt(0);

                gameOver = true;
                if (ch == 'y') {
                    guesses = 6; // Reset number of guesses
                    gameOver = false; // reset gameover
                    System.out.println("Select different Game: yes(y)\tno(n)");
                    ch = sc.next().toLowerCase().charAt(0);
                    if (ch == 'y' && game.equals("W")) {
                        game = "N";
                    } else if (ch == 'y' && game.equals("N")) {
                        game = "W";
                    }

                    if (game.equals("W")) {
                        // Restart the Game
                        word = getWord(word, rand); // New Word
                        wordLen = word.length();
                        wordGuess = new boolean[wordLen];
                    } else {
                        num = rand.nextInt();
                    }
                }
            }

        }
        sc.close();
    }

    static String getWord(String word, Random rand) throws IOException {
        Path filePath = Path.of("../Wordlists/english.txt");

        // Get all lines as a List of Strings
        var lines = Files.readAllLines(filePath);

        // Pick a random word from the list
        int guess = rand.nextInt(lines.size());
        word = lines.get(guess);

        return word;
    }
}
