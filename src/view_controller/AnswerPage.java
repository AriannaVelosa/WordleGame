package view_controller;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AnswerPage extends BorderPane {
	private String answer;
	private String player;
	
	public AnswerPage(String answer, String player){ 
		this.answer = answer;
		this.player = player;
		LayoutGUI();
		registerHandlers();
	}
	
	private void LayoutGUI() {
		BorderPane pane = new BorderPane();
		
		VBox area = new VBox();
		Label winner = new Label(player + " wins!");
		Label correctWord = new Label(answer);
		area.getChildren().addAll(winner, correctWord);
		
		pane.setCenter(area);
	}
	
	public void registerHandlers() {
		
	}

}
