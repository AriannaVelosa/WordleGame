package view_controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
/**
 * Creates a pane showing the user how to play Wordle
 * @author Savannah Rabasa
 */
public class HowToPlayPane extends BorderPane {
	
	private BorderPane pane = new BorderPane();
	private VBox textOrganizer = new VBox();
	
	private Label title = new Label("How To Play");
	private Label subtitle = new Label("Guess the Wordle in 6 tries");
	private StackPane word1Pane = new StackPane();
	private GridPane word1PaneMod = new GridPane();
	private StackPane word2Pane = new StackPane();
	private GridPane word2PaneMod = new GridPane();
	private StackPane word3Pane = new StackPane();
	private GridPane word3PaneMod = new GridPane();
	
	private Label bullet1;
	private Label bullet2;
	private Label ex1_1;
	private Label ex2_1;
	private Label ex3_1;
	private Label ex1_2;
	private Label ex2_2;
	private Label ex3_2;
	private Label word1;
	private Label word2;
	private Label word3;
	private Label examples;
	
	/**
	 * Constructor for how to play pane
	 */
	public HowToPlayPane() {
		LayoutGUI();
	}
	
	/**
	 * Lays out pane for how to play instructions
	 */
	private void LayoutGUI() {
		// change font and size of text
		bullet1 = new Label("-  Each guess must be a valid 5-letter word.");
		bullet2 = new Label("-  The color of the tiles will change to show how close your guess was to the word.");
		examples = new Label("\nExamples");
		
		word1 = new Label("W       E       A      R       Y");
		decor1(word1Pane);
		word1Pane.getChildren().addAll(word1);
		word1PaneMod.add(word1Pane, 0, 0);
		GridPane exPane = new GridPane();
		word1PaneMod.add(exPane, 1, 0);
		
		
		word2 = new Label("P       I       L       L        S");
		decor2(word2Pane);
		word2Pane.getChildren().addAll(word2);
		word2PaneMod.add(word2Pane, 0, 0);
		word2PaneMod.add(exPane, 1, 0);
		
		word3 = new Label("V       A       G      U       E");
		decor3(word3Pane);
		word3Pane.getChildren().addAll(word3);
		word3PaneMod.add(word3Pane, 0, 0);
		word3PaneMod.add(exPane, 1, 0);
		
		examples.setAlignment(Pos.CENTER);
		
		ex1_1 = new Label("W ");
		ex2_1 = new Label("I ");
		ex3_1 = new Label("U ");
		
		
		ex1_2 = new Label("is in the word and in the correct spot");
		HBox ex1 = new HBox();
		ex1.getChildren().addAll(ex1_1, ex1_2);
		
		ex2_2 = new Label("is in the word but in the wrong spot");
		HBox ex2 = new HBox();
		ex2.getChildren().addAll(ex2_1, ex2_2);
		
		ex3_2 = new Label("is not in the word in any spot");
		HBox ex3 = new HBox();
		ex3.getChildren().addAll(ex3_1, ex3_2);
		
		textOrganizer.getChildren().addAll(title, subtitle, bullet1, bullet2, examples, word1PaneMod, ex1, word2PaneMod, ex2, word3PaneMod, ex3);
		textOrganizer.setSpacing(5);
	
		pane.setCenter(textOrganizer);
		
		setFonts();
		
		BorderPane spacingPane = new BorderPane();
		spacingPane.setMinSize(10, 10);
		pane.setLeft(spacingPane);
		
		this.setCenter(pane);
	}
	
	private void setFonts() {
		Font font = Font.font("Arial", 13);
		bullet1.setFont(font);
		bullet2.setFont(font);
		
		font = Font.font("Tahoma", FontWeight.BOLD, 30);
		title.setFont(font);
		
		font = Font.font("Berlin Sans RB Demi", 20);
		subtitle.setFont(font);
		
		font = Font.font("Arial", FontWeight.BOLD,13);
		word1.setFont(font);
		word2.setFont(font);
		word3.setFont(font);
		
		font = Font.font("Arial", FontWeight.BOLD, 14);	
		ex1_1.setFont(font);
		ex2_1.setFont(font);
		ex3_1.setFont(font);
		
		font = Font.font("Arial", 14);
		ex1_2.setFont(font);
		ex2_2.setFont(font);
		ex3_2.setFont(font);
		
		font = Font.font("Arial", FontWeight.BOLD, 17);	
		examples.setFont(font);
	}
	
	/**
	 * This method sets up the row of squares that appears behind the first Wordle example
	 */
	private void decor1(StackPane pane) {
		HBox squares = new HBox();
		squares.setSpacing(5);

		// 5 squares for each letter in Wordle
		for (int i = 0; i < 5; i++) {
			Rectangle rect = new Rectangle(30, 30);
			if (i == 0) {
				rect.setFill(Color.SEAGREEN);
			}
			else {
				rect.setFill(Color.WHITE);
			}
			rect.setStroke(Color.BLACK);
			rect.setStrokeWidth(1);
			squares.getChildren().add(rect);
		}
		
		squares.setAlignment(Pos.CENTER);
		
		word1Pane.getChildren().add(squares);
	}
	
	/**
	 * This method sets up the row of squares that appears behind the second Wordle example
	 */
	private void decor2(StackPane pane) {
		HBox squares = new HBox();
		squares.setSpacing(5);

		// 5 squares for each letter in Wordle
		for (int i = 0; i < 5; i++) {
			Rectangle rect = new Rectangle(30, 30);
			if (i == 1) {
				rect.setFill(Color.GOLDENROD);
			}
			else {
				rect.setFill(Color.WHITE);
			}
			rect.setStroke(Color.BLACK);
			rect.setStrokeWidth(1);
			squares.getChildren().add(rect);
		}
		squares.setAlignment(Pos.CENTER);
		word2Pane.getChildren().add(squares);
	}
	
	/**
	 * This method sets up the row of squares that appears behind the third Wordle example
	 */
	private void decor3(StackPane pane) {
		HBox squares = new HBox();
		squares.setSpacing(5);

		// 5 squares for each letter in Wordle
		for (int i = 0; i < 5; i++) {
			Rectangle rect = new Rectangle(30, 30);
			if (i == 1) {
				rect.setFill(Color.DIMGREY);
			}
			else {
				rect.setFill(Color.WHITE);
			}
			rect.setStroke(Color.BLACK);
			rect.setStrokeWidth(1);
			
			squares.getChildren().add(rect);
		}
		squares.setAlignment(Pos.CENTER);
		word3Pane.getChildren().add(squares);
	}
}
