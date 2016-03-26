package com.cooperlomaz.util;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class WordReader {
    public static String[] getAllWords(String fileName) throws IOException {     
        String line = null;
        List<String> wordList = new ArrayList<>();
        //File file = new File("resources//TestFile.txt");
        File file = new File(fileName);
    	File anagramFile = new File(fileName);
    	if (!anagramFile.isFile()) {
    		System.err.println("Usage : java AnagramFinder <A Valid AnagramFileName>");
    		throw new IOException();
    	}
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
               wordList.add(line);
            }
        }
        String[] wordListStrings = new String[wordList.size()];
        Iterator<String> wordListIterator = wordList.iterator();
        int i=0;
        while(wordListIterator.hasNext()) {
        	wordListStrings[i] = (String) wordListIterator.next();
        	i++;
        }
        return wordListStrings;        
    }    
}
