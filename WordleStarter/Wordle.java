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
        for (String word : WordleDictionary.FIVE_LETTER_WORDS)
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
        else
        {
            gw.showMessage("Nice guess");
            for(int i = 0; i < 5; i++)
            {
                boolean letterInList = false;
                for(String letta : letters)
                {
                    if(letta.equalsIgnoreCase(s.substring(i,i+1)))
                    {
                        letterInList = true;
                    }
                }
                if(!letterInList)
                {
                    letters[i] = s.substring(i,i+1);
                }
            }
            System.out.println(letters[0] + letters[1] + letters[2] +letters[3] +letters[4]);
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
