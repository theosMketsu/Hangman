import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    
    static boolean gameOver;
    static int guesses;

    static boolean guessNumber(int num, int val) {

        if (val == num) return true;

        //val is your guess
        guesses--;
        if (val > num) {
            // Guess is bigger
            if ((3*num) < val) {
                System.out.println("Guess too Large");
            } else {
                System.out.println("Try a smaller guess");
            }
        } else if (val < num) {
            // Guess is smaller
            if ((3*val) < num) {
                System.out.println("Guess too Small");
            } else {
                System.out.println("Try a bigger number");
            }
        }

        return false;
    }

    static void guessWord(char ch, String word, boolean[] wordGuess) {

        int j;
        int wordLen;

        j = word.indexOf(ch); //Get index of character
        if (j == -1) {
            // if guess is incorrect
            guesses--;
        } else {
            wordLen = word.length();
            // Check if the guessed character in other indexes
            for (int i = j; i < wordLen; i++) {
                if (ch == word.charAt(i)) {
                    wordGuess[i] = true;
                }
            }
        }
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
        boolean[] wordGuess = new boolean[32];
        char ch;
        int gui; //gui default is true
        int num = 0;
        int val;
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
            game = sc.next().toUpperCase();
            if (game.equals("W")) {
                System.out.println("Guess the Word");
                // guess word selection
                //word = getWord(word, rand).toLowerCase();
                word = "Hello".toLowerCase();
                wordLen = word.length();
                wordGuess = new boolean[wordLen];
                printWord(word,wordGuess);
            } else if (game.equals("N")) {
                System.out.println("Guess the Number");
                num = rand.nextInt(100);
            } else {
                System.err.println("Invalid option, Try again");
                game = "";
            }

        }
        
        guesses = 6;
        //Play Game
        while (!gameOver) {
            System.out.printf("Guesses: %d\n", guesses);
            System.out.println("Enter a guess");
            printWord(word,wordGuess);
            guess = sc.next().toLowerCase();
            System.out.println();

            // guess Number game
            if (game.equals("N")) {
                try {
                    val = Integer.parseInt(guess);
                    gameOver = guessNumber(num, val);
                } catch (Exception e) {
                    System.err.println("Enter a number");
                    continue;
                }
                
            } else {
                // ---word guess---
                ch = guess.charAt(0); //only read the first character
                guessWord(ch, word, wordGuess);
                for (boolean x: wordGuess) {
                    gameOver = x;
                    if (!gameOver) {
                        break;
                    }
                }
            }

            // Game over
            if (guesses == 0) {
                // Restart Game or End Game
                System.out.println("YOU LOSE");
                if (game.equals("W")) {
                    System.out.printf("Word is %s\n\n", word);
                } else {
                    System.out.printf("Number is %d\n\n", num);
                }
                guesses = -1;
                gameOver = true;

            } else if (gameOver) {
                System.out.println("You WIN");
                if (game.equals("W")) {
                    System.out.printf("Word is %s\n\n", word);
                } else {
                    System.out.printf("Number is %d\n\n", num);
                }
                guesses = -1;
            }

            if (guesses == -1) {
                System.out.print("Play again?\n yes(y)\tno(n)\n");
                ch = sc.next().toLowerCase().charAt(0);
                if (ch == 'y') {
                    guesses = 6; // Reset number of guesses
                    gameOver = false; // reset gameover
                    System.out.println("Select different Game: yes(y)\tno(n)");
                    ch = sc.next().toLowerCase().charAt(0);
                    if (ch == 'y') {
                        if (game.equals("W")) {
                            System.out.println("Guess the Number");
                            game = "N";
                        } else if (ch == 'y' && game.equals("N")) {
                            System.out.println("Guess the Word");
                            game = "W";
                        }
                    }

                    System.out.println(game);
                    if (game.equals("W")) {
                        // Restart the Game
                        word = getWord(word, rand); // New Word
                        wordLen = word.length();
                        wordGuess = new boolean[wordLen];
                        printWord(word,wordGuess);
                    } else {
                        num = rand.nextInt(100);
                    }
                }
            }

        }

        if (guesses > 0) {

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
