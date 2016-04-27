package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    //variable's
    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private int wordLength = DEFAULT_WORD_LENGTH;


    //initialize random class
    private Random random = new Random();


    //initializing Collections
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String , ArrayList<String>> lettersToWord = new HashMap<>();
    private ArrayList<String> wordList = new ArrayList<>();
    private HashMap<Integer , ArrayList<String>> sizeToWords = new HashMap<>();


    public AnagramDictionary(InputStream wordListStream) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        while((line = in.readLine()) != null) {

            String word = line.trim();


            //adding words to wordSet and wordList
            wordSet.add(word);
            wordList.add(word);


            //adding the word according to the length as key
            if(sizeToWords.get(word.length()) == null){

                sizeToWords.put(word.length(), new ArrayList<String>());
                sizeToWords.get(word.length()).add(word);
            }

            else{

                sizeToWords.get(word.length()).add(word);
            }



            //getting the word sorted to make it as a key and adding it to the lettersToWord hashMap
            String sortedWord = Sort(word);

            if(lettersToWord.get(sortedWord) != null){

                lettersToWord.get(sortedWord).add(word);
            }

            else {

                ArrayList<String> values = new ArrayList<>();
                values.add(word);
                lettersToWord.put(sortedWord , values);

            }
        }
    }


    public boolean isGoodWord(String word, String base) {

        if(wordSet.contains(word) && !(word.contains(base)))
            return true;

        else
            return false;

    }


    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {

        ArrayList<String> result = new ArrayList<>();

        for(char i = 'a' ; i <= 'z' ; i++){

            String temp = word + i;
            String newStr = Sort(temp);


            if(lettersToWord.get(newStr) != null){

                result.addAll(lettersToWord.get(newStr));
            }

        }

        return result;
    }


    public String pickGoodStarterWord() {

        ArrayList<String> start = new ArrayList<>();
        start = sizeToWords.get(wordLength);

        int i = random.nextInt((start.size()));

        while(getAnagramsWithOneMoreLetter(start.get(i)).size() <= MIN_NUM_ANAGRAMS){

            i = random.nextInt((start.size()));

        }

        if(wordLength == MAX_WORD_LENGTH)
            wordLength = DEFAULT_WORD_LENGTH;

        else
            wordLength++;

        return start.get(i);
    }


    public String Sort(String sort){

        char[] arr = sort.toCharArray();
        Arrays.sort(arr);
        String str = new String(arr);

        return str;

    }

}



