package view_controller;

import java.io.IOException;
import java.util.Random;

/**
 * Creates the GUI for wordle, allowing players to interact with the game through a virtual
 * and physical keyboard, as well as visually updating the status of the game. 
 * 
 * @author Rachel Whitaker
 * @referenced: https://asgteach.com/2011/10/javafx-animation-and-binding-simple-countdown-timer-2/ 
 */

import java.util.ArrayList;
import java.util.Optional;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.scene.text.Text;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.lang.String;
import model.Wordle;
import model.WordleAccount;

/**
 * This is the class that holds the main view of the wordle game and changes
 * things around.
 */
public class WordleGUI extends BorderPane {
	// ----- for the Timer --------
	private static final Integer STARTTIME = 30;
	protected Timeline timeline;
	private Label timerLabel;
	private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
	private boolean outOfTime = false;
	
	// ------ Visual variables -------
	private BorderPane pane = new BorderPane();
	private BorderPane statsPane = new BorderPane();
	private VBox gameArea;

	// ------ Guess area vars -------
	private Label wordleLabel;
	private ArrayList<ArrayList<StackPane>> letterSpaces;
	private final int WORDLENGTH = 5;
	private final int TOTALGUESSES = 6;

	// ----- functioning keyboard -----
	private final int KEYWIDTH = 50;
	private final int KEYHEIGHT = 60;
	private ArrayList<ArrayList<Button>> keyboard;

	// ------ functional variables ----
	private final int PANEWIDTH = 700;
	private final int PANEHEIGHT = 800;
	private String guess;
	private int guessNum;

	
	private Wordle wordle;
	private WordleAccount currUser;
	private StatisticsPage page;
	private boolean isCoWordle = false;				
	private String currPlayer = "player1";			
	private Label player1;
	private Label player2;
	private boolean isLightMode;
	protected boolean isStatsPage = false;			

	/**
	 * This method is one of the constructor options for the wordle gui.
	 * You can set the account and set the page properly. 
	 * @param account - a WordleAccount representing a user
	 * @param page - the correct statistics page for the user
	 * @throws IOException
	 */
	public WordleGUI(WordleAccount account, StatisticsPage page) throws IOException {
		guess = "";
		guessNum = 0;
		currUser = account;
		isLightMode = true;	
		this.page = page;			
		layoutGUI();
		registerHandlers();
		
		wordle = new Wordle();
		System.out.println(wordle.getWord());
		if(isCoWordle) {
			updateTimer();
		}
	}

	/**
	 * This method is the other constructor for wordle gui but accounts for
	 * doing cowordle and that layout.
	 * @param account - a WordleAccount that represents a user
	 * @param page - the user's correct statistics page
	 * @param isCoWordle - a boolean that sets cowordle on
	 * @throws IOException
	 */
	public WordleGUI(WordleAccount account, StatisticsPage page, Boolean isCoWordle) throws IOException {
		guess = "";
		guessNum = 0;
		currUser = account;
		isLightMode = true;
		this.isCoWordle = isCoWordle;
		this.page = page;
		layoutGUI();
		registerHandlers();
		wordle = new Wordle();
		System.out.println(wordle.getWord());
		if (isCoWordle) {
			updateTimer();
		}
	}

	
	/**
	 * Getter method which returns the wordle answer.
	 * 
	 * @param None
	 * @return A string that stores the wordle answer.
	 */
	public String getAnswer() {
		return wordle.getWord();
	}
	
	/**
	 * Getter method which retuns the current player if the game is
	 * in co-wordle mode. 
	 * 
	 * @param None
	 * @return A string which stores the current player.
	 */
	public String getCurrPlayer() {
		return this.currPlayer;
	}
	
	/**
	 * Getter method which returns if the game is set to light mode. 
	 * 
	 * @param None
	 * @return A boolean storing true when the game is set to lightmode and 
	 * 		   false otherwise.
	 */
	public boolean getMode() {
		return isLightMode;
	}
	
	/**
	 * Setter function which updates the mode: dark/light
	 * 
	 * @param mode - A boolean which stores true if in light mode, false otherwise.
	 * @return None
	 */
	public void setMode(boolean mode) {
		this.isLightMode = mode;
		setColorMode(this.isLightMode);
	}
	
	/**
	 * Sets the mode to coWordle.
	 * 
	 * @param None
	 * @return None
	 */
	public void setCoWordle() {
		this.isCoWordle = true;
	}
	
	/**
	 * Sets the mode to regular Wordle
	 * 
	 * @param None
	 * @return None
	 */
	public void setWordle() {
		this.isCoWordle = false;
	}
	
	/**
	 * Returns if the game states is currently cowordle. 
	 * 
	 * @return A boolean which stores true if the game mode is cowordle and false otherwise. 
	 */
	public boolean getIsCoWordle() {
		return this.isCoWordle;
	}
	
	/**
	 * Pass in whatever structure this being visually added to.
	 */
	private void layoutTimer(VBox timerSpace) { 
		timeline = new Timeline();
		timerLabel = new Label();
		timerLabel.setText(timeSeconds.toString());
        timerLabel.setTextFill(Color.RED);
        timerLabel.setStyle("-fx-font-size: 4em;");
        timerLabel.textProperty().bind(timeSeconds.asString());
	
        timerSpace.getChildren().add(timerLabel);
        timerSpace.setAlignment(Pos.CENTER); 
	}
		
	/**
	 * Creates the guess boxes for a wordle game. Each guess box is constructed of a
	 * StackPane holding a rectangle overlayed with text.
	 * 
	 * @param letters
	 */
	private void createGuessSpaces(GridPane letters) {
		for (int i = 0; i < WORDLENGTH; i++) {
			letterSpaces.add(new ArrayList<StackPane>());
			for (int j = 0; j < TOTALGUESSES; j++) {
				StackPane letterBox = new StackPane();

				// One guess area
				addOneSpace(letters, letterBox, i, j);
			}
		}
		letters.setHgap(5);
		letters.setVgap(5);
		letters.setAlignment(Pos.CENTER);
	}
	
	/**
	 * Adds a single letter space to the game to the 2D array
	 * of game spaces.
	 * 
	 * @param letters: GridPane in which each element is a stackpane.
	 * @param letterBox: StackPane which holds a rectangle and text
	 * @param i: row of element in gridpane
	 * @param j: column of element in gridpane
	 */
	private void addOneSpace(GridPane letters, StackPane letterBox, int i, int j) {
		Rectangle rect = new Rectangle(50, 50);
		rect.setFill(Color.WHITE);
		rect.setStroke(Color.LIGHTGRAY);
		Text text = new Text("");
		letterBox.getChildren().addAll(rect, text);

		letterSpaces.get(i).add(j, letterBox);
		letters.add(letterBox, i + 1, j + 1);
	}
	

	/**
	 * Creates a single row of the virtual keyboard using a string to define the
	 * content of the row.
	 * 
	 * @param strRow
	 * @param row
	 * @param rowNum
	 * @return None
	 */
	private void createLetterRow(String strRow, HBox row, int rowNum) {
		keyboard.add(new ArrayList<Button>());
		for (int i = 0; i < strRow.length(); i++) {
			keyboard.get(rowNum).add(i, new Button(String.valueOf(strRow.charAt(i))));
			keyboard.get(rowNum).get(i).setPrefSize(KEYWIDTH, KEYHEIGHT);
			keyboard.get(rowNum).get(i).setFont((Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 17)));
			

			row.getChildren().add(keyboard.get(rowNum).get(i));
		}
		row.setSpacing(5);
		row.setAlignment(Pos.CENTER);
	}

	/**
	 * Creates the bottom row of the virtual keyboard using a string. Also adds and
	 * enter and backspace key. Overloads initial createLetterRow() when
	 * enter/backspace button is needed.
	 * 
	 * @param strRow
	 * @param row
	 */
	private void createLetterRow(String strRow, HBox row, Boolean offset) {
		// Add enter key
		keyboard.add(new ArrayList<Button>());
		keyboard.get(2).add(0, new Button("Enter"));
		keyboard.get(2).get(0).setPrefSize(KEYWIDTH + 20, KEYHEIGHT);
		keyboard.get(2).get(0).setFont((Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15)));
		
		// Add letters to keyboard row
		row.getChildren().add(keyboard.get(2).get(0));
		for (int i = 0; i < strRow.length(); i++) {
			keyboard.get(2).add(i + 1, new Button(String.valueOf(strRow.charAt(i))));
			row.getChildren().add(keyboard.get(2).get(i + 1));
			keyboard.get(2).get(i + 1).setPrefSize(KEYWIDTH, KEYHEIGHT);
			keyboard.get(2).get(i + 1).setFont((Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 17)));
		}
		
		// Add Backspace Key
		keyboard.get(2).add(7, new Button("<--")); 
		keyboard.get(2).get(7).setPrefSize(KEYWIDTH + 20, KEYHEIGHT);
		keyboard.get(2).get(7).setFont((Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 17)));
		row.getChildren().add(keyboard.get(2).get(7));
		row.setSpacing(5);
		row.setAlignment(Pos.CENTER);
	}
	
	/**
	 * Randomly selects which player has the first move.
	 * 
	 * @param None
	 * @return None
	 */
	private void choosePlayer() { 
		Random rand = new Random();
		int player_num = rand.nextInt(2);
		if(player_num == 0) {
			currPlayer = "player1";
		}
		else {
			currPlayer = "player2";
		}
		System.out.println(player_num);
	}
	
	/**
	 * Sets up the board so it indicates which player is guessing first by
	 * bolding the player's name and highlighting the first row with the player's 
	 * color. 
	 * 
	 * @param None
	 * @return None
	 */
	private void setFirstPlayerLayout(){ 
		choosePlayer();
		switchRowColor();
		
	}	
	/**
	 * Sets up the GUI of the title section and player names 
	 * if user(s) are playing coWordle option.
	 * 
	 * @param titleSection
	 */
	private void layoutHeader(HBox titleSection) { 
		if (isCoWordle) {							
			layoutCoWordleHeader(titleSection);
		}
		
		titleSection.setSpacing(400);
		titleSection.setAlignment(Pos.CENTER);
	}
	
	private void layoutCoWordleHeader(HBox titleSection) {
		player1 = new Label("Player 1");
		player1.setTextFill(Color.BLUE);
		player2 = new Label("Player 2");
		player2.setFont(Font.font("Arial"));
		player2.setTextFill(Color.RED);
		titleSection.getChildren().addAll(player1, wordleLabel, player2);
		titleSection.setSpacing(400);
		titleSection.setAlignment(Pos.CENTER);
	}
	
	/**
	 * Switches the color of the guessing spaces when transitioning between
	 * light and dark mode. 
	 * @param background
	 * @param border
	 * @param text
	 */
	private void switchLetterSpaceColors(String background, String border, String text) {
		// set all letter spaces to alternate color
		for (int i = 0; i < letterSpaces.get(0).size(); i++) {
			setRowColor(background, border, i); 
			if(isCoWordle) {
				switchRowColor();
			}
			for (int j = 0; j < letterSpaces.size(); j++) {
				((Text) letterSpaces.get(j).get(i).getChildren().get(1)).setFill(Color.web(text));
			}
		}
	}
	
	/**
	 * Switches the color of the keyboard spaces when transitioning between dark and light mode. 
	 * @param text
	 * @param background
	 */
	private void switchKeyboardColor(String text, String background) {
		// keyboard to alternate color
		System.out.println("Switching the keyboard color ....");
		
		for (int i = 0; i < keyboard.size(); i++) {
			for (int j = 0; j < keyboard.get(i).size(); j++) {			
				int settingsLength = keyboard.get(i).get(j).getStyle().split("\\s+").length; 
				if(settingsLength >= 4) {

					// Only switch color if it has not already been guessed.
					String textColor = keyboard.get(i).get(j).getStyle().split("\\s+")[3];	
					if(textColor.equals("grey;") || textColor.equals("lightgrey")) {
						keyboard.get(i).get(j)
						.setStyle("-fx-text-fill: " + text + "; -fx-background-color: " + background + ";");
					}
				}
			}
		}
	}
	
	/**
	 * Changes game mode between dark and lightmode. 
	 * 
	 * @param mode
	 */
	private void setColorMode(boolean mode) {
		if (mode == true) {
			pane.setStyle("-fx-background-color: " + "white");
			wordleLabel.setTextFill(Color.web("black"));
			switchLetterSpaceColors("white", "grey", "black");
			switchKeyboardColor("black", "lightgrey");
		} else {
			pane.setStyle("-fx-background-color: " + "black");
			wordleLabel.setTextFill(Color.web("white"));
			switchLetterSpaceColors("black", "dimgrey", "white");
			switchKeyboardColor("white", "grey");				
		}

	}

	private void layoutGUI() {
		pane.setMinHeight(PANEHEIGHT);
		pane.setMinWidth(PANEWIDTH);
		this.setCenter(pane);
		
		gameArea = new VBox();

		// Title section
		wordleLabel = new Label("Wordle");

		//player2.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		wordleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
		
		HBox titleSection = new HBox();
		layoutHeader(titleSection);
		
		// ---------------- Create guess spaces ---------------
		GridPane letters = new GridPane();
		letterSpaces = new ArrayList<ArrayList<StackPane>>();
		createGuessSpaces(letters);

		VBox timerSpace = new VBox();
		layoutTimer(timerSpace);        
		
		// ------------------- Create Keyboard ----------------
		keyboard = new ArrayList<ArrayList<Button>>();
		HBox topRow = new HBox();
		createLetterRow("QWERTYUIOP", topRow, 0);

		HBox centerRow = new HBox();
		createLetterRow("ASDFGHJKL", centerRow, 1);

		HBox bottomRow = new HBox();
		createLetterRow("ZXCVBNM", bottomRow, true);

		if(isLightMode) {
			setColorMode(true);
		}
		else {
			setColorMode(false); 
		}
		
		// Add elements to VBox
		if(isCoWordle) {
			gameArea.getChildren().addAll(wordleLabel, titleSection, letters, timerSpace, topRow, centerRow, bottomRow);
			setFirstPlayerLayout();
		}
		else {
			gameArea.getChildren().addAll(wordleLabel, titleSection, letters, topRow, centerRow, bottomRow);
		}
		gameArea.setAlignment(Pos.CENTER);
		gameArea.setSpacing(10);
		pane.setCenter(gameArea);

		
		
		// Set focus to static element so enter on physical keyboard
		// does not affect the "focus" of the virtual keyboard.
		wordleLabel.requestFocus();
	}

	/**
	 * Process letter, only add it to guess if number of letters in the guess is
	 * less than 5.
	 * 
	 * Adds to proper row and column of guesses.
	 * 
	 * @param letter
	 */
	private void processGuess(String letter) {
		if (!gameOver() && guess.length() < 5 && guessNum < 6) {
			guess += letter;
			((Text) letterSpaces.get(guess.length() - 1).get(guessNum).getChildren().get(1)).setText(letter);
			((Text) letterSpaces.get(guess.length() - 1).get(guessNum).getChildren().get(1)).setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		}
		wordleLabel.requestFocus();
	}

	/**
	 * Deletes the most recently entered letter from the guess.
	 * 
	 * Removes letter from proper row and column.
	 */
	private void backspace() {
		if (!gameOver() && guess.length() > 0) {
			guess = guess.substring(0, guess.length() - 1);
			((Text) letterSpaces.get(guess.length()).get(guessNum).getChildren().get(1)).setText("");
		}
	}
	
	/**
	 * This method changes the color of buttons and letter spaces based
	 * on whether or not the guess was correct.
	 */
	private void setColor() {
		ArrayList<Integer> color = wordle.makeGuess(guess);
		ArrayList<Integer> lsColor = wordle.lsCheck(guess);
		System.out.println(color);
		for (int i = 0; i < color.size(); i++) {
			// The letter is not in the right spot
			// Button currB = findButton(String.valueOf(guess.charAt(i)));
			if (color.get(i) == 2) {
				changeButtonColor(i, "#c8b653;");
			}
			if (lsColor.get(i) == 2) {
				lsYellow(i);
			}
			// The letter is in the right spot
			if (color.get(i) == 1) {
				changeButtonColor(i, "#6ca965;");// "#03c04a;");
			}
			if (lsColor.get(i) == 1) {
				lsGreen(i);
			}
			// The letter is not in the word
			if (color.get(i) == 0) {
				changeButtonColor(i, "#787c7f;");
			}
			if (lsColor.get(i) == 0) {
				lsGrey(i);
			}
		}
	}
	
	private void changeButtonColor(int index, String colorHex) {
		Button currB = findButton(String.valueOf(guess.charAt(index)));
		if(isLightMode) {
		currB.setStyle("-fx-text-fill: black; -fx-background-color: "+ colorHex);
	
		}else {
			currB.setStyle("-fx-text-fill: white; -fx-background-color: "+ colorHex);
			
		}
		
	}
	
	/**
	 * This method changes a letter space to yellow.
	 * @param index - an integer that represents the location of the lSpace
	 */
	private void lsYellow(Integer index) {
		Node lSpace = letterSpaces.get(index).get(guessNum - 1).getChildren().get(0);
		lSpace.setStyle("-fx-fill: #c8b653;");			
	}
	
	/**	
	 * This method changes a letter space to green.
	 * @param index - an integer that represents the location of the lSpace
	 */
	private void lsGreen(Integer index) {
		Node lSpace = letterSpaces.get(index).get(guessNum - 1).getChildren().get(0);
		lSpace.setStyle("-fx-fill: #6ca965;");
	}
	
	/**	
	 * This method changes a letter space to green.
	 * @param index - an integer that represents the location of the lSpace
	 */
	private void lsGrey(Integer index) {
		Node lSpace = letterSpaces.get(index).get(guessNum - 1).getChildren().get(0);
		lSpace.setStyle("-fx-fill: #787c7f;");
	}

	/**
	 * Updates the GUI to show the user won by putting a label on the screen. Should
	 * be changed later to pull up the statistics page.
	 */
	private void gameWon() {
		Confetti confetti = new Confetti(PANEHEIGHT, PANEWIDTH, wordle.getWord(), currUser.getUsername(), isCoWordle, isLightMode);
		VBox confettiPane = confetti.getBox();
		confetti.confettiFall();
		pane.setTop(confettiPane);
	}

	/**
	 * This method creates the labels that indicate that a user has lost. It checks
	 * for cowordle, which changes the message slightly. It also checks for dark
	 * mode to change the color of the font. It adds it to the top of the pane.
	 */
	private void lost() {
		Label lost = new Label("You lost. The word was " + wordle.getWord() + ".");
		Label wait = new Label("Better luck next time :(... Currating your stats...");
		if (isCoWordle) {
			wait.setText("Better luck next time... dare to try again?");
		}
		Font font1 = Font.font("Tahoma", FontWeight.BOLD, 25);
		Font font2 = Font.font("Tahoma", FontWeight.BOLD, 15);
		lost.setFont(font1);
		lost.setTextFill(Color.RED);
		wait.setFont(font2);
		if (!isLightMode) {
			wait.setTextFill(Color.WHITE);
		}
		VBox labels = new VBox();
		labels.getChildren().add(lost);
		labels.getChildren().add(wait);
		labels.setStyle("-fx-padding: 15 0 0 0;");
		labels.setAlignment(Pos.CENTER);
		pane.setTop(labels);
	}

	/**
	 * This method shows an alert to the user warning them that the guess
	 * that they gave was not of proper length or type.
	 */
	private void wrong() {
		wrongAnimation();
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Word not in list or too short");
		alert.setContentText("Must be a real 5 letter word");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			wrongAnimation();
		}
		else {
			wrongAnimation();
		}
	}
	

	/**
	 * This method hndles the animation for when the user gives an incorrect
	 * guess. It uses RotateTransition to partially rotate the letter spaces
	 * and then rotate them back.
	 */
	private void wrongAnimation() {
		for (int i = 0; i < 5; i++) {
			Node lSpace = letterSpaces.get(i).get(guessNum).getChildren().get(0);
			RotateTransition rotate = new RotateTransition();
			rotate.setAxis(Rotate.Y_AXIS);
			rotate.setByAngle(45);
			rotate.setCycleCount(2);
			rotate.setDuration(Duration.millis(400));
			rotate.setAutoReverse(true);
			rotate.setNode(lSpace);
			rotate.play();
		}
	}

	/**
	 * Checks if the wordle game is at an end-game state and changes label to
	 * appropriate label if the game is won.
	 */
	private void checkEndGame() {
		if (wordle.correct()) {
			timeline.stop();
			gameWon();
			updateStats(true);
			page.update();
			pausePopUp();
		}
		// else if not correct, else if out of guesses and incorrect
		if (guessNum == 6) {
			timeline.stop();
			System.out.println("End of game");
			if (!wordle.correct()) {
				updateStats(false);
				lost();
			}
			page.update();
			pausePopUp();
		}
		if (isCoWordle && !outOfTime) {
			switchPlayer();
		}
	}



	/**
	 * This method updates the Statistics page
	 * @param won tells the page to update for a win or a loss
	 */
	private void updateStats(boolean won) {
		if(won && !isCoWordle) {
			// update user's stats
			currUser.updateWinStreak();
			currUser.updateTotalGames();
			currUser.updateNumGamesWon();
			currUser.updateGuessesPerGame(String.valueOf(guessNum));
			if (currUser.getMaxStreak() < currUser.getWinStreak())
				currUser.updateMaxStreak(currUser.getWinStreak());
		}
		else if(!won && !isCoWordle) {
			currUser.updateTotalGames();
			currUser.resetWinStreak();
		}
		
	}
	
	/**
	 * This method determines if the current game is over
	 * 
	 * @return true if game is over, otherwise false
	 */
	private boolean gameOver() {
		if (guessNum >= 6 || wordle.correct() || outOfTime) {
			return true;
		}
		return false;
	}

	/**
	 * This method resets the WordleGUI to the center of the pane instead of the pop
	 * up stats page.
	 * 
	 * @throws IOException
	 */
	public void resetWordleGUI() throws IOException { 
		pane.setTop(null);
		this.setCenter(pane); // switches from end game to game
		isStatsPage = false;
	}

	/**
	 * This method starts the Wordle game to a new game
	 * @throws IOException
	 */
	public void startWordleGUI() throws IOException {
		pane.setTop(null);
		this.setCenter(pane);
		guess = "";
		guessNum = 0;
		wordle = new Wordle();
		System.out.println(wordle.getWord());
		layoutGUI();
	}

	/**
	 * This method resets the CoWordle game to a new game
	 * @throws IOException
	 */
	public void resetCoWordleGUI() throws IOException {
		pane.setTop(null);
		this.setCenter(pane);		//switches from end game to game
		if(!isCoWordle) {		
			isStatsPage = false;
			System.out.println("Not cowordle");
		} else {
			System.out.println("Add cowordle elements");
			// Add functionailty, switches endgame back to game.
		}
		guess = "";
		guessNum = 0;
		wordle = new Wordle();
		System.out.println(wordle.getWord());
		layoutGUI();
		if (isCoWordle) {
			updateTimer();
		}
	}

	/**
	 * This method allows there to be a 5 second pause before the stats pop up page
	 * is displayed. Allows user to view if they won or lost instead of instantly
	 * switching to stats.
	 */
	private void pausePopUp() {
		// set true first so stats menu button can't be pressed
		if (!isCoWordle) {
			isStatsPage = true;
			PauseTransition pause = new PauseTransition(Duration.seconds(5));
			pause.setOnFinished(event -> {
				statsPane.setCenter(page);
				this.setCenter(statsPane);
			});
			pause.play();
		}
	}

	/**
	 * Resets time to 30 seconds after each player's turn. 
	 */
	private void updateTimer() {
		if (timeline != null) {
			timeline.stop();
		}
		timeSeconds.set(STARTTIME);
		timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(STARTTIME + 1), new KeyValue(timeSeconds, 0)));
		timeline.playFromStart();
		timeline.setOnFinished(event -> {
			if(isCoWordle) {
				System.out.println("COUNTDOWN FINISHED");
				lost();
				outOfTime = true;							
			}
		});
	}
	
	/**
	 * This method goes through a row of the rectangles that make up the
	 * letter spaces and uses RotateTransition to rotate the letter spaces
	 * after a guess is made.
	 */
	private void lsRotate() {
		for (int i = 0; i < 5; i++) {
			Node lSpace = letterSpaces.get(i).get(guessNum - 1).getChildren().get(0);
			
			RotateTransition rotate = new RotateTransition();
			rotate.setAxis(Rotate.Y_AXIS);
			rotate.setByAngle(360);
			rotate.setCycleCount(1);
			rotate.setDuration(Duration.millis(1000));
			rotate.setNode(lSpace);
			rotate.play();
		}
	}
	
	
	/**
	 * Functionality when player presses enter button to check guess, 
	 * update board to reflect changes caused by player guess. 
	 * 
	 * @throws IOException
	 */
	private void enter() throws IOException {
		if (guess.length() == 5 && wordle.checkReal(guess.toLowerCase())) {
			if (isCoWordle && isLightMode) {
				setRowColor("white", "lightgray", guessNum);
			} else if (isCoWordle) {
				setRowColor("black", "lightgray", guessNum);
			}
			guessNum += 1;
			lsRotate();
			setColor();
			checkEndGame();
			if (isCoWordle && !gameOver()) {
				updateTimer();
			}
			// updateTimer();
			guess = "";
		} else if (!gameOver()) {
			wrongAnimation();
		}
	}

	/**
	 * Used for CoWordle to change a row of letter spaces to another color. 
	 * Sets row to grey, red or blue depending on player or if row is being guessed.  
	 * 
	 * @param fill
	 * @param border
	 * @param rowNumber
	 */
	private void setRowColor(String fill, String border, int rowNumber) {
		for (int i = 0; i < 5; i++) {
			((Rectangle) letterSpaces.get(i).get(rowNumber).getChildren().get(0)).setFill(Color.web(fill));
			((Rectangle) letterSpaces.get(i).get(rowNumber).getChildren().get(0)).setStroke(Color.web(border));
		}
	}
	
	private void switchRowToLightMode() {
		if (currPlayer.equals("player1") && guessNum <= 5) {
			player1.setFont(Font.font("Arial", FontWeight.BOLD, 13));
			setRowColor("lightblue", "blue", guessNum);
		} else if (guessNum <= 5) {
			player2.setFont(Font.font("Arial", FontWeight.BOLD, 13));
			setRowColor("lightpink", "red", guessNum);
		}
	}

	private void switchRowToDarkMode() {
		if (currPlayer.equals("player1") && guessNum < 6) {
			setRowColor("darkslateblue", "blue", guessNum);
		} else if (currPlayer.equals("player2") && guessNum < 6) {
			setRowColor("darkred", "red", guessNum);
		}
	}

	/**
	 * For CoWordle: Determines which color the current row needs to be set to based
	 * on which player's turn it is.
	 */
	private void switchRowColor() {
		if (isLightMode) {
			switchRowToLightMode();
		} else {
			switchRowToDarkMode();
		}
	}

	/**
	 * For CoWordle: switches which player's turn it is and changes the GUI to
	 * reflect which player is guessing by bolding their name.
	 */
	private void switchPlayer() {
		if (currPlayer.equals("player1")) {
			currPlayer = "player2";
			player1.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
			player2.setFont(Font.font("Arial", FontWeight.BOLD, 13));
			switchRowColor();
		} else {
			currPlayer = "player1";
			player1.setFont(Font.font("Arial", FontWeight.BOLD, 13));
			player2.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
			switchRowColor();
		}
	}

	/**
	 * Goes through the keyboard to find the button that matches a letter provided.
	 */
	private Button findButton(String letter) {
		for (int i = 0; i < keyboard.size(); i++) {
			for (int j = 0; j < keyboard.get(i).size(); j++) {
				if (keyboard.get(i).get(j).getText().equals(letter.toUpperCase())) {
					return keyboard.get(i).get(j);
				}
			}
		}
		return null;
	}
	
	/**
	 * Sets up functionality of the keyboard both on-screen and physical. Registers
	 * handlers for every button on keyboard.
	 *
	 */
	private void registerHandlers() {
		registerVirtualFirstRow();
		registerVirtualSecondRow();
		registerVirtualThirdRow();
		registerPhysicalKeyboard();
	}
	
	/**
	 * Register the handlers for the first row of buttons for the on-screen
	 * keyboard. Matches the same format as a physical keyboard.
	 * 
	 * @param None
	 * @return None
	 */
	private void registerVirtualFirstRow() {
		this.keyboard.get(0).get(0).setOnAction((event) -> { processGuess("Q"); });
		this.keyboard.get(0).get(1).setOnAction((event) -> { processGuess("W"); });
		this.keyboard.get(0).get(2).setOnAction((event) -> { processGuess("E"); });
		this.keyboard.get(0).get(3).setOnAction((event) -> { processGuess("R"); });
		this.keyboard.get(0).get(4).setOnAction((event) -> { processGuess("T"); });
		this.keyboard.get(0).get(5).setOnAction((event) -> { processGuess("Y"); });
		this.keyboard.get(0).get(6).setOnAction((event) -> { processGuess("U"); });
		this.keyboard.get(0).get(7).setOnAction((event) -> { processGuess("I"); });
		this.keyboard.get(0).get(8).setOnAction((event) -> { processGuess("O"); });
		this.keyboard.get(0).get(9).setOnAction((event) -> { processGuess("P");	});
	}

	/**
	 * Register the handlers for the second row of buttons for the on-screen
	 * keyboard. Matches the same format as a physical keyboard.
	 * 
	 * @param None
	 * @return None
	 */
	private void registerVirtualSecondRow() {
		this.keyboard.get(1).get(0).setOnAction((event) -> { processGuess("A");	});
		this.keyboard.get(1).get(1).setOnAction((event) -> { processGuess("S"); });
		this.keyboard.get(1).get(2).setOnAction((event) -> { processGuess("D"); });
		this.keyboard.get(1).get(3).setOnAction((event) -> { processGuess("F");	});
		this.keyboard.get(1).get(4).setOnAction((event) -> { processGuess("G");	});
		this.keyboard.get(1).get(5).setOnAction((event) -> { processGuess("H"); });
		this.keyboard.get(1).get(6).setOnAction((event) -> { processGuess("J"); });
		this.keyboard.get(1).get(7).setOnAction((event) -> { processGuess("K"); });
		this.keyboard.get(1).get(8).setOnAction((event) -> { processGuess("L"); });
	}

	/**
	 * Register the handlers for the third row of buttons for the on-screen
	 * keyboard. Matches the same format as a physical keyboard, but includes enter
	 * and backspace buttons.
	 * 
	 * @param None
	 * @return None
	 */
	public void registerVirtualThirdRow() {
		this.keyboard.get(2).get(0).setOnAction(event -> {
			try {
				enter();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
 
		this.keyboard.get(2).get(1).setOnAction((event) -> { processGuess("Z");	});
		this.keyboard.get(2).get(2).setOnAction((event) -> { processGuess("X");	});
		this.keyboard.get(2).get(3).setOnAction((event) -> { processGuess("C");	});
		this.keyboard.get(2).get(4).setOnAction((event) -> { processGuess("V");	});
		this.keyboard.get(2).get(5).setOnAction((event) -> { processGuess("B");	});
		this.keyboard.get(2).get(6).setOnAction((event) -> { processGuess("N"); });
		this.keyboard.get(2).get(8).setOnAction((event) -> { processGuess("M");	});
		this.keyboard.get(2).get(7).setOnAction((event) -> { backspace(); });
	}
	
	/**
	 * Implement physical keyboard, including all letters as well as enter and
	 * backspace buttons. Numbers and tabs do affect wordle game.
	 * 
	 * @param None
	 * @return None
	 */
	public void registerPhysicalKeyboard() {
		wordleLabel.requestFocus();
		this.pane.setOnKeyPressed((event) -> {
			if (!gameOver()) {
				String currEvent = event.getCode().toString();
				if (currEvent.equals("BACK_SPACE")) {
					backspace();
				} else if (currEvent.equals("ENTER")) {
					try {
						enter();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (currEvent.matches("^[a-zA-Z]*$") && currEvent.length() == 1) {
					processGuess(currEvent);
				}
			}
		});
	}

}
