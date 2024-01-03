package model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * This class is a WordleAccount that contains a username and password. Also
 * keeps track of the accounts win streak, total games, number of games won, max
 * streak and guesses per game. These stats are useful for the StatisticsPage.
 * 
 * @author Zsavanni Ware and Savannah Rabasa
 */

public class WordleAccount implements Serializable {
	private String username;
	private String password;
	private int winStreak;
	private int totalGames;
	private int numGamesWon;
	private int maxStreak;
	private HashMap<String, Integer> guessesPerGame;

	public WordleAccount(String username, String password) {
		this.username = username;
		this.password = password;
		guessesPerGame = new HashMap<String, Integer>();
		guessesPerGame.put("1", 0);
		guessesPerGame.put("2", 0);
		guessesPerGame.put("3", 0);
		guessesPerGame.put("4", 0);
		guessesPerGame.put("5", 0);
		guessesPerGame.put("6", 0);
		System.out.println("Account created!");
	}

	/**
	 * The getter for the username
	 * 
	 * @return username is the username
	 */
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String newUsername) {
		this.username = newUsername;
	}
	
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}

	/**
	 * The getter for the password
	 * 
	 * @return password is the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * The getter for the win streak
	 * 
	 * @return winStreak is the win streak
	 */
	public int getWinStreak() {
		return winStreak;
	}

	/**
	 * The getter for the total number of games
	 * 
	 * @return totalGames is the total number of games
	 */
	public int getTotalGames() {
		return totalGames;
	}

	/**
	 * The getter for the # of games won
	 * 
	 * @return numGamesWon is the # of games won
	 */
	public int getNumGamesWon() {
		return numGamesWon;
	}

	/**
	 * The getter for the max win streak
	 * 
	 * @return maxStreak is the max win streak
	 */
	public int getMaxStreak() {
		return maxStreak;
	}

	/**
	 * The getter for the # of guesses per game
	 * 
	 * @return guessesPerGame is the # of guesses per game
	 */
	public HashMap<String, Integer> getGuessesPerGame() {
		return guessesPerGame;
	}

	/**
	 * Increases win streak by one
	 */
	public void updateWinStreak() {
		winStreak++;
	}
	
	public void resetWinStreak() {
		winStreak = 0;
	}
	
	/**
	 * Increases the total # of games by one
	 */
	public void updateTotalGames() {
		totalGames++;
	}

	/**
	 * Increases the # games won by one
	 */
	public void updateNumGamesWon() {
		numGamesWon++;
	}

	/**
	 * Updates max streak
	 */
	public void updateMaxStreak(int winStreak) {
		maxStreak = winStreak;
	}

	/**
	 * The keys: are the number of guesses 1-6 The values: are the # of games that
	 * used that # of guesses Adds one to the proper value
	 * 
	 * @param numGuesses is the key that's value needs to be updated
	 */
	public void updateGuessesPerGame(String numGuesses) {
		if (Integer.valueOf(numGuesses) <= 6) {
			Integer oldNumGuesses = guessesPerGame.get(numGuesses);
			oldNumGuesses++;
			guessesPerGame.put(numGuesses, oldNumGuesses);
		}
	}

}
