package com.cooperlomaz.anagram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cooperlomaz.util.WordReader;

public class AnagramFinder {


	public AnagramFinder() {
	}
	
	/**
	 * This is the anagram matcher
	 * @param word1 String
	 * @param word2 String
	 * @return true if they are anagrams
	 */
	public static boolean isAnagram(String word1, String word2) {
		char[] word1Chars = word1.toLowerCase().toCharArray();
		char[] word2Chars = word2.toLowerCase().toCharArray();

		Arrays.sort(word1Chars);
		Arrays.sort(word2Chars);

		return Arrays.equals(word1Chars, word2Chars);
	}

	
	/**
	 * Finds the anagrams and populates in a List. 
	 * @param words the input array of the words. Note that the given input will be used for calculations and will be distorted.
	 * The user is advised to present a cloned copy.
	 * @return The list of Anagrams
	 */
	public List<Anagram> findAnagrams(String[] words) {
		List<Anagram> anagramSets = new ArrayList<>();

		for (int i = 0; i < words.length; i++) {
			String currentWord = words[i];

			// currentWord will be null if this word has already been found to
			// be an anagram of a word
			// already. In this case, we do not need to do anything more...
			if (currentWord != null) {
				// We will instantiate this if we find one or more anagrams of
				// 'currentWord'
				Anagram anagramSet = null;

				// Does 'currentWord' have any anagrams anywhere in the list?
				// We only need to search from index (i+1) onwards
				for (int j = i + 1; j < words.length; j++) {
					String compareWord = words[j];

					// compareWord will be null if this word has already been
					// found to be an anagram of a word already
					if (compareWord != null) {
						if (isAnagram(currentWord, compareWord)) {
							// We have an anagram!
							if (anagramSet == null) {
								// This is the first anagram we've found for
								// 'currentWord'
								anagramSet = new Anagram(currentWord);
							}

							anagramSet.addAnagram(compareWord);

							// Now clear 'compareWord' so that it does not get
							// used again to find any more anagrams
							// otherwise we may get duplicates
							words[j] = null;
						}
					}
				}

				if (anagramSet != null) {
					// Anagrams were found
					anagramSets.add(anagramSet);
				}
			}
		}

		return anagramSets;
	}

	public static void main(String[] args) {
		String fileName = null;
		if (args.length == 0) {
			System.err
					.println("Usage : java AnagramFinder <A Valid AnagramFileName>");
			return;
		}
		fileName = args[0];
		try {
			String[] wordList1 =  WordReader.getAllWords(fileName);
			AnagramFinder an = new AnagramFinder();
			List<Anagram> anagrams = an.findAnagrams(wordList1);

			System.out.println("Anagram sets are:");
			for (Anagram as : anagrams) {
				System.out.println(as.toString());
			}
		} catch (IOException e) {
			System.err.println("The file name and location is incorrect : "
					+ e.getMessage());
		}
	}
}
