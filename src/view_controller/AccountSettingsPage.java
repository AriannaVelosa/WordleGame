package view_controller;

import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.AccountCollection;
import model.WordleAccount;

public class AccountSettingsPage extends BorderPane{
	
	private final int PANEWIDTH = 700;
	private final int PANEHEIGHT = 800;
	
	// Visual Variables
	private BorderPane pane = new BorderPane();
	private Label title = new Label("Edit Profile");
	
	private Label confirmLabel = new Label("Confirm password before editing account:");
	private Label confirmPassword = new Label("Password:");
	private PasswordField securePassword = new PasswordField();
	private Button enter1 = new Button("Enter");
	
	private Label changeUsername = new Label("Change Username: ");
	private Label newUsername = new Label("New Username: ");
	private TextField typeNewUsername = new TextField();
	private Button enter2 = new Button("Enter");

	
	private Label changePassword = new Label("Change Password: ");
	private Label newPassword = new Label("New Password: ");
	private PasswordField typeNewPassword = new PasswordField();
	private Label confirmNewPassword = new Label("Confirm New Password: ");
	private PasswordField typeConfirmNewPassword = new PasswordField();
	private Button enter3 = new Button("Enter");
	private CheckBox show1 = new CheckBox("Show Password");
	private CheckBox show2 = new CheckBox("Show Password");
	private Label showPassword = new Label();
	private Label showConfirmPassword = new Label();
	
	// Functional variables
	private WordleAccount account;
	private AccountCollection accounts;
	private boolean show1Clicked = false;
	private boolean show2Clicked = false;
	private boolean isLightMode;

	public AccountSettingsPage(WordleAccount account, AccountCollection accounts) {
		isLightMode = true;
		layoutGUI();
		registerHandlers();
		this.account = account;
		this.accounts = accounts;
		
	}

	private void layoutGUI() {
		pane.setMinHeight(PANEHEIGHT);
		pane.setMinWidth(PANEHEIGHT);
		this.setLeft(pane);
		
		setUneditable();
		setColorMode(isLightMode);
		//pane.setStyle("-fx-background-color: white");		
		VBox layout = new VBox();
		HBox usernameLayout = new HBox();
		HBox passwordLayout = new HBox();
		HBox securityLayout = new HBox();
		HBox test = new HBox();
		HBox test2 = new HBox();

		VBox usernameLayout2 = new VBox();
		VBox passwordLayout2 = new VBox();
		setFonts();

		securityLayout.getChildren().add(confirmPassword);
		securityLayout.getChildren().add(securePassword);
		securityLayout.getChildren().add(enter1);

		usernameLayout2.getChildren().add(changeUsername);
		usernameLayout2.getChildren().add(newUsername);
		usernameLayout2.getChildren().add(typeNewUsername);
		usernameLayout2.getChildren().add(enter2);
		usernameLayout.getChildren().add(usernameLayout2);

		passwordLayout2.getChildren().add(changePassword);
		passwordLayout2.getChildren().add(newPassword);
		test.getChildren().add(typeNewPassword);
		test.getChildren().add(showPassword);
		//passwordLayout2.getChildren().add(typeNewPassword);
		passwordLayout2.getChildren().add(test);
		passwordLayout2.getChildren().add(show1);
		passwordLayout2.getChildren().add(confirmNewPassword);
		//passwordLayout2.getChildren().add(typeConfirmNewPassword);
		test2.getChildren().add(typeConfirmNewPassword);
		test2.getChildren().add(showConfirmPassword);
		passwordLayout2.getChildren().add(test2);
		passwordLayout2.getChildren().add(show2);
		passwordLayout2.getChildren().add(enter3);
		passwordLayout.getChildren().add(passwordLayout2);

		layout.getChildren().add(title);
		layout.getChildren().add(confirmLabel);
		layout.getChildren().add(securityLayout);
		layout.getChildren().add(usernameLayout);
		layout.getChildren().add(passwordLayout);

		
		layout.setAlignment(Pos.CENTER);
		layout.setSpacing(30);
		layout.setPadding(new Insets(50,100,0,0));
		
		securityLayout.setAlignment(Pos.CENTER);
		securityLayout.setSpacing(10);
		securityLayout.setPadding(new Insets(20, 0, 0, 0));
		
		usernameLayout.setAlignment(Pos.CENTER);
		usernameLayout.setSpacing(10);
		usernameLayout.setPadding(new Insets(20, 120, 0, 0));

		
		usernameLayout2.setSpacing(10);
		passwordLayout2.setSpacing(10);
		



		passwordLayout.setAlignment(Pos.CENTER);
		passwordLayout.setSpacing(10);
		//passwordLayout.setPadding(new Insets(0, 120, 0, 120));
		passwordLayout.setPadding(new Insets(0, 0, 0, 0));

		typeNewPassword.setMinWidth(180);
		typeNewPassword.setMaxWidth(180);
		typeConfirmNewPassword.setMinWidth(180);
		typeConfirmNewPassword.setMaxWidth(180);
		passwordLayout2.setMaxWidth(300);
		passwordLayout2.setMinWidth(300);
		
		showPassword.setMinWidth(250);
		showConfirmPassword.setMinWidth(250);
		
		pane.setTop(layout);
	}
	
	private void setUneditable() {
		typeNewUsername.setEditable(false);
		typeNewPassword.setEditable(false);
		typeConfirmNewPassword.setEditable(false);
		enter2.setDisable(true);
		enter3.setDisable(true);
		show1.setDisable(true);
		show2.setDisable(true);
	}
	
	private void setEditable() {
		confirmLabel.setStyle("-fx-text-fill: #343434;");
		typeNewUsername.setEditable(true);
		typeNewPassword.setEditable(true);
		typeConfirmNewPassword.setEditable(true);
		enter2.setDisable(false);
		enter3.setDisable(false);
		securePassword.setEditable(false);
		enter1.setDisable(true);
		show1.setDisable(false);
		show2.setDisable(false);
	}

	
	private void setFonts() {
		// Overall design TBD
		Font font1 = Font.font("Tahoma", FontWeight.BOLD, 50);
		Font font2 = Font.font("Tahoma", 20);
		Font font3 = Font.font("Tahoma", 15);

		title.setFont(font1);
		title.setStyle("-fx-text-fill: darkgrey;");
		confirmLabel.setFont(font2);
		confirmLabel.setStyle("-fx-text-fill: red;");
		confirmPassword.setFont(font3);
		changeUsername.setFont(font2);
		changePassword.setFont(font2);
		newUsername.setFont(font3);
		newPassword.setFont(font3);
		confirmNewPassword.setFont(font3);
		enter1.setFont(font3);
		enter2.setFont(font3);
		enter3.setFont(font3);
	}
	
	private void registerHandlers() {
		// 1st enter button is for entering login info
		enter1.setOnAction((event) -> {
			firstEnterButton();
		});
		
		enter2.setOnAction((event) -> {
			secondEnterButton();
		});
		
		enter3.setOnAction((event) -> {
			thirdEnterButton();
		});
		
		show1.setOnAction((event) -> {
			showPassword();
		});
		
		show2.setOnAction((event) -> {
			showConfirmPassword();
		});
		
		typeNewPassword.setOnKeyTyped((event) -> {
			if(show1Clicked) {
				System.out.println("typed");
				showPassword.setText(typeNewPassword.getText());
			}
		});
		
		typeConfirmNewPassword.setOnKeyTyped((event) -> {
			if(show2Clicked) {
				System.out.println("typed");
				showConfirmPassword.setText(typeConfirmNewPassword.getText());
			}
		});

	}

	private void showConfirmPassword() {
		if(!show2Clicked) {
			System.out.println("clicked");
			show2Clicked = true;
			String password = typeConfirmNewPassword.getText();
			showConfirmPassword.setText(password);
		}
		else {
			show2Clicked = false;
			showConfirmPassword.setText("");
		}
		
	}

	private void showPassword() {
		if(!show1Clicked) {
			System.out.println("clicked");
			show1Clicked = true;
			String password = typeNewPassword.getText();
			showPassword.setText(password);
		}
		else {
			show1Clicked = false;
			showPassword.setText("");
		}
	}

	private void thirdEnterButton() {
		String newPassword = typeNewPassword.getText();
		String newPasswordConfirmed = typeConfirmNewPassword.getText();

		if(!newPassword.equals(newPasswordConfirmed)) {
			System.out.println("Passwords do not match, try again");
			invalidPasswordAlert("Passwords do not match, try again");
		}
		else if(newPassword.equals(account.getPassword())) {
			System.out.println("New password can't be the same as old password");
			invalidPasswordAlert("New password can't be the same as old password");
		}
		else if (newPassword.isEmpty()){
			invalidPasswordAlert("Invalid password");
		}
		else {
			account.setPassword(newPassword);
			showPassword.setText("");
			showConfirmPassword.setText("");
			System.out.println("Password changed successfully!");
			changeSuccessfulAlert("Password successfully changed!");
		}
		typeNewPassword.clear();
		typeConfirmNewPassword.clear();
		
	}

	private void changeSuccessfulAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(message);
		Optional<ButtonType> result = alert.showAndWait();
		
	}

	private void secondEnterButton() {
		String newUsername = typeNewUsername.getText();
		if (accounts.nameTaken(newUsername) || newUsername.equals(account.getUsername())) {
			usernameTakenAlert();
		}
		// username can't be a blank field and password can't be empty field
		else if (newUsername.isBlank()) {
			invalidInfoAlert();
		} else {
			System.out.println("Username successfully changed!");
			changeSuccessfulAlert("Username successfully changed!");
			String oldUsername = account.getUsername();
			account.setUsername(newUsername);
			accounts.updateUsername(oldUsername, newUsername, account);
			typeNewUsername.clear();
		}
		
	}

	private void invalidInfoAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Invalid username/password");
		Optional<ButtonType> result = alert.showAndWait();
		typeNewUsername.clear();
		
	}

	private void usernameTakenAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Username is taken");
		Optional<ButtonType> result = alert.showAndWait();
		typeNewUsername.clear();		
	}
	
	private void invalidPasswordAlert(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(message);
		Optional<ButtonType> result = alert.showAndWait();
		showPassword.setText("");
		showConfirmPassword.setText("");
	}
	
	private void firstEnterButton() {
		if(securePassword.getText().equals(account.getPassword())) {
			System.out.println("Permission granted");
			setEditable();
			securePassword.clear();
		}
		else {
			System.out.println("Permission denied, try again");
			securePassword.clear();
		}
		
	}
	
	/**
	 * This method sets the color mode of the gui
	 * @param mode is either true for light mode or false for dark mode
	 */
	public void setColorMode(boolean mode) {
		isLightMode = mode;
		if (mode == true) {
			pane.setStyle("-fx-background-color: " + "white");
			confirmPassword.setStyle("-fx-text-fill: " + "black");
			changeUsername.setStyle("-fx-text-fill: " + "black");
			newUsername.setStyle("-fx-text-fill: " + "black");
			changePassword.setStyle("-fx-text-fill: " + "black");
			newPassword.setStyle("-fx-text-fill: " + "black");
			confirmNewPassword.setStyle("-fx-text-fill: " + "black");
			show1.setStyle("-fx-text-fill: " + "grey");
			show2.setStyle("-fx-text-fill: " + "grey");
			showPassword.setStyle("-fx-text-fill: " + "black");
			showConfirmPassword.setStyle("-fx-text-fill: " + "black");
		} else {
			pane.setStyle("-fx-background-color: " + "black");
			confirmPassword.setStyle("-fx-text-fill: " + "slategrey");
			changeUsername.setStyle("-fx-text-fill: " + "slategrey");
			newUsername.setStyle("-fx-text-fill: " + "slategrey");
			changePassword.setStyle("-fx-text-fill: " + "slategrey");
			newPassword.setStyle("-fx-text-fill: " + "slategrey");
			confirmNewPassword.setStyle("-fx-text-fill: " + "slategrey");
			show1.setStyle("-fx-text-fill: " + "darkgrey");
			show2.setStyle("-fx-text-fill: " + "darkgrey");
			showPassword.setStyle("-fx-text-fill: " + "darkgrey");
			showConfirmPassword.setStyle("-fx-text-fill: " + "darkgrey");

		}
	}

}
