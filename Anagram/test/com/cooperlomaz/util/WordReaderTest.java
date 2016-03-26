package com.cooperlomaz.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.cooperlomaz.util.WordReader;

public class WordReaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testGetAllWordsForIncorrectFile() {
		try {
			WordReader.getAllWords("ABCD");
			fail("Error remains undetected"); 
		}catch(IOException ioe) {
			assertTrue(true);
		}catch (Exception e) {
			fail("Exception is not expected here");
		}
	}

	@Test
	public final void testGetAllWordsGoodFile() {
		try {
			String words[] = WordReader.getAllWords("resources//TestFile.txt");
			assertEquals(40, words.length);
		}catch(IOException ioe) {
			fail("Exception is not expected here");
		}catch (Exception e) {
			fail("Exception is not expected here");
		}
	}
	
}
