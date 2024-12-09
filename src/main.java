import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class main {

    static void printWord(String word, boolean[] wordGuess) {
        int n = word.length();
        int c;
        for (int i = 0; i < n; i++) {
            c = word.charAt(i);
            if ((c > 64 && c <= 90) || (c > 96 && c <= 122)) {
                if (wordGuess[i]) {
                    System.out.printf("%c ",wordGuess[i]);
                } else {
                    System.out.printf("_ "); 
                }
            } else {
                System.out.printf("%c ",wordGuess[i]);
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
        

        if (args.length < 2) {
            if (args[0] != "1") {
                gui = 0;
            }
        }
        
        
        
        // guess word selection
        word = getWord(word);
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
                wordGuess[j] = true;
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
    
    }

    static String getWord(String word) throws IOException {
        Random rand = new Random();
        int guess;
        // 1. Read file from wordlists(At a later stage we will choose which wordlist based on langauges)
        File file = new File("../src/english.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        
        // 2. Set guess word
        int n = (int) file.length();
        // guess -> position of the word in the wordlist
        guess = rand.nextInt(n);
        while (guess >= 0) {
            if  (guess == 0) {
                // when position of the word is reached
                word = br.readLine();
                break;
            }
            // decrement until the word
            guess--;
            br.readLine();
        }

        br.close();
        return word;
    }
}