package com.cooperlomaz.anagram;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.cooperlomaz.anagram.Anagram;
import com.cooperlomaz.anagram.AnagramFinder;

public class AnagramFinderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testIsAnagram() {
		boolean result = AnagramFinder.isAnagram("ABC", "BAC");
		assertTrue(result);
	}
	@Test
	public final void testIsAnagramWithCaps() {
		boolean result = AnagramFinder.isAnagram("Abc", "baC");
		assertTrue(result);
	}	
	@Test
	public final void testIsAnagramWithSpace() {
		boolean result = AnagramFinder.isAnagram("ABC ", "BAC ");
		assertTrue(result);
	}		
	@Test
	public final void testIsAnagramWithSpecialChar() {
		boolean result = AnagramFinder.isAnagram("ABC-", "BA-C");
		assertTrue(result);
	}	
	@Test
	public final void testFindAnagrams() {
		// Words in the word list that have length 3 and more
		String[] wORD_LIST_3 = new String[] { "rat", "bat",
				"cat", "bog", "dog", "boy", "toy", "Roy", "coy", "ray", "tar",
				"yar", "bar", "Raj", "Tej", "Gra", "Adi", "yob", "Rob", "nob",
				"jet", "wet", "pet", "met", "ice", "lad", "dal", "IAD", "Ayr",
				"A gentleman", "Elegant man" };
		AnagramFinder finder = new AnagramFinder();
		List<Anagram> anagramList = finder.findAnagrams(wORD_LIST_3);
		assertEquals(7, anagramList.size());
	}
	@Test
	public final void testFindAnagramsWithNonAnagramWords() {
		String[] wORD_LIST_Some_ANA = new String[] { "rat", "bat",
				"cat", "bog", "dog", "boy", "toy", "Roy", "coy", "ray", "tar",
				"yar", "bar", "Raj", "Tej", "Gra", "Adi", "yob", "Rob", "nob",
				"jet", "wet", "pet", "met", "ice", "lad", "dal", "IAD", "Ayr",
				"A gentleman", "Elegant man", "A", "B","C" };
		AnagramFinder finder = new AnagramFinder();
		List<Anagram> anagramList = finder.findAnagrams(wORD_LIST_Some_ANA);
		assertEquals(7, anagramList.size());
	}
	@Test
	public final void testFindAnagramsWithNOAnagramWords() {
		String[] wORD_LIST_0 = new String[] { "rat", "bat",
				"cat", "baby", "dog is my pet", "boys are good", "toys r us", "Roy the author", "cosy", "rayban", "tartar",
				"yacht", "bar", "Rajesh", "Tej", "Graeme", "Adi", "sweet", "honey", "love",
				"jetski", "race", "pet", "meet", "ice", "lad", "darling", "Info", "Aiyar",
				"gentleman", "An Elegant man" };
		AnagramFinder finder = new AnagramFinder();
		List<Anagram> anagramList = finder.findAnagrams(wORD_LIST_0);
		assertEquals(0, anagramList.size());
	}
}
