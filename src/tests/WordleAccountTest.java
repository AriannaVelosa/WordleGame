package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import model.AccountCollection;
import model.WordleAccount;

class WordleAccountTest {
	
	private AccountCollection accounts = new AccountCollection();
	private WordleAccount acc = new WordleAccount("Test", "123");
	private WordleAccount acc2 = new WordleAccount("Bob", "000");

	@Test
	void testCreateAccount() {
		
		System.out.println(acc.getUsername());
		assertEquals(acc.getUsername(), "Test");
		
		System.out.println(acc.getPassword());
		assertEquals(acc.getPassword(), "123");
		
		acc.updateWinStreak();
		assertEquals(acc.getWinStreak(), 1);
		
		acc.updateTotalGames();
		acc.updateTotalGames();
		assertEquals(acc.getTotalGames(), 2);
		
		acc.updateNumGamesWon();
		acc.updateNumGamesWon();
		acc.updateNumGamesWon();
		assertEquals(acc.getNumGamesWon(), 3);
		
//		acc.updateMaxStreak();
//		acc.updateMaxStreak();
		assertEquals(acc.getMaxStreak(), 2);
		
		acc.updateGuessesPerGame("1");
		acc.updateGuessesPerGame("3");
		acc.updateGuessesPerGame("5");
		
		HashMap<String,Integer> guess = acc.getGuessesPerGame();
		assertEquals(guess.get("1"), 1);
		assertEquals(guess.get("3"), 1);
		assertEquals(guess.get("5"), 1);
		acc.updateGuessesPerGame("1");
		acc.updateGuessesPerGame("2");
		acc.updateGuessesPerGame("5");
		assertEquals(guess.get("1"), 2);
		assertEquals(guess.get("2"), 1);
		assertEquals(guess.get("5"), 2);
		System.out.println(guess);		
	}
	
	@Test
	void testAccountCollection() {
		accounts.add(acc.getUsername(),acc);
		accounts.add(acc2.getUsername(),acc2);
				
		assertTrue(accounts.nameTaken("Test"));
		assertFalse(accounts.nameTaken("Ryan"));
		assertTrue(accounts.nameTaken("Bob"));
		
		assertTrue(accounts.hasAccount("Test","123"));
		assertFalse(accounts.hasAccount("Ryan", "password"));
		assertFalse(accounts.hasAccount("Bob", "111"));

		accounts.getAccount("Test");
		accounts.getAccount("Sally");
		HashMap<String,WordleAccount> accounts2 = accounts.getAccounts();

	}

}