package myWordle;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WordleGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	// ------ Visual variables -------
	private BorderPane pane = new BorderPane();
	private VBox gameArea;
	
	private Label wordleLabel;
	// 6x5 squares
	private Rectangle letters[];
	private final int MAXLETTERS = 30;
	
	// functioning keyboard
	private final int KEYWIDTH = 50;
	private final int KEYHEIGHT = 50;
	
	// ------ functional variables ----
	private final int PANEWIDTH = 700;
	private final int PANEHEIGHT = 800;
	
	
	//private boolean previousFile;

	@Override
	public void start(Stage stage) {
		// Keep start small by partitioning
		layoutGUI();
		registerHandlers();

		// Put the pane in a sized Scene and show the GUI
		Scene scene = new Scene(pane, PANEWIDTH, PANEHEIGHT);
		stage.setScene(scene);
		// Don't forget to show the running app:
		stage.show();
	}

	private void layoutGUI() {
		gameArea = new VBox();
		
		// Title section
		wordleLabel = new Label("Wordle");
		wordleLabel.setFont(new Font("Arial", 24));
		
		// 2-D array, 6x3 of boxes for letters
		GridPane letters = new GridPane();
		for(int i = 1; i < 6; i++) {
			for(int j = 1; j < 7; j ++) {
				Rectangle rect = new Rectangle(50,50);
				rect.setFill(Color.WHITE);
			    rect.setStroke(Color.LIGHTGRAY);
				letters.add(rect, i, j);
			}
		}
		letters.setHgap(5);
		letters.setVgap(5);
		letters.setAlignment(Pos.CENTER);
		
		
		// Keyboard QWERTY style
		// ------ Top row of keyboard ------
		String row = "QWERTYUIOP";
		HBox topRow = new HBox();
		for(int i = 0; i < row.length(); i++) {
			Button button = new Button(String.valueOf(row.charAt(i)));
			button.setPrefSize(KEYWIDTH, KEYHEIGHT);
			topRow.getChildren().add(button);
		}
		topRow.setSpacing(5);
		topRow.setAlignment(Pos.CENTER);
		
		// ------ Middle row of keyboard ------
		String middleRow = "ASDFGHJKL";
		HBox centerRow = new HBox();
		for(int i = 0; i < middleRow.length(); i++) {
			Button button = new Button(String.valueOf(middleRow.charAt(i)));
			centerRow.getChildren().add(button);
			button.setPrefSize(KEYWIDTH, KEYHEIGHT);
		}
		centerRow.setSpacing(5);
		centerRow.setAlignment(Pos.CENTER);
		
		// ------ Bottom row of keyboard ------
		String lastRow = "ZXCVBNM";
		HBox bottomRow = new HBox();
		for(int i = 0; i < lastRow.length(); i++) {
			Button button = new Button(String.valueOf(lastRow.charAt(i)));
			bottomRow.getChildren().add(button);
			button.setPrefSize(KEYWIDTH, KEYHEIGHT);
		}
		bottomRow.setSpacing(5);
		bottomRow.setAlignment(Pos.CENTER);
		
		
		// Add elements to VBox
		gameArea.getChildren().addAll(wordleLabel, letters, topRow, centerRow, bottomRow);
		gameArea.setAlignment(Pos.CENTER);
		gameArea.setSpacing(10);
		pane.setCenter(gameArea);
	}
	

	private void registerHandlers() {
//		this.button.setOnAction((event) -> {
//			//TODO
//		})
		
	}

}
