package view_controller;

import java.util.HashMap;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.AccountCollection;
import model.WordleAccount;

/**
 * Opens a Statistics Pane showing a user's overall statistics
 * 
 * @author Savannah Rabasa and Zsavanni Ware
 */
public class StatisticsPage extends BorderPane {

	private WordleAccount user;
	private AccountCollection accounts;

	// main pane
	private GridPane everything;

	// "STATISTICS" label
	private Label mainTitle = new Label("STATISTICS");

	// holds all the STATISTICS info
	private HBox mainStats = new HBox(30);

	// holds each individual statistic and corresponding label
	private VBox playedBox;
	private VBox winBox = new VBox(5);
	private VBox curBox = new VBox(5);
	private VBox maxBox = new VBox(5);

	// Numbers shown in STATISTICS area
	private Label numPlayed;
	private Label percentWins;
	private Label curStreak;
	private Label maxStreak;

	// labels shown in STATISTICS area
	private Label played = new Label("Played");
	private Label win = new Label("Win %");
	private Label currentStreak = new Label("Current Streak");
	private Label maxStreakWords = new Label("Max Streak");

	// "GUESS DISTRIBUTION" label
	private Label guessDist = new Label("GUESS DISTRIBUTION");

	// graph of guess distribution
	private BarChart<Number, String> barChart;
	private XYChart.Series graphData;

	// login or create account button
	private Button loginCreateButton;

	// global leaderboard
	private Label leaderboardLabel = new Label("Global Leaderboard");
	private Label first = new Label("First: ");
	private Label second = new Label("Second: ");
	private Label third = new Label("Third: ");
	private WordleAccount firstUser = new WordleAccount("None", "0");
	private WordleAccount secondUser = new WordleAccount("None", "0");
	private WordleAccount thirdUser = new WordleAccount("None", "0");
	private VBox leaders = new VBox(10);

	// tells layoutgui what mode to set
	private boolean isLightMode;

	/**
	 * Constructor for the stats page
	 * 
	 * @param user     current WordleAccount playing wordle
	 * @param accounts serialized accounts for global leaderboard
	 */
	public StatisticsPage(WordleAccount user, AccountCollection accounts) {
		this.user = user;
		this.accounts = accounts;
		isLightMode = true;
		layoutGUI();
	}

	/**
	 * Lays out the pane for the stats page
	 */
	private void layoutGUI() {
		everything = new GridPane();
		everything.setMinHeight(700);
		everything.setMinWidth(800);
		this.setCenter(everything);

		GridPane spacing = new GridPane();
		spacing.setMinSize(20, 20);
		everything.add(spacing, 0, 0);

		setColorMode(isLightMode);
		getStats();
		setFonts();

		// STATISTICS
		everything.add(mainTitle, 1, 0);
		mainStatsArea();

		// GUESS DISTRIBUTION
		everything.add(guessDist, 1, 2);
		makeGraph();

		getLeaderBoard();
	}

	/**
	 * Make the numeric part of the stats area and set font
	 */
	private void getStats() {
		mainStats = new HBox(30);
		numPlayed = new Label(Integer.toString(user.getTotalGames()));
		if (user.getTotalGames() != 0) {
			float pWinsFloat = (float) user.getNumGamesWon() / user.getTotalGames() * 100;
			percentWins = new Label(Integer.toString(Math.round(pWinsFloat)));
		} else
			percentWins = new Label("0");
		curStreak = new Label(Integer.toString(user.getWinStreak()));
		maxStreak = new Label(Integer.toString(user.getMaxStreak()));
	}

	/**
	 * Set all Labels to correct font
	 */
	private void setFonts() {
		Font font = Font.font("Tahoma", FontWeight.BOLD, 20);
		mainTitle.setFont(font);
		guessDist.setFont(font);
		leaderboardLabel.setFont(font);

		font = Font.font("Tahoma", FontWeight.NORMAL, 10);
		played.setFont(font);
		win.setFont(font);
		currentStreak.setFont(font);
		maxStreakWords.setFont(font);

		font = Font.font("Tahoma", FontWeight.BOLD, 25);
		numPlayed.setFont(font);
		percentWins.setFont(font);
		curStreak.setFont(font);
		maxStreak.setFont(font);

		font = Font.font("Tahoma", FontWeight.NORMAL, 15);
		first.setFont(font);
		second.setFont(font);
		third.setFont(font);
	}

	/**
	 * Creats VBox for each stat and label, the combines to a mainStats pane and
	 * adds to the main pane
	 */
	private void mainStatsArea() {
		// create VBox for each stat and its label
		playedBox = new VBox(5);
		playedBox.setAlignment(Pos.CENTER);
		playedBox.getChildren().addAll(numPlayed, played);

		winBox = new VBox(5);
		winBox.setAlignment(Pos.CENTER);
		winBox.getChildren().addAll(percentWins, win);

		curBox = new VBox(5);
		curBox.setAlignment(Pos.CENTER);
		curBox.getChildren().addAll(curStreak, currentStreak);

		maxBox = new VBox(5);
		maxBox.setAlignment(Pos.CENTER);
		maxBox.getChildren().addAll(maxStreak, maxStreakWords);

		// combine all VBoxes in an HBox for main stats area
		mainStats.setAlignment(Pos.CENTER);
		mainStats.getChildren().addAll(playedBox, winBox, curBox, maxBox);

		everything.add(mainStats, 1, 1);
	}

	/**
	 * Makes the graph of guess distributions as a bar chart
	 */
	private void makeGraph() {
		NumberAxis xAxis = new NumberAxis();
		CategoryAxis yAxis = new CategoryAxis();
		barChart = new BarChart<Number, String>(xAxis, yAxis);
		xAxis.setLabel("Frequency");
		yAxis.setTickLabelRotation(90);
		yAxis.setLabel("Number of Guesses");

		getGraphData();
		everything.add(barChart, 1, 3);
	}

	/**
	 * Gets the data for each bar in the graph and adds to the bar chart
	 */
	private void getGraphData() {
		HashMap<String, Integer> guesses = user.getGuessesPerGame();
		graphData = new XYChart.Series();
		graphData.getData().add(new XYChart.Data(guesses.get("1"), "1"));
		graphData.getData().add(new XYChart.Data(guesses.get("2"), "2"));
		graphData.getData().add(new XYChart.Data(guesses.get("3"), "3"));
		graphData.getData().add(new XYChart.Data(guesses.get("4"), "4"));
		graphData.getData().add(new XYChart.Data(guesses.get("5"), "5"));
		graphData.getData().add(new XYChart.Data(guesses.get("6"), "6"));

		barChart.getData().add(graphData);
	}

	/**
	 * Creates the global leaderboard
	 */
	private void getLeaderBoard() {
		everything.add(leaderboardLabel, 1, 5);

		firstUser = new WordleAccount("None", "0");
		secondUser = new WordleAccount("None", "0");
		thirdUser = new WordleAccount("None", "0");

		// iterate through accounts to find top 3 max streaks
		accounts.getAccounts().forEach((key, value) -> {
			if (firstUser.getUsername() == "None"
					|| accounts.getAccount(key).getMaxStreak() > firstUser.getMaxStreak()) {
				thirdUser = secondUser;
				secondUser = firstUser;
				firstUser = accounts.getAccount(key);
			} else if (secondUser.getUsername() == "None"
					|| accounts.getAccount(key).getMaxStreak() > secondUser.getMaxStreak()) {
				thirdUser = secondUser;
				secondUser = accounts.getAccount(key);
			} else if (thirdUser.getUsername() == "None"
					|| accounts.getAccount(key).getMaxStreak() > thirdUser.getMaxStreak()) {
				thirdUser = accounts.getAccount(key);
			}
		});

		first.setText("First: " + firstUser.getUsername() + ", max streak of " + firstUser.getMaxStreak());
		second.setText("Second: " + secondUser.getUsername() + ", max streak of " + secondUser.getMaxStreak());
		third.setText("Third: " + thirdUser.getUsername() + ", max streak of " + thirdUser.getMaxStreak());

		leaders = new VBox(10);
		leaders.getChildren().addAll(first, second, third);
		everything.add(leaders, 1, 6);
	}

		private void setTextColor(String color) {
		mainTitle.setTextFill(Color.web(color));
		guessDist.setTextFill(Color.web(color));
		leaderboardLabel.setTextFill(Color.web(color));
		first.setTextFill(Color.web(color));
		second.setTextFill(Color.web(color));
		third.setTextFill(Color.web(color));
		
		if(numPlayed != null) {
			numPlayed.setTextFill(Color.web(color));	
			percentWins.setTextFill(Color.web(color));
			curStreak.setTextFill(Color.web(color));
			maxStreak.setTextFill(Color.web(color));
		}
		// labels shown in STATISTICS area
		played.setTextFill(Color.web(color));
		win.setTextFill(Color.web(color));
		currentStreak.setTextFill(Color.web(color));
		maxStreakWords.setTextFill(Color.web(color));
	}
	
	/**
	 * This method sets the color mode of the gui
	 * 
	 * @param mode is either true for light mode or false for dark mode
	 */
	public void setColorMode(boolean mode) {
		isLightMode = mode;
		if (mode == true) {
			everything.setStyle("-fx-background-color: " + "white");
			setTextColor("black");
		} else {
			everything.setStyle("-fx-background-color: " + "black;");
			setTextColor("lightgrey");
		}
	}

	/**
	 * This method allows WordleGUI to update the stats page before displaying it
	 * instead of creating a new StatsPage each time.
	 */
	public void update() {
		everything.getChildren().clear();
		layoutGUI();
	}

}
