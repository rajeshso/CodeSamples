package com.cooperlomaz.anagram;
import java.util.ArrayList;
import java.util.List;
/**
 * The Anagram for a particular length are abstracted in this class
 * @author Rajesh
 *
 */
public class Anagram {
	//List of words which are all anagrams of each other
    private final List<String> anagramList;

    public Anagram(String word)
    {
        anagramList = new ArrayList<>();
        anagramList.add(word);
    }

    public void addAnagram(String word)
    {
        anagramList.add(word);
    }

    @Override
	public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (String anagram : anagramList)
        {
            sb.append(anagram);
            sb.append(' ');
        }

        return sb.toString();
    }
}
