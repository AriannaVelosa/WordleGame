package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.Test;

import model.Wordle;

/**
 * This is the class that does testing of Wordle.java. It checks to make
 * sure that the game itself functions as it should.
 */
public class WordleTest {
	
	/**
	 * This function tests the helpful methods in Wordle. This includes all
	 * of the getter methods, correct, checkReal, and countLetter to make
	 * sure they work as they should using the word that the guess is set to.
	 * 
	 * Parameters: None
	 * Returns: None
	 * 
	 * @throws IOException
	 */
	@Test
	public void testWordleFunctions() throws IOException {
		Wordle wordle = new Wordle();
		wordle.setWord("trees");
		assertEquals(0, wordle.getCount());
		String word = wordle.getWord();
		assertEquals(5, word.length());
		assertFalse(wordle.correct());
		assertFalse(wordle.checkReal("asdfe"));
		assertTrue(wordle.checkReal("light"));
		assertEquals(1, wordle.countLetter("t"));
	}
	
	/**
	 * This method tests the checking of a guess in Wordle. It uses setWord
	 * to get uniform answers every time, and sets the word to trees. It
	 * checks how it handles a word that is too short, a word that is correct,
	 * a word that is partially correct, and then also tests the method
	 * that gets the correct values for the letter spaces
	 * 
	 * Parameters: None
	 * Returns: None
	 * 
	 * @throws IOException
	 */
	@Test
	public void testWordleGuess() throws IOException {
		Wordle wordle = new Wordle();
		wordle.setWord("trees");
		assertEquals("[0, 0, 0, 0, 0]", (wordle.makeGuess("the")).toString());
		assertEquals("[1, 1, 1, 1, 1]", (wordle.makeGuess("trees")).toString());
		assertEquals("[0, 0, 2, 2, 1]", (wordle.makeGuess("carts")).toString());
		assertEquals("[1, 1, 1, 0, 2]", (wordle.lsCheck("treat")).toString());
	}
}
