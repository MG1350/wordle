/*
 * File: Wordle.java
 * -----------------
 * This module is the starter file for the Wordle assignment.
 * BE SURE TO UPDATE THIS COMMENT WHEN YOU COMPLETE THE CODE.
 */

import edu.willamette.cs1.wordle.WordleDictionary;
import edu.willamette.cs1.wordle.WordleGWindow;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Wordle {

    public void run() {
        gw = new WordleGWindow();
        gw.addEnterListener((s) -> enterAction(s));
        randomWord = WordleDictionary.FIVE_LETTER_WORDS[(int) (Math.random()*WordleDictionary.FIVE_LETTER_WORDS.length)];
        File score = new File("score.txt");
        scoreboard = new int[6];
        try {
            if (score.createNewFile()) {
                System.out.println("Score created successfully.");
                FileWriter fw = new FileWriter(score);
                fw.write("0\n");
                fw.write("0\n");
                fw.write("0\n");
                fw.write("0\n");
                fw.write("0\n");
                fw.write("0\n");
                fw.close();
            } else {
                System.out.println("Score already exists.");
                FileInputStream file = new FileInputStream("score.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(file));
                for(int i = 0; i < 6; i++)
                {
                    int number = Integer.valueOf(reader.readLine());
                    gw.setSquareColor(i, 0, gw.CORRECT_COLOR);
                    gw.setSquareLetter(i, 0, i+1+"");
                    gw.setSquareLetter(i, 4, number+"");
                    scoreboard[i] = number;
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error creating/writing to file: " + e.getMessage());
        }
        // Debug show word in first row
        // for(int i = 0; i <= WordleGWindow.N_COLS-1; i++)
        // {
        //     gw.setSquareLetter(0, i, randomWord.substring(i, i+1));
        // }
        
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
        if(!(s.length() == 5))
        {
            // Printing every possible word
            if(s.length() == 0)
            {
                gw.showMessage("Printing possible words");
                for(String word : WordleDictionary.FIVE_LETTER_WORDS)
                {
                    boolean possible = true;
                    String[] Yletters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
                    // Checking if each letter for missing color or guess letter in place of a green letter does not match
                    for(int i = 0; i < 5; i++)
                    {
                        if(gw.getKeyColor(word.substring(i,i+1).toUpperCase()) == gw.MISSING_COLOR || (gw.getKeyColor(randomWord.substring(i,i+1).toUpperCase())) == gw.CORRECT_COLOR && !(randomWord.substring(i,i+1).equalsIgnoreCase(word.substring(i,i+1))))
                        {
                            possible = false;
                        }
                    }
                    // Checking if the word has a yellow character not in the word
                    for(String letter : Yletters)
                    {
                        if(gw.getKeyColor(letter) == gw.PRESENT_COLOR && word.indexOf(letter.toLowerCase()) == -1)
                        {
                            possible = false;
                        }
                    }
                    if(possible)
                    {
                        System.out.println(word);
                    }
                }
                System.out.println("========");
            }
            else
            {
                gw.showMessage("Word not long enough");
            }
        }
        else
        {
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
                gw.setKeyColor(s.substring(i, i + 1), gw.CORRECT_COLOR);
            }
            try (FileWriter fw = new FileWriter("score.txt", false)) {
                for(int i = 0; i < 6; i++)
                {
                    if(i == gw.getCurrentRow())
                    {
                        scoreboard[i] += 1;
                    }
                    fw.write(scoreboard[i]+"\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            gw.showMessage("Nice guess");
            // Adding the green letters and removing from input word count array
            for(int i = 0; i < 5; i++)
            {
                if(s.substring(i, i + 1).equalsIgnoreCase(randomWord.substring(i,i+1)))
                {
                    gw.setSquareColor(gw.getCurrentRow(), i, gw.CORRECT_COLOR);
                    gw.setKeyColor(s.substring(i, i + 1), gw.CORRECT_COLOR);
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
                else
                {
                    gw.setSquareColor(gw.getCurrentRow(), i, gw.MISSING_COLOR);
                    gw.setKeyColor(s.substring(i, i + 1), gw.MISSING_COLOR);
                }
            }
            // Adding the yellow letters
            for(int i = 0; i < 5; i++)
            {
                if(!s.substring(i, i + 1).equalsIgnoreCase(randomWord.substring(i, i + 1)))
                {
                    for(int j = 0; j < 5; j++)
                    {
                        if (letters[j].equalsIgnoreCase(s.substring(i, i + 1)) && count[j] > 0)
                        {
                            for(int k = 0; k < 5; k++)
                            {
                                if(Rletters[k].equalsIgnoreCase(s.substring(i, i + 1)) && Rcount[k] > 0 )
                                {
                                    gw.setSquareColor(gw.getCurrentRow(), i, gw.PRESENT_COLOR);
                                    if(!gw.getKeyColor(s.substring(i, i + 1)).equals(gw.CORRECT_COLOR))
                                    {
                                        gw.setKeyColor(s.substring(i, i + 1), gw.PRESENT_COLOR);
                                    }
                                    count[j]--;
                                    Rcount[k]--;
                                }
                            }
                        }
                    }
                }
            }
            if(gw.getCurrentRow() < 5)
                gw.setCurrentRow(gw.getCurrentRow()+1);
        }
        }
    }

/* Startup code */

    public static void main(String[] args) {
        new Wordle().run();
    }

/* Private instance variables */

    private WordleGWindow gw;
    private String randomWord;
    private int[] scoreboard;
}
