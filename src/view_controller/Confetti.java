package view_controller;

import java.util.ArrayList;

import javafx.animation.PathTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class Confetti extends VBox {
	private Integer height;
	private Integer width;
	private VBox pane;
	private String word;
	private String person;
	private boolean co;
	private boolean mode;
	private ArrayList<ArrayList<Rectangle>> rects = new ArrayList<>();
	
	/**
	 * This class builds the confetti shown when a user wins.
	 * @param h - an integer, height of the pane
	 * @param w - an integer, width of the pane
	 * @param wd - a String, the correct word
	 * @param player - a String, the player that won
	 * @param cowordle - a boolean, if it is cowordle or not
	 * @param color - a boolean, if it is light mode or not
	 */
	public Confetti (Integer h, Integer w, String wd, String player, boolean cowordle, boolean color) {
		height = h;
		width = w;
		word = wd;
		person = player;
		co = cowordle;
		mode = color;
		pane = layoutGUI();
	}
	
	/**
	 * This is a getter method for the VBox with the labels and confetti.
	 * @return pane - a VBox
	 */
	public VBox getBox() {
		return pane;
	}
	
	/**
	 * This method animates the confetti falling from the top to the bottom
	 * of the gui using PathTransition.
	 */
	public void confettiFall() {
		Integer wd = width/8;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 7; j++) {
				wd = (wd * j) + i;
				Rectangle rect = rects.get(i).get(j);
				Line line = new Line(j + 5, j, 0, height - 5);
				
				PathTransition transition = new PathTransition();
				transition.setNode(rect);
				transition.setDuration(Duration.seconds(3));
				transition.setPath(line);
				transition.setCycleCount(1);
				transition.play();
			}
		}
	}
	
	/**
	 * This method creates the VBox that has all of the labels and confetti.
	 * It also makes it look nice and gives it some style.
	 * @return cRows - a VBox of labels and confetti
	 */
	private VBox layoutGUI() {
		VBox cRows = new VBox();
		makeConfetti();
		
		HBox row1 = makeHbox();
		HBox row2 = makeHboxBackwards();
		HBox row3 = makeHboxThin();
		
		Label win = new Label("Congrats, you won! The word was " + word + ".");
		Label wait = new Label("Please wait a few seconds to see your stats.");
		if (co) {
			win.setText("Congrats " + person + "! You guessed the word: " + word + ".");
			wait.setText("Dare to play again?");
		}
		Font font1 = Font.font("Tahoma", FontWeight.BOLD, 25);
		Font font2 = Font.font("Tahoma", FontWeight.BOLD, 15);
		win.setFont(font1);
		wait.setFont(font2);
		if (!mode) {
			win.setTextFill(Color.WHITE);
			wait.setTextFill(Color.WHITE);
		}
		
		cRows.getChildren().add(win);
		cRows.getChildren().add(wait);
		cRows.getChildren().add(row1);
		cRows.getChildren().add(row2);
		cRows.getChildren().add(row3);
		
		cRows.setStyle("-fx-padding: 15 0 0 0;");
		cRows.setSpacing(3);
		cRows.setAlignment(Pos.CENTER);
		return cRows;
	}
	
	/**
	 * This method creates an HBox of the rectangles.
	 * @return confetti - a HBox of rectangles
	 */
	private HBox makeHbox() {
		HBox confetti = new HBox();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 7; j++) {
				confetti.getChildren().add(rects.get(i).get(j));
			}
		}
		confetti.setSpacing(2);
		confetti.setAlignment(Pos.CENTER);
		return confetti;
	}
	
	/**
	 * This method creates an HBox of the rectangles but sparse.
	 * @return confetti - a HBox of rectangles
	 */
	private HBox makeHboxThin() {
		HBox confetti = new HBox();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				confetti.getChildren().add(rects.get(i).get(j));
			}
		}
		confetti.setSpacing(10);
		confetti.setAlignment(Pos.CENTER);
		return confetti;
	}
	
	/**
	 * This method creates an HBox of the rectangles but in reverse order and
	 * more sparse.
	 * @return confetti - a HBox of rectangles
	 */
	private HBox makeHboxThinBackwards() {
		HBox confetti = new HBox();
		for (int i = 0; i < 4; i++) {
			for (int j = 6; j >= 0; j--) {
				confetti.getChildren().add(rects.get(i).get(j));
			}
		}
		confetti.setSpacing(10);
		confetti.setAlignment(Pos.CENTER);
		return confetti;
	}
	
	/**
	 * This method creates an HBox of the rectangles but in reverse order.
	 * @return confetti - a HBox of rectangles
	 */
	private HBox makeHboxBackwards() {
		HBox confetti = new HBox();
		for (int i = 0; i < 8; i++) {
			for (int j = 6; j >= 0; j--) {
				confetti.getChildren().add(rects.get(i).get(j));
			}
		}
		confetti.setSpacing(2);
		confetti.setAlignment(Pos.CENTER);
		return confetti;
	}
	
	/**
	 * This method creates lists that have one rectangle from each color.
	 * It adds it to the list of rectangles.
	 */
	private void makeConfetti() {
		for (int i = 0; i < 8; i++) {
			ArrayList<Rectangle> row = new ArrayList<>();
			row.add(red());
			row.add(orange());
			row.add(yellow());
			row.add(green());
			row.add(blue());
			row.add(purple());
			row.add(pink());
			rects.add(row);
		}
	}
	
	/**
	 * This color creates the rectangle for red confetti.
	 * @return rect - a rectangle object
	 */
	private Rectangle red() {
		Rectangle rect = new Rectangle(10, 10, 10, 10);
		rect.setFill(Color.RED);
		return rect;
	}
	
	/**
	 * This color creates the rectangle for orange confetti.
	 * @return rect - a rectangle object
	 */
	private Rectangle orange() {
		Rectangle rect = new Rectangle(10, 10, 10, 10);
		rect.setFill(Color.ORANGE);
		return rect;
	}
	
	/**
	 * This color creates the rectangle for yellow confetti.
	 * @return rect - a rectangle object
	 */
	private Rectangle yellow() {
		Rectangle rect = new Rectangle(10, 10, 10, 10);
		rect.setFill(Color.YELLOW);
		return rect;
	}
	
	/**
	 * This color creates the rectangle for green confetti.
	 * @return rect - a rectangle object
	 */
	private Rectangle green() {
		Rectangle rect = new Rectangle(10, 10, 10, 10);
		rect.setFill(Color.GREEN);
		return rect;
	}
	
	/**
	 * This color creates the rectangle for blue confetti.
	 * @return rect - a rectangle object
	 */
	private Rectangle blue() {
		Rectangle rect = new Rectangle(10, 10, 10, 10);
		rect.setFill(Color.BLUE);
		return rect;
	}
	
	/**
	 * This color creates the rectangle for purple confetti.
	 * @return rect - a rectangle object
	 */
	private Rectangle purple() {
		Rectangle rect = new Rectangle(10, 10, 10, 10);
		rect.setFill(Color.PURPLE);
		return rect;
	}
	
	/**
	 * This color creates the rectangle for pink confetti.
	 * @return rect - a rectangle object
	 */
	private Rectangle pink() {
		Rectangle rect = new Rectangle(10, 10, 10, 10);
		rect.setFill(Color.PINK);
		return rect;
	}

}
