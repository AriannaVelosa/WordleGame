package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.io.*;

/**
 * This is the class that contains the model section for the Wordle game.
 */
public class Wordle {
	private String word;
	private ArrayList<String> letters;
	private HashMap<String, Integer> guesses;
	private HashMap<String, Boolean> letterColor = new HashMap<>();
	private HashMap<Integer, Boolean> letterSpace = new HashMap<>();
	private Integer count;
	private boolean done;
	private File file = new File("words.txt");
	
	/**
	 * This is the constructor for the Wordle game. It randomly picks a
	 * word from the word file and sets it as the word to be guessed. It
	 * breaks the letters up into an array, and initializes variables for
	 * the total guesses, a list for guesses made, and a boolean variable
	 * to check if the game is done.
	 * @throws IOException
	 */
	public Wordle() throws IOException {
		Random rand = new Random();
		BufferedReader br = new BufferedReader(new FileReader(file));
		int number = rand.nextInt(5757);
		String st = br.readLine();
		int j = 0;
		while (j < number) {
			st = br.readLine();
			j++;
		}
		br.close();
		word = st;
		
		letters = new ArrayList<String>();
		for (int i = 0; i < word.length(); i++) {
			letters.add(String.valueOf(word.charAt(i)));
			letterColor.put(String.valueOf(word.charAt(i)), false);
			letterSpace.put(i, false);
		}
		guesses = new HashMap<String, Integer>();
		count = 0;
		done = false;
	}
	
	/**
	 * This is a getter method for count, which tracks the number of proper
	 * guesses made.
	 * @return count - an integer
	 */
	public Integer getCount() {
		return count;
	}
	
	/**
	 * This is a getter method for word, which is the String the user is
	 * trying to guess.
	 * @return word - a string
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * This is a getter method for done, a boolean value that indicates if
	 * the user guessed the word correctly.
	 * @return done - a boolean
	 */
	public boolean correct() {
		return done;
	}
	
	/**
	 * This method sets the word to one provided by the user. This is largely
	 * for doing testing.
	 * @param newWord - a string that represents the new word
	 */
	public void setWord(String newWord) {
		ArrayList<String> newList = new ArrayList<>();
		word = newWord;
		for (int i = 0; i < word.length(); i++) {
			newList.add(String.valueOf(word.charAt(i)));
			letterColor.put(String.valueOf(word.charAt(i)), false);
			letterSpace.put(i, false);
		}
		letters = newList;
	}
	
	/**
	 * This method gets a guess and compares it to all of the words in
	 * the list of 5 letter words. If it is found, then this method returns
	 * true. Otherwise, it returns false because it wasn't found.
	 * @param guess - a string
	 * @return true or false
	 * @throws IOException
	 */
	public boolean checkReal(String guess) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st = br.readLine();
		while (st != null) {
			if (st.equals(guess)) {
				br.close();
				return true;
			}
			st = br.readLine();
		}
		br.close();
		return false;
	}
	
	/**
	 * This method handles the case of receiving a bad guess from the user.
	 * It sets all of the values in the ArrayList to 0 and returns that.
	 * @return answer - an ArrayList of integers
	 */
	private ArrayList<Integer> badGuess() {
		System.out.println("Bad guess, try again");
		ArrayList<Integer> answer = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			answer.add(0);
		}
		return answer;
	}
	
	/**
	 * This method handles the case of receiving the correct word from the
	 * user. It sets all of the values in the ArrayList to 1 and returns it.
	 * @return answer - and ArrayList of integers
	 */
	private ArrayList<Integer> right() {
		ArrayList<Integer> answer = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			answer.add(1);
		}
		System.out.println("You got it right. Congrats!");
		done = true;
		return answer;
	}
	
	/**
	 * This method counts how many times a letter occurs in the word and
	 * returns that number.
	 * 
	 * @param letter - a string representing a letter
	 * @return lcount - the number of times a letter is in word
	 */
	public Integer countLetter(String letter) {
		Integer lcount = 0;
		for (int i = 0; i < 5; i++) {
			if (String.valueOf(word.charAt(i)).equals(letter)) {
				lcount++;
			}
		}
		return lcount;
	}
	
	/**
	 * This method  goes through the word guessed and word to be guessed,
	 * comparing the letters to each other. If the letter is correct, it
	 * puts 2 in the list, if it's in the word but in the wrong spot, it
	 * puts a 2, otherwise it puts a 0.
	 * @param guess - a string
	 * @return answer - an ArrayList of integers
	 */
	private ArrayList<Integer> checking(String guess) {
		ArrayList<Integer> answer = new ArrayList<Integer>();
		for (int i = 0; i < guess.length(); i++) {
			String letter = String.valueOf(guess.charAt(i));
			guesses.put(letter, i);
			if (letter.equals(letters.get(i))) {
				// Correct letter and correct location, make it green
				answer.add(1);
				letterColor.replace(letter, true);
			}
			
			else if(word.contains(letter) && letterColor.get(letter) == false) {
				// Correct letter but wrong location, make it yellow
				answer.add(2);
			}
			else {
				answer.add(0);
			}
		}
		return answer;
	}
	
	/**
	 * This method  goes through the word guessed and word to be guessed,
	 * comparing the letters to each other. If the letter is correct, it
	 * puts 2 in the list, if it's in the word but in the wrong spot, it
	 * puts a 2, otherwise it puts a 0.
	 * @param guess - a string
	 * @return answer - an ArrayList of integers
	 */
	public ArrayList<Integer> lsCheck(String guess) {
		guess = guess.toLowerCase();
		ArrayList<Integer> answer = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			String letter = String.valueOf(guess.charAt(i));
			if (letter.equals(letters.get(i))) {
				// Correct letter and correct location, make it green
				answer.add(1);
			}
			
			else if(word.contains(letter)) {
				// Correct letter but wrong location, make it yellow
				answer.add(2);
			}
			else {
				answer.add(0);
			}
		}
		return answer;
	}

	/**
	 * This method gets the guess from the user and determines what to do
	 * with it. It first determines if it is of proper length. If it isn't,
	 * it calls badGuess(). If it is, it checks if the word is actually
	 * correct. In that case it calls right(). If not, then it calls checking()
	 * to determine what letters are correct.
	 * @param guess - a string
	 * @return an ArrayList of integers
	 */
	public ArrayList<Integer> makeGuess(String guess) {
		if (guess.length() != 5) {
			return badGuess();
		}
		else {
			guess = guess.toLowerCase();
			count++;
			if (guess.equals(word)) {
				return right();
			}
			else {
				return checking(guess);
			}
		}
	}
}
