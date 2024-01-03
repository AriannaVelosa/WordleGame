package view_controller;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.AccountCollection;
import model.WordleAccount;

/**
 * This class displays the Login and Signup Pane for Wordle. Uses serialization
 * in order to login to a previously created account and to save future
 * accounts. Still needs to implement behavior for after user signs in or
 * creates an account.
 * 
 * @author Zsavanni Ware
 */
public class LoginPane extends Application {

	// Visual variables for login and sign up page
	private BorderPane pane = new BorderPane();
	private Label welcome = new Label("WELCOME!");
	private Label loginTitle = new Label("LOGIN TO WORDLE ACCOUNT");
	private Label signupTitle = new Label("CREATE A WORDLE ACCOUNT");
	private Label username = new Label("username");
	private Label password = new Label("password");
	private Label noAccountTitle = new Label("Don't have an account?");
	private Label or = new Label("OR");
	private Button enter1 = new Button("Enter");
	private Button enter2 = new Button("Enter");
	private Button signup = new Button("sign up");
	private Button guest = new Button("CONTINUE AS GUEST");
	private Button back = new Button("Back");
	private TextField usernameField = new TextField();
	private PasswordField passwordField = new PasswordField();
	private StackPane titlePane = new StackPane();
	public final int PANEWIDTH = 700;
	public final int PANEHEIGHT = 800;

	private AccountCollection accounts = new AccountCollection();
	private WordleAccount currUser;
	private WordleGUI game;
	private boolean isLightMode = true;
	private StatisticsPage page;
	private AccountSettingsPage settings;

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This method starts the GUI by laying out the login page calling the register
	 * handler method.
	 * 
	 * @param stage is the area to display all GUI elements
	 */
	@Override
	public void start(Stage stage) throws Exception {
		accounts.readAccounts();
		accounts.print();
		layoutGUI();
		registerHandlers();
		Scene scene = new Scene(pane, PANEWIDTH, PANEHEIGHT);
		stage.setScene(scene);
		stage.show();

		// close alert for saving
		stage.setOnCloseRequest((event) -> {
			saveChanges();
		});
	}

	/**
	 * This method sets up the whole login page
	 */
	private void layoutGUI() {
		// Font.getFontNames().forEach(System.out::println);
		pane.setStyle("-fx-background-color: " + "lightgrey");
		setFonts();
		VBox layout = new VBox();
		HBox usernameLayout = new HBox();
		HBox passwordLayout = new HBox();
		HBox noAccountLayout = new HBox();

		layout.getChildren().add(titlePane);
		decor();

		layout.getChildren().add(welcome);
		layout.getChildren().add(loginTitle);

		usernameLayout.getChildren().add(username);
		usernameLayout.getChildren().add(usernameField);
		passwordLayout.getChildren().add(password);
		passwordLayout.getChildren().add(passwordField);
		noAccountLayout.getChildren().add(noAccountTitle);
		noAccountLayout.getChildren().add(signup);

		layout.getChildren().add(usernameLayout);
		layout.getChildren().add(passwordLayout);
		layout.getChildren().add(enter1);
		layout.getChildren().add(noAccountLayout);
		layout.getChildren().add(or);
		layout.getChildren().add(guest);

		layout.setAlignment(Pos.CENTER);
		layout.setSpacing(30);
		layout.setPadding(new Insets(50));

		usernameLayout.setAlignment(Pos.CENTER);
		usernameLayout.setSpacing(10);
		usernameLayout.setPadding(new Insets(20, 0, 0, 0));
		passwordLayout.setAlignment(Pos.CENTER);
		passwordLayout.setSpacing(10);
		passwordLayout.setPadding(new Insets(20, 0, 0, 0));
		noAccountLayout.setAlignment(Pos.CENTER);
		noAccountLayout.setSpacing(10);
		noAccountLayout.setPadding(new Insets(20, 0, 10, 0));
		pane.setTop(layout);
	}

	/**
	 * This method sets up the whole sign up page
	 */
	private void signupLayoutGUI() {
		VBox layout = new VBox();
		HBox usernameLayout = new HBox();
		HBox passwordLayout = new HBox();
		HBox backButtonLayout = new HBox();

		layout.getChildren().add(titlePane);
		layout.getChildren().add(welcome);
		layout.getChildren().add(signupTitle);

		usernameLayout.getChildren().add(username);
		usernameLayout.getChildren().add(usernameField);
		passwordLayout.getChildren().add(password);
		passwordLayout.getChildren().add(passwordField);
		backButtonLayout.getChildren().add(back);

		layout.getChildren().add(usernameLayout);
		layout.getChildren().add(passwordLayout);
		layout.getChildren().add(enter2);
		layout.getChildren().add(or);
		layout.getChildren().add(guest);
		layout.getChildren().add(backButtonLayout);

		layout.setAlignment(Pos.CENTER);
		layout.setSpacing(30);
		layout.setPadding(new Insets(50));

		usernameLayout.setAlignment(Pos.CENTER);
		usernameLayout.setSpacing(10);
		usernameLayout.setPadding(new Insets(20, 0, 0, 0));
		passwordLayout.setAlignment(Pos.CENTER);
		passwordLayout.setSpacing(10);
		passwordLayout.setPadding(new Insets(20, 0, 0, 0));
		backButtonLayout.setAlignment(Pos.BASELINE_LEFT);
		backButtonLayout.setSpacing(10);
		backButtonLayout.setPadding(new Insets(90, 0, 0, 5));
		pane.setTop(layout);
	}

	/**
	 * This method sets the font and style for all the login and sign up page
	 * elements
	 */
	private void setFonts() {
		Font font1 = Font.font("Tahoma", FontWeight.BOLD, 50);
		Font font2 = Font.font("Tahoma", 20);
		Font font3 = Font.font("Tahoma", 15);

		welcome.setFont(font2);
		loginTitle.setFont(font2);
		signupTitle.setFont(font2);
		username.setFont(font3);
		password.setFont(font3);
		enter1.setFont(font3);
		enter2.setFont(font3);
		noAccountTitle.setFont(font2);
		signup.setFont(font3);
		or.setFont(font2);
		guest.setFont(font3);
		back.setFont(font3);

	}

	/**
	 * Adds one square to the title.
	 */
	private StackPane addOneSquare(StackPane temp, Rectangle rect, String title, int i) {
		Font font1 = Font.font("Tahoma", FontWeight.BOLD, 50);

		temp = new StackPane();
		Text letter = new Text(String.valueOf(title.charAt(i)));
		letter.setFont(font1);
		letter.setFill(Color.LIGHTGREY);

		temp.getChildren().addAll(rect, letter);
		return temp;
	}

	/**
	 * This method sets up the row of squares that appears behind the Wordle title
	 */
	private void decor() {
		HBox squares = new HBox();
		squares.setAlignment(Pos.CENTER);
		squares.setSpacing(11);
		String title = "WORDLE";
		// 6 squares for each letter in Wordle
		for (int i = 0; i < 6; i++) {
			Rectangle rect = new Rectangle(70, 70);
			StackPane temp = addOneSquare(new StackPane(), rect, title, i);

			if (i == 0 || i == 3) {
				rect.setFill(Color.SEAGREEN);
				rect.setStroke(Color.BLACK);
				rect.setStrokeWidth(3);
			}
			if (i == 1 || i == 2 || i == 4) {
				rect.setFill(Color.DIMGREY);
				rect.setStroke(Color.BLACK);
				rect.setStrokeWidth(3);
			}
			if (i == 5) {
				rect.setFill(Color.GOLDENROD);
				rect.setStroke(Color.BLACK);
				rect.setStrokeWidth(3);
			}
			squares.getChildren().add(temp);
		}
		titlePane.getChildren().add(squares);
	}

	/**
	 * This method is called when a user attempts to login. If any login information
	 * is incorrect an alert is shown.
	 * 
	 * @throws IOException if there's a words.txt file issue
	 */
	private void firstEnterButton() throws IOException {
		String username = usernameField.getText();
		String password = passwordField.getText();
		if (accounts.hasAccount(username, password)) {
			currUser = accounts.getAccount(username);
			System.out.println("Welcome " + username + "!");

			// WordleGUI
			page = new StatisticsPage(currUser, accounts);
			game = new WordleGUI(currUser, page);
			settings = new AccountSettingsPage(currUser,accounts);
			setViewTo(game);
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Invalid information, try again");
			Optional<ButtonType> result = alert.showAndWait();
			usernameField.clear();
			passwordField.clear();
		}
	}

	/**
	 * This method creates the menu, menu bar, and menu items. It also calls the
	 * menu listeners for each option.
	 */
	private void addMenuRegisterListeners() {
		MenuItem wordle = new MenuItem("Wordle");
		MenuItem statistics = new MenuItem("Statistics");

		MenuItem logout = new MenuItem("Logout");
		MenuItem newGame = new MenuItem("New Game");
		MenuItem theme = new MenuItem("Switch Theme");
		MenuItem coWordle = new MenuItem("Co-Wordle");
		MenuItem accountSettings = new MenuItem("Account Settings");
		MenuItem howToPlay = new MenuItem("How to Play");

		Menu menu = new Menu("Menu");
		menu.getItems().addAll(coWordle, wordle, statistics, newGame, theme, howToPlay, accountSettings, logout);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(menu);
		pane.setTop(menuBar);

		registerMenuHandlers(coWordle);
		registerMenuHandlers(wordle);
		registerMenuHandlers(statistics);
		registerMenuHandlers(newGame);
		registerMenuHandlers(theme);
		registerMenuHandlers(accountSettings);
		registerMenuHandlers(logout);
		registerMenuHandlers(howToPlay);
	}

	/**
	 * This method handles setting the view when a menu option is clicked or when
	 * the user logs in.
	 * 
	 * @param newView is the view to display
	 */
	private void setViewTo(BorderPane newView) {
		pane.setTop(null);
		addMenuRegisterListeners();
		pane.setCenter((Node) newView);
	}

	/**
	 * This method is called when a user attempts to sign up. If a user name is
	 * already taken or invalid info is entered an alert is shown.
	 * 
	 * @throws IOException if there's a words.txt file issue
	 */

	private void secondEnterButton() throws IOException {
		String username = usernameField.getText();
		String password = passwordField.getText();
		if (accounts.nameTaken(username)) {
			usernameTakenAlert();
		}
		// username can't be a blank field and password can't be empty field
		else if (username.isBlank() || password.isEmpty()) {
			invalidInfoAlert();
		} else {
			createAccount(username, password, false);
			// WordleGUI game = new WordleGUI();
			page = new StatisticsPage(currUser, accounts);
			game = new WordleGUI(currUser, page);
			settings = new AccountSettingsPage(currUser, accounts);
			setViewTo(game);
		}
	}

	/**
	 * This method handles when a user wants to play Wordle as a guest. An account
	 * is created but it is not saved to the account collection.
	 */
	private void guestButton() {
		System.out.println("Creating new wordle game...");
		createAccount("guest", "1", true);
		try {
			page = new StatisticsPage(currUser, accounts);
			game = new WordleGUI(currUser, page);
			settings = new AccountSettingsPage(currUser, accounts);
			setViewTo(game);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method shows an alert if a user name is taken.
	 */
	private void usernameTakenAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Username is taken");
		Optional<ButtonType> result = alert.showAndWait();
		usernameField.clear();
		passwordField.clear();
	}

	/**
	 * This method shows an alert if an invalid user name/password is entered.
	 */
	private void invalidInfoAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Invalid username/password");
		Optional<ButtonType> result = alert.showAndWait();
		usernameField.clear();
		passwordField.clear();
	}

	/**
	 * This method creates an account and adds it to the account collection, if it
	 * is not a guest account.
	 * 
	 * @param username     is the username for the account
	 * @param password     is the password for the account
	 * @param guestAccount true if account is guest account, otherwise false
	 */
	private void createAccount(String username, String password, Boolean guestAccount) {
		WordleAccount account = new WordleAccount(username, password);
		if (!guestAccount) {
			accounts.add(username, account);
		}
		currUser = account;
		System.out.println("Welcome " + username + "!");
	}

	/**
	 * This method handles the functionality of each button on the login and sign up
	 * pages.
	 */
	private void registerHandlers() {
		// 1st enter button is for entering login info
		enter1.setOnAction((event) -> {
			try {
				firstEnterButton();
			} catch (IOException e) {
				e.printStackTrace();
			}

		});

		// Clears pane and resets the GUI for sign up
		signup.setOnAction((event) -> {
			pane.getChildren().clear();
			usernameField.clear();
			passwordField.clear();
			signupLayoutGUI();
		});

		// 2nd enter button for entering sign up info
		enter2.setOnAction((event) -> {
			try {
				secondEnterButton();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		// Guest button allows user to continue as guest
		guest.setOnAction((event) -> {
			guestButton();
			System.out.println("Creating new wordle game...");
		});

		// Returns to previous page (login page)
		back.setOnAction((event) -> {
			pane.getChildren().clear();
			titlePane = new StackPane();
			usernameField.clear();
			passwordField.clear();
			layoutGUI();
		});
	}

	/**
	 * This method calls the handle method within the MenuButtonHandler.
	 */
	private void registerMenuHandlers(MenuItem item) {
		EventHandler<ActionEvent> menuButtonHandler = new MenuButtonHandler();
		item.setOnAction(menuButtonHandler);
	}

	/**
	 * This class defines the handle method which defines how the GUI will handle
	 * each menu item that can be clicked.
	 */
	private class MenuButtonHandler implements EventHandler<ActionEvent> {

		/**
		 * This method defines how the GUI will handle each menu item that can be
		 * clicked.
		 * 
		 * @param arg0 is the menu item
		 */
		@Override
		public void handle(ActionEvent arg0) {
			MenuItem menuItem = (MenuItem) arg0.getSource();
			String optionClicked = menuItem.getText();

			if (optionClicked.equals("Co-Wordle")) {
				System.out.println("starting cowordle game");
				if (!game.getIsCoWordle()) {
					game.setCoWordle();
					try {
						game.resetCoWordleGUI();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (game.isStatsPage) {
					try {
						game.resetWordleGUI();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				setViewTo(game);
			}

			if (optionClicked.equals("Wordle")) {
				System.out.println("changing view to worlde");
				// without checking if the stats page is displayed the
				// Wordle option won't work

				if (game.timeline != null) {
					game.timeline.stop();
				}

				if (game.isStatsPage) {
					game.setWordle();
					try {
						game.resetWordleGUI();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (game.getIsCoWordle()) {
					game.setWordle();
					try {
						game.startWordleGUI();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				setViewTo(game);
			}

			if (optionClicked.equals("Statistics")) {
				System.out.println("changing view to stats");
				// doesn't allow the user to select stats meny button if game pop up in progress
				if (!game.isStatsPage && !game.getIsCoWordle()) {
					setViewTo(page);
				}
			}

			if (optionClicked.equals("Switch Theme")) {
				System.out.println("Switching theme ..... ");

				// if its currently light mode, turn to dark
				if (isLightMode == true) {
					isLightMode = false;
					game.setMode(false);
					page.setColorMode(false);
					settings.setColorMode(false);

				}
				// if its currently dark mode turn light
				else {
					isLightMode = true;
					game.setMode(true);
					page.setColorMode(true);
					settings.setColorMode(true);
				}
			}

			if (optionClicked.equals("New Game")) {
				System.out.println("starting new game...");

				try {
					isLightMode = true;
					if (game.getIsCoWordle()) {
						game.timeline.stop();
						game = new WordleGUI(currUser, page, true);
					} else {
						game.timeline.stop();
						game = new WordleGUI(currUser, page, false);
					}
					page.setColorMode(isLightMode);
					settings.setColorMode(isLightMode);
					setViewTo(game);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (optionClicked.equals("Account Settings")) {
				System.out.println("Switching to account settings...");
				// settings = new AccountSettingsPage(currUser, accounts);
				if (game.isStatsPage) {
					try {
						game.resetWordleGUI();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				setViewTo(settings);
			}
			if (optionClicked.equals("Logout")) {
				System.out.println("logging out...");
				isLightMode = true;
				pane.getChildren().clear();
				titlePane = new StackPane();
				usernameField.clear();
				passwordField.clear();
				layoutGUI();
			}
			if (optionClicked.equals("How to Play")) {
				System.out.println("changing view to how to play");
				HowToPlayPane playPane = new HowToPlayPane();
				setViewTo(playPane);
			}
		}

	}

	/**
	 * This method saves account information when user exits
	 */
	private void saveChanges() {
		accounts.writeAccounts();
		Platform.exit();
		System.exit(0);
	}
}
