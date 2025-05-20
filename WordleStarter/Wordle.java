/*
 * File: Wordle.java
 * -----------------
 * This module is the starter file for the Wordle assignment.
 * BE SURE TO UPDATE THIS COMMENT WHEN YOU COMPLETE THE CODE.
 */

import edu.willamette.cs1.wordle.WordleDictionary;
import edu.willamette.cs1.wordle.WordleGWindow;

public class Wordle {

    public void run() {
        gw = new WordleGWindow();
        gw.addEnterListener((s) -> enterAction(s));
        randomWord = WordleDictionary.FIVE_LETTER_WORDS[(int) (Math.random()*WordleDictionary.FIVE_LETTER_WORDS.length)];
        for(int i = 0; i <= WordleGWindow.N_COLS-1; i++)
        {
            gw.setSquareLetter(0, i, randomWord.substring(i, i+1));
        }
    }

/*
 * Called when the user hits the RETURN key or clicks the ENTER button,
 * passing in the string of characters on the current row.
 */

    public void enterAction(String s) {
        boolean real = false;
        String[] letters = {"#", "#", "#", "#", "#"};
        String[] Rletters = {"#", "#", "#", "#", "#"};
        int[] count = {0,0,0,0,0};
        int[] Rcount = {0,0,0,0,0};
        // Setting letters and count for user word
        for(int i = 0; i < 5; i++)
        {
            boolean letterInList = false;
            for(int j = 0; j < 5; j++)
            {
                if(letters[j].equalsIgnoreCase(s.substring(i,i+1)))
                {
                    letterInList = true;
                    count[j] += 1;
                }
            }
            if(!letterInList)
            {
                letters[i] = s.substring(i,i+1);
                count[i] += 1;
            }
        }
        // Setting letters and count for random word
        for(int i = 0; i < 5; i++)
        {
            boolean letterInList = false;
            for(int j = 0; j < 5; j++)
            {
                if(Rletters[j].equalsIgnoreCase(randomWord.substring(i,i+1)))
                {
                    letterInList = true;
                    Rcount[j] += 1;
                }
            }
            if(!letterInList)
            {
                Rletters[i] = randomWord.substring(i,i+1);
                Rcount[i] += 1;
            }
        }
        for(String word : WordleDictionary.FIVE_LETTER_WORDS)
        {
            if(s.equalsIgnoreCase(word))
            {
                real = true;
            }
        }
        if(!real)
        {
            gw.showMessage("Not in word list");
        }
        else if(s.equalsIgnoreCase(randomWord))
        {
            gw.showMessage("Word Found!");
            for(int i = 0; i < 5; i++)
            {
                gw.setSquareColor(gw.getCurrentRow(), i, gw.CORRECT_COLOR);
            }
            System.out.println(letters[0]+count[0] + letters[1]+count[1] + letters[2]+count[2] + letters[3]+count[3] + letters[4]+count[4]);
            System.out.println(Rletters[0]+Rcount[0] + Rletters[1]+Rcount[1] + Rletters[2]+Rcount[2] + Rletters[3]+Rcount[3] + Rletters[4]+Rcount[4]);
        }
        else
        {
            gw.showMessage("Nice guess");
            System.out.println(letters[0]+count[0] + letters[1]+count[1] + letters[2]+count[2] + letters[3]+count[3] + letters[4]+count[4]);
            System.out.println(Rletters[0]+Rcount[0] + Rletters[1]+Rcount[1] + Rletters[2]+Rcount[2] + Rletters[3]+Rcount[3] + Rletters[4]+Rcount[4]);
            System.out.println(randomWord);
            // Adding the green letters and removing from input word count array
            for(int i = 0; i < 5; i++)
            {
                if(s.substring(i,i+1).equalsIgnoreCase(randomWord.substring(i,i+1)))
                {
                    gw.setSquareColor(gw.getCurrentRow(), i, gw.CORRECT_COLOR);
                    // Decrement count in guess word
                    for(int j = 0; j < 5; j++) {
                        if(letters[j].equalsIgnoreCase(s.substring(i, i + 1)) && count[j] > 0) {
                            count[j]--;
                            break;
                        }
                    }
                    // Decrement count in randomWord
                    for(int k = 0; k < 5; k++) {
                        if(Rletters[k].equalsIgnoreCase(s.substring(i, i + 1)) && Rcount[k] > 0) {
                            Rcount[k]--;
                            break;
                        }
                    }
                }
            }
            // Adding the yellow letters
            for(int i = 0; i < 5; i++)
            {
                if(!s.substring(i, i + 1).equalsIgnoreCase(randomWord.substring(i, i + 1))) 
                {
                    for(int j = 0; j < 5; j++)
                    {
                    }
                }
            }
            System.out.println(letters[0]+count[0] + letters[1]+count[1] + letters[2]+count[2] + letters[3]+count[3] + letters[4]+count[4]);
            System.out.println(Rletters[0]+Rcount[0] + Rletters[1]+Rcount[1] + Rletters[2]+Rcount[2] + Rletters[3]+Rcount[3] + Rletters[4]+Rcount[4]);
            gw.setCurrentRow(gw.getCurrentRow()+1);
        }
    }

/* Startup code */

    public static void main(String[] args) {
        new Wordle().run();
    }

/* Private instance variables */

    private WordleGWindow gw;
    private String randomWord;
}
